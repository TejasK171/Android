package com.jio.siops_ngo.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewLight
import com.jio.siops_ngo.R

class InfraSitesDownHistoryViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {

    var txtStartDate: TextViewLight? = null
    var txtAgeing: TextViewLight? = null
    var txtIc: TextViewLight? = null


    init {
        txtStartDate = view!!.findViewById(R.id.txtStartDate);
        txtAgeing = view!!.findViewById(R.id.txtAgeing);
        txtIc = view!!.findViewById(R.id.txt_ic);
    }
}