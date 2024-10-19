package com.jio.siops_ngo.infra.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.network.business.BaseCoroutines
import com.jio.jioinfra.utilities.Busicode
import com.jio.jioinfra.utilities.MyConstants
import com.jio.myjio.bean.CoroutinesResponse
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.infra.viewholder.OrderJouneryViewholder
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.T
import com.jio.siops_ngo.utilities.Utils
import kotlinx.coroutines.*


class OrderActivationJourneryCircleAdapter(
    private val activity: MainActivity?,
    private val activationCircleRespMapList: ArrayList<Map<String, Any>>,
    var category: String,
    var apiRequestFormattedDate: String?,
    var circleName: String,
    var categoryToDisplay: String
) : RecyclerView.Adapter<OrderJouneryViewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viwType: Int): OrderJouneryViewholder {
        var view: View?

        view = LayoutInflater.from(parent.context).inflate(R.layout.journey_item_view, parent, false)

        return OrderJouneryViewholder(view)
    }



    override fun onBindViewHolder(holder: OrderJouneryViewholder, position: Int) {
        try {
         //   var activationData : HashMap<String,  =  activationCircleRespMapList: ArrayList<Map<String, Any>>

            val activationData = activationCircleRespMapList[position]

//            when (position) {
//                0 -> activationData = activationJourneyMap["allChannels"] as HashMap<String, Any>
//                1 -> activationData = activationJourneyMap["generalTrade"] as HashMap<String, Any>
//                2 -> activationData = activationJourneyMap["relianceRetail"] as HashMap<String, Any>
//            }

            if(activationData!!.containsKey("zone") && activationData.get("zone")!=null){
                if(position == 0)
                    holder.txtTitle!!.text = activationData["zone"] as String+" ("+categoryToDisplay+")"
                else{
                    holder.txtTitle!!.text = activationData["zone"] as String
                }
            }
            if(activationData!!.containsKey("entered") && activationData.get("entered")!=null){
//                holder.txtEnteredCount!!.text = activationData["entered"].toString()
                holder.txtEnteredCount!!.text = Utils.getCommaSeparatedCount(activationData["entered"] as Int)
            }
            if(activationData!!.containsKey("activated") && activationData.get("activated")!=null){
//                holder.txtActivatedCount!!.text = activationData["activated"].toString()
                holder.txtActivatedCount!!.text = Utils.getCommaSeparatedCount(activationData["activated"] as Int)
            }
            if(activationData!!.containsKey("network_latched") && activationData.get("network_latched")!=null){
//                holder.txtNetworkLatchedCount!!.text = activationData["network_latched"].toString()
                holder.txtNetworkLatchedCount!!.text = Utils.getCommaSeparatedCount(activationData["network_latched"] as Int)
            }
            if(activationData!!.containsKey("rejected") && activationData.get("rejected")!=null){
//                holder.txtRejectedCount!!.text = activationData["rejected"].toString()
                holder.txtRejectedCount!!.text = Utils.getCommaSeparatedCount(activationData["rejected"] as Int)
            }
            if(activationData!!.containsKey("in_process") && activationData.get("in_process")!=null){
//                holder.txtInProcessCount!!.text = activationData["in_process"].toString()
                holder.txtInProcessCount!!.text = Utils.getCommaSeparatedCount(activationData["in_process"] as Int)
            }
            if(activationData!!.containsKey("tv_pending") && activationData.get("tv_pending")!=null){
//                holder.txtTvPendingCount!!.text = activationData["tv_pending"].toString()
                holder.txtTvPendingCount!!.text = Utils.getCommaSeparatedCount(activationData["tv_pending"] as Int)
            }


            holder.txtLessDetails!!.setOnClickListener {
                if(holder.cnstrntLHiddenRow!!.visibility== View.VISIBLE){
                    holder.txtLessDetails!!.text = "More details"
                    holder.cnstrntLHiddenRow!!.visibility=View.GONE
                    holder.txtLessDetails!!.setBackgroundDrawable(activity!!.resources.getDrawable(R.drawable.grey_rounded_corner_bg))
                    holder.txtLessDetails!!.setTextColor(activity!!.resources.getColor(R.color.black))
                }else{
                    holder.txtLessDetails!!.text = "Less details"
                    holder.cnstrntLHiddenRow!!.visibility=View.VISIBLE
                    holder.txtLessDetails!!.setBackgroundDrawable(activity!!.resources.getDrawable(R.drawable.blue_rounded_corner_bg))
                    holder.txtLessDetails!!.setTextColor(activity!!.resources.getColor(R.color.white))
                }

            }



            holder.txtLessDetails!!.setTextColor(activity!!.resources.getColor(R.color.text_color_grey))
            holder.txtLessDetails!!.setOnClickListener {
                if(holder.cnstrntLHiddenRow!!.visibility== View.VISIBLE){
                    holder.txtLessDetails!!.text =activity.getString(R.string.viewdetails)
                    holder.cnstrntLHiddenRow!!.visibility=View.GONE
                    holder.cnstrntL_inProcess!!.visibility = View.GONE
                    holder.txtLessDetails!!.setBackgroundDrawable(activity!!.resources.getDrawable(R.drawable.grey_rounded_corner_bg))
                    holder.txtLessDetails!!.setTextColor(activity!!.resources.getColor(R.color.text_color_grey))
                }else{
                    holder.txtLessDetails!!.text = activity.getString(R.string.hide_details)
                    holder.cnstrntL_inProcess!!.visibility = View.GONE
                    holder.cnstrntLHiddenRow!!.visibility=View.VISIBLE
                    holder.txtLessDetails!!.setBackgroundDrawable(activity!!.resources.getDrawable(R.drawable.blue_rounded_corner_bg))
                    holder.txtLessDetails!!.setTextColor(activity!!.resources.getColor(R.color.white))
                }

            }

            //  holder.cnstrntL_inProcess!!.visibility=View.GONE

            holder.card4!!.setOnClickListener {


                holder.txt_process_header!!.text="Rejected"


                fetchData(
                    holder,
                    Busicode.O2ARejected,
                    1,
                    position,
                    holder.txtTitle!!.text.toString()
                )





            }


            holder.card5!!.setOnClickListener {

                holder.txt_process_header!!.text="In Process"
                fetchData(
                    holder,
                    Busicode.O2AInProcess,
                    0,
                    position,
                    holder.txtTitle!!.text.toString()
                )


            }










//            holder.txtValue!!.text = content["key"] as String
//
//
//            holder.txtCount!!.text = content["value"]!!.toString()
//
//            holder.dcbConstraintLayout!!.setOnClickListener {
//
//                try {
//                    if (content.containsKey("onclick") && content.containsKey("onclick") != null){
//                        if ((content["onclick"] as Int).toString().equals("0")) {
//                            val commonBean = CommonBean()
//                            commonBean.setmSubTitle(content["key"] as String+ " ("+ content["value"]!!.toString()+")")
//                            var dcbDashboardClickFragment = DcbDashboardClickFragment.newInstance()
//                            commonBean.`object` = content
//                            dcbDashboardClickFragment.setData(commonBean, content["key"] as String, appRoleCode)
//                            (activity)!!.openFragments(dcbDashboardClickFragment, commonBean, true, true)
//                        }
//                }
//
//                } catch (e: Exception) {
//                    MyExceptionHandler.handle(e)
//                }
//            }
//
        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
        }
    }

    override fun getItemCount(): Int {
        return activationCircleRespMapList.size
    }


    fun fetchData(
        holder: OrderJouneryViewholder,
        busiCode: String,
        show: Int,
        position: Int,
        region: String
    ) {
        (activity as MainActivity).showProgressBar()

        val requestBody = HashMap<String, Any>()
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "userInfo"
        requestBody["appRoleCode"] = MyConstants.APP_ROLE_CODE_NGO


        if(position==0){
            requestBody["channel"] = category
            requestBody["zone"] = circleName
            // requestBody["region"] = "Region"
        }else {
            requestBody["channel"] = category
             requestBody["region"] =region
            requestBody["zone"] = circleName
        }
//        requestBody["zone"] = ""
//        requestBody["region"] = ""
        requestBody["date"] = apiRequestFormattedDate!!
        CoroutineScope(Dispatchers.IO).launch {

            var job = async {
                BaseCoroutines().fetchData(
                    requestBody,
                    busiCode,
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
                    filterData(response,holder,show)
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


    fun filterData(
        mCoroutinesResponse: CoroutinesResponse,
        holder: OrderJouneryViewholder,
        show: Int
    ) {

        try {
            val msg = mCoroutinesResponse.responseEntity as HashMap<String, Any>


            if (msg != null) {
                try {

                    if(show==0) {
                        holder.cnstrntL_inProcess!!.visibility = View.VISIBLE
                        holder.title1_wo!!.text = msg["cafScanning"].toString()
                        holder.title2_wo!!.text = msg["inTransit"].toString()
                        holder.title3_wo!!.text = msg["inApproval"].toString()
                        holder.title4_wo!!.text = msg["lrCheck"].toString()
                        holder.count1_wo!!.text = "CFA Scanning"
                        holder.count2_wo!!.text = "In Transit"
                        holder.count3_wo!!.text = "In Approval"
                        holder.count4_wo!!.text = "Lr Check"
                        holder.card5_wo!!.visibility = View.GONE
                    }else{
                        holder.cnstrntL_inProcess!!.visibility = View.VISIBLE
                        holder.title1_wo!!.text = msg["deDupe"].toString()
                        holder.title2_wo!!.text = msg["deDupeSys"].toString()
                        holder.title3_wo!!.text = msg["ao"].toString()
                        holder.title4_wo!!.text = msg["lrCheck"].toString()
                        holder.title5_wo!!.text = msg["cancelled"].toString()
                        holder.count1_wo!!.text = "De Dupe"
                        holder.count2_wo!!.text = "De DupeSys"
                        holder.count3_wo!!.text = "AO"
                        holder.count4_wo!!.text = "Lr Check"
                        holder.count5_wo!!.text = "Cancelled"

                        holder.card5_wo!!.visibility = View.VISIBLE
                    }

                    //   rechargeJourneryList = msg["rechargeJourney"] as ArrayList<Map<String, Any>>
                    //  rechargePandingList = msg["rechargePending"] as ArrayList<Map<String, Any>>





                } catch (e: Exception) {
                    MyExceptionHandler.handle(e)
                }
            }
        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
            T.show(activity,activity!!.getString(R.string.something_went_wrong), 0)
        }
    }
}