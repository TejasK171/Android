package com.jio.siops_ngo.ngo.viewholder

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewLight
import com.jio.jioinfra.custom.TextViewMedium
import com.jio.siops_ngo.R

class NgoOpneHistoryViewHolder (view: View?) : RecyclerView.ViewHolder(view!!) {

    var txt_ack_status: TextViewMedium? = null
    var txttime: TextViewLight? = null
    var acknowladgeheaderId: TextViewLight? = null
    var txt_siteId: TextViewLight? = null
    var txt_name: TextViewMedium? = null
    var txt_hrs: TextViewLight? = null
    var title1: TextViewMedium? = null
    var title2: TextViewMedium? = null
    var title3: TextViewMedium? = null
    var title4: TextViewMedium? = null
    var txt_opne_at: TextViewLight? = null
    var txt_ack_at: TextViewLight? = null
    var txt_app_i_ops: TextViewLight? = null
    var view_history_id: TextViewMedium? = null
    var view_errer_msg_id: TextViewMedium? = null
    var view_hide_id: TextViewMedium? = null
    var call_img_id: AppCompatImageView? = null
    init {
        txt_ack_status = view!!.findViewById(R.id.txt_ack_status);
        txttime = view!!.findViewById(R.id.txttime);
        acknowladgeheaderId = view!!.findViewById(R.id.acknowladgeheaderId);
        txt_app_i_ops = view!!.findViewById(R.id.txt_app_i_ops);
        txt_siteId = view!!.findViewById(R.id.txt_siteId);
        txt_name = view!!.findViewById(R.id.txt_header);
        call_img_id = view!!.findViewById(R.id.call_img_id);
        txt_hrs = view!!.findViewById(R.id.txt_hrs);
        title1 = view!!.findViewById(R.id.title1);
        title2 = view!!.findViewById(R.id.title2);
        txt_ack_at = view!!.findViewById(R.id.txt_ack_at);
        title3 = view!!.findViewById(R.id.title3);
        title4 = view!!.findViewById(R.id.title4);
        view_errer_msg_id = view!!.findViewById(R.id.view_errer_msg_id);
        view_hide_id = view!!.findViewById(R.id.view_hide_id);
        view_history_id = view!!.findViewById(R.id.view_history_id);
        txt_opne_at = view!!.findViewById(R.id.txt_opne_at);


    }
}