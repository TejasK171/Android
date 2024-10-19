package com.jio.siops_ngo.infra.fragment


import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.jio.jioinfra.bean.CommonBean
import com.jio.jioinfra.network.business.BaseCoroutines
import com.jio.jioinfra.utilities.Busicode
import com.jio.jioinfra.utilities.MyConstants
import com.jio.myjio.bean.CoroutinesResponse
import com.jio.siops_ngo.MainActivity

import com.jio.siops_ngo.R
import com.jio.siops_ngo.databinding.FragmentEnergyDashboardBinding
import com.jio.siops_ngo.infra.adapter.EnergyDashboardAdapter
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.T
import kotlinx.coroutines.*

/**
 * A simple [Fragment] subclass.
 */
class EnergyDashboardFragment : Fragment() {


    var mBinding: FragmentEnergyDashboardBinding? = null
    var commonBean: CommonBean? = null
    var dataSites: ArrayList<Map<String, Any>>? = null
    var dataSitesLoad: ArrayList<Map<String, Any>>? = null
    var dataPower: ArrayList<Map<String, Any>>? = null
    var dataCo2: ArrayList<Map<String, Any>>? = null
    var dataHighCPH: ArrayList<Map<String, Any>>? = null
    var dataEbBills: ArrayList<Map<String, Any>>? = null
  //  var showLoadingImg: Boolean? = false

    companion object {
        fun newInstance() = EnergyDashboardFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_energy_dashboard, container, false)

        return mBinding!!.root


    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fetchData();


        if (activity != null) {
            mBinding!!.itemsswipetorefresh.setProgressBackgroundColorSchemeColor(
                ContextCompat.getColor(
                    (activity as MainActivity),
                    R.color.toolbar_bg
                )
            )
        }
        mBinding!!.itemsswipetorefresh.setColorSchemeColors(Color.WHITE)
        mBinding!!.itemsswipetorefresh.setOnRefreshListener { fetchData() }
    }


    fun fetchData() {
        (activity as MainActivity).showProgressBar()

        val requestBody = HashMap<String, Any>()
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "userInfo"
        requestBody["appRoleCode"] = "741"
        CoroutineScope(Dispatchers.IO).launch {

            var job = async {
                BaseCoroutines().fetchData(
                    requestBody,
                    Busicode.BCBMainControl,
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

        try {
            val msg = mCoroutinesResponse.responseEntity as HashMap<String, Any>


            if (msg != null) {
                try {
                    var commonBeanMainServices: CommonBean = CommonBean()
                    dataSites = msg["sites"] as ArrayList<Map<String, Any>>
                    dataCo2 = msg["co2"] as ArrayList<Map<String, Any>>
                    dataPower = msg["power"] as ArrayList<Map<String, Any>>
                 //   dataEbBills = msg["ebBills"] as ArrayList<Map<String, Any>>
                    dataHighCPH = msg["highCPH"] as ArrayList<Map<String, Any>>
                    dataSitesLoad = msg["siteLoad"] as ArrayList<Map<String, Any>>
                    mBinding!!.itemsswipetorefresh.isRefreshing = false
                    var adapter = EnergyDashboardAdapter(activity as MainActivity?,dataSites!!,dataSitesLoad!!,dataPower!!,dataCo2!!,dataHighCPH!!)
                    mBinding!!.energyList.layoutManager = LinearLayoutManager(activity)
                    mBinding!!.energyList.itemAnimator = DefaultItemAnimator()
                    mBinding!!.energyList!!.adapter = adapter
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



}
