package com.jio.siops_ngo.infra.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewBold
import com.jio.siops_ngo.R

class DcbTowerSitesRvViewholder (view: View?) : RecyclerView.ViewHolder(view!!) {

    var txtCount: TextViewBold? = null
    var dcbSitesListRv: RecyclerView? = null




    init {
        txtCount = view!!.findViewById(R.id.txt_tower_count);
        dcbSitesListRv = view!!.findViewById(R.id.dcb_list_rv);

    }
}