package com.jio.siops_ngo.infra.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.bean.CommonBean
import com.jio.jioinfra.network.business.BaseCoroutines
import com.jio.jioinfra.utilities.Busicode
import com.jio.jioinfra.utilities.MyConstants
import com.jio.myjio.bean.CoroutinesResponse
import com.jio.siops_ngo.MainActivity

import com.jio.siops_ngo.R
import com.jio.siops_ngo.adapter.InfraMaintenanceItemAdapter
import com.jio.siops_ngo.bean.FilterGetterAndSetter
import com.jio.siops_ngo.databinding.FragmentOpenAlertsHistoryBinding
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.T
import kotlinx.coroutines.*

/**
 * A simple [Fragment] subclass.
 */
class InfraSiteHistoryFragment : Fragment() {


    private var commonBean: CommonBean? = null
    private var mFilterGetterAndSetter: FilterGetterAndSetter? = null
    //  private var stateMpJcBean: StateMpJcBean? = null
    var mBinding: FragmentOpenAlertsHistoryBinding? = null
    var commonBeanMap: Map<String, Any>? = null
    var commonActionStr: String? = null
    var datalist: ArrayList<Map<String, Any>>? = null
    var outLierId: String?=null
    var name:String?=null
    var reason:String?=null
    var category: String?=null
    var pageSize: Int? = 0
    var count: Int? = 1
    var dataSubList: ArrayList<Map<String, Any>>? = null

    var adapter:InfraMaintenanceItemAdapter?=null

    companion object {
        fun newInstance() = InfraSiteHistoryFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_open_alerts_history,
            container,
            false
        )
        //  mBinding!!.welcome!!.visibility = View.GONE
        return mBinding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        commonBeanMap = commonBean!!.`object` as Map<String, Any>





        mBinding!!.historyListView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //// Toast.makeText(activity, "Load More", Toast.LENGTH_SHORT).show()

                    count = count!! + 1
                    if (count!! > pageSize!!) {
                        //count = 1
                        mBinding!!.progressBar.visibility=View.GONE
                    } else {
                        mBinding!!.progressBar.visibility=View.VISIBLE
                        fetchAlarmsHistoryList(count)
                    }
                }
            }
        })


//        mBinding!!.historyListView.setNestedScrollingEnabled(false);
//
//        mBinding!!.scroll.getViewTreeObserver().addOnScrollChangedListener({
//            val view =
//                mBinding!!.scroll.getChildAt( mBinding!!.scroll.getChildCount() - 1) as View
//            Log.d("CategoryNeswList",  mBinding!!.scroll.getChildCount().toString() + " child")
//            val diff: Int = view.bottom - (mBinding!!.scroll.getHeight() + mBinding!!.scroll.getScrollY())
//            if (diff == 0) {
//                //Toast.makeText(activity, "Load More", Toast.LENGTH_SHORT).show()
//                count = count!! + 1
//                if (count!! > pageSize!!) {
//                    //count = 1
//                    mBinding!!.progressBar.visibility=View.GONE
//                } else {
//                    mBinding!!.progressBar.visibility=View.VISIBLE
//                    fetchAlarmsHistoryList(count)
//                }
//            }
//        })


        fetchAlarmsHistoryList(count)

      //  fetchAlarmsHistoryList()
    }

    fun fetchAlarmsHistoryList(count: Int?) {
        if(count==1) {
            (activity as MainActivity).showProgressBar()
        }

        val requestBody = HashMap<String, Any>()
        val requestBodyfilters = HashMap<String, Any>()

        requestBody["outlierId"] = outLierId!!
        requestBody["category"] = category!!
        requestBody["reason"] = reason!!
      //  requestBody["appRoleCode"] = PreferenceUtility.getString(activity, MyConstants.APP_CODE_FOPS, "")
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "userInfo"
        requestBody["pageCount"] = count!!

        requestBodyfilters["startAge"]=mFilterGetterAndSetter!!.getStartAging()!!
        requestBodyfilters["endAge"]=mFilterGetterAndSetter!!.getEndAging()
        requestBodyfilters["startIC"]=mFilterGetterAndSetter!!.getStartimpactedCustomer()
        requestBodyfilters["endIC"]=mFilterGetterAndSetter!!.getEndimpactedCustomer()
        requestBodyfilters["region"]=mFilterGetterAndSetter!!.getRegian()
        requestBodyfilters["circle"]=mFilterGetterAndSetter!!.getCircle()
        requestBodyfilters["mp"]=mFilterGetterAndSetter!!.getMp()
        requestBodyfilters["jc"]=mFilterGetterAndSetter!!.getJc()
        requestBody["filters"] =requestBodyfilters



        CoroutineScope(Dispatchers.IO).launch {

            var job = async {
                BaseCoroutines().fetchData(
                    requestBody,
                    Busicode.SiteDownDetail,
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
                    PreferenceUtility.addString(activity, MyConstants.NOTIFICATION_COUNT, "0")
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

    fun setData(
        commonBean: CommonBean,
        outLierId: String,
        name: String,
        reason: String,
        category: String,
        mFilterGetterAndSetter: FilterGetterAndSetter
    ) {
        this.commonBean = commonBean
        this.outLierId = outLierId
        this.name = name
        this.reason = reason
        this.category = category
        this.mFilterGetterAndSetter = mFilterGetterAndSetter
    }
//
//    fun setCircleMpJcBean(stateMpJcBean: StateMpJcBean) {
//        this.stateMpJcBean = stateMpJcBean
//    }

    fun filterData(mCoroutinesResponse: CoroutinesResponse) {

        try {
            val msg = mCoroutinesResponse.responseEntity as HashMap<String, Any>
            if (msg != null) {

                try {
                    if (msg.containsKey("pageSize")) {
                        pageSize = msg.get("pageSize") as Int
                    }
                    if (msg["subList"] != null) {
                        if(datalist!=null){
                            dataSubList = msg["subList"] as ArrayList<Map<String, Any>>
                            datalist!!.addAll(dataSubList!!)
                            adapter!!.notifyDataSetChanged()
                        }else{
                            datalist = msg["subList"] as ArrayList<Map<String, Any>>
                            adapter = InfraMaintenanceItemAdapter(datalist!!, activity as MainActivity?,name, outLierId!!)
                            mBinding!!.historyListView!!.layoutManager = LinearLayoutManager(activity) as RecyclerView.LayoutManager?
                            mBinding!!.historyListView!!.itemAnimator = DefaultItemAnimator()
                            mBinding!!.historyListView!!.adapter = adapter
                            adapter!!.notifyDataSetChanged()
                        }
                        mBinding!!.progressBar.visibility=View.GONE
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


}