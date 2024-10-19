package com.jio.siops_ngo.ngo.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.ngo.viewholder.NgoOpenActionItemHolder
import com.jio.siops_ngo.utilities.MyExceptionHandler


class NgoOpenActionItemAdapter(private val activity: MainActivity?,private val appList: List<Map<String, Any>>, var selection:Int) : RecyclerView.Adapter<NgoOpenActionItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viwType: Int): NgoOpenActionItemHolder {
        var view: View?

        view = LayoutInflater.from(parent.context).inflate(R.layout.ngo_open_action_item, parent, false)

        return NgoOpenActionItemHolder(view)
    }



    override fun onBindViewHolder(holder: NgoOpenActionItemHolder, position: Int) {
        try {
            val content = appList[position]



            if(content.containsKey("meetingid") && content["meetingid"]!=null){
                holder.txtRefNo!!.text = content["meetingid"] as String
            }
//            if(selection == 1) {
                if (content.containsKey("createdon") && content["createdon"] != null) {
                    holder.txtOpenAt!!.text = "\n" + content["createdon"] as String
                }
                if(content.containsKey("aging") && content["aging"]!=null){
                    holder.txtAgeing!!.text = "\n"+content["aging"].toString()
                    /*holder.txtAgeingTitle!!.text = "Ageing (days)"*/
                }
            holder.txtAgeingTitle!!.text = "Ageing (days)"
            /*}else{
                if (content.containsKey("createdon") && content["createdon"] != null) {
                    holder.txtOpenAt!!.text = content["createdon"] as String
                }
                if(content.containsKey("aging") && content["aging"]!=null){
                    holder.txtAgeing!!.text = content["aging"].toString()
                    holder.txtAgeingTitle!!.text = "Ageing (days)"
                }

            }*/

            if(selection == 0){
                holder.txtStatus!!.text = "Pending"
//                holder.constraintLayoutClosedAt!!.visibility = View.GONE
                holder.txtClosedAtTitle!!.text = "Due Date"
            }else{
                if(content.containsKey("cal_STATUS") && content["cal_STATUS"]!=null){
                    holder.txtStatus!!.text = content["cal_STATUS"] as String
                }
                holder.txtClosedAtTitle!!.text = "Revised Date"
                /*holder.constraintLayoutClosedAt!!.visibility = View.VISIBLE
                if(content.containsKey("targetdate") && content["targetdate"]!=null){
                    holder.txtClosedAt!!.text = "\n"+content["targetdate"] as String
                }
                holder.txtClosedAtTitle!!.text = "Revised Date"*/
            }

            holder.constraintLayoutClosedAt!!.visibility = View.VISIBLE
            if(content.containsKey("targetdate") && content["targetdate"]!=null){
                holder.txtClosedAt!!.text = "\n"+content["targetdate"] as String
            }



            if(content.containsKey("action") && content["action"]!=null){
                holder.txtActionDescr!!.text = content["action"] as String
            }

            if(content.containsKey("assignee") && content["assignee"]!=null){
                holder.txtAssignedBy!!.text = content["assignee"] as String

            }
            holder.txtAssignedByTitle!!.text = "Assigned To"

//            holder.txtdescr!!.text = content["action"] as String

            holder.txtShowMore!!.setOnClickListener {

                if(holder.txtdescr!!.visibility == View.GONE){

                    if(content.containsKey("action") && content["action"]!=null){
                        holder.txtdescr!!.text = content["action"] as String
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
