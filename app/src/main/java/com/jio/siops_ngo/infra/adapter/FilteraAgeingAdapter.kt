package com.jio.siops_ngo.infra.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.infra.viewholder.InfraFilterViewHolder
import java.lang.Exception

class FilteraAgingAdapter(private  val pos:String,private  val mOptionInterfaceFilterName:AgingInterfaceFilterName,private  val list: ArrayList<Map<String, Any>>?, private val activity: MainActivity?) : RecyclerView.Adapter<InfraFilterViewHolder>() {

    private var selected: RadioButton? = null



    interface AgingInterfaceFilterName {

        fun agingclickInterface(value:String,startAage:String,endage:String)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viwType: Int): InfraFilterViewHolder {
        var view: View?


        view = LayoutInflater.from(parent.context).inflate(R.layout.option_item, parent, false)

        return InfraFilterViewHolder(view)
    }

    override fun onBindViewHolder(holder: InfraFilterViewHolder, position: Int) {
        try {
            val content = list?.get(position)

           var name= content!!["name"].toString()

            holder.radioButton!!.text= name

            //by default last radio button selected
            if (pos == position.toString()) {
                if (selected == null) {
                    holder!!.radioButton!!.setChecked(true);
                    selected = holder.radioButton;
                }
            }

            holder.radioButton!!.setOnClickListener {
                if (selected != null) {
                    selected!!.isChecked = false
                }
                holder.radioButton!!.isChecked = true
                selected = holder.radioButton

                mOptionInterfaceFilterName.agingclickInterface(position.toString(), content!!["start_rang"].toString(), content!!["end_rang"].toString())
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return list!!.size
    }


}