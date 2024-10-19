package com.jio.siops_ngo.governance.viewholder

import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewLight
import com.jio.jioinfra.custom.TextViewMedium
import com.jio.siops_ngo.R

class ResourceProductivityItemViewHolder (view: View?) : RecyclerView.ViewHolder(view!!) {
    var txtHeader: TextViewMedium? = null
    var txtItem: TextViewLight? = null
    var cnstrntlItem: ConstraintLayout? = null
    var cnstrntlHeader: ConstraintLayout? = null



    init {
        txtHeader = view!!.findViewById(R.id.txt_item_header);
        txtItem = view!!.findViewById(R.id.txt_item);
        cnstrntlItem = view!!.findViewById(R.id.cnstrntL_item)
        cnstrntlHeader = view!!.findViewById(R.id.cnstrntL_header)
    }
}