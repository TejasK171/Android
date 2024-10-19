package com.jio.siops_ngo.governance.viewholder

import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewLight
import com.jio.jioinfra.custom.TextViewMedium
import com.jio.siops_ngo.R

class GeneralShiftLiveItemViewHolder (view: View?) : RecyclerView.ViewHolder(view!!) {
    var txtDetails: TextViewMedium? = null
    var txtHide: TextViewMedium? = null
    var txtResource: TextViewMedium? = null
    var txtCitrixSessionStatus: TextViewMedium? = null
    var txtNgoSessionStatus: TextViewMedium? = null
    var txtAlertAcknwCount: TextViewMedium? = null
    var txtCitrixStartTime: TextViewMedium? = null
    var txtCitrixEndTime: TextViewMedium? = null
    var txtNgoStartTime: TextViewMedium? = null
    var imgProdStatus: AppCompatImageView? = null
    var txtNgoEndTime: TextViewMedium? = null
    var constHide: ConstraintLayout? = null

    init {
        txtDetails = view!!.findViewById(R.id.txt_details);
        txtHide = view!!.findViewById(R.id.txt_hide);
        txtCitrixSessionStatus = view!!.findViewById(R.id.txt_citrix_session_status);
        txtNgoSessionStatus = view!!.findViewById(R.id.txt_ngo_session_status);
        txtAlertAcknwCount = view!!.findViewById(R.id.txt_alerts_acknw_count);
        txtCitrixStartTime = view!!.findViewById(R.id.txt_start_time_citrix);
        txtCitrixEndTime = view!!.findViewById(R.id.txt_end_time_citrix);
        txtNgoStartTime = view!!.findViewById(R.id.txt_start_time_ngo);
        txtNgoEndTime = view!!.findViewById(R.id.txt_end_time_ngo);
        constHide = view!!.findViewById(R.id.const_show);
        txtResource = view!!.findViewById(R.id.txt_resource)
        imgProdStatus = view!!.findViewById(R.id.img_productivity_status)

    }
}