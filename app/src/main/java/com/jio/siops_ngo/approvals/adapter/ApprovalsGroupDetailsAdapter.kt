package com.jio.siops_ngo.ngo.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jio.siops_ngo.R
import com.jio.siops_ngo.approvals.viewholder.ApprovalsGroupItemViewHolder
import com.jio.siops_ngo.utilities.MyExceptionHandler


class ApprovalsGroupDetailsAdapter(
    private val listData: ArrayList<Map<String, Any>>

) : RecyclerView.Adapter<ApprovalsGroupItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viwType: Int
    ): ApprovalsGroupItemViewHolder {
        var view: View?

        view = LayoutInflater.from(parent.context)
            .inflate(R.layout.group_items, parent, false)

        return ApprovalsGroupItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ApprovalsGroupItemViewHolder, position: Int) {
        try {
           val content = listData[position]

            if (content.containsKey("Name") && content["Name"] != null) {
                holder.txtGroupTitle!!.text = content["Name"] as String
            }
            if (content.containsKey("Value") && content["Value"] != null) {
                holder.txtGroupValue!!.text = content["Value"].toString()
            }


        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}
