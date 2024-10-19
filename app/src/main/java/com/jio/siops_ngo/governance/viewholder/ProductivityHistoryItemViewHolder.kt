package com.jio.siops_ngo.governance.viewholder

import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewLight
import com.jio.jioinfra.custom.TextViewMedium
import com.jio.siops_ngo.R

class ProductivityHistoryItemViewHolder (view: View?) : RecyclerView.ViewHolder(view!!) {
    var txtDetails: TextViewMedium? = null
    var txtHide: TextViewMedium? = null
    var txtResource: TextViewMedium? = null
    var imgProdStatus: AppCompatImageView? = null
    var txtCitrixTime: TextViewMedium? = null
    var txtNgoTime: TextViewMedium? = null
    var txtTimeClocked: TextViewMedium? = null
    var txtAlertsAcknowledged: TextViewMedium? = null
    var txtAlertAckTime: TextViewMedium? = null
    var txtDelayedAckAlerts: TextViewMedium? = null
    var constHide: ConstraintLayout? = null

    init {
        txtDetails = view!!.findViewById(R.id.txt_details);
        txtHide = view!!.findViewById(R.id.txt_hide);
        txtCitrixTime = view!!.findViewById(R.id.txt_citrix_time);
        txtNgoTime = view!!.findViewById(R.id.txt_ngo_time);
        txtTimeClocked = view!!.findViewById(R.id.txt_time_clocked);
        txtAlertsAcknowledged = view!!.findViewById(R.id.txt_alert_ack_count);
        txtAlertAckTime = view!!.findViewById(R.id.txt_alert_ack_time);
        txtDelayedAckAlerts = view!!.findViewById(R.id.txt_delayed_ack_alerts_count);
        constHide = view!!.findViewById(R.id.const_show);
        txtResource = view!!.findViewById(R.id.txt_resource)
        imgProdStatus = view!!.findViewById(R.id.img_productivity_status)

    }
}