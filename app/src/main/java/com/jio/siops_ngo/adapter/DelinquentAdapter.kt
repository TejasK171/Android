package com.jio.siops_ngo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.network.business.BaseCoroutines
import com.jio.jioinfra.utilities.Busicode
import com.jio.jioinfra.utilities.MyConstants
import com.jio.myjio.bean.CoroutinesResponse
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.T
import com.jio.siops_ngo.viewholder.NgoDeliquencyItemViewHolder
import kotlinx.coroutines.*
import java.lang.Exception

class DelinquentAdapter(private val dataList: List<Map<String, Any>>, private val activity: MainActivity?) : RecyclerView.Adapter<NgoDeliquencyItemViewHolder>() {

    var dataListNgoItem: ArrayList<Map<String, Any>>? = null
    var map:HashMap<Int,Int>? = hashMapOf()

    override fun onCreateViewHolder(parent: ViewGroup, viwType: Int): NgoDeliquencyItemViewHolder {
        var view: View?


        view = LayoutInflater.from(parent.context).inflate(R.layout.ngo_deliquency_sub_item, parent, false)

        return NgoDeliquencyItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: NgoDeliquencyItemViewHolder, position: Int) {

        val content = dataList[position]

        if(content.containsKey("domainName")){
            holder!!.txtTitle!!.text= content["domainName"] as String
        }

        if(content.containsKey("count")){
            holder!!.txtCount!!.text= content["count"].toString()
        }


        try {


            if(map!!.containsKey(position)){

                holder.cnstrntL_background!!.setBackgroundResource(R.drawable.blue_background)
                holder!!.imgDropDown!!!!.visibility = View.GONE
                holder!!.up_down!!!!.visibility = View.VISIBLE
                holder.txtTitle!!.setTextColor(activity!!.getResources().getColor(R.color.white))
                holder.txtCount!!.setTextColor(activity!!.getResources().getColor(R.color.white))
                holder.detailsList!!.visibility = View.VISIBLE

            }else{
                holder.cnstrntL_background!!.setBackgroundResource(R.drawable.white_rounded_corner_bg)
                holder.txtTitle!!.setTextColor(activity!!.getResources().getColor(R.color.black))
                holder.txtCount!!.setTextColor(activity!!.getResources().getColor(R.color.blue_text))

                // holder!!.imgDropDown!!.setBackgroundResource(R.drawable.ic_up)
                holder.detailsList!!.visibility = View.GONE
                holder!!.imgDropDown!!!!.visibility = View.VISIBLE
                holder!!.up_down!!!!.visibility = View.GONE
            }

            holder.up_down!!.setOnClickListener {
                if(map!!.containsKey(position)){
                    map!!.remove(position)
                }
              //  var appType :String = content["featureName"] as String

              //  fetchDashboardDbData(Busicode.NGOOnclickType,appType,holder)


                holder.cnstrntL_background!!.setBackgroundResource(R.drawable.white_rounded_corner_bg)
                holder.txtTitle!!.setTextColor(activity!!.getResources().getColor(R.color.black))
                holder.txtCount!!.setTextColor(activity!!.getResources().getColor(R.color.blue_text))

                // holder!!.imgDropDown!!.setBackgroundResource(R.drawable.ic_up)
                holder.detailsList!!.visibility = View.GONE
                holder!!.imgDropDown!!!!.visibility = View.VISIBLE
                holder!!.up_down!!!!.visibility = View.GONE
                //  boolean=true

            }

            holder.imgDropDown!!.setOnClickListener {

                map!!.put(position,position)
                var domainName :String = content["domainName"] as String





                fetchDashboardDbData(domainName,holder)



            }


        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


    fun fetchDashboardDbData(domeinName:String,holder: NgoDeliquencyItemViewHolder) {
        (activity as MainActivity).showProgressBar()
        val requestBody = HashMap<String, Any>()

       // requestBody["outlierType"] = type
        requestBody["appRoleCode"] = PreferenceUtility.getString(activity, MyConstants.APP_CODE_NGO, "")
        requestBody["domainName"] = domeinName
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "userInfo"
        CoroutineScope(Dispatchers.IO).launch {

            var job = async { BaseCoroutines().fetchData(requestBody, Busicode.NGOTSPlatform, activity as MainActivity) }
            withContext(Dispatchers.Main)
            {

                var response = job.await()
                if (activity != null) {
                    (activity!! as MainActivity).hideProgressBar()
                }


                if (response!!.responseEntity != null && response.status == MappActor.STATUS_OK) {
                    filterData(domeinName, response,holder)
                } else if (response!!.errorCode != null && response!!.errorCode.equals(MappActor.VERSION_SESSION_INVALID)){
                    activity!!.showDialogForSessionExpired(activity.resources.getString(R.string.session_expired), activity)
                } else if (response!!.errorMsg != null) {
                    T.show(activity, response!!.errorMsg!!, 0)
                } else {
                    T.show(activity, "Something went wrong!", 0)
                }
            }

        }
    }
    fun filterData(domainName: String, mCoroutinesResponse: CoroutinesResponse,holder: NgoDeliquencyItemViewHolder) {

        try {
            val msg = mCoroutinesResponse.responseEntity as HashMap<String, Any>


            if (msg != null) {
                try {


                    dataListNgoItem = msg["platformList"] as ArrayList<Map<String, Any>>



                    var adapter = DelinquentSubAdapter(dataListNgoItem!!, activity as MainActivity?, domainName)
                    //  var adapter = AlarmSubAdapter( activity as MainActivity?)
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


}