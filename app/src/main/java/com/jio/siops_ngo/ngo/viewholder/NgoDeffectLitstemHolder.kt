package com.jio.siops_ngo.ngo.viewholder

import android.view.View
import android.widget.LinearLayout

import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewLight
import com.jio.siops_ngo.R


class NgoDeffectLitstemHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
    var txt_siteid_title: TextViewLight? = null
    var txt_siteid_value: TextViewLight? = null
    var ll: LinearLayout? = null



    init {
        txt_siteid_title = view!!.findViewById(R.id.txt_siteid_title);
        txt_siteid_value = view!!.findViewById(R.id.txt_siteid_value);
        ll = view!!.findViewById(R.id.ll);


    }
}