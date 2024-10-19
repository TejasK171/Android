package com.jio.siops_ngo.approvals.viewholder

import android.view.View
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewLight
import com.jio.jioinfra.custom.TextViewMedium
import com.jio.siops_ngo.R

class ApprovalsDetailsItemHolder (view: View?) : RecyclerView.ViewHolder(view!!) {
    var txtItemName: TextViewMedium? = null
    var txtItemCount: TextViewMedium? = null
    var txtName: TextViewMedium? = null
    var txtRequestIdTitle: TextViewMedium? = null
    var txtStartDate: TextViewLight? = null
    var txtStartDateTitle: TextViewMedium? = null
    var txtEndDate: TextViewLight? = null
    var txtEndDateTitle: TextViewMedium? = null
//    var txtGroup: TextViewLight? = null
    var txtGroupViewDetails: TextViewMedium? = null
    var txtJustification: TextViewLight? = null
    var txtJustificationTitle: TextViewMedium? = null
    var txtJustificationViewDetails: TextViewMedium? = null
    var txtJustificationDetails: TextViewMedium? = null
    var txtGroupTitle: TextViewMedium? = null
    var rvGroup: RecyclerView? = null
    var cardApprove: CardView? = null
    var cardReject: CardView? = null
    var cnstrntLEndDate: ConstraintLayout? = null
    var cnstrntLGroup: ConstraintLayout? = null
    var txtViewSimilar: TextViewMedium? = null

    init {
        txtItemName = view!!.findViewById(R.id.txt_item_name);
        cardReject = view!!.findViewById(R.id.card_reject);
        cardApprove = view!!.findViewById(R.id.card_approve);
        txtItemCount = view!!.findViewById(R.id.txt_item_count);
        txtName = view!!.findViewById(R.id.txt_header);
        txtRequestIdTitle = view!!.findViewById(R.id.txt_request_id_title);
        txtStartDate = view!!.findViewById(R.id.txt_start_date);
        txtEndDate = view!!.findViewById(R.id.txt_end_date);
        txtEndDateTitle = view!!.findViewById(R.id.txt_end_date_title);
        txtGroupTitle = view!!.findViewById(R.id.txt_risk_analysis_title);
        txtGroupViewDetails = view!!.findViewById(R.id.txt_group_view_details);
        txtJustification = view!!.findViewById(R.id.txt_justification);
        txtJustificationTitle = view!!.findViewById(R.id.txt_justification_title);
        txtJustificationViewDetails = view!!.findViewById(R.id.txt_justification_view_details);
        txtStartDateTitle = view!!.findViewById(R.id.txt_start_date_title);
        txtJustificationDetails = view!!.findViewById(R.id.txt_justification_details);
        rvGroup = view!!.findViewById(R.id.rv_grp);
        cnstrntLEndDate = view!!.findViewById(R.id.cnstrntL_ed);
        cnstrntLGroup = view!!.findViewById(R.id.cnstrntL_grp);
        txtViewSimilar = view!!.findViewById(R.id.txt_view_similar);

    }
}