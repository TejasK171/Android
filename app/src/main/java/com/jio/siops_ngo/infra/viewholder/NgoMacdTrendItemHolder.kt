package com.jio.siops_ngo.infra.viewholder

import android.view.View
import android.widget.LinearLayout

import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewLight
import com.jio.jioinfra.custom.TextViewMedium
import com.jio.siops_ngo.R


class NgoMacdTrendItemHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
    var title1: TextViewLight? = null
    var title2: TextViewLight? = null
    var title3: TextViewLight? = null
    var title4: TextViewLight? = null
    var title5: TextViewLight? = null
    var lnrHeader: LinearLayout? = null
    var lnrTrendValues: LinearLayout? = null

    var header1: TextViewMedium? = null
    var header2: TextViewMedium? = null
    var header3: TextViewMedium? = null
    var header4: TextViewMedium? = null
    var header5: TextViewMedium? = null


    init {
        title1 = view!!.findViewById(R.id.title1);
        title2 = view!!.findViewById(R.id.title2);
        title3 = view!!.findViewById(R.id.title3);
        title4 = view!!.findViewById(R.id.title4);
        lnrHeader = view!!.findViewById(R.id.lnr_header);
        lnrTrendValues = view!!.findViewById(R.id.lnr_trend_values);
        header1 = view!!.findViewById(R.id.header1);
        header2 = view!!.findViewById(R.id.header2);
        header3 = view!!.findViewById(R.id.header3);
        header4 = view!!.findViewById(R.id.header4);
    }
}