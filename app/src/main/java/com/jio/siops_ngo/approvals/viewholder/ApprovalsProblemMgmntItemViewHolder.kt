package com.jio.siops_ngo.approvals.viewholder

import android.view.View
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewLight
import com.jio.jioinfra.custom.TextViewMedium
import com.jio.siops_ngo.R

class ApprovalsProblemMgmntItemViewHolder (view: View?) : RecyclerView.ViewHolder(view!!) {
    var txtProblemId: TextViewLight? = null
    var txtProblemDescription: TextViewLight? = null
    var txtRcaDomain: TextViewLight? = null
    var txtRcaApprover: TextViewLight? = null
    var txtRcaReport: TextViewLight? = null
    var txtPreventiveMeasure: TextViewLight? = null
    var cardApprove: CardView? = null
    var cardReject: CardView? = null
    init {
        txtProblemId = view!!.findViewById(R.id.txt_problem_id);
        txtProblemDescription = view!!.findViewById(R.id.txt_problem_descr);
        txtRcaDomain = view!!.findViewById(R.id.txt_rca_domain);
        txtRcaApprover = view!!.findViewById(R.id.txt_rca_approver);
        txtRcaReport = view!!.findViewById(R.id.txtRcaReport);
        txtPreventiveMeasure = view!!.findViewById(R.id.txtPrevMeasure);
        cardReject = view!!.findViewById(R.id.card_reject);
        cardApprove = view!!.findViewById(R.id.card_approve);

    }
}