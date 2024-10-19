package com.jio.siops_ngo.approvals.viewholder

import android.view.View
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewLight
import com.jio.jioinfra.custom.TextViewMedium
import com.jio.siops_ngo.R

class ApprovalsChangeMgmntItemViewHolder (view: View?) : RecyclerView.ViewHolder(view!!) {
    var txtHeader: TextViewMedium? = null
    var txtTitle: TextViewLight? = null
    var txtDescription: TextViewLight? = null
    var txtAppInfra: TextViewLight? = null
    var txtRequestedPlatform: TextViewLight? = null
    var txtStartDate: TextViewLight? = null
    var txtEndDate: TextViewLight? = null
    var txtActualStartDownTime: TextViewLight? = null
    var txtActualEndDownTime: TextViewLight? = null
    var txtRiskAnalysis: TextViewLight? = null
    var cardApprove: CardView? = null
    var cardReject: CardView? = null



    init {
        txtHeader = view!!.findViewById(R.id.txt_header);
        txtTitle = view!!.findViewById(R.id.txt_title);
        txtDescription = view!!.findViewById(R.id.txtDescrp);
        txtAppInfra = view!!.findViewById(R.id.txt_app_infra);
        txtRequestedPlatform = view!!.findViewById(R.id.txt_req_platform);
        txtStartDate = view!!.findViewById(R.id.txt_start_date);
        txtEndDate = view!!.findViewById(R.id.txt_end_date);
        txtActualStartDownTime = view!!.findViewById(R.id.txt_actual_start_dwnTime);
        txtActualEndDownTime = view!!.findViewById(R.id.txt_actual_dwnTime_end);
        txtRiskAnalysis = view!!.findViewById(R.id.txt_risk_analysis);
        cardReject = view!!.findViewById(R.id.card_reject);
        cardApprove = view!!.findViewById(R.id.card_approve);

    }
}