package com.jio.jioinfra.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.infra.viewholder.InfraPerformanceCalendarItemViewHolder
import java.lang.Exception
import java.util.*


class InfraPerformanceCalendarItemAdapter(private val activity: MainActivity?) : RecyclerView.Adapter<InfraPerformanceCalendarItemViewHolder>() {

    var boolean: Boolean = true

    override fun onCreateViewHolder(parent: ViewGroup, viwType: Int): InfraPerformanceCalendarItemViewHolder {
        var view: View?


        view = LayoutInflater.from(parent.context)
            .inflate(R.layout.infra_performance_calendar_item, parent, false)

        return InfraPerformanceCalendarItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: InfraPerformanceCalendarItemViewHolder, position: Int) {
        try {


            var calendar = Calendar.getInstance()
            calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE))
            val endOfMonth = calendar.getTimeInMillis()
            calendar = Calendar.getInstance()
            calendar.set(Calendar.DATE, 1)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            val startOfMonth = calendar.getTimeInMillis()
            holder.calendarView!!.setMaxDate(endOfMonth)
            holder.calendarView!!.setMinDate(startOfMonth)


//            holder.calendarView!!.minDate = ""


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return 1
    }

}