package com.jio.siops_ngo.infra.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.infra.viewholder.PandingRechargeJouneryViewholder
import com.jio.siops_ngo.utilities.MyExceptionHandler


class PandingRechargeJourneryAdapter(
    private val activity: MainActivity?,
    private  val pendingrechargeJourneryList: ArrayList<Map<String, Any>>
) : RecyclerView.Adapter<PandingRechargeJouneryViewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viwType: Int): PandingRechargeJouneryViewholder {
        var view: View?

        view = LayoutInflater.from(parent.context).inflate(R.layout.recharge_pending_item, parent, false)

        return PandingRechargeJouneryViewholder(view)
    }

    override fun onBindViewHolder(holder: PandingRechargeJouneryViewholder, position: Int) {
        try {
            val content = pendingrechargeJourneryList[position]

            if (content.containsKey("errormessage")) {

                holder.header2!!.text = content["errormessage"].toString()
            }

            if (content.containsKey("rposgt")) {
                holder.header4!!.text = content["rposgt"].toString()
            }

            if (content.containsKey("rposrr")) {
                holder.header6!!.text = content["rposrr"].toString()
            }

            if (content.containsKey("jiocom")) {
                holder.header8!!.text = content["jiocom"].toString()
            }

            if (content.containsKey("myjio")) {
                holder.header10!!.text = content["myjio"].toString()
            }
            if (content.containsKey("tpa")) {

                holder.header12!!.text = content["tpa"].toString()
            }

            if (content.containsKey("jiomoney")) {
                holder.header14!!.text = content["jiomoney"].toString()
            }
            if (content.containsKey("jiophone")){
                holder.header16!!.text = content["jiophone"].toString()
        }

            if(content.containsKey("enterprise")) {
                holder.header18!!.text = content["enterprise"].toString()
            }

            if(content.containsKey("jioposlite")) {
                holder.header20!!.text = content["jioposlite"].toString()
            }
            if(content.containsKey("loadmoney")) {
                holder.header22!!.text = content["loadmoney"].toString()
            }






//
        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
        }
    }

    override fun getItemCount(): Int {
        return pendingrechargeJourneryList.size
    }
}