package com.jio.siops_ngo.infra.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.energy.viewholder.EnergyOAPathItemHolder
import com.jio.siops_ngo.utilities.MyExceptionHandler
import java.util.ArrayList


class EnergyOpenActionPathAdapter(
    private val activity: MainActivity?,
    private val dataList: ArrayList<Map<String, Any>>,
    val pathRegionStateMpJcListener: PathRegionStateMpJcCLickListener

) : RecyclerView.Adapter<EnergyOAPathItemHolder>() {


    interface PathRegionStateMpJcCLickListener {

        fun onPathRegStateMpJcClicked(positionSelected:Int, key:String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viwType: Int): EnergyOAPathItemHolder {
        var view: View?

        view =
            LayoutInflater.from(parent.context).inflate(R.layout.energy_inner_path_text, parent, false)

        return EnergyOAPathItemHolder(view)
    }


    override fun onBindViewHolder(holder: EnergyOAPathItemHolder, position: Int) {
        try {
            val content = dataList[position]
            var featureName: String = ""
            if (content["key"] != null && content.containsKey("key")) {
                holder.txtName!!.text = content["key"] as String
                featureName = content["key"] as String
            }


            if(position == dataList.size - 1){
                holder.mCardView!!.setCardBackgroundColor(activity!!.resources.getColor(R.color.blue_faint_color))

            }else{
                holder.mCardView!!.setCardBackgroundColor(activity!!.resources.getColor(R.color.txt_grey_color))
            }

            holder.mCardView!!.setOnClickListener {

                try {
                    pathRegionStateMpJcListener.onPathRegStateMpJcClicked(position, featureName)

                } catch (e: Exception) {
                    MyExceptionHandler.handle(e)
                }
            }



        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}