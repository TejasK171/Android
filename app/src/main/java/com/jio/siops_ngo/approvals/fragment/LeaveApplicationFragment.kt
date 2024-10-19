package com.jio.siops_ngo.approvals.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.jio.jioinfra.network.business.BaseCoroutines
import com.jio.jioinfra.utilities.Busicode
import com.jio.jioinfra.utilities.MyConstants
import com.jio.myjio.bean.CoroutinesResponse
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.databinding.FragmentApplyLeaveBinding
import com.jio.siops_ngo.network.MappActor
import kotlinx.coroutines.*
import android.content.res.ColorStateList
import android.widget.ArrayAdapter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import android.util.Log
import android.widget.AdapterView
import android.widget.CalendarView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.jio.jioinfra.utilities.ViewUtils
import com.jio.siops_ngo.utilities.*


/**
 * A simple [Fragment] subclass.
 */
class LeaveApplicationFragment : Fragment() {
    var mBinding: FragmentApplyLeaveBinding? = null
    var listData: ArrayList<Map<String, Any>>? = null
    var leaveTypeArray = arrayOf("First Half", "Second Half", "Full Day")
    var leaveTypeKeyArray = arrayOf("FH", "SH", "FD")
    var toDateMinYear = 0
    var toDateMinMonth = 0
    var toDateMinDate = 0
    var selectedStartDate: String? = null
    var selectedEndDate: String? = null
    //    var selectedDate: String? = null
    var selectedDateType = 0
    var leaveTypeSelected = ""
    var startDate: Date? = null
    var endDate: Date? = null
    var currentDateForma: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
    var apiRequiredDateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    var displayDateFormat: SimpleDateFormat = SimpleDateFormat("dd-MMM-yy")
    var reasonStr: String = ""

    companion object {
        fun newInstance() = LeaveApplicationFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_apply_leave, container, false)
        return mBinding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val aa = ArrayAdapter(
            activity as MainActivity,
            android.R.layout.simple_spinner_item,
            leaveTypeArray
        )
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //Setting the ArrayAdapter data on the Spinner
        mBinding!!.spnrLeaveType.setAdapter(aa)

        mBinding!!.cnstrntLSd.setOnClickListener {

            if (mBinding!!.cnstrntLCalendarSd.visibility == View.VISIBLE) {
                mBinding!!.cnstrntLCalendarSd.visibility = View.GONE
                if (selectedStartDate == null) {
                    var calendarInstance = Calendar.getInstance()
                    var calendar = Calendar.getInstance().getTime();
                    var todaysDisplayDate = displayDateFormat.format(calendar)
                    var todaysDate = currentDateForma.format(calendar)
                    mBinding!!.txtStartDate.text = todaysDisplayDate
                    selectedStartDate = todaysDate

                    toDateMinYear = calendarInstance.get(Calendar.YEAR)
                    toDateMinMonth = calendarInstance.get(Calendar.MONTH)
                    toDateMinDate = calendarInstance.get(Calendar.DAY_OF_MONTH)

                    createEndDateCalendar()
                }
            } else {
                mBinding!!.cnstrntLCalendarSd.visibility = View.VISIBLE
            }

            mBinding!!.txtStartDateTitle.setTextColor(
                (activity as MainActivity).resources.getColor(
                    R.color.black
                )
            )
            ImageViewCompat.setImageTintList(
                mBinding!!.imgCalendaSd, ColorStateList.valueOf(
                    ContextCompat.getColor(activity as MainActivity, R.color.dcb_grey_txt_color)
                )
            );
            mBinding!!.cnstrntLCalendarEd.visibility = View.GONE
            openSDCalendar()
        }
        mBinding!!.cnstrntEd.setOnClickListener {

            if (mBinding!!.cnstrntLCalendarEd.visibility == View.VISIBLE) {
                mBinding!!.cnstrntLCalendarEd.visibility = View.GONE
                if (selectedEndDate == null  && selectedStartDate!=null) {

                    var calendar = Calendar.getInstance().getTime();
                    var todaysDisplayDate = displayDateFormat.format(calendar)
                    var todaysDate = currentDateForma.format(calendar)
                    mBinding!!.txtEndDate.text = todaysDisplayDate
                    selectedEndDate = todaysDate

                    /*mBinding!!.txtEndDate.text =
                        Utils.convertDate(selectedStartDate, currentDateForma, displayDateFormat)
                    selectedEndDate = Utils.convertDate(selectedStartDate, currentDateForma, currentDateForma)*/
                }
            } else {
                if (selectedStartDate != null) {
                    mBinding!!.cnstrntLCalendarEd.visibility = View.VISIBLE
                }

            }

            if (selectedStartDate != null) {
                mBinding!!.txtEndDateTitle.setTextColor(
                    (activity as MainActivity).resources.getColor(
                        R.color.black
                    )
                )

                ImageViewCompat.setImageTintList(
                    mBinding!!.imgCalendarEd, ColorStateList.valueOf(
                        ContextCompat.getColor(activity as MainActivity, R.color.dcb_grey_txt_color)
                    )
                );
                mBinding!!.cnstrntLCalendarSd.visibility = View.GONE
//                mBinding!!.cnstrntLCalendarEd.visibility = View.VISIBLE
            } else {
                T.show(activity, "Please select start date first", 0)
            }

        }

        mBinding!!.btnApplyLeave.setOnClickListener {
            var isDataValid = true

            if (selectedStartDate == null) {
                isDataValid = false
                mBinding!!.txtStartDateTitle.setTextColor(
                    (activity as MainActivity).resources.getColor(
                        R.color.red_error_color
                    )
                )
                ImageViewCompat.setImageTintList(
                    mBinding!!.imgCalendaSd, ColorStateList.valueOf(
                        ContextCompat.getColor(activity as MainActivity, R.color.red_error_color)
                    )
                );
            }

            if (selectedEndDate == null) {
                isDataValid = false
                mBinding!!.txtEndDateTitle.setTextColor(
                    (activity as MainActivity).resources.getColor(
                        R.color.red_error_color
                    )
                )
                ImageViewCompat.setImageTintList(
                    mBinding!!.imgCalendarEd, ColorStateList.valueOf(
                        ContextCompat.getColor(activity as MainActivity, R.color.red_error_color)
                    )
                );
            }

            reasonStr = mBinding!!.edtEnterReason.text.trim().toString()
            if (ViewUtils.isEmptyString(reasonStr!!)) {
                isDataValid = false
                mBinding!!.edtEnterReason!!.setError("Please enter reason for leave");
            }
            if (isDataValid)
                fetchData()
        }


        mBinding!!.calendarViewSd?.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // Note that months are indexed from 0. So, 0 means January, 1 means february, 2 means march etc.
//            selectedStartDate = "Selected date is " + dayOfMonth + "/" + (month + 1) + "/" + year
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

            selectedStartDate = displayDay + "/" + displayMonth + "/" + year

            if (selectedDateType == 0) {
                toDateMinYear = year
                toDateMinMonth = month
                toDateMinDate = dayOfMonth
            }

            mBinding!!.txtStartDate.text =
                Utils.convertDate(selectedStartDate, currentDateForma, displayDateFormat)

            if (selectedStartDate != null && selectedEndDate != null) {
                endDate = currentDateForma.parse(selectedEndDate!!)
                startDate = currentDateForma.parse(selectedStartDate!!)
                if (startDate!!.after(endDate)) {
//                    mBinding!!.txtEndDate.text = selectedStartDate
                    mBinding!!.txtEndDate.text =
                        Utils.convertDate(selectedStartDate, currentDateForma, displayDateFormat)
                    selectedEndDate = selectedStartDate
                }
                if (!selectedStartDate.equals(selectedEndDate)) {
                    mBinding!!.spnrLeaveType.setSelection(2)
                    mBinding!!.spnrLeaveType.isEnabled = false

                } else {
                    mBinding!!.spnrLeaveType.setSelection(0)
                    mBinding!!.spnrLeaveType.isEnabled = true
                }


            }
            mBinding!!.cnstrntLCalendarSd.visibility = View.GONE
            createEndDateCalendar()
        }




        mBinding!!.spnrLeaveType.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                arg0: AdapterView<*>?,
                arg1: View,
                arg2: Int,
                arg3: Long
            ) { // TODO Auto-generated method stub
                leaveTypeSelected = leaveTypeKeyArray[arg2]
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) { // TODO Auto-generated method stub
            }
        })


    }


    fun openSDCalendar() {
        selectedDateType = 0
        if (selectedStartDate != null) {
            mBinding!!.calendarViewSd.setDate(
                SimpleDateFormat("dd/MM/yyyy").parse(selectedStartDate)!!.time,
                true,
                true
            )
        } else {
            val cal = Calendar.getInstance();
//                    cal.add(Calendar.DATE, -7);
            mBinding!!.calendarViewSd.setDate(
                Calendar.getInstance().getTimeInMillis(),
                false,
                true
            );

            mBinding!!.calendarViewSd.setDate(
                cal.getTimeInMillis(),
                false,
                true
            );
            cal.add(Calendar.DATE, -7);

            mBinding!!.calendarViewSd.setMinDate(cal.getTimeInMillis())


            val calMaxDate = Calendar.getInstance();
            calMaxDate.add(Calendar.MONTH, 1)



            mBinding!!.calendarViewSd.setMaxDate(calMaxDate.getTimeInMillis())


        }

    }

    fun createEndDateCalendar() {

        mBinding!!.cnstrntLCalendarEd.removeAllViews()
        var mCalenDarView = CalendarView(activity as MainActivity)
        mCalenDarView.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        if (toDateMinYear != 0) {
            val cal = Calendar.getInstance()
            cal.set(Calendar.YEAR, toDateMinYear);
            cal.set(Calendar.MONTH, toDateMinMonth);
            cal.set(Calendar.DAY_OF_MONTH, toDateMinDate);

            mCalenDarView.setMinDate(cal.getTimeInMillis())

            val myFormat = "dd/MM/yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
            Log.e("minDate", sdf.format(cal.getTime()))
        }


        if (selectedEndDate != null) {
            mCalenDarView.setDate(
                SimpleDateFormat("dd/MM/yyyy").parse(selectedEndDate)!!.time,
                true,
                true
            )
        }

        mBinding!!.cnstrntLCalendarEd.addView(mCalenDarView)

        val calMaxDate = Calendar.getInstance();
        calMaxDate.add(Calendar.MONTH, 1)

        mCalenDarView.setMaxDate(calMaxDate.getTimeInMillis())


        mCalenDarView?.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // Note that months are indexed from 0. So, 0 means January, 1 means february, 2 means march etc.
//            selectedStartDate = "Selected date is " + dayOfMonth + "/" + (month + 1) + "/" + year
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

            selectedEndDate = displayDay + "/" + displayMonth + "/" + year

//            mBinding!!.txtEndDate.text = selectedEndDate
            mBinding!!.txtEndDate.text =
                Utils.convertDate(selectedEndDate, currentDateForma, displayDateFormat)

            mBinding!!.cnstrntLCalendarEd.visibility = View.GONE

            var sdf = SimpleDateFormat("dd/MM/yyyy");
            endDate = sdf.parse(selectedEndDate!!);

            if (!selectedStartDate.equals(selectedEndDate)) {
                mBinding!!.spnrLeaveType.setSelection(2)
                mBinding!!.spnrLeaveType.isEnabled = false

            } else {
                mBinding!!.spnrLeaveType.setSelection(0)
                mBinding!!.spnrLeaveType.isEnabled = true
            }

        }


    }


    fun fetchData() {
        (activity as MainActivity).showProgressBar()

        val requestBody = HashMap<String, Any>()
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "userInfo"
        requestBody["relatedTo"] = "LEAVE"
        requestBody["action"] = leaveTypeSelected
        requestBody["startDate"] =
            Utils.convertDate(selectedStartDate!!, currentDateForma, apiRequiredDateFormat)!!
        requestBody["endDate"] =
            Utils.convertDate(selectedEndDate!!, currentDateForma, apiRequiredDateFormat)!!
        requestBody["leaveReason"] = reasonStr
        //   requestBody["appRoleCode"] = "741"
        CoroutineScope(Dispatchers.IO).launch {

            var job = async {
                BaseCoroutines().fetchData(
                    requestBody,
                    Busicode.ApprovalLeaveApplication,
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
            val msg = mCoroutinesResponse.responseEntity as HashMap<String, Any>
            if (msg != null) {
                try {
                    if (msg.containsKey("messageDetails") && msg["messageDetails"] != null) {
                        showDialog(msg["messageDetails"].toString())
                    }

                } catch (e: Exception) {
                    MyExceptionHandler.handle(e)
                }
            }
        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
            T.show(activity, getString(R.string.something_went_wrong), 0)
        }
    }

    private fun showDialog(msg: String) {

        var mContext = activity as MainActivity

        DialogUtils.showYesDialogAutoDismiss(mContext,
            msg,
            this.resources.getString(R.string.button_ok),
            this.resources.getString(R.string.cancel),
            object : DialogUtils.AutoDismissOnClickListener {
                override fun onYesClick() {
                    (activity as MainActivity).onBackPressed()
                }

                override fun onNoClick() {
                }
            })


    }
}
