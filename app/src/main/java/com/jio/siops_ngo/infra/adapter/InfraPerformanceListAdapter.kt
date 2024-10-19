package com.jio.jioinfra.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.bean.CommonBean

import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.infra.fragment.InfraPerformanceCalendarFragment
import com.jio.siops_ngo.infra.viewholder.InfraPerformanceItemViewHolder
import java.lang.Exception

class InfraPerformanceListAdapter(private val activity: MainActivity?) : RecyclerView.Adapter<InfraPerformanceItemViewHolder>() {

    var boolean: Boolean = true

    override fun onCreateViewHolder(parent: ViewGroup, viwType: Int): InfraPerformanceItemViewHolder {
        var view: View?


        view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_dead_cells_item, parent, false)

        return InfraPerformanceItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: InfraPerformanceItemViewHolder, position: Int) {
        try {

            holder.cnstrntL850Mhz!!.setOnClickListener {
                holder.cnstrntL850MhzData!!.visibility = View.VISIBLE

                holder.txtLast30days!!.setOnClickListener {  }
                var infraPerformanceCalendarFragment = InfraPerformanceCalendarFragment.newInstance()
                var commonBean = CommonBean()
                activity!!.openFragments(infraPerformanceCalendarFragment!!,commonBean, true, true)
                /*var adapter = InfraPerformanceCalendarItemAdapter( activity as MainActivity?)
                holder!!.rv850Mhz!!.layoutManager = LinearLayoutManager(activity as MainActivity?)
                holder!!.rv850Mhz!!.itemAnimator = DefaultItemAnimator()
                holder!!.rv850Mhz!!.adapter = adapter*/



            }


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return 5
    }

}