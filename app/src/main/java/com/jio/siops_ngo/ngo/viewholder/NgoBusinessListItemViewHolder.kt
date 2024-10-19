package com.jio.siops_ngo.ngo.viewholder

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout

import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewMedium
import com.jio.siops_ngo.R


class NgoBusinessListItemViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
    var txtTitle: TextViewMedium? = null
    var imgCircle: AppCompatImageView? = null
    var cnstrntLItem: ConstraintLayout? = null


    init {
        txtTitle = view!!.findViewById(R.id.txt_item_title);
        imgCircle = view!!.findViewById(R.id.img_circle);
        cnstrntLItem = view!!.findViewById(R.id.cnstrnt_item);
    }
}