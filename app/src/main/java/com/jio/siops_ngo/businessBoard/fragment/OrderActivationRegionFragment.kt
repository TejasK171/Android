package com.jio.siops_ngo.businessBoard.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.jio.jioinfra.bean.CommonBean
import com.jio.jioinfra.custom.TextViewMedium
import com.jio.jioinfra.network.business.BaseCoroutines
import com.jio.jioinfra.utilities.Busicode
import com.jio.jioinfra.utilities.MyConstants
import com.jio.myjio.bean.CoroutinesResponse
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.businessBoard.adapter.OrderActivationRegionAdapter
import com.jio.siops_ngo.databinding.FragmentOrderActivationCircleBinding
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.T
import kotlinx.coroutines.*
import java.lang.Exception
import java.sql.DriverManager.println
import java.text.SimpleDateFormat
import java.util.*




/**
 * A simple [Fragment] Ranjan subclass.
 */
class OrderActivationRegionFragment : Fragment() {


    var mBinding: FragmentOrderActivationCircleBinding? = null
    var apiRequestFormattedDate: String? = null
    var commonBean: CommonBean? = null
    var dataList: ArrayList<Map<String, Any>>? = null
    var adapter: OrderActivationRegionAdapter? = null
    var map: Map<String, Any>? = null
    var zoneAllChannels:String = ""
    var enteredAllChannels:String = ""
    var activatedAllChannels:String = ""
    var networkLatchedAllChannels:String = ""
    var rejectedAllChannels:String = ""
    var inProcessAllChannels:String = ""
    var tvPendingAllChannels:String = ""
    var dateStr:String = ""
    var categoryToDisplay:String = ""

    companion object {
        fun newInstance() =
            OrderActivationRegionFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_order_activation_circle,
            container,
            false
        )
        // Inflate the layout for this fragment
        val c = Calendar.getInstance().time
        println("Current time => $c")

        val df = SimpleDateFormat("dd/MM/yyyy")
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val dateFormatted = dateFormat.parse(dateStr)
        mBinding!!.collapsibleCalendarView.visibility = View.GONE
        mBinding!!.txtDate.text = df.format(dateFormatted)


        map = commonBean!!.`object` as Map<String, Any>
        return mBinding!!.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        mBinding!!.txtGt.visibility = View.VISIBLE
        mBinding!!.txtRr.visibility = View.VISIBLE



        mBinding!!.txtRegion.setOnClickListener {

            changeViewBg(mBinding!!.txtRegion, mBinding!!.txtGt, mBinding!!.txtRr, "ALL")
        }

        mBinding!!.txtGt.setOnClickListener {

            changeViewBg(mBinding!!.txtGt, mBinding!!.txtRegion, mBinding!!.txtRr, "GT")
        }

        mBinding!!.txtRr.setOnClickListener {

            changeViewBg(mBinding!!.txtRr, mBinding!!.txtRegion, mBinding!!.txtGt, "RR")
        }


        mBinding!!.txtRegion.performClick()
        /*mBinding!!.txtDate.setOnClickListener {


            val c = Calendar.getInstance()
            var _pickedDate: String? = null
            val dialog = DatePickerDialog(
                context!!,
                OnDateSetListener { view, year, month, dayOfMonth ->
                    val _year = year.toString()
                    val _month =
                        if (month + 1 < 10) "0" + (month + 1) else (month + 1).toString()
                    val _date =
                        if (dayOfMonth < 10) "0$dayOfMonth" else dayOfMonth.toString()
                    _pickedDate = "$_date/$_month/$year"
                    mBinding!!.txtDate.text = _pickedDate
                    Log.e("PickedDate: ", "Date: $_pickedDate") //2019-02-12
                }, c[Calendar.YEAR], c[Calendar.MONTH], c[Calendar.MONTH]
            )
            dialog.datePicker.minDate = System.currentTimeMillis() - 1000

            dialog.show()


        }*/
    }

    fun fetchDashboardData(category:String) {
        (activity as MainActivity).showProgressBar()
        val requestBody = HashMap<String, Any>()
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "userInfo"
//        requestBody["date"] = apiRequestFormattedDate!!
        requestBody["date"] = dateStr!!
        requestBody["category"] = category
        CoroutineScope(Dispatchers.IO).launch {

            var job = async {
                BaseCoroutines().fetchData(
                    requestBody,
                    Busicode.O2AJourneyCategory,
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
                    filterData(response,category)
                } else if (response!!.errorCode != null && response!!.errorCode.equals(MappActor.VERSION_SESSION_INVALID)) {
                    (activity as MainActivity).showDialogForSessionExpired(
                        (activity as MainActivity).resources.getString(
                            R.string.session_expired
                        ), (activity as MainActivity)
                    )
                } else if (response!!.errorMsg != null) {
                    T.show(activity, response!!.errorMsg!!, 0)
                } else {
                    T.show(activity, "Something went wrong!", 0)
                }
            }

        }
    }


    fun filterData(
        mCoroutinesResponse: CoroutinesResponse,
        category: String
    ) {
        try {
            val activationRespMap = mCoroutinesResponse.responseEntity as HashMap<String, Any>
             if (activationRespMap.containsKey("list") && activationRespMap != null) {
                 dataList = activationRespMap["list"] as ArrayList<Map<String, Any>>

                 if(activationRespMap!!.containsKey("category") && activationRespMap!!.get("category")!=null){
                     categoryToDisplay = activationRespMap!!["category"] as String
                 }


                 if(map!!.containsKey("zone") && map!!.get("zone")!=null){
                     zoneAllChannels = map!!["zone"] as String
                 }

                 if(map!!.containsKey("entered") && map!!.get("entered")!=null){
                     enteredAllChannels = map!!["entered"].toString()
                 }
                 if(map!!.containsKey("activated") && map!!.get("activated")!=null){
                     activatedAllChannels = map!!["activated"].toString()
                 }
                 if(map!!.containsKey("network_latched") && map!!.get("network_latched")!=null){
                     networkLatchedAllChannels = map!!["network_latched"].toString()
                 }
                 if(map!!.containsKey("rejected") && map!!.get("rejected")!=null){
                     rejectedAllChannels = map!!["rejected"].toString()
                 }
                 if(map!!.containsKey("in_process") && map!!.get("in_process")!=null){
                     inProcessAllChannels = map!!["in_process"].toString()
                 }
                 if(map!!.containsKey("tv_pending") && map!!.get("tv_pending")!=null){
                     tvPendingAllChannels = map!!["tv_pending"].toString()
                 }


                 /*var map1: Map<String, Any> = mutableMapOf(
                     "zone" to zoneAllChannels,
                     "entered" to enteredAllChannels,
                     "activated" to activatedAllChannels,
                     "network_latched" to networkLatchedAllChannels,
                     "rejected" to rejectedAllChannels,
                     "in_process" to inProcessAllChannels,
                     "tv_pending" to tvPendingAllChannels
                 )
                 dataList!!.add(0, map1!!)*/


                 adapter = OrderActivationRegionAdapter(dataList!!,activity as MainActivity?, commonBean!!,category!!,dateStr!!, categoryToDisplay!!)
                 mBinding!!.journeyList.layoutManager = LinearLayoutManager(activity)
                 mBinding!!.journeyList.itemAnimator = DefaultItemAnimator()
                 mBinding!!.journeyList!!.adapter = adapter
                 adapter!!.notifyDataSetChanged()
             }




        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun setData(commonBean: CommonBean, dateStr:String) {
        this.commonBean = commonBean
        this.dateStr = dateStr
    }


    fun changeViewBg(v1: TextViewMedium, v2: TextViewMedium, v3: TextViewMedium, category: String) {

        v1.setBackgroundDrawable(activity!!.resources.getDrawable(R.drawable.region_rounded))
        v1.setTextColor(activity!!.resources.getColor(R.color.white))

        v2.setBackgroundDrawable(activity!!.resources.getDrawable(R.drawable.rounded_corner_blue_border_bg))
        v2.setTextColor(activity!!.resources.getColor(R.color.jioinfra_gray))


        v3.setBackgroundDrawable(activity!!.resources.getDrawable(R.drawable.rounded_corner_blue_border_bg))
        v3.setTextColor(activity!!.resources.getColor(R.color.jioinfra_gray))


        dataList?.clear()
        adapter?.notifyDataSetChanged()
        fetchDashboardData(category)

    }


}
