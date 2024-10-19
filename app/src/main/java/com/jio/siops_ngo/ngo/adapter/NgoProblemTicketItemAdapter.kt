package com.jio.siops_ngo.ngo.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.ngo.viewholder.NgoOpenActionItemHolder
import com.jio.siops_ngo.utilities.MyExceptionHandler


class NgoProblemTicketItemAdapter(private val activity: MainActivity?,private val appList: List<Map<String, Any>>, var selection:Int) : RecyclerView.Adapter<NgoOpenActionItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viwType: Int): NgoOpenActionItemHolder {
        var view: View?

        view = LayoutInflater.from(parent.context).inflate(R.layout.ngo_open_action_item, parent, false)

        return NgoOpenActionItemHolder(view)
    }



    override fun onBindViewHolder(holder: NgoOpenActionItemHolder, position: Int) {
        try {
            val content = appList[position]



            if(content.containsKey("id") && content["id"]!=null){
                holder.txtRefNo!!.text = content["id"] as String
                holder.txtRefNoTitle!!.text = "HPSM Ticket Number"
            }else{
                holder.txtRefNo!!.text = "NA"
                holder.txtRefNoTitle!!.text = "HPSM Ticket Number"
            }
            if(content.containsKey("open_TIME") && content["open_TIME"]!=null){
                holder.txtOpenAt!!.text = content["open_TIME"] as String
            }

            if(selection == 0){
                holder.txtStatus!!.text = "\n"+"Pending"
                holder.constraintLayoutClosedAt!!.visibility = View.GONE
            }else{
                holder.cnstrntLstatus!!.visibility = View.GONE

                if(content.containsKey("due_DATE") && content["due_DATE"]!=null){
                    holder.txtClosedAt!!.text = content["due_DATE"] as String
                }
                holder.txtClosedAtTitle!!.text = "Due Date"
            }


            if(content.containsKey("brief_DESCRIPTION") && content["brief_DESCRIPTION"]!=null){
                holder.txtActionDescr!!.text = content["brief_DESCRIPTION"] as String
                holder.txtActionDescrTitle!!.text = "Description"
                holder.txtShowMore!!.visibility = View.VISIBLE
            }else{
                holder.txtActionDescrTitle!!.text = "Description"
                holder.txtShowMore!!.visibility = View.GONE
            }
            if(content.containsKey("incidentid") && content["incidentid"]!=null){
                holder.txtAssignedBy!!.text = "\n"+content["incidentid"] as String
            }

            /*if(selection == 1){
                if(content.containsKey("incidentid") && content["incidentid"]!=null){
                    holder.txtAssignedBy!!.text = "\n"+content["incidentid"] as String
                }
            }else{
                if(content.containsKey("incidentid") && content["incidentid"]!=null){
                    holder.txtAssignedBy!!.text = content["incidentid"] as String
                }
            }*/
            holder.txtAssignedByTitle!!.text ="Incident Id"
            if(content.containsKey("assignment") && content["assignment"]!=null){
                holder.txtAgeing!!.text = content["assignment"].toString()
                holder.txtAgeingTitle!!.text = "Assignment Group"
            }
            holder.txtShowMore!!.setOnClickListener {

                if(holder.txtdescr!!.visibility == View.GONE){

                    if(content.containsKey("brief_DESCRIPTION") && content["brief_DESCRIPTION"]!=null){
                        holder.txtdescr!!.text = content["brief_DESCRIPTION"] as String
                    }
                    holder.txtdescr!!.visibility = View.VISIBLE
                    holder.txtActionDescr!!.visibility = View.INVISIBLE
                    holder.txtShowMore!!.text = activity!!.getString(R.string.viewdetails)
                }else{
                    holder.txtdescr!!.visibility = View.GONE
                    holder.txtActionDescr!!.visibility = View.VISIBLE
                    holder.txtShowMore!!.text = activity!!.getString(R.string.hide_details)
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
