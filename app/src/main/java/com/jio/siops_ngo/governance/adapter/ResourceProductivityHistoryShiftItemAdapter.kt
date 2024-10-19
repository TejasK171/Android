package com.jio.siops_ngo.governance.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.governance.viewholder.GeneralShiftLiveItemViewHolder
import com.jio.siops_ngo.governance.viewholder.ProductivityHistoryItemViewHolder
import com.jio.siops_ngo.utilities.MyExceptionHandler

class ResourceProductivityHistoryShiftItemAdapter(val mainActivity: MainActivity?, internal val shiftsDomainIdDataList: ArrayList<Map<String, Any>>, val shiftsCitrixSessionDataList: ArrayList<Map<String, Any>>,
                                           val shiftsNgoSessionDataList: ArrayList<Map<String, Any>>, val shiftAlertDataList: ArrayList<Map<String, Any>>,
                                                  val shiftTimeSheetDataList: ArrayList<Map<String, Any>>) : RecyclerView.Adapter<ProductivityHistoryItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viwType: Int): ProductivityHistoryItemViewHolder {
        var view: View?

        view = LayoutInflater.from(parent.context).inflate(R.layout.history_shift_item, parent, false)

        return ProductivityHistoryItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductivityHistoryItemViewHolder, position: Int) {
        try {
              val content = shiftsDomainIdDataList[position]
              val shiftsCitrixSessionDataMap = shiftsCitrixSessionDataList[position]
              val shiftsNgoSessionDataMap = shiftsNgoSessionDataList[position]
              val shiftTimesheetDataMap = shiftTimeSheetDataList[position]
              val shiftsAlertDataMap = shiftAlertDataList[position]
            if(content.containsKey("domainId") && content["domainId"]!=null){
                holder.txtResource!!.text = content["domainId"].toString()
            }

            if(shiftsCitrixSessionDataMap.containsKey("totalActivity") && shiftsCitrixSessionDataMap["totalActivity"]!=null){
                    holder.txtCitrixTime!!.text = shiftsCitrixSessionDataMap["totalActivity"].toString()
            }

            if(shiftsNgoSessionDataMap.containsKey("totalActivity") && shiftsNgoSessionDataMap["totalActivity"]!=null){
                holder.txtNgoTime!!.text = shiftsNgoSessionDataMap["totalActivity"].toString()
            }
            if(shiftTimesheetDataMap.containsKey("k2STATUS") && shiftTimesheetDataMap["k2STATUS"]!=null && shiftTimesheetDataMap["k2STATUS"].toString().equals("Ex",true)){
                holder.txtTimeClocked!!.text = shiftTimesheetDataMap["k2STATUS"].toString()
            }else if(shiftTimesheetDataMap.containsKey("sumOfTicketActualTimeMin") && shiftTimesheetDataMap["sumOfTicketActualTimeMin"]!=null ){
                holder.txtTimeClocked!!.text = shiftTimesheetDataMap["sumOfTicketActualTimeMin"].toString()
            }

            if(shiftsAlertDataMap.containsKey("totalAcknowledged") && shiftsAlertDataMap["totalAcknowledged"]!=null){
                holder.txtAlertsAcknowledged!!.text = shiftsAlertDataMap["totalAcknowledged"].toString()
            }
            if(shiftsAlertDataMap.containsKey("avgAckTimeHrs") && shiftsAlertDataMap["avgAckTimeHrs"]!=null){
                holder.txtAlertAckTime!!.text = shiftsAlertDataMap["avgAckTimeHrs"].toString()
            }
            if(shiftsAlertDataMap.containsKey("delayed") && shiftsAlertDataMap["delayed"]!=null){

                holder.txtDelayedAckAlerts!!.text = shiftsAlertDataMap["delayed"].toString()
                if(shiftsAlertDataMap["delayed"].toString().equals(0)){
                    holder.txtDelayedAckAlerts!!.setTextColor(ContextCompat.getColor(mainActivity!!, R.color.red_error_color))
                }else{
                    holder.txtDelayedAckAlerts!!.setTextColor(ContextCompat.getColor(mainActivity!!, R.color.jioinfra_gray))
                }
            }

            if(holder.txtCitrixTime!!.text.equals("00:00:00") && holder.txtNgoTime!!.text.equals("00:00:00")){
                holder.imgProdStatus!!.setBackgroundResource(R.drawable.res_productivity_red_rounded)
            }else{
                holder.imgProdStatus!!.setBackgroundResource(R.drawable.green_rounded)
            }



            holder.txtDetails!!.setOnClickListener {
                holder.constHide!!.visibility=View.VISIBLE
                holder.txtDetails!!.visibility=View.GONE
            }
            holder.txtHide!!.setOnClickListener {
                holder.constHide!!.visibility=View.GONE
                holder.txtDetails!!.visibility=View.VISIBLE

            }
            //   holder.txtDcbItemTitle!!.text = content["featureId"] as String



        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
        }
    }

    override fun getItemCount(): Int {
        return shiftsDomainIdDataList.size
    }
}
