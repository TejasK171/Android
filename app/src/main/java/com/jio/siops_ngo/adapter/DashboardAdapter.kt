package com.jio.siops_ngo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.bean.CommonBean
import com.jio.jioinfra.ui.infraServices.viewholder.DashboardMaintenanceViewholder
import com.jio.jioinfra.ui.infraServices.viewholder.DashboardPerformanceViewholder
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.fragment.InfraOpenAlertsFragment
import com.jio.siops_ngo.infra.fragment.InfraMaintenanceFragment
import com.jio.siops_ngo.viewholder.DashboardUsefulLinksViewholder

class DashboardAdapter(
    private val dashboardDataMap: Map<String, List<Map<String, Any>>>,
    private val usefulLinksList: List<Map<String, Any>>,
    private val activity: MainActivity?,
    val commonBean: CommonBean
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private val LAYOUT_MAINTENANCE = 0
        private val LAYOUT_PERFORMANCE = 1
//        private val LAYOUT_PROJECTS = 2
        private val LAYOUT_USEFUL_LINKS = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null

        var view: View

        if (viewType == LAYOUT_MAINTENANCE) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.dashboard_maintenance, parent, false)
            viewHolder = DashboardMaintenanceViewholder(view)
        } else if (viewType == LAYOUT_PERFORMANCE) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.dashboard_performance, parent, false)
            viewHolder = DashboardPerformanceViewholder(view)
        }

        /*else if (viewType == LAYOUT_PROJECTS) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.dashboard_projects, parent, false)
            viewHolder = DashboardProjectsViewholder(view)
        }*/

        else if (viewType == LAYOUT_USEFUL_LINKS) {
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

        if (position == LAYOUT_MAINTENANCE) {
            if (dashboardDataMap.containsKey("sitesDownCount")) {
                (holder as DashboardMaintenanceViewholder).txtSitesDownCount!!.text ="("+
                        dashboardDataMap["sitesDownCount"].toString()+")"
            }
            if (dashboardDataMap.containsKey("sitesDown")) {
                var sitesDownList = dashboardDataMap["sitesDown"] as List<Map<String, Any>>
                if (sitesDownList != null && sitesDownList.size > 0) {

                    if (hasIndex(0, sitesDownList)) {
                        if (sitesDownList.get(0).containsKey("featureName"))
                            (holder as DashboardMaintenanceViewholder).title1!!.text =
                                sitesDownList.get(0)["featureName"] as String

                        if (sitesDownList.get(0).containsKey("outlierCount"))


                            (holder as DashboardMaintenanceViewholder).count1!!.text = sitesDownList.get(0)["outlierCount"]!!.toString()

                        (holder as DashboardMaintenanceViewholder).card1!!.setOnClickListener {

                           opneMaintainceItemFragment(sitesDownList.get(0)["featureId"] as String, sitesDownList.get(0)["featureName"] as String, sitesDownList.get(0)["outlierCount"].toString())
                        }
                    }

                    if (hasIndex(1, sitesDownList)) {
                        if (sitesDownList.get(1).containsKey("featureName"))
                            (holder as DashboardMaintenanceViewholder).title2!!.text =
                                sitesDownList.get(1)["featureName"] as String

                        if (sitesDownList.get(1).containsKey("outlierCount"))
                            (holder as DashboardMaintenanceViewholder).count2!!.text =
                                sitesDownList.get(1)["outlierCount"] !!.toString()


                        (holder as DashboardMaintenanceViewholder).card2!!.setOnClickListener {

                            opneMaintainceItemFragment(sitesDownList.get(1)["featureId"] as String, sitesDownList.get(1)["featureName"] as String, sitesDownList.get(1)["outlierCount"].toString())
                        }
                    }

                    if (hasIndex(2, sitesDownList)) {
                        if (sitesDownList.get(2).containsKey("featureName"))
                            (holder as DashboardMaintenanceViewholder).title3!!.text =
                                sitesDownList.get(2)["featureName"] as String

                        if (sitesDownList.get(2).containsKey("outlierCount"))
                            (holder as DashboardMaintenanceViewholder).count3!!.text =
                                sitesDownList.get(2)["outlierCount"]!!.toString()


                        (holder as DashboardMaintenanceViewholder).card3!!.setOnClickListener {

                            opneMaintainceItemFragment(sitesDownList.get(2)["featureId"] as String, sitesDownList.get(2)["featureName"] as String, sitesDownList.get(2)["outlierCount"].toString())
                        }
                    }

                }
            }

            if(dashboardDataMap.containsKey("alarmCount")){
                (holder as DashboardMaintenanceViewholder).alarmeId!!.text= "Alarms "+"("+dashboardDataMap.get("alarmCount").toString()+")"
            }

            if(dashboardDataMap.containsKey("siteDownLastUpdate")){
                (holder as DashboardMaintenanceViewholder).txtDateTime!!.text= "("+dashboardDataMap.get("siteDownLastUpdate").toString()+")"
            }

            (holder as DashboardMaintenanceViewholder).alarmeId!!.setOnClickListener {

                val commonBean = CommonBean()
                //   commonBean.`object` = listData
                var infraOpenAlertsFragment = InfraOpenAlertsFragment.newInstance()
                infraOpenAlertsFragment.setData(commonBean)
                (activity as MainActivity).openFragments(infraOpenAlertsFragment, commonBean, true, true)

            }

            (holder as DashboardMaintenanceViewholder).imgAlarm!!.setOnClickListener {

                val commonBean = CommonBean()
                //   commonBean.`object` = listData
                var infraOpenAlertsFragment = InfraOpenAlertsFragment.newInstance()
                infraOpenAlertsFragment.setData(commonBean)
                (activity as MainActivity).openFragments(infraOpenAlertsFragment, commonBean, true, true)

            }

            /*(holder as DashboardMaintenanceViewholder).alarmeId!!.setOnClickListener {


                val commonBean = CommonBean()
             //   commonBean.`object` = listData
                var dashboardFragment = NgoOpenAlertsFragment.newInstance()
                dashboardFragment.setData(commonBean)
                (activity as MainActivity).openFragments(dashboardFragment, commonBean, false, true)

            }*/

        }else if (position == LAYOUT_PERFORMANCE) {
            if (dashboardDataMap.containsKey("cellImpactedCount")) {
                (holder as DashboardPerformanceViewholder).txtCellsImpactedCount!!.text ="("+
                    dashboardDataMap["cellImpactedCount"].toString()+")"
            }

            if(dashboardDataMap.containsKey("performanceLastUpdate")){
                (holder as DashboardPerformanceViewholder).txtDateTime!!.text= "("+dashboardDataMap.get("performanceLastUpdate").toString()+")"
            }
            if (dashboardDataMap.containsKey("cellImpacted")) {
                var cellImpactedList = dashboardDataMap["cellImpacted"] as List<Map<String, Any>>
                if (hasIndex(0, cellImpactedList)) {
                    if (cellImpactedList.get(0).containsKey("featureName"))
                        (holder as DashboardPerformanceViewholder).txtDead!!.text =
                            cellImpactedList.get(0)["featureName"] as String

                    if (cellImpactedList.get(0).containsKey("unit") && cellImpactedList.get(0).get("unit")!=null)
                        (holder as DashboardPerformanceViewholder).txtDeadCountUnit!!.text =
                            cellImpactedList.get(0)["unit"] as String

                    if (cellImpactedList.get(0).containsKey("formattedCount"))
                        (holder as DashboardPerformanceViewholder).txtDeadCount!!.text =
                            cellImpactedList.get(0)["formattedCount"] !!.toString()
                }

                if (hasIndex(1, cellImpactedList)) {
                    if (cellImpactedList.get(1).containsKey("featureName"))
                        (holder as DashboardPerformanceViewholder).txtICUCells!!.text =
                            cellImpactedList.get(1)["featureName"] as String

                    if (cellImpactedList.get(1).containsKey("unit") && cellImpactedList.get(1).get("unit")!=null)
                        (holder as DashboardPerformanceViewholder).txtICUCellsUnit!!.text =
                            cellImpactedList.get(1)["unit"] as String

                    if (cellImpactedList.get(1).containsKey("formattedCount"))
                        (holder as DashboardPerformanceViewholder).txtICUCellsCount!!.text =
                            cellImpactedList.get(1)["formattedCount"]!!.toString()
                }

                if (hasIndex(2, cellImpactedList)) {
                    if (cellImpactedList.get(2).containsKey("featureName"))
                        (holder as DashboardPerformanceViewholder).txtHospCells!!.text =
                            cellImpactedList.get(2)["featureName"] as String

                    if (cellImpactedList.get(2).containsKey("unit") && cellImpactedList.get(2).get("unit")!=null)
                        (holder as DashboardPerformanceViewholder).txtHospCellsCountUnit!!.text =
                            cellImpactedList.get(2)["unit"] as String

                    if (cellImpactedList.get(2).containsKey("formattedCount"))
                        (holder as DashboardPerformanceViewholder).txtHospCellsCount!!.text =
                            cellImpactedList.get(2)["formattedCount"]!!.toString()
                }




            }

            if (dashboardDataMap.containsKey("voice")) {

                if (dashboardDataMap["voice"] != null) {
                var voiceMap = dashboardDataMap["voice"] as HashMap<String, Any>

                    if (voiceMap.containsKey("featureName")) {
                        (holder as DashboardPerformanceViewholder).txtDCR!!.text =
                            voiceMap["featureName"] as String
                    }
                    if (voiceMap.containsKey("outlierCount")) {
                        (holder as DashboardPerformanceViewholder).txtDcrCount!!.text =
                            voiceMap["outlierCount"].toString() + " %"
                    }
                }
            }


            if (dashboardDataMap.containsKey("throughput")) {

                if (dashboardDataMap["throughput"] != null) {
                    var throughputMap = dashboardDataMap["throughput"] as List<Map<String, Any>>

                    if (hasIndex(0, throughputMap)) {
                        if (throughputMap[0].containsKey("featureName")) {
                            (holder as DashboardPerformanceViewholder).titleThroughputCount!!.text =
                                throughputMap[0]["featureName"] as String

                            (holder as DashboardPerformanceViewholder).txtComplaints!!.text =
                                throughputMap[0]["featureName"] as String
                        }
                        if (throughputMap[0].containsKey("outlierCount")) {
                            (holder as DashboardPerformanceViewholder).titleThroughput!!.text =
                                throughputMap[0]["outlierCount"].toString()
                            (holder as DashboardPerformanceViewholder).txtComplaintsCount!!.text =
                                throughputMap[0]["outlierCount"].toString()
                        }
                        (holder as DashboardPerformanceViewholder).titleThroughputCountUnit!!.text =
                            " Mbps"

                        (holder as DashboardPerformanceViewholder).titleComplaintsCountUnit!!.text =
                            " Mbps"
                    }
                }
            }




            (holder as DashboardPerformanceViewholder).txtViewDetails!!.setOnClickListener {

                if((holder as DashboardPerformanceViewholder).cnstrntHiddenLayout!!.visibility == View.VISIBLE){

                    (holder as DashboardPerformanceViewholder).cnstrntHiddenLayout!!.visibility = View.GONE
                    (holder as DashboardPerformanceViewholder).txtViewDetails!!.text = activity!!.resources.getString(R.string.view_details)
                    (holder as DashboardPerformanceViewholder).txtViewDetails!!.setBackgroundDrawable(activity!!.resources.getDrawable(R.drawable.grey_rounded_corner_bg))
                    (holder as DashboardPerformanceViewholder).txtViewDetails!!.setTextColor(activity!!.resources.getColor(R.color.black))

                }else{
                    (holder as DashboardPerformanceViewholder).cnstrntHiddenLayout!!.visibility = View.VISIBLE
                    (holder as DashboardPerformanceViewholder).txtViewDetails!!.text = activity!!.resources.getString(R.string.hide_details)
                    (holder as DashboardPerformanceViewholder).txtViewDetails!!.setBackgroundDrawable(activity!!.resources.getDrawable(R.drawable.blue_rounded_corner_bg))
                    (holder as DashboardPerformanceViewholder).txtViewDetails!!.setTextColor(activity!!.resources.getColor(R.color.white))
                }
            }

        }

        else if (position == LAYOUT_USEFUL_LINKS) {

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
            LAYOUT_MAINTENANCE -> {
                return LAYOUT_MAINTENANCE
            }

            LAYOUT_PERFORMANCE -> {
                return LAYOUT_PERFORMANCE
            }

            /*LAYOUT_PROJECTS -> {
                return LAYOUT_PROJECTS
            }*/

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

    fun opneMaintainceItemFragment(featureId:String,featureName:String,featureCount:String){
        val commonBean = CommonBean()

        var infraMaintenanceFragment = InfraMaintenanceFragment.newInstance()
        infraMaintenanceFragment.setData(commonBean,featureId,featureName,featureCount)
        (activity as MainActivity).openFragments(infraMaintenanceFragment, commonBean, true, true)
    }

}