package com.jio.siops_ngo.governance.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.jio.jioinfra.network.business.BaseCoroutines
import com.jio.jioinfra.utilities.Busicode
import com.jio.jioinfra.utilities.MyConstants
import com.jio.myjio.bean.CoroutinesResponse
import com.jio.siops_ngo.MainActivity

import com.jio.siops_ngo.R
import com.jio.siops_ngo.databinding.FragmentResourceLiveBinding
import com.jio.siops_ngo.governance.adapter.*
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.T
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap
import android.util.Log
import com.jio.jioinfra.bean.CommonBean


/**
 * A simple [Fragment] subclass.
 */
class ResourceProductivityLiveFragment : Fragment() {
    var appDataMap = HashMap<String, Any>()
    var appId = ""
    var appname = ""
    var mapId = ""
    var appType = ""
    var mBinding: FragmentResourceLiveBinding? = null
    var displayDateFormat: SimpleDateFormat = SimpleDateFormat("dd-MMM-yyyy")
    var currentDate = ""
    var shiftDomainIdDataMap = HashMap<String, List<Map<String, Any>>>()
    var shiftCitrixSessionDataMap = HashMap<String, List<Map<String, Any>>>()
    var shiftNgoSessionDataMap = HashMap<String, List<Map<String, Any>>>()
    var shiftAlertsDataMap = HashMap<String, List<Map<String, Any>>>()
    var shiftList: ArrayList<String>? = ArrayList()
    var userDasboardResponseSuccess: Int? = -1
    var citrixSessionDetailResponseSuccess: Int? = -1
    var ngoSessionDetailResponseSuccess: Int? = -1
    var alertByDomainIdResponseSuccess: Int? = -1

    companion object {
        fun newInstance() = ResourceProductivityLiveFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_resource_live, container, false)

        return mBinding!!.root
        // return inflater.inflate(R.layout.fragment_resource_live, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (appDataMap.containsKey("appId") && appDataMap["appId"] != null) {
            appId = appDataMap["appId"].toString()
        }
        if (appDataMap.containsKey("mapId") && appDataMap["mapId"] != null) {
            mapId = appDataMap["mapId"].toString()
        }
        if (appDataMap.containsKey("appType") && appDataMap["appType"] != null) {
            appType = appDataMap["appType"].toString()
        }
        if (appDataMap.containsKey("appName") && appDataMap["appName"] != null) {
            appname = appDataMap["appName"].toString()
            mBinding!!.txtAppName.text = appname
        }

        var calendar = Calendar.getInstance().getTime();
        currentDate = displayDateFormat.format(calendar)
//


        onClickView()

        fetchDomainIdData(Busicode.ProductivityApplicationUserDashboard)
        fetchDomainIdData(Busicode.ProductivityCitrixSessionDetail)
        fetchDomainIdData(Busicode.ProductivityNgoSessionDetail)
        fetchDomainIdData(Busicode.ProductivityAlertByDomainId)
    }

    private fun onClickView() {


        mBinding!!.imgCalanderId.setOnClickListener {
            if (mBinding!!.cnstrntLCalendarSd!!.visibility == View.GONE) {
                mBinding!!.cnstrntLCalendarSd.visibility = View.VISIBLE
            } else {
                mBinding!!.cnstrntLCalendarSd.visibility = View.GONE
            }

        }

        mBinding!!.txtLiveAndHistoryDashboard.setOnClickListener {


            var commonBean = CommonBean()
            commonBean.`object` = appDataMap
            var resourceProductivityHistoryFragment = ResourceProductivityHistoryFragment.newInstance()
            resourceProductivityHistoryFragment.setData(appDataMap as HashMap<String, Any>)
            (activity as MainActivity)!!.openFragments(
                resourceProductivityHistoryFragment,
                commonBean,
                true,
                true
            )

        }
    }


    fun fetchDomainIdData(busicode: String) {
        (activity as MainActivity).showProgressBar()

        val requestBody = HashMap<String, Any>()
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "productivityRequest"
        requestBody["jwtToken"] = "POSTMAN-TESTING"
        requestBody["view"] = "live"
        requestBody["appType"] = appType
        requestBody["appId"] = appId
        requestBody["mapId"] = mapId
        requestBody["startDate"] = currentDate
        requestBody["endDate"] = currentDate
        //   requestBody["appRoleCode"] = "741"
        CoroutineScope(Dispatchers.IO).launch {

            var job = async {
                BaseCoroutines().fetchData(
                    requestBody,
                    busicode!!,
                    activity as MainActivity
                )
            }
            withContext(Dispatchers.Main)
            {

                var response = job.await()
                /*if (activity != null) {
                    (activity as MainActivity).hideProgressBar()
                }*/
                if (response!!.responseEntity != null && response.status == MappActor.STATUS_OK) {
                    when(busicode){
                        Busicode.ProductivityApplicationUserDashboard ->  userDasboardResponseSuccess = 0
                        Busicode.ProductivityCitrixSessionDetail -> citrixSessionDetailResponseSuccess = 0
                        Busicode.ProductivityNgoSessionDetail -> ngoSessionDetailResponseSuccess = 0
                        Busicode.ProductivityAlertByDomainId -> alertByDomainIdResponseSuccess = 0

                    }
                    filterData(response, busicode)
                } else if (response!!.errorCode != null && response!!.errorCode.equals(MappActor.VERSION_SESSION_INVALID)) {
                    when(busicode){
                        Busicode.ProductivityApplicationUserDashboard ->  userDasboardResponseSuccess = 1
                        Busicode.ProductivityCitrixSessionDetail -> citrixSessionDetailResponseSuccess = 1
                        Busicode.ProductivityNgoSessionDetail -> ngoSessionDetailResponseSuccess = 1
                        Busicode.ProductivityAlertByDomainId -> alertByDomainIdResponseSuccess = 1

                    }
                    (activity as MainActivity).showDialogForSessionExpired(
                        (activity as MainActivity).resources.getString(
                            R.string.session_expired
                        ), (activity as MainActivity)
                    )
                } else if (response!!.errorMsg != null) {
                    when(busicode){
                        Busicode.ProductivityApplicationUserDashboard ->  userDasboardResponseSuccess = 1
                        Busicode.ProductivityCitrixSessionDetail -> citrixSessionDetailResponseSuccess = 1
                        Busicode.ProductivityNgoSessionDetail -> ngoSessionDetailResponseSuccess = 1
                        Busicode.ProductivityAlertByDomainId -> alertByDomainIdResponseSuccess = 1

                    }
                    T.show(activity, response!!.errorMsg!!, 0)
                } else {
                    when(busicode){
                        Busicode.ProductivityApplicationUserDashboard ->  userDasboardResponseSuccess = 1
                        Busicode.ProductivityCitrixSessionDetail -> citrixSessionDetailResponseSuccess = 1
                        Busicode.ProductivityNgoSessionDetail -> ngoSessionDetailResponseSuccess = 1
                        Busicode.ProductivityAlertByDomainId -> alertByDomainIdResponseSuccess = 1

                    }
                    T.show(activity, "Something went wrong!", 0)
                }
            }

        }
    }


    fun filterData(mCoroutinesResponse: CoroutinesResponse, busicode: String) {

        try {

            val msg = mCoroutinesResponse.responseEntity as HashMap<String, Any>
            if (msg != null) {
                try {
                    var listData = HashMap<String, Any>()

                    when(busicode){
                        Busicode.ProductivityApplicationUserDashboard ->  listData = msg["productivityUserVsApplicationMap"] as HashMap<String, Any>
                        Busicode.ProductivityCitrixSessionDetail -> listData = msg["citrixSessionList"] as HashMap<String, Any>
                        Busicode.ProductivityNgoSessionDetail -> listData = msg["ngoSessionList"] as HashMap<String, Any>
                        Busicode.ProductivityAlertByDomainId -> listData = msg

                    }
                    shiftList!!.clear()

                    for (key in listData.keys) {
                        val lKey = key as String
                        Log.e("keys", lKey)
                        shiftList!!.add(lKey)

                        if (lKey.trim().length > 0) {
                            when (busicode) {
                                Busicode.ProductivityApplicationUserDashboard -> shiftDomainIdDataMap.put(
                                    lKey,
                                    listData[lKey] as List<Map<String, Any>>
                                )
                                Busicode.ProductivityCitrixSessionDetail -> shiftCitrixSessionDataMap.put(
                                    lKey,
                                    listData[lKey] as List<Map<String, Any>>
                                )
                                Busicode.ProductivityNgoSessionDetail -> shiftNgoSessionDataMap.put(
                                    lKey,
                                    listData[lKey] as List<Map<String, Any>>
                                )
                                Busicode.ProductivityAlertByDomainId -> shiftAlertsDataMap.put(
                                    lKey,
                                    listData[lKey] as List<Map<String, Any>>
                                )
                            }

                        }
                    }

                    /*if (shiftDomainIdDataMap.size > 0 && shiftCitrixSessionDataMap.size > 0
                        && shiftNgoSessionDataMap.size>0 && shiftAlertsDataMap.size>0) {*/
                    if (userDasboardResponseSuccess!! > -1 && citrixSessionDetailResponseSuccess!! > -1 && ngoSessionDetailResponseSuccess!! > -1
                        && alertByDomainIdResponseSuccess!! > -1
                    ) {
                        if (activity != null) {
                            (activity!! as MainActivity).hideProgressBar()
                        }
                        var resourceProductivityShiftAdapter = ResourceProductivityLiveShiftAdapter(
                            activity as MainActivity?,
                            shiftDomainIdDataMap!!,
                            shiftList!!,
                            shiftCitrixSessionDataMap!!,
                            shiftNgoSessionDataMap!!,
                            shiftAlertsDataMap!!
                        )
                        mBinding!!.mainList.layoutManager = LinearLayoutManager(activity)
                        mBinding!!.mainList.itemAnimator = DefaultItemAnimator()
                        mBinding!!.mainList!!.adapter = resourceProductivityShiftAdapter
                        resourceProductivityShiftAdapter!!.notifyDataSetChanged()
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

    fun setData(mapData: HashMap<String, Any>) {
        appDataMap = mapData!!
    }


}
