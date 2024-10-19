package com.jio.siops_ngo.infra.fragment

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.jio.jioinfra.bean.CommonBean
import com.jio.jioinfra.network.business.BaseCoroutines
import com.jio.jioinfra.ui.dashboard.adapter.InfraMaintenanceListAdapter
import com.jio.jioinfra.utilities.Busicode
import com.jio.jioinfra.utilities.MyConstants
import com.jio.myjio.bean.CoroutinesResponse
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.bean.FilterGetterAndSetter
import com.jio.siops_ngo.databinding.InfraMaintenanceInnerFragmentBinding
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.utilities.*
import kotlinx.coroutines.*
import java.lang.Exception

class InfraMaintenanceFragment : androidx.fragment.app.Fragment(),
    FilterSiteDownFragmentDialog.FragmentCallback {
    var commonBean:CommonBean?=null
    var mBinding: InfraMaintenanceInnerFragmentBinding?= null
    var selection: Int? = 0
    var openAlertsDataMap: Map<String, List<Map<String, Any>>>? = null
    var dataListNgoItem: ArrayList<Map<String, Any>>?=null
    var datalist: ArrayList<Map<String, Any>>?=null
    var openAlertsDataList: List<Map<String, Any>>? = null
    var boldTypeface: Typeface? = null
    var lightTypeface: Typeface? = null
    var outLierId: String? = null
    var name:String? = "All"
    var count:String? = null
    var mFragmentCallback: FilterSiteDownFragmentDialog.FragmentCallback?=null

    val mFilterGetterAndSetter= FilterGetterAndSetter()
    var category:String?="All"
    var agingPoss:String?="0"
    var workerOrderPoss:String?="0"
    var impactedCustomerPoss:String?="0"
    var regionn: String?="ALL"
    var circlee: String?="ALL"
    var mpp: String?="ALL"
    var jcc: String?="ALL"
    var posRegionn:String?="0"
    var posCirclee:String?="0"
    var posMpp:String?="0"
    var posJCc:String?="0"
    var startrang:String?="All"
    var endrang:String?="All"
    var filterCountValue:String?=null

    companion object {
        fun newInstance() = InfraMaintenanceFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.infra_maintenance_inner_fragment, container, false)

        return mBinding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        boldTypeface = ResourcesCompat.getFont(activity!!, R.font.jio_type_bold)
        lightTypeface = ResourcesCompat.getFont(activity!!, R.font.jio_type_light)
        mBinding!!.txtDashboardTitle.visibility=View.VISIBLE
        mBinding!!.constanthide.visibility=View.GONE
        mBinding!!.cnstrntLMaintenanceData1.visibility=View.GONE
        mBinding!!.rvMaintenanceList.visibility=View.GONE
        mFragmentCallback=this
        fetchDashboardDbData()

        mFilterGetterAndSetter.setStartAging("All")
        mFilterGetterAndSetter.setEndAging("All")
        mFilterGetterAndSetter.setRegian("All")
        mFilterGetterAndSetter.setCircle("All")
        mFilterGetterAndSetter.setMp("All")
        mFilterGetterAndSetter.setJc("All")
        mFilterGetterAndSetter.setStartimpactedCustomer("All")
        mFilterGetterAndSetter.setEndimpactedCustomer("All")
        clickListiner()
    }


    fun fetchDashboardDbData() {
        (activity as MainActivity).showProgressBar()
        val requestBody = HashMap<String, Any>()

         requestBody["outlierId"] =  outLierId!!
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "userInfo"
        CoroutineScope(Dispatchers.IO).launch {

            var job = async { BaseCoroutines().fetchData(requestBody,   Busicode.SiteDownSummary, activity as MainActivity) }
            withContext(Dispatchers.Main)
            {

                var response = job.await()
                if (activity != null) {
                    (activity!! as MainActivity).hideProgressBar()
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
                    dataListNgoItem = msg["sitesList"] as ArrayList<Map<String, Any>>
                    datalist = msg["list"] as ArrayList<Map<String, Any>>
                    if(Utils.hasIndex(0,dataListNgoItem!!)) {
                        if (dataListNgoItem!!.get(0).containsKey("count")) {
                            mBinding!!.count1.text = dataListNgoItem!!.get(0)["count"].toString()
                        }
                        if (dataListNgoItem!!.get(0).containsKey("name")) {
                            mBinding!!.title1.text = dataListNgoItem!!.get(0)["name"].toString()
                        }
                    }

                    if(Utils.hasIndex(1,dataListNgoItem!!)) {
                        if (dataListNgoItem!!.get(1).containsKey("count")) {
                            mBinding!!.count2.text = dataListNgoItem!!.get(1)["count"].toString()
                        }
                        if (dataListNgoItem!!.get(1).containsKey("name")) {
                            mBinding!!.title2.text = dataListNgoItem!!.get(1)["name"].toString()
                        }
                    }
                    if(Utils.hasIndex(2,dataListNgoItem!!)) {
                        if (dataListNgoItem!!.get(2).containsKey("count")) {
                            mBinding!!.count3.text = dataListNgoItem!!.get(2)["count"].toString()
                        }
                        if (dataListNgoItem!!.get(2).containsKey("name")) {
                            mBinding!!.title3.text = dataListNgoItem!!.get(2)["name"].toString()
                        }
                    }else{
                        mBinding!!.card3.visibility = View.GONE
                    }

                    if(Utils.hasIndex(3,dataListNgoItem!!)) {
                        if (dataListNgoItem!!.get(3).containsKey("count")) {
                            mBinding!!.count4.text = dataListNgoItem!!.get(3)["count"].toString()
                        }
                        if (dataListNgoItem!!.get(3).containsKey("name")) {
                            mBinding!!.title4.text = dataListNgoItem!!.get(3)["name"].toString()
                        }
                    }else{
                        mBinding!!.card4.visibility = View.GONE
                    }
                 //   mBinding!!.constanthide.visibility=View.VISIBLE
                    mBinding!!.constanthide.visibility=View.VISIBLE
//                    mBinding!!.cnstrntLMaintenanceData1.visibility=View.VISIBLE
                    mBinding!!.rvMaintenanceList.visibility=View.VISIBLE
                    mBinding!!.txtDashboardTitle.text=name+" "+count

                    var adapter = InfraMaintenanceListAdapter(
                        datalist!!,
                        activity as MainActivity?,
                        outLierId!!,
                        name!!,category!!,
                        mFilterGetterAndSetter!!
                    )
                    mBinding!!.rvMaintenanceList.layoutManager = LinearLayoutManager(activity)
                    mBinding!!.rvMaintenanceList.itemAnimator = DefaultItemAnimator()
                    mBinding!!.rvMaintenanceList!!.adapter = adapter
                    adapter!!.notifyDataSetChanged()
                    mBinding!!.cnstrntLMaintenanceData1.visibility=View.VISIBLE
                } catch (e: Exception) {
                    MyExceptionHandler.handle(e)
                }
            }
        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
            T.show(activity, activity!!.getString(R.string.something_went_wrong), 0)
        }
    }



    fun setData(commonBean: CommonBean, outLierId: String,name:String,count:String) {
        this.commonBean = commonBean
        this.outLierId = outLierId
        this.name = name
        this.count = count

    }

    fun clickListiner(){



        if( PreferenceUtility.getString(activity,"FilterApply","").equals("1")){

        }

        mBinding!!.filterId.setOnClickListener {

            val ft: FragmentTransaction = fragmentManager!!.beginTransaction()
            val prev =
                fragmentManager!!.findFragmentByTag("dialog")
            if (prev != null) {
                ft.remove(prev)
            }
            ft.addToBackStack(null)
            var filterFragmentOpneSite: DialogFragment = FilterSiteDownFragmentDialog(mFragmentCallback!!,agingPoss!!,regionn,circlee,mpp,jcc,posRegionn!!,posCirclee!!,posMpp!!,posJCc!!,startrang!!,endrang!! ,workerOrderPoss!!,impactedCustomerPoss!!)
            var commonBean =  CommonBean()
            //    filterFragmentAlerms.show("","")
            filterFragmentOpneSite.show(ft, "dialog");
            //   filterFragmentAlerms.setData(commonBean,mFragmentCallback!!)
            // val dialogFragment: DialogFragment = MyCustomDialogFragment()
            //  dialogFragment.show(ft, "dialog")


            filterFragmentOpneSite.showsDialog

        }

       mBinding!!.card1.setOnClickListener {
           mBinding!!.count1.setTypeface(boldTypeface)
           mBinding!!.count2.setTypeface(lightTypeface)
           mBinding!!.count3.setTypeface(lightTypeface)
           mBinding!!.count4.setTypeface(lightTypeface)
           mBinding!!.title1.text.trim().toString()
         //  fetchSiteDownDbData(mBinding!!.title1.text.trim().toString())
           category=mBinding!!.title1.text.trim().toString()

           filterCountValue="0"
         agingPoss="0"
          workerOrderPoss="0"
          impactedCustomerPoss="0"
            regionn="ALL"
           circlee="ALL"
          mpp="ALL"
            jcc="ALL"
           if(filterCountValue=="0") {
               mBinding!!.txtfilter.visibility = View.GONE
               //  mBinding!!.txtfilter.text = filterCount!!
           }
           fetchSiteDownDbData()
       }

        mBinding!!.card2.setOnClickListener {
            mBinding!!.count1.setTypeface(lightTypeface)
            mBinding!!.count2.setTypeface(boldTypeface)
            mBinding!!.count3.setTypeface(lightTypeface)
            mBinding!!.count4.setTypeface(lightTypeface)
            mBinding!!.title2.text.trim().toString()
          //  fetchSiteDownDbData(mBinding!!.title2.text.trim().toString())
            category=mBinding!!.title2.text.trim().toString()
            fetchSiteDownDbData()

            filterCountValue="0"
            agingPoss="0"
            workerOrderPoss="0"
            impactedCustomerPoss="0"
            regionn="ALL"
            circlee="ALL"
            mpp="ALL"
            jcc="ALL"
            if(filterCountValue=="0") {
                mBinding!!.txtfilter.visibility = View.GONE
                //  mBinding!!.txtfilter.text = filterCount!!
            }
        }
        mBinding!!.card3.setOnClickListener {
            mBinding!!.count1.setTypeface(lightTypeface)
            mBinding!!.count2.setTypeface(lightTypeface)
            mBinding!!.count3.setTypeface(boldTypeface)
            mBinding!!.count4.setTypeface(lightTypeface)
            mBinding!!.title3.text.trim().toString()
            category=mBinding!!.title3.text.trim().toString()
            fetchSiteDownDbData()

            filterCountValue="0"
            agingPoss="0"
            workerOrderPoss="0"
            impactedCustomerPoss="0"
            regionn="ALL"
            circlee="ALL"
            mpp="ALL"
            jcc="ALL"
            if(filterCountValue=="0") {
                mBinding!!.txtfilter.visibility = View.GONE
                //  mBinding!!.txtfilter.text = filterCount!!
            }
        }

        mBinding!!.card4.setOnClickListener {
            mBinding!!.count1.setTypeface(lightTypeface)
            mBinding!!.count2.setTypeface(lightTypeface)
            mBinding!!.count3.setTypeface(lightTypeface)
            mBinding!!.count4.setTypeface(boldTypeface)
            category=mBinding!!.title4.text.trim().toString()
            fetchSiteDownDbData()

            filterCountValue="0"
            agingPoss="0"
            workerOrderPoss="0"
            impactedCustomerPoss="0"
            regionn="ALL"
            circlee="ALL"
            mpp="ALL"
            jcc="ALL"
            if(filterCountValue=="0") {
                mBinding!!.txtfilter.visibility = View.GONE
                //  mBinding!!.txtfilter.text = filterCount!!
            }
        }

    }






    fun fetchSiteDownDbData() {

        (activity as MainActivity).showProgressBar()
        val requestBody = HashMap<String, Any>()

        requestBody["outlierId"] =  outLierId!!
        requestBody["category"] = category!!
        //  requestBody["appRoleCode"] = "723"
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "userInfo"
        CoroutineScope(Dispatchers.IO).launch {

            var job = async { BaseCoroutines().fetchData(requestBody,   Busicode.SiteDownCategory, activity as MainActivity) }
            withContext(Dispatchers.Main)
            {

                var response = job.await()
                if (activity != null) {
                    (activity!! as MainActivity).hideProgressBar()
                }


                if (response!!.responseEntity != null && response.status == MappActor.STATUS_OK) {
                    filterReffreshData(response)
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




    fun filterReffreshData(mCoroutinesResponse: CoroutinesResponse) {

        try {
            val msg = mCoroutinesResponse.responseEntity as HashMap<String, Any>


            if (msg != null) {
                try {
                    datalist!!.clear()
                  //  dataListNgoItem = msg["sitesList"] as ArrayList<Map<String, Any>>
                    datalist = msg["list"] as ArrayList<Map<String, Any>>
                    //   mBinding!!.constanthide.visibility=View.VISIBLE
                    mBinding!!.constanthide.visibility=View.VISIBLE
//                    mBinding!!.cnstrntLMaintenanceData1.visibility=View.VISIBLE
                    mBinding!!.rvMaintenanceList.visibility=View.VISIBLE
                    mBinding!!.txtDashboardTitle.text=name+" "+count

                    var adapter = InfraMaintenanceListAdapter(
                        datalist!!,
                        activity as MainActivity?,
                        outLierId!!,
                        name!!,
                        category!!,
                        mFilterGetterAndSetter!!
                    )
                    mBinding!!.rvMaintenanceList.layoutManager = LinearLayoutManager(activity)
                    mBinding!!.rvMaintenanceList.itemAnimator = DefaultItemAnimator()
                    mBinding!!.rvMaintenanceList!!.adapter = adapter
                    adapter!!.notifyDataSetChanged()
                } catch (e: Exception) {
                    MyExceptionHandler.handle(e)
                }
            }
        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
            T.show(activity, activity!!.getString(R.string.something_went_wrong), 0)
        }
    }
    override fun onDataSent(
        startAge: String?,
        endAge: String?,
        region: String?,
        circle: String?,
        mp: String?,
        jc: String?,
        agingPos: String,
        filterCount: String,
        posRegion: String,
        posCircle: String,
        posMp: String,
        posJC: String,
        impacted_customer_Start: String,
        impacted_customer_end: String,
        impactedCustomerPos: String
    ) {
        agingPoss=agingPos
        impactedCustomerPoss=impactedCustomerPos
        regionn=region!!
        circlee=circle
        mpp=mp
        jcc=jc
        posRegionn=posRegion
        posCirclee=posCircle
        posMpp=posMpp
        posJCc=posJC

        startrang=impacted_customer_Start
        endrang=impacted_customer_end
        filterCountValue=filterCount
        if(filterCountValue=="0") {
            mBinding!!.txtfilter.visibility = View.GONE
            //  mBinding!!.txtfilter.text = filterCount!!
        }else{
            mBinding!!.txtfilter.visibility = View.VISIBLE
            mBinding!!.txtfilter.text = filterCount!!

        }

        fetchFilterDbData(startAge,endAge,region,circle,mp,jc,impacted_customer_Start,impacted_customer_end)
    }
    fun fetchFilterDbData(startAge: String?,endAge: String?,region: String?,circle: String?,mp: String?,jc: String?,startIC:String,endIC:String) {
        (activity as MainActivity).showProgressBar()
        val requestBody = HashMap<String, Any>()
        val requestBodyfilters = HashMap<String, Any>()
        mFilterGetterAndSetter.setStartAging(startAge!!)
        mFilterGetterAndSetter.setEndAging(endAge!!)
        mFilterGetterAndSetter.setRegian(region!!)
        mFilterGetterAndSetter.setCircle(circle!!)
        mFilterGetterAndSetter.setMp(mp!!)
        mFilterGetterAndSetter.setJc(jc!!)
        mFilterGetterAndSetter.setStartimpactedCustomer(startIC!!)
        mFilterGetterAndSetter.setEndimpactedCustomer(endIC!!)

        //  requestBody["outlierType"] = dataMap!!["featureName"] as String
        requestBody["appRoleCode"] = "723"
        requestBody["category"] =category!!
        requestBody["outlierId"] =outLierId!!


        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "userInfo"
        requestBodyfilters["startAge"]=startAge!!
        requestBodyfilters["endAge"]=endAge!!
        requestBodyfilters["startIC"]=startIC!!
        requestBodyfilters["endIC"]=endIC!!
        requestBodyfilters["region"]=region!!
        requestBodyfilters["circle"]=circle!!
        requestBodyfilters["mp"]=mp!!
        requestBodyfilters["jc"]=jc!!
        requestBody["filters"] =requestBodyfilters
        CoroutineScope(Dispatchers.IO).launch {

            var job = async {
                BaseCoroutines().fetchData(
                    requestBody,
                    Busicode.SiteDownCategory,
                    activity as MainActivity
                )
            }
            withContext(Dispatchers.Main)
            {

                var response = job.await()
                if (activity != null) {
                    (activity!! as MainActivity).hideProgressBar()
                }


                if (response!!.responseEntity != null && response.status == MappActor.STATUS_OK) {
                    filterCategoryWiseData(response,mFilterGetterAndSetter)
                } else if (response!!.errorCode != null && response!!.errorCode.equals(MappActor.VERSION_SESSION_INVALID)){
                    (activity as MainActivity).showDialogForSessionExpired((activity as MainActivity).resources.getString(R.string.session_expired), (activity as MainActivity))
                } else if (response!!.errorMsg != null) {
                    if(datalist!=null) {
                        datalist!!.clear()
                        mBinding!!.rvMaintenanceList!!.adapter!!.notifyDataSetChanged()
                    }
                    T.show(activity, response!!.errorMsg!!, 0)
                } else {
                    if(datalist!=null) {
                        datalist!!.clear()
                        mBinding!!.rvMaintenanceList!!.adapter!!.notifyDataSetChanged()
                    }
                    T.show(activity, "Something went wrong!", 0)
                }
            }

        }
    }
    fun filterCategoryWiseData(mCoroutinesResponse: CoroutinesResponse,mFilterGetterAndSetter:FilterGetterAndSetter) {

        try {
            val msg = mCoroutinesResponse.responseEntity as HashMap<String, Any>


            if (msg != null) {
                try {


                    datalist = msg["list"] as ArrayList<Map<String, Any>>


                    mBinding!!.constanthide.visibility = View.VISIBLE


                    var adapter = InfraMaintenanceListAdapter(datalist!!,activity as MainActivity?,outLierId!!,name!!,category!!,mFilterGetterAndSetter!!)
                    mBinding!!.rvMaintenanceList.layoutManager = LinearLayoutManager(activity)
                    mBinding!!.rvMaintenanceList.itemAnimator = DefaultItemAnimator()
                    mBinding!!.rvMaintenanceList!!.adapter = adapter
                    adapter!!.notifyDataSetChanged()


                } catch (e: Exception) {
                    MyExceptionHandler.handle(e)
                }
            }

        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
            T.show(activity, activity!!.getString(R.string.something_went_wrong), 0)
        }
    }

}




