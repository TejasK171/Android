package com.jio.siops_ngo.infra.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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
import com.jio.siops_ngo.databinding.FragmentDcbBinding
import com.jio.siops_ngo.infra.adapter.DcbDasbboardAdapter
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.T
import com.jio.siops_ngo.utilities.Utils.Companion.maxOfNumList
import kotlinx.coroutines.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.math.roundToInt


class DcbDashboardFragment : Fragment() {
    internal var commonBean: CommonBean? = null
    var mBinding: FragmentDcbBinding? = null
    var map: Map<String, Any>? = null
    var actionOutlier: List<HashMap<String, Any>>? = null
    var dataList: ArrayList<Map<String, Any>>? = null
    var groupedData: Map<String, Map<String, Any>>? = null
    var groupList: ArrayList<String>? = null
    var totalCount: Int? = 0
    var totalCountValue: Int? = 0
    var count1: Int? = 0
    var count2: Int? = 0
    var count3: Int? = 0
    var progressBarData: ArrayList<Map<String, Any>>? = null
  //  var numList: List<Int> = ArrayList()
    var ar: ArrayList<Int> = ArrayList()
    private var progressBar: ProgressBar? = null

    companion object {
        fun newInstance() = DcbDashboardFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dcb, container, false)
        return mBinding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        map = commonBean!!.`object` as Map<String, Any>
        mBinding!!.scrollId.visibility=View.GONE
        fetchDashboardData()
    }

    fun fetchDashboardData() {
        (activity as MainActivity).showProgressBar()

        val requestBody = HashMap<String, Any>()
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["appRoleCode"] = map!!["applicationCode"] as String
        requestBody["type"] = "userInfo"
        CoroutineScope(Dispatchers.IO).launch {

            var job = async {
                BaseCoroutines().fetchData(
                    requestBody,
                    Busicode.GraniteDataComplete,
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
            mBinding!!.scrollId.visibility=View.VISIBLE
            val msg = mCoroutinesResponse.responseEntity as HashMap<String, Any>
            if (msg != null) {
                try {
                    var commonBeanMainServices: CommonBean = CommonBean()
                    if (msg.containsKey("totalTowerSites")) {
                        totalCount = msg.get("totalTowerSites") as Int
                        mBinding!!.txtTotalCount.text = totalCount!!.toString()
                        mBinding!!.missingCount1.text=totalCount.toString()
                        mBinding!!.txtTowerCount.text="("+totalCount.toString()+")"
                    }
                    if (msg.containsKey("missing")) {
                        var missingCount=msg.get("missing") as Int
                        mBinding!!.missingCount2.text=missingCount.toString()
                    }
                    if (msg.containsKey("expected")) {
                        var expectedCount=msg.get("expected") as Int
                        mBinding!!.missingCount3.text=expectedCount.toString()
                    }
                    if (msg.containsKey("countBar")) {

                        progressBarData = msg["countBar"] as ArrayList<Map<String, Any>>

                        ar.add(progressBarData!![0].get("cnt") as Int)
                        ar.add(progressBarData!![1].get("cnt") as Int)
                        ar.add(progressBarData!![2].get("cnt") as Int)

                        totalCountValue= maxOfNumList(ar)
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

                        /*if (progressBarData!!.size == 3) {

                            if (progressBarData!![0].containsKey("sites")) {
                                mBinding!!.pb1Title.text = progressBarData!![0].get("sites").toString()
                            }
                            if (progressBarData!![0].containsKey("cnt")) {
                                count1 = progressBarData!![0].get("cnt") as Int
                                mBinding!!.pb1Count.text = count1.toString()



                                if (totalCount!! > 0) {
                                    var p1Percentage = (count1!!.toFloat() / totalCount!!.toFloat()) * 100
                                    mBinding!!.pb1.progress = p1Percentage!!.roundToInt()
                                }
                            }
                            if (progressBarData!![1].containsKey("sites")) {
                                mBinding!!.pb2Title.text = progressBarData!![1].get("sites").toString()
                            }

                            if (progressBarData!![1].containsKey("cnt")) {
                                mBinding!!.pb2Count.text = progressBarData!![1].get("cnt").toString()
                                count2 = progressBarData!![1].get("cnt") as Int


                                if (totalCount!! > 0) {
                                    var p1Percentage = (count2!!.toFloat() / totalCount!!.toFloat()) * 100
                                    mBinding!!.pb2.progress = p1Percentage!!.roundToInt()
                                }
                            }

                            if (progressBarData!![2].containsKey("sites")) {
                                mBinding!!.pb3Title.text = progressBarData!![2].get("sites").toString()
                            }

                            if (progressBarData!![2].containsKey("cnt")) {
                                mBinding!!.pb3Count.text = progressBarData!![2].get("cnt").toString()
                                count3 = progressBarData!![2].get("cnt") as Int


                                if (totalCount!! > 0) {
                                    var p1Percentage = (count3!!.toFloat() / totalCount!!.toFloat()) * 100
                                    mBinding!!.pb3.progress = p1Percentage!!.roundToInt()
                                }
                            }



                        }*/
                    }

                    if (msg["list"] != null) {

                        commonBeanMainServices.`object` = msg["list"] as List<HashMap<String, Any>>
                        var adapter = DcbDasbboardAdapter(commonBeanMainServices.`object` as List<Map<String, Any>>, activity as MainActivity?, commonBean!!, "", map!!["applicationCode"] as String)
                        val gridLayoutManagerUsefulLinks = GridLayoutManager(activity, 3)
                        mBinding!!.dcbRecyclerView.layoutManager = gridLayoutManagerUsefulLinks
                        mBinding!!.dcbRecyclerView.itemAnimator = DefaultItemAnimator() as RecyclerView.ItemAnimator?
                        mBinding!!.dcbRecyclerView!!.adapter = adapter
                        adapter!!.notifyDataSetChanged()


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

    fun setData(commonBean: CommonBean) {
        this.commonBean = commonBean
    }


    fun hasIndex(index: Int): Boolean {
        if (index < progressBarData!!.size)
            return true
        else
            return false

    }


}