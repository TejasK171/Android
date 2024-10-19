package com.jio.siops_ngo.infra.viewholder

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewLight
import com.jio.jioinfra.custom.TextViewMedium
import com.jio.siops_ngo.R

class DashboardEnergyViewholder(view: View?) : RecyclerView.ViewHolder(view!!) {

    var card1: ConstraintLayout? = null
    var card2: ConstraintLayout? = null
    var card3: ConstraintLayout? = null
    var site_title1: TextViewMedium? = null
    var site_title2: TextViewMedium? = null
    var site_title3: TextViewMedium? = null
    var site__load_title1: TextViewMedium? = null
    var site__load_title2: TextViewMedium? = null
    var site__load_title3: TextViewMedium? = null
    var co2_title1: TextViewMedium? = null
    var co2_title2: TextViewMedium? = null
    var co2_title3: TextViewMedium? = null
    var power_title1: TextViewMedium? = null
    var power_title2: TextViewMedium? = null
    var power_title3: TextViewMedium? = null
    var paid_txt: TextViewMedium? = null
    var cillected_txt: TextViewMedium? = null
    var eb_text: TextViewMedium? = null
    var dg_text: TextViewMedium? = null
    var site_count1: TextViewLight? = null
    var site_count2: TextViewLight? = null
    var site_count3: TextViewLight? = null
    var site_load_count1: TextViewLight? = null
    var site_load_count2: TextViewLight? = null
    var site_load_count3: TextViewLight? = null
    var eb_count: TextViewLight? = null
    var dg_count: TextViewLight? = null
    var collected_count: TextViewLight? = null
    var paid_count: TextViewLight? = null

    var power_count1: TextViewLight? = null
    var power_count2: TextViewLight? = null
    var power_count3: TextViewLight? = null
    var co_count1: TextViewLight? = null
    var co_count2: TextViewLight? = null
    var co_count3: TextViewLight? = null
    var txtSiteLoadUnit1: TextViewMedium? = null
    var txtSiteLoadUnit2: TextViewMedium? = null
    var txtSiteLoadUnit3: TextViewMedium? = null
    init {
        site_title1 = view!!.findViewById(R.id.txt_site_title1);
        site_title2 = view!!.findViewById(R.id.txt_site_title2);
        site_title3 = view!!.findViewById(R.id.txt_site_title3);
        site__load_title1 = view!!.findViewById(R.id.site__load_title1);
        site__load_title2 = view!!.findViewById(R.id.site__laod_title2);
        site__load_title3 = view!!.findViewById(R.id.site_load_title3);
        co2_title1 = view!!.findViewById(R.id.co2_title1);
        co2_title2 = view!!.findViewById(R.id.co2_title2);
        co2_title3 = view!!.findViewById(R.id.co2_title3);
        power_title1 = view!!.findViewById(R.id.power_title1);
        power_title2 = view!!.findViewById(R.id.power_title2);
        power_title3 = view!!.findViewById(R.id.power_title3);
        site_count1 = view!!.findViewById(R.id.site_count1);
        site_count2 = view!!.findViewById(R.id.site_count2);
        site_count3 = view!!.findViewById(R.id.txt_site_count3);
        site_load_count1 = view!!.findViewById(R.id.site_load_count1);
        site_load_count2 = view!!.findViewById(R.id.site_laod_count2);
        site_load_count3 = view!!.findViewById(R.id.site__load_count3);
        power_count1 = view!!.findViewById(R.id.power_count1);
        power_count2 = view!!.findViewById(R.id.power_count2);
        power_count3 = view!!.findViewById(R.id.power_count3);
        co_count1 = view!!.findViewById(R.id.co_count1);
        co_count2 = view!!.findViewById(R.id.co_count2);
        co_count3 = view!!.findViewById(R.id.co_count3);
        eb_text = view!!.findViewById(R.id.eb_text);
        dg_text = view!!.findViewById(R.id.dg_text);
        cillected_txt = view!!.findViewById(R.id.cillected_txt);
        paid_txt = view!!.findViewById(R.id.paid_txt);
        paid_count = view!!.findViewById(R.id.paid_count);
        collected_count = view!!.findViewById(R.id.collected_count);
        paid_count = view!!.findViewById(R.id.paid_count);
        dg_count = view!!.findViewById(R.id.dg_count);
        eb_count = view!!.findViewById(R.id.eb_count);
        card1 = view!!.findViewById(R.id.card1);
        card2 = view!!.findViewById(R.id.card2);
        card3 = view!!.findViewById(R.id.card3);
        txtSiteLoadUnit1 = view!!.findViewById(R.id.txt_site_load_unit);
        txtSiteLoadUnit2 = view!!.findViewById(R.id.txt_site_load_unit2);
        txtSiteLoadUnit3 = view!!.findViewById(R.id.txt_site_load_unit3);


    }
}