package com.jio.siops_ngo.ngo.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.ngo.viewholder.NgoOpenIncidentItemHolder
import com.jio.siops_ngo.utilities.MyExceptionHandler


class NgoOpenChangeItemAdapter(private val activity: MainActivity?,private val appList: List<Map<String, Any>>) : RecyclerView.Adapter<NgoOpenIncidentItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viwType: Int): NgoOpenIncidentItemHolder {
        var view: View?

        view = LayoutInflater.from(parent.context).inflate(R.layout.ngo_open_change_item, parent, false)

        return NgoOpenIncidentItemHolder(view)
    }



    override fun onBindViewHolder(holder: NgoOpenIncidentItemHolder, position: Int) {
        try {
            val content = appList[position]



            if(content.containsKey("changeid") && content["changeid"]!=null){
                holder.txtTicketNo!!.text = content["changeid"] as String
            }
            if(content.containsKey("actual_STARTDATE") && content["actual_STARTDATE"]!=null){
                holder.txtOpenAt!!.text = content["actual_STARTDATE"] as String
            }
            if(content.containsKey("actual_ENDDATE") && content["actual_ENDDATE"]!=null){
                holder.txtClosedAt!!.text = content["actual_ENDDATE"] as String
            }
            if(content.containsKey("crstatus") && content["crstatus"]!=null){
                holder.txtStatus!!.text = content["crstatus"] as String
            }
            if(content.containsKey("applicationimpacted") && content["applicationimpacted"]!=null){
                holder.txtApplication!!.text = content["applicationimpacted"] as String
            }
            if(content.containsKey("requestedapplication") && content["requestedapplication"]!=null){
                holder.txtSeverity!!.text = content["requestedapplication"] as String
            }

            if(content.containsKey("crtitle") && content["crtitle"]!=null){
                holder.view_summary_id!!.text = content["crtitle"] as String
            }
            if(content.containsKey("changeexecutorgroup") && content["changeexecutorgroup"]!=null){
                holder.txtResolverGroup!!.text = content["changeexecutorgroup"] as String
            }

           // holder.txtSeverity!!.visibility=View.INVISIBLE
            if(content.containsKey("ril_TTR") && content["ril_TTR"]!=null){
                holder.txtAgeing!!.text = content["ril_TTR"].toString()
            }


            holder.view_hide_id!!.setOnClickListener {

                if(holder.view_summary_id!!.visibility== View.VISIBLE){
                    holder.view_hide_id!!.text = activity!!.getString(R.string.viewdetails)
                    holder.view_summary_id!!.visibility=View.GONE

                }else{
                    holder.view_hide_id!!.text = activity!!.getString(R.string.hide_details)
                    holder.view_summary_id!!.visibility=View.VISIBLE

                }
            }


        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
        }
    }

    override fun getItemCount(): Int {
        return appList.size
    }
}
