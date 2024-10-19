package com.jio.siops_ngo.ngo.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.jio.jioinfra.bean.CommonBean
import com.jio.jioinfra.network.business.BaseCoroutines
import com.jio.jioinfra.utilities.Busicode
import com.jio.jioinfra.utilities.MyConstants
import com.jio.myjio.bean.CoroutinesResponse
import com.jio.siops_ngo.MainActivity

import com.jio.siops_ngo.R
import com.jio.siops_ngo.databinding.FragmentNgoOiHistoryBinding
import com.jio.siops_ngo.databinding.FragmentNgoOpenIncidentBinding
import com.jio.siops_ngo.databinding.FragmentNgoServiceGlanceDetailsBinding
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.T
import com.jio.siops_ngo.utilities.Utils
import kotlinx.coroutines.*

/**
 * A simple [Fragment] subclass.
 */
class NgoOpenIncidentHistoryFragment : Fragment() {

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
    var mBinding: FragmentNgoOiHistoryBinding? = null
    var selection: Int? = 0
    var date: String? = ""

    var severityList: ArrayList<HashMap<String, Any>>? = null


    companion object {
        fun newInstance() =
            NgoOpenIncidentHistoryFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_ngo_oi_history, container, false)
        return mBinding!!.root
        // return inflater.inflate(R.layout.fragment_ngo_service_glance_details, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mBinding!!.txtDate.text= Utils.dateConvert(date)

        mBinding!!.card1Oi.visibility = View.INVISIBLE
        mBinding!!.card2Oi.visibility = View.INVISIBLE
        mBinding!!.card3Oi.visibility = View.INVISIBLE
        mBinding!!.card4Oi.visibility = View.INVISIBLE
        mBinding!!.card1OiAvg.visibility = View.INVISIBLE
        mBinding!!.card2OiAvg.visibility = View.INVISIBLE
        mBinding!!.card3OiAvg.visibility = View.INVISIBLE
        mBinding!!.card4OiAvg.visibility = View.INVISIBLE
        fetchData()

        /*if (selection == 0) {
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
            if (selection == 0) {

                if (mBinding!!.txtSeverityCount1.text != "0") {
                    mBinding!!.cnstrntLOiSeverityData.visibility = View.VISIBLE
                    mBinding!!.recyclerViewOi.visibility = View.VISIBLE
                    if (msg!!.containsKey("busiS1") && msg!!["busiS1"] != null) {
                        mBinding!!.txtSeverityHeader.text =
                            activity!!.resources.getString(R.string.open_severity_incident)
                                .replace("%", "1")
                        mBinding!!.txtSeverityCount.text = mBinding!!.txtSeverityCount1.text
                        severityList = msg!!["busiS1"] as ArrayList<HashMap<String, Any>>

                        mBinding!!.recyclerViewOi.apply {
                            layoutManager =
                                LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                            adapter = NgoOpenIncidentItemAdapter(severityList!!)
                        }

                    }
                } else {
                    mBinding!!.cnstrntLOiSeverityData.visibility = View.GONE
                    mBinding!!.recyclerViewOi.visibility = View.GONE
                }

            } else {
                if (mBinding!!.txtSeverityCount1.text != "0") {
                    mBinding!!.cnstrntLOiSeverityData.visibility = View.VISIBLE
                    mBinding!!.recyclerViewOi.visibility = View.VISIBLE
                    mBinding!!.txtSeverityHeader.text =
                        activity!!.resources.getString(R.string.open_severity_incident)
                            .replace("%", "1")
                    mBinding!!.txtSeverityCount.text = mBinding!!.txtSeverityCount1.text
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
                    mBinding!!.cnstrntLOiSeverityData.visibility = View.GONE
                    mBinding!!.recyclerViewOi.visibility = View.GONE
                }

            }
        }

        mBinding!!.card2Oi.setOnClickListener {
            if (selection == 0) {
                if (mBinding!!.txtSeverityCount2.text != "0") {
                    mBinding!!.cnstrntLOiSeverityData.visibility = View.VISIBLE
                    mBinding!!.recyclerViewOi.visibility = View.VISIBLE
                    mBinding!!.txtSeverityHeader.text =
                        activity!!.resources.getString(R.string.open_severity_incident)
                            .replace("%", "2")
                    mBinding!!.txtSeverityCount.text = mBinding!!.txtSeverityCount2.text
                    if (msg!!.containsKey("busiS2") && msg!!["busiS2"] != null) {
                        severityList = msg!!["busiS2"] as ArrayList<HashMap<String, Any>>

                        mBinding!!.recyclerViewOi.apply {
                            layoutManager =
                                LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                            adapter = NgoOpenIncidentItemAdapter(severityList!!)
                        }

                    }
                } else {
                    mBinding!!.cnstrntLOiSeverityData.visibility = View.GONE
                    mBinding!!.recyclerViewOi.visibility = View.GONE

                }

            } else {
                if (mBinding!!.txtSeverityCount2.text != "0") {
                    mBinding!!.cnstrntLOiSeverityData.visibility = View.VISIBLE
                    mBinding!!.recyclerViewOi.visibility = View.VISIBLE
                    mBinding!!.txtSeverityHeader.text =
                        activity!!.resources.getString(R.string.open_severity_incident)
                            .replace("%", "2")
                    mBinding!!.txtSeverityCount.text = mBinding!!.txtSeverityCount2.text
                    if (msg!!.containsKey("nonBusiS2") && msg!!["nonBusiS2"] != null) {
                        severityList = msg!!["nonBusiS2"] as ArrayList<HashMap<String, Any>>

                        mBinding!!.recyclerViewOi.apply {
                            layoutManager =
                                LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                            adapter = NgoOpenIncidentItemAdapter(severityList!!)
                        }

                    }
                } else {
                    mBinding!!.cnstrntLOiSeverityData.visibility = View.GONE
                    mBinding!!.recyclerViewOi.visibility = View.GONE
                    *//*if(severityList!=null) {
                        severityList!!.clear()

                        mBinding!!.recyclerViewOi.apply {
                            layoutManager =
                                LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                            adapter = NgoOpenIncidentItemAdapter(severityList!!)
                        }
                    }*//*

                }

            }
        }

        mBinding!!.card3Oi.setOnClickListener {
            if (mBinding!!.txtSeverityCount3.text != "0") {
                mBinding!!.cnstrntLOiSeverityData.visibility = View.VISIBLE
                mBinding!!.recyclerViewOi.visibility = View.VISIBLE
                mBinding!!.txtSeverityHeader.text =
                    activity!!.resources.getString(R.string.open_severity_incident)
                        .replace("%", "3")
                mBinding!!.txtSeverityCount.text = mBinding!!.txtSeverityCount3.text
                if (msg!!.containsKey("nonBusiS3") && msg!!["nonBusiS3"] != null) {
                    severityList = msg!!["nonBusiS3"] as ArrayList<HashMap<String, Any>>

                    mBinding!!.recyclerViewOi.apply {
                        layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                        adapter = NgoOpenIncidentItemAdapter(severityList!!)
                    }

                }
            } else {
                mBinding!!.cnstrntLOiSeverityData.visibility = View.GONE
                mBinding!!.recyclerViewOi.visibility = View.GONE

            }

        }

        mBinding!!.card4Oi.setOnClickListener {
            if (mBinding!!.txtSeverityCount4.text != "0") {
                mBinding!!.cnstrntLOiSeverityData.visibility = View.VISIBLE
                mBinding!!.recyclerViewOi.visibility = View.VISIBLE
                mBinding!!.txtSeverityHeader.text =
                    activity!!.resources.getString(R.string.open_severity_incident)
                        .replace("%", "4")
                mBinding!!.txtSeverityCount.text = mBinding!!.txtSeverityCount4.text
                if (msg!!.containsKey("nonBusiS4") && msg!!["nonBusiS4"] != null) {
                    severityList = msg!!["nonBusiS4"] as ArrayList<HashMap<String, Any>>

                    mBinding!!.recyclerViewOi.apply {
                        layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                        adapter = NgoOpenIncidentItemAdapter(severityList!!)
                    }

                }
            } else {
                mBinding!!.cnstrntLOiSeverityData.visibility = View.GONE
                mBinding!!.cnstrntLOiSeverityData.visibility = View.GONE
                mBinding!!.recyclerViewOi.visibility = View.GONE

            }
        }*/


    }


    fun fetchData() {
        (activity as MainActivity).showProgressBar()

        val requestBody = HashMap<String, Any>()
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "userInfo"
        requestBody["appRoleCode"] = "726"
        requestBody["date"] = date!!
        CoroutineScope(Dispatchers.IO).launch {

            var job = async {
                BaseCoroutines().fetchData(
                    requestBody,
                    Busicode.NGOOpenIncidentHistory,
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
            msg = mCoroutinesResponse.responseEntity as HashMap<String, Any>


            if (msg != null) {
                try {
                    inflateUi()
                } catch (e: Exception) {

                }
            }
        } catch (e: Exception) {

        }
    }


    fun inflateUi() {


        if (selection == 0) {
            mBinding!!.txtHeader.text =
                (activity as MainActivity).resources.getString(R.string.open_business_impacting_incidents)
            mBinding!!.card1Oi.visibility = View.VISIBLE
            mBinding!!.card2Oi.visibility = View.VISIBLE
            mBinding!!.card3Oi.visibility = View.GONE
            mBinding!!.card4Oi.visibility = View.GONE
            mBinding!!.card1OiAvg.visibility = View.VISIBLE
            mBinding!!.card2OiAvg.visibility = View.VISIBLE
            mBinding!!.card3OiAvg.visibility = View.GONE
            mBinding!!.card4OiAvg.visibility = View.GONE

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


                    var busiART = msg!!["busiART"] as ArrayList<Map<String, Any>>
                    if (Utils.hasIndex(0, busiART!!)) {
                        if (busiART[0]!!.containsKey("value") && busiART[0]!!["value"] != null) {
                            mBinding!!.txtSeverityCount1Avg.text =
                                busiART[0]!!["value"].toString()
                        }

                        if (busiART[0]!!.containsKey("featureName") && busiART[0]!!["featureName"] != null) {
                            mBinding!!.txtSeverity1Avg.text = busiART[0]!!["featureName"].toString()
                        }

                    }
                    if (Utils.hasIndex(1, busiOI!!)) {
                        if (busiART[1]!!.containsKey("value") && busiART[1]!!["value"] != null) {
                            mBinding!!.txtSeverityCount2Avg.text =
                                busiOI[1]!!["value"].toString()
                        }

                        if (busiART[1]!!.containsKey("featureName") && busiART[1]!!["featureName"] != null) {
                            mBinding!!.txtSeverity2Avg.text = busiART[1]!!["featureName"].toString()
                        }
                    }

                }
            }
        } else {

            mBinding!!.card1Oi.visibility = View.VISIBLE
            mBinding!!.card2Oi.visibility = View.VISIBLE
            mBinding!!.card3Oi.visibility = View.VISIBLE
            mBinding!!.card4Oi.visibility = View.VISIBLE
            mBinding!!.card1OiAvg.visibility = View.VISIBLE
            mBinding!!.card2OiAvg.visibility = View.VISIBLE
            mBinding!!.card3OiAvg.visibility = View.VISIBLE
            mBinding!!.card4OiAvg.visibility = View.VISIBLE

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


                    var nonBusiART = msg!!["nonBusiART"] as ArrayList<Map<String, Any>>

                    if (Utils.hasIndex(0, nonBusiART!!)) {
                        if (nonBusiART[0]!!.containsKey("value") && nonBusiART[0]!!["value"] != null) {
                            mBinding!!.txtSeverityCount1Avg.text =
                                nonBusiART[0]!!["value"].toString()
                        }

                        if (nonBusiART[0]!!.containsKey("featureName") && nonBusiART[0]!!["featureName"] != null) {
                            mBinding!!.txtSeverity1Avg.text =
                                nonBusiART[0]!!["featureName"].toString()
                        }

                    }
                    if (Utils.hasIndex(1, nonBusiART!!)) {
                        if (nonBusiART[1]!!.containsKey("value") && nonBusiART[1]!!["value"] != null) {
                            mBinding!!.txtSeverityCount2Avg.text =
                                nonBusiART[1]!!["value"].toString()
                        }

                        if (nonBusiART[1]!!.containsKey("featureName") && nonBusiART[1]!!["featureName"] != null) {
                            mBinding!!.txtSeverity2Avg.text =
                                nonBusiART[1]!!["featureName"].toString()
                        }

                    }
                    if (Utils.hasIndex(2, nonBusiART!!)) {
                        if (nonBusiART[2]!!.containsKey("value") && nonBusiART[2]!!["value"] != null) {
                            mBinding!!.txtSeverityCount3Avg.text =
                                nonBusiART[2]!!!!["value"].toString()
                        }

                        if (nonBusiART[2]!!.containsKey("featureName") && nonBusiART[2]!!["featureName"] != null) {
                            mBinding!!.txtSeverity3Avg.text =
                                nonBusiART[2]!!["featureName"].toString()
                        }

                    }
                    if (Utils.hasIndex(3, nonBusiART!!)) {
                        if (nonBusiART[3]!!.containsKey("value") && nonBusiART[3]!!["value"] != null) {
                            mBinding!!.txtSeverityCount4Avg.text =
                                nonBusiART[3]!!["value"].toString()
                        }

                        if (nonBusiART[3]!!.containsKey("featureName") && nonBusiART[3]!!["featureName"] != null) {
                            mBinding!!.txtSeverity4Avg.text =
                                nonBusiART[3]!!["featureName"].toString()
                        }

                    }


                }
            }

        }


        mBinding!!.card1Oi.setOnClickListener {
            if (selection == 0) {
                if (mBinding!!.txtSeverityCount1.text != "0") {
                    var title = activity!!.resources.getString(R.string.open_severity_incident)
                        .replace("%", "1")
                    var count = mBinding!!.txtSeverityCount1.text.toString()
                    openNgoIncidentHistoryFragment(title, count, "busiS1")
                }

            } else {
                if (mBinding!!.txtSeverityCount1.text != "0") {

                    var title = activity!!.resources.getString(R.string.open_severity_incident)
                        .replace("%", "1")
                    var count = mBinding!!.txtSeverityCount1.text.toString()
                    openNgoIncidentHistoryFragment(title, count, "nonBusiS1")
                }

            }
        }



        mBinding!!.card2Oi.setOnClickListener {
            if (selection == 0) {
                if (mBinding!!.txtSeverityCount2.text != "0") {
                    var title = activity!!.resources.getString(R.string.open_severity_incident)
                        .replace("%", "2")
                    var count = mBinding!!.txtSeverityCount2.text.toString()
                    openNgoIncidentHistoryFragment(title, count, "busiS2")

                }
            } else {
                if (mBinding!!.txtSeverityCount2.text != "0") {
                    var title = activity!!.resources.getString(R.string.open_severity_incident)
                        .replace("%", "2")
                    var count = mBinding!!.txtSeverityCount2.text.toString()
                    openNgoIncidentHistoryFragment(title, count, "nonBusiS2")
                }

            }
        }

        mBinding!!.card3Oi.setOnClickListener {
            if (mBinding!!.txtSeverityCount3.text != "0") {
                var title = activity!!.resources.getString(R.string.open_severity_incident)
                    .replace("%", "3")
                var count = mBinding!!.txtSeverityCount3.text.toString()
                openNgoIncidentHistoryFragment(title, count, "nonBusiS3")

            }
        }

        mBinding!!.card4Oi.setOnClickListener {
            if (mBinding!!.txtSeverityCount4.text != "0") {

                var title = activity!!.resources.getString(R.string.open_severity_incident)
                    .replace("%", "4")
                var count = mBinding!!.txtSeverityCount4.text.toString()
                openNgoIncidentHistoryFragment(title, count, "nonBusiS4")
            }
        }


    }

    fun setData(selection: Int, date: String) {
//        this.msg = msg
        this.selection = selection
        this.date = date
    }

    fun openNgoIncidentHistoryFragment(title: String, count: String, key: String) {

        val commonBeanData = CommonBean()
        commonBeanData.setmTitle(MyConstants.NGO)
        var ngoOpenIncidentHistoryItemFragment =
            NgoOpenIncidentHistoryItemFragment.newInstance()
        ngoOpenIncidentHistoryItemFragment.setData(msg, title!!, key, count)
        (activity as MainActivity).openFragments(
            ngoOpenIncidentHistoryItemFragment,
            commonBeanData,
            true,
            true
        )


    }
}
