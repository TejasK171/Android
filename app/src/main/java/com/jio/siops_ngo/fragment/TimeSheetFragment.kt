package com.jio.siops_ngo.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.jio.jioinfra.bean.CommonBean
import com.jio.jioinfra.network.business.BaseCoroutines
import com.jio.jioinfra.utilities.Busicode
import com.jio.jioinfra.utilities.MyConstants
import com.jio.myjio.bean.CoroutinesResponse
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.adapter.DelinquentAdapter
import com.jio.siops_ngo.databinding.FragmentNgoTimeclockingBinding
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.T
import kotlinx.android.synthetic.main.fragment_delinquent.*
import kotlinx.coroutines.*

/**
 * A simple [Fragment] subclass.
 */
class TimeSheetFragment : Fragment() {

    var mBinding: FragmentNgoTimeclockingBinding? = null
    var commonBean: CommonBean? = null
    var dataListNgoItem: ArrayList<Map<String, Any>>? = null
    var dataListSelfClockingItem: ArrayList<Map<String, Any>>? = null

    companion object {
        fun newInstance() = TimeSheetFragment()
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_ngo_timeclocking, container, false)

        return mBinding!!.root
      //  return inflater.inflate(R.layout.fragment_delinquent, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fetchSelfClockingData()
        fetchDashboardDbData()

    }
    fun setData(commonBean: CommonBean) {
        this.commonBean = commonBean
    }


    fun fetchSelfClockingData() {
        (activity as MainActivity).showProgressBar()
        val requestBody = HashMap<String, Any>()

        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "userInfo"
        CoroutineScope(Dispatchers.IO).launch {

            var job = async { BaseCoroutines().fetchData(requestBody, Busicode.NGOEmployeeWiseSelfClocking, activity as MainActivity) }
            withContext(Dispatchers.Main)
            {

                var response = job.await()
                if (activity != null) {
                    (activity!! as MainActivity).hideProgressBar()
                }


                if (response!!.responseEntity != null && response.status == MappActor.STATUS_OK) {
                    filterSelfClockingData(response)
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


    fun fetchDashboardDbData() {
        (activity as MainActivity).showProgressBar()
        val requestBody = HashMap<String, Any>()

       // requestBody["outlierType"] = type
        requestBody["appRoleCode"] = PreferenceUtility.getString(activity, MyConstants.APP_CODE_NGO, "")
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "userInfo"
        CoroutineScope(Dispatchers.IO).launch {

            var job = async { BaseCoroutines().fetchData(requestBody, Busicode.NGOTSDomain, activity as MainActivity) }
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


                    dataListNgoItem = msg["domainList"] as ArrayList<Map<String, Any>>



                    var adapter = DelinquentAdapter(dataListNgoItem!!, activity as MainActivity?)
                    //  var adapter = AlarmSubAdapter( activity as MainActivity?)
                    delingentList!!.layoutManager = LinearLayoutManager(activity)
                    delingentList!!.itemAnimator = DefaultItemAnimator()
                    delingentList!!.adapter = adapter
                    adapter!!.notifyDataSetChanged()


                    if(msg.containsKey("delinquentEmployeeCount") && msg["delinquentEmployeeCount"]!=null){
                        mBinding!!.txtDelinquentTitle.text = "Delinquency ("+ msg["delinquentEmployeeCount"].toString()+")"
                    }

                } catch (e: Exception) {
                    MyExceptionHandler.handle(e)
                }
            }
            // val responsePayload = msg["responsePayload"] as HashMap<String, Any>
            //val listData = responsePayload["applications"] as List<Map<String, Any>>
            /*listData as MutableList
             listData.addAll(responsePayload["applications"] as List<Map<String, Any>>)
*/

        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
            T.show(activity, activity!!.getString(R.string.something_went_wrong), 0)
        }
    }


    fun filterSelfClockingData(mCoroutinesResponse: CoroutinesResponse) {

        try {
            val msg = mCoroutinesResponse.responseEntity as HashMap<String, Any>


            if (msg != null) {
                try {


                    dataListSelfClockingItem = msg["delinquentDate"] as ArrayList<Map<String, Any>>

                    if (dataListSelfClockingItem!![0].containsKey("dates")) {
                        mBinding!!.txtDate1!!.text = dataListSelfClockingItem!![0]["dates"] as String
                    }
                    if (dataListSelfClockingItem!![1].containsKey("dates")) {
                        mBinding!!.txtDate2!!.text = dataListSelfClockingItem!![1]["dates"] as String
                    }
                    if (dataListSelfClockingItem!![2].containsKey("dates")) {
                        mBinding!!.txtDate3!!.text = dataListSelfClockingItem!![2]["dates"] as String
                    }
                    if (dataListSelfClockingItem!![3].containsKey("dates")) {
                        mBinding!!.txtDate4!!.text = dataListSelfClockingItem!![3]["dates"] as String
                    }
                    if (dataListSelfClockingItem!![4].containsKey("dates")) {
                        mBinding!!.txtDate5!!.text = dataListSelfClockingItem!![4]["dates"] as String
                    }
                    if (dataListSelfClockingItem!![5].containsKey("dates")) {
                        mBinding!!.txtDate6!!.text = dataListSelfClockingItem!![5]["dates"] as String
                    }
                    if (dataListSelfClockingItem!![6].containsKey("dates")) {
                        mBinding!!.txtDate7!!.text = dataListSelfClockingItem!![6]["dates"] as String
                    }


                    if (dataListSelfClockingItem!![0].containsKey("hrs")) {
                        mBinding!!.txtHrs1!!.text = dataListSelfClockingItem!![0]["hrs"] as String + " hrs"
                    }
                    if (dataListSelfClockingItem!![1].containsKey("hrs")) {
                        mBinding!!.txtHrs2!!.text = dataListSelfClockingItem!![1]["hrs"] as String + " hrs"
                    }
                    if (dataListSelfClockingItem!![2].containsKey("hrs")) {
                        mBinding!!.txtHrs3!!.text = dataListSelfClockingItem!![2]["hrs"] as String + " hrs"
                    }
                    if (dataListSelfClockingItem!![3].containsKey("hrs")) {
                        mBinding!!.txtHrs4!!.text = dataListSelfClockingItem!![3]["hrs"] as String + " hrs"
                    }
                    if (dataListSelfClockingItem!![4].containsKey("hrs")) {
                        mBinding!!.txtHrs5!!.text = dataListSelfClockingItem!![4]["hrs"] as String + " hrs"
                    }
                    if (dataListSelfClockingItem!![5].containsKey("hrs")) {
                        mBinding!!.txtHrs6!!.text = dataListSelfClockingItem!![5]["hrs"] as String + " hrs"
                    }
                    if (dataListSelfClockingItem!![6].containsKey("hrs")) {
                        mBinding!!.txtHrs7!!.text = dataListSelfClockingItem!![6]["hrs"] as String + " hrs"
                    }
                    /*if (dataListSelfClockingItem!![7].containsKey("hrs")) {
                        mBinding!!.txtHrs7!!.text = dataListSelfClockingItem!![6]["hrs"] as String + " hrs"
                    }*/


                    if(msg.containsKey("delinquentDays") && msg["delinquentDays"]!=null){
                        mBinding!!.txtDomainId.text = "My Clocking - Delinquency "+msg["delinquentDays"].toString()+" days"
                    }

                    /*mBinding!!.delingentList.apply {
                        layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                        adapter = NgoSelfTimeClockingAdapter(dataListSelfClockingItem!!, activity as MainActivity?)
                    }*/


                    /*var adapter = NgoAlarmSubAdapter(dataListInfraItem!!, activity as MainActivity?)
                    holder.detailsList!!.layoutManager = LinearLayoutManager(activity)
                    holder!!.detailsList!!.itemAnimator = DefaultItemAnimator()
                    holder!!.detailsList!!.adapter = adapter
                    adapter!!.notifyDataSetChanged()*/


                    /* mBinding!!.rvParent.apply {
                         layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                         adapter = NgoAlarmSubAdapter(dataListInfraItem!!, activity as MainActivity?)
                     }*/


                } catch (e: java.lang.Exception) {
                    MyExceptionHandler.handle(e)
                }
            }
            // val responsePayload = msg["responsePayload"] as HashMap<String, Any>
            //val listData = responsePayload["applications"] as List<Map<String, Any>>
            /*listData as MutableList
             listData.addAll(responsePayload["applications"] as List<Map<String, Any>>)
*/

        } catch (e: java.lang.Exception) {
            MyExceptionHandler.handle(e)
            T.show(activity, activity!!.getString(R.string.something_went_wrong), 0)
        }
    }

}
