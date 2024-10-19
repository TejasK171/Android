package com.jio.siops_ngo.ngo.viewholder

import android.view.View

import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewLight
import com.jio.jioinfra.custom.TextViewMedium
import com.jio.siops_ngo.R


class NgoOpenIncidentItemHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
    var txtTicketNo: TextViewLight? = null
    var txtStatus: TextViewLight? = null
    var txtSeverity: TextViewLight? = null
    var txtImpact: TextViewLight? = null
    var txtResolverGroup: TextViewLight? = null
    var txtAgeing: TextViewLight? = null
    var txtOpenAt: TextViewLight? = null
    var txtClosedAt: TextViewLight? = null
    var txtApplication: TextViewLight? = null
    var view_summary_id: TextViewLight? = null
    var view_hide_id: TextViewMedium? = null



    init {
        txtTicketNo = view!!.findViewById(R.id.txt_oi_ticket_no);
        view_summary_id = view!!.findViewById(R.id.view_summary_id);
        txtStatus = view!!.findViewById(R.id.txt_status);
        txtSeverity = view!!.findViewById(R.id.txt_oi_severity);
        txtImpact = view!!.findViewById(R.id.txt_impact);
        txtResolverGroup = view!!.findViewById(R.id.txt_resolver_grp);
        txtAgeing = view!!.findViewById(R.id.txt_ageing);
        txtOpenAt = view!!.findViewById(R.id.txt_open_at);
        txtClosedAt = view!!.findViewById(R.id.txt_closed_at);
        txtApplication = view!!.findViewById(R.id.txt_application);
        view_hide_id = view!!.findViewById(R.id.view_hide_id);


    }
}