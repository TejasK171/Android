package com.jio.siops_ngo.infra.viewholder

import android.view.View

import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewLight
import com.jio.siops_ngo.R


class DcbOnClickItemHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
    var txtDcbItemTitle: TextViewLight? = null


    init {
        txtDcbItemTitle = view!!.findViewById(R.id.txt_siteid_value);
    }
}