package com.jio.siops_ngo.viewholder

import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewBold
import com.jio.jioinfra.custom.TextViewMedium
import com.jio.siops_ngo.R

class InfraAlarmsReasonViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {

    var txtHistory: TextViewBold? = null
    var view_hide_id: TextViewMedium? = null
    var view_history_id: TextViewMedium? = null
    var txt_last_days_history: TextViewMedium? = null
//    var txtCount: TextViewBold? = null
//    var imgDropDown: AppCompatImageView? = null
    var historyList: RecyclerView? = null
//    var constantIdHeader: ConstraintLayout? = null
    var ll: LinearLayout? = null
//    var img: ImageView? = null
//    var cardView: CardView? = null



    init {
        txtHistory = view!!.findViewById(R.id.view_history_id);
        view_hide_id = view!!.findViewById(R.id.view_hide_id);
        view_history_id = view!!.findViewById(R.id.view_hide_id);
        txt_last_days_history = view!!.findViewById(R.id.view_hide_id);
        ll = view!!.findViewById(R.id.ll);
//        txtCount = view!!.findViewById(R.id.count_data);
//        imgDropDown = view!!.findViewById(R.id.drop_down);
        historyList = view!!.findViewById(R.id.historyList);
//        constantIdHeader = view!!.findViewById(R.id.constantIdHeader);
//        img = view!!.findViewById(R.id.img_useful_links);
//        cardView = view!!.findViewById(R.id.card_img);
    }
}