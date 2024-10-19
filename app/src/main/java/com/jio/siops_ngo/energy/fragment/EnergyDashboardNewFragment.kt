package com.jio.siops_ngo.energy.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.jio.jioinfra.bean.CommonBean
import com.jio.jioinfra.network.business.BaseCoroutines
import com.jio.jioinfra.utilities.Busicode
import com.jio.jioinfra.utilities.MyConstants
import com.jio.myjio.bean.CoroutinesResponse
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.databinding.FragmentEnerygyDashboardNavDrawerBinding
import com.jio.siops_ngo.infra.adapter.EnergyDashboardNewAdapter
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.T
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 */
class EnergyDashboardNewFragment : Fragment() {

    var mBinding: FragmentEnerygyDashboardNavDrawerBinding? = null

    var category: String? = null
    var parentCode: String? = null
    var selectedDate: String? = null
    var regionlist: ArrayList<Map<String, Any>>? = null
    var codeRegionlist: ArrayList<String>? = ArrayList()
    var codeStatelist: ArrayList<String>? = ArrayList()
    var codeMplist: ArrayList<String>? = ArrayList()
    var codeJclist: ArrayList<String>? = ArrayList()
    var stateMaplist: ArrayList<Map<String, Any>>? = null
    var mpMaplist: ArrayList<Map<String, Any>>? = null
    var jcMaplist: ArrayList<Map<String, Any>>? = null
    var rgList: ArrayList<String>? = ArrayList()
    var stateList: ArrayList<String>? = ArrayList()
    var mpList: ArrayList<String>? = ArrayList()
    var jcList: ArrayList<String>? = ArrayList()
    var eNodeList: ArrayList<String>? = ArrayList()

    //  var eNodeList=arrayOf("eNodeB")
    var startDate: String? = null
    var endDate: String? = null
    var region: String? = "ALL"
    var circle: String? = "ALL"
    var mp: String? = "ALL"
    var jc: String? = "ALL"
    var duration: String? = "Daily"
    var categoryFilter: String? = "ALL"

    // var arr = arrayOf("eNodeB")
    companion object {
        fun newInstance() = EnergyDashboardNewFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_enerygy_dashboard_nav_drawer, container, false
        )
        return mBinding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mBinding!!.rbDaily.isChecked = true
        initView()


    }

    //Start initialize View
    private fun initView() {
        (activity as MainActivity).rel_notification.visibility = View.VISIBLE

        val cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);

        val c = cal.time
        //c.add(Calendar.DATE, -1);

        region = "ALL"
        circle = "ALL"
        mp = "ALL"
        jc = "ALL"
        duration = "Daily"
        categoryFilter = "ALL"
        /*mBinding!!.rbDaily.isChecked = true
        mBinding!!.rbMtd.isChecked = false
        mBinding!!.rbYtd.isChecked = false*/
        val df = SimpleDateFormat("dd-MM-yyyy")
        var formattedDate = df.format(c)

        startDate = formattedDate

        endDate = formattedDate
        mBinding!!.txtDate.text = formattedDate
        category = null
        parentCode = null
        fetchDataEnergy()
        fetchFilterAgeienDbData("INVENTORY_MASTER_REGION")

        // eNodeList!!.add("eNodeB")


        mBinding!!.radioGroup1.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->

                if (checkedId == mBinding!!.rbDaily.id) {
                    // category1= "ALL"
                    duration = "Daily"
                    fetchDataEnergy()

                } else if (checkedId == mBinding!!.rbMtd.id) {
                    // categor1 = "IP-Colo"
                    duration = "MTD"
                    fetchDataEnergy()


                } else if (checkedId == mBinding!!.rbYtd.id) {
                    // category = "P1"
                    duration = "YTD"
                    fetchDataEnergy()
                }
            }
        )


        mBinding!!.rbDaily.isChecked = true
//        mBinding!!.radioGroup2.setOnCheckedChangeListener(
//            RadioGroup.OnCheckedChangeListener { group, checkedId ->
//
//                if (checkedId == mBinding!!.rbAll.id) {
//                    categoryFilter = "ALL"
//
//                } else if (checkedId == mBinding!!.rbAg2.id) {
//                    categoryFilter = "AG1"
//
//
//
//                } else if (checkedId == mBinding!!.rbAg1.id) {
//                    categoryFilter = "AG2"
//
//
//
//                } else if (checkedId == mBinding!!.rbEnb.id) {
//                    categoryFilter = "eNodeB"
//
//
//                }
//
//
//            }
//        )


        // Pending Action Click Button
        mBinding!!.cnstrntLPendingAction.setOnClickListener {
            val commonBean = CommonBean()

            var energyEnergyOpenActionFragment = EnergyOpenActionFragment.newInstance()
            commonBean!!.setmTitle(MyConstants.DCB)
            (activity as MainActivity).openFragments(
                energyEnergyOpenActionFragment,
                commonBean!!,
                true,
                true
            )
        }


        // Calander Img Click Click Button
        /*mBinding!!.imgCalendar.setOnClickListener {

            if (mBinding!!.cnstrntLCalendar.visibility == View.VISIBLE) {
                mBinding!!.cnstrntLCalendar.visibility = View.GONE
            } else {

                if (selectedDate != null) {
                    mBinding!!.cnstrntLCalendar.visibility = View.VISIBLE

                    mBinding!!.calendarView.setDate(
                        SimpleDateFormat("dd/MM/yyyy").parse(selectedDate)!!.time,
                        true,
                        true
                    )
                } else {
                    mBinding!!.cnstrntLCalendar.visibility = View.VISIBLE
                    val cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, -1);
                    *//*mBinding!!.calendarView.setDate(
                        Calendar.getInstance().getTimeInMillis(),
                        false,
                        true
                    );
                    mBinding!!.calendarView.setMaxDate(Calendar.getInstance().getTimeInMillis())*//*


                    mBinding!!.calendarView.setDate(
                        cal.getTimeInMillis(),
                        false,
                        true
                    );
                    mBinding!!.calendarView.setMaxDate(cal.getTimeInMillis())
                }
            }

        }*/

        // For Use Calender View Click
        mBinding!!.calendarView?.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // Note that months are indexed from 0. So, 0 means January, 1 means february, 2 means march etc.
            selectedDate = "Selected date is " + dayOfMonth + "/" + (month + 1) + "/" + year
            var displayMonth: String = ""
            var displayDay: String = ""
            if (month < 10)
                displayMonth = "0" + (month + 1)
            else {
                displayMonth = (month + 1).toString()
            }

            if (dayOfMonth < 10)
                displayDay = "0" + dayOfMonth
            else {
                displayDay = dayOfMonth.toString()
            }

            selectedDate = displayDay + "/" + displayMonth + "/" + year
            startDate = displayDay + "-" + displayMonth + "-" + year
            endDate = displayDay + "-" + displayMonth + "-" + year
            duration = "Daily"
            mBinding!!.rbDaily.isChecked = true
            mBinding!!.rbMtd.isChecked = false
            mBinding!!.rbYtd.isChecked = false
            mBinding!!.txtDate.text = startDate
            fetchDataEnergy()
            mBinding!!.cnstrntLCalendar.visibility = View.GONE
        }
        // Apply Click Button
        mBinding!!.txtApply.setOnClickListener {
            mBinding!!.drawerLayout.closeDrawers()

            fetchDataEnergy()
        }

        // Filter Click Button
        mBinding!!.imgFilter.setOnClickListener {

            if (mBinding!!.drawerLayout.isDrawerOpen(GravityCompat.END)) {
                mBinding!!.drawerLayout.closeDrawers()
            } else {
                mBinding!!.drawerLayout.openDrawer(GravityCompat.END)
            }
        }
        //Start Region Selection dropDown
        mBinding!!.spnrEnode.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                arg0: AdapterView<*>?,
                arg1: View,
                arg2: Int,
                arg3: Long
            ) { // TODO Auto-generated method stub
                //  category = rgList!!.get(arg2)
                //  parentCode = rgList!!.get(arg2)
                //  region=rgList!!.get(arg2)

                //    categoryFilter=eNodeList!!.get(arg2)

                //fetchFilterAgeienDbData("INVENTORY_MASTER_CIRCLE")
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) { // TODO Auto-generated method stub
            }
        })


        //Start Region Selection dropDown
        mBinding!!.spnrRegion.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                arg0: AdapterView<*>?,
                arg1: View,
                arg2: Int,
                arg3: Long
            ) { // TODO Auto-generated method stub
                category = rgList!!.get(arg2)
                parentCode = rgList!!.get(arg2)
                region = codeRegionlist!!.get(arg2)

                fetchFilterAgeienDbData("INVENTORY_MASTER_CIRCLE")
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) { // TODO Auto-generated method stub
            }
        })

        //End Region Selection dropDown


        //Start State Selection dropDown
        mBinding!!.spnrState.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                arg0: AdapterView<*>?,
                arg1: View,
                arg2: Int,
                arg3: Long
            ) { // TODO Auto-generated method stub
                category = stateList!!.get(arg2)
                circle = codeStatelist!!.get(arg2)
                fetchFilterAgeienDbData("INVENTORY_MASTER_MP")
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) { // TODO Auto-generated method stub
            }
        })
//End State Selection dropDown

        //Start Mp Selection dropDown
        mBinding!!.spnrMp.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                arg0: AdapterView<*>?,
                arg1: View,
                arg2: Int,
                arg3: Long
            ) { // TODO Auto-generated method stub

                category = mpList!!.get(arg2)
                mp = codeMplist!!.get(arg2)
                fetchFilterAgeienDbData("INVENTORY_MASTER_JC")
                parentCode = mpList!!.get(arg2)

            }

            override fun onNothingSelected(arg0: AdapterView<*>?) { // TODO Auto-generated method stub
            }
        })

        //End Mp Selection dropDown

        //Start Jc Selection dropDown
        mBinding!!.spnrJc.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                arg0: AdapterView<*>?,
                arg1: View,
                arg2: Int,
                arg3: Long
            ) { // TODO Auto-generated method stub
                //  regionlist!!.get(arg2)["name"]
                //   category=jcList!!.get(arg2)
                // parentCode = rgList!!.get(arg2)
                jc = codeJclist!!.get(arg2)
                //   fetchFilterAgeienDbData("INVENTORY_MASTER_JC")

            }

            override fun onNothingSelected(arg0: AdapterView<*>?) { // TODO Auto-generated method stub
            }
        })
    }
    //End initialize View
    //End Jc Selection dropDown

    // Start Call Api For Filter
    fun fetchFilterAgeienDbData(ddqCode: String) {

        //  (activity as MainActivity).showProgressBar()
        val requestBody = HashMap<String, Any>()

        if (category != null) {
            requestBody["category"] = category!!
            requestBody["parentCode"] = parentCode!!
        }
        //  requestBody["category"] = category!!
        //  requestBody["parentCode"] = parentCode!!
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "userInfo"
        requestBody["ddqCode"] = ddqCode!!
        CoroutineScope(Dispatchers.IO).launch {

            var job = async {
                BaseCoroutines().fetchData(
                    requestBody,
                    Busicode.Filter,
                    activity as MainActivity
                )
            }
            withContext(Dispatchers.Main)
            {

                var response = job.await()
                if (activity != null) {
                    (activity!! as MainActivity).hideProgressBar()
                }

                if (response!!.responseEntity != null && response.status == MappActor.STATUS_OK) {
                    filterReffreshData(response, ddqCode)
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
    // End Call Api For Filter

    // Show Api Data on UI
    fun filterReffreshData(mCoroutinesResponse: CoroutinesResponse, ddqCode: String) {

        try {
            val msg = mCoroutinesResponse.responseEntity as HashMap<String, Any>


            if (msg != null) {
                try {
                    if (ddqCode.equals("INVENTORY_MASTER_REGION")) {
                        regionlist = msg["filter"] as ArrayList<Map<String, Any>>
                        rgList!!.clear()
                        codeRegionlist!!.clear()
                        for ((index, value) in regionlist!!.withIndex()) {
                            val regionMap = value
                            var regionSelected = regionMap.get("name") as String

                            rgList!!.add((regionSelected as String?)!!)
                            codeRegionlist!!.add(regionMap.get("code") as String)
                        }
                        val langAdapter =
                            ArrayAdapter<CharSequence>(
                                activity!!,
                                R.layout.spinner_text,
                                rgList!! as List<CharSequence>
                            )
                        //   langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
                        mBinding!!.spnrRegion.setAdapter(langAdapter)

                    } else if (ddqCode.equals("INVENTORY_MASTER_CIRCLE")) {


                        stateList!!.clear()
                        codeStatelist!!.clear()
                        stateMaplist = msg["filter"] as ArrayList<Map<String, Any>>


                        for ((index, value) in stateMaplist!!.withIndex()) {
                            val stateMap = value
                            var stateSelected = stateMap.get("name") as String

                            stateList!!.add((stateSelected as String?)!!)
                            codeStatelist!!.add(stateMap.get("code") as String)
                        }
                        val langAdapter =
                            ArrayAdapter<CharSequence>(
                                activity!!,
                                R.layout.spinner_text,
                                stateList!! as List<CharSequence>
                            )
                        //   langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
                        mBinding!!.spnrState.setAdapter(langAdapter)
                    } else if (ddqCode.equals("INVENTORY_MASTER_MP")) {


                        mpList!!.clear()
                        codeMplist!!.clear()
                        mpMaplist = msg["filter"] as ArrayList<Map<String, Any>>


                        for ((index, value) in mpMaplist!!.withIndex()) {
                            val mpMap = value
                            var mpSelected = mpMap.get("name") as String
                            codeMplist!!.add(mpMap.get("code") as String)
                            mpList!!.add((mpSelected as String?)!!)
                        }
                        val langAdapter =
                            ArrayAdapter<CharSequence>(
                                activity!!,
                                R.layout.spinner_text,
                                mpList!! as List<CharSequence>
                            )
                        //   langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
                        mBinding!!.spnrMp.setAdapter(langAdapter)
                    } else if (ddqCode.equals("INVENTORY_MASTER_JC")) {


                        jcList!!.clear()
                        codeJclist!!.clear()
                        jcMaplist = msg["filter"] as ArrayList<Map<String, Any>>


                        for ((index, value) in jcMaplist!!.withIndex()) {
                            val jcMap = value
                            var jcSelected = jcMap.get("name") as String

                            codeJclist!!.add(jcMap.get("code") as String)

                            jcList!!.add((jcSelected as String?)!!)
                        }
                        val langAdapter =
                            ArrayAdapter<CharSequence>(
                                activity!!,
                                R.layout.spinner_text,
                                jcList!! as List<CharSequence>
                            )
                        //   langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
                        mBinding!!.spnrJc.setAdapter(langAdapter)
                    }


                } catch (e: Exception) {
                    MyExceptionHandler.handle(e)
                }
            }
        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
            T.show(activity, activity!!.getString(R.string.something_went_wrong), 0)
        }
    }
    // End Show Api Data on UI

    //Start Call API For Energy List Data
    fun fetchDataEnergy() {
        (activity as MainActivity).showProgressBar()
        val requestBody = HashMap<String, Any>()

        val requestBodyfilters = HashMap<String, Any>()
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "userInfo"
        requestBody["appRoleCode"] = "741"
        requestBodyfilters["startAge"] = startDate!!
        requestBodyfilters["endAge"] = endDate!!
        requestBodyfilters["category"] = categoryFilter!!
        requestBodyfilters["region"] = region!!
        requestBodyfilters["circle"] = circle!!
        requestBodyfilters["mp"] = mp!!
        requestBodyfilters["jc"] = jc!!
        requestBodyfilters["duration"] = duration!!
        requestBody["filters"] = requestBodyfilters
        CoroutineScope(Dispatchers.IO).launch {
            var job = async {
                BaseCoroutines().fetchData(
                    requestBody,
                    Busicode.BCBMainControl,
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
                    filterData(response, requestBodyfilters)
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
    //End Call API For Energy List Data

    //Start Show Energy Data on List
    fun filterData(
        mCoroutinesResponse: CoroutinesResponse,
        requestBodyfilters: HashMap<String, Any>
    ) {

        try {

            eNodeList!!.clear()
            val msg = mCoroutinesResponse.responseEntity as HashMap<String, Any>
            val energyList = msg["list"] as ArrayList<HashMap<String, Any>>

            eNodeList!!.add("eNodeB");

            val langAdapter =
                ArrayAdapter<String>(
                    activity!!,
                    R.layout.spinner_text,
                    eNodeList!!
                )
            mBinding!!.spnrEnode.setAdapter(langAdapter)

            if (msg != null) {
                try {

                    if (msg["openActionCount"] != null && msg.containsKey("openActionCount")) {
                        mBinding!!.txtPendingCount.text = msg["openActionCount"].toString()
                    }

                    var adapter = EnergyDashboardNewAdapter(
                        activity as MainActivity?,
                        energyList!!,
                        requestBodyfilters
                    )
                    mBinding!!.rvEnergyList.layoutManager = LinearLayoutManager(activity)
                    mBinding!!.rvEnergyList.itemAnimator = DefaultItemAnimator()
                    mBinding!!.rvEnergyList!!.adapter = adapter
                    adapter!!.notifyDataSetChanged()

                } catch (e: Exception) {
                    MyExceptionHandler.handle(e)
                }
            }
        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
            T.show(activity, getString(R.string.something_went_wrong), 0)
        }
    }
    //Start Show Energy Data on List


}