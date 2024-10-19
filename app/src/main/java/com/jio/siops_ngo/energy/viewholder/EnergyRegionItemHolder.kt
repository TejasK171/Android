package com.jio.siops_ngo.energy.viewholder

import android.view.View
import android.widget.RelativeLayout

import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewLight
import com.jio.jioinfra.custom.TextViewMedium
import com.jio.siops_ngo.R


class EnergyRegionItemHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
    var txtName: TextViewLight? = null
    var txtCount: TextViewMedium? = null
    var lnrItem: RelativeLayout? = null


    init {
        txtName = view!!.findViewById(R.id.txt_header);
        txtCount = view!!.findViewById(R.id.txt_count);
        lnrItem = view!!.findViewById(R.id.lnr_oa_item);
    }
}