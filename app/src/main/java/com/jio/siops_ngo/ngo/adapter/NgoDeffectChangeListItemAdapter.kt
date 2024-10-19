package com.jio.siops_ngo.ngo.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.ngo.viewholder.NgoOpenIncidentItemHolder
import com.jio.siops_ngo.utilities.MyExceptionHandler


class NgoDeffectChangeListItemAdapter(private val activity: MainActivity?,private val appList: List<Map<String, Any>>) : RecyclerView.Adapter<NgoOpenIncidentItemHolder>() {
    var map:HashMap<Int,Int>? = hashMapOf()
    override fun onCreateViewHolder(parent: ViewGroup, viwType: Int): NgoOpenIncidentItemHolder {
        var view: View?

        view = LayoutInflater.from(parent.context).inflate(R.layout.ngo_deffect_item, parent, false)

        return NgoOpenIncidentItemHolder(view)
    }



    override fun onBindViewHolder(holder: NgoOpenIncidentItemHolder, position: Int) {
        try {
            val content = appList[position]



            if(content.containsKey("defectid") && content["defectid"]!=null){
                holder.txtTicketNo!!.text = content["defectid"] as String
            }


            if(content.containsKey("summary") && content["summary"]!=null){
                holder.view_summary_id!!.text = content["summary"] as String
            }

            if(content.containsKey("creation_DATE") && content["creation_DATE"]!=null){

                var dateS=content["creation_DATE"] as String
                holder.txtOpenAt!!.text = dateS!!.replace(" ","\n")
            }
            if(content.containsKey("due_DATE") && content["due_DATE"]!=null){
                holder.txtClosedAt!!.text = content["due_DATE"] as String
            }else{
                holder.txtClosedAt!!.text = "NA"
            }
            if(content.containsKey("status") && content["status"]!=null){
                holder.txtStatus!!.text = content["status"] as String
            }
            if(content.containsKey("applicationimpacted") && content["applicationimpacted"]!=null){
                holder.txtImpact!!.text = content["applicationimpacted"] as String
            }
            if(content.containsKey("request_CATEGORY_NAME") && content["request_CATEGORY_NAME"]!=null){
                holder.txtApplication!!.text = content["request_CATEGORY_NAME"] as String
            }
            if(content.containsKey("createdby") && content["createdby"]!=null){
                holder.txtResolverGroup!!.text = content["createdby"] as String
            }
            if(content.containsKey("defectseverity") && content["defectseverity"]!=null){
                holder.txtSeverity!!.text = content["defectseverity"] as String
            }
//            holder.txtSeverity!!.visibility=View.INVISIBLE
            if(content.containsKey("bucket") && content["bucket"]!=null){
                holder.txtAgeing!!.text = content["bucket"].toString()
            }

            if(map!!.containsKey(position)){
                holder.view_hide_id!!.text = activity!!.resources.getString(R.string.hidedetails)
                holder.view_summary_id!!.visibility = View.VISIBLE
            }else{
                holder.view_hide_id!!.text = activity!!.resources.getString(R.string.viewdetails)
                holder.view_summary_id!!.visibility = View.GONE
            }

            holder.view_hide_id!!.setOnClickListener {

                if(holder.view_summary_id!!.visibility== View.VISIBLE){
                    if(map!!.containsKey(position)){
                        map!!.remove(position)
                    }
                    holder.view_hide_id!!.text = activity!!.getString(R.string.viewdetails)
                    holder.view_summary_id!!.visibility=View.GONE

                }else{
                    map!!.put(position,position)
                    holder.view_hide_id!!.text =  activity!!.getString(R.string.hide_details)
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
