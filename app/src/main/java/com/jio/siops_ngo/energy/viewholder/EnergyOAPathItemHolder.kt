package com.jio.siops_ngo.energy.viewholder

import android.view.View
import androidx.cardview.widget.CardView

import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewMedium
import com.jio.siops_ngo.R


class EnergyOAPathItemHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
    var txtName: TextViewMedium? = null
    var mCardView: CardView? = null


    init {
        txtName = view!!.findViewById(R.id.txt_path);
        mCardView = view!!.findViewById(R.id.card_path);
    }
}