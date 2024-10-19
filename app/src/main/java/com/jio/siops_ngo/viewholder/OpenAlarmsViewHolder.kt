package com.jio.siops_ngo.viewholder

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewBold
import com.jio.jioinfra.custom.TextViewLight
import com.jio.siops_ngo.R

class OpenAlarmsViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {

    var txtTitle: TextViewLight? = null
    var txtCount: TextViewBold? = null
    var imgDropDown: AppCompatImageView? = null
//    var up_down: AppCompatImageView? = null
//    var detailsList: RecyclerView? = null
    var cnstrntL_background: ConstraintLayout? = null
//    var img: ImageView? = null
//    var cardView: CardView? = null



    init {
        txtTitle = view!!.findViewById(R.id.txt_dashboard_subtitle_1);
        txtCount = view!!.findViewById(R.id.count_data);
        imgDropDown = view!!.findViewById(R.id.drop_down);
//        up_down = view!!.findViewById(R.id.up_down);
//        detailsList = view!!.findViewById(R.id.detailsList);
        cnstrntL_background = view!!.findViewById(R.id.cnstrntL_header);
//        img = view!!.findViewById(R.id.img_useful_links);
//        cardView = view!!.findViewById(R.id.card_img);
    }
}