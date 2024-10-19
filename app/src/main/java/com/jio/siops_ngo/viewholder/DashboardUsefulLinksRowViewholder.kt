package com.jio.jioinfra.ui.infraServices.viewholder

import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewMedium
import com.jio.siops_ngo.R

class DashboardUsefulLinksRowViewholder (view: View?) : RecyclerView.ViewHolder(view!!) {

    var txtTitle: TextViewMedium? = null
    var img: ImageView? = null
    /*var cardView: CardView? = null
    var cnstrntL: ConstraintLayout? = null*/
    var cnstrntLMain: ConstraintLayout? = null



    init {
        txtTitle = view!!.findViewById(R.id.txt_useful_links_name);
        img = view!!.findViewById(R.id.img_useful_links);
        /*cardView = view!!.findViewById(R.id.card_img);
        cnstrntL = view!!.findViewById(R.id.cnstrntL_img);*/
        cnstrntLMain = view!!.findViewById(R.id.cnstrntL_main);
    }
}