package com.jio.siops_ngo.infra.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.infra.viewholder.InfraFilterViewHolder


class FilterJCAdapter (private val mOptionInterfaceJC:OptionInterfaceJC,private  val list:ArrayList<String>?, private val activity: MainActivity?) : RecyclerView.Adapter<InfraFilterViewHolder>() {

    var booleanCheck:Boolean=true
    private var selected: RadioButton? = null




    interface OptionInterfaceJC {

        fun clickInterfaceItemJC(value:String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viwType: Int): InfraFilterViewHolder {
        var view: View?


        view = LayoutInflater.from(parent.context).inflate(R.layout.option_item, parent, false)

        return InfraFilterViewHolder(view)
    }

    override fun onBindViewHolder(holder: InfraFilterViewHolder, position: Int) {
        try {


            holder.radioButton!!.text=list!!.get(position).toString()

            //by default last radio button selected
            if (position == 0) {
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

            }

        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return list!!.size
    }


}