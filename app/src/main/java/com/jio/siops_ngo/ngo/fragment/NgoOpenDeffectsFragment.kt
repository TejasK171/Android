package com.jio.siops_ngo.ngo.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.jio.siops_ngo.databinding.FragmentNgoOpenDeffectsBinding
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.ngo.adapter.NgoDeffectAdapter
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.T
import kotlinx.coroutines.*


/**
 * A simple [Fragment] subclass.
 */
class NgoOpenDeffectsFragment : Fragment() {

    var allDataList: ArrayList<HashMap<String, Any>>? = null

    var commonBean: CommonBean?=null
    var msg: ArrayList<Map<String, Any>>?=null
    var feactureName:String?=null
    var count:String?=null
    var countDate:String?=null
    var deffectname:String?=null

    var mBinding:FragmentNgoOpenDeffectsBinding?=null

    companion object {
        fun newInstance() = NgoOpenDeffectsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_ngo_open_deffects,
            container,
            false
        )
        return mBinding!!.root
       // return inflater.inflate(R.layout.fragment_ngo_open_deffects, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mBinding!!.header.text= "Open Defects-"+PreferenceUtility.getString(activity,"title","")

        mBinding!!.txtOpenDefectsTitle.text=feactureName
        mBinding!!.txtOpendefectsCount.text="("+count+")"

      //  var count:String?=null
        if(msg!!.get(0).containsKey("featureName")&& msg!!.get(0)["featureName"]!=null){
            mBinding!!.hotFixTitle1.text= msg!!.get(0)["featureName"].toString()
        }

        if(msg!!.get(0).containsKey("outlierCount")&& msg!!.get(0)["outlierCount"]!=null){
            mBinding!!.hotFixCount1.text= msg!!.get(0)["outlierCount"].toString()
            countDate= msg!!.get(0)["outlierCount"].toString()

            mBinding!!.txtOpenChargeHeader.text=mBinding!!.hotFixTitle1.text.toString()
            mBinding!!.txtOpenChargeCount.text=countDate
            fetchData(mBinding!!.hotFixTitle1.text.toString())
        }

        if(msg!!.get(1).containsKey("featureName")&& msg!!.get(1)["featureName"]!=null){
            mBinding!!.bugFixTitle2.text= msg!!.get(1)["featureName"].toString()
        }

        if(msg!!.get(1).containsKey("outlierCount")&& msg!!.get(1)["outlierCount"]!=null){
            mBinding!!.bugFixCount2.text= msg!!.get(1)["outlierCount"].toString()
        }

        if(msg!!.get(2).containsKey("featureName")&& msg!!.get(2)["featureName"]!=null){
            mBinding!!.defectedTitel.text= msg!!.get(2)["featureName"].toString()
        }

        if(msg!!.get(2).containsKey("outlierCount")&& msg!!.get(2)["outlierCount"]!=null){
            mBinding!!.defectedCount1.text= msg!!.get(2)["outlierCount"].toString()
        }

        if(msg!!.get(3).containsKey("featureName")&& msg!!.get(3)["featureName"]!=null){
            mBinding!!.defected2Titel.text= msg!!.get(3)["featureName"].toString()
        }

        if(msg!!.get(3).containsKey("outlierCount")&& msg!!.get(3)["outlierCount"]!=null){
            mBinding!!.defectedCount2.text= msg!!.get(3)["outlierCount"].toString()
        }

        if(msg!!.get(4).containsKey("featureName")&& msg!!.get(4)["featureName"]!=null){
            mBinding!!.defected1Titel.text= msg!!.get(4)["featureName"].toString()
        }

        if(msg!!.get(4).containsKey("outlierCount")&& msg!!.get(4)["outlierCount"]!=null){
            mBinding!!.defectedCount3.text= msg!!.get(4)["outlierCount"].toString()
        }

        mBinding!!.cardOpenDeffected1.setOnClickListener {
            mBinding!!.txtOpenChargeHeader.text=mBinding!!.hotFixTitle1.text.toString()
            mBinding!!.txtOpenChargeCount.text=mBinding!!.hotFixCount1.text.toString()

            fetchData(mBinding!!.hotFixTitle1.text.toString())
        }

        mBinding!!.cardOpenDeffecte2.setOnClickListener {

            mBinding!!.txtOpenChargeHeader.text=mBinding!!.bugFixTitle2.text.toString()
            mBinding!!.txtOpenChargeCount.text=mBinding!!.bugFixCount2.text.toString()
            fetchData(mBinding!!.bugFixTitle2.text.toString())
        }

        mBinding!!.card1Defected.setOnClickListener {


            mBinding!!.txtOpenChargeHeader.text=mBinding!!.defectedTitel.text.toString()
            mBinding!!.txtOpenChargeCount.text=mBinding!!.defectedCount1.text.toString()
            fetchData(mBinding!!.defectedTitle.text.toString())
        }

        mBinding!!.card2Defected.setOnClickListener {

            mBinding!!.txtOpenChargeHeader.text=mBinding!!.defected2Titel.text.toString()
            mBinding!!.txtOpenChargeCount.text=mBinding!!.defectedCount2.text.toString()
            fetchData( mBinding!!.defected2Titel.text.toString())
        }

        mBinding!!.card3Defected.setOnClickListener {

            mBinding!!.txtOpenChargeHeader.text=mBinding!!.defected1Titel.text.toString()
            mBinding!!.txtOpenChargeCount.text=mBinding!!.defectedCount3.text.toString()
            fetchData(mBinding!!.defected1Titel.text.toString())
        }

    }


    fun fetchData(categoryName:String) {


        deffectname=feactureName
        (activity as MainActivity).showProgressBar()
        val requestBody = HashMap<String, Any>()
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["appRoleCode"] = MyConstants.APP_ROLE_CODE_NGO
        requestBody["type"] = "userInfo"
        requestBody["defectType"] = feactureName!!
        requestBody["category"] = categoryName!!
        CoroutineScope(Dispatchers.IO).launch {

            var job = async { BaseCoroutines().fetchData(requestBody, Busicode.NGOOpenDefectOnClick, activity as MainActivity) }
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
            mBinding!!.cnstrntLdeffectlist.visibility=View.VISIBLE

            if (msg["categoryList"] != null) {

                allDataList = msg["categoryList"] as ArrayList<HashMap<String, Any>>
                var adapter = NgoDeffectAdapter(allDataList!!, activity as MainActivity?,feactureName!!, mBinding!!.txtOpenChargeCount.text.toString()!!,mBinding!!.txtOpenChargeHeader.text.toString())
                mBinding!!.deffectlist.layoutManager = LinearLayoutManager(activity)
                mBinding!!.deffectlist.itemAnimator = DefaultItemAnimator()
                mBinding!!.deffectlist!!.adapter = adapter
                adapter!!.notifyDataSetChanged()
            }


        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
            T.show(activity, getString(R.string.something_went_wrong), 0)
        }
    }

    fun setData(
        commonBean: CommonBean,
        msg: ArrayList<Map<String, Any>>?,
        feactureName:String,
        count:String) {
        this.commonBean = commonBean
        this.msg = msg
        this.feactureName = feactureName
        this.count = count

    }
}
