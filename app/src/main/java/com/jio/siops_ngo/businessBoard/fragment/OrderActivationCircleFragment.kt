package com.jio.siops_ngo.businessBoard.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.jio.jioinfra.bean.CommonBean
import com.jio.jioinfra.custom.TextViewMedium
import com.jio.jioinfra.network.business.BaseCoroutines
import com.jio.jioinfra.utilities.Busicode
import com.jio.jioinfra.utilities.MyConstants
import com.jio.myjio.bean.CoroutinesResponse
import com.jio.siops_ngo.MainActivity

import com.jio.siops_ngo.R
import com.jio.siops_ngo.databinding.FragmentOrderActivationCircleBinding
import com.jio.siops_ngo.infra.adapter.OrderActivationJourneryCircleAdapter
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.T
import kotlinx.coroutines.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class OrderActivationCircleFragment : Fragment() {

    var mBinding: FragmentOrderActivationCircleBinding?=null
    internal var commonBean: CommonBean? = null
    var activationCircleRespMapList: ArrayList<Map<String, Any>>? = null
    var apiRequestFormattedDate: String? = null
    var map: Map<String, Any>? = null
    var dataList: java.util.ArrayList<Map<String, Any>>? = null
    var adapter: OrderActivationJourneryCircleAdapter? = null
    var zoneAllChannels:String = ""
    var enteredAllChannels:String = ""
    var activatedAllChannels:String = ""
    var networkLatchedAllChannels:String = ""
    var rejectedAllChannels:String = ""
    var inProcessAllChannels:String = ""
    var tvPendingAllChannels:String = ""
    var circleName:String = ""
    var selectedDate:String = ""
    var categoryToDisplay:String = ""
    companion object {
        fun newInstance() =
            OrderActivationCircleFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_activation_circle, container, false)
        return mBinding!!.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        map = commonBean!!.`object` as Map<String, Any>



        val c = Calendar.getInstance().time
        println("Current time => $c")

        val df = SimpleDateFormat("dd/MM/yyyy")
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val dateFormatted = dateFormat.parse(selectedDate)
        mBinding!!.collapsibleCalendarView.visibility = View.GONE
        mBinding!!.txtDate.text = df.format(dateFormatted)




        fetchDashboardData("ALL")

        mBinding!!.txtRegion.setOnClickListener {

            changeViewBg(mBinding!!.txtRegion, mBinding!!.txtGt, mBinding!!.txtRr, "ALL")
        }


        mBinding!!.txtGt.setOnClickListener {

            changeViewBg(mBinding!!.txtGt, mBinding!!.txtRegion, mBinding!!.txtRr, "GT")
        }

        mBinding!!.txtRr.setOnClickListener {

            changeViewBg(mBinding!!.txtRr, mBinding!!.txtRegion, mBinding!!.txtGt, "RR")
        }

        mBinding!!.txtRegion.performClick()
    }
    fun fetchDashboardData(category:String) {
        (activity as MainActivity).showProgressBar()
        val requestBody = HashMap<String, Any>()
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "userInfo"
        requestBody["date"] = selectedDate!!
        requestBody["category"] = category
        if(map!!.containsKey("zone") && map!!.get("zone")!=null){
            zoneAllChannels = map!!["zone"] as String
            requestBody["region"] = zoneAllChannels.toUpperCase()
        }

        CoroutineScope(Dispatchers.IO).launch {

            var job = async {
                BaseCoroutines().fetchData(
                    requestBody,
                    Busicode.O2AJourneyRegion,
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
                    filterData(response,category)
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


    fun filterData(
        mCoroutinesResponse: CoroutinesResponse,
        category: String
    ) {
        try {
            val activationRespMap = mCoroutinesResponse.responseEntity as HashMap<String, Any>
            if (activationRespMap.containsKey("list") && activationRespMap != null) {
                dataList = activationRespMap["list"] as java.util.ArrayList<Map<String, Any>>

                if(activationRespMap!!.containsKey("category") && activationRespMap!!.get("category")!=null){
                    categoryToDisplay = activationRespMap!!["category"] as String
                }


                if(map!!.containsKey("zone") && map!!.get("zone")!=null){
                    zoneAllChannels = map!!["zone"] as String
                }

                if(map!!.containsKey("entered") && map!!.get("entered")!=null){
                    enteredAllChannels = map!!["entered"].toString()
                }
                if(map!!.containsKey("activated") && map!!.get("activated")!=null){
                    activatedAllChannels = map!!["activated"].toString()
                }
                if(map!!.containsKey("network_latched") && map!!.get("network_latched")!=null){
                    networkLatchedAllChannels = map!!["network_latched"].toString()
                }
                if(map!!.containsKey("rejected") && map!!.get("rejected")!=null){
                    rejectedAllChannels = map!!["rejected"].toString()
                }
                if(map!!.containsKey("in_process") && map!!.get("in_process")!=null){
                    inProcessAllChannels = map!!["in_process"].toString()
                }
                if(map!!.containsKey("tv_pending") && map!!.get("tv_pending")!=null){
                    tvPendingAllChannels = map!!["tv_pending"].toString()
                }


                /*var map1: Map<String, Any> = mutableMapOf(
                    "zone" to zoneAllChannels,
                    "entered" to enteredAllChannels,
                    "activated" to activatedAllChannels,
                    "network_latched" to networkLatchedAllChannels,
                    "rejected" to rejectedAllChannels,
                    "in_process" to inProcessAllChannels,
                    "tv_pending" to tvPendingAllChannels
                )
                dataList!!.add(0, map1!!)*/


                adapter = OrderActivationJourneryCircleAdapter(activity as MainActivity?,dataList!!,category,selectedDate!!,circleName, categoryToDisplay)
                mBinding!!.journeyList.layoutManager = LinearLayoutManager(activity)
                mBinding!!.journeyList.itemAnimator = DefaultItemAnimator()
                mBinding!!.journeyList!!.adapter = adapter
                adapter!!.notifyDataSetChanged()
            }




        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    fun setData(commonBean: CommonBean,circleName:String, selectedDate:String) {
        this.commonBean = commonBean
        this.circleName = circleName
        this.selectedDate = selectedDate
    }


    fun changeViewBg(v1: TextViewMedium, v2: TextViewMedium, v3: TextViewMedium, category: String) {

        v1.setBackgroundDrawable(activity!!.resources.getDrawable(R.drawable.region_rounded))
        v1.setTextColor(activity!!.resources.getColor(R.color.white))

        v2.setBackgroundDrawable(activity!!.resources.getDrawable(R.drawable.rounded_corner_blue_border_bg))
        v2.setTextColor(activity!!.resources.getColor(R.color.jioinfra_gray))


        v3.setBackgroundDrawable(activity!!.resources.getDrawable(R.drawable.rounded_corner_blue_border_bg))
        v3.setTextColor(activity!!.resources.getColor(R.color.jioinfra_gray))


        dataList?.clear()
        adapter?.notifyDataSetChanged()
        fetchDashboardData(category)

    }

}
