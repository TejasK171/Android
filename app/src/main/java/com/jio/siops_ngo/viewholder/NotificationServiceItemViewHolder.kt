package com.jio.siops_ngo.viewholder

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewBold
import com.jio.jioinfra.custom.TextViewLight
import com.jio.siops_ngo.R

class NotificationServiceItemViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
    var title: TextViewBold? = null
    var subTitle: TextViewLight? = null
    var txt_time: TextViewLight? = null
//    var container: ConstraintLayout? = null
    var cardView: ConstraintLayout? = null
//    var moreIcon: ImageView? = null


    init {

        title = view!!.findViewById(R.id.outlier_title);
        subTitle = view!!.findViewById(R.id.sub_title);
        txt_time = view!!.findViewById(R.id.txt_time);
//        container = view!!.findViewById(R.id.container_parent);
        cardView = view!!.findViewById(R.id.constId);
//        moreIcon = view!!.findViewById(R.id.more_icon);
    }
}