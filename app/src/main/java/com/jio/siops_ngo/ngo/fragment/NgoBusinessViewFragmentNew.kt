package com.jio.siops_ngo.ngo.fragment

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.jio.jioinfra.bean.CommonBean
import com.jio.jioinfra.network.business.BaseCoroutines
import com.jio.jioinfra.utilities.Busicode
import com.jio.jioinfra.utilities.MyConstants
import com.jio.myjio.bean.CoroutinesResponse
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.databinding.NgoBusinessViewBinding
import com.jio.siops_ngo.adapter.NgoActivationsAdapter
import com.jio.siops_ngo.adapter.NgoMacdTrendAdapter
import com.jio.siops_ngo.adapter.NgoMnpTrendAdapter
import com.jio.siops_ngo.adapter.UsefulLinksAdapter
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.T
import com.jio.siops_ngo.utilities.Utils
import kotlinx.coroutines.*

class NgoBusinessViewFragmentNew : Fragment() {
    var mBinding: NgoBusinessViewBinding? = null
    var activationsDataList: MutableList<Map<String, Any>>? = null
    var activationsTrendDataList: MutableList<Map<String, Any>>? = null
    var rechargeDataList: MutableList<Map<String, Any>>? = null
    var rechargeTrendDataList: MutableList<Map<String, Any>>? = null
    var portInPortOutTrendDataList: MutableList<Map<String, Any>>? = null
    var macdTrendDataList: MutableList<Map<String, Any>>? = null
    var activationResponseSuccess: Int? = -1
    var rechargeActivationResponseSuccess: Int? = -1
    var portInOutResponseSuccess: Int? = -1
    var macdResponseSuccess: Int? = -1
    var mHandler: Handler? = null
     var mRunnable: Runnable? = null
    var commonBean: CommonBean? = null
    var busicode: String? = null

    var isDataLoading: Boolean? = false;
    var usefulLinksBusiness: List<Map<String, Any>>? = null

    companion object {
        fun newInstance() = NgoBusinessViewFragmentNew()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.ngo_business_view, container, false)

        mHandler = Handler()
        if(activity!=null){
            usefulLinksBusiness = Utils.getListFromJson(activity!!, MyConstants.USEFUL_LINKS_BUSINESS_VIEW)
            /*fetchActivationsData(Busicode.NGOOrderActivation)
            fetchActivationsData(Busicode.NGORechargeActivation)
            fetchActivationsData(Busicode.NGOPortInPortOutCountTrends)
            fetchActivationsData(Busicode.NGOMACDDetails)*/
        }
        mBinding!!.cnstrntLActivations.visibility = View.GONE
        mBinding!!.cnstrntLRecharge.visibility = View.GONE
        mBinding!!.cnstrntLMnp.visibility = View.GONE
        mBinding!!.cnstrntLMacd.visibility = View.GONE

        mBinding!!.itemsswipetorefresh.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(
                (activity as MainActivity),
                R.color.toolbar_bg
            )
        )

        when (busicode) {
            Busicode.NGOOrderActivation -> fetchActivationsData(Busicode.NGOOrderActivation)
            Busicode.NGORechargeActivation -> fetchActivationsData(Busicode.NGORechargeActivation)
            Busicode.NGOPortInPortOutCountTrends -> fetchActivationsData(Busicode.NGOPortInPortOutCountTrends)
            Busicode.NGOMACDDetails -> fetchActivationsData(Busicode.NGOMACDDetails)
        }
        mBinding!!.itemsswipetorefresh.setColorSchemeColors(Color.WHITE)
        mBinding!!.itemsswipetorefresh.setOnRefreshListener {
            if (!isDataLoading!!) {
                when (busicode) {
                    Busicode.NGOOrderActivation -> fetchActivationsData(Busicode.NGOOrderActivation)
                    Busicode.NGORechargeActivation -> fetchActivationsData(Busicode.NGORechargeActivation)
                    Busicode.NGOPortInPortOutCountTrends -> fetchActivationsData(Busicode.NGOPortInPortOutCountTrends)
                    Busicode.NGOMACDDetails -> fetchActivationsData(Busicode.NGOMACDDetails)
                }
            } else {
                mBinding!!.itemsswipetorefresh.isRefreshing = false
            }
        }

        var adapter = UsefulLinksAdapter(
            usefulLinksBusiness!!,
            activity as MainActivity,
            commonBean!!
        )

//        val gridLayoutManagerUsefulLinks = GridLayoutManager(activity, 3)

        var horizonalLayoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )


        mBinding!!.usefulLinksRv!!.layoutManager =
            horizonalLayoutManager
        mBinding!!.usefulLinksRv!!.itemAnimator =
            DefaultItemAnimator()

        mBinding!!.usefulLinksRv!!.adapter = adapter



        return mBinding!!.root
    }


    fun fetchActivationsData(busicode: String) {
        (activity as MainActivity).showProgressBar()
        isDataLoading = true
        val requestBody = HashMap<String, Any>()

        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
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

                    when (busicode) {
                        Busicode.NGOOrderActivation -> activationResponseSuccess = 0
                        Busicode.NGORechargeActivation -> rechargeActivationResponseSuccess = 0
                        Busicode.NGOPortInPortOutCountTrends -> portInOutResponseSuccess = 0
                        Busicode.NGOMACDDetails -> macdResponseSuccess = 0
                    }

                    filterData(response, busicode)
                } else if (response!!.errorCode != null && response!!.errorCode.equals(MappActor.VERSION_SESSION_INVALID)){
                    when (busicode) {
                        Busicode.NGOOrderActivation -> activationResponseSuccess = 1
                        Busicode.NGORechargeActivation -> rechargeActivationResponseSuccess = 1
                        Busicode.NGOPortInPortOutCountTrends -> portInOutResponseSuccess = 1
                        Busicode.NGOMACDDetails -> macdResponseSuccess = 1
                    }
                    (activity as MainActivity).showDialogForSessionExpired((activity as MainActivity).resources.getString(R.string.session_expired), (activity as MainActivity))
                } else if (response!!.errorMsg != null) {
                    when (busicode) {
                        Busicode.NGOOrderActivation -> activationResponseSuccess = 1
                        Busicode.NGORechargeActivation -> rechargeActivationResponseSuccess = 1
                        Busicode.NGOPortInPortOutCountTrends -> portInOutResponseSuccess = 1
                        Busicode.NGOMACDDetails -> macdResponseSuccess = 1
                    }
                    /*if (activationResponseSuccess!! == 1 && rechargeActivationResponseSuccess!! == 1 && portInOutResponseSuccess!! == 1
                        && macdResponseSuccess!! == 1){
                        T.show(activity, response!!.errorMsg!!, 0)
                    }*/
                    T.show(activity, response!!.errorMsg!!, 0)

                } else {
                    when (busicode) {
                        Busicode.NGOOrderActivation -> activationResponseSuccess = 1
                        Busicode.NGORechargeActivation -> rechargeActivationResponseSuccess = 1
                        Busicode.NGOPortInPortOutCountTrends -> portInOutResponseSuccess = 1
                        Busicode.NGOMACDDetails -> macdResponseSuccess = 1
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
                try {
                    if (activity != null) {
                        (activity!! as MainActivity).hideProgressBar()
                    }
                    if (busicode.equals(Busicode.NGOOrderActivation)) {


                        mBinding!!.cnstrntLActivations.visibility = View.VISIBLE
                        mBinding!!.ngoActivationsTrendRv.visibility = View.VISIBLE
                        if (msg.containsKey("orderStages")) {
                            if (msg["orderStages"] != null) {

                                activationsDataList =
                                    msg["orderStages"] as MutableList<Map<String, Any>>
                                var map1: Map<String, Any> = mutableMapOf(
                                    "stage" to "Stage",
                                    "todayCount" to "Today",
                                    "yesCount" to "Yesterday"
                                )
                                activationsDataList!!.add(0, map1!!)
                                var adapter =
                                    NgoActivationsAdapter(
                                        activationsDataList!!,
                                        activity as MainActivity?
                                    ,Busicode.NGOOrderActivation)
                                mBinding!!.ngoActivationsRv.layoutManager =
                                    LinearLayoutManager(activity)
                                mBinding!!.ngoActivationsRv.itemAnimator = DefaultItemAnimator()
                                mBinding!!.ngoActivationsRv!!.adapter = adapter
                                adapter!!.notifyDataSetChanged()
                            }
                        }
                        if (msg.containsKey("orderTrends")) {
                            if (msg["orderTrends"] != null) {
                                activationsTrendDataList =
                                    msg["orderTrends"] as MutableList<Map<String, Any>>
                                var mapTrend: Map<String, Any> = mutableMapOf(
                                    "ddate" to "Date",
                                    "entered" to "Entered",
                                    "activated" to "Activated",
                                    "rejected" to "Rejected",
                                    "wip" to "AO\nPending"
                                )
                                activationsTrendDataList!!.add(0, mapTrend!!)

                                if (activationsTrendDataList != null) {

                                    var adapter =
                                        NgoMnpTrendAdapter(
                                            activationsTrendDataList!!,
                                            activity as MainActivity?, Busicode.NGOOrderActivation
                                        )
                                    mBinding!!.ngoActivationsTrendRv.layoutManager =
                                        LinearLayoutManager(activity!!)
                                    mBinding!!.ngoActivationsTrendRv.itemAnimator = DefaultItemAnimator()
                                    mBinding!!.ngoActivationsTrendRv!!.adapter = adapter
                                    adapter!!.notifyDataSetChanged()
                                    mBinding!!.ngoActivationsTrendRv!!.visibility = View.VISIBLE
                                }

                            }


                        }

                        /*if (activationResponseSuccess!! > -1 && rechargeActivationResponseSuccess!! > -1 && portInOutResponseSuccess!! > -1
                            && macdResponseSuccess!! > -1
                        ) {
                            if (activity != null) {
                                (activity!! as MainActivity).hideProgressBar()
                            }
                            showUi()
                            autoRefresh()

                        }*/

                    } else if (busicode.equals(Busicode.NGORechargeActivation)) {
                        if (msg.containsKey("rechargeStages")) {
                            if (msg["rechargeStages"] != null) {
                                mBinding!!.cnstrntLRecharge.visibility = View.VISIBLE
                                mBinding!!.ngoRechargeTrendRv.visibility = View.VISIBLE
                                rechargeDataList =
                                    msg["rechargeStages"] as MutableList<Map<String, Any>>
                                var map1: Map<String, Any> = mutableMapOf(
                                    "stage" to "Stage",
                                    "todayCount" to "Today",
                                    "yesCount" to "Yesterday"
                                )
                                rechargeDataList!!.add(0, map1!!)
                                var adapter =
                                    NgoActivationsAdapter(
                                        rechargeDataList!!,
                                        activity as MainActivity?,
                                        Busicode.NGOActivationJourney
                                    )
                                mBinding!!.ngoRechargeRv.layoutManager =
                                    LinearLayoutManager(activity)
                                mBinding!!.ngoRechargeRv.itemAnimator = DefaultItemAnimator()
                                mBinding!!.ngoRechargeRv!!.adapter = adapter
                                adapter!!.notifyDataSetChanged()
                            }
                        }
                        if (msg.containsKey("rechargeTrends")) {
                            if (msg["rechargeTrends"] != null) {
                                rechargeTrendDataList =
                                    msg["rechargeTrends"] as MutableList<Map<String, Any>>
                                var mapRechargeTrend: Map<String, Any> = mutableMapOf(
                                    "rdate" to "Date",
                                    "initiated" to "Initiated",
                                    "paymentfailed" to "Payment\nAborted",
                                    "paymentsuccess" to "Payment\nSuccess",
                                    "succes" to "Recharge\nSuccess"
                                )
                                rechargeTrendDataList!!.add(0, mapRechargeTrend!!)


                                if (rechargeTrendDataList != null) {

                                    var adapter =
                                        NgoMnpTrendAdapter(
                                            rechargeTrendDataList!!,
                                            activity as MainActivity?, Busicode.NGORechargeActivation
                                        )
                                    mBinding!!.ngoRechargeTrendRv.layoutManager =
                                        LinearLayoutManager(activity!!)
                                    mBinding!!.ngoRechargeTrendRv.itemAnimator = DefaultItemAnimator()
                                    mBinding!!.ngoRechargeTrendRv!!.adapter = adapter
                                    adapter!!.notifyDataSetChanged()
                                    mBinding!!.ngoRechargeTrendRv!!.visibility = View.VISIBLE
                                }
                            }
                        }
                        /*if (activationResponseSuccess!! > -1 && rechargeActivationResponseSuccess!! > -1 && portInOutResponseSuccess!! > -1
                            && macdResponseSuccess!! > -1
                        ) {
                            if (activity != null) {
                                (activity!! as MainActivity).hideProgressBar()
                            }
                            showUi()
                            autoRefresh()
                        }*/
                    } else if (busicode.equals(Busicode.NGOPortInPortOutCountTrends)) {
                        mBinding!!.cnstrntLMnp.visibility = View.VISIBLE
                        if (msg.containsKey("todayPortInTotal") && msg["todayPortInTotal"] != null) {
//                            mBinding!!.txtMnpCount1.text = msg["todayPortInTotal"].toString()
                            mBinding!!.txtMnpCount1.text = Utils.getCommaSeparatedCount(msg["todayPortInTotal"] as Int)
                        }
                        if (msg.containsKey("todatPortOutTotalCompleted") && msg["todatPortOutTotalCompleted"] != null) {
//                            mBinding!!.txtMnpCount2.text = msg["todatPortOutTotalCompleted"].toString()
                            mBinding!!.txtMnpCount2.text = Utils.getCommaSeparatedCount(msg["todatPortOutTotalCompleted"] as Int)
                        }


                        if (msg.containsKey("yesPortInTotal") && msg["yesPortInTotal"] != null) {
                            /*mBinding!!.txtMnpSubTitle1.text =
                                activity!!.resources.getString(R.string.yesterday) + ": " + msg["yesPortInTotal"].toString()*/

                            mBinding!!.txtMnpSubTitle1.text =
                                activity!!.resources.getString(R.string.yesterday) + ": " + Utils.getCommaSeparatedCount(msg["yesPortInTotal"] as Int)
                        }

                        if (msg.containsKey("yesPortOutTotalCompleted") && msg["yesPortOutTotalCompleted"] != null) {
                            /*mBinding!!.txtMnpSubTitle2.text =
                                activity!!.resources.getString(R.string.yesterday) + ": " + msg["yesPortOutTotalCompleted"].toString()*/

                            mBinding!!.txtMnpSubTitle2.text =
                                activity!!.resources.getString(R.string.yesterday) + ": " + Utils.getCommaSeparatedCount(msg["yesPortOutTotalCompleted"] as Int)
                        }

                        if (msg.containsKey("portinoutListTrends") && msg["portinoutListTrends"] != null) {


                            portInPortOutTrendDataList =
                                msg["portinoutListTrends"] as MutableList<Map<String, Any>>
                            var mapPortInPortOutTrend: Map<String, Any> = mutableMapOf(
                                "ddate" to "Date",
                                "pintotal" to "Port In",
                                "pinact" to "Completed",
                                "pouttotal" to "Port Out",
                                "poutact" to "Completed"
                            )
                            portInPortOutTrendDataList!!.add(0, mapPortInPortOutTrend!!)

                            var adapter =
                                NgoMnpTrendAdapter(
                                    portInPortOutTrendDataList!!,
                                    activity as MainActivity?, Busicode.NGOPortInPortOutCountTrends
                                )
                            mBinding!!.ngoMnpTrendRv.layoutManager =
                                LinearLayoutManager(activity!!)
                            mBinding!!.ngoMnpTrendRv.itemAnimator = DefaultItemAnimator()
                            mBinding!!.ngoMnpTrendRv!!.adapter = adapter
                            adapter!!.notifyDataSetChanged()
                            mBinding!!.ngoMnpTrendRv!!.visibility = View.VISIBLE

                        }

                        /*rechargeTrendDataList =
                            msg["rechargeTrends"] as MutableList<Map<String, Any>>
                        var mapRechargeTrend: Map<String, Any> = mutableMapOf(
                            "rdate" to "Date",
                            "initiated" to "Attempted",
                            "paymentfailed" to "Payment\nAborted",
                            "paymentsuccess" to "Payment\nSuccess",
                            "succes" to "Recharge\nSuccess"
                        )
                        rechargeTrendDataList!!.add(0, mapRechargeTrend!!)*/

                        /*if (activationResponseSuccess!! > -1 && rechargeActivationResponseSuccess!! > -1 && portInOutResponseSuccess!! > -1
                            && macdResponseSuccess!! > -1
                        ) {
                            if (activity != null) {
                                (activity!! as MainActivity).hideProgressBar()
                            }
                            showUi()

                            autoRefresh()
                        }*/

                    } else if (busicode.equals(Busicode.NGOMACDDetails)) {
                        mBinding!!.cnstrntLMacd.visibility = View.VISIBLE
                        if (msg.containsKey("simChangeCount") && msg["simChangeCount"] != null) {
//                            mBinding!!.txtMacdCount1.text = msg["simChangeCount"].toString()
                            mBinding!!.txtMacdCount1.text = Utils.getCommaSeparatedCount(msg["simChangeCount"] as Int)
                        }

                        if (msg.containsKey("irWatchCount") && msg["irWatchCount"] != null) {
//                            mBinding!!.txtMacdCount2.text = msg["irWatchCount"].toString()
                            mBinding!!.txtMacdCount2.text = Utils.getCommaSeparatedCount(msg["irWatchCount"] as Int)
                        }

                        if (msg.containsKey("appleIWatchCount") && msg["appleIWatchCount"] != null) {
//                            mBinding!!.txtMacdCount3.text = msg["appleIWatchCount"].toString()
                            mBinding!!.txtMacdCount3.text = Utils.getCommaSeparatedCount(msg["appleIWatchCount"] as Int)
                        }

                        if (msg.containsKey("macdHeaderList") && msg["macdHeaderList"] != null) {

                            macdTrendDataList =
                                msg["macdHeaderList"] as MutableList<Map<String, Any>>
                            var mapMacdTrend: Map<String, Any> = mutableMapOf(
                                "ddate" to "Date",
                                "simChangeCount" to "Sim Change",
                                "irWatchCount" to "International\nRoaming",
                                "appleIWatchCount" to "Apple Watch"
                            )
                            macdTrendDataList!!.add(0, mapMacdTrend!!)

                            var adapter =
                                NgoMacdTrendAdapter(
                                    macdTrendDataList!!,
                                    activity as MainActivity?
                                )
                            mBinding!!.ngoMacdRv.layoutManager =
                                LinearLayoutManager(activity!!)
                            mBinding!!.ngoMacdRv.itemAnimator = DefaultItemAnimator()
                            mBinding!!.ngoMacdRv!!.adapter = adapter
                            adapter!!.notifyDataSetChanged()
                            mBinding!!.ngoMacdRv!!.visibility = View.VISIBLE
                        }

                        /*if (activationResponseSuccess!! > -1 && rechargeActivationResponseSuccess!! > -1 && portInOutResponseSuccess!! > -1
                            && macdResponseSuccess!! > -1
                        ) {
                            if (activity != null) {
                                (activity!! as MainActivity).hideProgressBar()
                            }

                            showUi()

                            autoRefresh()
                        }*/
                    }
                    mBinding!!.itemsswipetorefresh.isRefreshing = false
                    isDataLoading = false
                } catch (e: Exception) {
                    isDataLoading = false
                    MyExceptionHandler.handle(e)
                }
            }
            // val responsePayload = msg["responsePayload"] as HashMap<String, Any>
            //val listData = responsePayload["applications"] as List<Map<String, Any>>
            /*listData as MutableList
             listData.addAll(responsePayload["applications"] as List<Map<String, Any>>)
*/

        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
            T.show(activity, activity!!.getString(R.string.something_went_wrong), 0)
        }
    }


    /*fun inflateUI(ngoJourneyDataList: HashMap<String, Any>, busicode: String){

        if(busicode.equals(Busicode.NGOActivationJourney)){

            if(ngoJourneyDataList.containsKey("todayCompletedCount")){
                mBinding!!.txtActCount1.text = ngoJourneyDataList["todayCompletedCount"].toString()
            }

            if(ngoJourneyDataList.containsKey("yesterdayCompletedCount")){
                mBinding!!.txtActCount2.text = ngoJourneyDataList["yesterdayCompletedCount"].toString()
            }

            mBinding!!.cnstrntLActivations.visibility = View.VISIBLE

        }else if(busicode.equals(Busicode.NGOMNPPortInJourney)){

            if(ngoJourneyDataList.containsKey("todayTotalCount")){
                mBinding!!.txtMnpCount1.text = ngoJourneyDataList["todayTotalCount"].toString()
            }
            if(ngoJourneyDataList.containsKey("yesterdayTotalCount")){
                mBinding!!.txtMnpCount2.text = ngoJourneyDataList["yesterdayTotalCount"].toString()
            }

            mBinding!!.cnstrntLMnp.visibility = View.VISIBLE
        }

    }*/


    fun autoRefresh() {


        if (isVisible) {


            mRunnable = Runnable {
                // Do something here
                if (isVisible) {

                    if (!isDataLoading!!) {

                        activationResponseSuccess = -1
                        rechargeActivationResponseSuccess = -1
                        portInOutResponseSuccess = -1
                        macdResponseSuccess = -1

                        fetchActivationsData(Busicode.NGOOrderActivation)
                        fetchActivationsData(Busicode.NGORechargeActivation)
                        fetchActivationsData(Busicode.NGOPortInPortOutCountTrends)
                        fetchActivationsData(Busicode.NGOMACDDetails)

                        // Schedule the task to repeat after 1 second
                        mHandler!!.postDelayed(
                            mRunnable, // Runnable
                            240000 // Delay in milliseconds
                        )
                    }
                }
            }

            // Schedule the task to repeat after 1 second
            mHandler!!.postDelayed(
                mRunnable, // Runnable
                240000 // Delay in milliseconds
            )


        }

    }

    override fun onPause() {
        super.onPause()
        if (!isVisible && mHandler!! !=null && mRunnable!=null) {
            mHandler!!.removeCallbacks(mRunnable)
            mHandler!!.removeCallbacksAndMessages(mRunnable)
        }
    }

    fun setData(commonBean: CommonBean, busicode:String) {
        this.commonBean = commonBean
        this.busicode = busicode
    }

    fun showUi() {

        if (activationResponseSuccess == 0) {
            mBinding!!.cnstrntLActivations.visibility = View.VISIBLE
        } else {
            mBinding!!.cnstrntLActivations.visibility = View.GONE
        }

        if (rechargeActivationResponseSuccess == 0) {
            mBinding!!.cnstrntLRecharge.visibility = View.VISIBLE
        } else {
            mBinding!!.cnstrntLRecharge.visibility = View.GONE
        }
        if (portInOutResponseSuccess == 0) {
            mBinding!!.cnstrntLMnp.visibility = View.VISIBLE
        } else {
            mBinding!!.cnstrntLMnp.visibility = View.GONE
        }
        if (macdResponseSuccess == 0) {
            mBinding!!.cnstrntLMacd.visibility = View.VISIBLE
        } else {
            mBinding!!.cnstrntLMacd.visibility = View.GONE
        }
        mBinding!!.cnstrntLUsefulLinks.visibility = View.VISIBLE

        isDataLoading = false
    }
}