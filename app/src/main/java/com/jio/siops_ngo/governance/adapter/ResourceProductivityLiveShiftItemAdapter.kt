package com.jio.siops_ngo.governance.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.governance.viewholder.GeneralShiftLiveItemViewHolder
import com.jio.siops_ngo.utilities.MyExceptionHandler
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ResourceProductivityShiftItemAdapter(
    val mainActivity: MainActivity?,
    internal val shiftsDomainIdDataList: ArrayList<Map<String, Any>>,
    val shiftsCitrixSessionDataList: ArrayList<Map<String, Any>>,
    val shiftsNgoSessionDataList: ArrayList<Map<String, Any>>,
    val shiftAlertDataList: ArrayList<Map<String, Any>>
) : RecyclerView.Adapter<GeneralShiftLiveItemViewHolder>() {
    var displayDateFormat: SimpleDateFormat = SimpleDateFormat("dd-MMM")
    var currentDate = ""
    var isUserActive = false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viwType: Int
    ): GeneralShiftLiveItemViewHolder {
        var view: View?

        view = LayoutInflater.from(parent.context).inflate(R.layout.live_shift_item, parent, false)
        var calendar = Calendar.getInstance().getTime();
        currentDate = displayDateFormat.format(calendar)

        return GeneralShiftLiveItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: GeneralShiftLiveItemViewHolder, position: Int) {
        try {
            val content = shiftsDomainIdDataList[position]
            val shiftsCitrixSessionDataMap = shiftsCitrixSessionDataList[position]
            val shiftsNgoSessionDataMap = shiftsNgoSessionDataList[position]
            val shiftsAlertDataMap = shiftAlertDataList[position]
            isUserActive = false
            if (content.containsKey("domainId") && content["domainId"] != null) {
                holder.txtResource!!.text = content["domainId"].toString()
            }

            if (shiftsCitrixSessionDataMap.containsKey("activeStatus") && shiftsCitrixSessionDataMap["activeStatus"] != null) {
                if (shiftsCitrixSessionDataMap["activeStatus"].toString().equals("y", true)) {
                    holder.txtCitrixSessionStatus!!.text = "Active"
                    holder.txtCitrixSessionStatus!!.setTextColor(
                        ContextCompat.getColor(
                            mainActivity!!,
                            R.color.acknowladge_back
                        )
                    )
                } else {
                    holder.txtCitrixSessionStatus!!.text = "Inactive"
                    holder.txtCitrixSessionStatus!!.setTextColor(
                        ContextCompat.getColor(
                            mainActivity!!,
                            R.color.red_error_color
                        )
                    )
                }
            }

            if (shiftsCitrixSessionDataMap.containsKey("sessionStartDateTime") && shiftsCitrixSessionDataMap["sessionStartDateTime"] != null) {

                holder.txtCitrixStartTime!!.text =
                    shiftsCitrixSessionDataMap["sessionStartDateTime"].toString()
                if(shiftsCitrixSessionDataMap["sessionStartDateTime"].toString().contains(currentDate,true)){
                    isUserActive = true
                }
            }

            if (shiftsCitrixSessionDataMap.containsKey("sessionEndDateTime") && shiftsCitrixSessionDataMap["sessionEndDateTime"] != null) {

                holder.txtCitrixEndTime!!.text =
                    shiftsCitrixSessionDataMap["sessionEndDateTime"].toString()
                if(shiftsCitrixSessionDataMap["sessionEndDateTime"].toString().contains(currentDate,true)){
                    isUserActive = true
                }
            }

            if (shiftsNgoSessionDataMap.containsKey("activeStatus") && shiftsNgoSessionDataMap["activeStatus"] != null) {
                if (shiftsNgoSessionDataMap["activeStatus"].toString().equals("y", true)) {
                    holder.txtNgoSessionStatus!!.text = "Active"
                    holder.txtNgoSessionStatus!!.setTextColor(
                        ContextCompat.getColor(
                            mainActivity!!,
                            R.color.acknowladge_back
                        )
                    )
                } else {
                    holder.txtNgoSessionStatus!!.text = "Inactive"
                    holder.txtNgoSessionStatus!!.setTextColor(
                        ContextCompat.getColor(
                            mainActivity!!,
                            R.color.red_error_color
                        )
                    )
                }
            }


            /*if (holder.txtCitrixSessionStatus!!.text.equals("Inactive") && holder.txtNgoSessionStatus!!.text.equals(
                    "Inactive"
                )
            ) {
                holder.imgProdStatus!!.setBackgroundResource(R.drawable.res_productivity_red_rounded)
            } else {
                holder.imgProdStatus!!.setBackgroundResource(R.drawable.green_rounded)
            }*/
            if (shiftsNgoSessionDataMap.containsKey("sessionStartDateTime") && shiftsNgoSessionDataMap["sessionStartDateTime"] != null) {

                holder.txtNgoStartTime!!.text =
                    shiftsNgoSessionDataMap["sessionStartDateTime"].toString()

                if(shiftsNgoSessionDataMap["sessionStartDateTime"].toString().contains(currentDate,true)){
                    isUserActive = true
                }
            }

            if (shiftsNgoSessionDataMap.containsKey("sessionEndDateTime") && shiftsNgoSessionDataMap["sessionEndDateTime"] != null) {

                holder.txtNgoEndTime!!.text =
                    shiftsNgoSessionDataMap["sessionEndDateTime"].toString()

                if(shiftsNgoSessionDataMap["sessionEndDateTime"].toString().contains(currentDate,true)){
                    isUserActive = true
                }
            }

            if (shiftsAlertDataMap.containsKey("totalAcknowledged") && shiftsAlertDataMap["totalAcknowledged"] != null) {
                holder.txtAlertAcknwCount!!.text =
                    shiftsAlertDataMap["totalAcknowledged"].toString()
            }


            if (isUserActive){
                holder.imgProdStatus!!.setBackgroundResource(R.drawable.green_rounded)
            }else{
                holder.imgProdStatus!!.setBackgroundResource(R.drawable.res_productivity_red_rounded)
            }


            holder.txtDetails!!.setOnClickListener {
                holder.constHide!!.visibility = View.VISIBLE
                holder.txtDetails!!.visibility = View.GONE
            }
            holder.txtHide!!.setOnClickListener {
                holder.constHide!!.visibility = View.GONE
                holder.txtDetails!!.visibility = View.VISIBLE

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
