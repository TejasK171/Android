package com.jio.siops_ngo.infra.fragment


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
import com.jio.jioinfra.utilities.*
import com.jio.myjio.bean.CoroutinesResponse
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.databinding.DcbInnerLayoutBinding
import com.jio.siops_ngo.databinding.DcbInuseLayoutBinding
import com.jio.siops_ngo.infra.adapter.DcbInUseClickAdapter
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.T
import com.jio.siops_ngo.utilities.Utils
import kotlinx.coroutines.*
import kotlin.math.roundToInt


class DcbInUseClickFragment : Fragment() {
    internal var commonBean: CommonBean? = null
    var mBinding: DcbInuseLayoutBinding? = null
    var map: Map<String, Any>? = null
    var allDataList: ArrayList<HashMap<String, Any>>? = null
    var dataSubList: ArrayList<HashMap<String, Any>>? = null
    var p1DataList: ArrayList<Map<String, Any>>? = null
    var rp1DataList: ArrayList<Map<String, Any>>? = null
    var ipColoDataList: ArrayList<Map<String, Any>>? = null
    var progressBarData: ArrayList<Map<String, Any>>? = null
    var statusType: String? = null
    var appRoleCode: String? = null
    var outlierId: String? = null
    var headertitle: String? = null
    var totalCount: Int? = 0
    var count1: Int? = 0
    var count2: Int? = 0
    var count3: Int? = 0
    var pageSize: Int? = 0
    var count: Int? = 1
    var adapter: DcbInUseClickAdapter? = null
    var ar: ArrayList<Int> = ArrayList()
    var totalCountValue: Int? = 0

    companion object {
        fun newInstance() = DcbInUseClickFragment()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.dcb_inuse_layout, container, false)
        return mBinding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (commonBean!!.getmSubTitle() != null) {

            mBinding!!.rlId.visibility=View.GONE
         //   mBinding!!.dcbSubTitle.text = " Granite "
            mBinding!!.dcbHeaderTitle.text = " "+headertitle
            mBinding!!.txtTitle.text = "Total "+headertitle
            mBinding!!.txtTotalCoun1.text = headertitle+" sites listing "

        }

        mBinding!!.dcbRecyclerView.setNestedScrollingEnabled(false);

        mBinding!!.scroll.getViewTreeObserver().addOnScrollChangedListener({
            val view =
                mBinding!!.scroll.getChildAt( mBinding!!.scroll.getChildCount() - 1) as View
            Log.d("CategoryNeswList",  mBinding!!.scroll.getChildCount().toString() + " child")
            val diff: Int = view.bottom - (mBinding!!.scroll.getHeight() + mBinding!!.scroll.getScrollY())
            if (diff == 0) {
                //Toast.makeText(activity, "Load More", Toast.LENGTH_SHORT).show()
                count = count!! + 1
                if (count!! > pageSize!!) {
                    //count = 1
                    mBinding!!.progressBar.visibility=View.GONE
                } else {
                    mBinding!!.progressBar.visibility=View.VISIBLE
                    fetchDashboardData(count)
                }
            }
        })


        fetchDashboardData(count)
    }
    fun fetchDashboardData(count: Int?) {
        if(count==1) {
            (activity as MainActivity).showProgressBar()
        }
        (activity as MainActivity).showProgressBar()
        val requestBody = HashMap<String, Any>()
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["appRoleCode"] = appRoleCode!!
        requestBody["type"] = "userInfo"
        requestBody["statusType"] = statusType!!
        requestBody["outlierId"] = outlierId!!
        requestBody["pageCount"] = count!!
        CoroutineScope(Dispatchers.IO).launch {

            var job = async { BaseCoroutines().fetchData(requestBody, Busicode.DataCompleteOnClick, activity as MainActivity) }
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

            mBinding!!.rlId.visibility=View.VISIBLE
            if (msg.containsKey("totalCount")) {

                totalCount = msg.get("totalCount") as Int
                mBinding!!.txtTotalCount.text = totalCount!!.toString()

            }


            if (msg.containsKey("pageSize")) {
                pageSize = msg.get("pageSize") as Int
            }


            if (msg.containsKey("countBar")) {

                progressBarData = msg["countBar"] as ArrayList<Map<String, Any>>

                ar.add(progressBarData!![0].get("cnt") as Int)
                ar.add(progressBarData!![1].get("cnt") as Int)
                ar.add(progressBarData!![2].get("cnt") as Int)

                totalCountValue= Utils.maxOfNumList(ar)

                if (hasIndex(0)) {
                    if (progressBarData!![0].containsKey("sites")) {
                        mBinding!!.pb1Title.text = progressBarData!![0].get("sites").toString()
                    }
                    if (progressBarData!![0].containsKey("cnt")) {
                        count1 = progressBarData!![0].get("cnt") as Int
                        mBinding!!.pb1Count.text = count1.toString()



                        if (totalCountValue!! > 0) {
                            var p1Percentage = (count1!!.toFloat() / totalCountValue!!.toFloat()) * 100
                            mBinding!!.pb1.progress = p1Percentage!!.roundToInt()
                        }
                    }
                }

                if (hasIndex(1)) {
                    if (progressBarData!![1].containsKey("sites")) {
                        mBinding!!.pb2Title.text = progressBarData!![1].get("sites").toString()
                    }

                    if (progressBarData!![1].containsKey("cnt")) {
                        mBinding!!.pb2Count.text = progressBarData!![1].get("cnt").toString()
                        count2 = progressBarData!![1].get("cnt") as Int


                        if (totalCountValue!! > 0) {
                            var p1Percentage = (count2!!.toFloat() / totalCountValue!!.toFloat()) * 100
                            mBinding!!.pb2.progress = p1Percentage!!.roundToInt()
                        }
                    }
                }
                if (hasIndex(2)) {
                    if (progressBarData!![2].containsKey("sites")) {
                        mBinding!!.pb3Title.text = progressBarData!![2].get("sites").toString()
                    }

                    if (progressBarData!![2].containsKey("cnt")) {
                        mBinding!!.pb3Count.text = progressBarData!![2].get("cnt").toString()
                        count3 = progressBarData!![2].get("cnt") as Int


                        if (totalCountValue!! > 0) {
                            var p1Percentage = (count3!!.toFloat() / totalCountValue!!.toFloat()) * 100
                            mBinding!!.pb3.progress = p1Percentage!!.roundToInt()
                        }
                    }
                }
            }
            if (msg != null) {
                try {
                    if (msg["subList"] != null) {
                        if(allDataList!=null){
                            dataSubList = msg["subList"] as ArrayList<HashMap<String, Any>>
                            allDataList!!.addAll(dataSubList!!)
                            adapter!!.notifyDataSetChanged()
                        }else{
                            allDataList = msg["subList"] as ArrayList<HashMap<String, Any>>
                            adapter = DcbInUseClickAdapter(allDataList!!, activity as MainActivity?)
                            mBinding!!.dcbRecyclerView.layoutManager = LinearLayoutManager(activity)
                            mBinding!!.dcbRecyclerView.itemAnimator = DefaultItemAnimator()
                            mBinding!!.dcbRecyclerView!!.adapter = adapter
                            adapter!!.notifyDataSetChanged()
                        }
                        mBinding!!.progressBar.visibility=View.INVISIBLE
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

    fun setData(commonBean: CommonBean, statusType: String, appRoleCode: String,outlierId:String,headertitle:String) {
        this.commonBean = commonBean
        this.statusType = statusType
        this.appRoleCode = appRoleCode
        this.outlierId = outlierId
        this.headertitle = headertitle
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

}