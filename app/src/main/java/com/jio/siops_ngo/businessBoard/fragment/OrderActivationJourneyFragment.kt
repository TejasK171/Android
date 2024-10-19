package com.jio.siops_ngo.businessBoard.fragment


import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.jio.siops_ngo.calendar.widget.CollapsibleCalendar
import com.jio.siops_ngo.databinding.FragmentOrderActivationCircleBinding
import com.jio.siops_ngo.infra.adapter.ActivationJourneryAdapter
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.T
import kotlinx.coroutines.*
import java.lang.Exception
import java.sql.DriverManager.println
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] Ranjan subclass.
 */
class OrderActivationJourneyFragment : Fragment() {


    var mBinding: FragmentOrderActivationCircleBinding? = null
    var apiRequestFormattedDate: String? = null
    lateinit var collapsibleCalendar: CollapsibleCalendar


    companion object {
        fun newInstance() =
            OrderActivationJourneyFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_order_activation_circle,
            container,
            false
        )
        // Inflate the layout for this fragment
        val c = Calendar.getInstance().time
        println("Current time => $c")

        val df = SimpleDateFormat("dd/MM/yyyy")
        val formattedDate = df.format(c)
        mBinding!!.txtDate.text = formattedDate


        val apiRequestDate = SimpleDateFormat("yyyy-MM-dd")
        apiRequestFormattedDate = apiRequestDate.format(c)

        fetchDashboardData()

        return mBinding!!.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mBinding!!.txtGt.visibility = View.GONE
        mBinding!!.txtRr.visibility = View.GONE


        //To hide or show expand icon
        mBinding!!.collapsibleCalendarView.setExpandIconVisible(true)
        val today = GregorianCalendar()
      //  mBinding!!.collapsibleCalendarView.addEventTag(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH))
        today.add(Calendar.DATE, 1)
        mBinding!!.collapsibleCalendarView.params = CollapsibleCalendar.Params(-30, 0)
        mBinding!!. collapsibleCalendarView.setCalendarListener(object : CollapsibleCalendar.CalendarListener {
            override fun onDayChanged() {
            }
            override fun onClickListener() {
                if( mBinding!!.collapsibleCalendarView.expanded){
                    mBinding!!. collapsibleCalendarView.collapse(400)
                }
                else{
                    mBinding!!.collapsibleCalendarView.expand(400)
                }
            }
            override fun onDaySelect() {
                val date = mBinding!!.collapsibleCalendarView!!.selectedDay?.day.toString()
                val month = mBinding!!.collapsibleCalendarView.selectedDay!!.month+1
                val year = mBinding!!.collapsibleCalendarView.selectedDay?.year.toString()
                mBinding!!.collapsibleCalendarView!!.todayItemBackgroundDrawable = null
                mBinding!!.collapsibleCalendarView!!.todayItemTextColor = activity!!.resources.getColor(R.color.jioinfra_gray)
                apiRequestFormattedDate = year+"-"+month+"-"+date
                val dateFormatReqdFormat = SimpleDateFormat("yyyy-MM-dd")
                val dateFormat = SimpleDateFormat("yyyy-M-dd")
                val dateFormatted = dateFormat.parse(apiRequestFormattedDate)
                apiRequestFormattedDate = dateFormatReqdFormat.format(dateFormatted)
                fetchDashboardData()
            }

            override fun onItemClick(v: View) {

            }

            override fun onDataUpdate() {

            }

            override fun onMonthChange() {
            }

            override fun onWeekChange(position: Int) {

            }
        })
        mBinding!!.txtDate.setOnClickListener {

            val c = Calendar.getInstance()
            var _pickedDate: String? = null
            val dialog = DatePickerDialog(
                context!!,
                OnDateSetListener { view, year, month, dayOfMonth ->
                    val _year = year.toString()
                    val _month =
                        if (month + 1 < 10) "0" + (month + 1) else (month + 1).toString()
                    val _date =
                        if (dayOfMonth < 10) "0$dayOfMonth" else dayOfMonth.toString()
                    _pickedDate = "$_date/$_month/$year"
                    mBinding!!.txtDate.text = _pickedDate
                    Log.e("PickedDate: ", "Date: $_pickedDate") //2019-02-12
                }, c[Calendar.YEAR], c[Calendar.MONTH], c[Calendar.MONTH]
            )
            dialog.datePicker.minDate = System.currentTimeMillis() - 1000

            dialog.show()


        }
    }

    fun fetchDashboardData() {
        (activity as MainActivity).showProgressBar()
        val requestBody = HashMap<String, Any>()
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "userInfo"
        requestBody["date"] = apiRequestFormattedDate!!
        CoroutineScope(Dispatchers.IO).launch {

            var job = async {
                BaseCoroutines().fetchData(
                    requestBody,
                    Busicode.O2ASummary,
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
                    filterData(response)
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
            val activationRespMap = mCoroutinesResponse.responseEntity as HashMap<String, Any>

            var adapter = ActivationJourneryAdapter(activity as MainActivity?, activationRespMap,apiRequestFormattedDate!!)
            mBinding!!.journeyList.layoutManager = LinearLayoutManager(activity)
            mBinding!!.journeyList.itemAnimator = DefaultItemAnimator()
            mBinding!!.journeyList!!.adapter = adapter
            adapter!!.notifyDataSetChanged()


            mBinding!!.txtRegion.setOnClickListener {

                var orderActivationJourneyFragment = OrderActivationRegionFragment.newInstance()
                val commonBean = CommonBean()
                if (activationRespMap.containsKey("allChannels") && activationRespMap["allChannels"] != null) {
                    commonBean.`object` = activationRespMap["allChannels"] as HashMap<String, Any>
                    orderActivationJourneyFragment.setData(commonBean, apiRequestFormattedDate!!)
                }
                (activity as MainActivity).openFragments(orderActivationJourneyFragment, commonBean, true, true)

            }



        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


}
