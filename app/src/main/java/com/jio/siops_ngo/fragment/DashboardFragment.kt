package com.jio.siops_ngo.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.bean.CommonBean
import com.jio.jioinfra.network.business.BaseCoroutines
import com.jio.jioinfra.utilities.Busicode
import com.jio.jioinfra.utilities.MyConstants
import com.jio.myjio.bean.CoroutinesResponse
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.adapter.DashboardAdapter
import com.jio.siops_ngo.adapter.UsefulLinksAdapter
import com.jio.siops_ngo.databinding.FragmentDashboardBinding
import com.jio.siops_ngo.databinding.FragmentDashboardInfraBinding
import com.jio.siops_ngo.databinding.FragmentNewLoginBinding
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.T
import com.jio.siops_ngo.utilities.Utils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class DashboardFragment : androidx.fragment.app.Fragment() {

    var mBinding: FragmentDashboardInfraBinding? = null
    var commonBean: CommonBean? = null
    var showLoadingImg: Boolean? = false
    var usefulLinksInfra: List<Map<String, Any>>? = null

    companion object {
        fun newInstance() = DashboardFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard_infra, container, false)

        return mBinding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val txtBadge = (activity as MainActivity).txtBadge

        PreferenceUtility.addString(activity as MainActivity,"noti","0");

        PreferenceUtility.addString(
            activity as MainActivity,
            MyConstants.USER_TYPE,
            "INFRA"
        )
        if (showLoadingImg!!) {
            (activity as MainActivity).rel_toolbar.visibility = View.GONE
            mBinding!!.cnstrntLLoadImg.visibility = View.VISIBLE
            mBinding!!.rvParent.visibility = View.GONE
        } else {
            (activity as MainActivity).rel_toolbar.visibility = View.VISIBLE
            mBinding!!.cnstrntLLoadImg.visibility = View.GONE
            mBinding!!.rvParent.visibility = View.VISIBLE
        }


        var notificationCount: String =
            PreferenceUtility.getString(activity, MyConstants.NOTIFICATION_COUNT, "")

        if (!notificationCount.equals("0")) {
            txtBadge.visibility = View.VISIBLE
            txtBadge.setText(notificationCount)
        } else {
            txtBadge.visibility = View.GONE
        }

        if (activity != null) {
            mBinding!!.itemsswipetorefresh.setProgressBackgroundColorSchemeColor(
                ContextCompat.getColor(
                    (activity as MainActivity),
                    R.color.toolbar_bg
                )
            )
        }
        mBinding!!.itemsswipetorefresh.setColorSchemeColors(Color.WHITE)
        mBinding!!.itemsswipetorefresh.setOnRefreshListener { fetchDashboardData() }




        /*val gson =  Gson();
        val usefulLinksInfraJson = PreferenceUtility.getString(activity!!, MyConstants.USEFUL_LINKS_INFRA, "")

        val typeToken = object : TypeToken<ArrayList<Map<String, Any>>>() {}.type

        usefulLinksInfra = gson.fromJson(usefulLinksInfraJson, typeToken) as ArrayList<Map<String, Any>>*/

        if (activity != null) {
            usefulLinksInfra = Utils.getListFromJson(activity!!, MyConstants.USEFUL_LINKS_INFRA)
            fetchDashboardData()
        }

//        var dashboardAdapter = DashboardAdapter((activity as MainActivity), commonBean!!)

    }


    fun fetchDashboardData() {
        if (!showLoadingImg!!)
            (activity as MainActivity).showProgressBar()

        val requestBody = HashMap<String, Any>()
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "userInfo"
        CoroutineScope(Dispatchers.IO).launch {

            var job = async {
                BaseCoroutines().fetchData(
                    requestBody,
                    Busicode.DashSummary,
                    activity as MainActivity
                )
            }
            withContext(Dispatchers.Main)
            {

                var response = job.await()
                if (activity != null && !showLoadingImg!!) {
                    (activity as MainActivity).hideProgressBar()
                }

                if (response!!.responseEntity != null && response.status == MappActor.STATUS_OK) {
                    filterData(response)
                } else if (response!!.errorCode != null && response!!.errorCode.equals(MappActor.VERSION_SESSION_INVALID)){
                    (activity as MainActivity).showDialogForSessionExpired((activity as MainActivity).resources.getString(R.string.session_expired), (activity as MainActivity))
                }else if (response!!.errorMsg != null) {
                    if (showLoadingImg!!) {
                        mBinding!!.cnstrntLLoadImg.visibility = View.GONE
                    }
                    if (activity != null)
                        (activity as MainActivity).rel_toolbar.visibility = View.VISIBLE
                    mBinding!!.rvParent.visibility = View.VISIBLE
                    T.show(activity, response!!.errorMsg!!, 0)
                    mBinding!!.itemsswipetorefresh.isRefreshing = false
                } else {
                    if (showLoadingImg!!) {
                        mBinding!!.cnstrntLLoadImg.visibility = View.GONE
                    }
                    if (activity != null)
                        (activity as MainActivity).rel_toolbar.visibility = View.VISIBLE
                    mBinding!!.rvParent.visibility = View.VISIBLE
                    T.show(activity, "Something went wrong!", 0)
                    mBinding!!.itemsswipetorefresh.isRefreshing = false


                    if (activity != null) {
                        mBinding!!.cnstrntLUsefulLinks.visibility = View.VISIBLE
                        mBinding!!.rvParent.visibility = View.GONE
                        var adapter = UsefulLinksAdapter(usefulLinksInfra!!, activity as MainActivity, commonBean!!)
                        val gridLayoutManagerUsefulLinks = GridLayoutManager(activity, 3)
                        mBinding!!.rvUsefulLinks!!.layoutManager =
                            gridLayoutManagerUsefulLinks
                        mBinding!!.rvUsefulLinks!!.itemAnimator =
                            DefaultItemAnimator()

                        mBinding!!.rvUsefulLinks!!.adapter = adapter

                    }


                }
            }

        }
    }


    fun filterData(mCoroutinesResponse: CoroutinesResponse) {

        try {
            val msg = mCoroutinesResponse.responseEntity as HashMap<String, Any>


            if (msg != null) {
                try {


                    var commonBeanMainServices: CommonBean = CommonBean()
                    var responsePayLoadMap: Map<String, List<Map<String, Any>>> =
                        msg as Map<String, List<Map<String, Any>>>

                    if (responsePayLoadMap != null) {

                        commonBeanMainServices.`object` = responsePayLoadMap
                        mBinding!!.cnstrntLUsefulLinks.visibility = View.GONE
                        mBinding!!.rvParent.visibility = View.VISIBLE
                        if (activity != null) {
                            mBinding!!.rvParent.apply {
                                layoutManager =
                                    LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                                /*adapter = DashboardAdapter(
                                    commonBeanMainServices.`object` as Map<String, List<Map<String, Any>>>,
                                    (commonBean!!.usefulLinksNgo) as List<Map<String, Any>>,
                                    (activity as MainActivity),
                                    commonBean!!
                                )*/

                                adapter = DashboardAdapter(
                                    commonBeanMainServices.`object` as Map<String, List<Map<String, Any>>>,
                                    usefulLinksInfra!!,
                                    (activity as MainActivity),
                                    commonBean!!
                                )
                            }

                        }
                        if (showLoadingImg!!) {
                            mBinding!!.cnstrntLLoadImg.visibility = View.GONE
                        }
                        if (activity != null)
                            (activity as MainActivity).rel_toolbar.visibility = View.VISIBLE
                        mBinding!!.rvParent.visibility = View.VISIBLE

                    }
                    mBinding!!.itemsswipetorefresh.isRefreshing = false

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


    fun setData(commonBean: CommonBean, showLoadingImg: Boolean) {
        this.commonBean = commonBean
        this.showLoadingImg = showLoadingImg
    }


}