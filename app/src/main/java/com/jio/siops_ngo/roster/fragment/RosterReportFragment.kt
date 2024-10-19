package com.jio.siops_ngo.roster.fragment


import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.jio.jioinfra.bean.CommonBean
import com.jio.jioinfra.network.business.BaseCoroutines
import com.jio.jioinfra.utilities.Busicode
import com.jio.jioinfra.utilities.MyConstants
import com.jio.jioinfra.utilities.ViewUtils
import com.jio.myjio.bean.CoroutinesResponse
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.databinding.FragmentApprovalsPendingBinding
import com.jio.siops_ngo.databinding.FragmentRosterReportBinding
import com.jio.siops_ngo.databinding.FragmentTimeTrackingViewSimilarBinding
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.ngo.adapter.ApprovalsPendingListAdapter
import com.jio.siops.ngo.adapter.TimeTrackingViewSimilarAdapter
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.T
import com.jio.siops_ngo.utilities.Utils
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 */
class RosterReportFragment : Fragment(), View.OnClickListener {
    var domainId: String? = ""
    var selectedMonth: Int? = -1
    var currentMonth: Int? = -1
    override fun onClick(v: View?) {

        val id = v!!.getId()

        if (id == R.id.btn_prev_month) {
            getRosterForTheMonth(selectedMonth!! - 1)


        } else if (id == R.id.btn_next_month) {
            getRosterForTheMonth(selectedMonth!! + 1)
        } else {

            var txtView = mBinding!!.lnrName.findViewById<TextView>(id)

            if (null != txtView) {
                /*Toast.makeText(
                    mContext,
                    "Clicked on row :: " + id + ", Text :: " + txtView.getText().toString(),
                    Toast.LENGTH_SHORT
                ).show()*/
                var selectedDomainId = txtView.getText().toString()

                val commonBean = CommonBean()
                var rosterReportFragment = RosterReportFragment.newInstance()
                rosterReportFragment.setData(selectedDomainId!!, selectedMonth!!)
                (activity as MainActivity)!!.openFragments(
                    rosterReportFragment,
                    commonBean,
                    true,
                    true
                )
            }
        }


    }

    var mBinding: FragmentRosterReportBinding? = null
    var listData: ArrayList<Map<String, Any>>? = null
    var apiRequiredDateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    var monthYearFormat: SimpleDateFormat = SimpleDateFormat("MMM yyyy")
    var mContext: Context? = null
    var numOfDaysInMonth: Int? = 0


    companion object {
        fun newInstance() = RosterReportFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_roster_report,
            container,
            false
        )
        return mBinding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mContext = activity as MainActivity
        /*val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val day = 1
        var month: Int = -1
        if (selectedMonth != -1) {
            month = c.get(Calendar.MONTH)
            c.set(year, month, day)
        } else {
            month = selectedMonth!! + 1
            c.set(year, selectedMonth!!, day)
        }


        numOfDaysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH)
        var currentMonthStartDate = apiRequiredDateFormat.format(c.time)
        System.out.println("First Day of month: " + currentMonthStartDate)
        c.add(Calendar.DAY_OF_MONTH, numOfDaysInMonth!! - 1)
        var currentMonthEndDate = apiRequiredDateFormat.format(c.time)
        System.out.println("Last Day of month: " + currentMonthEndDate)
        fetchData(currentMonthStartDate, currentMonthEndDate)
        mBinding!!.txtMonth.text = monthYearFormat.format(c.time)*/
        val c = Calendar.getInstance()
        var month :Int
        currentMonth = c.get(Calendar.MONTH)
        if (selectedMonth == -1) {
            month = c.get(Calendar.MONTH)

        } else {
            month = selectedMonth!!
        }
        if(month == 0){
            mBinding!!.btnPrevMonth.visibility = View.GONE
        }
        if(month == 11){
            mBinding!!.btnNextMonth.visibility = View.GONE
        }
        getRosterForTheMonth(month)

        mBinding!!.btnNextMonth.setOnClickListener (this)
        mBinding!!.btnPrevMonth.setOnClickListener (this)

    }

    fun fetchData(startDate: String, endDate: String) {
        mBinding!!.lnrName.removeAllViews()
        mBinding!!.table.removeAllViews()
        (activity as MainActivity).showProgressBar()

        val requestBody = HashMap<String, Any>()
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "userInfo"
        requestBody["relatedTo"] = "ROSTER"
        requestBody["startDate"] = startDate
        requestBody["endDate"] = endDate
        if (ViewUtils.isEmptyString(domainId)) {
            domainId = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
            requestBody["domainId"] = domainId!!

        } else {
            requestBody["domainId"] = domainId!!
        }
        //   requestBody["appRoleCode"] = "741"
        CoroutineScope(Dispatchers.IO).launch {

            var job = async {
                BaseCoroutines().fetchData(
                    requestBody,
                    Busicode.ApprovalRosterLeaveHistory,
                    activity as MainActivity
                )
            }
            withContext(Dispatchers.Main)
            {

                var response = job.await()
                if (activity != null) {
                    (activity as MainActivity).hideProgressBar()
                }
                if (response!!.responseEntity != null && response.status == MappActor.STATUS_OK) {
//                    dynamicColumns()
                    filterData(response)
                    /*addHeaders()
                    filterData(response)*/
                } else if (response!!.errorCode != null && response!!.errorCode.equals(MappActor.VERSION_SESSION_INVALID)) {
                    (activity as MainActivity).showDialogForSessionExpired(
                        (activity as MainActivity).resources.getString(
                            R.string.session_expired
                        ), (activity as MainActivity)
                    )
                } else if (response!!.errorMsg != null) {
                    T.show(activity, response!!.errorMsg!!, 0)
                } else {
                    T.show(activity, "Something went wrong!", 0)
                }
            }

        }
    }


    fun filterData(mCoroutinesResponse: CoroutinesResponse) {

        try {

            /*mBinding!!.lnrName.removeAllViews()
            mBinding!!.table.removeAllViews()*/
            val msg = mCoroutinesResponse.responseEntity as HashMap<String, Any>
            if (msg != null) {
                try {
                    if (msg.containsKey("dataList") && msg["dataList"] != null) {
                        listData = msg["dataList"] as ArrayList<Map<String, Any>>
                        val tr = TableRow(mContext)
                        for (i in 1 until numOfDaysInMonth!! + 1) {
                            tr.layoutParams = getLayoutParams()
                            tr.addView(
                                getDateTextView(
                                    i + numOfDaysInMonth!!,
                                    i.toString(),
                                    Color.BLACK,
                                    Typeface.NORMAL,
                                    ContextCompat.getColor(
                                        mContext!!,
                                        R.color.white
                                    )
                                )
                            )

                        }
                        mBinding!!.table.addView(tr, TableLayout.LayoutParams())
                        mBinding!!.lnrName.addView(
                            getTextView(
                                0,
                                "",
                                Color.BLACK,
                                Typeface.NORMAL,
                                ContextCompat.getColor(mContext!!, R.color.white),
                                "false"
                            )
                        )

                        for (i in 0 until listData!!.size) {

                            var content = listData!![i]
                            if (content.containsKey("DomainID") && content["DomainID"] != null) {
                                var isManager = "false"
                                if (content.containsKey("IsManager") && content["IsManager"] != null) {
                                    isManager = content["IsManager"].toString()
                                }

                                mBinding!!.lnrName.addView(
                                    getTextView(
                                        i + 1,
                                        content["DomainID"].toString(),
                                        Color.BLACK,
                                        Typeface.NORMAL,
                                        ContextCompat.getColor(mContext!!, R.color.white),
                                        isManager
                                    )
                                )
                            }
                            if (content.containsKey("RosterList") && content["RosterList"] != null) {
                                val tr = TableRow(mContext)
                                val rosterList =
                                    content["RosterList"] as ArrayList<Map<String, Any>>
                                for (j in 0 until rosterList!!.size) {
                                    var rosterRow = rosterList[j]
                                    if (rosterRow.containsKey("Value") && rosterRow["Value"] != null) {
                                        /*var newRow = TextView(mContext);
                                        newRow.text = j.toString()
                                        tr.addView(newRow)*/
                                        tr.layoutParams = getLayoutParams()
                                        if (rosterRow["Value"]!!.equals("L")) {
                                            tr.addView(
                                                getDateTextView(
                                                    j + rosterList!!.size,
                                                    rosterRow["Value"].toString(),
                                                    Color.WHITE,
                                                    Typeface.NORMAL,
                                                    ContextCompat.getColor(
                                                        mContext!!,
                                                        R.color.red_error_color
                                                    )
                                                )
                                            )
                                        } else if (rosterRow["Value"]!!.equals("WO")) {
                                            tr.addView(
                                                getDateTextView(
                                                    j + rosterList!!.size,
                                                    rosterRow["Value"].toString(),
                                                    Color.WHITE,
                                                    Typeface.NORMAL,
                                                    ContextCompat.getColor(
                                                        mContext!!,
                                                        R.color.roster_green_color_code
                                                    )
                                                )
                                            )
                                        } else {
                                            tr.addView(
                                                getDateTextView(
                                                    j + rosterList!!.size,
                                                    rosterRow["Value"].toString(),
                                                    Color.BLACK,
                                                    Typeface.NORMAL,
                                                    ContextCompat.getColor(
                                                        mContext!!,
                                                        R.color.white
                                                    )
                                                )
                                            )
                                        }


                                    }

                                }
                                mBinding!!.table.addView(tr, TableLayout.LayoutParams())

                            }


                        }

                        /*var adapter = RosterReportAdapter(activity as MainActivity, listData!!)
                        mBinding!!.recyclerviewRoster.layoutManager = LinearLayoutManager(activity)
                        mBinding!!.recyclerviewRoster.itemAnimator = DefaultItemAnimator()
                        mBinding!!.recyclerviewRoster!!.adapter = adapter
                        adapter!!.notifyDataSetChanged()*/
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                    MyExceptionHandler.handle(e)
                }
            }
        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
            T.show(activity, getString(R.string.something_went_wrong), 0)
        }
    }


    private fun getTextView(
        id: Int,
        title: String,
        color: Int,
        typeface: Int,
        bgColor: Int,
        isManager: String
    ): TextView {
        val tv = TextView(mContext)
        tv.id = id
        tv.text = title
        tv.setTextColor(color)
//        tv.setPadding(40, 40, 40, 40)
        tv.setPadding(20, 20, 20, 20)
        tv.setTypeface(Typeface.DEFAULT, typeface)
        tv.setBackgroundColor(bgColor)
        tv.layoutParams = getLayoutParams()
//        tv.getLayoutParams().width = 120;
        tv.getLayoutParams().height = 120;
        tv.gravity = Gravity.CENTER_VERTICAL
        if (isManager.equals("true") && !title.equals(domainId)) {
            tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_right_arrow, 0)
            tv.setOnClickListener(this)

        } else {
            tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        }

        return tv
    }


    private fun getDateTextView(
        id: Int,
        title: String,
        color: Int,
        typeface: Int,
        bgColor: Int
    ): TextView {
        val tv = TextView(mContext)
        tv.id = id
        tv.text = title
        tv.setTextColor(color)
        tv.setPadding(20, 20, 20, 20)
        tv.setTypeface(Typeface.DEFAULT, typeface)
        tv.setBackgroundColor(bgColor)
        tv.layoutParams = getLayoutParams()
        tv.getLayoutParams().width = 120;
        tv.getLayoutParams().height = 120;
        tv.gravity = Gravity.CENTER
//        tv.setOnClickListener(this)
        return tv
    }

    @NonNull
    private fun getLayoutParams(): TableRow.LayoutParams {
        val params = TableRow.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(2, 0, 0, 2)
        return params
    }

    @NonNull
    private fun getTblLayoutParams(): TableLayout.LayoutParams {
        return TableLayout.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT
        )
    }

    /**
     * This function add the headers to the table
     */
    /*fun addHeaders() {
        val tr = TableRow(mContext)
        tr.layoutParams = getLayoutParams()
        tr.addView(getTextView(0, "COMPANY", Color.WHITE, Typeface.BOLD, Color.BLUE))
        tr.addView(getTextView(0, "OS", Color.WHITE, Typeface.BOLD, Color.BLUE))
        tr.addView(getTextView(0, "Versions", Color.WHITE, Typeface.BOLD, Color.BLUE))
        tr.addView(getTextView(0, "OS-Copy", Color.WHITE, Typeface.BOLD, Color.BLUE))
        tr.addView(getTextView(0, "OS-Copy-Copy", Color.WHITE, Typeface.BOLD, Color.BLUE))
        mBinding!!.table.addView(tr, getTblLayoutParams())





    }*/

    /*fun addData() {
        val numCompanies = companies.size

        for (i in 0 until numCompanies) {
            val tr = TableRow(mContext)
            tr.layoutParams = getLayoutParams()
            tr.addView(
                getTextView(
                    i + 1,
                    companies[i],
                    Color.BLACK,
                    Typeface.NORMAL,
                    ContextCompat.getColor(mContext!!, R.color.white)
                )
            )
            tr.addView(
                getTextView(
                    i + numCompanies,
                    os[i],
                    Color.BLACK,
                    Typeface.NORMAL,
                    ContextCompat.getColor(mContext!!, R.color.white)
                )
            )
            tr.addView(
                getTextView(
                    i + numCompanies,
                    versions[i],
                    Color.BLACK,
                    Typeface.NORMAL,
                    ContextCompat.getColor(mContext!!, R.color.white)
                )
            )
            tr.addView(
                getTextView(
                    i + numCompanies,
                    os[i],
                    Color.BLACK,
                    Typeface.NORMAL,
                    ContextCompat.getColor(mContext!!, R.color.white)
                )
            )
            tr.addView(
                getTextView(
                    i + numCompanies,
                    os[i],
                    Color.BLACK,
                    Typeface.NORMAL,
                    ContextCompat.getColor(mContext!!, R.color.white)
                )
            )
            mBinding!!.table.addView(tr, getTblLayoutParams())
        }

        for (i in rows.indices) {

            mBinding!!.lnrName.addView(
                getTextView(
                    i,
                    rows[i],
                    Color.WHITE,
                    Typeface.NORMAL,
                    ContextCompat.getColor(mContext!!, R.color.colorAccent)
                )
            )
        }
    }*/

    /**
     * This function add the data to the table
     */
    /*fun addData() {
        val numCompanies = companies.size

        for (i in 0 until numCompanies) {
            val tr = TableRow(activity as MainActivity)
            tr.layoutParams = getLayoutParams()
            tr.addView(
                getTextView(
                    i + 1,
                    companies[i],
                    Color.WHITE,
                    Typeface.NORMAL,
                    ContextCompat.getColor(mContext!!, R.color.colorAccent)
                )
            )
            tr.addView(
                getTextView(
                    i + numCompanies,
                    os[i],
                    Color.WHITE,
                    Typeface.NORMAL,
                    ContextCompat.getColor(mContext!!, R.color.colorAccent)
                )
            )
            tr.addView(
                getTextView(
                    i + numCompanies,
                    versions[i],
                    Color.WHITE,
                    Typeface.NORMAL,
                    ContextCompat.getColor(mContext!!, R.color.colorAccent)
                )
            )
            tr.addView(
                getTextView(
                    i + numCompanies,
                    os[i],
                    Color.WHITE,
                    Typeface.NORMAL,
                    ContextCompat.getColor(mContext!!, R.color.colorAccent)
                )
            )
            tr.addView(
                getTextView(
                    i + numCompanies,
                    os[i],
                    Color.WHITE,
                    Typeface.NORMAL,
                    ContextCompat.getColor(mContext!!, R.color.colorAccent)
                )
            )
            tl.addView(tr, getTblLayoutParams())
        }

        for (i in rows.indices) {

            lnrNames.addView(
                getTextView(
                    i,
                    rows[i],
                    Color.WHITE,
                    Typeface.NORMAL,
                    ContextCompat.getColor(mContext!!, R.color.colorAccent)
                )
            )
        }
    }*/

    /*fun dynamicColumns(){
    var col1 = ""
    var col2= ""
    var col3 = ""
    var col4 = ""
        for (x in 0 until 10) {

            col1 = "(" + x + ") Column 1";
            col2 = "(" + x + ") Column 2";
            col3 = "(" + x + ") Column 3";
            col4 = "(" + x + ") Column 4";

            var newRow = TableRow(mContext);

            var column1 = TextView(mContext);
            var column2 =  TextView(mContext);
            var column3 =  TextView(mContext);
            var column4 =  TextView(mContext);

            column1.setText(col1);
            column2.setText(col2);
            column3.setText(col3);
            column4.setText(col4);

            newRow.addView(column1);
            newRow.addView(column2);
            newRow.addView(column3);
            newRow.addView(column4);

            mBinding!!.table.addView(newRow,  TableLayout.LayoutParams())
        }
    }*/

    fun setData(domainId: String, selectedMonth: Int) {
        this.domainId = domainId
        this.selectedMonth = selectedMonth
    }

    fun getRosterForTheMonth(monthSelected:Int){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val day = 1
        var month: Int = -1
        selectedMonth = monthSelected
        if(monthSelected == 0){
            mBinding!!.btnPrevMonth.visibility = View.GONE
        }else{
            mBinding!!.btnPrevMonth.visibility = View.VISIBLE
        }
        if(monthSelected == currentMonth){
            mBinding!!.btnNextMonth.visibility = View.GONE
        }else{
            mBinding!!.btnNextMonth.visibility = View.VISIBLE
        }
        /*if (monthSelected != -1) {
            month = c.get(Calendar.MONTH)
            c.set(year, month, day)
        } else {
            month = monthSelected!! + 1
            c.set(year, monthSelected!!, day)
        }*/
        c.set(year, monthSelected!!, day)



        numOfDaysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH)
        var currentMonthStartDate = apiRequiredDateFormat.format(c.time)
        System.out.println("First Day of month: " + currentMonthStartDate)
        c.add(Calendar.DAY_OF_MONTH, numOfDaysInMonth!! - 1)
        var currentMonthEndDate = apiRequiredDateFormat.format(c.time)
        System.out.println("Last Day of month: " + currentMonthEndDate)
        fetchData(currentMonthStartDate, currentMonthEndDate)
        mBinding!!.txtMonth.text = monthYearFormat.format(c.time)



    }

}
