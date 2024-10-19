package com.jio.siops_ngo.ngo.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.utilities.MyConstants
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.approvals.viewholder.ApprovalsProblemMgmntItemViewHolder
import com.jio.siops_ngo.utilities.MyExceptionHandler


class ApprovalsProblemManagementAdapter(
    private val activity: MainActivity?,
    private val listData: ArrayList<Map<String, Any>>,
    private val onProblemMgmntSelectListener: OnProblemMgmntOptionSelectedListener

) : RecyclerView.Adapter<ApprovalsProblemMgmntItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viwType: Int
    ): ApprovalsProblemMgmntItemViewHolder {
        var view: View?

        view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_approval_problem_mgmnt, parent, false)

        return ApprovalsProblemMgmntItemViewHolder(view)
    }

    interface OnProblemMgmntOptionSelectedListener {
        fun onProblemManagementOptionSelected(data: Any, selectedOption: String)
    }

    override fun onBindViewHolder(holder: ApprovalsProblemMgmntItemViewHolder, position: Int) {
        try {
            val content = listData[position]
            var key: String? = ""
            var name: String? = ""
            var count: String? = ""


            if(content.containsKey("details") && content["details"] != null){
                var changeMgmntDetails = content["details"] as ArrayList<Map<String, Any>>
                if(changeMgmntDetails[0].containsKey("problemId") && changeMgmntDetails[0]["problemId"] != null){

                    holder.txtProblemId!!.text = changeMgmntDetails[0]["problemId"] as String

                }

                if(changeMgmntDetails[0].containsKey("problemDescription") && changeMgmntDetails[0]["problemDescription"] != null){

                    holder.txtProblemDescription!!.text = changeMgmntDetails[0]["problemDescription"] as String

                }

                if(changeMgmntDetails[0].containsKey("preventiveMeasures") && changeMgmntDetails[0]["preventiveMeasures"] != null){

                    holder.txtPreventiveMeasure!!.text = changeMgmntDetails[0]["preventiveMeasures"] as String

                }

                if(changeMgmntDetails[0].containsKey("rcaDomain") && changeMgmntDetails[0]["rcaDomain"] != null){

                    holder.txtRcaDomain!!.text = changeMgmntDetails[0]["rcaDomain"] as String

                }

                if(changeMgmntDetails[0].containsKey("platformRcaApprover") && changeMgmntDetails[0]["platformRcaApprover"] != null){

                    holder.txtRcaApprover!!.text = changeMgmntDetails[0]["platformRcaApprover"] as String

                }

                if(changeMgmntDetails[0].containsKey("rcaReport") && changeMgmntDetails[0]["rcaReport"] != null){

                    holder.txtRcaReport!!.text = changeMgmntDetails[0]["rcaReport"] as String

                }
                holder.cardApprove!!.setOnClickListener {

                    onProblemMgmntSelectListener.onProblemManagementOptionSelected(changeMgmntDetails[0], MyConstants.HPSM_APPROVED)
                }

                holder.cardReject!!.setOnClickListener {

                    onProblemMgmntSelectListener.onProblemManagementOptionSelected(changeMgmntDetails[0], MyConstants.HPSM_REJECTED)
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
