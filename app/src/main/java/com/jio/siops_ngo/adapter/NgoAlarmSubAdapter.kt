package com.jio.siops_ngo.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.viewholder.OpneAlarmsDetailsViewHolder
import java.lang.Exception

class NgoAlarmSubAdapter(
    private val dataList: List<Map<String, Any>>,
    private val activity: MainActivity?
) : RecyclerView.Adapter<OpneAlarmsDetailsViewHolder>() {

    var boolean: Boolean = true

    override fun onCreateViewHolder(parent: ViewGroup, viwType: Int): OpneAlarmsDetailsViewHolder {
        var view: View?


        view = LayoutInflater.from(parent.context).inflate(R.layout.ngo_details_item, parent, false)

        return OpneAlarmsDetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: OpneAlarmsDetailsViewHolder, position: Int) {
        try {

            val content = dataList[position]

            if (content.containsKey("eventMessage")) {
                holder!!.view_errer_msg_id!!.text = content["eventMessage"] as String
            }
            if (content.containsKey("ack_BY")) {
                if (content.get("ack_BY") != null)
                    holder!!.txt_name!!.text = content["ack_BY"] as String
            }
            if (content.containsKey("cname")) {
                holder!!.txt_siteId!!.text =
                    content["cname"] as String + "\n" + "("+content["ipAddress"] as String+")"
            }
            if (content.containsKey("aging_since_insert")) {
                holder!!.txt_hrs!!.text = content["aging_since_insert"] as String
            }

            if (content.containsKey("createTime")) {
                holder!!.txt_opne_at!!.text = content["createTime"] as String
            }
            if (content.containsKey("appName")) {
                holder!!.title4!!.text = content["appName"] as String
            }

            if (content.containsKey("remaing_time_to_ack")) {
                if(content["remaing_time_to_ack"]!!.equals("Elapsed")){
                    holder!!.txttime!!.setTextColor(activity!!.resources.getColor(R.color.dead_dells_orange))
                }else{
                    holder!!.txttime!!.setTextColor(activity!!.resources.getColor(R.color.black))
                }
                holder!!.txttime!!.text = content["remaing_time_to_ack"] as String
            }

            if (content.containsKey("ack_status")) {
                var status: String = content["ack_status"] as String

                if (status.equals("0")) {
                    //   holder.txt_ack_status!!.setBackgroundDrawable(R.drawable.)




                    if(content.containsKey("ackByMobile") && null != content["ackByMobile"]){

                        holder.call_img_id!!.visibility = View.VISIBLE
                    }else{
                        holder.call_img_id!!.visibility = View.GONE
                    }

                    holder.txt_ack_status!!.setBackgroundResource(R.drawable.rounded_acknowladge_green);
                    holder.txt_ack_status!!.text = activity!!.resources.getString(R.string.acknowledge)
                    //  holder.txttime!!.visibility=View.VISIBLE
                    holder.acknowladgeheaderId!!.visibility = View.VISIBLE
                } else {
                    holder!!.txt_ack_at!!.text = "Not Ack"
                    holder.call_img_id!!.visibility = View.GONE
                    holder.txt_ack_status!!.setBackgroundResource(R.drawable.rounded_gray);
                    holder.txt_ack_status!!.text = activity!!.resources.getString(R.string.unacknowledge)
                    //  holder.txttime!!.visibility=View.INVISIBLE
                    holder.acknowladgeheaderId!!.visibility = View.INVISIBLE
                }


            }



            if (content.containsKey("ack_ON")) {
                if (content.get("ack_ON") != null)
                    holder!!.txt_ack_at!!.text = content["ack_ON"] as String
            }


            holder.call_img_id!!.setOnClickListener {
                val callIntent = Intent(Intent.ACTION_DIAL)
                callIntent.data = Uri.parse("tel:" + content["ackByMobile"] as String)
                activity!!.startActivity(callIntent)

            }
            holder.view_history_id!!.setOnClickListener {


                holder.view_hide_id!!.visibility = View.VISIBLE
                holder.view_history_id!!.visibility = View.GONE
                holder.view_errer_msg_id!!.visibility = View.VISIBLE

            }


            holder.view_hide_id!!.setOnClickListener {

                holder.view_hide_id!!.visibility = View.GONE
                holder.view_history_id!!.visibility = View.VISIBLE
                holder.view_errer_msg_id!!.visibility = View.GONE


            }
            //   val content = appList[position]
            //   var title = content["applicationName"] as String

            //   holder.txtTitle!!.text = title!!
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


}