package com.jio.siops_ngo.infra.viewholder

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewLight
import com.jio.jioinfra.custom.TextViewMedium
import com.jio.siops_ngo.R

class InfraPerformanceItemViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {

    var txtSiteId: TextViewLight? = null
    var cnstrntL850Mhz: ConstraintLayout? = null
    var cnstrntL850MhzData: ConstraintLayout? = null
    var txtLast30days: TextViewMedium? = null
    var rv850Mhz: RecyclerView? = null


    init {
        txtSiteId = view!!.findViewById(R.id.txt_site_id_value);
        cnstrntL850Mhz = view!!.findViewById(R.id.cnstrntL_850Mhz);
        cnstrntL850MhzData = view!!.findViewById(R.id.cnstrntL_850Mhz_data);
        txtLast30days = view!!.findViewById(R.id.txt_last30days);
        rv850Mhz = view!!.findViewById(R.id.rv_850Mhz_data);
    }
}