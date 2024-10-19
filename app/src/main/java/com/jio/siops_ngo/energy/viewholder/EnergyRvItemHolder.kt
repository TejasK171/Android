package com.jio.siops_ngo.energy.viewholder

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout

import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewLight
import com.jio.jioinfra.custom.TextViewMedium
import com.jio.siops_ngo.R


class EnergyRvItemHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
    var txtCount1: TextViewLight? = null
    var txtSite1: TextViewMedium? = null

    var txtCount2: TextViewLight? = null
    var txtSite2: TextViewMedium? = null
    var txtSiteCountUnit1: TextViewMedium? = null

    var txtCount3: TextViewLight? = null
    var txtSite3: TextViewMedium? = null
    var txtSiteCountUnit2: TextViewMedium? = null

    var txtSiteTitle: TextViewMedium? = null
    var txtSiteCount: TextViewMedium? = null
    var txtSiteCountUnit3: TextViewMedium? = null

    var cnstrntL1: ConstraintLayout? = null
    var cnstrntL2: ConstraintLayout? = null
    var cnstrntL3: ConstraintLayout? = null


    init {

        txtCount1 = view!!.findViewById(R.id.txt_site_count1);
        txtCount1 = view!!.findViewById(R.id.txt_site_count1);
        txtSite1 = view!!.findViewById(R.id.txt_site_title1);
        txtCount2 = view!!.findViewById(R.id.txt_site_count2);
        txtSite2 = view!!.findViewById(R.id.txt_site_title2);
        txtCount3 = view!!.findViewById(R.id.txt_site_count3);
        txtSite3 = view!!.findViewById(R.id.txt_site_title3);
        txtSiteTitle = view!!.findViewById(R.id.txt_site_title);
        txtSiteCount = view!!.findViewById(R.id.txt_site_count);
        cnstrntL1 = view!!.findViewById(R.id.card1);
        cnstrntL2 = view!!.findViewById(R.id.card2);
        cnstrntL3= view!!.findViewById(R.id.card3);
        txtSiteCountUnit1= view!!.findViewById(R.id.txt_site_count_unit1);
        txtSiteCountUnit2= view!!.findViewById(R.id.txt_site_count_unit2);
        txtSiteCountUnit3= view!!.findViewById(R.id.txt_site_count_unit3);
    }
}