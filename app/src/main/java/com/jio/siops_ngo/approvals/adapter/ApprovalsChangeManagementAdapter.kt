package com.jio.siops_ngo.ngo.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.bean.CommonBean
import com.jio.jioinfra.utilities.MyConstants
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.approvals.fragment.ApprovalsDetailsFragment
import com.jio.siops_ngo.approvals.viewholder.ApprovalsChangeMgmntItemViewHolder
import com.jio.siops_ngo.approvals.viewholder.ApprovalsPendigItemViewHolder
import com.jio.siops_ngo.utilities.MyExceptionHandler


class ApprovalsChangeManagementAdapter(
    private val activity: MainActivity?,
    private val listData: ArrayList<Map<String, Any>>,
    private val onChangeMgmntOptionSelectListener: OnChangeMgmntOptionSelectedListener

) : RecyclerView.Adapter<ApprovalsChangeMgmntItemViewHolder>() {

    interface OnChangeMgmntOptionSelectedListener {
        fun onChangeMgmntOptionSelected(data: Any, selectedOption: String)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viwType: Int
    ): ApprovalsChangeMgmntItemViewHolder {
        var view: View?

        view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_approval_change_mgmnt, parent, false)

        return ApprovalsChangeMgmntItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ApprovalsChangeMgmntItemViewHolder, position: Int) {
        try {
            val content = listData[position]
            var key: String? = ""
            var name: String? = ""
            var count: String? = ""


            if (content.containsKey("message") && content["message"] != null) {
                holder.txtHeader!!.text = content["message"] as String
            }
            if(content.containsKey("details") && content["details"] != null){
                var changeMgmntDetails = content["details"] as ArrayList<Map<String, Any>>
                if(changeMgmntDetails[0].containsKey("title") && changeMgmntDetails[0]["title"] != null){

                    holder.txtTitle!!.text = changeMgmntDetails[0]["title"] as String

                }

                if(changeMgmntDetails[0].containsKey("description") && changeMgmntDetails[0]["description"] != null){

                    holder.txtDescription!!.text = changeMgmntDetails[0]["description"] as String

                }

                if(changeMgmntDetails[0].containsKey("requesterApplicationInfra") && changeMgmntDetails[0]["requesterApplicationInfra"] != null){

                    holder.txtAppInfra!!.text = changeMgmntDetails[0]["requesterApplicationInfra"] as String

                }

                if(changeMgmntDetails[0].containsKey("rilRequestorPlatform") && changeMgmntDetails[0]["rilRequestorPlatform"] != null){

                    holder.txtRequestedPlatform!!.text = changeMgmntDetails[0]["rilRequestorPlatform"] as String

                }

                if(changeMgmntDetails[0].containsKey("requestedStartTime") && changeMgmntDetails[0]["requestedStartTime"] != null){

                    holder.txtStartDate!!.text = changeMgmntDetails[0]["requestedStartTime"] as String

                }

                if(changeMgmntDetails[0].containsKey("requestedEndTime") && content["requestedEndTime"] != null){

                    holder.txtEndDate!!.text = content["requestedEndTime"] as String

                }

                if(changeMgmntDetails[0].containsKey("requestedDowntimeStart") && changeMgmntDetails[0]["requestedDowntimeStart"] != null){

                    holder.txtActualStartDownTime!!.text = changeMgmntDetails[0]["requestedDowntimeStart"] as String

                }

                if(changeMgmntDetails[0].containsKey("requestedDowntimeEnd") && changeMgmntDetails[0]["requestedDowntimeEnd"] != null){

                    holder.txtActualEndDownTime!!.text = changeMgmntDetails[0]["requestedDowntimeEnd"] as String

                }

                if(changeMgmntDetails[0].containsKey("rilRiskImpact") && changeMgmntDetails[0]["rilRiskImpact"] != null){

                    holder.txtRiskAnalysis!!.text = changeMgmntDetails[0]["rilRiskImpact"] as String

                }
                holder.cardApprove!!.setOnClickListener {

                    onChangeMgmntOptionSelectListener.onChangeMgmntOptionSelected(changeMgmntDetails[0], MyConstants.HPSM_APPROVED)
                }

                holder.cardReject!!.setOnClickListener {

                    onChangeMgmntOptionSelectListener.onChangeMgmntOptionSelected(changeMgmntDetails[0], MyConstants.HPSM_REJECTED)
                }
            }

        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}
