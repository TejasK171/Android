package com.jio.siops_ngo.infra.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewLight
import com.jio.siops_ngo.R

class MaintenanceInnerItemViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
    var title: TextViewLight? = null



    init {

        title = view!!.findViewById(R.id.txt_dashboard_subtitle);

    }
}