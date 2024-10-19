package com.jio.siops_ngo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.bean.CommonBean
import com.jio.jioinfra.ui.infraServices.viewholder.DashboardMaintenanceViewholder
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.fragment.DelinquentFragment
import com.jio.siops_ngo.fragment.NgoOpenAlertsFragment
import com.jio.siops_ngo.fragment.NgoOpenAlertsHistoryFragment
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.viewholder.DashboardUsefulLinksViewholder
import java.lang.Exception

class NgoDashboardAdapter(
    private val dashboardDataMap: Map<String, List<Map<String, Any>>>,
    private val usefulLinksList: List<Map<String, Any>>,
    private val activity: MainActivity?,
    val commonBean: CommonBean
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private val LAYOUT_TECHNOLOGY = 0
        private val LAYOUT_TIME_CLOCKING = 1
        private val LAYOUT_USEFUL_LINKS = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null

        var view: View

        if (viewType == LAYOUT_TECHNOLOGY) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.dashboard_maintenance, parent, false)
            viewHolder = DashboardMaintenanceViewholder(view)
        } else if (viewType == LAYOUT_TIME_CLOCKING) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.dashboard_maintenance, parent, false)
            viewHolder = DashboardMaintenanceViewholder(view)
        } else if (viewType == LAYOUT_USEFUL_LINKS) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.useful_links_rv, parent, false)
            viewHolder = DashboardUsefulLinksViewholder(view)
        }
        return viewHolder!!
    }

    override fun getItemCount(): Int {
        return 3;
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (position == LAYOUT_TECHNOLOGY) {
            (holder as DashboardMaintenanceViewholder).imgAlarm!!.visibility = View.GONE
            (holder as DashboardMaintenanceViewholder).alarmeId!!.visibility = View.VISIBLE
            (holder as DashboardMaintenanceViewholder).alarmeId!!.text = "History"
            (holder as DashboardMaintenanceViewholder).txtSitesDwn!!.text ="Open "+
                activity!!.resources.getString(R.string.open_alerts)
            (holder as DashboardMaintenanceViewholder).txtHeader!!.text =
                activity!!.resources.getString(R.string.technology)
            if (dashboardDataMap.containsKey("openAlertsCount")) {
                (holder as DashboardMaintenanceViewholder).txtSitesDownCount!!.text = "(" +
                        dashboardDataMap["openAlertsCount"].toString() + ")"
            }
            if (dashboardDataMap.containsKey("openAlerts")) {
                var openAlertsList = dashboardDataMap["openAlerts"] as List<Map<String, Any>>
                if (openAlertsList != null && openAlertsList.size > 0) {

                    if (hasIndex(0, openAlertsList)) {
                        holder.card1!!.visibility = View.VISIBLE
                        if (openAlertsList.get(0).containsKey("featureName"))
                            (holder as DashboardMaintenanceViewholder).title1!!.text =
                                openAlertsList.get(0)["featureName"] as String

                        if (openAlertsList.get(0).containsKey("outlierCount"))


                            (holder as DashboardMaintenanceViewholder).count1!!.text =
                                openAlertsList.get(0)["outlierCount"]!!.toString()
                        openNgoOpenAlertsFragment(
                            (holder as DashboardMaintenanceViewholder).card1!!,
                            0
                        )
                    } else {
                        holder.card1!!.visibility = View.GONE

                    }

                    if (hasIndex(1, openAlertsList)) {
                        holder.card2!!.visibility = View.VISIBLE
                        if (openAlertsList.get(1).containsKey("featureName"))
                            (holder as DashboardMaintenanceViewholder).title2!!.text =
                                openAlertsList.get(1)["featureName"] as String

                        if (openAlertsList.get(1).containsKey("outlierCount"))
                            (holder as DashboardMaintenanceViewholder).count2!!.text =
                                openAlertsList.get(1)["outlierCount"]!!.toString()

                        openNgoOpenAlertsFragment((holder as DashboardMaintenanceViewholder).card2!!, 1)
                    } else {
                        holder.card2!!.visibility = View.GONE

                    }

                    if (hasIndex(2, openAlertsList)) {
                        holder.card3!!.visibility = View.VISIBLE
                        if (openAlertsList.get(2).containsKey("featureName"))
                            (holder as DashboardMaintenanceViewholder).title3!!.text =
                                openAlertsList.get(2)["featureName"] as String

                        if (openAlertsList.get(2).containsKey("outlierCount"))
                            (holder as DashboardMaintenanceViewholder).count3!!.text =
                                openAlertsList.get(2)["outlierCount"]!!.toString()

                        openNgoOpenAlertsFragment((holder as DashboardMaintenanceViewholder).card3!!, 2)
                    } else {
                        holder.card3!!.visibility = View.GONE

                    }

                }
            }

            (holder as DashboardMaintenanceViewholder).alarmeId!!.setOnClickListener {


                val commonBean = CommonBean()
                var ngoOpenAlertsHistoryFragment = NgoOpenAlertsHistoryFragment.newInstance()
//                ngoOpenAlertsHistoryFragment.setData(commonBean, 0)
                (activity as MainActivity).openFragments(
                    ngoOpenAlertsHistoryFragment,
                    commonBean,
                    true,
                    true
                )
            }


        } else if (position == LAYOUT_TIME_CLOCKING) {

            (holder as DashboardMaintenanceViewholder).imgAlarm!!.visibility = View.GONE
            (holder as DashboardMaintenanceViewholder).alarmeId!!.visibility = View.GONE

            (holder as DashboardMaintenanceViewholder).txtSitesDwn!!.text =
                activity!!.resources.getString(R.string.resource)
            (holder as DashboardMaintenanceViewholder).txtHeader!!.text =
                activity!!.resources.getString(R.string.time_clocking)
            if (dashboardDataMap.containsKey("delinquencyCount")) {
                (holder as DashboardMaintenanceViewholder).txtSitesDownCount!!.text = "(" +
                        dashboardDataMap["delinquencyCount"].toString() + ")"
            }
            if (dashboardDataMap.containsKey("delinquency")) {
                var delinquencyList = dashboardDataMap["delinquency"] as List<Map<String, Any>>
                if (delinquencyList != null && delinquencyList.size > 0) {

                    if (hasIndex(0, delinquencyList)) {
                        holder.card1!!.visibility = View.VISIBLE
                        if (delinquencyList.get(0).containsKey("featureName"))
                            (holder as DashboardMaintenanceViewholder).title1!!.text =
                                delinquencyList.get(0)["featureName"] as String

                        if (delinquencyList.get(0).containsKey("outlierCount"))


                            (holder as DashboardMaintenanceViewholder).count1!!.text =
                                delinquencyList.get(0)["outlierCount"]!!.toString()
                    } else {
                        holder.card1!!.visibility = View.GONE

                    }

                    if (hasIndex(1, delinquencyList)) {
                        holder.card2!!.visibility = View.VISIBLE
                        if (delinquencyList.get(1).containsKey("featureName"))
                            (holder as DashboardMaintenanceViewholder).title2!!.text =
                                delinquencyList.get(1)["featureName"] as String

                        if (delinquencyList.get(1).containsKey("outlierCount"))
                            (holder as DashboardMaintenanceViewholder).count2!!.text =
                                delinquencyList.get(1)["outlierCount"]!!.toString()

                        holder.card2!!.setOnClickListener { openNgoDeliquencyFragment() }
                    } else {
                        holder.card2!!.visibility = View.GONE

                    }

                    if (hasIndex(2, delinquencyList)) {
                        holder.card3!!.visibility = View.VISIBLE
                        if (delinquencyList.get(2).containsKey("featureName"))
                            (holder as DashboardMaintenanceViewholder).title3!!.text =
                                delinquencyList.get(2)["featureName"] as String

                        if (delinquencyList.get(2).containsKey("outlierCount"))
                            (holder as DashboardMaintenanceViewholder).count3!!.text =
                                delinquencyList.get(2)["outlierCount"]!!.toString()
                    } else {
                        holder.card3!!.visibility = View.GONE

                    }

                }
            }
        } else if (position == LAYOUT_USEFUL_LINKS) {

            var adapter = UsefulLinksAdapter(usefulLinksList!!, activity, commonBean!!)
            val gridLayoutManagerUsefulLinks = GridLayoutManager(activity, 3)
            (holder as DashboardUsefulLinksViewholder).usefulLInksRV!!.layoutManager =
                gridLayoutManagerUsefulLinks
            (holder as DashboardUsefulLinksViewholder).usefulLInksRV!!.itemAnimator =
                DefaultItemAnimator()

            (holder as DashboardUsefulLinksViewholder).usefulLInksRV!!.adapter = adapter
        }

    }

    override fun getItemViewType(position: Int): Int {
        when (position) {
            LAYOUT_TECHNOLOGY -> {
                return LAYOUT_TECHNOLOGY
            }

            LAYOUT_TIME_CLOCKING -> {
                return LAYOUT_TIME_CLOCKING
            }

            LAYOUT_USEFUL_LINKS -> {
                return LAYOUT_USEFUL_LINKS
            }
        }
        return 0
    }

    fun hasIndex(index: Int, dataList: List<Map<String, Any>>): Boolean {
        if (index < dataList!!.size)
            return true
        else
            return false

    }

    fun openNgoOpenAlertsFragment(view: ConstraintLayout, selection: Int) {
        view!!.setOnClickListener {
            try {
                val commonBean = CommonBean()
                commonBean.`object` = dashboardDataMap
                var ngoOpenAlertsFragment = NgoOpenAlertsFragment.newInstance()
                ngoOpenAlertsFragment.setData(commonBean, selection)
                (activity as MainActivity).openFragments(
                    ngoOpenAlertsFragment,
                    commonBean,
                    true,
                    true
                )
            } catch (e: Exception) {
                MyExceptionHandler.handle(e)
            }
        }
    }


    fun openNgoDeliquencyFragment() {
            try {
                val commonBean = CommonBean()
                //  commonBean.`object` = dashboardDataMap
                var delinquentFragment = DelinquentFragment.newInstance()
                delinquentFragment.setData(commonBean)
                (activity as MainActivity).openFragments(
                    delinquentFragment,
                    commonBean,
                    true,true)
            } catch (e: Exception) {
                MyExceptionHandler.handle(e)
            }
    }

}