package com.jio.siops_ngo.approvals.viewholder

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewLight
import com.jio.siops_ngo.R

class ApprovalsPendigItemViewHolder (view: View?) : RecyclerView.ViewHolder(view!!) {
    var txt_pending_title: TextViewLight? = null
    var txt_count_value: TextViewLight? = null
    var cnstrntL: ConstraintLayout? = null



    init {
        txt_pending_title = view!!.findViewById(R.id.txt_pending_title);
        txt_count_value = view!!.findViewById(R.id.txt_count_value);
        cnstrntL = view!!.findViewById(R.id.cnstrtL_list);

    }
}