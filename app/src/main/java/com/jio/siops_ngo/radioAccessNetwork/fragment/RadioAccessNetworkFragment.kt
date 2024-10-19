package com.jio.siops_ngo.radioAccessNetwork.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.jio.jioinfra.bean.CommonBean
import com.jio.jioinfra.network.business.BaseCoroutines
import com.jio.jioinfra.utilities.Busicode
import com.jio.jioinfra.utilities.MyConstants
import com.jio.myjio.bean.CoroutinesResponse
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.databinding.RanDashboardBinding
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.PreferenceUtility
import kotlinx.coroutines.*

class RadioAccessNetworkFragment : Fragment() {
    var mBinding: RanDashboardBinding? = null
    var map: Map<String, Any>? = null
    internal var commonBean: CommonBean? = null

    companion object {
        fun newInstance() = RadioAccessNetworkFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        mBinding = DataBindingUtil.inflate(inflater, R.layout.ran_dashboard, container, false)

        return mBinding!!.root


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        map = commonBean!!.`object` as Map<String, Any>
        fetchRanData(Busicode.PerformanceRAN)
        fetchRanData(Busicode.AvailabilityRAN)
        fetchRanData(Busicode.WorkOrderRAN)


        if (activity != null) {
            mBinding!!.itemsswipetorefresh.setProgressBackgroundColorSchemeColor(
                ContextCompat.getColor(
                    (activity as MainActivity),
                    R.color.toolbar_bg
                )
            )
        }

        mBinding!!.itemsswipetorefresh.setColorSchemeColors(Color.WHITE)
        mBinding!!.itemsswipetorefresh.setOnRefreshListener {
            fetchRanData(Busicode.PerformanceRAN)
            fetchRanData(Busicode.AvailabilityRAN)
            fetchRanData(Busicode.WorkOrderRAN)
        }
    }

    fun fetchRanData(busicode: String) {
        if (busicode.equals(Busicode.PerformanceRAN))
            mBinding!!.progressBarPerf.isIndeterminate = true
        else if (busicode.equals(Busicode.AvailabilityRAN))
            mBinding!!.progressBarAvailability.isIndeterminate = true

        val requestBody = HashMap<String, Any>()
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["appRoleCode"] = map!!["applicationCode"] as String
        requestBody["type"] = "userInfo"
        CoroutineScope(Dispatchers.IO).launch {

            var job = async {
                BaseCoroutines().fetchData(
                    requestBody,
                    busicode,
                    activity as MainActivity
                )
            }
            withContext(Dispatchers.Main)
            {

                var response = job.await()

                if (response!!.responseEntity != null && response.status == MappActor.STATUS_OK) {
                    if (busicode.equals(Busicode.PerformanceRAN)) {
                        mBinding!!.progressBarPerf.visibility = View.GONE
                        mBinding!!.cnstrntLPerformance.visibility = View.VISIBLE
                    } else if (busicode.equals(Busicode.AvailabilityRAN)) {
                        mBinding!!.progressBarAvailability.visibility = View.GONE
                        mBinding!!.cnstrntLAvailability.visibility = View.VISIBLE
                    } else if (busicode.equals(Busicode.WorkOrderRAN)) {
                        mBinding!!.progressBarWorkOrder.visibility = View.GONE
                        mBinding!!.cnstrntLWorkOrder.visibility = View.VISIBLE
                    }
                    filterData(response, busicode)
                } else if (response!!.errorCode != null && response!!.errorCode.equals(MappActor.VERSION_SESSION_INVALID)) {
                    (activity as MainActivity).showDialogForSessionExpired(
                        (activity as MainActivity).resources.getString(
                            R.string.session_expired
                        ), (activity as MainActivity)
                    )
                } else if (response!!.errorMsg != null) {
                    if (busicode.equals(Busicode.PerformanceRAN)) {
                        mBinding!!.progressBarPerf.visibility = View.GONE
                        mBinding!!.txtAvailabilityMsg.text = response!!.errorMsg!!
                    } else if (busicode.equals(Busicode.AvailabilityRAN)) {
                        mBinding!!.progressBarAvailability.visibility = View.GONE
                        mBinding!!.txtPerformanceMsg.text = response!!.errorMsg!!
                    } else if (busicode.equals(Busicode.WorkOrderRAN)) {
                        mBinding!!.progressBarWorkOrder.visibility = View.GONE
                        mBinding!!.txtWoMsg.text = response!!.errorMsg!!
                    }
//                    T.show(activity, response!!.errorMsg!!, 0)
                } else {
                    if (busicode.equals(Busicode.PerformanceRAN)) {
                        mBinding!!.progressBarPerf.visibility = View.GONE
                        mBinding!!.txtAvailabilityMsg.text = "Something went wrong!"
                    } else if (busicode.equals(Busicode.AvailabilityRAN)) {
                        mBinding!!.progressBarAvailability.visibility = View.GONE
                        mBinding!!.txtPerformanceMsg.text = "Something went wrong!"
                    } else if (busicode.equals(Busicode.WorkOrderRAN)) {
                        mBinding!!.progressBarWorkOrder.visibility = View.GONE
                        mBinding!!.txtWoMsg.text = "Something went wrong!"
                    }
//                    T.show(activity, "Something went wrong!", 0)
                }
            }

        }
    }

    fun filterData(mCoroutinesResponse: CoroutinesResponse, busicode: String) {

        try {
            val msg = mCoroutinesResponse.responseEntity as HashMap<String, Any>

            if (msg != null) {

                mBinding!!.itemsswipetorefresh.isRefreshing = false

                if (busicode.equals(Busicode.PerformanceRAN)) {


                    if (msg.containsKey("dead") && msg["dead"] != null) {

                        mBinding!!.txtPerformanceDeadCount.text =
                            msg["dead"].toString() + " " + msg["deadUnit"].toString()

                    }

                    if (msg.containsKey("icu") && msg["icu"] != null) {

                        mBinding!!.txtPerformanceIcuCount.text =
                            msg["icu"].toString() + " " + msg["icuUnit"].toString()

                    }

                    if (msg.containsKey("hosp") && msg["hosp"] != null) {

                        mBinding!!.txtPerformanceHospCount.text =
                            msg["hosp"].toString() + " " + msg["hospUnit"].toString()

                    }

                    if (msg.containsKey("healthy") && msg["healthy"] != null) {

                        mBinding!!.txtPerformanceHealthyCount.text =
                            msg["healthy"].toString() + " " + msg["healthyUnit"].toString()

                    }

                    if (msg.containsKey("ipThroughput") && msg["ipThroughput"] != null) {
//                        if( msg["ipThroughput"].toString()!!.equals("0")) {
//                            mBinding!!.txtPerformanceIpThCount.text ="-"
//
//                        }else{
                        mBinding!!.txtPerformanceIpThCount.text =
                            msg["ipThroughput"].toString() + " " + msg["ipThroughputUnit"].toString()
                        //       }

                    }

                    if (msg.containsKey("cellThroughput") && msg["cellThroughput"] != null) {


                        if (msg["cellThroughput"].toString()!!.equals("0")) {
                            mBinding!!.txtPerformanceCellEffCount.text = "-"

                        } else {
                            mBinding!!.txtPerformanceCellEffCount.text =
                                msg["cellThroughput"].toString() + " " + msg["cellThroughputUnit"].toString()

                        }


                    }

                    if (msg.containsKey("consumptionData") && msg["consumptionData"] != null) {

                        mBinding!!.txtPerformanceConsumpCount.text =
                            msg["consumptionData"].toString() + " " + msg["consumptionDataUnit"].toString()

                    }

                    if (msg.containsKey("consumptionVoice") && msg["consumptionVoice"] != null) {

                        if (msg["consumptionVoice"].toString()!!.equals("0")) {
                            mBinding!!.txtPerformanceConsumpCountVoice.text = "-"
                        } else {

                            mBinding!!.txtPerformanceConsumpCountVoice.text =
                                msg["consumptionVoice"].toString() + " " + msg["consumptionVoiceUnit"].toString()
                        }

                    }


                    if (msg.containsKey("dcr") && msg["dcr"] != null) {


//                        if( msg["dcr"].toString()!!.equals("0")) {
//                            mBinding!!.txtPerformanceDcrCount.text ="-"
//                        }else {

                        mBinding!!.txtPerformanceDcrCount.text = msg["dcr"].toString() + " %"
                        //     }

                    }

                    if (msg.containsKey("mcr") && msg["mcr"] != null) {

                        if (msg["mcr"].toString()!!.equals("0")) {
                            mBinding!!.txtPerformanceMcrCount.text = "-"
                        } else {

                            mBinding!!.txtPerformanceMcrCount.text = msg["mcr"].toString() + " %"
                        }

                    }
                } else if (busicode.equals(Busicode.AvailabilityRAN)) {
                    if (msg.containsKey("avalPercent") && msg["avalPercent"] != null) {

                        mBinding!!.txtAvailabilityCount.text = msg["avalPercent"].toString() + " %"

                    }
                    if (msg.containsKey("unAvalPercent") && msg["unAvalPercent"] != null) {

                        mBinding!!.txtUnavailabilityCount.text =
                            msg["unAvalPercent"].toString() + " %"

                    }
                    if (msg.containsKey("avalMins") && msg["avalMins"] != null) {

                        mBinding!!.txtAvailabilityTime.text =
                            msg["avalMins"].toString() + " " + msg["avalMinsUnit"].toString()

                    }
                    if (msg.containsKey("unAvalMins") && msg["unAvalMins"] != null) {

                        mBinding!!.txtUnavailabilityTime.text =
                            msg["unAvalMins"].toString() + " " + msg["unAvalMinsUnit"].toString()

                    }
                }else if (busicode.equals(Busicode.WorkOrderRAN)) {
                    if (msg.containsKey("performance") && msg["performance"] != null) {

                        mBinding!!.txtWoPerfCount.text = msg["performance"].toString()

                    }
                    if (msg.containsKey("deployment") && msg["deployment"] != null) {

                        mBinding!!.txtWoDeploymentCount.text = msg["deployment"].toString()

                    }
                    if (msg.containsKey("operations") && msg["operations"] != null) {

                        mBinding!!.txtWoOpsCount.text = msg["operations"].toString()

                    }
                }

            }

        } catch (e: Exception) {
            e.printStackTrace()
            MyExceptionHandler.handle(e)
        }
    }

    fun setData(commonBean: CommonBean) {
        this.commonBean = commonBean
    }


}