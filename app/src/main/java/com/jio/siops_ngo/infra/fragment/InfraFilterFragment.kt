/*
package com.jio.siops.infra.fragment



import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.jio.jioinfra.bean.CommonBean
import com.jio.jioinfra.network.business.BaseCoroutines
import com.jio.jioinfra.utilities.Busicode
import com.jio.jioinfra.utilities.MyConstants
import com.jio.myjio.bean.CoroutinesResponse
import com.jio.siops.MainActivity
import com.jio.siops.R
import com.jio.siops.databinding.FragmentInfraFilterBinding
import com.jio.siops.infra.adapter.*
import com.jio.siops.network.MappActor
import com.jio.siops.utilities.MyExceptionHandler
import com.jio.siops.utilities.PreferenceUtility
import com.jio.siops.utilities.T
import kotlinx.android.synthetic.main.fragment_infra_filter.*
import kotlinx.coroutines.*


*/
/**
 * A simple [Fragment] subclass.
 *//*

class InfraFilterFragment : Fragment(), FilteraAgingAdapter.AgingInterfaceFilterName,
    FilteraImpactedCustomerAdapter.ImpactedCustomerInterfaceFilterName,
    FilteraWorkOrderAdapter.WorkerOrderInterfaceFilterName {
    override fun agingclickInterface(value: String, startAage: String, endage: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var mBinding: FragmentInfraFilterBinding?= null
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
    var agingpos:String?="0"
    var impactedCustomerPos:String?="0"
    var workerOrderPos:String?="0"
    var click:String?="other"
    var mOptionInterfaceFilterName: FilteraAgingAdapter.AgingInterfaceFilterName?=null
    var mImpactedCustomerInterfaceFilterName: FilteraImpactedCustomerAdapter.ImpactedCustomerInterfaceFilterName?=null
    var mWorkerOrderInterfaceFilterName: FilteraWorkOrderAdapter.WorkerOrderInterfaceFilterName?=null

    companion object {
        fun newInstance() = InfraFilterFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_infra_filter, container, false)

        return mBinding!!.root
      //  return inflater.inflate(R.layout.fragment_short_filter, container, false)
    }

    @SuppressLint("ResourceAsColor")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mBinding!!.txtAgeing.setBackgroundResource(R.color.white)
        mOptionInterfaceFilterName=this
        mImpactedCustomerInterfaceFilterName=this
        mWorkerOrderInterfaceFilterName=this

        mBinding!!.listRecycleview.visibility=View.VISIBLE
        fetchFilterAgeienDbData("INFRA_AGING_FILTER")

        mBinding!!.dropdownlistSpinerRegion.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                arg0: AdapterView<*>?,
                arg1: View,
                arg2: Int,
                arg3: Long
            ) { // TODO Auto-generated method stub

                regionlist!!.get(arg2)["name"]
                category = regionlist!!.get(arg2)["name"].toString()
                if(category.equals("All")) {
                    state_ll.visibility=View.GONE
                    mpll.visibility=View.GONE
                    jcll.visibility=View.GONE
                }else{
                    state_ll.visibility=View.GONE
                    mpll.visibility=View.GONE
                    jcll.visibility=View.GONE
                    parentCode = regionlist!!.get(arg2)["name"].toString()
                    fetchFilterAgeienDbData("INVENTORY_MASTER_CIRCLE")
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
                category=statelist!!.get(arg2)["name"].toString()

                if(category.equals("All")) {
                   // state_ll.visibility=View.GONE
                    mpll.visibility=View.GONE
                    jcll.visibility=View.GONE

                }else{

                  //  state_ll.visibility=View.GONE
                    mpll.visibility=View.GONE
                    jcll.visibility=View.GONE
                    fetchFilterAgeienDbData("INVENTORY_MASTER_MP")
                    parentCode = category
                }

              //  val selectedItem:String = statelist!!.get(arg2)["name"].toString()
                //   val selectedItem: String =  mBinding!!.dropdownlistSpinerRegion.getSelectedItem().toString()
                //Toast.makeText(activity, selectedItem, Toast.LENGTH_SHORT).show()
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

                category = mplist!!.get(arg2)["name"].toString()
                if(category.equals("All")) {
                //    state_ll.visibility=View.GONE
                //    mpll.visibility=View.GONE
                    jcll.visibility=View.GONE

                }else{

                    jcll.visibility=View.GONE
                    fetchFilterAgeienDbData("INVENTORY_MASTER_JC")
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

//                jclist!!.get(arg2)["name"]
           //     category= jclist!!.get(arg2)["name"].toString()
//                parentCode= regionlist!!.get(arg2)["name"].toString()
             //   fetchFilterAgeienDbData("INVENTORY_MASTER_CIRCLE")

              //  val selectedItem:String = jclist!!.get(arg2)["name"].toString()
                  // val selectedItem: String =  mBinding!!.dropdownlistSpinerRegion.getSelectedItem().toString()
              //  Toast.makeText(activity, selectedItem, Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) { // TODO Auto-generated method stub
            }
        })
        mBinding!!.txtAgeing.setOnClickListener {
            click="other"
            mBinding!!.reginId.visibility=View.GONE
            mBinding!!.listRecycleview.visibility=View.VISIBLE

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

        }



    fun setData(commonBean: CommonBean, fragmenTag: String) {
        this.commonBean = commonBean
        this.commonBean = commonBean


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
                } else if (response!!.errorMsg != null) {
                    T.show(activity, response!!.errorMsg!!, 0)
                } else {
                    T.show(activity, "Something went wrong!", 0)
                }
            }

        }
    }



    fun filterReffreshData(mCoroutinesResponse: CoroutinesResponse,ddqCode:String) {

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
                        var spinnerAdapter: RegionCustomDropDownAdapter = RegionCustomDropDownAdapter(activity as MainActivity?, regionlist)
                        mBinding!!.dropdownlistSpinerRegion?.adapter = spinnerAdapter

                    }else if(ddqCode.equals("INVENTORY_MASTER_CIRCLE")){
                    statelist= msg["filter"] as ArrayList<Map<String, Any>>


                    var spinnerAdapter: StateCustomDropDownAdapter = StateCustomDropDownAdapter(activity as MainActivity?, statelist)
                    mBinding!!.dropdownlistSpinerState?.adapter = spinnerAdapter
                        mBinding!!.stateLl.visibility=View.VISIBLE

                }else if(ddqCode.equals("INVENTORY_MASTER_MP")){
                        mplist= msg["filter"] as ArrayList<Map<String, Any>>


                        var spinnerAdapter: MpCustomDropDownAdapter = MpCustomDropDownAdapter(activity as MainActivity?, mplist)
                        mBinding!!.dropdownlistSpinerMp?.adapter = spinnerAdapter
                        mBinding!!.mpll.visibility=View.VISIBLE

                    }else if(ddqCode.equals("INVENTORY_MASTER_JC")){
                        jclist= msg["filter"] as ArrayList<Map<String, Any>>


                        var spinnerAdapter: JcCustomDropDownAdapter = JcCustomDropDownAdapter(activity as MainActivity?, jclist)
                        mBinding!!.dropdownlistSpinerJc?.adapter = spinnerAdapter
                        mBinding!!.jcll.visibility=View.VISIBLE

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

//    override fun agingclickInterface(value: String) {
//
//        agingpos=value
//
//    }

    override fun impactedClickInterface(value: String) {
        impactedCustomerPos=value

    }

    override fun workerOrderClickInterface(value: String) {

        workerOrderPos=value

    }

}
*/
