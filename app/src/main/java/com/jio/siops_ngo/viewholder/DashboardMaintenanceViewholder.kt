package com.jio.jioinfra.ui.infraServices.viewholder

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewBold
import com.jio.jioinfra.custom.TextViewLight
import com.jio.jioinfra.custom.TextViewMedium
import com.jio.siops_ngo.R

class DashboardMaintenanceViewholder (view: View?) : RecyclerView.ViewHolder(view!!) {

    var txtHeader: TextViewMedium? = null
    var txtSitesDwn: TextViewLight? = null
    var txtSitesDownCount: TextViewBold? = null
    var title1: TextViewMedium? = null
    var count1: TextViewLight? = null
    var title2: TextViewMedium? = null
    var count2: TextViewLight? = null
    var title3: TextViewMedium? = null
    var count3: TextViewLight? = null
    var alarmeId: TextViewMedium? = null
    var imgAlarm: ImageView? = null
    var card3: ConstraintLayout? = null
    var card2: ConstraintLayout? = null
    var card1: ConstraintLayout? = null
    var txtDateTime: TextViewBold? = null


    init {
        txtHeader = view!!.findViewById(R.id.txt_dashboard_title);
        txtSitesDwn = view!!.findViewById(R.id.txt_dashboard_subtitle);
        txtSitesDownCount = view!!.findViewById(R.id.txt_side_dwn_count);
        title1 = view!!.findViewById(R.id.title1);
        count1 = view!!.findViewById(R.id.count1);
        title2 = view!!.findViewById(R.id.title2);
        count2 = view!!.findViewById(R.id.count2);
        title3 = view!!.findViewById(R.id.title3);
        count3 = view!!.findViewById(R.id.count3);
        alarmeId = view!!.findViewById(R.id.alarmeId);
        imgAlarm = view!!.findViewById(R.id.img_alarm);
        card3 = view!!.findViewById(R.id.card3);
        card2 = view!!.findViewById(R.id.card2);
        card1 = view!!.findViewById(R.id.card1);
        txtDateTime = view!!.findViewById(R.id.txt_datetime);



    }
}