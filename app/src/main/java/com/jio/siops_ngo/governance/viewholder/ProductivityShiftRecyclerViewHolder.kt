package com.jio.siops_ngo.governance.viewholder

import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewLight
import com.jio.jioinfra.custom.TextViewMedium
import com.jio.siops_ngo.R

class ProductivityShiftRecyclerViewHolder (view: View?) : RecyclerView.ViewHolder(view!!) {
    var txtDetails: TextViewMedium? = null
    var txtHide: TextViewMedium? = null
    var txtShiftHeader: TextViewMedium? = null
    var constHide: ConstraintLayout? = null
    var live_moring_list: RecyclerView? = null
    //var txtItem: TextViewLight? = null



    init {
        txtDetails = view!!.findViewById(R.id.txt_details);
        txtHide = view!!.findViewById(R.id.txt_hide);
        txtShiftHeader = view!!.findViewById(R.id.txt_shift_header);
        live_moring_list = view!!.findViewById(R.id.live_moring_list);

       // constHide = view!!.findViewById(R.id.const_show);
        //    txtItem = view!!.findViewById(R.id.txt_item);

    }
}