package com.jio.siops_ngo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.bean.CommonBean
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.fragment.NgoEmpDeliquencyFragment
import com.jio.siops_ngo.viewholder.OpenAlarmsViewHolder
import java.lang.Exception

class DelinquentSubAdapter(private val dataList: List<Map<String, Any>>, private val activity: MainActivity?, val domainName: String) : RecyclerView.Adapter<OpenAlarmsViewHolder>() {

    var dataListNgoItem: ArrayList<Map<String, Any>>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viwType: Int): OpenAlarmsViewHolder {
        var view: View?


        view = LayoutInflater.from(parent.context).inflate(R.layout.ngo_deliquency_item, parent, false)

        return OpenAlarmsViewHolder(view)
    }

    override fun onBindViewHolder(holder: OpenAlarmsViewHolder, position: Int) {

        val content = dataList[position]

        if(content.containsKey("platformName")){
            holder!!.txtTitle!!.text= content["platformName"] as String
        }

        if(content.containsKey("count")){
            holder!!.txtCount!!.text= content["count"].toString()
        }


        try {


            /*holder.up_down!!.setOnClickListener {

                //  var appType :String = content["featureName"] as String

                //  fetchDashboardDbData(Busicode.NGOOnclickType,appType,holder)


                holder.cnstrntL_background!!.setBackgroundResource(R.drawable.white_rounded_corner_bg)
                holder.txtTitle!!.setTextColor(activity!!.getResources().getColor(R.color.black))
                holder.txtCount!!.setTextColor(activity!!.getResources().getColor(R.color.blue_text))

                // holder!!.imgDropDown!!.setBackgroundResource(R.drawable.ic_up)
//                holder.detailsList!!.visibility = View.GONE
                holder!!.imgDropDown!!!!.visibility = View.VISIBLE
                holder!!.up_down!!!!.visibility = View.GONE
                //  boolean=true

            }*/

            holder.imgDropDown!!.setOnClickListener {

                val commonBean = CommonBean()
                var ngoEmpDeliquencyFragment = NgoEmpDeliquencyFragment.newInstance()
                commonBean.`object` = content
                ngoEmpDeliquencyFragment.setData(commonBean!!, domainName)
                (activity as MainActivity).openFragments(ngoEmpDeliquencyFragment, commonBean, true, true)


            }


        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }





}