package com.jio.siops_ngo.approvals.fragment


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
import com.jio.jioinfra.utilities.Busicode
import com.jio.jioinfra.utilities.MyConstants
import com.jio.myjio.bean.CoroutinesResponse
import com.jio.siops.ngo.adapter.TimeTrackingViewSimilarAdapter
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.databinding.FragmentTimeTrackingViewSimilarBinding
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.T
import kotlinx.coroutines.*

/**
 * A simple [Fragment] subclass.
 */
class TimeTrackingExclusionFragment : Fragment() {
    var mBinding: FragmentTimeTrackingViewSimilarBinding? = null
    var listData: ArrayList<Map<String, Any>>? = null
    var jobRole: String? = null
    var platform: String? = null

    companion object {
        fun newInstance() = TimeTrackingExclusionFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_time_tracking_view_similar,
            container,
            false
        )
        return mBinding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if(jobRole !=null){
            mBinding!!.txtJobroleValue.text = jobRole
        }

        if(platform !=null){
            mBinding!!.txtPlatformValue.text = platform
        }

        fetchData()
    }

    fun fetchData() {
        (activity as MainActivity).showProgressBar()

        val requestBody = HashMap<String, Any>()
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "userInfo"
        requestBody["platform"] = platform!!
        requestBody["jobRole"] = jobRole!!
        requestBody["relatedTo"] = "TTEXCLUSIONREPORTTODL"
        //   requestBody["appRoleCode"] = "741"
        CoroutineScope(Dispatchers.IO).launch {

            var job = async {
                BaseCoroutines().fetchData(
                    requestBody,
                    Busicode.ApprovalDetail,
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
                    listData = msg["list"] as ArrayList<Map<String, Any>>
                    var adapter = TimeTrackingViewSimilarAdapter(activity as MainActivity?, listData!!)
                    mBinding!!.recyclerviewList.layoutManager = LinearLayoutManager(activity)
                    mBinding!!.recyclerviewList.itemAnimator = DefaultItemAnimator()
                    mBinding!!.recyclerviewList!!.adapter = adapter
                    adapter!!.notifyDataSetChanged()


                } catch (e: Exception) {
                    MyExceptionHandler.handle(e)
                }
            }
        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
            T.show(activity, getString(R.string.something_went_wrong), 0)
        }
    }

    fun setData(jobRole: String, platform: String) {
        this.jobRole = jobRole
        this.platform = platform

    }
}
