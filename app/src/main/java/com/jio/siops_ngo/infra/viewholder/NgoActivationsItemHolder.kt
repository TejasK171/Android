package com.jio.siops_ngo.infra.viewholder

import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView

import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewLight
import com.jio.siops_ngo.R


class NgoActivationsItemHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
    var txtStage: TextViewLight? = null
    var txtToday: TextViewLight? = null
    var txtYday: TextViewLight? = null
    var txtYdayDiff: TextViewLight? = null
    var txtPercentage: TextViewLight? = null
    var imgStatus: AppCompatImageView? = null
    var ll: LinearLayout? = null


    init {
        txtStage = view!!.findViewById(R.id.txt_stage);
        txtToday = view!!.findViewById(R.id.txt_today);
        txtYday = view!!.findViewById(R.id.txt_yday);
        txtYdayDiff = view!!.findViewById(R.id.txt_yday_diff);
        txtPercentage = view!!.findViewById(R.id.txt_percentage);
        imgStatus = view!!.findViewById(R.id.img_status);
        ll = view!!.findViewById(R.id.ll);
    }
}