package com.jio.siops_ngo.infra.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.bean.CommonBean
import com.jio.jioinfra.network.business.BaseCoroutines
import com.jio.jioinfra.utilities.Busicode
import com.jio.jioinfra.utilities.MyConstants
import com.jio.myjio.bean.CoroutinesResponse
import com.jio.siops_ngo.MainActivity

import com.jio.siops_ngo.R
import com.jio.siops_ngo.databinding.FragmentDcbInserviceBinding
import com.jio.siops_ngo.infra.adapter.DcbInServiceAdapter
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.T
import com.jio.siops_ngo.utilities.Utils
import kotlinx.coroutines.*
import kotlin.math.roundToInt

/**
 * A simple [Fragment] subclass.
 */
class DcbInserviceFragment : Fragment() {

    var mBinding: FragmentDcbInserviceBinding? = null
    var map: Map<String, Any>? = null
    internal var commonBean: CommonBean? = null

    var list:List<HashMap<String, Any>>?=null
    var teMIP:List<HashMap<String, Any>>?=null
    var jcp:List<HashMap<String, Any>>?=null
    var everest:List<HashMap<String, Any>>?=null
    var mcom:List<HashMap<String, Any>>?=null
    var totalCount: Int? = 0
    var count1: Int? = 0
    var count2: Int? = 0
    var count3: Int? = 0
    var statusType:String?=null
    var appRoleCode:String?=null
    var ar: ArrayList<Int> = ArrayList()
    var totalCountValue: Int? = 0
    var subtitlewithslach: String? = null

    var progressBarData: ArrayList<Map<String, Any>>? = null
    companion object {
        fun newInstance() = DcbInserviceFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dcb_inservice, container, false)
        return mBinding!!.root

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        map = commonBean!!.`object` as Map<String, Any>
        mBinding!!.scrollId.visibility=View.GONE

        mBinding!!.txtGraniteHeader.text="Granite "+subtitlewithslach
        mBinding!!.txtSubHeader.text=" "+subtitlewithslach
        mBinding!!.txtTitle.text="Total "+subtitlewithslach
        fetchDashboardData()
    }

    fun fetchDashboardData() {
        (activity as MainActivity).showProgressBar()
        val requestBody = HashMap<String, Any>()
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["appRoleCode"] =appRoleCode!!
        requestBody["type"] = "userInfo"
        requestBody["statusType"] = statusType!!
        CoroutineScope(Dispatchers.IO).launch {

            var job = async {
                BaseCoroutines().fetchData(
                    requestBody,
                    Busicode.DataCompleteOnClick,
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
                } else if (response!!.errorCode != null && response!!.errorCode!!.equals(MappActor.VERSION_SESSION_INVALID)){
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
                    mBinding!!.scrollId.visibility=View.VISIBLE

                    var commonBeanMainServices: CommonBean = CommonBean()

                    if (msg.containsKey("totalCount")) {

                        totalCount = msg.get("totalCount") as Int
                        mBinding!!.txtTotalCount.text = totalCount!!.toString()
//                        mBinding!!.txtTowerCount.text = "("+totalCount!!.toString()+")"

                    }

                    if (msg.containsKey("countBar")) {

                        progressBarData = msg["countBar"] as ArrayList<Map<String, Any>>
                        ar.add(progressBarData!![0].get("cnt") as Int)
                        ar.add(progressBarData!![1].get("cnt") as Int)
                        ar.add(progressBarData!![2].get("cnt") as Int)

                        totalCountValue= Utils.maxOfNumList(ar)

                        if (hasIndex(0)) {
                            if (progressBarData!![0].containsKey("sites")) {
                                mBinding!!.pb1Title.text =
                                    progressBarData!![0].get("sites").toString()
                            }
                            if (progressBarData!![0].containsKey("cnt")) {
                                count1 = progressBarData!![0].get("cnt") as Int
                                mBinding!!.pb1Count.text = count1.toString()



                                if (totalCountValue!! > 0) {
                                    var p1Percentage =
                                        (count1!!.toFloat() / totalCountValue!!.toFloat()) * 100
                                    mBinding!!.pb1.progress = p1Percentage!!.roundToInt()
                                }
                            }
                        }

                        if (hasIndex(1)) {
                            if (progressBarData!![1].containsKey("sites")) {
                                mBinding!!.pb2Title.text =
                                    progressBarData!![1].get("sites").toString()
                            }

                            if (progressBarData!![1].containsKey("cnt")) {
                                mBinding!!.pb2Count.text =
                                    progressBarData!![1].get("cnt").toString()
                                count2 = progressBarData!![1].get("cnt") as Int


                                if (totalCountValue!! > 0) {
                                    var p1Percentage =
                                        (count2!!.toFloat() / totalCountValue!!.toFloat()) * 100
                                    mBinding!!.pb2.progress = p1Percentage!!.roundToInt()
                                }
                            }
                        }

                        if (hasIndex(2)) {
                            if (progressBarData!![2].containsKey("sites")) {
                                mBinding!!.pb3Title.text =
                                    progressBarData!![2].get("sites").toString()
                            }

                            if (progressBarData!![2].containsKey("cnt")) {
                                mBinding!!.pb3Count.text =
                                    progressBarData!![2].get("cnt").toString()
                                count3 = progressBarData!![2].get("cnt") as Int


                                if (totalCountValue!! > 0) {
                                    var p1Percentage =
                                        (count3!!.toFloat() / totalCountValue!!.toFloat()) * 100
                                    mBinding!!.pb3.progress = p1Percentage!!.roundToInt()
                                }
                            }
                        }
                    }


                    if (msg.containsKey("controlCount")) {
                        mBinding!!.txtControlsCount.text= "("+msg["controlCount"].toString()+")"

                    }

                    if (msg["controls"] != null) {
                     //   mBinding!!.inserviceRecyclerView.visibility=View.VISIBLE
                        list=  msg["controls"] as List<HashMap<String, Any>>

                        if(list!!.size!=0) {
                            mBinding!!.inserviceRecyclerView.visibility=View.VISIBLE
                            mBinding!!.cnstrntTowerHeader.visibility=View.VISIBLE
                            var adapter = DcbInServiceAdapter(
                                list!!,
                                activity as MainActivity?,
                                statusType,
                                appRoleCode
                            )
                            val gridLayoutManagerUsefulLinks = GridLayoutManager(activity, 3)
                            mBinding!!.inserviceRecyclerView.layoutManager =
                                gridLayoutManagerUsefulLinks
                            mBinding!!.inserviceRecyclerView.itemAnimator =
                                DefaultItemAnimator() as RecyclerView.ItemAnimator?
                            mBinding!!.inserviceRecyclerView!!.adapter = adapter
                            adapter!!.notifyDataSetChanged()
                        }else{
                            mBinding!!.inserviceRecyclerView.visibility=View.GONE
                            mBinding!!.cnstrntTowerHeader.visibility=View.GONE
                        }

                    }else{
                        mBinding!!.inserviceRecyclerView.visibility=View.GONE
                        mBinding!!.cnstrntTowerHeader.visibility=View.GONE
                    }

                    if (msg["teMIP"] != null) {

                        teMIP=  msg["teMIP"] as List<HashMap<String, Any>>


                        if(teMIP!!.size==0){
                            mBinding!!.cnstrntLTemp.visibility=View.GONE
                        }else {
                            mBinding!!.cnstrntLTemp.visibility = View.VISIBLE
                             if(Utils.hasIndexInService(0,teMIP)){
                                 mBinding!!.tempCount1.text = teMIP!!.get(0)["value"].toString()
                                 mBinding!!.tempTitle1.text = teMIP!!.get(0)["key"].toString()
                             }
                            if(Utils.hasIndexInService(1,teMIP)){
                                mBinding!!.tempCount2.text = teMIP!!.get(1)["value"].toString()
                                mBinding!!.tempTitle2.text = teMIP!!.get(1)["key"].toString()
                            }
                        }

                    }else{
                        mBinding!!.cnstrntLTemp.visibility=View.GONE
                    }

                    if (msg["jcp"] != null) {
                        jcp=  msg["jcp"] as List<HashMap<String, Any>>

                        if(jcp!!.size==0){
                            mBinding!!.cnstrntLJcp.visibility=View.GONE
                        }else {
                            mBinding!!.cnstrntLJcp.visibility=View.VISIBLE

                            if(Utils.hasIndexInService(0,jcp)){
                                mBinding!!.jcpCount1.text = jcp!!.get(0)["value"].toString()
                                mBinding!!.jcpTitle1.text = jcp!!.get(0)["key"].toString()
                            }


                            if(Utils.hasIndexInService(1,jcp)){
                                mBinding!!.jcpCount2.text = jcp!!.get(1)["value"].toString()
                                mBinding!!.jcpTitle2.text = jcp!!.get(1)["key"].toString()
                            }





                        }
                    }else{
                        mBinding!!.cnstrntLJcp.visibility=View.GONE
                    }
                    if (msg["everest"] != null) {
                        everest=  msg["everest"] as List<HashMap<String, Any>>

                        if(everest!!.size==0){
                            mBinding!!.cnstrntLEverest.visibility=View.GONE
                        }else {



                            mBinding!!.cnstrntLEverest.visibility=View.VISIBLE


                            if(Utils.hasIndexInService(0,everest)) {
                                mBinding!!.everestCount1.text = everest!!.get(0)["value"].toString()
                                mBinding!!.everestTitle1.text = everest!!.get(0)["key"].toString()
                            }


                            if(Utils.hasIndexInService(1,everest)){
                                mBinding!!.everestTitle2.text = everest!!.get(1)["key"].toString()
                                mBinding!!.everestCount2.text = everest!!.get(1)["value"].toString()
                            }


                        }
                    }else{
                        mBinding!!.cnstrntLEverest.visibility=View.GONE
                    }



                    if (msg["mcom"] != null) {
                        mcom=  msg["mcom"] as List<HashMap<String, Any>>

                        if(mcom!!.size==0){
                            mBinding!!.cnstrntLMcome.visibility=View.GONE
                        }else{
                            mBinding!!.cnstrntLMcome.visibility=View.VISIBLE



                            if(Utils.hasIndexInService(0,mcom)) {
                                mBinding!!.mcomeCount1.text = mcom!!.get(0)["value"].toString()
                                mBinding!!.mcomeTitle1.text = mcom!!.get(0)["key"].toString()
                            }


                            if(Utils.hasIndexInService(1,mcom)){
                                mBinding!!.mcomeTitle2.text = mcom!!.get(1)["key"].toString()
                                mBinding!!.mcomeCount2.text = mcom!!.get(1)["value"].toString()
                            }


                        }
                    }else{
                        mBinding!!.cnstrntLMcome.visibility=View.GONE
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
        statusType: String,
        appRoleCode: String,
        subtitlewithslach: String
    ) {
        this.commonBean = commonBean
        this.statusType = statusType
        this.appRoleCode = appRoleCode
        this.subtitlewithslach = subtitlewithslach
    }
    fun hasIndex(index: Int): Boolean {
        if (index < progressBarData!!.size)
            return true
        else
            return false

    }

}
