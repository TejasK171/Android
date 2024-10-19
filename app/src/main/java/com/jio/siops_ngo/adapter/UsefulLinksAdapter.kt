package com.jio.siops_ngo.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.bean.CommonBean
import com.jio.jioinfra.ui.infraServices.viewholder.DashboardUsefulLinksRowViewholder
import com.jio.jioinfra.utilities.MyConstants
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.approvals.fragment.ApprovalsPendingFragment
import com.jio.siops_ngo.approvals.fragment.LeaveApplicationFragment
import com.jio.siops_ngo.energy.fragment.EnergyDashboardNewFragment
import com.jio.siops_ngo.fragment.DashboardFragment
import com.jio.siops_ngo.fragment.NgoBusinessListFragment
import com.jio.siops_ngo.fragment.TimeSheetFragment
import com.jio.siops_ngo.governance.fragment.GovernanceLandingFragment
import com.jio.siops_ngo.infra.fragment.DcbDashboardFragment
import com.jio.siops_ngo.ngo.fragment.NgoServiceGlanceFragment
import com.jio.siops_ngo.radioAccessNetwork.fragment.RadioAccessNetworkFragment
import com.jio.siops_ngo.roster.fragment.RosterReportFragment

class UsefulLinksAdapter(
    private val appList: List<Map<String, Any>>,
    private val activity: MainActivity?,
    val commonBean: CommonBean
) : RecyclerView.Adapter<DashboardUsefulLinksRowViewholder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viwType: Int
    ): DashboardUsefulLinksRowViewholder {
        var view: View?


        view = LayoutInflater.from(parent.context)
            .inflate(R.layout.dashboard_useful_links_row, parent, false)

        return DashboardUsefulLinksRowViewholder(view)
    }

    override fun onBindViewHolder(holder: DashboardUsefulLinksRowViewholder, position: Int) {
        try {
            val content = appList[position]

            var appUCode: String? = ""

            if (content.containsKey("applicationUCode")) {
                appUCode = content["applicationUCode"] as String
            }
            var title = content["applicationName"] as String

            holder.txtTitle!!.text = title!!

            /*if (content.containsKey("colourCodeForApp")) {
                holder.cardView!!.setCardBackgroundColor(Color.parseColor(content["colourCodeForApp"] as String))
            }*/



            var appCode: String? = ""
            if (content.containsKey("applicationCode")) {
                appCode = content["applicationCode"] as String
            }
            if (appCode!!.equals("0")) {
                holder.img!!.setImageResource(R.drawable.ic_toggle)
            } else {
                holder.img!!.setImageResource(R.drawable.ic_dcb)
            }

            if (appUCode!!.equals(MyConstants.F_Governance_Dashboard)) {
                holder.img!!.setImageResource(R.drawable.ic_governance_grey)
            }else if (appUCode!!.equals(MyConstants.F_VITAL_APPROVALS)) {
                holder.img!!.setImageResource(R.drawable.ic_approvals_grey)
            }else if (appUCode!!.equals(MyConstants.APP_CODE_TIMESHEET)|| appUCode!!.equals(MyConstants.F_Leave_Application)) {
                holder.img!!.setImageResource(R.drawable.ic_timesheet_grey)
            }else if (appUCode!!.equals(MyConstants.F_Roster_Report)) {
                holder.img!!.setImageResource(R.drawable.ic_roster)
            }else if (appUCode!!.equals(MyConstants.APP_CODE_BUSINESS_BOARD) || appUCode!!.equals(MyConstants.UCODE_NGO)) {
                holder.img!!.setImageResource(R.drawable.ic_technical_board)
            }
            else{
                holder.img!!.setImageResource(R.drawable.ic_technical_board_dummy)
            }


            holder.cnstrntLMain!!.setOnClickListener {
                if (appCode!!.equals("0")) {
                    val commonBeanData = CommonBean()

                    if (commonBean.getmTitle().equals(MyConstants.NGO)) {
                        /*commonBeanData.usefulLinksNgo = commonBean!!.usefulLinksNgo
                        commonBeanData.usefulLinksNgo = commonBean!!.usefulLinksNgo
                        commonBeanData.usefulLinksBusinessView = commonBean!!.usefulLinksBusinessView*/
                        commonBeanData.setmTitle(MyConstants.INFRA)
                        var dashboardFragment = DashboardFragment.newInstance()
                        dashboardFragment.setData(commonBeanData, true)
                        (activity as MainActivity).openFragments(
                            dashboardFragment,
                            commonBean,
                            false,
                            true
                        )

                    } else if (commonBean.getmTitle().equals(MyConstants.INFRA) || commonBean.getmTitle().equals(MyConstants.DCB)) {
                        commonBeanData.setmTitle(MyConstants.NGO)
                        /*commonBeanData.usefulLinksNgo = commonBean!!.usefulLinksNgo
                        commonBeanData.usefulLinksNgo = commonBean!!.usefulLinksNgo
                        commonBeanData.usefulLinksBusinessView = commonBean!!.usefulLinksBusinessView*/
//                        var dashboardNgoFragment = DashboardNgoFragment.newInstance()
                        var dashboardNgoFragment = NgoServiceGlanceFragment.newInstance()
                        dashboardNgoFragment.setData(commonBeanData)
                        (activity as MainActivity).openFragments(
                            dashboardNgoFragment,
                            commonBean,
                            false,
                            true
                        )

                    }
                } else if (appUCode!!.equals(MyConstants.APP_CODE_DATA_COMPLETNESS) || appUCode!!.equals(
                        MyConstants.APP_CODE_DATA_COMPLETNESS_AFTER
                    )
                ) {


                    var dataCompleteNessControlFragment = DcbDashboardFragment.newInstance()
                    commonBean.`object` = content
                    commonBean.setmTitle(MyConstants.DCB)
                    dataCompleteNessControlFragment.setData(commonBean)
                    (activity)!!.openFragments(
                        dataCompleteNessControlFragment,
                        commonBean,
                        true,
                        true
                    )


                }
                else if (appUCode!!.equals(MyConstants.UCODE_NGO)) {

                    val commonBeanData = CommonBean()
                    commonBeanData.setmTitle(MyConstants.NGO)
                    var dashboardNgoFragment = NgoServiceGlanceFragment.newInstance()
                    dashboardNgoFragment.setData(commonBeanData)
                    (activity as MainActivity).openFragments(
                        dashboardNgoFragment,
                        commonBean,
                        false,
                        true
                    )


                }else if (appUCode!!.equals(MyConstants.APP_CODE_BUSINESS_BOARD)) {

                    val commonBeanData = CommonBean()
                    commonBeanData.setmTitle(MyConstants.NGO_BUSINESS_VIEW)
                    /*commonBeanData.usefulLinksNgo = commonBean!!.usefulLinksNgo
                    commonBeanData.usefulLinksNgo = commonBean!!.usefulLinksNgo
                    commonBeanData.usefulLinksBusinessView = commonBean!!.usefulLinksBusinessView*/
//                    var dashboardNgoBusinessFragment = NgoBusinessViewFragment.newInstance()
                    var dashboardNgoBusinessFragment = NgoBusinessListFragment.newInstance()
                    dashboardNgoBusinessFragment.setData(commonBeanData)
                    (activity as MainActivity).openFragments(
                        dashboardNgoBusinessFragment,
                        commonBean,
                        false,
                        true
                    )


                }else if (appUCode!!.equals(MyConstants.APP_CODE_ENERGY)) {


//                    var energyDashboardFragment = EnergyDataControlBoardDashBoardFragment.newInstance()
//                    commonBean.setmTitle(MyConstants.DCB)
//                    (activity)!!.openFragments(
//                        energyDashboardFragment,
//                        commonBean,
//                        true,
//                        true
//                    )

                    var energyDashboardFragment = EnergyDashboardNewFragment.newInstance()
                    commonBean.setmTitle(MyConstants.DCB)
                    (activity)!!.openFragments(
                        energyDashboardFragment,
                        commonBean,
                        true,
                        true
                    )
                }else if (appUCode!!.equals(MyConstants.F_VITAL_APPROVALS)) {
                    var approvalsPandingFragment = ApprovalsPendingFragment.newInstance()
                  //  commonBean.setmTitle(MyConstants.DCB)
                    (activity)!!.openFragments(
                        approvalsPandingFragment,
                        commonBean,
                        true,
                        true
                    )
                }else if (appUCode!!.equals(MyConstants.F_Leave_Application)) {
                    var leaveApplicationFragment = LeaveApplicationFragment.newInstance()
                    //  commonBean.setmTitle(MyConstants.DCB)
                    (activity)!!.openFragments(
                        leaveApplicationFragment,
                        commonBean,
                        true,
                        true
                    )
                }
                else if (appUCode!!.equals(MyConstants.F_Roster_Report)) {
                    var rosterReportFragment = RosterReportFragment.newInstance()
                    //  commonBean.setmTitle(MyConstants.DCB)
                    (activity)!!.openFragments(
                        rosterReportFragment,
                        commonBean,
                        true,
                        true
                    )
                }
                else if (appUCode!!.equals(MyConstants.APP_CODE_RADIO_ACCESS_NETWORK)) {


                    var radioAccessNetworkFragment = RadioAccessNetworkFragment.newInstance()
//                    commonBean.setmTitle(MyConstants.DCB)
                    commonBean.`object` = content
                    radioAccessNetworkFragment.setData(commonBean)
                    (activity)!!.openFragments(
                        radioAccessNetworkFragment,
                        commonBean,
                        true,
                        true
                    )


                }
                else if (appUCode!!.equals(MyConstants.APP_CODE_TIMESHEET)) {


                    var timeSheetFragment = TimeSheetFragment.newInstance()
//                    commonBean.setmTitle(MyConstants.DCB)
                    commonBean.`object` = content
                    timeSheetFragment.setData(commonBean)
                    (activity)!!.openFragments(
                        timeSheetFragment,
                        commonBean,
                        true,
                        true
                    )


                }else if (appUCode!!.equals(MyConstants.F_Governance_Dashboard)) {


                    var governanceLandingFragment = GovernanceLandingFragment.newInstance()
                    (activity)!!.openFragments(
                        governanceLandingFragment,
                        commonBean,
                        true,
                        true
                    )


                }

            }


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return appList.size
    }


}


