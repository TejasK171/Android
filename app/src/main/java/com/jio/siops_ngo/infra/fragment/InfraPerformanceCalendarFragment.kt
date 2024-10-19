package com.jio.siops_ngo.infra.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.jio.siops_ngo.R
import com.jio.siops_ngo.databinding.DeadCellsItemCalendarBinding
import com.jio.siops_ngo.databinding.InfraMaintenanceInnerFragmentBinding

class InfraPerformanceCalendarFragment : androidx.fragment.app.Fragment() {
    var mBinding: DeadCellsItemCalendarBinding?= null
    companion object {
        fun newInstance() = InfraPerformanceCalendarFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.dead_cells_item_calendar, container, false)

        return mBinding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        /*var adapter = InfraPerformanceListAdapter( activity as MainActivity?)
        mBinding!!.rvMaintenanceList.layoutManager = LinearLayoutManager(activity as MainActivity?)
        mBinding!!.rvMaintenanceList.itemAnimator = DefaultItemAnimator()
        mBinding!!.rvMaintenanceList.adapter = adapter*/
    }



}




