package com.jio.siops_ngo.approvals.viewholder

import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewLight
import com.jio.jioinfra.custom.TextViewMedium
import com.jio.siops_ngo.R

class TimeTrackingExViewSImilarViewHolder (view: View?) : RecyclerView.ViewHolder(view!!) {
    var txtResourceName: TextViewMedium? = null
    var txtExcluded: TextViewMedium? = null



    init {
        txtResourceName = view!!.findViewById(R.id.txt_resource_name);
        txtExcluded = view!!.findViewById(R.id.txt_excluded_name);

    }
}