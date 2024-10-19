package com.jio.siops_ngo.energy.fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.jio.jioinfra.bean.CommonBean
import com.jio.jioinfra.network.business.BaseCoroutines
import com.jio.jioinfra.utilities.Busicode
import com.jio.jioinfra.utilities.MyConstants
import com.jio.myjio.bean.CoroutinesResponse
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.databinding.EnergyClickLayoutBinding
import com.jio.siops_ngo.databinding.FragmentEnergyOpenActionClickBinding
import com.jio.siops_ngo.energy.adapter.EnergyOnClickAdapter
import com.jio.siops_ngo.infra.adapter.EnergyOpenActionAdapter
import com.jio.siops_ngo.infra.adapter.EnergyOpenActionPathAdapter
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.T
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*


class EnergyDashboardClickFragment : Fragment(), EnergyOpenActionAdapter.regionStateMpJcCLickListener,
    EnergyOpenActionPathAdapter.PathRegionStateMpJcCLickListener  {
    internal var commonBean: CommonBean? = null
    var mBinding: FragmentEnergyOpenActionClickBinding? = null
    var map: Map<String, Any>? = null
    var allDataList: ArrayList<HashMap<String, Any>>? = null
    var dataSubList: ArrayList<HashMap<String, Any>>? = null
    var p1DataList: ArrayList<Map<String, Any>>? = null
    var rp1DataList: ArrayList<Map<String, Any>>? = null
    var ipColoDataList: ArrayList<Map<String, Any>>? = null
    var progressBarData: ArrayList<Map<String, Any>>? = null
    var outlierId: String? = null
    var appRoleCode: String? = "741"
    var subtitlewithslach: String? = null
    var totalCount: Int? = 0
    var count1: Int? = 0
    var count2: Int? = 0
    var count3: Int? = 0
    var start: Int? = 0
    var count: Int? = 1
    var pageSize: Int? = 0
    var adapter:EnergyOnClickAdapter?=null
    var ar: ArrayList<Int> = ArrayList()
    var totalCountValue: Int? = 0
    var layoutManager:LinearLayoutManager?=null
    var requestBodyfilters: HashMap<String, Any>?=null
    var region:String? = ""
    var circle:String? = ""
    var mp:String? = ""
    var jc:String? = ""
    var featureName:String? = ""
    var pathList: java.util.ArrayList<Map<String, Any>>? = arrayListOf()
    var pathCompleteList: java.util.ArrayList<Map<String, Any>>? = arrayListOf()
    var selectedPosition: Int? = 0

    var dropDwnValueList = arrayOf(
        "All",
        "P1 Only",
        "RP1 Only",
        "Ip Colo Only",
        "P1 + RP1",
        "P1 + IP Colo",
        "RP1 + IP Colo"
    )

    var apiCode: String = MyConstants.ENERGY_REGION

    companion object {
        fun newInstance() = EnergyDashboardClickFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_energy_open_action_click, container, false)
        return mBinding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mBinding!!.cnstntLSpnr!!.visibility = View.GONE
//        mBinding!!.userName.text = "Hello, " + PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")

        (activity as MainActivity).rel_notification.visibility = View.VISIBLE
//        mBinding!!.recyclerViewEnergyList.setNestedScrollingEnabled(false);
        mBinding!!.txtSubHeader.text = featureName!!
//        mBinding!!.txtCountHeader.text = ""


        if(requestBodyfilters!=null){

            if(requestBodyfilters!!.containsKey("region") && requestBodyfilters!!["region"] !=null) {

                region = requestBodyfilters!!["region"].toString()
            }

            if(requestBodyfilters!!.containsKey("circle") && requestBodyfilters!!["circle"] !=null) {

                circle = requestBodyfilters!!["circle"].toString()
            }

            if(requestBodyfilters!!.containsKey("mp") && requestBodyfilters!!["mp"] !=null) {

                mp = requestBodyfilters!!["mp"].toString()
            }

            if(requestBodyfilters!!.containsKey("jc") && requestBodyfilters!!["jc"] !=null) {

                jc = requestBodyfilters!!["jc"].toString()
            }

        }


        if(region.equals("ALL")){
            apiCode = MyConstants.ENERGY_REGION
            pathList!!.clear()
            var map: Map<String, Any> = mutableMapOf(
                "apiCode" to apiCode,
                "key" to "Region"
            )
            pathList!!.add(map!!)

        }else if(circle.equals("ALL")){
            apiCode = MyConstants.ENERGY_STATE
            pathList!!.clear()
            var map: Map<String, Any> = mutableMapOf(
                "apiCode" to MyConstants.ENERGY_REGION,
                "key" to "Region"
            )
            pathList!!.add(map!!)
            var regnMap: Map<String, Any> = mutableMapOf(
                "apiCode" to apiCode,
                "key" to region!!
            )
            pathList!!.add(regnMap)

        }else if(mp.equals("ALL")){
            apiCode = MyConstants.ENERGY_MP
            var map: Map<String, Any> = mutableMapOf(
                "apiCode" to MyConstants.ENERGY_REGION,
                "key" to "Region"
            )
            pathList!!.add(map!!)
            var regnMap: Map<String, Any> = mutableMapOf(
                "apiCode" to MyConstants.ENERGY_STATE,
                "key" to region!!
            )
            pathList!!.add(regnMap)
            var circleMap: Map<String, Any> = mutableMapOf(
                "apiCode" to apiCode,
                "key" to circle!!
            )
            pathList!!.add(circleMap)


        }else if(jc.equals("ALL")){
            apiCode = MyConstants.ENERGY_JC
            var map: Map<String, Any> = mutableMapOf(
                "apiCode" to MyConstants.ENERGY_REGION,
                "key" to "Region"
            )
            pathList!!.add(map!!)
            var regnMap: Map<String, Any> = mutableMapOf(
                "apiCode" to MyConstants.ENERGY_STATE,
                "key" to region!!
            )
            pathList!!.add(regnMap)
            var circleMap: Map<String, Any> = mutableMapOf(
                "apiCode" to MyConstants.ENERGY_MP,
                "key" to circle!!
            )
            pathList!!.add(circleMap)

            var mPMap: Map<String, Any> = mutableMapOf(
                "apiCode" to apiCode,
                "key" to mp!!
            )
            pathList!!.add(mPMap)

        }else{
            if(!jc.equals("ALL")){
                apiCode = MyConstants.ENERGY_JC
                var map: Map<String, Any> = mutableMapOf(
                    "apiCode" to MyConstants.ENERGY_REGION,
                    "key" to "Region"
                )
                pathList!!.add(map!!)
                var regnMap: Map<String, Any> = mutableMapOf(
                    "apiCode" to MyConstants.ENERGY_STATE,
                    "key" to region!!
                )
                pathList!!.add(regnMap)
                var circleMap: Map<String, Any> = mutableMapOf(
                    "apiCode" to MyConstants.ENERGY_MP,
                    "key" to circle!!
                )
                pathList!!.add(circleMap)

                var mPMap: Map<String, Any> = mutableMapOf(
                    "apiCode" to apiCode,
                    "key" to mp!!
                )
                pathList!!.add(mPMap)

                var jcMap: Map<String, Any> = mutableMapOf(
                    "apiCode" to MyConstants.ENERGY_SAP_ID,
                    "key" to jc!!
                )
                pathList!!.add(jcMap)
            }else if(!mp.equals("ALL")){

                apiCode = MyConstants.ENERGY_JC
                var map: Map<String, Any> = mutableMapOf(
                    "apiCode" to MyConstants.ENERGY_REGION,
                    "key" to "Region"
                )
                pathList!!.add(map!!)
                var regnMap: Map<String, Any> = mutableMapOf(
                    "apiCode" to MyConstants.ENERGY_STATE,
                    "key" to region!!
                )
                pathList!!.add(regnMap)
                /*var circleMap: Map<String, Any> = mutableMapOf(
                    "apiCode" to MyConstants.ENERGY_MP,
                    "key" to circle!!
                )
                pathList!!.add(circleMap)*/


            }else if(!circle.equals("ALL")){

                apiCode = MyConstants.ENERGY_JC
                var map: Map<String, Any> = mutableMapOf(
                    "apiCode" to MyConstants.ENERGY_REGION,
                    "key" to "Region"
                )
                pathList!!.add(map!!)
                var regnMap: Map<String, Any> = mutableMapOf(
                    "apiCode" to MyConstants.ENERGY_STATE,
                    "key" to region!!
                )
                pathList!!.add(regnMap)
                var circleMap: Map<String, Any> = mutableMapOf(
                    "apiCode" to MyConstants.ENERGY_MP,
                    "key" to circle!!
                )
                pathList!!.add(circleMap)


            }



        }

        pathCompleteList!!.addAll(pathList!!)

        /*if(!region.equals("ALL") && circle.equals("ALL")&& mp.equals("ALL") && jc.equals("ALL")){
            apiCode = MyConstants.ENERGY_STATE
            pathList!!.clear()
            var map: Map<String, Any> = mutableMapOf(
                "apiCode" to apiCode,
                "key" to "Region"
            )
            pathList!!.add(map!!)




        }*/


        fetchData(apiCode!!)

    }

    fun fetchData(apiCode: String) {
        if(count==1) {
            (activity as MainActivity).showProgressBar()
        }

        val requestBody = HashMap<String, Any>()
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["appRoleCode"] = appRoleCode!!
        requestBody["type"] = "userInfo"
        requestBody["outlierId"] = outlierId!!
        requestBody["pageCount"] = count!!
        /*requestBody["region"] = region!!
        requestBody["circle"] = circle!!
        requestBody["mp"] = mp!!
        requestBody["jc"] = jc!!*/
        requestBody["category"] = "ALL"



        if (apiCode.equals(MyConstants.ENERGY_REGION)) {
            requestBody["region"] = "ALL"
            requestBody["circle"] = "ALL"
            requestBody["mp"] = "ALL"
            requestBody["jc"] = "ALL"
        }
        if (apiCode.equals(MyConstants.ENERGY_STATE)) {
            requestBody["region"] = region!!
            requestBody["circle"] = "ALL"
            requestBody["mp"] = "ALL"
            requestBody["jc"] = "ALL"
        }
        else if (apiCode.equals(MyConstants.ENERGY_MP)) {
            requestBody["region"] = region!!
            requestBody["circle"] = circle!!
            requestBody["mp"] = "ALL"
            requestBody["jc"] = "ALL"
//            requestBody["city"] = city!!
        } else if (apiCode.equals(MyConstants.ENERGY_JC)) {
            requestBody["region"] = region!!
            requestBody["circle"] = circle!!
            requestBody["mp"] = mp!!
            requestBody["jc"] = "ALL"
        } else if (apiCode.equals(MyConstants.ENERGY_SAP_ID)) {
            requestBody["region"] = region!!
            requestBody["circle"] = circle!!
            requestBody["mp"] = mp!!
            requestBody["jc"] = jc!!
        }


        /*if (apiCode.equals(MyConstants.ENERGY_STATE)) {
            requestBody["region"] = region!!
        }
        *//*else if (apiCode.equals(MyConstants.ENERGY_CITY)) {
            requestBody["region"] = region!!
            requestBody["circle"] = circle!!
        }*//*
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
        }*/



        CoroutineScope(Dispatchers.IO).launch {

            var job = async { BaseCoroutines().fetchData(requestBody, Busicode.EnergyDashboardClick, activity as MainActivity) }
            withContext(Dispatchers.Main)
            {

                var response = job.await()
                if (activity != null) {
                    (activity as MainActivity).hideProgressBar()
                }

                if (response!!.responseEntity != null && response.status == MappActor.STATUS_OK) {
                    filterData(response, apiCode)
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


    fun filterData(mCoroutinesResponse: CoroutinesResponse, apiCode: String) {

        try {
            val msg = mCoroutinesResponse.responseEntity as java.util.HashMap<String, Any>
            //  val energyList = msg["list"] as ArrayList<HashMap<String, Any>>

            if (msg != null) {
                try {



                    if(msg.containsKey("totalCount") && msg["totalCount"]!=null){

                        mBinding!!.txtCountHeader.text = msg["totalCount"].toString()

                    }

                    val dataList = msg["list"] as java.util.ArrayList<Map<String, Any>>




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


    fun setData(
        commonBean: CommonBean,
        outlierId: String,
        featureName: String,
        requestBodyfilters: HashMap<String, Any>
    ) {
        this.commonBean = commonBean
        this.outlierId = outlierId
        this.requestBodyfilters = requestBodyfilters
        this.featureName = featureName

    }

    override fun onResume() {
        super.onResume()


    }
    fun hasIndex(index: Int): Boolean {
        if (index < progressBarData!!.size)
            return true
        else
            return false

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

    fun createPathList(){


    }
}