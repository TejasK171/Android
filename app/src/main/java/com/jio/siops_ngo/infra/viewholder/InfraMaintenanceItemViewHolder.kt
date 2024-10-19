package com.jio.siops_ngo.infra.viewholder

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewMedium
import com.jio.siops_ngo.R

class InfraMaintenanceItemViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {

    var txtHistory: TextViewMedium? = null
    var view_hide_id: TextViewMedium? = null
    var view_history_id: TextViewMedium? = null
    var txt_last_days_history: TextViewMedium? = null
//    var txtCount: TextViewBold? = null
//    var imgDropDown: AppCompatImageView? = null
    var historyList: RecyclerView? = null
    var constantIdHeader: ConstraintLayout? = null
//    var img: ImageView? = null
//    var cardView: CardView? = null



    init {
        txtHistory = view!!.findViewById(R.id.view_history_id);
        view_hide_id = view!!.findViewById(R.id.view_hide_id);
        view_history_id = view!!.findViewById(R.id.view_hide_id);
        txt_last_days_history = view!!.findViewById(R.id.view_hide_id);
//        txtCount = view!!.findViewById(R.id.count_data);
//        imgDropDown = view!!.findViewById(R.id.drop_down);
        historyList = view!!.findViewById(R.id.historyList);
        constantIdHeader = view!!.findViewById(R.id.ll);
//        img = view!!.findViewById(R.id.img_useful_links);
//        cardView = view!!.findViewById(R.id.card_img);
    }
}