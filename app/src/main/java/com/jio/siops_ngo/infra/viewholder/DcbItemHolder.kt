package com.jio.siops_ngo.infra.viewholder

import android.view.View
import android.widget.RelativeLayout

import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewLight
import com.jio.siops_ngo.R


class DcbItemHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
    var txtCount: TextViewLight? = null
    var txtValue: TextViewLight? = null
    var dcbConstraintLayout: RelativeLayout? = null


    init {
        txtCount = view!!.findViewById(R.id.txt_dcb_count);
        txtValue = view!!.findViewById(R.id.txt_dcb_value);
        dcbConstraintLayout = view!!.findViewById(R.id.cnsrntL_dcb);
    }
}