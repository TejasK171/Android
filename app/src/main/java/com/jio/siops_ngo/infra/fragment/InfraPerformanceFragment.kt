package com.jio.siops_ngo.infra.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.jio.jioinfra.ui.dashboard.adapter.InfraPerformanceListAdapter
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.databinding.InfraMaintenanceInnerFragmentBinding

class InfraPerformanceFragment : androidx.fragment.app.Fragment() {
    var mBinding: InfraMaintenanceInnerFragmentBinding?= null
    companion object {
        fun newInstance() = InfraPerformanceFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.infra_maintenance_inner_fragment, container, false)

        return mBinding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        var adapter = InfraPerformanceListAdapter( activity as MainActivity?)
        mBinding!!.rvMaintenanceList.layoutManager = LinearLayoutManager(activity as MainActivity?)
        mBinding!!.rvMaintenanceList.itemAnimator = DefaultItemAnimator()
        mBinding!!.rvMaintenanceList.adapter = adapter
    }



}




