package com.jio.siops_ngo.ngo.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.jio.jioinfra.bean.CommonBean
import com.jio.jioinfra.network.business.BaseCoroutines
import com.jio.jioinfra.utilities.Busicode
import com.jio.jioinfra.utilities.MyConstants
import com.jio.myjio.bean.CoroutinesResponse
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.databinding.FragmentNgoHistoryBinding
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.T
import com.jio.siops_ngo.utilities.Utils
import com.jio.siops_ngo.utilities.Utils.Companion.dateConvert

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlinx.coroutines.*


/**
 * A simple [Fragment] subclass.
 */
class NgoHistoryFragment : Fragment() {
    var mBinding:FragmentNgoHistoryBinding?=null
    var arrays:ArrayList<String>?=null
    var commonBean: CommonBean? = null
    var openAlertsList:ArrayList<Map<String, Any>>? = null
    var acknowledementList:ArrayList<Map<String, Any>>? = null
    var acknowledementSLAList:ArrayList<Map<String, Any>>? = null
    var applicationList:ArrayList<Map<String, Any>>? = null
    var statusList:ArrayList<Map<String, Any>>? = null
    var averageTimeList:ArrayList<Map<String, Any>>? = null
    var toolList:ArrayList<Map<String, Any>>? = null
    var infrastructureList:ArrayList<Map<String, Any>>? = null
    var missedList:ArrayList<Map<String, Any>>? = null
    var metList:ArrayList<Map<String, Any>>? = null
    var acknowledgedList:ArrayList<Map<String, Any>>? = null
    var unacknowledgedList:ArrayList<Map<String, Any>>? = null
    var systemList:ArrayList<Map<String, Any>>? = null
    var closeList:ArrayList<Map<String, Any>>? = null
    var openList:ArrayList<Map<String, Any>>? = null
    var title:String?=null
    var dateStr:String?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_ngo_history, container, false)
        return mBinding!!.root
    }
    companion object{
        fun  newInstance()=
            NgoHistoryFragment()
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mBinding!!.scrolViewId.visibility=View.GONE
        mBinding!!.header.text="Service Glance - "+title
        val c = Calendar.getInstance().time
        val df = SimpleDateFormat("dd/MM/yyyy")
        val formattedDate = df.format(c)
       // mBinding!!.txtDate.text = formattedDate



        mBinding!!.currentdate.text=dateConvert(dateStr)

        fetchData()

        mBinding!!.card1.setOnClickListener {

            if(infrastructureList!=null) {

                if (mBinding!!.infraCount1.text.toString() != "0") {


                    PreferenceUtility.addString(activity,"subtitle", mBinding!!.infraTitle1.text.toString())

                    val commonBeanData = CommonBean()
                    commonBeanData.setmTitle(MyConstants.NGO)
                    var ngoServiceGlaneceNgoOpenAlertsDetailsFragment =
                        NgoOpenHistoryAlertsDetailsFragment.newInstance()
                    ngoServiceGlaneceNgoOpenAlertsDetailsFragment.setData(
                        commonBeanData,
                        infrastructureList,
                        mBinding!!.infraTitle1.text.toString(),
                        mBinding!!.infraCount1.text.toString()
                    )
                    (activity as MainActivity).openFragments(
                        ngoServiceGlaneceNgoOpenAlertsDetailsFragment,
                        commonBeanData,
                        true,
                        true
                    )
                }
            }
        }


        mBinding!!.card22.setOnClickListener {


            if(systemList!=null) {

                if (mBinding!!.systemCount2.text.toString() != "0") {

                    PreferenceUtility.addString(activity,"subtitle", mBinding!!.systemTitle2.text.toString())

                    val commonBeanData = CommonBean()
                    commonBeanData.setmTitle(MyConstants.NGO)
                    var ngoServiceGlaneceNgoOpenAlertsDetailsFragment =
                        NgoOpenHistoryAlertsDetailsFragment.newInstance()
                    ngoServiceGlaneceNgoOpenAlertsDetailsFragment.setData(
                        commonBeanData,
                        systemList,
                        mBinding!!.systemTitle2.text.toString(),
                        mBinding!!.systemCount2.text.toString()
                    )
                    (activity as MainActivity).openFragments(
                        ngoServiceGlaneceNgoOpenAlertsDetailsFragment,
                        commonBeanData,
                        true,
                        true
                    )
                }
            }
        }
        mBinding!!.card1Status.setOnClickListener {


            if(closeList!=null) {

                if (mBinding!!.closedCount1.text.toString() != "0") {


                    PreferenceUtility.addString(activity,"subtitle", mBinding!!.closedTitle1.text.toString())

                    val commonBeanData = CommonBean()
                    commonBeanData.setmTitle(MyConstants.NGO)
                    var ngoServiceGlaneceNgoOpenAlertsDetailsFragment =
                        NgoOpenHistoryAlertsDetailsFragment.newInstance()
                    ngoServiceGlaneceNgoOpenAlertsDetailsFragment.setData(
                        commonBeanData,
                        closeList,
                        mBinding!!.closedTitle1.text.toString(),
                        mBinding!!.closedCount1.text.toString()
                    )
                    (activity as MainActivity).openFragments(
                        ngoServiceGlaneceNgoOpenAlertsDetailsFragment,
                        commonBeanData,
                        true,
                        true
                    )
                }
            }
        }


        mBinding!!.card3Status.setOnClickListener {


            if(openList!=null) {

                if (mBinding!!.openCount3.text.toString() != "0") {
                    PreferenceUtility.addString(activity,"subtitle", mBinding!!.openTitle3.text.toString())
                    val commonBeanData = CommonBean()
                    commonBeanData.setmTitle(MyConstants.NGO)
                    var ngoServiceGlaneceNgoOpenAlertsDetailsFragment =
                        NgoOpenHistoryAlertsDetailsFragment.newInstance()
                    ngoServiceGlaneceNgoOpenAlertsDetailsFragment.setData(
                        commonBeanData,
                        openList,
                        mBinding!!.openTitle3.text.toString(),
                        mBinding!!.openCount3.text.toString()
                    )
                    (activity as MainActivity).openFragments(
                        ngoServiceGlaneceNgoOpenAlertsDetailsFragment,
                        commonBeanData,
                        true,
                        true
                    )
                }
            }
        }


        mBinding!!.card2.setOnClickListener {


            if(applicationList!=null) {

                if (mBinding!!.applicationCount2.text.toString() != "0") {

                    val commonBeanData = CommonBean()
                    PreferenceUtility.addString(activity,"subtitle", mBinding!!.applicationTitle2.text.toString())
                    commonBeanData.setmTitle(MyConstants.NGO)
                    var ngoServiceGlaneceNgoOpenAlertsDetailsFragment =
                        NgoOpenHistoryAlertsDetailsFragment.newInstance()
                    ngoServiceGlaneceNgoOpenAlertsDetailsFragment.setData(
                        commonBeanData,
                        applicationList,
                        mBinding!!.applicationTitle2.text.toString(),
                        mBinding!!.applicationCount2.text.toString()
                    )
                    (activity as MainActivity).openFragments(
                        ngoServiceGlaneceNgoOpenAlertsDetailsFragment,
                        commonBeanData,
                        true,
                        true
                    )
                }
            }
        }
        mBinding!!.card3.setOnClickListener {


            if(toolList!=null) {

                if (mBinding!!.toolCount3.text.toString() != "0") {

                    val commonBeanData = CommonBean()
                    commonBeanData.setmTitle(MyConstants.NGO)
                    PreferenceUtility.addString(activity,"subtitle", mBinding!!.toolTitle3.text.toString())

                    var ngoServiceGlaneceNgoOpenAlertsDetailsFragment =
                        NgoOpenHistoryAlertsDetailsFragment.newInstance()
                    ngoServiceGlaneceNgoOpenAlertsDetailsFragment.setData(
                        commonBeanData,
                        toolList,
                        mBinding!!.toolTitle3.text.toString(),
                        mBinding!!.toolCount3.text.toString()
                    )
                    (activity as MainActivity).openFragments(
                        ngoServiceGlaneceNgoOpenAlertsDetailsFragment,
                        commonBeanData,
                        true,
                        true
                    )

                }
            }
        }
//        mBinding!!.card3.setOnClickListener {
//
//
//
//            if(toolList!=null) {
//
//                if (mBinding!!.toolCount3.text.toString() != "0") {
//
//                    val commonBeanData = CommonBean()
//                    commonBeanData.setmTitle(MyConstants.NGO)
//                    /*commonBeanData.usefulLinksNgo = commonBean!!.usefulLinksNgo
//            commonBeanData.usefulLinksNgo = commonBean!!.usefulLinksNgo
//            commonBeanData.usefulLinksBusinessView = commonBean!!.usefulLinksBusinessView*/
//                    //var dashboardNgoFragment = DashboardNgoFragment.newInstance()
//                    var ngoServiceGlaneceNgoOpenAlertsDetailsFragment =
//                        NgoOpenHistoryAlertsDetailsFragment.newInstance()
//                    ngoServiceGlaneceNgoOpenAlertsDetailsFragment.setData(
//                        commonBeanData,
//                        toolList,
//                        mBinding!!.toolTitle3.text.toString(),
//                        mBinding!!.toolCount3.text.toString()
//                    )
//                    (activity as MainActivity).openFragments(
//                        ngoServiceGlaneceNgoOpenAlertsDetailsFragment,
//                        commonBeanData,
//                        true,
//                        true
//                    )
//                }
//            }
//        }

        mBinding!!.card11.setOnClickListener {

            if(acknowledgedList!=null) {

                if (mBinding!!.acknowladgedCount1.text.toString() != "0") {

                    PreferenceUtility.addString(activity,"subtitle", mBinding!!.acknowladgedTitle1.text.toString())
                    val commonBeanData = CommonBean()
                    commonBeanData.setmTitle(MyConstants.NGO)
                    var ngoServiceGlaneceNgoOpenAlertsDetailsFragment =
                        NgoOpenHistoryAlertsDetailsFragment.newInstance()
                    ngoServiceGlaneceNgoOpenAlertsDetailsFragment.setData(
                        commonBeanData,
                        acknowledgedList,
                        mBinding!!.acknowladgedTitle1.text.toString(),
                        mBinding!!.acknowladgedCount1.text.toString()
                    )
                    (activity as MainActivity).openFragments(
                        ngoServiceGlaneceNgoOpenAlertsDetailsFragment,
                        commonBeanData,
                        true,
                        true
                    )
                }
            }
        }


        mBinding!!.card33.setOnClickListener {


            if(unacknowledgedList!=null) {

                if (mBinding!!.acknowladgedCount3.text.toString() != "0") {

                    PreferenceUtility.addString(activity,"subtitle", mBinding!!.acknowladgedTitle3.text.toString())
                    val commonBeanData = CommonBean()
                    commonBeanData.setmTitle(MyConstants.NGO)
                    var ngoServiceGlaneceNgoOpenAlertsDetailsFragment =
                        NgoOpenHistoryAlertsDetailsFragment.newInstance()
                    ngoServiceGlaneceNgoOpenAlertsDetailsFragment.setData(
                        commonBeanData,
                        unacknowledgedList,
                        mBinding!!.acknowladgedTitle3.text.toString(),
                        mBinding!!.acknowladgedCount3.text.toString()
                    )
                    (activity as MainActivity).openFragments(
                        ngoServiceGlaneceNgoOpenAlertsDetailsFragment,
                        commonBeanData,
                        true,
                        true
                    )
                }
            }
        }


        mBinding!!.cardopencharge.setOnClickListener {


            if(metList!=null) {

                if (mBinding!!.metCount1.text.toString() != "0") {
                    val commonBeanData = CommonBean()
                    PreferenceUtility.addString(activity,"subtitle", mBinding!!.openchargeTitle1.text.toString())
                    commonBeanData.setmTitle(MyConstants.NGO)
                    var ngoServiceGlaneceNgoOpenAlertsDetailsFragment =
                        NgoOpenHistoryAlertsDetailsFragment.newInstance()
                    ngoServiceGlaneceNgoOpenAlertsDetailsFragment.setData(
                        commonBeanData,
                        metList,
                        mBinding!!.openchargeTitle1.text.toString(),
                        mBinding!!.metCount1.text.toString()
                    )
                    (activity as MainActivity).openFragments(
                        ngoServiceGlaneceNgoOpenAlertsDetailsFragment,
                        commonBeanData,
                        true,
                        true
                    )
                }
            }
        }

        mBinding!!.cardopencharge3.setOnClickListener {



            if(missedList!=null) {

                if (mBinding!!.missedCount3.text.toString() != "0") {
                    PreferenceUtility.addString(activity,"subtitle", mBinding!!.opnechargeTitle3.text.toString())
                    val commonBeanData = CommonBean()
                    commonBeanData.setmTitle(MyConstants.NGO)
                    var ngoServiceGlaneceNgoOpenAlertsDetailsFragment =
                        NgoOpenHistoryAlertsDetailsFragment.newInstance()
                    ngoServiceGlaneceNgoOpenAlertsDetailsFragment.setData(
                        commonBeanData,
                        missedList,
                        mBinding!!.opnechargeTitle3.text.toString(),
                        mBinding!!.missedCount3.text.toString()
                    )
                    (activity as MainActivity).openFragments(
                        ngoServiceGlaneceNgoOpenAlertsDetailsFragment,
                        commonBeanData,
                        true,
                        true
                    )
                }
            }
        }

    }


    fun fetchData() {
        (activity as MainActivity).showProgressBar()
        val requestBody = HashMap<String, Any>()
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "userInfo"
        requestBody["appRoleCode"] = "726"
        requestBody["date"] = dateStr!!
        CoroutineScope(Dispatchers.IO).launch {
            var job = async {
                BaseCoroutines().fetchData(
                    requestBody,
                    Busicode.NGOOpenAlertHistory,
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
                } else if (response!!.errorCode != null && response!!.errorCode.equals(MappActor.VERSION_SESSION_INVALID)){
                    (activity as MainActivity).showDialogForSessionExpired((activity as MainActivity).resources.getString(R.string.session_expired), (activity as MainActivity))
                } else if (response!!.errorMsg != null) {
                    T.show(activity, response!!.errorMsg!!, 0)
                } else {
                    T.show(activity, "Something went wrong!", 0)
                }
            }

        }
    }


    fun filterData(mCoroutinesResponse: CoroutinesResponse) {
        mBinding!!.scrolViewId.visibility=View.VISIBLE
        try {
            var msg = mCoroutinesResponse.responseEntity as HashMap<String, Any>


            if (msg != null) {
                try {
                    var commonBeanMainServices: CommonBean = CommonBean()

                    if(msg!!.containsKey("acknowledementCount")&& msg!!["acknowledementCount"]!=null){
                        mBinding!!.txtAcknowladgementCount.text="("+msg!!["acknowledementCount"].toString()+")"
                    }
                    if(msg!!.containsKey("acknowledementCount")&& msg!!["acknowledementCount"]!=null){
                        mBinding!!.txtAcknowladgeSlaCount.text="("+msg!!["acknowledementCount"].toString()+")"
                    }

                    if(msg!!.containsKey("openAlertCount")&& msg!!["openAlertCount"]!=null){
                        mBinding!!.txtOpenAlartCount.text= "("+msg!!["openAlertCount"].toString()+")"
                    }

                    if(msg!!.containsKey("statusCount")&& msg!!["statusCount"]!=null){
                        mBinding!!.txtStatusCount.text= "("+msg!!["statusCount"].toString()+")"
                    }

                    if(msg.containsKey("openAlerts")&&msg!!["openAlerts"]!=null){
                        openAlertsList= msg!!["openAlerts"] as ArrayList<Map<String, Any>>
                    }
                    if(msg.containsKey("acknowledement")&&msg!!["acknowledement"]!=null){
                        acknowledementList= msg!!["acknowledement"] as ArrayList<Map<String, Any>>
                    }

                    if(msg.containsKey("acknowledementSLA")&&msg!!["acknowledementSLA"]!=null){
                        acknowledementSLAList= msg!!["acknowledementSLA"] as ArrayList<Map<String, Any>>
                    }

                    if(msg.containsKey("status")&&msg!!["status"]!=null){
                        statusList= msg!!["status"] as ArrayList<Map<String, Any>>
                    }
                    if(msg.containsKey("averageTime")&&msg!!["averageTime"]!=null){
                        averageTimeList= msg!!["averageTime"] as ArrayList<Map<String, Any>>
                    }
                    if(msg.containsKey("application")&&msg!!["application"]!=null){
                        applicationList = msg!!["application"] as ArrayList<Map<String, Any>>
                    }
                    if(msg.containsKey("infrastructure")&&msg!!["infrastructure"]!=null){
                        infrastructureList = msg!!["infrastructure"] as ArrayList<Map<String, Any>>
                    }
                    if(msg.containsKey("tools")&&msg!!["tools"]!=null){
                        toolList = msg!!["tools"] as ArrayList<Map<String, Any>>
                    }
                    if(msg.containsKey("engineer")&&msg!!["engineer"]!=null){
                        acknowledgedList = msg!!["engineer"] as ArrayList<Map<String, Any>>
                    }

                    if(msg.containsKey("unAcknowledged")&&msg!!["unAcknowledged"]!=null){
                        unacknowledgedList = msg!!["unAcknowledged"] as ArrayList<Map<String, Any>>
                    }

                    if(msg.containsKey("unAcknowledged")&&msg!!["unAcknowledged"]!=null){
                        metList = msg!!["met"] as ArrayList<Map<String, Any>>
                    }

                    if(msg.containsKey("unAcknowledged")&&msg!!["unAcknowledged"]!=null){
                        missedList = msg!!["missed"] as ArrayList<Map<String, Any>>
                    }

                    if(msg.containsKey("system")&&msg!!["system"]!=null){
                        systemList = msg!!["system"] as ArrayList<Map<String, Any>>
                    }

                    if(msg.containsKey("open")&&msg!!["open"]!=null){
                        openList = msg!!["open"] as ArrayList<Map<String, Any>>
                    }


                    if(msg.containsKey("closed")&&msg!!["closed"]!=null){
                        closeList = msg!!["closed"] as ArrayList<Map<String, Any>>
                    }




                    if(Utils.hasIndex(0, openAlertsList!!)){
                        if(openAlertsList!!.get(0).containsKey("featureName")&& openAlertsList!!.get(0)["featureName"]!=null){
                            mBinding!!.infraTitle1.text= openAlertsList!!.get(0)["featureName"].toString()
                        }
                        if(openAlertsList!!.get(0).containsKey("outlierCount")&& openAlertsList!!.get(0)["outlierCount"]!=null){
                            mBinding!!.infraCount1.text= openAlertsList!!.get(0)["outlierCount"].toString()
                        }
                    }

                    if(Utils.hasIndex(1, openAlertsList!!)){
                        if(openAlertsList!!.get(1).containsKey("featureName")&& openAlertsList!!.get(1)["featureName"]!=null){
                            mBinding!!.applicationTitle2.text= openAlertsList!!.get(1)["featureName"].toString()
                        }
                        if(openAlertsList!!.get(1).containsKey("outlierCount")&& openAlertsList!!.get(1)["outlierCount"]!=null){
                            mBinding!!.applicationCount2.text= openAlertsList!!.get(1)["outlierCount"].toString()
                        }
                    }

                    if(Utils.hasIndex(2, openAlertsList!!)){
                        if(openAlertsList!!.get(2).containsKey("featureName")&& openAlertsList!!.get(2)["featureName"]!=null){
                            mBinding!!.toolTitle3.text= openAlertsList!!.get(2)["featureName"].toString()
                        }
                        if(openAlertsList!!.get(0).containsKey("outlierCount")&& openAlertsList!!.get(2)["outlierCount"]!=null){
                            mBinding!!.toolCount3.text= openAlertsList!!.get(2)["outlierCount"].toString()
                        }
                    }




                    if(Utils.hasIndex(0, acknowledementList!!)){
                        if(openAlertsList!!.get(0).containsKey("featureName")&& acknowledementList!!.get(0)["featureName"]!=null){
                            mBinding!!.acknowladgedTitle1.text= acknowledementList!!.get(0)["featureName"].toString()
                        }
                        if(openAlertsList!!.get(0).containsKey("outlierCount")&& acknowledementList!!.get(0)["outlierCount"]!=null){
                            mBinding!!.acknowladgedCount1.text= acknowledementList!!.get(0)["outlierCount"].toString()
                        }
                    }

                    if(Utils.hasIndex(1, acknowledementList!!)){
                        if(acknowledementList!!.get(1).containsKey("featureName")&& acknowledementList!!.get(1)["featureName"]!=null){
                            mBinding!!.systemTitle2.text= acknowledementList!!.get(1)["featureName"].toString()
                        }
                        if(acknowledementList!!.get(1).containsKey("outlierCount")&& acknowledementList!!.get(1)["outlierCount"]!=null){
                            mBinding!!.systemCount2.text= acknowledementList!!.get(1)["outlierCount"].toString()
                        }
                    }


                    if(Utils.hasIndex(2, acknowledementList!!)){
                        if(acknowledementList!!.get(2).containsKey("featureName")&& acknowledementList!!.get(2)["featureName"]!=null){
                            mBinding!!.acknowladgedTitle3.text= acknowledementList!!.get(2)["featureName"].toString()
                        }
                        if(acknowledementList!!.get(2).containsKey("outlierCount")&& acknowledementList!!.get(2)["outlierCount"]!=null){
                            mBinding!!.acknowladgedCount3.text= acknowledementList!!.get(2)["outlierCount"].toString()
                        }
                    }



                    if(Utils.hasIndex(0, acknowledementSLAList!!)){
                        if(acknowledementSLAList!!.get(0).containsKey("featureName")&& acknowledementSLAList!!.get(0)["featureName"]!=null){
                            mBinding!!.openchargeTitle1.text= acknowledementSLAList!!.get(0)["featureName"].toString()
                        }
                        if(openAlertsList!!.get(0).containsKey("outlierCount")&& acknowledementSLAList!!.get(0)["outlierCount"]!=null){
                            mBinding!!.metCount1.text= acknowledementSLAList!!.get(0)["outlierCount"].toString()
                        }
                    }

                    if(Utils.hasIndex(1, acknowledementSLAList!!)){
                        if(acknowledementSLAList!!.get(1).containsKey("featureName")&& acknowledementSLAList!!.get(1)["featureName"]!=null){
                            mBinding!!.opnechargeTitle3.text= acknowledementSLAList!!.get(1)["featureName"].toString()
                        }
                        if(acknowledementSLAList!!.get(1).containsKey("outlierCount")&& acknowledementSLAList!!.get(1)["outlierCount"]!=null){
                            mBinding!!.missedCount3.text= acknowledementSLAList!!.get(1)["outlierCount"].toString()
                        }
                    }
                    if(Utils.hasIndex(0, statusList!!)){
                        if(statusList!!.get(0).containsKey("featureName")&& statusList!!.get(0)["featureName"]!=null){
                            mBinding!!.closedTitle1.text= statusList!!.get(0)["featureName"].toString()
                        }
                        if(statusList!!.get(0).containsKey("outlierCount")&& statusList!!.get(0)["outlierCount"]!=null){
                            mBinding!!.closedCount1.text= statusList!!.get(0)["outlierCount"].toString()
                        }
                    }
                    if(Utils.hasIndex(1, statusList!!)){
                        if(statusList!!.get(1).containsKey("featureName")&& statusList!!.get(1)["featureName"]!=null){
                            mBinding!!.openTitle3.text= statusList!!.get(1)["featureName"].toString()
                        }
                        if(statusList!!.get(1).containsKey("outlierCount")&& statusList!!.get(1)["outlierCount"]!=null){
                            mBinding!!.openCount3.text= statusList!!.get(1)["outlierCount"].toString()
                        }
                    }
                    if(Utils.hasIndex(0, averageTimeList!!)){
                        if(averageTimeList!!.get(0).containsKey("featureName")&& averageTimeList!!.get(0)["featureName"]!=null){
                            mBinding!!.acknowladgementTitle1.text= averageTimeList!!.get(0)["featureName"].toString()
                        }
                        if(statusList!!.get(0).containsKey("value")&& averageTimeList!!.get(0)["value"]!=null){
                            mBinding!!.acknowladgementCount1.text= averageTimeList!!.get(0)["value"].toString()
                        }
                    }
                    if(Utils.hasIndex(1, averageTimeList!!)){
                        if(averageTimeList!!.get(1).containsKey("featureName")&& averageTimeList!!.get(1)["featureName"]!=null){
                            mBinding!!.resoluvationTitle3.text= averageTimeList!!.get(1)["featureName"].toString()
                        }
                        if(averageTimeList!!.get(1).containsKey("value")&& averageTimeList!!.get(1)["value"]!=null){
                            mBinding!!.resoluvationCount3.text= averageTimeList!!.get(1)["value"].toString()
                        }
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




    fun setData(commonBean: CommonBean,title:String,dateStr:String) {
        this.commonBean = commonBean
        this.title = title
        this.dateStr = dateStr

    }



}
