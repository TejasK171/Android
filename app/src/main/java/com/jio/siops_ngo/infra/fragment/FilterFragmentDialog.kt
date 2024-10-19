package com.jio.siops_ngo.infra.fragment


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
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
import com.jio.siops_ngo.databinding.FragmentInfraAlermsFilterBinding
import com.jio.siops_ngo.infra.adapter.*
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.T
import kotlinx.android.synthetic.main.fragment_infra_filter.*
import kotlinx.coroutines.*


/**
 * A simple [Fragment] subclass.
 */
class FilterFragmentDilog(var mFragmentCallback:FragmentCallback,var agingpos:String,var region: String?,var circle: String?,var mp: String?,var jc: String?,var posRegion:String,var posCircle:String,var posMp:String,var posJC:String) : DialogFragment(), FilteraAgingAdapter.AgingInterfaceFilterName{

    var mBinding: FragmentInfraAlermsFilterBinding?= null
    var commonBean: CommonBean?=null
    var agingdatalist: ArrayList<Map<String, Any>>?=null
    var workOrderdatalist: ArrayList<Map<String, Any>>?=null
    var impactCustomerdatalist: ArrayList<Map<String, Any>>?=null
    var regionlist: ArrayList<Map<String, Any>>?=null
    var statelist: ArrayList<Map<String, Any>>?=null
    var mplist: ArrayList<Map<String, Any>>?=null
    var jclist: ArrayList<Map<String, Any>>?=null
    var agingAdapter: FilteraAgingAdapter?=null
    var impactedAdapter: FilteraImpactedCustomerAdapter?=null
    var workerOrderAdapter: FilteraWorkOrderAdapter?=null
    var category:String?=null
    var parentCode:String?=null
    var savedRegion:String?=""
    var savedState:String?=""
    var savedMp:String?=""
    var savedJc:String?=""
    var impactedCustomerPos:String?="0"
    var workerOrderPos:String?="0"
    var clearStr:String?="0"
    var click:String?="other"
    var mOptionInterfaceFilterName: FilteraAgingAdapter.AgingInterfaceFilterName?=null
    var mImpactedCustomerInterfaceFilterName: FilteraImpactedCustomerAdapter.ImpactedCustomerInterfaceFilterName?=null
    var mWorkerOrderInterfaceFilterName: FilteraWorkOrderAdapter.WorkerOrderInterfaceFilterName?=null



    var startAge: String?=null
    var endAge: String? =null
//    var region: String?="ALL"
//    var circle: String?="ALL"
//    var mp: String?="ALL"
//    var jc: String?="ALL"

   var filterCount:String?=null

//    companion object {
//        fun newInstance() = FilterFragmentDilog()
//    }


    interface FragmentCallback {
        fun onDataSent(startAge: String?,endAge: String?,region: String?,circle: String?,mp: String?,jc: String?,agingPos:String,filterCount:String,posRegion:String,posCircle:String,posMp:String,posJC:String)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_infra_alerms_filter, container, false)

        return mBinding!!.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AppTheme)
    }

    @SuppressLint("ResourceAsColor")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mBinding!!.txtAgeing.setBackgroundResource(R.color.white)
        mOptionInterfaceFilterName=this
       // mImpactedCustomerInterfaceFilterName=this
      //  mWorkerOrderInterfaceFilterName=this

        this.savedRegion = region;
        this.savedState= circle;
        this.savedMp = mp;
        this.savedJc = jc;

        mBinding!!.listRecycleview.visibility=View.VISIBLE

        mBinding!!.crossImg.setOnClickListener {

           // dismiss()

            if(clearStr.equals("1")) {
                filterCount = "0"
                agingpos = "0"
                category = "All"
                region = "ALL"
                circle = "ALL"
                mp = "ALL"
                jc = "ALL"
                mBinding!!.crossImg.isClickable = false
                startAge = agingdatalist!!.get(agingpos.toInt())["start_rang"].toString()
                endAge = agingdatalist!!.get(agingpos.toInt())["end_rang"].toString()
                mFragmentCallback.onDataSent(
                    startAge,
                    endAge,
                    region,
                    circle,
                    mp,
                    jc,
                    agingpos,
                    filterCount!!,
                    posRegion,
                    posCircle,
                    posMp,
                    posJC
                )
                dismiss()
            }else{
                dismiss()
            }
        }
        fetchFilterAgeienDbData("INFRA_AGING_FILTER")

        filterCount="1"
        mBinding!!.txtClear.setOnClickListener {

            //category ="All"
            mBinding!!.txtClear.isClickable=false

            clearStr="1"
            region="ALL"
            circle ="ALL"
            mp ="ALL"
            jc ="ALL"
            filterCount="0"
            agingpos="0"
            startAge= agingdatalist!!.get(agingpos.toInt())["start_rang"].toString()
            endAge= agingdatalist!!.get(agingpos.toInt())["end_rang"].toString()

            agingAdapter= FilteraAgingAdapter(agingpos!!,mOptionInterfaceFilterName!!,agingdatalist!!, activity as MainActivity?)
            mBinding!!.listRecycleview.layoutManager = LinearLayoutManager(activity)
            mBinding!!.listRecycleview.itemAnimator = DefaultItemAnimator()
            mBinding!!.listRecycleview!!.adapter = agingAdapter
            agingAdapter!!.notifyDataSetChanged()

            dropdownlistSpinerRegion.setSelection(0)
            state_ll.visibility=View.GONE
            mpll.visibility=View.GONE
            jcll.visibility=View.GONE


         //   mFragmentCallback.onDataSent(startAge,endAge,region,circle,mp,jc,agingpos,filterCount!!,posRegion,posCircle,posMp,posJC)
           // dismiss()
           // dismiss()
        }

        mBinding!!.dropdownlistSpinerRegion.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                arg0: AdapterView<*>?,
                arg1: View,
                arg2: Int,
                arg3: Long
            ) { // TODO Auto-generated method stub

              //  regionlist!!.get(arg2)["name"]

                posRegion = regionlist!!.get(arg2)["name"].toString()
                category = regionlist!!.get(arg2)["name"].toString()
                if(category.equals("All")) {
                    state_ll.visibility=View.GONE
                    mpll.visibility=View.GONE
                    jcll.visibility=View.GONE
                    region="ALL"
                  //  posRegion=arg2.toString()
                }else{
                    state_ll.visibility=View.GONE
                    mpll.visibility=View.GONE
                    jcll.visibility=View.GONE


                    region=regionlist!!.get(arg2)["name"].toString()
                    parentCode = regionlist!!.get(arg2)["name"].toString()
                    fetchFilterAgeienDbData("INVENTORY_MASTER_CIRCLE")
                //    posRegion=arg2.toString()
                }

                //   val selectedItem:String = regionlist!!.get(arg2)["name"].toString()
                // Toast.makeText(activity, selectedItem, Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) { // TODO Auto-generated method stub
            }
        })


        mBinding!!.dropdownlistSpinerState.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                arg0: AdapterView<*>?,
                arg1: View,
                arg2: Int,
                arg3: Long
            ) { // TODO Auto-generated method stub

                statelist!!.get(arg2)["name"]

                posCircle=statelist!!.get(arg2)["name"].toString()
                category=statelist!!.get(arg2)["name"].toString()

                if(category.equals("All")) {
                    // state_ll.visibility=View.GONE
                    mpll.visibility=View.GONE
                    jcll.visibility=View.GONE
                    circle="All"
                  //  posCircle=arg2.toString()

                }else{
                    mpll.visibility=View.GONE
                    jcll.visibility=View.GONE

                    circle=statelist!!.get(arg2)["name"].toString()
                    fetchFilterAgeienDbData("INVENTORY_MASTER_MP")
                    parentCode = category
                    posCircle=arg2.toString()
                }

            }
            override fun onNothingSelected(arg0: AdapterView<*>?) { // TODO Auto-generated method stub
            }
        })

        mBinding!!.dropdownlistSpinerMp.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                arg0: AdapterView<*>?,
                arg1: View,
                arg2: Int,
                arg3: Long
            ) { // TODO Auto-generated method stub

                posMp= mplist!!.get(arg2)["name"].toString()

                category = mplist!!.get(arg2)["name"].toString()
                if(category.equals("All")) {
                    //    state_ll.visibility=View.GONE
                    //    mpll.visibility=View.GONE
                    mp="All"
                  //  posMp=arg2.toString()
                    jcll.visibility=View.GONE

                }else{

                    jcll.visibility=View.GONE
                    mp=mplist!!.get(arg2)["name"].toString()
                    fetchFilterAgeienDbData("INVENTORY_MASTER_JC")
                   // posMp=arg2.toString()
                }

            }

            override fun onNothingSelected(arg0: AdapterView<*>?) { // TODO Auto-generated method stub
            }
        })
        mBinding!!.dropdownlistSpinerJc.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                arg0: AdapterView<*>?,
                arg1: View,
                arg2: Int,
                arg3: Long
            ) { // TODO Auto-generated method stub


                posJC= jclist!!.get(arg2)["name"].toString()

                jc= jclist!!.get(arg2)["name"].toString()


            }

            override fun onNothingSelected(arg0: AdapterView<*>?) { // TODO Auto-generated method stub
            }
        })
        mBinding!!.txtAgeing.setOnClickListener {
            click="other"
            mBinding!!.reginId.visibility=View.GONE
            mBinding!!.listRecycleview.visibility=View.VISIBLE
          //  filterCount="1"
            mBinding!!.txtAgeing.setBackgroundResource(R.color.white)
            mBinding!!.txtWorkOrder.setBackgroundResource(R.color.bg_color_grey)
            mBinding!!.txtInpactCustomer.setBackgroundResource(R.color.bg_color_grey)
            mBinding!!.txtGeography.setBackgroundResource(R.color.bg_color_grey)

            mBinding!!.reginId.visibility=View.GONE
            mBinding!!.listRecycleview.visibility=View.VISIBLE
            if(agingdatalist!=null){
                agingAdapter= FilteraAgingAdapter(agingpos!!,mOptionInterfaceFilterName!!,agingdatalist!!, activity as MainActivity?)
                mBinding!!.listRecycleview.layoutManager = LinearLayoutManager(activity)
                mBinding!!.listRecycleview.itemAnimator = DefaultItemAnimator()
                mBinding!!.listRecycleview!!.adapter = agingAdapter
                agingAdapter!!.notifyDataSetChanged()
            }else {
                fetchFilterAgeienDbData("INFRA_AGING_FILTER")
            }

        }

        mBinding!!.txtInpactCustomer.setOnClickListener {
            click="other"
            mBinding!!.reginId.visibility=View.GONE
            mBinding!!.listRecycleview.visibility=View.VISIBLE
            mBinding!!.txtInpactCustomer.setBackgroundResource(R.color.white)
            mBinding!!.txtAgeing.setBackgroundResource(R.color.bg_color_grey)
            mBinding!!.txtWorkOrder.setBackgroundResource(R.color.bg_color_grey)
            mBinding!!.txtGeography.setBackgroundResource(R.color.bg_color_grey)


            mBinding!!.reginId.visibility=View.GONE
            mBinding!!.listRecycleview.visibility=View.VISIBLE


            if(impactCustomerdatalist!=null){
                impactedAdapter = FilteraImpactedCustomerAdapter(impactedCustomerPos!!,mImpactedCustomerInterfaceFilterName!!,impactCustomerdatalist!!, activity as MainActivity?)
                mBinding!!.listRecycleview.layoutManager = LinearLayoutManager(activity)
                mBinding!!.listRecycleview.itemAnimator = DefaultItemAnimator()
                mBinding!!.listRecycleview!!.adapter = impactedAdapter
                impactedAdapter!!.notifyDataSetChanged()
            }else {
                fetchFilterAgeienDbData("INFRA_IMPACTED_CUST_FILTER")
            }
        }
        mBinding!!.txtWorkOrder.setOnClickListener {
            click="other"
            mBinding!!.txtWorkOrder.setBackgroundResource(R.color.white)
            mBinding!!.txtInpactCustomer.setBackgroundResource(R.color.bg_color_grey)
            mBinding!!.txtAgeing.setBackgroundResource(R.color.bg_color_grey)
            mBinding!!.txtGeography.setBackgroundResource(R.color.bg_color_grey)
            mBinding!!.reginId.visibility=View.GONE
            mBinding!!.listRecycleview.visibility=View.VISIBLE
            if(workOrderdatalist!=null){
                workerOrderAdapter= FilteraWorkOrderAdapter(workerOrderPos!!,mWorkerOrderInterfaceFilterName!!,workOrderdatalist!!, activity as MainActivity?)
                mBinding!!.listRecycleview.layoutManager = LinearLayoutManager(activity)
                mBinding!!.listRecycleview.itemAnimator = DefaultItemAnimator()
                mBinding!!.listRecycleview!!.adapter = workerOrderAdapter
                workerOrderAdapter!!.notifyDataSetChanged()
            }else {
                fetchFilterAgeienDbData("INFRA_WORK_ORDER_FILTER")
            }

        }
        mBinding!!.txtGeography.setOnClickListener {
            click="Geography"
            mBinding!!.txtClear.isClickable=true
            filterCount="2"
            region="ALL"
            circle ="ALL"
            mp ="ALL"
            jc ="ALL"
            mBinding!!.txtWorkOrder.setBackgroundResource(R.color.bg_color_grey)
            mBinding!!.txtInpactCustomer.setBackgroundResource(R.color.bg_color_grey)
            mBinding!!.txtAgeing.setBackgroundResource(R.color.bg_color_grey)
            mBinding!!.txtGeography.setBackgroundResource(R.color.white)
            mBinding!!.reginId.visibility=View.VISIBLE
            mBinding!!.listRecycleview.visibility=View.GONE
            mBinding!!.regionLl.visibility=View.VISIBLE

            if(regionlist==null) {

                fetchFilterAgeienDbData("INVENTORY_MASTER_REGION")

            }


        }


        mBinding!!.btnApply.setOnClickListener {



            startAge= agingdatalist!!.get(agingpos.toInt())["start_rang"].toString()
            endAge= agingdatalist!!.get(agingpos.toInt())["end_rang"].toString()
            if(region!!.equals("ALL")){
                circle ="ALL"
                mp ="ALL"
                jc ="ALL"
                filterCount="1"
            }else{
                filterCount="1"
            }

            if(circle!!.equals("ALL")){
                mp ="ALL"
                jc ="ALL"
            }

            if(mp!!.equals("ALL")){

                jc ="ALL"
            }

            if(startAge!!.equals("ALL")&&endAge!!.equals("ALL")){
                filterCount="0"
            }else{
                filterCount="2"
            }


            mFragmentCallback!!.onDataSent(startAge,endAge,region,circle,mp,jc,agingpos,filterCount!!,posRegion,posCircle,posMp,posJC)
            dismiss()

        }

    }
    fun setData(commonBean: CommonBean, mFragmentCallback:FragmentCallback) {
        this.commonBean = commonBean
        this.mFragmentCallback = mFragmentCallback



    }
    fun fetchFilterAgeienDbData(ddqCode:String) {

        (activity as MainActivity).showProgressBar()
        val requestBody = HashMap<String, Any>()

        if(click.equals("Geography")) {
            if(category!=null) {
                requestBody["category"] = category!!
                requestBody["parentCode"] = parentCode!!
            }
        }
        //  requestBody["appRoleCode"] = "723"
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "userInfo"


        requestBody["ddqCode"] =ddqCode

        CoroutineScope(Dispatchers.IO).launch {

            var job = async { BaseCoroutines().fetchData(requestBody,   Busicode.Filter, activity as MainActivity) }
            withContext(Dispatchers.Main)
            {

                var response = job.await()
                if (activity != null) {
                    (activity!! as MainActivity).hideProgressBar()
                }

                if (response!!.responseEntity != null && response.status == MappActor.STATUS_OK) {
                    filterReffreshData(response,ddqCode)
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



    fun filterReffreshData(mCoroutinesResponse: CoroutinesResponse, ddqCode:String) {

        try {
            val msg = mCoroutinesResponse.responseEntity as HashMap<String, Any>


            if (msg != null) {
                try {

                    if(ddqCode.equals("INFRA_AGING_FILTER")) {
                        agingdatalist = msg["filter"] as ArrayList<Map<String, Any>>
                        agingAdapter= FilteraAgingAdapter(agingpos!!,mOptionInterfaceFilterName!!,agingdatalist!!, activity as MainActivity?)
                        mBinding!!.listRecycleview.layoutManager = LinearLayoutManager(activity)
                        mBinding!!.listRecycleview.itemAnimator = DefaultItemAnimator()
                        mBinding!!.listRecycleview!!.adapter = agingAdapter
                        agingAdapter!!.notifyDataSetChanged()
                    }else if(ddqCode.equals("INFRA_IMPACTED_CUST_FILTER")){
                        impactCustomerdatalist = msg["filter"] as ArrayList<Map<String, Any>>

                        impactedAdapter = FilteraImpactedCustomerAdapter(impactedCustomerPos!!,mImpactedCustomerInterfaceFilterName!!,impactCustomerdatalist!!, activity as MainActivity?)
                        mBinding!!.listRecycleview.layoutManager = LinearLayoutManager(activity)
                        mBinding!!.listRecycleview.itemAnimator = DefaultItemAnimator()
                        mBinding!!.listRecycleview!!.adapter = impactedAdapter
                        impactedAdapter!!.notifyDataSetChanged()
                    }else if(ddqCode.equals("INFRA_WORK_ORDER_FILTER")){
                        workOrderdatalist = msg["filter"] as ArrayList<Map<String, Any>>
                        workerOrderAdapter= FilteraWorkOrderAdapter(workerOrderPos!!,mWorkerOrderInterfaceFilterName!!,workOrderdatalist!!, activity as MainActivity?)
                        mBinding!!.listRecycleview.layoutManager = LinearLayoutManager(activity)
                        mBinding!!.listRecycleview.itemAnimator = DefaultItemAnimator()
                        mBinding!!.listRecycleview!!.adapter = workerOrderAdapter
                        workerOrderAdapter!!.notifyDataSetChanged()
                    }else if(ddqCode.equals("INVENTORY_MASTER_REGION")){
                        regionlist= msg["filter"] as ArrayList<Map<String, Any>>
//                        regionMutablelist= msg["filter"] as MutableList<Map<String, Any>>



                        var spinnerAdapter: RegionCustomDropDownAdapter = RegionCustomDropDownAdapter(activity as MainActivity?, regionlist)
                        mBinding!!.dropdownlistSpinerRegion?.adapter = spinnerAdapter

                        if(savedRegion.equals("")){

                        }else {
                            for ((index, value) in regionlist!!.withIndex()) {
                                val regionMap = value
                                var regionSelected = regionMap.get("name") as String

                                if (savedRegion.equals(regionSelected)) {

                                    mBinding!!.dropdownlistSpinerRegion?.setSelection(index)
                                    savedRegion = ""

                                }
                            }
                        }



                    }else if(ddqCode.equals("INVENTORY_MASTER_CIRCLE")){
                        statelist= msg["filter"] as ArrayList<Map<String, Any>>


                        var spinnerAdapter: StateCustomDropDownAdapter = StateCustomDropDownAdapter(activity as MainActivity?, statelist)
                        mBinding!!.dropdownlistSpinerState?.adapter = spinnerAdapter
                        mBinding!!.stateLl.visibility=View.VISIBLE
                        if(savedState.equals("")){

                        }else {
                            for ((index, value) in statelist!!.withIndex()) {
                                val regionMap = value
                                var stateSelected = regionMap.get("name") as String

                                if (savedState.equals(stateSelected)) {

                                    mBinding!!.dropdownlistSpinerState?.setSelection(index)
                                    savedState = ""

                                }
                            }
                        }

                    }else if(ddqCode.equals("INVENTORY_MASTER_MP")){
                        mplist= msg["filter"] as ArrayList<Map<String, Any>>


                        var spinnerAdapter: MpCustomDropDownAdapter = MpCustomDropDownAdapter(activity as MainActivity?, mplist)
                        mBinding!!.dropdownlistSpinerMp?.adapter = spinnerAdapter
                        mBinding!!.mpll.visibility=View.VISIBLE
                        if(savedMp.equals("")){

                        }else {
                            for ((index, value) in mplist!!.withIndex()) {
                                val mpMap = value
                                var mpSelected = mpMap.get("name") as String

                                if (savedMp.equals(mpSelected)) {

                                    mBinding!!.dropdownlistSpinerMp?.setSelection(index)
                                    savedMp = ""

                                }
                            }
                        }


                    }else if(ddqCode.equals("INVENTORY_MASTER_JC")){
                        jclist= msg["filter"] as ArrayList<Map<String, Any>>


                        var spinnerAdapter: JcCustomDropDownAdapter = JcCustomDropDownAdapter(activity as MainActivity?, jclist)
                        mBinding!!.dropdownlistSpinerJc?.adapter = spinnerAdapter
                        mBinding!!.jcll.visibility=View.VISIBLE

                      if(savedJc.equals("")){

                      }else{
                        for ((index, value) in jclist!!.withIndex()) {
                            val jcMap = value
                            var jcSelected = jcMap.get("name") as String

                            if (savedJc.equals(jcSelected)) {

                                mBinding!!.dropdownlistSpinerJc?.setSelection(index)
                                savedJc=""

                            }
                        }
                        }

                    }

                } catch (e: Exception) {
                    MyExceptionHandler.handle(e)
                }
            }
        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
            T.show(activity, activity!!.getString(R.string.something_went_wrong), 0)
        }
    }


//    override fun impactedClickInterface(value: String) {
//        impactedCustomerPos=value
//
//    }
//
//    override fun workerOrderClickInterface(value: String) {
//
//        workerOrderPos=value
//
//
//    }

    override fun agingclickInterface(value: String, startAage: String, endage: String) {

        agingpos=value
        startAge=startAage
        endAge=endage
        //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}