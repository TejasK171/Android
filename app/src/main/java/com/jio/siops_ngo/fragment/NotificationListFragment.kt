package com.jio.siops_ngo.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.jio.jioinfra.bean.CommonBean
import com.jio.jioinfra.network.business.BaseCoroutines
import com.jio.jioinfra.ui.dashboard.adapter.NotificationListAdapter
import com.jio.jioinfra.utilities.Busicode
import com.jio.jioinfra.utilities.MyConstants
import com.jio.myjio.bean.CoroutinesResponse
import com.jio.siops_ngo.MainActivity

import com.jio.siops_ngo.R
import com.jio.siops_ngo.databinding.FragmentInfraNotificationBinding
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.T
import kotlinx.coroutines.*

/**
 * A simple [Fragment] subclass.
 */
class NotificationListFragment : Fragment() {


    private var commonBean: CommonBean? = null
  //  private var stateMpJcBean: StateMpJcBean? = null
    var mBinding: FragmentInfraNotificationBinding? = null
    var commonBeanMap: Map<String, Any>? = null
    var commonActionStr: String? = null

    companion object {
        fun newInstance() = NotificationListFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_infra_notification, container, false)
      //  mBinding!!.welcome!!.visibility = View.GONE
        return mBinding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        if(commonBean!=null){
//            commonActionStr = commonBean!!.getmCommonAction()
//        }
        //   commonBeanMap = commonBean!!.`object` as Map<String, Any>
//        if(commonActionStr.equals(MyConstants.STATE_SELECTION)) {
//            stateMpJcBean = StateMpJcBean()
//            var outlierIdSelected = commonBeanMap!!["id"] as String
//            PreferenceUtility.addString(activity, MyConstants.OUTLIER_ID_STATE_MP_JC, outlierIdSelected)
//            fetchCircleSummaryLst(MyConstants.ON_CLICK_CIRCLE_BusiCODE, "", "")
//        }else{
//
//            if(commonActionStr.equals(MyConstants.MP_SELECTION)){
////                var circleName = commonBeanMap!!["keyName"] as String
//                var circleName = stateMpJcBean!!.getCircle()
//                fetchCircleSummaryLst(MyConstants.ON_CLICK_MP_BusiCODE, circleName, "")
//            }else if(commonActionStr.equals(MyConstants.JC_SELECTION)){
////                var mpName = commonBeanMap!!["keyName"] as String
//                var mpName = stateMpJcBean!!.getMp()
////                var circleName = PreferenceUtility.getString(activity,MyConstants.CIRCLE_SELECTED,"")
//                var circleName = stateMpJcBean!!.getCircle()
//                fetchCircleSummaryLst(MyConstants.ON_CLICK_JC_BusiCODE, circleName, mpName)
//            }
//        }

        fetchNotificationList()
    }

    fun fetchNotificationList() {
        (activity as MainActivity).showProgressBar()

        val requestBody = HashMap<String, Any>()

        // requestBody["outlierId"] = PreferenceUtility.getString(activity, MyConstants.OUTLIER_ID_STATE_MP_JC, "")
        //  requestBody["appRoleCode"] = PreferenceUtility.getString(activity, MyConstants.APP_ROLE_CODE, "")
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "message"
//        requestBody["toggleView"] = PreferenceUtility.getString(activity, MyConstants.USER_TYPE, "")
        requestBody["toggleView"] = "NGO"



        CoroutineScope(Dispatchers.IO).launch {

            var job = async { BaseCoroutines().fetchData(requestBody, Busicode.NotificationList, activity as MainActivity) }
            withContext(Dispatchers.Main)
            {

                var response = job.await()
                if (activity != null) {
                    (activity!! as MainActivity).hideProgressBar()
                }


                if (response!!.responseEntity != null && response.status == MappActor.STATUS_OK) {
                    filterData(response)
                    PreferenceUtility.addString(activity, MyConstants.NOTIFICATION_COUNT, "0" )
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

    fun setData(commonBean: CommonBean) {
        this.commonBean = commonBean
    }
//
//    fun setCircleMpJcBean(stateMpJcBean: StateMpJcBean) {
//        this.stateMpJcBean = stateMpJcBean
//    }

    fun filterData(mCoroutinesResponse: CoroutinesResponse) {

        try {
            val msg = mCoroutinesResponse.responseEntity as HashMap<String, Any>


            if (msg != null) {
                try {


                    var commonBeanMainServices: CommonBean = CommonBean()

                    if (msg["list"] != null) {

                        commonBeanMainServices.`object` = msg["list"] as List<HashMap<String, Any>>

                        var adapter = NotificationListAdapter(commonBeanMainServices.`object` as List<Map<String, Any>>, activity as MainActivity?)
                        mBinding!!.mainServicesRecyclerView.layoutManager = LinearLayoutManager(activity)
                        mBinding!!.mainServicesRecyclerView.itemAnimator = DefaultItemAnimator()
                        mBinding!!.mainServicesRecyclerView!!.adapter = adapter
                        adapter!!.notifyDataSetChanged()
                    }


                } catch (e: Exception) {
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
            T.show(activity, getString(R.string.something_went_wrong), 0)
        }
    }


}
