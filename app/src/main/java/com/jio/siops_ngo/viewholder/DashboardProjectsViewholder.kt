package com.jio.jioinfra.ui.infraServices.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewLight
import com.jio.jioinfra.custom.TextViewMedium
import com.jio.siops_ngo.R

class DashboardProjectsViewholder (view: View?) : RecyclerView.ViewHolder(view!!) {

    var txtProjectsTitle1: TextViewMedium? = null
    var txtProjectsCount1: TextViewLight? = null
    var txtProjectsTitle2: TextViewMedium? = null
    var txtProjectsCount2: TextViewLight? = null
    var txtProjectsTitle3: TextViewMedium? = null
    var txtProjectsCount3: TextViewLight? = null




    init {
        txtProjectsTitle1 = view!!.findViewById(R.id.txt_project_title1);
        txtProjectsCount1 = view!!.findViewById(R.id.txt_project_count1);
        txtProjectsTitle2 = view!!.findViewById(R.id.txt_project_title2);
        txtProjectsCount2 = view!!.findViewById(R.id.txt_project_count2);
        txtProjectsTitle3 = view!!.findViewById(R.id.txt_project_title3);
        txtProjectsCount3 = view!!.findViewById(R.id.txt_project_count3);

    }
}