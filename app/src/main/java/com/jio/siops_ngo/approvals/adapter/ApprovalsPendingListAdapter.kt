package com.jio.siops_ngo.ngo.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.bean.CommonBean
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.approvals.fragment.ApprovalsChangeOrProblemManagementFragment
import com.jio.siops_ngo.approvals.fragment.ApprovalsDetailsFragment
import com.jio.siops_ngo.approvals.viewholder.ApprovalsPendigItemViewHolder
import com.jio.siops_ngo.utilities.MyExceptionHandler


class ApprovalsPendingListAdapter(
    private val activity: MainActivity?,
    private val listData: ArrayList<Map<String, Any>>

) : RecyclerView.Adapter<ApprovalsPendigItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viwType: Int
    ): ApprovalsPendigItemViewHolder {
        var view: View?

        view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pending_approvals_item, parent, false)

        return ApprovalsPendigItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ApprovalsPendigItemViewHolder, position: Int) {
        try {
            val content = listData[position]
            var key: String? = ""
            var name: String? = ""
            var count: String? = ""

            if (content.containsKey("Name") && content["Name"] != null) {
                holder.txt_pending_title!!.text = content["Name"] as String
                name = content["Name"] as String
            }
            if (content.containsKey("Count") && content["Count"] != null) {
                holder.txt_count_value!!.text = content["Count"].toString()
                count = content["Count"].toString()
            }
            if (content.containsKey("Key") && content["Key"] != null) {
                key = content["Key"].toString()
            }

            holder.cnstrntL!!.setOnClickListener {

                if (count != "0") {
                    if (key!!.equals("Change") || key.equals("Problem")) {
                        var approvalsChangeOrProblemManagementFragment =
                            ApprovalsChangeOrProblemManagementFragment.newInstance()
                        val commonBean = CommonBean()
                        if (key!!.equals("Change")) {
                            approvalsChangeOrProblemManagementFragment.setData(true)
                        } else {
                            approvalsChangeOrProblemManagementFragment.setData(false)
                        }
                        (activity)!!.openFragments(
                            approvalsChangeOrProblemManagementFragment,
                            commonBean,
                            true,
                            true
                        )
                    }

                    else {
                        var approvalsDetailsFragment = ApprovalsDetailsFragment.newInstance()
                        val commonBean = CommonBean()
                        approvalsDetailsFragment.setData(key!!, count!!, name!!)
                        (activity)!!.openFragments(
                            approvalsDetailsFragment,
                            commonBean,
                            true,
                            true
                        )

                    }

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
