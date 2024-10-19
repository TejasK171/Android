package com.jio.siops_ngo.infra.viewholder

import android.view.View

import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewLight
import com.jio.siops_ngo.R


class NgoActivationsTrendItemHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
    var title1: TextViewLight? = null
    var title2: TextViewLight? = null
    var title3: TextViewLight? = null
    var title4: TextViewLight? = null
    var title5: TextViewLight? = null


    init {
        title1 = view!!.findViewById(R.id.title1);
        title2 = view!!.findViewById(R.id.title2);
        title3 = view!!.findViewById(R.id.title3);
        title4 = view!!.findViewById(R.id.title4);
        title5 = view!!.findViewById(R.id.title5);
    }
}