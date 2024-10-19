package com.jio.siops_ngo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.infra.viewholder.InfraMaintenanceItemViewHolder
import java.lang.Exception

class AlarmsInnerHistoryAdapter (private val activity: MainActivity?) : RecyclerView.Adapter<InfraMaintenanceItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viwType: Int
    ): InfraMaintenanceItemViewHolder {
        var view: View?


        view = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_item, parent, false)

        return InfraMaintenanceItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: InfraMaintenanceItemViewHolder, position: Int) {



        try {



            //   val content = appList[position]
            //   var title = content["applicationName"] as String

            //   holder.txtTitle!!.text = title!!
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return 5
    }
}