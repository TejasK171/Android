package com.jio.siops_ngo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.viewholder.InfraOpenAlarmsHistoryViewHolder


class InfraOpenAlarmsHistoryAdapter(
    private val datalist: ArrayList<Map<String, Any>>, private val activity: MainActivity?
) : RecyclerView.Adapter<InfraOpenAlarmsHistoryViewHolder>() {

    var boolean: Boolean = true
    var dataListNgoItem: ArrayList<Map<String, Any>>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viwType: Int): InfraOpenAlarmsHistoryViewHolder {
        var view: View?


        view = LayoutInflater.from(parent.context)
            .inflate(R.layout.infra_open_alarms_history_item, parent, false)

        return InfraOpenAlarmsHistoryViewHolder(view)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: InfraOpenAlarmsHistoryViewHolder, position: Int) {
        try {
            val content = datalist[position]
            if(content.containsKey("startDate") && content.get("startDate")!=null){

                holder.txtStartDate!!.text = content["startDate"] as String
            }

            if(content.containsKey("ageing") && content.get("ageing")!=null){

                holder.txtAgeing!!.text = content["ageing"].toString()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {


//        if(datalist.size>5){
//            return 5;
//        }else{
        return datalist.size;
        //    }
        // return datalist.size
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
    }
    fun filterData(mCoroutinesResponse: CoroutinesResponse, holder: OpenAlarmsViewHolder) {

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