package com.jio.siops_ngo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.bean.CommonBean
import com.jio.jioinfra.network.business.BaseCoroutines
import com.jio.jioinfra.utilities.MyConstants
import com.jio.myjio.bean.CoroutinesResponse
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.adapter.NgoAlarmSubAdapter
import com.jio.siops_ngo.databinding.FragmentDashboardBinding
import com.jio.siops_ngo.databinding.FragmentNewLoginBinding
import com.jio.siops_ngo.databinding.FragmentNgoOpenItemDetailsBinding
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.T
import kotlinx.coroutines.*
import java.lang.Exception

class NgoOpenAlertsDetailsFragment : androidx.fragment.app.Fragment() {

    var mBinding: FragmentNgoOpenItemDetailsBinding? = null
    var commonBean: CommonBean? = null
    var busicode: String? = null
    var dataListNgoItem: ArrayList<Map<String, Any>>? = null
    var dataMap: Map<String, Any>? = null

    companion object {
        fun newInstance() = NgoOpenAlertsDetailsFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_ngo_open_item_details,
            container,
            false
        )

        return mBinding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dataMap = commonBean!!.`object` as Map<String, Any>
        if (dataMap!!.containsKey("featureName")) {
            mBinding!!.txtDashboardSubtitle1.text = dataMap!!.get("featureName") as String
        }
        if (dataMap!!.containsKey("outlierCount")) {
            mBinding!!.countData.text = dataMap!!.get("outlierCount").toString()
        }
        fetchDashboardDbData()

    }


    fun fetchDashboardDbData() {
        (activity as MainActivity).showProgressBar()
        val requestBody = HashMap<String, Any>()

        requestBody["outlierType"] = dataMap!!["featureName"] as String
        requestBody["appRoleCode"] =
            PreferenceUtility.getString(activity, MyConstants.APP_CODE_NGO, "")
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "userInfo"
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
                if (activity != null) {
                    (activity!! as MainActivity).hideProgressBar()
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

        try {
            val msg = mCoroutinesResponse.responseEntity as HashMap<String, Any>


            if (msg != null) {
                try {


                    dataListNgoItem = msg["mainOutlier"] as ArrayList<Map<String, Any>>


                    /*var adapter = NgoAlarmSubAdapter(dataListInfraItem!!, activity as MainActivity?)
                    holder.detailsList!!.layoutManager = LinearLayoutManager(activity)
                    holder!!.detailsList!!.itemAnimator = DefaultItemAnimator()
                    holder!!.detailsList!!.adapter = adapter
                    adapter!!.notifyDataSetChanged()*/


                    mBinding!!.rvParent.apply {
                        layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                        adapter = NgoAlarmSubAdapter(dataListNgoItem!!, activity as MainActivity?)
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
            T.show(activity, activity!!.getString(R.string.something_went_wrong), 0)
        }
    }


    fun setData(commonBean: CommonBean, busicode: String) {
        this.commonBean = commonBean
        this.busicode = busicode

    }


}