package com.jio.siops_ngo.ngo.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jio.siops_ngo.R
import com.jio.siops_ngo.ngo.viewholder.NgoOpenIncidentItemHolder
import com.jio.siops_ngo.utilities.MyExceptionHandler


class NgoOpenIncidentItemAdapter(private val appList: List<Map<String, Any>>) : RecyclerView.Adapter<NgoOpenIncidentItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viwType: Int): NgoOpenIncidentItemHolder {
        var view: View?

        view = LayoutInflater.from(parent.context).inflate(R.layout.ngo_open_incident_item, parent, false)

        return NgoOpenIncidentItemHolder(view)
    }



    override fun onBindViewHolder(holder: NgoOpenIncidentItemHolder, position: Int) {
        try {
            val content = appList[position]

            if(content.containsKey("incidentno") && content["incidentno"]!=null){
                holder.txtTicketNo!!.text = content["incidentno"] as String
            }
            if(content.containsKey("open_TIME") && content["open_TIME"]!=null){
                holder.txtOpenAt!!.text = content["open_TIME"] as String
            }
            if(content.containsKey("resolved_TIME") && content["resolved_TIME"]!=null){
                holder.txtClosedAt!!.text = content["resolved_TIME"] as String
            }
            if(content.containsKey("problem_STATUS") && content["problem_STATUS"]!=null){
                holder.txtStatus!!.text = content["problem_STATUS"] as String
            }
            if(content.containsKey("business_IMPACT") && content["business_IMPACT"]!=null){
                holder.txtImpact!!.text = content["business_IMPACT"] as String
            }
            if(content.containsKey("impacted_APPLICATIONS") && content["impacted_APPLICATIONS"]!=null){
                holder.txtApplication!!.text = content["impacted_APPLICATIONS"] as String
            }
            if(content.containsKey("assignment_GROUP") && content["assignment_GROUP"]!=null){
                holder.txtResolverGroup!!.text = content["assignment_GROUP"] as String
            }
            if(content.containsKey("severity") && content["severity"]!=null){
                holder.txtSeverity!!.text = content["severity"] as String
            }
            if(content.containsKey("ril_TTR") && content["ril_TTR"]!=null){
                holder.txtAgeing!!.text = content["ril_TTR"].toString()
            }
        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
        }
    }

    override fun getItemCount(): Int {
        return appList.size
    }
}
