package com.jio.jioinfra.ui.infraServices.viewholder

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewBold
import com.jio.jioinfra.custom.TextViewLight
import com.jio.jioinfra.custom.TextViewMedium
import com.jio.siops_ngo.R

class DashboardPerformanceViewholder (view: View?) : RecyclerView.ViewHolder(view!!) {

    var txtCellsImpacted: TextViewLight? = null
    var txtCellsImpactedCount: TextViewBold? = null
    var txtDead: TextViewMedium? = null
    var txtDeadCount: TextViewLight? = null
    var txtDateTime: TextViewBold? = null
//    var txtMainCount: TextViewLight? = null
    var txtICUCells: TextViewMedium? = null
    var txtICUCellsCount: TextViewLight? = null
    var txtHospCells: TextViewMedium? = null
    var txtHospCellsCount: TextViewLight? = null
    var txtViewDetails: TextViewMedium? = null
    var cnstrntHiddenLayout: ConstraintLayout? = null
    var txtDCR: TextViewMedium? = null
    var txtDcrCount: TextViewLight? = null
    var txtDeadCountUnit: TextViewMedium? = null
    var txtICUCellsUnit: TextViewMedium? = null
    var txtHospCellsCountUnit: TextViewMedium? = null
    var titleThroughput: TextViewLight? = null
    var txtComplaints: TextViewLight? = null
    var txtComplaintsCount: TextViewLight? = null
    var titleThroughputCount: TextViewMedium? = null
    var titleThroughputCountUnit: TextViewMedium? = null
    var titleComplaintsCountUnit: TextViewMedium? = null
    var titleComplaintsTitle: TextViewMedium? = null


    init {
        txtCellsImpacted = view!!.findViewById(R.id.txt_cell_impacted);
//        txtMainCount = view!!.findViewById(R.id.main_count);
        txtCellsImpactedCount = view!!.findViewById(R.id.txt_impacted_cells_count);
        txtDead = view!!.findViewById(R.id.txt_dead);
        txtDeadCount = view!!.findViewById(R.id.txt_dead_count);
        txtICUCells = view!!.findViewById(R.id.txt_icu_cells);
        txtICUCellsCount = view!!.findViewById(R.id.txt_icu_cells_count);
        txtHospCells = view!!.findViewById(R.id.txt_hosp_cells);
        txtHospCellsCount = view!!.findViewById(R.id.txt_hosp_cells_count);
        txtViewDetails = view!!.findViewById(R.id.performance_view_details);
        cnstrntHiddenLayout = view!!.findViewById(R.id.cnstrntL_performance_hidden_layout);
        txtDCR = view!!.findViewById(R.id.action_name);
        txtDcrCount = view!!.findViewById(R.id.main_count);
        txtDeadCountUnit = view!!.findViewById(R.id.txt_dead_count_unit);
        txtICUCellsUnit = view!!.findViewById(R.id.txt_icu_cells_unit);
        txtHospCellsCountUnit = view!!.findViewById(R.id.txt_hosp_cells_count_unit);
        titleThroughput = view!!.findViewById(R.id.title1_throughput);
        titleThroughputCount = view!!.findViewById(R.id.count1_throughput);
        titleThroughputCountUnit = view!!.findViewById(R.id.txt_throughput_unit);
        txtDateTime = view!!.findViewById(R.id.txt_datetime);
        txtComplaints = view!!.findViewById(R.id.txt_complaints);
        txtComplaintsCount = view!!.findViewById(R.id.main_count1);
        titleComplaintsCountUnit = view!!.findViewById(R.id.txt_count_unit);
        titleComplaintsTitle = view!!.findViewById(R.id.action_name1);


    }
}