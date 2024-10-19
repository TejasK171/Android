package com.jio.siops_ngo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.viewholder.InfraAlarmsReasonViewHolder
import java.lang.Exception

class AlarmSubAdapter(private val activity: MainActivity?) : RecyclerView.Adapter<InfraAlarmsReasonViewHolder>() {

    var boolean:Boolean=true

    override fun onCreateViewHolder(parent: ViewGroup, viwType: Int): InfraAlarmsReasonViewHolder {
        var view: View?


        view = LayoutInflater.from(parent.context).inflate(R.layout.reasons_details_item, parent, false)

        return InfraAlarmsReasonViewHolder(view)
    }

    override fun onBindViewHolder(holder: InfraAlarmsReasonViewHolder, position: Int) {
        try {


            holder.txtHistory!!.setOnClickListener {


                holder.historyList!!.visibility = View.VISIBLE
             //   holder.constantIdHeader!!.visibility = View.VISIBLE
                holder.txt_last_days_history!!.visibility = View.VISIBLE
                holder.view_history_id!!.visibility = View.VISIBLE
                holder.txtHistory!!.visibility = View.GONE
                holder.ll!!.visibility = View.VISIBLE

                var adapter = AlarmsInnerHistoryAdapter(activity as MainActivity?)
                holder.historyList!!.layoutManager = LinearLayoutManager(activity)
                holder.historyList!!.itemAnimator = DefaultItemAnimator()
                holder.historyList!!.adapter = adapter
                adapter.notifyDataSetChanged()
            }


            holder.view_history_id!!.setOnClickListener {


                holder.historyList!!.visibility = View.GONE
             //   holder.constantIdHeader!!.visibility = View.GONE
                holder.txt_last_days_history!!.visibility = View.GONE
                holder.view_history_id!!.visibility = View.GONE
                holder.txtHistory!!.visibility = View.VISIBLE
                holder.ll!!.visibility = View.GONE

//                var adapter = AlarmsInnerHistoryAdapter(activity as MainActivity?)
//                holder.historyList!!.layoutManager = LinearLayoutManager(activity)
//                holder.historyList!!.itemAnimator = DefaultItemAnimator()
//                holder.historyList!!.adapter = adapter
//                adapter.notifyDataSetChanged()



        }
            //   val content = appList[position]
            //   var title = content["applicationName"] as String

            //   holder.txtTitle!!.text = title!!
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return 5
    }


}