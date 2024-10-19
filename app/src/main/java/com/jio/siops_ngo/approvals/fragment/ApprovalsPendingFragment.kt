package com.jio.siops_ngo.approvals.fragment


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
import com.jio.siops_ngo.databinding.FragmentApprovalsPendingBinding
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.ngo.adapter.ApprovalsPendingListAdapter
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.T
import kotlinx.coroutines.*

/**
 * A simple [Fragment] subclass.
 */
class ApprovalsPendingFragment : Fragment() {
    var mBinding:FragmentApprovalsPendingBinding?=null
    var listData: ArrayList<Map<String, Any>>? = arrayListOf()


    companion object {
        fun newInstance() = ApprovalsPendingFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding =DataBindingUtil.inflate(inflater, R.layout.fragment_approvals_pending, container, false)
        return mBinding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fetchData()
    }

    fun fetchData() {
        listData!!.clear()
        (activity as MainActivity).showProgressBar()

        val requestBody = HashMap<String, Any>()
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "userInfo"
        CoroutineScope(Dispatchers.IO).launch {

            var job = async {
                BaseCoroutines().fetchData(
                    requestBody,
                    Busicode.ApprovalSummary,
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
                    fetchHpsmData()
                } else {
                    T.show(activity, "Something went wrong!", 0)
                    fetchHpsmData()
                }
            }

        }
    }


    fun filterData(mCoroutinesResponse: CoroutinesResponse) {

        try {
            val msg = mCoroutinesResponse.responseEntity as HashMap<String, Any>
            if (msg != null) {
                try {
                    listData = msg["list"] as ArrayList<Map<String, Any>>
                    fetchHpsmData()


                } catch (e: Exception) {
                    MyExceptionHandler.handle(e)
                }
            }
        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
            T.show(activity, getString(R.string.something_went_wrong), 0)
        }
    }

    fun fetchHpsmData() {
        (activity as MainActivity).showProgressBar()

        val requestBody = HashMap<String, Any>()
        requestBody["domainId"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "hpsmRequest"
        //   requestBody["appRoleCode"] = "741"
        CoroutineScope(Dispatchers.IO).launch {

            var job = async {
                BaseCoroutines().fetchData(
                    requestBody,
                    Busicode.HPSMSummaryList,
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
                    filterHpsmData(response)
                } else if (response!!.errorCode != null && response!!.errorCode.equals(MappActor.VERSION_SESSION_INVALID)){
                    (activity as MainActivity).showDialogForSessionExpired((activity as MainActivity).resources.getString(R.string.session_expired), (activity as MainActivity))
                } else if (response!!.errorMsg != null) {
                    T.show(activity, response!!.errorMsg!!, 0)
                    var adapter = ApprovalsPendingListAdapter(activity as MainActivity?,listData!!)
                    mBinding!!.recyclerApprovalsList.layoutManager = LinearLayoutManager(activity)
                    mBinding!!.recyclerApprovalsList.itemAnimator = DefaultItemAnimator()
                    mBinding!!.recyclerApprovalsList!!.adapter = adapter
                    adapter!!.notifyDataSetChanged()
                } else {
                    T.show(activity, "Something went wrong!", 0)
                    var adapter = ApprovalsPendingListAdapter(activity as MainActivity?,listData!!)
                    mBinding!!.recyclerApprovalsList.layoutManager = LinearLayoutManager(activity)
                    mBinding!!.recyclerApprovalsList.itemAnimator = DefaultItemAnimator()
                    mBinding!!.recyclerApprovalsList!!.adapter = adapter
                    adapter!!.notifyDataSetChanged()
                }
            }

        }
    }


    fun filterHpsmData(mCoroutinesResponse: CoroutinesResponse) {

        try {
            val msg = mCoroutinesResponse.responseEntity as HashMap<String, Any>
            if (msg != null) {
                try {
                    var data = msg["dataList"] as ArrayList<Map<String, Any>>

                    for (hpsmData in data) {
                        if (hpsmData.containsKey("code") && hpsmData["code"] != null) {
                            var code =
                                hpsmData["code"] as String
                            if (hpsmData.containsKey("title") && hpsmData["title"] != null) {
                                var title =
                                    hpsmData["title"] as String
                                if (hpsmData.containsKey("count") && hpsmData["count"] != null) {
                                    var count =
                                        hpsmData["count"].toString()
                                    var hpsmMap: Map<String, Any> = mutableMapOf(
                                        "Key" to code,
                                        "Name" to title,
                                        "Count" to count
                                    )
                                    listData!!.add(hpsmMap)
                                }

                                var adapter = ApprovalsPendingListAdapter(activity as MainActivity?,listData!!)
                                mBinding!!.recyclerApprovalsList.layoutManager = LinearLayoutManager(activity)
                                mBinding!!.recyclerApprovalsList.itemAnimator = DefaultItemAnimator()
                                mBinding!!.recyclerApprovalsList!!.adapter = adapter
                                adapter!!.notifyDataSetChanged()


                            }
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
}
