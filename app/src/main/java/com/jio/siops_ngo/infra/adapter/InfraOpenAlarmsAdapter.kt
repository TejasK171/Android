package com.jio.siops_ngo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.bean.CommonBean
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.bean.FilterGetterAndSetter
import com.jio.siops_ngo.fragment.InfraOpenAlertsHistoryFragment
import com.jio.siops_ngo.infra.viewholder.InfraOpenAlarmsViewHolder


class InfraOpenAlarmsAdapter(
    private val datalist: ArrayList<Map<String, Any>>,
    private val activity: MainActivity?,
    var name: String,
    var catagry: String,
    var mFilterGetterAndSetter: FilterGetterAndSetter
) : RecyclerView.Adapter<InfraOpenAlarmsViewHolder>() {

    var boolean: Boolean = true
    var dataListNgoItem: ArrayList<Map<String, Any>>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viwType: Int): InfraOpenAlarmsViewHolder {
        var view: View?


        view = LayoutInflater.from(parent.context)
            .inflate(R.layout.alarm_drop_down_item, parent, false)

        return InfraOpenAlarmsViewHolder(view)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: InfraOpenAlarmsViewHolder, position: Int) {
        try {
            val content = datalist[position]
            // holder.detailsList!!.visibility = View.GONE

            if (content.contains("name"))

            //   val str:String=content["name"] as String
                holder.txtTitle!!.text = content["name"] as String

            val str: String = content["name"] as String
            val length: Int = str.length

            //if(length)

            if (content.contains("count"))
                holder.txtCount!!.text = content["count"].toString()

//
            holder.imgDropDown!!.setOnClickListener {

                val commonBeanNew = CommonBean()
                commonBeanNew.`object` = content
                var infraNotificationFragment = InfraOpenAlertsHistoryFragment.newInstance()
                infraNotificationFragment.setData(commonBeanNew,name,holder.txtTitle!!.text.toString().trim(),catagry!!,mFilterGetterAndSetter!!)
                ((activity as MainActivity).openFragments(
                    infraNotificationFragment!!,
                    commonBeanNew,
                    true,
                    false
                ))
            }

            //   val content = appList[position]
            //   var title = content["applicationName"] as String

            //   holder.txtTitle!!.text = title!!
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