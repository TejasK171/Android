package com.jio.siops_ngo.energy.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.jio.jioinfra.network.business.BaseCoroutines
import com.jio.jioinfra.utilities.Busicode
import com.jio.jioinfra.utilities.MyConstants
import com.jio.myjio.bean.CoroutinesResponse
import com.jio.siops_ngo.MainActivity

import com.jio.siops_ngo.R
import com.jio.siops_ngo.databinding.FragmentEnergyOpenActionBinding
import com.jio.siops_ngo.infra.adapter.EnergyOpenActionItemAdapter
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.T
import com.jio.siops_ngo.utilities.Utils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.diseal_status_item.view.*
import kotlinx.android.synthetic.main.include_closed_action_item_layout.view.*
import kotlinx.coroutines.*
import java.util.ArrayList
import java.util.HashMap

/**
 * A simple [Fragment] subclass.
 */
class EnergyOpenActionFragment : Fragment() {

    var mBinding: FragmentEnergyOpenActionBinding? = null

    var openActionItemList: ArrayList<Map<String, Any>>? = null
    var closedActionItemList: ArrayList<Map<String, Any>>? = null
    var category: String? = "ALL"
    var siteCategoryList: ArrayList<String>? = arrayListOf()
    var rbPosition:Int? = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_energy_open_action, container, false
        )
        return mBinding!!.root
    }

    companion object {
        fun newInstance() = EnergyOpenActionFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        (activity as MainActivity).rel_notification.visibility = View.VISIBLE

        if (category.equals("ALL")){
            mBinding!!.rbAll.isChecked = true
            siteCategoryList!!.clear()
            siteCategoryList!!.add(MyConstants.P1)
            siteCategoryList!!.add(MyConstants.RP1)
            siteCategoryList!!.add(MyConstants.IP_Colo)
            fetchData()
        }

        initView()
    }

    private fun initView() {

        mBinding!!.radioGroupAll.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->

                if (checkedId == mBinding!!.rbAll.id) {
                    category = "ALL"
                    rbPosition = 0
                    siteCategoryList!!.clear()
                    siteCategoryList!!.add(MyConstants.P1)
                    siteCategoryList!!.add(MyConstants.RP1)
                    siteCategoryList!!.add(MyConstants.IP_Colo)
                    fetchData()

                }  else if (checkedId == mBinding!!.rbP1.id) {
                    rbPosition = 1
                    category = MyConstants.P1
                    siteCategoryList!!.clear()
                    siteCategoryList!!.add(MyConstants.P1)
                    fetchData()

                } else if (checkedId == mBinding!!.rbRp1.id) {
                    rbPosition = 2
                    category = MyConstants.RP1
                    siteCategoryList!!.clear()
                    siteCategoryList!!.add(MyConstants.RP1)
                    fetchData()

                }else if (checkedId == mBinding!!.rbIpColo.id) {
                    rbPosition = 3
                    category = MyConstants.IP_Colo
                    siteCategoryList!!.clear()
                    siteCategoryList!!.add(MyConstants.IP_Colo)
                    fetchData()

                }


            }
        )
    }


    //Start Call API For Action Panding  Data
    fun fetchData() {
        (activity as MainActivity).showProgressBar()
        val requestBody = HashMap<String, Any>()
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "userInfo"
        requestBody["appRoleCode"] = "741"
        requestBody["category"] = category!!
        CoroutineScope(Dispatchers.IO).launch {
            var job = async {
                BaseCoroutines().fetchData(
                    requestBody,
                    Busicode.EnergyPendingAction,
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
                    actionPendingData(response)
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
    //End Call API For Action Panding List Data

    //Start Show Action Panding Data on List
    fun actionPendingData(mCoroutinesResponse: CoroutinesResponse) {

        try {
            val msg = mCoroutinesResponse.responseEntity as HashMap<String, Any>
            //  val energyList = msg["list"] as ArrayList<HashMap<String, Any>>

            if (msg != null) {
                try {
//                    openActionItemList!!.clear()
//                    closedActionItemList!!.clear()
//                    dieselStatusItemList!!.clear()

                    if(msg.containsKey("openActionCount")&& msg["openActionCount"]!=null){
                        mBinding!!.textViewLabelSiteCount.text = msg["openActionCount"].toString()
                    }

                    openActionItemList = msg["openActionItem"] as ArrayList<Map<String, Any>>
//                    closedActionItemList = msg["closedActionItem"] as ArrayList<Map<String, Any>>


                    //Initialize Open Item Recycler View
                    val openActionRecyclerViewAdapter = EnergyOpenActionItemAdapter(
                        activity as MainActivity,
                        openActionItemList!!,
                        siteCategoryList!!,
                        rbPosition!!
                    )
                    val linearLayoutManager = LinearLayoutManager(activity)
                    mBinding!!.recylOpenAction.recyclerViewOpenAction.layoutManager =
                        linearLayoutManager
                    mBinding!!.recylOpenAction.recyclerViewOpenAction.adapter =
                        openActionRecyclerViewAdapter
                    mBinding!!.recylOpenAction.recyclerViewOpenAction.isNestedScrollingEnabled =
                        false

                    if (msg.containsKey("dieselStatus") && msg["dieselStatus"] != null) {

                        var dieselStatusItemList =
                            msg["dieselStatus"] as ArrayList<Map<String, Any>>

                        if (Utils.hasIndex(0, dieselStatusItemList!!)) {


                            if (dieselStatusItemList[0]!!.containsKey("outlierCount") && dieselStatusItemList[0]!!["outlierCount"] != null) {
                                mBinding!!.dieselItem.txt_diesel_count1!!.text =
                                    dieselStatusItemList[0]!!["outlierCount"].toString()
                            }

                            if (dieselStatusItemList[0]!!.containsKey("featureName") && dieselStatusItemList[0]!!["featureName"] != null) {
                                mBinding!!.dieselItem.txt_diesel_title1!!.text =
                                    dieselStatusItemList[0]!!["featureName"].toString()
                            }

                        }

                        if (Utils.hasIndex(1, dieselStatusItemList!!)) {

                            if (dieselStatusItemList[1]!!.containsKey("outlierCount") && dieselStatusItemList[1]!!["outlierCount"] != null) {
                                mBinding!!.dieselItem.txt_diesel_count2!!.text =
                                    dieselStatusItemList[1]!!["outlierCount"].toString()
                            }

                            if (dieselStatusItemList[1]!!.containsKey("featureName") && dieselStatusItemList[1]!!["featureName"] != null) {
                                mBinding!!.dieselItem.txt_diesel_title2!!.text =
                                    dieselStatusItemList[1]!!["featureName"].toString()
                            }

                        }

                        if (Utils.hasIndex(2, dieselStatusItemList!!)) {

                            if (dieselStatusItemList[2]!!.containsKey("outlierCount") && dieselStatusItemList[2]!!["outlierCount"] != null) {
                                mBinding!!.dieselItem.txt_diesel_count3!!.text =
                                    dieselStatusItemList[2]!!["outlierCount"].toString()
                            }

                            if (dieselStatusItemList[2]!!.containsKey("featureName") && dieselStatusItemList[2]!!["featureName"] != null) {
                                mBinding!!.dieselItem.txt_diesel_title3!!.text =
                                    dieselStatusItemList[2]!!["featureName"].toString()
                            }

                        }

                    }


                    // Initialize Closed ItemRecycler View
                    /*val closedItemRecyclerView = EnergyOpenActionItemAdapter(activity as MainActivity,closedActionItemList!!)
                    val linearLayoutManager1 = LinearLayoutManager(activity)
                    mBinding!!.layoutClosedItemRecyclerView.recyclerViewOpenAction.recyclerViewOpenAction.layoutManager = linearLayoutManager1
                    mBinding!!.layoutClosedItemRecyclerView.recyclerViewOpenAction.recyclerViewOpenAction.adapter = closedItemRecyclerView
                    mBinding!!.layoutClosedItemRecyclerView.recyclerViewOpenAction.recyclerViewOpenAction.isNestedScrollingEnabled = false*/


                } catch (e: Exception) {
                    MyExceptionHandler.handle(e)
                }
            }
        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
            T.show(activity, getString(R.string.something_went_wrong), 0)
        }
    }
    //Start Show Action Panding on List


}
