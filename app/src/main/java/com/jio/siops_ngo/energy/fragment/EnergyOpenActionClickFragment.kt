package com.jio.siops_ngo.energy.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.jio.jioinfra.network.business.BaseCoroutines
import com.jio.jioinfra.utilities.Busicode
import com.jio.jioinfra.utilities.MyConstants
import com.jio.myjio.bean.CoroutinesResponse
import com.jio.siops_ngo.MainActivity

import com.jio.siops_ngo.R
import com.jio.siops_ngo.databinding.FragmentEnergyOpenActionClickBinding
import com.jio.siops_ngo.infra.adapter.EnergyOpenActionAdapter
import com.jio.siops_ngo.infra.adapter.EnergyOpenActionPathAdapter
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.T
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.util.ArrayList
import java.util.HashMap

/**
 * A simple [Fragment] subclass.
 */
class EnergyOpenActionClickFragment : Fragment(),
    EnergyOpenActionAdapter.regionStateMpJcCLickListener,
    EnergyOpenActionPathAdapter.PathRegionStateMpJcCLickListener {

    var mBinding: FragmentEnergyOpenActionClickBinding? = null


    var dropDwnValueList = arrayOf(
        "All",
        "P1 Only",
        "RP1 Only",
        "Ip Colo Only",
        "P1 + RP1",
        "P1 + IP Colo",
        "RP1 + IP Colo"
    )
    var outLierId: String? = ""
    var featureName: String? = ""
    var siteCategoryList: MutableList<String>? = arrayListOf()
    var apiCode: String = MyConstants.ENERGY_REGION
    var region: String? = ""
    var circle: String? = ""
    var city: String? = ""
    var mp: String? = ""
    var jc: String? = ""
    var pathList: ArrayList<Map<String, Any>>? = arrayListOf()
    var pathCompleteList: ArrayList<Map<String, Any>>? = arrayListOf()
    var selectedPosition: Int? = 0
    var apiCalled: Boolean? = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_energy_open_action_click, container, false
        )
        return mBinding!!.root
    }

    companion object {
        fun newInstance() = EnergyOpenActionClickFragment()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as MainActivity).rel_notification.visibility = View.VISIBLE


        apiCalled = false

        mBinding!!.txtSubHeader.text = featureName!!



        pathList!!.clear()
        region = "Region"
        var map: Map<String, Any> = mutableMapOf(
            "apiCode" to apiCode,
            "key" to "Region"
        )
        apiCode = MyConstants.ENERGY_REGION

        pathList!!.add(map!!)
        fetchData(apiCode)



        mBinding!!.actionDropDown.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                arg0: AdapterView<*>?,
                arg1: View,
                position: Int,
                arg3: Long
            ) { // TODO Auto-generated method stub

                selectedPosition = position

                if (position == 0) {

                    siteCategoryList!!.clear()
                    siteCategoryList!!.add(MyConstants.P1)
                    siteCategoryList!!.add(MyConstants.RP1)
                    siteCategoryList!!.add(MyConstants.IP_Colo)
                    fetchData(apiCode)


                } else if (position == 1) {
                    siteCategoryList!!.clear()
                    siteCategoryList!!.add(MyConstants.P1)
                    fetchData(apiCode)

                } else if (position == 2) {
                    siteCategoryList!!.clear()
                    siteCategoryList!!.add(MyConstants.RP1)
                    fetchData(apiCode)

                } else if (position == 3) {
                    siteCategoryList!!.clear()
                    siteCategoryList!!.add(MyConstants.IP_Colo)
                    fetchData(apiCode)

                } else if (position == 4) {
                    siteCategoryList!!.clear()
                    siteCategoryList!!.add(MyConstants.P1)
                    siteCategoryList!!.add(MyConstants.RP1)
                    fetchData(apiCode)

                } else if (position == 5) {
                    siteCategoryList!!.clear()
                    siteCategoryList!!.add(MyConstants.P1)
                    siteCategoryList!!.add(MyConstants.IP_Colo)
                    fetchData(apiCode)

                } else if (position == 6) {
                    siteCategoryList!!.clear()
                    siteCategoryList!!.add(MyConstants.RP1)
                    siteCategoryList!!.add(MyConstants.IP_Colo)
                    fetchData(apiCode)

                }

            }

            override fun onNothingSelected(arg0: AdapterView<*>?) { // TODO Auto-generated method stub
            }
        })


    }


    fun fetchData(apiCode: String) {
        (activity as MainActivity).showProgressBar()
        val requestBody = HashMap<String, Any>()
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "userInfo"
        requestBody["appRoleCode"] = MyConstants.APP_ROLE_CODE_ENERGY
        requestBody["outlierId"] = outLierId!!
        requestBody["siteCategory"] = siteCategoryList!!

        if (apiCode.equals(MyConstants.ENERGY_STATE)) {
            requestBody["region"] = region!!
        }
        /*else if (apiCode.equals(MyConstants.ENERGY_CITY)) {
            requestBody["region"] = region!!
            requestBody["circle"] = circle!!
        }*/
        else if (apiCode.equals(MyConstants.ENERGY_MP)) {
            requestBody["region"] = region!!
            requestBody["circle"] = circle!!
//            requestBody["city"] = city!!
        } else if (apiCode.equals(MyConstants.ENERGY_JC)) {
            requestBody["region"] = region!!
            requestBody["circle"] = circle!!
//            requestBody["city"] = city!!
            requestBody["mp"] = mp!!
        } else if (apiCode.equals(MyConstants.ENERGY_SAP_ID)) {
            requestBody["region"] = region!!
            requestBody["circle"] = circle!!
//            requestBody["city"] = city!!
            requestBody["mp"] = mp!!
            requestBody["jc"] = jc!!
        }


        CoroutineScope(Dispatchers.IO).launch {
            var job = async {
                BaseCoroutines().fetchData(
                    requestBody,
                    Busicode.EnergyPendingActionClick,
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
                    filterData(response, apiCode)
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


    fun filterData(mCoroutinesResponse: CoroutinesResponse, apiCode: String) {

        try {
            val msg = mCoroutinesResponse.responseEntity as HashMap<String, Any>
            //  val energyList = msg["list"] as ArrayList<HashMap<String, Any>>

            if (msg != null) {
                try {


                    if(!apiCalled!!){
                        val spnrAdapter =
                            ArrayAdapter<CharSequence>(activity!!, R.layout.spinner_text, dropDwnValueList)
                        mBinding!!.actionDropDown.setAdapter(spnrAdapter)
                        mBinding!!.actionDropDown.setSelection(selectedPosition!!)
                        apiCalled = true
                    }


                    if(msg.containsKey("totalCount") && msg["totalCount"]!=null){

                        mBinding!!.txtCountHeader.text = msg["totalCount"].toString()

                    }

                    val dataList = msg["list"] as ArrayList<Map<String, Any>>




                    var adapter =
                        EnergyOpenActionAdapter(activity as MainActivity?, dataList, this, apiCode)
                    mBinding!!.actionList.layoutManager = LinearLayoutManager(activity)
                    mBinding!!.actionList.itemAnimator = DefaultItemAnimator()
                    mBinding!!.actionList!!.adapter = adapter
                    adapter!!.notifyDataSetChanged()

//                    if (!apiCode.equals(MyConstants.ENERGY_REGION)) {
                    var adapterPath =
                        EnergyOpenActionPathAdapter(activity as MainActivity?, pathList!!, this)
                    mBinding!!.rvPath.layoutManager =
                        LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                    mBinding!!.rvPath.itemAnimator = DefaultItemAnimator()
                    mBinding!!.rvPath!!.adapter = adapterPath
                    adapterPath!!.notifyDataSetChanged()
                    mBinding!!.rvPath.scrollToPosition(pathList!!.size - 1);
//                    }

                    if (apiCode.equals(MyConstants.ENERGY_SAP_ID)) {
                        mBinding!!.txtCount.visibility = View.INVISIBLE
                    } else {
                        mBinding!!.txtCount.visibility = View.VISIBLE
                    }

                    when (apiCode) {
                        MyConstants.ENERGY_REGION -> mBinding!!.txtHeaderTitle.text =
                            "Open Action/ Region"
                        MyConstants.ENERGY_STATE -> mBinding!!.txtHeaderTitle.text =
                            "Open Action/ Region/ State"
                        /*MyConstants.ENERGY_CITY -> mBinding!!.txtHeaderTitle.text =
                            "Open Action/ Region/ State"*/
                        MyConstants.ENERGY_MP -> mBinding!!.txtHeaderTitle.text =
                            "Open Action/ Region/ State/ MP"
                        MyConstants.ENERGY_JC -> mBinding!!.txtHeaderTitle.text =
                            "Open Action/ Region/ State/ MP/ JC"
                        MyConstants.ENERGY_SAP_ID -> mBinding!!.txtHeaderTitle.text =
                            "Open Action/ Region/ State/ MP/ JC/ Site"
                    }

                    when (apiCode) {
                        MyConstants.ENERGY_REGION -> mBinding!!.txtheader.text = "Region"
                        MyConstants.ENERGY_STATE -> mBinding!!.txtheader.text = "States"
//                        MyConstants.ENERGY_CITY -> mBinding!!.txtheader.text = "City"
                        MyConstants.ENERGY_MP -> mBinding!!.txtheader.text = "Maintenance Point"
                        MyConstants.ENERGY_JC -> mBinding!!.txtheader.text = "Jio Center"
                        MyConstants.ENERGY_SAP_ID-> mBinding!!.txtheader.text = "Site"
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


    fun setData(outlierId: String, siteCategoryList: ArrayList<String>, selectedPosition: Int, featureName:String) {
        this.outLierId = outlierId
        this.siteCategoryList = siteCategoryList
        this.selectedPosition = selectedPosition
        this.featureName = featureName
    }


    override fun onRegStateMpJcClicked(key: String) {
        try {

            pathCompleteList!!.clear()
            if (apiCode.equals(MyConstants.ENERGY_REGION)) {
                apiCode = MyConstants.ENERGY_STATE
                region = key
                var map: Map<String, Any> = mutableMapOf(
                    "apiCode" to apiCode,
                    "key" to key
                )
                pathList!!.add(map!!)
                fetchData(apiCode)
            } else if (apiCode.equals(MyConstants.ENERGY_STATE)) {
                apiCode = MyConstants.ENERGY_MP
                circle = key
                var map: Map<String, Any> = mutableMapOf(
                    "apiCode" to apiCode,
                    "key" to key
                )
                pathList!!.add(map!!)
                fetchData(apiCode)
            }
            /*else if (apiCode.equals(MyConstants.ENERGY_CITY)) {
                apiCode = MyConstants.ENERGY_MP
                city = key
                var map: Map<String, Any> = mutableMapOf(
                    "apiCode" to apiCode,
                    "key" to key
                )
                pathList!!.add(map!!)
                fetchData(apiCode)
            } */
            else if (apiCode.equals(MyConstants.ENERGY_MP)) {
                apiCode = MyConstants.ENERGY_JC
                mp = key
                var map: Map<String, Any> = mutableMapOf(
                    "apiCode" to apiCode,
                    "key" to key
                )
                pathList!!.add(map!!)
                fetchData(apiCode)
            } else if (apiCode.equals(MyConstants.ENERGY_JC)) {
                apiCode = MyConstants.ENERGY_SAP_ID
                jc = key
                var map: Map<String, Any> = mutableMapOf(
                    "apiCode" to apiCode,
                    "key" to key
                )
                pathList!!.add(map!!)
                fetchData(apiCode)
            }
            pathCompleteList!!.addAll(pathList!!)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

    }

    override fun onPathRegStateMpJcClicked(posiiton: Int, key: String) {
        pathList!!.clear()
        for ((index, value) in pathCompleteList!!.withIndex()) {
            if (index < posiiton + 1) {
                pathList!!.add(value)

            }
            if (index == posiiton) {

                apiCode = value.get("apiCode").toString()
            }


        }


        Log.e("pathList", "" + pathList!!.size)
        fetchData(apiCode)

    }


}
