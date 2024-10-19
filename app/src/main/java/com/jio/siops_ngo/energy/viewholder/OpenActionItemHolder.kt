package com.jio.siops_ngo.energy.viewholder

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewLight
import com.jio.siops_ngo.R

class OpenActionItemHolder (view: View?) : RecyclerView.ViewHolder(view!!) {
    var textViewActionCount: TextViewLight? = null
    var textViewActionName: TextViewLight? = null
    var constLL: ConstraintLayout? = null


    init {
        textViewActionName = view!!.findViewById(R.id.textViewActionName);
        textViewActionCount = view!!.findViewById(R.id.textViewActionCount);

        constLL = view!!.findViewById(R.id.constLL);
    }
}