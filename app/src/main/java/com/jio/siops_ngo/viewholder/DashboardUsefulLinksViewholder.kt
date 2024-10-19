package com.jio.siops_ngo.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewMedium
import com.jio.siops_ngo.R

class DashboardUsefulLinksViewholder (view: View?) : RecyclerView.ViewHolder(view!!) {

    var txtTitle: TextViewMedium? = null
    var usefulLInksRV: RecyclerView? = null




    init {
        txtTitle = view!!.findViewById(R.id.txt_useful_links_name);
        usefulLInksRV = view!!.findViewById(R.id.useful_links_rv);

    }
}