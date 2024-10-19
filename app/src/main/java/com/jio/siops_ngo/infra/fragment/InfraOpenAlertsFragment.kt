package com.jio.siops_ngo.fragment


import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.jio.jioinfra.bean.CommonBean
import com.jio.jioinfra.network.business.BaseCoroutines
import com.jio.jioinfra.utilities.Busicode
import com.jio.jioinfra.utilities.MyConstants
import com.jio.myjio.bean.CoroutinesResponse
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.adapter.InfraOpenAlarmsAdapter
import com.jio.siops_ngo.bean.FilterGetterAndSetter
import com.jio.siops_ngo.databinding.FragmentInfraOpenAlertsBinding
import com.jio.siops_ngo.infra.fragment.FilterFragmentDilog
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.utilities.*
import kotlinx.coroutines.*


/**
 * A simple [Fragment] subclass.
 */
class InfraOpenAlertsFragment : Fragment(), FilterFragmentDilog.FragmentCallback {


    var mBinding: FragmentInfraOpenAlertsBinding? = null
    var commonBean: CommonBean? = null
    var dataListInfraItem: ArrayList<Map<String, Any>>? = null
    var datalist: ArrayList<Map<String, Any>>? = null
    var boldTypeface: Typeface? = null
    var lightTypeface: Typeface? = null
 //   var outLierId : String? = null

    var category:String?="All"
    var agingPoss:String?="0"
    var regionn: String?="ALL"
    var circlee: String?="ALL"
    var mpp: String?="ALL"
    var jcc: String?="ALL"

    var posRegionn:String?="0"
    var posCirclee:String?="0"
    var posMpp:String?="0"
    var posJCc:String?="0"
    var name:String? = "All"
    var count:String? = null

    var mFilterGetterAndSetter= FilterGetterAndSetter()

   var mFragmentCallback: FilterFragmentDilog.FragmentCallback?=null

    var adapter: InfraOpenAlarmsAdapter? = null

    companion object {
        fun newInstance() = InfraOpenAlertsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_infra_open_alerts, container, false)
        //  openAlertsDataMap = commonBean!!.`object` as Map<String, List<Map<String, Any>>>

        mBinding!!.txtSeeMore.visibility = View.GONE
        mBinding!!.txtSeeMore.setOnClickListener { seeMoreData() }
        return mBinding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        boldTypeface = ResourcesCompat.getFont(activity!!, R.font.jio_type_bold)
        lightTypeface = ResourcesCompat.getFont(activity!!, R.font.jio_type_light)

        mFilterGetterAndSetter.setStartAging("All")
        mFilterGetterAndSetter.setEndAging("All")
        mFilterGetterAndSetter.setRegian("All")
        mFilterGetterAndSetter.setCircle("All")
        mFilterGetterAndSetter.setMp("All")
        mFilterGetterAndSetter.setJc("All")
      //  mFilterGetterAndSetter.setStartimpactedCustomer("All")
      //  mFilterGetterAndSetter.setEndimpactedCustomer("All")

        mFragmentCallback=this
        mBinding!!.card1.setOnClickListener {
            changeSelection(0)
            agingPoss="0"
            regionn="ALL"
            circlee="ALL"
            mpp="ALL"
            jcc="ALL"
            mBinding!!.txtfilter.visibility = View.GONE
            category =mBinding!!.title1!!.getText().toString()
            fetchCategoryWiseData(mBinding!!.title1!!.getText().toString(),mFilterGetterAndSetter)
        }
        mBinding!!.card2.setOnClickListener {
            changeSelection(1)
            agingPoss="0"
            regionn="ALL"
            circlee="ALL"
            mpp="ALL"
            jcc="ALL"
            mBinding!!.txtfilter.visibility = View.GONE
            category =mBinding!!.title2!!.getText().toString()
            fetchCategoryWiseData(mBinding!!.title2!!.getText().toString(),mFilterGetterAndSetter)

        }

        mBinding!!.card3.setOnClickListener {
            changeSelection(2)

            agingPoss="0"
            regionn="ALL"
            circlee="ALL"
            mpp="ALL"
            jcc="ALL"
           // if(filterCount=="0") {
                mBinding!!.txtfilter.visibility = View.GONE
                //  mBinding!!.txtfilter.text = filterCount!!

            category =mBinding!!.title3!!.getText().toString()
            fetchCategoryWiseData(mBinding!!.title3!!.getText().toString(),mFilterGetterAndSetter)
        }


        mBinding!!.filterId.setOnClickListener {


            val ft: FragmentTransaction = fragmentManager!!.beginTransaction()
            val prev =
                fragmentManager!!.findFragmentByTag("dialog")
            if (prev != null) {
                ft.remove(prev)
            }
            ft.addToBackStack(null)
            var filterFragmentAlerms: DialogFragment = FilterFragmentDilog(mFragmentCallback!!,agingPoss!!,regionn,circlee,mpp,jcc,posRegionn!!,posCirclee!!,posMpp!!,posJCc!!)
            var commonBean =  CommonBean()
        //    filterFragmentAlerms.show("","")
            filterFragmentAlerms.show(ft, "dialog");
         //   filterFragmentAlerms.setData(commonBean,mFragmentCallback!!)
           // val dialogFragment: DialogFragment = MyCustomDialogFragment()
          //  dialogFragment.show(ft, "dialog")


            filterFragmentAlerms.showsDialog
           // (activity as MainActivity).openFragments(filterFragmentAlerms, commonBean, true, true )
        }

        fetchDashboardDbData()


    }
    fun setData(commonBean: CommonBean,name:String,count:String) {
        this.commonBean = commonBean

        this.name = name
        this.count = count

    }


    fun fetchDashboardDbData() {
        (activity as MainActivity).showProgressBar()
        val requestBody = HashMap<String, Any>()

        requestBody["appRoleCode"] = "723"
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "userInfo"
        CoroutineScope(Dispatchers.IO).launch {

            var job = async {
                BaseCoroutines().fetchData(
                    requestBody,
                    Busicode.UtilityAlarm,
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


                    if (msg.containsKey("alarmCount")) {
                        mBinding!!.txtAlarmCount.text =
                            "(" + (msg["alarmCount"] as Int).toString() + ")"
                    }
                    dataListInfraItem = msg["alarmsList"] as ArrayList<Map<String, Any>>
                    datalist = msg["list"] as ArrayList<Map<String, Any>>


                    if (Utils.hasIndex(0, dataListInfraItem!!)) {
                        if (dataListInfraItem!!.get(0).containsKey("count")) {
                            mBinding!!.count1.text = dataListInfraItem!!.get(0)["count"].toString()
                        }
                        if (dataListInfraItem!!.get(0).containsKey("name")) {
                            mBinding!!.title1.text = dataListInfraItem!!.get(0)["name"].toString()
                        }
                    }

                    if (Utils.hasIndex(1, dataListInfraItem!!)) {
                        if (dataListInfraItem!!.get(1).containsKey("count")) {
                            mBinding!!.count2.text = dataListInfraItem!!.get(1)["count"].toString()
                        }
                        if (dataListInfraItem!!.get(1).containsKey("name")) {
                            mBinding!!.title2.text = dataListInfraItem!!.get(1)["name"].toString()
                        }
                    }
                    if (Utils.hasIndex(2, dataListInfraItem!!)) {
                        if (dataListInfraItem!!.get(2).containsKey("count")) {
                            mBinding!!.count3.text = dataListInfraItem!!.get(2)["count"].toString()
                        }
                        if (dataListInfraItem!!.get(2).containsKey("name")) {
                            mBinding!!.title3.text = dataListInfraItem!!.get(2)["name"].toString()
                        }
                    }
                    mBinding!!.constanthide.visibility = View.VISIBLE

                    var adapter = InfraOpenAlarmsAdapter(datalist!!,activity as MainActivity?,name!!,category!!,mFilterGetterAndSetter!!
                    )
                    mBinding!!.recyclelistView.layoutManager = LinearLayoutManager(activity)
                    mBinding!!.recyclelistView.itemAnimator = DefaultItemAnimator()
                    mBinding!!.recyclelistView!!.adapter = adapter
                    adapter!!.notifyDataSetChanged()
                    mBinding!!.txtSeeMore.visibility = View.GONE


                } catch (e: Exception) {
                    MyExceptionHandler.handle(e)
                }
            }

        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
            T.show(activity, activity!!.getString(R.string.something_went_wrong), 0)
        }
    }


    fun setData(commonBean: CommonBean) {
        this.commonBean = commonBean

    }

    fun hasIndex(index: Int, dataList: List<Map<String, Any>>): Boolean {
        if (index < dataList!!.size)
            return true
        else
            return false

    }


    fun fetchCategoryWiseData(selectedCategory: String,mFilterGetterAndSetter:FilterGetterAndSetter) {
        (activity as MainActivity).showProgressBar()
        val requestBody = HashMap<String, Any>()
        requestBody["category"] = selectedCategory
        requestBody["appRoleCode"] = "723"
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "userInfo"
        CoroutineScope(Dispatchers.IO).launch {

            var job = async {
                BaseCoroutines().fetchData(
                    requestBody,
                    Busicode.UtilityAlarmCategory,
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
                    T.show(activity, response!!.errorMsg!!, 0)
                    mBinding!!.recyclelistView.visibility=View.GONE
                    datalist!!.clear()

                } else {
                    T.show(activity, "Something went wrong!", 0)
                }
            }

        }
    }

    fun changeSelection(selection: Int) {
        if (selection == 0) {
            mBinding!!.count1.setTypeface(boldTypeface)
            mBinding!!.count2.setTypeface(lightTypeface)
            mBinding!!.count3.setTypeface(lightTypeface)

        } else if (selection == 1) {
            mBinding!!.count2.setTypeface(boldTypeface)
            mBinding!!.count1.setTypeface(lightTypeface)
            mBinding!!.count3.setTypeface(lightTypeface)

        } else if (selection == 2) {
            mBinding!!.count3.setTypeface(boldTypeface)
            mBinding!!.count2.setTypeface(lightTypeface)
            mBinding!!.count1.setTypeface(lightTypeface)
        }

    }

    fun filterCategoryWiseData(mCoroutinesResponse: CoroutinesResponse,mFilterGetterAndSetter:FilterGetterAndSetter) {

        try {
            val msg = mCoroutinesResponse.responseEntity as HashMap<String, Any>


            if (msg != null) {
                try {


                    datalist = msg["list"] as ArrayList<Map<String, Any>>


                    mBinding!!.constanthide.visibility = View.VISIBLE
                    mBinding!!.recyclelistView.visibility=View.VISIBLE

                    var adapter = InfraOpenAlarmsAdapter( datalist!!,
                        activity as MainActivity?,

                        name!!,category!!,
                        mFilterGetterAndSetter!!)
                    mBinding!!.recyclelistView.layoutManager = LinearLayoutManager(activity)
                    mBinding!!.recyclelistView.itemAnimator = DefaultItemAnimator()
                    mBinding!!.recyclelistView!!.adapter = adapter
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

    fun seeMoreData(){

        var adapter = InfraOpenAlarmsAdapter(
            datalist!!,
            activity as MainActivity?,
            name!!,category!!,
            mFilterGetterAndSetter!!
        )
        mBinding!!.recyclelistView.layoutManager = LinearLayoutManager(activity)
        mBinding!!.recyclelistView.itemAnimator = DefaultItemAnimator()
        mBinding!!.recyclelistView!!.adapter = adapter
        adapter!!.notifyDataSetChanged()
        mBinding!!.txtSeeMore.visibility = View.GONE
    }

    override fun onDataSent(startAge: String?,endAge: String?,region: String?,circle: String?,mp: String?,jc: String?,agingPos:String,filterCount:String,posRegion:String,posCircle:String,posMp:String,posJC:String) {
    //    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        agingPoss=agingPos
        regionn=region!!
        circlee=circle
        mpp=mp
        jcc=jc
        posRegionn=posRegion
        posCirclee=posCircle
        posMpp=posMp
        posJCc=posJC

        if(filterCount=="0") {
            mBinding!!.txtfilter.visibility = View.GONE
          //  mBinding!!.txtfilter.text = filterCount!!
        }else{
            mBinding!!.txtfilter.visibility = View.VISIBLE
            mBinding!!.txtfilter.text = filterCount!!
        }

        fetchFilterDbData(startAge,endAge,region,circle,mp,jc)
    }

    fun fetchFilterDbData(startAge: String?,endAge: String?,region: String?,circle: String?,mp: String?,jc: String?) {
        (activity as MainActivity).showProgressBar()
        val requestBody = HashMap<String, Any>()
        val requestBodyfilters = HashMap<String, Any>()



        //  requestBody["outlierType"] = dataMap!!["featureName"] as String
        requestBody["appRoleCode"] = "723"
        requestBody["category"] =category!!

        mFilterGetterAndSetter.setStartAging(startAge!!)
        mFilterGetterAndSetter.setEndAging(endAge!!)
        mFilterGetterAndSetter.setRegian(region!!)
        mFilterGetterAndSetter.setCircle(circle!!)
        mFilterGetterAndSetter.setMp(mp!!)
        mFilterGetterAndSetter.setJc(jc!!)
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "userInfo"
         requestBodyfilters["startAge"]=startAge!!
         requestBodyfilters["endAge"]=endAge!!
         requestBodyfilters["region"]=region!!
         requestBodyfilters["circle"]=circle!!
         requestBodyfilters["mp"]=mp!!
         requestBodyfilters["jc"]=jc!!
        requestBody["filters"] =requestBodyfilters
        CoroutineScope(Dispatchers.IO).launch {

            var job = async {
                BaseCoroutines().fetchData(
                    requestBody,
                    Busicode.UtilityAlarmCategory,
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
                    T.show(activity, response!!.errorMsg!!, 0)
                    mBinding!!.recyclelistView.visibility=View.GONE
                } else {
                    T.show(activity, "Something went wrong!", 0)
                }
            }

        }
    }
}