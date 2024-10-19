package com.jio.siops_ngo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.bean.CommonBean
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.fragment.NgoOpenAlertsDetailsFragment
import com.jio.siops_ngo.viewholder.NgoOpenAlarmsViewHolder
import java.lang.Exception

class NgoOpenAlarmsAdapter(val busicodeForNgoDetails: String,
    private val dataList: List<Map<String, Any>>,
    private val activity: MainActivity?
) : RecyclerView.Adapter<NgoOpenAlarmsViewHolder>() {

    var boolean: Boolean = true
    var dataListNgoItem: ArrayList<Map<String, Any>>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viwType: Int): NgoOpenAlarmsViewHolder {
        var view: View?


        view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ngo_alarm_drop_down_item, parent, false)

        return NgoOpenAlarmsViewHolder(view)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: NgoOpenAlarmsViewHolder, position: Int) {
        try {
            val content = dataList[position]
//            holder.detailsList!!.visibility = View.GONE

            if(content.contains("featureName"))
                holder.txtTitle!!.text = content["featureName"] as String

            if(content.contains("outlierCount"))
                holder.txtCount!!.text = content["outlierCount"].toString()

            /*holder.up_down!!.setOnClickListener {

                var appType :String = content["featureName"] as String

                holder.cnstrntL_background!!.setBackgroundResource(R.drawable.white_rounded_corner_bg)
                holder.txtTitle!!.setTextColor(activity!!.getResources().getColor(R.color.black))
                holder.txtCount!!.setTextColor(activity!!.getResources().getColor(R.color.blue_text))

                holder.detailsList!!.visibility = View.GONE
                holder!!.imgDropDown!!!!.visibility = View.VISIBLE
                holder!!.up_down!!!!.visibility = View.GONE
                //  boolean=true

            }*/

            holder.imgDropDown!!.setOnClickListener {


                var appType :String = content["featureName"] as String

                val commonBean = CommonBean()
                var ngoDetailFragment = NgoOpenAlertsDetailsFragment.newInstance()
                commonBean.`object` = content
                ngoDetailFragment.setData(commonBean!!, busicodeForNgoDetails)
                (activity as MainActivity).openFragments(ngoDetailFragment, commonBean, true, true)

//                fetchDashboardDbData(busicodeForNgoDetails, appType,holder)

                /*holder.cnstrntL_background!!.setBackgroundResource(R.drawable.blue_background)
                holder!!.imgDropDown!!!!.visibility = View.GONE
                holder!!.up_down!!!!.visibility = View.VISIBLE

                holder.detailsList!!.visibility = View.VISIBLE


                holder.txtTitle!!.setTextColor(activity!!.getResources().getColor(R.color.white))
                holder.txtCount!!.setTextColor(activity!!.getResources().getColor(R.color.white))
                boolean = false*/


            }

            //   val content = appList[position]
            //   var title = content["applicationName"] as String

            //   holder.txtTitle!!.text = title!!
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }



    /*fun fetchDashboardDbData(busicode:String,type:String,holder: OpenAlarmsViewHolder) {
        (activity as MainActivity).showProgressBar()
        val requestBody = HashMap<String, Any>()

        requestBody["outlierType"] = type
        requestBody["appRoleCode"] = "726"
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "userInfo"
        CoroutineScope(Dispatchers.IO).launch {

            var job = async { BaseCoroutines().fetchData(requestBody, busicode, activity as MainActivity) }
            withContext(Dispatchers.Main)
            {

                var response = job.await()
                if (activity != null) {
                    (activity!! as MainActivity).hideProgressBar()
                }


                if (response!!.responseEntity != null && response.status == MappActor.STATUS_OK) {
                    filterData(response,holder)
                } else if (response!!.errorMsg != null) {
                    T.show(activity, response!!.errorMsg!!, 0)
                } else {
                    T.show(activity, "Something went wrong!", 0)
                }
            }

        }
    }*/
    /*fun filterData(mCoroutinesResponse: CoroutinesResponse,holder: OpenAlarmsViewHolder) {

        try {
            val msg = mCoroutinesResponse.responseEntity as HashMap<String, Any>


            if (msg != null) {
                try {


                    dataListInfraItem = msg["mainOutlier"] as ArrayList<Map<String, Any>>



                    var adapter = NgoAlarmSubAdapter(dataListInfraItem!!, activity as MainActivity?)
                    holder.detailsList!!.layoutManager = LinearLayoutManager(activity)
                    holder!!.detailsList!!.itemAnimator = DefaultItemAnimator()
                    holder!!.detailsList!!.adapter = adapter
                    adapter!!.notifyDataSetChanged()


                    holder.cnstrntL_background!!.setBackgroundResource(R.drawable.blue_background)
                    holder!!.imgDropDown!!!!.visibility = View.GONE
                    holder!!.up_down!!!!.visibility = View.VISIBLE

                    holder.detailsList!!.visibility = View.VISIBLE


                    holder.txtTitle!!.setTextColor(activity!!.getResources().getColor(R.color.white))
                    holder.txtCount!!.setTextColor(activity!!.getResources().getColor(R.color.white))
                    boolean = false



                } catch (e: Exception) {
                    MyExceptionHandler.handle(e)
                }
            }
            // val responsePayload = msg["responsePayload"] as HashMap<String, Any>
            //val listData = responsePayload["applications"] as List<Map<String, Any>>
            *//*listData as MutableList
             listData.addAll(responsePayload["applications"] as List<Map<String, Any>>)
*//*

        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
            T.show(activity, activity!!.getString(R.string.something_went_wrong), 0)
        }
    }*/


}