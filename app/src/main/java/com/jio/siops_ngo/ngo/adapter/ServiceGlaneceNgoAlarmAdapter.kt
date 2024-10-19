package com.jio.siops_ngo.ngo.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.ngo.viewholder.NgoOpneHistoryViewHolder
import java.lang.Exception

class ServiceGlaneceNgoAlarmAdapter(
    private val dataList: List<Map<String, Any>>,
    private val activity: MainActivity?
) : RecyclerView.Adapter<NgoOpneHistoryViewHolder>() {

    var boolean: Boolean = true
    var map:HashMap<Int,Int>? = hashMapOf()

    override fun onCreateViewHolder(parent: ViewGroup, viwType: Int): NgoOpneHistoryViewHolder {
        var view: View?


        view = LayoutInflater.from(parent.context).inflate(R.layout.ngodetails_item, parent, false)

        return NgoOpneHistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: NgoOpneHistoryViewHolder, position: Int) {
        try {

            val content = dataList[position]

            if (content.containsKey("eventMessage") && content["eventMessage"]!=null) {
                holder!!.view_errer_msg_id!!.text = content["eventMessage"] as String
            }
            if (content.containsKey("ack_BY")&& content["ack_BY"]!=null) {
                if (content.get("ack_BY") != null)
                    holder!!.txt_name!!.text = content["ack_BY"] as String
            }
            if (content.containsKey("cname")&& content["cname"]!=null) {
                holder!!.txt_siteId!!.text =
                    content["cname"] as String + "\n" + "("+content["ipAddress"] as String+")"
            }
            if (content.containsKey("aging_since_insert")&& content["aging_since_insert"]!=null) {
                holder!!.txt_hrs!!.text = content["aging_since_insert"] as String
            }
            if (content.containsKey("createTime")&& content["createTime"]!=null) {
                holder!!.txt_opne_at!!.text = content["createTime"] as String
            }
            if (content.containsKey("appName")&& content["appName"]!=null) {
                holder!!.txt_app_i_ops!!.text = content["appName"] as String
            }
            if (content.containsKey("remaing_time_to_ack")&& content["remaing_time_to_ack"]!=null) {
                if(content["remaing_time_to_ack"]!!.equals("Elapsed")){
                    holder!!.txttime!!.setTextColor(activity!!.resources.getColor(R.color.dead_dells_orange))
                }else{
                    holder!!.txttime!!.setTextColor(activity!!.resources.getColor(R.color.black))
                }
                holder!!.txttime!!.text = content["remaing_time_to_ack"] as String
            }
            if (content.containsKey("ack_status")&& content["ack_status"]!=null) {
                var status: String = content["ack_status"] as String
                if (status.equals("0")) {
                    if(content.containsKey("ackByMobile") && null != content["ackByMobile"]){
                        holder.call_img_id!!.visibility = View.VISIBLE
                    }else{
                        holder.call_img_id!!.visibility = View.GONE
                    }
                    holder.txt_ack_status!!.setBackgroundResource(R.drawable.rounded_acknowladge_green);
                    holder.txt_ack_status!!.text = activity!!.resources.getString(R.string.acknowledge)
                    holder.acknowladgeheaderId!!.visibility = View.VISIBLE
                } else {
                    holder!!.txt_ack_at!!.text = "Not Ack"
                    holder.call_img_id!!.visibility = View.GONE
                    holder.txt_ack_status!!.setBackgroundResource(R.drawable.rounded_gray);
                    holder.txt_ack_status!!.text = activity!!.resources.getString(R.string.unacknowledge)
                    holder.txt_name!!.visibility = View.INVISIBLE
                    holder.acknowladgeheaderId!!.visibility = View.INVISIBLE
                }
            }
            if (content.containsKey("ack_ON")&& content["ack_ON"]!=null) {
                if (content.get("ack_ON") != null)
                    holder!!.txt_ack_at!!.text = content["ack_ON"] as String
            }
            holder.call_img_id!!.setOnClickListener {
                val callIntent = Intent(Intent.ACTION_DIAL)
                callIntent.data = Uri.parse("tel:" + content["ackByMobile"] as String)
                activity!!.startActivity(callIntent)

            }
            if(map!!.containsKey(position)){
                holder.view_history_id!!.text = activity!!.resources.getString(R.string.hidedetails)
                holder.view_errer_msg_id!!.visibility = View.VISIBLE
            }else{
                holder.view_history_id!!.text = activity!!.resources.getString(R.string.viewdetails)
                holder.view_errer_msg_id!!.visibility = View.GONE

            }
          ///  holder.view_hide_id!!.visibility = View.GONE
            holder.view_history_id!!.setOnClickListener {

                if(holder.view_errer_msg_id!!.visibility == View.VISIBLE){
                    if(map!!.containsKey(position)){
                        map!!.remove(position)
                    }
                    holder.view_history_id!!.text = activity!!.resources.getString(R.string.viewdetails)
                    holder.view_errer_msg_id!!.visibility = View.GONE

                }else{
                    map!!.put(position,position)
                    holder.view_history_id!!.text = activity!!.resources.getString(R.string.hidedetails)
                    holder.view_errer_msg_id!!.visibility = View.VISIBLE
                    /*holder.view_hide_id!!.visibility = View.VISIBLE
                    holder.view_history_id!!.visibility = View.GONE
                    holder.view_errer_msg_id!!.visibility = View.VISIBLE*/
                }


            }


            /*holder.view_hide_id!!.setOnClickListener {

                holder.view_hide_id!!.visibility = View.GONE
                holder.view_history_id!!.visibility = View.VISIBLE
                holder.view_errer_msg_id!!.visibility = View.GONE


            }*/
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