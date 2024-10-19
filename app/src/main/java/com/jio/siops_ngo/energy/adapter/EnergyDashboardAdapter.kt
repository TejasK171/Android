package com.jio.siops_ngo.infra.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.bean.CommonBean
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.infra.fragment.SiteEnergyFragment
import com.jio.siops_ngo.infra.viewholder.DashboardEnergyViewholder
import com.jio.siops_ngo.utilities.Utils
import com.jio.siops_ngo.utilities.Utils.Companion.getBoldString


class EnergyDashboardAdapter(

    private val activity: MainActivity?,
    private val sites: ArrayList<Map<String, Any>>?,
    private val sitesLoad: ArrayList<Map<String, Any>>?,
    private val power: ArrayList<Map<String, Any>>?,
    private val co2: ArrayList<Map<String, Any>>?,
    private val high: ArrayList<Map<String, Any>>?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private val LAYOUT_SITES = 0
        private val LAYOUT_SITE_LOAD = 1
        private val LAYOUT_POWER = 2
        private val LAYOUT_CO2 = 3
        private val LAYOUT_HI_EB = 4
        private val LAYOUT_USEFUL_LINKS = 5
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null

        var view: View

        if (viewType == LAYOUT_SITES) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.dashboard_energy_sites, parent, false)
            viewHolder = DashboardEnergyViewholder(view)
        } else if (viewType == LAYOUT_SITE_LOAD) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.dashboard_energy_sites_load, parent, false)
            viewHolder = DashboardEnergyViewholder(view)
        } else if (viewType == LAYOUT_POWER) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.dashboard_energy_power, parent, false)
            viewHolder = DashboardEnergyViewholder(view)
        } else if (viewType == LAYOUT_CO2) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.dashboard_energy_co_two, parent, false)
            viewHolder = DashboardEnergyViewholder(view)
        } else if (viewType == LAYOUT_HI_EB) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.dashboard_energy_hps_eb_bils, parent, false)
            viewHolder = DashboardEnergyViewholder(view)
        }


        /*else if (viewType == LAYOUT_PROJECTS) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.dashboard_projects, parent, false)
            viewHolder = DashboardProjectsViewholder(view)
        }*/

//        else if (viewType == LAYOUT_USEFUL_LINKS) {
//            view = LayoutInflater.from(parent.context)
//                .inflate(R.layout.useful_links_rv, parent, false)
//            viewHolder = DashboardUsefulLinksViewholder(view)
//        }
        return viewHolder!!
    }

    override fun getItemCount(): Int {
        return 5;
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (position == LAYOUT_SITES) {
            if (Utils.hasIndex(0, sites!!)) {

                if (sites.get(0).containsKey("featureName") && sites.get(0)["featureName"] != null) {

                    (holder as DashboardEnergyViewholder).site_title1!!.text =
                        sites.get(0)["featureName"].toString()
                }
                if (sites.get(0).containsKey("outlierCount") && sites.get(0)["outlierCount"] != null) {
                    if (sites.get(0)["outlierCount"].toString().equals("0")) {
                        (holder as DashboardEnergyViewholder).site_count1!!.text =
                            "-"
                    } else {
                        (holder as DashboardEnergyViewholder).site_count1!!.text =
                            sites.get(0)["outlierCount"].toString()
                    }
                }
                (holder as DashboardEnergyViewholder).card1!!.setOnClickListener {
                    // opneEnergySiteItemFragment()
                }

            }


            if (Utils.hasIndex(1, sites!!)) {

                if (sites.get(1).containsKey("featureName") && sites.get(1)["featureName"] != null) {
                    (holder as DashboardEnergyViewholder).site_title2!!.text =
                        sites.get(1)["featureName"].toString()
                }
                if (sites.get(1).containsKey("outlierCount") && sites.get(1)["outlierCount"] != null) {


                    if (sites.get(1)["outlierCount"].toString().equals("0")) {
                        (holder as DashboardEnergyViewholder).site_count2!!.text = "-"
                    } else {
                        (holder as DashboardEnergyViewholder).site_count2!!.text =
                            sites.get(1)["outlierCount"].toString()
                    }

                }

                (holder as DashboardEnergyViewholder).card2!!.setOnClickListener {
                    //  opneEnergySiteItemFragment()
                }

            }


            if (Utils.hasIndex(2, sites!!)) {

                if (sites.get(2).containsKey("featureName") && sites.get(2)["featureName"] != null) {
                    (holder as DashboardEnergyViewholder).site_title3!!.text =
                        sites.get(2)["featureName"].toString()
                }
                if (sites.get(2).containsKey("outlierCount") && sites.get(2)["outlierCount"] != null) {


                    if (sites.get(2)["outlierCount"].toString().equals("0")) {
                        (holder as DashboardEnergyViewholder).site_count3!!.text =
                            "-"
                    } else {
                        (holder as DashboardEnergyViewholder).site_count3!!.text =
                            sites.get(2)["outlierCount"].toString()
                    }
                }


            }
            (holder as DashboardEnergyViewholder).card3!!.setOnClickListener {
                // opneEnergySiteItemFragment()
            }
        }
        if (position == LAYOUT_SITE_LOAD) {
            if (Utils.hasIndex(0, sitesLoad!!)) {


                if (sitesLoad.get(0).containsKey("featureName") && sitesLoad.get(0)["featureName"] != null) {
                    (holder as DashboardEnergyViewholder).site__load_title1!!.text =
                        sitesLoad.get(0)["featureName"].toString()
                }

                if (sitesLoad.get(0).containsKey("outlierCount") && sitesLoad.get(0)["outlierCount"] != null) {

                    (holder as DashboardEnergyViewholder).site_load_count1!!.text =
                        sitesLoad.get(0)["outlierCount"].toString()
                }
                if (sitesLoad.get(0).containsKey("unit") && sitesLoad.get(0)["unit"] != null) {
                    (holder as DashboardEnergyViewholder).txtSiteLoadUnit1!!.text =
                        sitesLoad.get(0)["unit"].toString()
                }

            }



            if (Utils.hasIndex(1, sitesLoad!!)) {


                if (sitesLoad.get(1).containsKey("featureName") && sitesLoad.get(1)["featureName"] != null) {
                    (holder as DashboardEnergyViewholder).site__load_title2!!.text =
                        sitesLoad.get(1)["featureName"].toString()
                }

                if (sitesLoad.get(1).containsKey("outlierCount")  && sitesLoad.get(0)["outlierCount"] != null) {


                    (holder as DashboardEnergyViewholder).site_load_count2!!.text =
                            sitesLoad.get(1)["outlierCount"].toString()
                }

                if (sitesLoad.get(1).containsKey("unit") && sitesLoad.get(1)["unit"] != null) {
                    (holder as DashboardEnergyViewholder).txtSiteLoadUnit2!!.text =
                        sitesLoad.get(1)["unit"].toString()
                }

            }



            if (Utils.hasIndex(2, sitesLoad!!)) {


                if (sitesLoad.get(2).containsKey("featureName") && sitesLoad.get(2)["featureName"] != null) {
                    (holder as DashboardEnergyViewholder).site__load_title3!!.text =
                        sitesLoad.get(2)["featureName"].toString()
                }

                if (sitesLoad.get(2).containsKey("outlierCount") && sitesLoad.get(2)["outlierCount"] != null
                ) {

                    (holder as DashboardEnergyViewholder).site_load_count3!!.text =
                            sitesLoad.get(2)["outlierCount"].toString()
                }

                if (sitesLoad.get(2).containsKey("unit") && sitesLoad.get(2)["unit"] != null) {
                    (holder as DashboardEnergyViewholder).txtSiteLoadUnit3!!.text =
                        sitesLoad.get(2)["unit"].toString()
                }

            }
        }

        if (position == LAYOUT_POWER) {
            if (Utils.hasIndex(0, power!!)) {


                if (power.get(0).containsKey("featureName") && power.get(0)["featureName"] != null) {
                    (holder as DashboardEnergyViewholder).power_title1!!.text =
                        power.get(0)["featureName"].toString()
                }
                if (power.get(0).containsKey("outlierCount") && power.get(0)["outlierCount"] != null) {
                    (holder as DashboardEnergyViewholder).power_count1!!.text =
                        power.get(0)["outlierCount"].toString()
                }


            }

            if (Utils.hasIndex(1, power!!)) {


                if (power.get(1).containsKey("featureName") && power.get(1)["featureName"] != null) {
                    (holder as DashboardEnergyViewholder).power_title2!!.text =
                        power.get(1)["featureName"].toString()
                }
                if (power.get(1).containsKey("outlierCount") && power.get(1)["outlierCount"] != null) {
                    (holder as DashboardEnergyViewholder).power_count2!!.text =
                        power.get(1)["outlierCount"].toString()
                }

            }
            if (Utils.hasIndex(2, power!!)) {
                if (power.get(2).containsKey("featureName") && power.get(2)["featureName"] != null) {
                    (holder as DashboardEnergyViewholder).power_title3!!.text =
                        power.get(2)["featureName"].toString()
                }
                if (power.get(2).containsKey("outlierCount") && power.get(2)["outlierCount"] != null) {
                    (holder as DashboardEnergyViewholder).power_count3!!.text =
                        power.get(2)["outlierCount"].toString()
                }
            }
        }

        if (position == LAYOUT_CO2) {
            if (Utils.hasIndex(0, co2!!)) {

                if (co2.get(0).containsKey("featureName") && co2.get(0)["featureName"] != null) {
                    (holder as DashboardEnergyViewholder).co2_title1!!.text =
                        co2.get(0)["featureName"].toString()
                }

                if (co2.get(0).containsKey("formattedCount") && co2.get(0).containsKey("unit") && co2.get(
                        0
                    )["formattedCount"] != null && co2.get(0)["unit"] != null
                ) {

                    (holder as DashboardEnergyViewholder).co_count1!!.text =
                        getBoldString(
                            co2.get(0)["formattedCount"].toString(),
                            co2.get(0)["unit"].toString(),
                            ""
                        )
                }
            }

            if (Utils.hasIndex(1, co2!!)) {
                if (co2.get(1).containsKey("featureName") && co2.get(1)["featureName"] != null) {
                    (holder as DashboardEnergyViewholder).co2_title2!!.text =
                        co2.get(1)["featureName"].toString()
                }

                if (co2.get(1).containsKey("formattedCount") && co2.get(1).containsKey("unit") && co2.get(
                        1
                    )["formattedCount"] != null && co2.get(1)["unit"] != null
                ) {


                    (holder as DashboardEnergyViewholder).co_count2!!.text = getBoldString(
                        co2.get(1)["formattedCount"].toString(),
                        co2.get(1)["unit"].toString(),
                        ""
                    )
                }
            }

            if (Utils.hasIndex(2, co2!!)) {

                if (co2.get(2).containsKey("featureName") && co2.get(2)["featureName"] != null) {
                    (holder as DashboardEnergyViewholder).co2_title3!!.text =
                        co2.get(2)["featureName"].toString()
                }
                if (co2.get(2).containsKey("formattedCount") && co2.get(2).containsKey("unit") && co2.get(
                        2
                    )["formattedCount"] != null && co2.get(2)["unit"] != null
                ) {

                    (holder as DashboardEnergyViewholder).co_count3!!.text =
                        getBoldString(
                            co2.get(2)["formattedCount"].toString(),
                            co2.get(2)["unit"].toString(),
                            ""
                        )
                }
            }
        }
        if (position == LAYOUT_HI_EB) {
            if (Utils.hasIndex(0, high!!)) {
                if (high.get(0).containsKey("featureName")!! && high.get(0)["featureName"] != null) {
                    (holder as DashboardEnergyViewholder).eb_text!!.text =
                        high.get(0)["featureName"].toString()
                }

                if (high.get(0).containsKey("outlierCount") && high.get(0)["outlierCount"] != null) {

                    if (high.get(0)["outlierCount"].toString().equals("0")) {
                        (holder as DashboardEnergyViewholder).eb_count!!.text =
                            "-"
                    } else {
                        (holder as DashboardEnergyViewholder).eb_count!!.text =
                            high.get(0)["outlierCount"].toString()
                    }
                }


            }

            if (Utils.hasIndex(1, high!!)) {

                if (high.get(1).containsKey("featureName") && high.get(1)["featureName"] != null) {
                    (holder as DashboardEnergyViewholder).dg_text!!.text =
                        high.get(1)["featureName"].toString()
                }

                if (high.get(1).containsKey("outlierCount") && high.get(1)["outlierCount"] != null) {

                    if (high.get(1)["outlierCount"].toString().equals("0")) {
                        (holder as DashboardEnergyViewholder).dg_count!!.text =
                            "-"
                    } else {
                        (holder as DashboardEnergyViewholder).dg_count!!.text =
                            high.get(1)["outlierCount"].toString()
                    }
                }

            }

//            if(Utils.hasIndex(0, eb!!)) {
//
//
//                if (eb.get(0).containsKey("featureName")) {
//                    (holder as DashboardEnergyViewholder).cillected_txt!!.text =
//                        eb.get(0)["featureName"].toString()
//                }
//
//                if (eb.get(0).containsKey("outlierCount")) {
//                    (holder as DashboardEnergyViewholder).collected_count!!.text =
//                        eb.get(0)["outlierCount"].toString()
//                }
//
//            }
//                if(Utils.hasIndex(1, eb!!)){
//                if(eb.get(1).containsKey("featureName")){
//                    (holder as DashboardEnergyViewholder).paid_txt!!.text=eb.get(1)["featureName"].toString()
//                }
//
//                if(eb.get(1).containsKey("outlierCount")){
//                    (holder as DashboardEnergyViewholder).paid_count!!.text=eb.get(1)["outlierCount"].toString()
//                }
//
//
//
//
//            }


        }


    }

    override fun getItemViewType(position: Int): Int {
        when (position) {
            LAYOUT_SITES -> {
                return LAYOUT_SITES
            }

            LAYOUT_SITE_LOAD -> {
                return LAYOUT_SITE_LOAD
            }
            LAYOUT_POWER -> {
                return LAYOUT_POWER
            }
            LAYOUT_CO2 -> {
                return LAYOUT_CO2
            }

            LAYOUT_HI_EB -> {
                return LAYOUT_HI_EB
            }


            /*LAYOUT_PROJECTS -> {
                return LAYOUT_PROJECTS
            }*/

//            LAYOUT_USEFUL_LINKS -> {
//                return LAYOUT_USEFUL_LINKS
//            }
        }
        return 0
    }

    fun hasIndex(index: Int, dataList: List<Map<String, Any>>): Boolean {
        if (index < dataList!!.size)
            return true
        else
            return false

    }

    fun opneEnergySiteItemFragment() {
        val commonBean = CommonBean()

        var sitesEnergyFragment = SiteEnergyFragment.newInstance()
        //  infraMaintenanceFragment.setData(commonBean,featureId,featureName,featureCount)
        (activity as MainActivity).openFragments(sitesEnergyFragment, commonBean, true, true)
    }

}