package com.jio.siops_ngo.energyDataControlBoard.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.infra.viewholder.DcbOnClickItemHolder
import com.jio.siops_ngo.utilities.MyExceptionHandler


class EnergyDataCompletsGapesListAdapter(mainActivity: MainActivity?) : RecyclerView.Adapter<DcbOnClickItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viwType: Int): DcbOnClickItemHolder {
        var view: View?

        view = LayoutInflater.from(parent.context).inflate(R.layout.item_view_open_action, parent, false)

        return DcbOnClickItemHolder(view)
    }

    override fun onBindViewHolder(holder: DcbOnClickItemHolder, position: Int) {
        try {
          //  val content = appList[position]



         //   holder.txtDcbItemTitle!!.text = content["featureId"] as String



        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
        }
    }

    override fun getItemCount(): Int {
        return 10
    }
}
