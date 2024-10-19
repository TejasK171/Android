package com.jio.siops_ngo.approvals.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewLight
import com.jio.jioinfra.custom.TextViewMedium
import com.jio.siops_ngo.R

class ApprovalsGroupItemViewHolder (view: View?) : RecyclerView.ViewHolder(view!!) {
    var txtGroupTitle: TextViewMedium? = null
    var txtGroupValue: TextViewLight? = null



    init {
        txtGroupTitle = view!!.findViewById(R.id.txt_risk_analysis_title);
        txtGroupValue = view!!.findViewById(R.id.txt_source_name);

    }
}