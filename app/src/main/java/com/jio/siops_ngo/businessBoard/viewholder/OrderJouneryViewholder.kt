package com.jio.siops_ngo.infra.viewholder

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewLight
import com.jio.jioinfra.custom.TextViewMedium
import com.jio.siops_ngo.R

class OrderJouneryViewholder(view: View?) : RecyclerView.ViewHolder(view!!) {

    var txtLessDetails: TextViewMedium? = null
    var cnstrntLHiddenRow: ConstraintLayout? = null
    var txtEntered: TextViewMedium? = null
    var txtEnteredCount: TextViewLight? = null
    var txtActivated: TextViewMedium? = null
    var txtActivatedCount: TextViewLight? = null
    var txtNetworkLatched: TextViewMedium? = null
    var txtNetworkLatchedCount: TextViewLight? = null
    var txtRejected: TextViewMedium? = null
    var txtRejectedCount: TextViewLight? = null
    var txtInProcess: TextViewMedium? = null
    var txtInProcessCount: TextViewLight? = null
    var txtTvPending: TextViewMedium? = null
    var txtTvPendingCount: TextViewLight? = null
    var title1_wo: TextViewLight? = null
    var title2_wo: TextViewLight? = null
    var title3_wo: TextViewLight? = null
    var title4_wo: TextViewLight? = null
    var title5_wo: TextViewLight? = null
    var txtTitle: TextViewMedium? = null
    var txtCircle: TextViewMedium? = null
    var count1_wo: TextViewMedium? = null
    var count2_wo: TextViewMedium? = null
    var count3_wo: TextViewMedium? = null
    var count4_wo: TextViewMedium? = null
    var count5_wo: TextViewMedium? = null
    var txt_process_header: TextViewMedium? = null
    var card5: ConstraintLayout? = null
    var card4: ConstraintLayout? = null
    var card5_wo: ConstraintLayout? = null
    var cnstrntL_inProcess: ConstraintLayout? = null
    init {
        txtLessDetails = view!!.findViewById(R.id.txt_less_details);
        txt_process_header = view!!.findViewById(R.id.txt_process_header);
        cnstrntL_inProcess = view!!.findViewById(R.id.cnstrntL_inProcess);
        count5_wo = view!!.findViewById(R.id.count5_wo);
        count4_wo = view!!.findViewById(R.id.count4_wo);
        count3_wo = view!!.findViewById(R.id.count3_wo);
        count2_wo = view!!.findViewById(R.id.count2_wo);
        count1_wo = view!!.findViewById(R.id.count1_wo);

        title5_wo = view!!.findViewById(R.id.title5_wo);
        title4_wo = view!!.findViewById(R.id.title4_wo);
        title3_wo = view!!.findViewById(R.id.title3_wo);
        title2_wo = view!!.findViewById(R.id.title2_wo);
        title1_wo = view!!.findViewById(R.id.title1_wo);
        card4 = view!!.findViewById(R.id.card4);
        card5 = view!!.findViewById(R.id.card5);
        card5_wo = view!!.findViewById(R.id.card5_wo);
        cnstrntLHiddenRow = view!!.findViewById(R.id.cnstrntL_hidden_row);
        txtEntered = view!!.findViewById(R.id.txt_entered);
        txtEnteredCount = view!!.findViewById(R.id.txt_entered_count);
        txtActivated = view!!.findViewById(R.id.txt_activated);
        txtActivatedCount = view!!.findViewById(R.id.txt_activated_count);
        txtNetworkLatched = view!!.findViewById(R.id.txt_network_latched);
        txtNetworkLatchedCount = view!!.findViewById(R.id.txt_network_latched_count);
        txtRejected = view!!.findViewById(R.id.txt_rejected);
        txtRejectedCount = view!!.findViewById(R.id.txt_rejected_count);
        txtInProcess = view!!.findViewById(R.id.txt_inprocess);
        txtInProcessCount = view!!.findViewById(R.id.txt_inprocess_count);
        txtTvPending = view!!.findViewById(R.id.txt_tvpending);
        txtTvPendingCount = view!!.findViewById(R.id.txt_tvpending_count);
        txtTitle = view!!.findViewById(R.id.txt_all_channels_title);
        txtCircle = view!!.findViewById(R.id.txt_circle);
    }
}