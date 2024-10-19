package com.jio.siops_ngo.infra.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.bean.CommonBean
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.businessBoard.fragment.RechargePendingFragment
import com.jio.siops_ngo.infra.viewholder.RechargeJouneryViewholder
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.Utils


class RechargeJourneryAdapter(
    private val activity: MainActivity?,
    private val rechargeJourneryList: ArrayList<Map<String, Any>>,
    private  val rechargePandingList: ArrayList<Map<String, Any>>
) : RecyclerView.Adapter<RechargeJouneryViewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viwType: Int): RechargeJouneryViewholder {
        var view: View?

        view = LayoutInflater.from(parent.context).inflate(R.layout.recharge_item, parent, false)

        return RechargeJouneryViewholder(view)
    }



    override fun onBindViewHolder(holder: RechargeJouneryViewholder, position: Int) {
        try {
              val content = rechargeJourneryList[position]

            if(content.containsKey("channel") && content["channel"]!=null){
                holder.txt_chanals_title!!.text= content["channel"].toString()
            }
            if(content.containsKey("total") && content["total"]!=null){
//                holder.channal_count1!!.text=content["total"].toString()
                holder.channal_count1!!.text= Utils.getCommaSeparatedCount(content["total"] as Int)
            }
            if(content.containsKey("paymentAborted")&& content["paymentAborted"]!=null){
//                holder.channal_count2!!.text=content["paymentAborted"].toString()
                holder.channal_count2!!.text= Utils.getCommaSeparatedCount(content["paymentAborted"] as Int)
            }
            if(content.containsKey("paymentsuccess")&& content["paymentsuccess"]!=null){
//                holder.channal_count3!!.text=content["paymentsuccess"].toString()
                holder.channal_count3!!.text= Utils.getCommaSeparatedCount(content["paymentsuccess"] as Int)
            }
            if(content.containsKey("rechargeSuccess")&& content["rechargeSuccess"]!=null){
//                holder.channal_count4!!.text=content["rechargeSuccess"].toString()
                holder.channal_count4!!.text= Utils.getCommaSeparatedCount(content["rechargeSuccess"] as Int)
            }
            if(content.containsKey("pending")&& content["pending"]!=null){
//                holder.channal_count5!!.text=content["pending"].toString()
                holder.channal_count5!!.text= Utils.getCommaSeparatedCount(content["pending"] as Int)
            }
            if(content.containsKey("refund")&& content["refund"]!=null){
//                holder.channal_count6!!.text=content["refund"].toString()
                holder.channal_count6!!.text= Utils.getCommaSeparatedCount(content["refund"] as Int)
            }


            holder.card5!!.setOnClickListener {



                if(content["pending"].toString().equals("0")) {

                }else{
                    val commonBean = CommonBean()

                    var rechargePendingFragment = RechargePendingFragment.newInstance()
                    rechargePendingFragment.setData(rechargePandingList)
                    (activity as MainActivity).openFragments(
                        rechargePendingFragment,
                        commonBean,
                        true,
                        true
                    )
                }
            }


            holder.txt_more_details!!.setOnClickListener {
                holder.txt_more_details!!.visibility=View.GONE
                holder.txt_hide_wo!!.visibility=View.VISIBLE
                holder.constantIdCl!!.visibility=View.VISIBLE
            }
            holder.txt_hide_wo!!.setOnClickListener {
                holder.txt_more_details!!.visibility=View.VISIBLE
                holder.txt_hide_wo!!.visibility=View.GONE
                holder.constantIdCl!!.visibility=View.GONE

            }


//             holder.header1!!.text=content["channel"].toString()
//             holder.header2!!.text=content["total"].toString()
//             holder.header3!!.text=content["paymentAborted"].toString()
//             holder.header4!!.text=content["paymentsuccess"].toString()
//             holder.header5!!.text=content["rechargeSuccess"].toString()
//             holder.header6!!.text=content["pending"].toString()
//             holder.header7!!.text=content["refund"].toString()






//
        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
        }
    }

    override fun getItemCount(): Int {
        return rechargeJourneryList.size
    }
}