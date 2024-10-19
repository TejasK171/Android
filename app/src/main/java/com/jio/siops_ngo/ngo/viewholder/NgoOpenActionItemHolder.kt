package com.jio.siops_ngo.ngo.viewholder

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout

import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewLight
import com.jio.jioinfra.custom.TextViewMedium
import com.jio.siops_ngo.R


class NgoOpenActionItemHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
    var txtRefNo: TextViewLight? = null
    var txtRefNoTitle: TextViewMedium? = null
    var txtStatus: TextViewLight? = null
    var txtAssignedBy: TextViewLight? = null
    var txtAssignedByTitle: TextViewMedium? = null
    var txtAgeing: TextViewLight? = null
    var txtAgeingTitle: TextViewMedium? = null
    var txtOpenAt: TextViewLight? = null
    var txtClosedAt: TextViewLight? = null
    var txtClosedAtTitle: TextViewMedium? = null
    var txtActionDescr: TextViewLight? = null
    var txtActionDescrTitle: TextViewMedium? = null
    var constraintLayoutClosedAt: ConstraintLayout? = null
    var txtShowMore: TextViewMedium? = null
    var txtdescr: TextViewLight? = null
    var cnstrntLstatus: ConstraintLayout? = null



    init {
        txtRefNo = view!!.findViewById(R.id.txt_ref_id);
        txtRefNoTitle = view!!.findViewById(R.id.txt_refernce_id_title);
        txtStatus = view!!.findViewById(R.id.txt_status);
        txtAssignedBy = view!!.findViewById(R.id.txt_assigned_by);
        txtAssignedByTitle = view!!.findViewById(R.id.txt_assigned_by_title);
        txtAgeing = view!!.findViewById(R.id.txt_ageing);
        txtAgeingTitle = view!!.findViewById(R.id.txt_ageing_title);
        txtOpenAt = view!!.findViewById(R.id.txt_open_at);
        txtClosedAt = view!!.findViewById(R.id.txt_closed_at);
        txtActionDescr = view!!.findViewById(R.id.txt_action_descr);
        txtActionDescrTitle = view!!.findViewById(R.id.txt_action_descr_title);
        txtClosedAtTitle = view!!.findViewById(R.id.txt_closed_at_title);
        constraintLayoutClosedAt = view!!.findViewById(R.id.card3_oi2);
        txtShowMore = view!!.findViewById(R.id.txt_show_more);
        txtdescr = view!!.findViewById(R.id.txt_descr_more);
        cnstrntLstatus = view!!.findViewById(R.id.card2_oi);

    }
}