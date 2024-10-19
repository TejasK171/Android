package com.jio.siops_ngo.ngo.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.bean.CommonBean
import com.jio.jioinfra.utilities.MyConstants
import com.jio.siops_ngo.MainActivity

import com.jio.siops_ngo.R
import com.jio.siops_ngo.databinding.FragmentNgoOpenIncidentBinding
import com.jio.siops_ngo.databinding.FragmentNgoServiceGlanceDetailsBinding
import com.jio.siops_ngo.ngo.adapter.NgoOpenIncidentItemAdapter
import com.jio.siops_ngo.utilities.Utils
import java.sql.DriverManager
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * A simple [Fragment] subclass.
 */
class NgoOpenIncidentFragment : Fragment() {

    var commonBean: CommonBean? = null
    var msg: HashMap<String, Any>? = null
    var openAlertsList: ArrayList<Map<String, Any>>? = null
    var acknowledementList: ArrayList<Map<String, Any>>? = null
    var acknowledementSLAList: ArrayList<Map<String, Any>>? = null
    var applicationList: ArrayList<Map<String, Any>>? = null
    var toolList: ArrayList<Map<String, Any>>? = null
    var infrastructureList: ArrayList<Map<String, Any>>? = null
    var missedList: ArrayList<Map<String, Any>>? = null
    var metList: ArrayList<Map<String, Any>>? = null
    var acknowledgedList: ArrayList<Map<String, Any>>? = null
    var unacknowledgedList: ArrayList<Map<String, Any>>? = null
    var mBinding: FragmentNgoOpenIncidentBinding? = null
    var selection: Int? = 0
    var apiRequestFormattedDate: String? = ""
    var severityList: ArrayList<HashMap<String, Any>>? = null


    companion object {
        fun newInstance() =
            NgoOpenIncidentFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_ngo_open_incident, container, false)
        return mBinding!!.root
        // return inflater.inflate(R.layout.fragment_ngo_service_glance_details, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        mBinding!!.imgCalendar.setOnClickListener {

            if(mBinding!!.dateLl.visibility==View.VISIBLE) {
                mBinding!!.dateLl.visibility = View.GONE
            }else{

                mBinding!!.dateLl.visibility = View.VISIBLE
            }
        }

        var dayes= Utils.getPreviousWeek(7)
        var dayesYear= Utils.getPreviousWeekYear(7)
        var day_1=dayes!!.get(0)
        var day_2=dayes!!.get(1)
        var day_3=dayes!!.get(2)
        var day_4=dayes!!.get(3)
        var day_5=dayes!!.get(4)
        var day_6=dayes!!.get(5)
        var day_7=dayes!!.get(6)
        mBinding!!.txtDay1.text= day_1!!.replace("-","\n")
        mBinding!!.txtDay2.text=day_2!!.replace("-","\n")
        mBinding!!.txtDay3.text=day_3!!.replace("-","\n")
        mBinding!!.txtDay4.text=day_4!!.replace("-","\n")
        mBinding!!.txtDay5.text=day_5!!.replace("-","\n")
        mBinding!!.txtDay6.text=day_6!!.replace("-","\n")
        mBinding!!.txtDay7.text=day_7!!.replace("-","\n")
        val c = Calendar.getInstance().time
        DriverManager.println("Current time => $c")

        val df = SimpleDateFormat("dd/MM/yyyy")
        val formattedDate = df.format(c)
        // mBinding!!.txtDate.text = formattedDate


        val apiRequestDate = SimpleDateFormat("yyyy-MM-dd")
        var apiRequestFormattedDatCuu = apiRequestDate.format(c)


        //  apiRequestFormattedDate = dayesYear!!.get(6).toString()
        mBinding!!.txtCurrentDate.text= formattedDate
        // mBinding!!.yesterdayid.visibility=View.GONE

        // changeViewBg(mBinding!!.txtDay7, mBinding!!.txtDay1,mBinding!!.txtDay2,mBinding!!.txtDay3,mBinding!!.txtDay4,mBinding!!.txtDay5,mBinding!!.txtDay6)


        mBinding!!.txtDay1.setOnClickListener {


            // val apiRequestDate = SimpleDateFormat("yyyy-MM-dd")
            apiRequestFormattedDate = dayesYear!!.get(0).toString()
            openNgoIncidentHistoryFragment()

        }

        mBinding!!.txtDay2.setOnClickListener {


            apiRequestFormattedDate = dayesYear!!.get(1).toString()
            openNgoIncidentHistoryFragment()
        }



        mBinding!!.txtDay3.setOnClickListener {


            apiRequestFormattedDate = dayesYear!!.get(2).toString()
            openNgoIncidentHistoryFragment()

        }


        mBinding!!.txtDay4.setOnClickListener {


            apiRequestFormattedDate = dayesYear!!.get(3).toString()
            openNgoIncidentHistoryFragment()

        }


        mBinding!!.txtDay5.setOnClickListener {

            apiRequestFormattedDate = dayesYear!!.get(4).toString()

            openNgoIncidentHistoryFragment()
        }


        mBinding!!.txtDay6.setOnClickListener {
            apiRequestFormattedDate = dayesYear!!.get(5).toString()
            openNgoIncidentHistoryFragment()
        }
        mBinding!!.txtDay7.setOnClickListener {
            apiRequestFormattedDate = dayesYear!!.get(6).toString()
            openNgoIncidentHistoryFragment()
        }





        if (selection == 0) {
            mBinding!!.txtHeader.text =
                (activity as MainActivity).resources.getString(R.string.open_business_impacting_incidents)
            mBinding!!.card3Oi.visibility = View.GONE
            mBinding!!.card4Oi.visibility = View.GONE

            if (msg != null) {
                if (msg!!.containsKey("busiOI") && msg!!["busiOI"] != null) {
                    if (msg!!.containsKey("businessImpactingCount") && msg!!["businessImpactingCount"] != null) {
                        mBinding!!.txtHeader.text =
                            (activity as MainActivity).resources.getString(R.string.open_business_impacting_incidents) + " (" + msg!!["businessImpactingCount"].toString() + ")"
                    }
                    var busiOI = msg!!["busiOI"] as ArrayList<Map<String, Any>>
                    if (Utils.hasIndex(0, busiOI!!)) {
                        if (busiOI[0]!!.containsKey("outlierCount") && busiOI[0]!!["outlierCount"] != null) {
                            mBinding!!.txtSeverityCount1.text =
                                busiOI[0]!!["outlierCount"].toString()
                        }

                        if (busiOI[0]!!.containsKey("featureName") && busiOI[0]!!["featureName"] != null) {
                            mBinding!!.txtSeverity1.text = busiOI[0]!!["featureName"].toString()
                        }

                    }
                    if (Utils.hasIndex(1, busiOI!!)) {
                        if (busiOI[1]!!.containsKey("outlierCount") && busiOI[1]!!["outlierCount"] != null) {
                            mBinding!!.txtSeverityCount2.text =
                                busiOI[1]!!["outlierCount"].toString()
                        }

                        if (busiOI[1]!!.containsKey("featureName") && busiOI[1]!!["featureName"] != null) {
                            mBinding!!.txtSeverity2.text = busiOI[1]!!["featureName"].toString()
                        }
                    }

                }
            }
        } else {
            mBinding!!.txtHeader.text =
                (activity as MainActivity).resources.getString(R.string.open_non_business_impacting_incidents)

            if (msg != null) {
                if (msg!!.containsKey("nonBusiOI") && msg!!["nonBusiOI"] != null) {
                    if (msg!!.containsKey("nonBusinessImpactingCount") && msg!!["nonBusinessImpactingCount"] != null) {
                        mBinding!!.txtHeader.text =
                            (activity as MainActivity).resources.getString(R.string.open_non_business_impacting_incidents) + " (" + msg!!["nonBusinessImpactingCount"].toString() + ")"
                    }
                    var nonBusiOI = msg!!["nonBusiOI"] as ArrayList<Map<String, Any>>

                    if (Utils.hasIndex(0, nonBusiOI!!)) {
                        if (nonBusiOI[0]!!.containsKey("outlierCount") && nonBusiOI[0]!!["outlierCount"] != null) {
                            mBinding!!.txtSeverityCount1.text =
                                nonBusiOI[0]!!["outlierCount"].toString()
                        }

                        if (nonBusiOI[0]!!.containsKey("featureName") && nonBusiOI[0]!!["featureName"] != null) {
                            mBinding!!.txtSeverity1.text = nonBusiOI[0]!!["featureName"].toString()
                        }

                    }
                    if (Utils.hasIndex(1, nonBusiOI!!)) {
                        if (nonBusiOI[1]!!.containsKey("outlierCount") && nonBusiOI[1]!!["outlierCount"] != null) {
                            mBinding!!.txtSeverityCount2.text =
                                nonBusiOI[1]!!["outlierCount"].toString()
                        }

                        if (nonBusiOI[1]!!.containsKey("featureName") && nonBusiOI[1]!!["featureName"] != null) {
                            mBinding!!.txtSeverity2.text = nonBusiOI[1]!!["featureName"].toString()
                        }

                    }
                    if (Utils.hasIndex(2, nonBusiOI!!)) {
                        if (nonBusiOI[2]!!.containsKey("outlierCount") && nonBusiOI[2]!!["outlierCount"] != null) {
                            mBinding!!.txtSeverityCount3.text =
                                nonBusiOI[2]!!!!["outlierCount"].toString()
                        }

                        if (nonBusiOI[2]!!.containsKey("featureName") && nonBusiOI[2]!!["featureName"] != null) {
                            mBinding!!.txtSeverity3.text = nonBusiOI[2]!!["featureName"].toString()
                        }

                    }
                    if (Utils.hasIndex(3, nonBusiOI!!)) {
                        if (nonBusiOI[3]!!.containsKey("outlierCount") && nonBusiOI[3]!!["outlierCount"] != null) {
                            mBinding!!.txtSeverityCount4.text =
                                nonBusiOI[3]!!["outlierCount"].toString()
                        }

                        if (nonBusiOI[3]!!.containsKey("featureName") && nonBusiOI[3]!!["featureName"] != null) {
                            mBinding!!.txtSeverity4.text = nonBusiOI[3]!!["featureName"].toString()
                        }

                    }


                }
            }

        }


        mBinding!!.card1Oi.setOnClickListener {
            mBinding!!.cnstrntLOiSeverityData.visibility = View.VISIBLE
            mBinding!!.txtSeverityHeader.text =
                activity!!.resources.getString(R.string.open_severity_incident)
                    .replace("%", "1")
            mBinding!!.txtSeverityCount.text = mBinding!!.txtSeverityCount1.text
            if (selection == 0) {
                if (mBinding!!.txtSeverityCount1.text != "0") {
//
                    mBinding!!.recyclerViewOi.visibility = View.VISIBLE
                    if (msg!!.containsKey("busiS1") && msg!!["busiS1"] != null) {

                        severityList = msg!!["busiS1"] as ArrayList<HashMap<String, Any>>

                        mBinding!!.recyclerViewOi.apply {
                            layoutManager =
                                LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                            adapter = NgoOpenIncidentItemAdapter(severityList!!)
                        }

                    }
                } else {
//                    mBinding!!.cnstrntLOiSeverityData.visibility = View.GONE
                    mBinding!!.recyclerViewOi.visibility = View.GONE
                }

            } else {
                mBinding!!.txtSeverityHeader.text =
                    activity!!.resources.getString(R.string.open_severity_incident)
                        .replace("%", "1")
                mBinding!!.txtSeverityCount.text = mBinding!!.txtSeverityCount1.text
                if (mBinding!!.txtSeverityCount1.text != "0") {
                    mBinding!!.cnstrntLOiSeverityData.visibility = View.VISIBLE
                    mBinding!!.recyclerViewOi.visibility = View.VISIBLE

                    if (msg!!.containsKey("nonBusiS1") && msg!!["nonBusiS1"] != null) {
                        severityList = msg!!["nonBusiS1"] as ArrayList<HashMap<String, Any>>
                        mBinding!!.txtSeverityHeader.text =
                            activity!!.resources.getString(R.string.open_severity_incident)
                                .replace("%", "2")
                        mBinding!!.txtSeverityCount.text = mBinding!!.txtSeverityCount2.text
                        mBinding!!.recyclerViewOi.apply {
                            layoutManager =
                                LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                            adapter = NgoOpenIncidentItemAdapter(severityList!!)
                        }

                    }
                } else {
//                    mBinding!!.cnstrntLOiSeverityData.visibility = View.GONE
                    mBinding!!.recyclerViewOi.visibility = View.GONE
                }

            }
        }

        mBinding!!.card2Oi.setOnClickListener {
            mBinding!!.txtSeverityHeader.text =
                activity!!.resources.getString(R.string.open_severity_incident)
                    .replace("%", "2")
            mBinding!!.txtSeverityCount.text = mBinding!!.txtSeverityCount2.text
            if (selection == 0) {
                if (mBinding!!.txtSeverityCount2.text != "0") {
                    mBinding!!.cnstrntLOiSeverityData.visibility = View.VISIBLE
                    mBinding!!.recyclerViewOi.visibility = View.VISIBLE

                    if (msg!!.containsKey("busiS2") && msg!!["busiS2"] != null) {
                        severityList = msg!!["busiS2"] as ArrayList<HashMap<String, Any>>

                        mBinding!!.recyclerViewOi.apply {
                            layoutManager =
                                LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                            adapter = NgoOpenIncidentItemAdapter(severityList!!)
                        }

                    }
                } else {
//                    mBinding!!.cnstrntLOiSeverityData.visibility = View.GONE
                    mBinding!!.recyclerViewOi.visibility = View.GONE

                }

            } else {
                mBinding!!.txtSeverityHeader.text =
                    activity!!.resources.getString(R.string.open_severity_incident)
                        .replace("%", "2")
                mBinding!!.txtSeverityCount.text = mBinding!!.txtSeverityCount2.text
                if (mBinding!!.txtSeverityCount2.text != "0") {
                    mBinding!!.cnstrntLOiSeverityData.visibility = View.VISIBLE
                    mBinding!!.recyclerViewOi.visibility = View.VISIBLE

                    if (msg!!.containsKey("nonBusiS2") && msg!!["nonBusiS2"] != null) {
                        severityList = msg!!["nonBusiS2"] as ArrayList<HashMap<String, Any>>

                        mBinding!!.recyclerViewOi.apply {
                            layoutManager =
                                LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                            adapter = NgoOpenIncidentItemAdapter(severityList!!)
                        }

                    }
                } else {
//                    mBinding!!.cnstrntLOiSeverityData.visibility = View.GONE
                    mBinding!!.recyclerViewOi.visibility = View.GONE
                    /*if(severityList!=null) {
                        severityList!!.clear()

                        mBinding!!.recyclerViewOi.apply {
                            layoutManager =
                                LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                            adapter = NgoOpenIncidentItemAdapter(severityList!!)
                        }
                    }*/

                }

            }
        }

        mBinding!!.card3Oi.setOnClickListener {
            mBinding!!.txtSeverityHeader.text =
                activity!!.resources.getString(R.string.open_severity_incident)
                    .replace("%", "3")
            mBinding!!.txtSeverityCount.text = mBinding!!.txtSeverityCount3.text
            if (mBinding!!.txtSeverityCount3.text != "0") {
                mBinding!!.cnstrntLOiSeverityData.visibility = View.VISIBLE
                mBinding!!.recyclerViewOi.visibility = View.VISIBLE
                if (msg!!.containsKey("nonBusiS3") && msg!!["nonBusiS3"] != null) {
                    severityList = msg!!["nonBusiS3"] as ArrayList<HashMap<String, Any>>

                    mBinding!!.recyclerViewOi.apply {
                        layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                        adapter = NgoOpenIncidentItemAdapter(severityList!!)
                    }

                }
            } else {
//                mBinding!!.cnstrntLOiSeverityData.visibility = View.GONE
                mBinding!!.recyclerViewOi.visibility = View.GONE

            }

        }

        mBinding!!.card4Oi.setOnClickListener {
            mBinding!!.txtSeverityHeader.text =
                activity!!.resources.getString(R.string.open_severity_incident)
                    .replace("%", "4")
            mBinding!!.txtSeverityCount.text = mBinding!!.txtSeverityCount4.text
            if (mBinding!!.txtSeverityCount4.text != "0") {
                mBinding!!.cnstrntLOiSeverityData.visibility = View.VISIBLE
                mBinding!!.recyclerViewOi.visibility = View.VISIBLE
                if (msg!!.containsKey("nonBusiS4") && msg!!["nonBusiS4"] != null) {
                    severityList = msg!!["nonBusiS4"] as ArrayList<HashMap<String, Any>>

                    mBinding!!.recyclerViewOi.apply {
                        layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                        adapter = NgoOpenIncidentItemAdapter(severityList!!)
                    }

                }
            } else {
//                mBinding!!.cnstrntLOiSeverityData.visibility = View.GONE
                mBinding!!.recyclerViewOi.visibility = View.GONE

            }
        }
        mBinding!!.card1Oi.performClick()

    }


    fun setData(commonBean: CommonBean, msg: HashMap<String, Any>?, selection: Int) {
        this.commonBean = commonBean
        this.msg = msg
        this.selection = selection
    }

    fun openNgoIncidentHistoryFragment(){

        val commonBeanData = CommonBean()
        commonBeanData.setmTitle(MyConstants.NGO)
        var ngoOpenIncidentHistoryFragment =
            NgoOpenIncidentHistoryFragment.newInstance()
        ngoOpenIncidentHistoryFragment.setData(selection!!, apiRequestFormattedDate!!)
        (activity as MainActivity).openFragments(
            ngoOpenIncidentHistoryFragment,
            commonBeanData,
            true,
            true
        )


    }
}
