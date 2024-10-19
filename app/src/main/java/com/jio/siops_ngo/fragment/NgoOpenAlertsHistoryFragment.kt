package com.jio.siops_ngo.fragment


import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.jio.jioinfra.bean.CommonBean
import com.jio.siops_ngo.MainActivity

import com.jio.siops_ngo.R
import com.jio.siops_ngo.adapter.NgoOpenAlarmsAdapter
import com.jio.siops_ngo.databinding.FragmentOpenAlarmsBinding
import androidx.core.content.res.ResourcesCompat
import com.jio.jioinfra.network.business.BaseCoroutines
import com.jio.jioinfra.utilities.Busicode
import com.jio.jioinfra.utilities.MyConstants
import com.jio.myjio.bean.CoroutinesResponse
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.T
import kotlinx.coroutines.*


/**
 * A simple [Fragment] subclass.
 */
class NgoOpenAlertsHistoryFragment : Fragment() {


    var mBinding: FragmentOpenAlarmsBinding? = null
    var commonBean: CommonBean? = null
    var selection: Int? = 0
    var openAlertsDataMap: Map<String, List<Map<String, Any>>>? = null
    var openAlertsDataList: List<Map<String, Any>>? = null
    var boldTypeface: Typeface? = null
    var lightTypeface: Typeface? = null

    companion object {
        fun newInstance() = NgoOpenAlertsHistoryFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_open_alarms, container, false)
//        openAlertsDataMap = commonBean!!.`object` as Map<String, List<Map<String, Any>>>

        return mBinding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        boldTypeface = ResourcesCompat.getFont(activity!!, R.font.jio_type_bold)
        lightTypeface = ResourcesCompat.getFont(activity!!, R.font.jio_type_light)


        fetchDashboardData()


    }


    fun fetchDashboardData() {
        (activity as MainActivity).showProgressBar()

        val requestBody = HashMap<String, Any>()
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "userInfo"
        CoroutineScope(Dispatchers.IO).launch {

            var job = async { BaseCoroutines().fetchData(requestBody, Busicode.NGOHistorySummary, activity as MainActivity) }
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


            if (msg != null)
            {
                try {


                    var responsePayLoadMap: Map<String,List<Map<String, Any>>> = msg as Map<String,List<Map<String, Any>>>

                    if (responsePayLoadMap  != null  ) {

                        openAlertsDataMap = responsePayLoadMap
                        inflateUI()

                        /*mBinding!!.rvParent.apply {
                            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                            adapter = NgoDashboardAdapter(commonBeanMainServices.`object` as Map<String, List<Map<String, Any>>>, (commonBean!!.`object`) as List<Map<String, Any>>, (activity as MainActivity), commonBean!!)
                        }*/

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

        }
        catch (e:Exception)
        {
            MyExceptionHandler.handle(e)
            T.show(activity,getString(R.string.something_went_wrong),0)
        }
    }



    fun inflateUI() {


        if (openAlertsDataMap!!.containsKey("openAlerts")) {
            var openAlertsList = openAlertsDataMap!!["openAlerts"] as List<Map<String, Any>>
            if (openAlertsList != null && openAlertsList.size > 0) {

                if (hasIndex(0, openAlertsList)) {
                    if (openAlertsList.get(0).containsKey("featureName"))
                        mBinding!!.title1!!.text =
                            openAlertsList.get(0)["featureName"] as String

                    if (openAlertsList.get(0).containsKey("outlierCount"))


                        mBinding!!.count1!!.text =
                            openAlertsList.get(0)["outlierCount"]!!.toString()


                }

                if (hasIndex(1, openAlertsList)) {
                    if (openAlertsList.get(1).containsKey("featureName"))
                        mBinding!!.title2!!.text =
                            openAlertsList.get(1)["featureName"] as String

                    if (openAlertsList.get(1).containsKey("outlierCount"))


                        mBinding!!.count2!!.text =
                            openAlertsList.get(1)["outlierCount"]!!.toString()


                }

                if (hasIndex(2, openAlertsList)) {
                    if (openAlertsList.get(2).containsKey("featureName"))
                        mBinding!!.title3!!.text =
                            openAlertsList.get(2)["featureName"] as String

                    if (openAlertsList.get(2).containsKey("outlierCount"))


                        mBinding!!.count3!!.text =
                            openAlertsList.get(2)["outlierCount"]!!.toString()


                }
            }
        }

        changeSelection(selection!!)


        mBinding!!.card1.setOnClickListener { changeSelection(0) }
        mBinding!!.card2.setOnClickListener { changeSelection(1) }
        mBinding!!.card3.setOnClickListener { changeSelection(2) }



    }

    fun setData(commonBean: CommonBean, selection: Int) {
        this.commonBean = commonBean
        this.selection = selection
    }

    fun hasIndex(index: Int, dataList: List<Map<String, Any>>): Boolean {
        if (index < dataList!!.size)
            return true
        else
            return false

    }

    fun changeSelection(selection: Int){
        if(selection == 0){
            mBinding!!.count1.setTypeface(boldTypeface)
            mBinding!!.count2.setTypeface(lightTypeface)
            mBinding!!.count3.setTypeface(lightTypeface)
            if (openAlertsDataMap!!.containsKey("infra")) {
                openAlertsDataList = openAlertsDataMap!!["infra"] as List<Map<String, Any>>
            }

        }else if (selection == 1){
            mBinding!!.count2.setTypeface(boldTypeface)
            mBinding!!.count1.setTypeface(lightTypeface)
            mBinding!!.count3.setTypeface(lightTypeface)
            if (openAlertsDataMap!!.containsKey("application")) {
                openAlertsDataList = openAlertsDataMap!!["application"] as List<Map<String, Any>>
            }
        }else if(selection == 2){
            mBinding!!.count3.setTypeface(boldTypeface)
            mBinding!!.count2.setTypeface(lightTypeface)
            mBinding!!.count1.setTypeface(lightTypeface)
            if (openAlertsDataMap!!.containsKey("tools")) {
                openAlertsDataList = openAlertsDataMap!!["tools"] as List<Map<String, Any>>
            }
        }

        var adapter = NgoOpenAlarmsAdapter(Busicode.NGOAlertHistory, openAlertsDataList!!, activity as MainActivity?)
        mBinding!!.recyclelistView.layoutManager = LinearLayoutManager(activity)
        mBinding!!.recyclelistView.itemAnimator = DefaultItemAnimator()
        mBinding!!.recyclelistView!!.adapter = adapter
        adapter!!.notifyDataSetChanged()



    }

}
