package com.jio.siops_ngo.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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
import com.jio.siops_ngo.adapter.InfraOpenAlarmsCardItemAdapter
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
class InfraOpenAlertsHistoryFragment : Fragment() {


    private var commonBean: CommonBean? = null
    //  private var stateMpJcBean: StateMpJcBean? = null
    var mBinding: FragmentOpenAlertsHistoryBinding? = null
    var commonBeanMap: Map<String, Any>? = null
    var commonActionStr: String? = null
    var datalist: ArrayList<Map<String, Any>>? = null
    var outLierId : String? = null
    var dataSubList: ArrayList<Map<String, Any>>? = null
    var name:String?=null
    var reason:String?=null
    var category: String?=null
    var mFilterGetterAndSetter=FilterGetterAndSetter()
    var adapter:InfraOpenAlarmsCardItemAdapter?=null
    var pageSize: Int? = 0
    var count: Int? = 1
    private var loading = true
    var pastVisiblesItems = 0
    var visibleItemCount:Int = 0
    var totalItemCount:Int = 0

    var mLoading = false

    private var previousTotal = 0

    private val visibleThreshold = 5
    var firstVisibleItem = 0







    companion object {
        fun newInstance() = InfraOpenAlertsHistoryFragment()
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
        outLierId = commonBeanMap!!["id"] as String

//
//       mBinding!!.historyListView.setNestedScrollingEnabled(false);
//
//
//        mBinding!!.historyListView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                val mLayoutManager: LinearLayoutManager
//                mLayoutManager = LinearLayoutManager(activity)
//                visibleItemCount =  mBinding!!.historyListView.getChildCount()
//                totalItemCount = mLayoutManager.getItemCount()
//                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition()
//                if (loading) {
//                    if (totalItemCount > previousTotal) {
//                        loading = false
//                        previousTotal = totalItemCount
//                    }
//                }
//                if (!loading && totalItemCount - visibleItemCount
//                    <= firstVisibleItem + visibleThreshold
//
//                ) { // End has been reached
//                    Log.i("Yaeye!", "end called")
//                    Toast.makeText(activity, "Load More", Toast.LENGTH_SHORT).show()
//                    // Do something
//                    loading = true
//                }
//            }
//        })


//        mBinding!!.historyListView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                if (dy > 0) //check for scroll down
//                {
//
//                    val mLayoutManager: LinearLayoutManager
//                    mLayoutManager = LinearLayoutManager(activity)
//                    mBinding!!.historyListView.setLayoutManager(mLayoutManager)
//                    visibleItemCount = mLayoutManager.getChildCount()
//                    totalItemCount = mLayoutManager.getItemCount()
//                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition()
//                    if (loading) {
//                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
//                            loading = false
//                            Log.v("...", "Last Item Wow !")
//                            //Do pagination.. i.e. fetch new data
//                            Toast.makeText(activity, "Load More", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                }
//            }
//        })


//        mBinding!!.historyListView.setOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                val layoutManager =
//                    mBinding!!.historyListView.layoutManager as LinearLayoutManager?
//                val totalItem: Int = layoutManager!!.getItemCount()
//                val lastVisibleItem: Int = layoutManager!!.findLastVisibleItemPosition()
//                if (!mLoading && lastVisibleItem == totalItem - 1) {
//                    mLoading = true
//                    // Scrolled to bottom. Do something here.
//                    Toast.makeText(activity, "Load More", Toast.LENGTH_SHORT).show()
//
//                    count = count!! + 1
//                if (count!! > pageSize!!) {
//                    //count = 1
//                    mBinding!!.progressBar.visibility=View.GONE
//                    mLoading = false
//                } else {
//                    mBinding!!.progressBar.visibility=View.VISIBLE
//                    mLoading = false
//                    fetchAlarmsHistoryList(count)
//
//                }
//
//                }
//            }
//        })



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
        requestBody["appRoleCode"] = "723"
        requestBody["category"] = category!!
        requestBody["reason"] = reason!!
        requestBody["pageCount"] = count!!
        //  requestBody["appRoleCode"] = PreferenceUtility.getString(activity, MyConstants.APP_CODE_FOPS, "")
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "userInfo"

        requestBodyfilters["startAge"]=mFilterGetterAndSetter!!.getStartAging()!!
        requestBodyfilters["endAge"]=mFilterGetterAndSetter!!.getEndAging()
        requestBodyfilters["region"]=mFilterGetterAndSetter!!.getRegian()
        requestBodyfilters["circle"]=mFilterGetterAndSetter!!.getCircle()
        requestBodyfilters["mp"]=mFilterGetterAndSetter!!.getMp()
        requestBodyfilters["jc"]=mFilterGetterAndSetter!!.getJc()
        requestBody["filters"] =requestBodyfilters

        CoroutineScope(Dispatchers.IO).launch {

            var job = async {
                BaseCoroutines().fetchData(
                    requestBody,
                    Busicode.UtilityAlarmDetail,
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
        name: String,
        reason: String,
        category: String,
        mFilterGetterAndSetter: FilterGetterAndSetter
    ) {
        this.commonBean = commonBean
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


                if (msg.containsKey("pageSize")) {
                    pageSize = msg.get("pageSize") as Int
                }
                try {
                    if (msg["subList"] != null) {
                        if (datalist != null) {
                            dataSubList = msg["subList"] as ArrayList<Map<String, Any>>
                            datalist!!.addAll(dataSubList!!)
                            adapter!!.notifyDataSetChanged()
                        } else {
                            datalist = msg["subList"] as ArrayList<Map<String, Any>>
                            adapter = InfraOpenAlarmsCardItemAdapter(datalist!!, activity as MainActivity?, outLierId!!)
                            mBinding!!.historyListView!!.layoutManager =
                                LinearLayoutManager(activity) as RecyclerView.LayoutManager?
                            mBinding!!.historyListView!!.itemAnimator = DefaultItemAnimator()
                            mBinding!!.historyListView!!.adapter = adapter
                            adapter!!.notifyDataSetChanged()
                        }
                        mBinding!!.progressBar.visibility = View.GONE
                       // mLoading = true
                    }


//                    if (msg["list"] != null) {
//                        datalist = msg["list"] as ArrayList<Map<String, Any>>
//
//                        var adapter = InfraOpenAlarmsCardItemAdapter(datalist!!, activity as MainActivity?, outLierId!!)
//                        mBinding!!.historyListView!!.layoutManager = LinearLayoutManager(activity)
//                        mBinding!!.historyListView!!.itemAnimator = DefaultItemAnimator()
//                        mBinding!!.historyListView!!.adapter = adapter
//                        adapter!!.notifyDataSetChanged()
//                    }


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