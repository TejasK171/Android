package com.jio.siops.ngo.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.approvals.viewholder.TimeTrackingExViewSImilarViewHolder
import com.jio.siops_ngo.utilities.MyExceptionHandler


class TimeTrackingViewSimilarAdapter(
    private val activity: MainActivity?,
    private val listData: ArrayList<Map<String, Any>>

) : RecyclerView.Adapter<TimeTrackingExViewSImilarViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viwType: Int
    ): TimeTrackingExViewSImilarViewHolder {
        var view: View?

        view = LayoutInflater.from(parent.context)
            .inflate(R.layout.time_tracking_exclusion_item, parent, false)

        return TimeTrackingExViewSImilarViewHolder(view)
    }

    override fun onBindViewHolder(holder: TimeTrackingExViewSImilarViewHolder, position: Int) {
        try {
            val content = listData[position]

            if (content.containsKey("UserName") && content["UserName"] != null) {
                holder.txtResourceName!!.text = content["UserName"] as String
            }
            if (content.containsKey("Excluded") && content["Excluded"] != null) {
                holder.txtExcluded!!.text = content["Excluded"].toString()
            }


        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}
