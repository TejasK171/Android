package com.jio.siops_ngo.infra.viewholder

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewLight
import com.jio.jioinfra.custom.TextViewMedium
import com.jio.siops_ngo.R

class RechargeJouneryViewholder(view: View?) : RecyclerView.ViewHolder(view!!) {
var card5 :ConstraintLayout?=null

    var txt_more_details: TextViewMedium? = null
    var txt_hide_wo: TextViewMedium? = null
    var header1: TextViewMedium? = null
    var header2: TextViewLight? = null
    var header3: TextViewLight? = null
    var header4: TextViewLight? = null
    var header5: TextViewLight? = null
    var header6: TextViewLight? = null
    var header7: TextViewLight? = null
    var txt_chanals_title: TextViewMedium? = null
    var channal_count1: TextViewLight? = null
    var channal_count2: TextViewLight? = null
    var channal_count3: TextViewLight? = null
    var channal_count4: TextViewLight? = null
    var channal_count5: TextViewLight? = null
    var channal_count6: TextViewLight? = null
    var channal_title1: TextViewMedium? = null
    var channal_title2: TextViewMedium? = null
    var channal_title3: TextViewMedium? = null
    var channal_title4: TextViewMedium? = null
    var channal_title5: TextViewMedium? = null
    var channal_title6: TextViewMedium? = null
    var constantIdCl: ConstraintLayout? = null



    init {
        card5 = view!!.findViewById(R.id.card5);
        txt_more_details = view!!.findViewById(R.id.txt_more_details);
        txt_hide_wo = view!!.findViewById(R.id.txt_hide_wo);
        constantIdCl = view!!.findViewById(R.id.constantIdCl);
        header1 = view!!.findViewById(R.id.header1);
        header2 = view!!.findViewById(R.id.header2);
        header3 = view!!.findViewById(R.id.header3);
        header4 = view!!.findViewById(R.id.header4);
        header5 = view!!.findViewById(R.id.header5);
        header6 = view!!.findViewById(R.id.header6);
        header7 = view!!.findViewById(R.id.header7);
        channal_count1 = view!!.findViewById(R.id.channal_count1);
        channal_count2 = view!!.findViewById(R.id.channal_count2);
        channal_count3 = view!!.findViewById(R.id.channal_count3);
        channal_count4 = view!!.findViewById(R.id.channal_count4);
        channal_count5 = view!!.findViewById(R.id.channal_count5);
        channal_count6 = view!!.findViewById(R.id.channal_count6);
        channal_title1 = view!!.findViewById(R.id.channal_title1);
        channal_title2 = view!!.findViewById(R.id.channal_title2);
        channal_title3 = view!!.findViewById(R.id.channal_title3);
        channal_title4 = view!!.findViewById(R.id.channal_title4);
        channal_title5 = view!!.findViewById(R.id.channal_title5);
        channal_title6 = view!!.findViewById(R.id.channal_title6);
        txt_chanals_title = view!!.findViewById(R.id.txt_chanals_title);



    }
}