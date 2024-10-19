package com.jio.siops_ngo.infra.viewholder

import android.view.View
import android.widget.CalendarView
import androidx.recyclerview.widget.RecyclerView
import com.jio.siops_ngo.R
import com.jio.siops_ngo.eventcalenderlibrary.CalenderEvent

class InfraPerformanceCalendarItemViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {

    var calendarObj: CalenderEvent? = null
    var calendarView: CalendarView? = null


    init {
        calendarView = view!!.findViewById(R.id.calendar_view);

    }
}