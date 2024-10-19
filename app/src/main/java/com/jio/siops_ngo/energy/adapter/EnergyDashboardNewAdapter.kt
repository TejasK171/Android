package com.jio.siops_ngo.infra.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.bean.CommonBean
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.energy.fragment.EnergyDashboardClickFragment
import com.jio.siops_ngo.energy.viewholder.EnergyRvItemHolder
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.Utils
import java.util.HashMap


class EnergyDashboardNewAdapter(
    private val activity: MainActivity?,
    val energyList: ArrayList<HashMap<String, Any>>,
   val requestBodyfilters: HashMap<String, Any>
) : RecyclerView.Adapter<EnergyRvItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viwType: Int): EnergyRvItemHolder {
        var view: View?

        view = LayoutInflater.from(parent.context)
            .inflate(R.layout.dashboard_energy_sites, parent, false)

        return EnergyRvItemHolder(view)
    }


    override fun onBindViewHolder(holder: EnergyRvItemHolder, position: Int) {
        try {
            val content = energyList[position]


            if (content.containsKey("title") && content["title"] != null) {

                holder.txtSiteTitle!!.text = content["title"] as String
            }


            if (content.containsKey("count") && content["count"] != null && content.containsKey("unit") && content["unit"] != null) {

                holder.txtSiteCount!!.text = content["count"].toString()+" "+ content["unit"] as String
            }else{
                holder.txtSiteCount!!.text = ""
            }
            if (content.containsKey("childList") && content["childList"] != null) {

                val subList = content["childList"] as ArrayList<Map<String, Any>>


                if (Utils.hasIndex(0, subList!!)) {
                    var featureId:String = ""
                    var outlierCount:String = ""
                    var featureName:String = ""
                    if (subList[0]!!.containsKey("outlierCount") && subList[0]!!["outlierCount"] != null) {
                        holder.txtCount1!!.text = subList[0]!!["outlierCount"].toString()
                        outlierCount = subList[0]!!["outlierCount"].toString()
                    }

                    if (subList[0]!!.containsKey("featureName") && subList[0]!!["featureName"] != null) {
                        holder.txtSite1!!.text = subList[0]!!["featureName"].toString()
                        featureName = subList[0]!!["featureName"].toString()
                    }

                    if (subList[0]!!.containsKey("unit") && subList[0]!!["unit"] != null) {
                        holder.txtSiteCountUnit1!!.text = subList[0]!!["unit"].toString()
                    }

                    if (subList[0]!!.containsKey("featureId") && subList[0]!!["featureId"] != null) {
                        featureId = subList[0]!!["featureId"].toString()
                    }


                    holder.cnstrntL1!!.setOnClickListener {
                        if(outlierCount!="0") {
                            val commonBean = CommonBean()
                            var energyDashboardClickFragment =
                                EnergyDashboardClickFragment.newInstance()
                            commonBean.`object` = content
                            energyDashboardClickFragment.setData(
                                commonBean,
                                featureId,
                                featureName,
                                requestBodyfilters
                            )
                            (activity)!!.openFragments(
                                energyDashboardClickFragment,
                                commonBean,
                                true,
                                true
                            )
                        }
                    }


                }

                if (Utils.hasIndex(1, subList!!)) {
                    var featureId:String = ""
                    var outlierCount:String = ""
                    var featureName:String = ""
//                    holder.cnstrntL2!!.visibility = View.VISIBLE

                    if (subList[1]!!.containsKey("outlierCount") && subList[1]!!["outlierCount"] != null) {
                        holder.txtCount2!!.text = subList[1]!!["outlierCount"].toString()
                        outlierCount = subList[1]!!["outlierCount"].toString()
                    }

                    if (subList[1]!!.containsKey("featureName") && subList[1]!!["featureName"] != null) {
                        holder.txtSite2!!.text = subList[1]!!["featureName"].toString()
                        featureName = subList[1]!!["featureName"].toString()
                    }

                    if (subList[1]!!.containsKey("unit") && subList[1]!!["unit"] != null) {
                        holder.txtSiteCountUnit2!!.text = subList[1]!!["unit"].toString()
                    }

                    if (subList[1]!!.containsKey("featureId") && subList[1]!!["featureId"] != null) {
                        featureId = subList[1]!!["featureId"].toString()
                    }

                    holder.cnstrntL2!!.setOnClickListener {
                        if (outlierCount != "0") {
                            val commonBean = CommonBean()
                            var energyDashboardClickFragment =
                                EnergyDashboardClickFragment.newInstance()
                            commonBean.`object` = content
                            energyDashboardClickFragment.setData(
                                commonBean,
                                featureId,
                                featureName,
                                requestBodyfilters
                            )
                            (activity)!!.openFragments(
                                energyDashboardClickFragment,
                                commonBean,
                                true,
                                true
                            )
                        }
                    }


                } else {
//                    holder.cnstrntL2!!.visibility = View.GONE
                }

                if (Utils.hasIndex(2, subList!!)) {
                    var featureId:String = ""
                    var outlierCount:String = ""
                    var featureName:String = ""
//                    holder.cnstrntL2!!.visibility = View.VISIBLE

                    if (subList[2]!!.containsKey("outlierCount") && subList[2]!!["outlierCount"] != null) {
                        holder.txtCount3!!.text = subList[2]!!["outlierCount"].toString()
                        outlierCount = subList[2]!!["outlierCount"].toString()
                    }

                    if (subList[2]!!.containsKey("featureName") && subList[2]!!["featureName"] != null) {
                        holder.txtSite3!!.text = subList[2]!!["featureName"].toString()
                        featureName = subList[2]!!["featureName"].toString()
                    }

                    if (subList[2]!!.containsKey("unit") && subList[2]!!["unit"] != null) {
                        holder.txtSiteCountUnit3!!.text = subList[2]!!["unit"].toString()
                    }

                    if (subList[2]!!.containsKey("featureId") && subList[2]!!["featureId"] != null) {
                        featureId = subList[2]!!["featureId"].toString()
                    }

                    holder.cnstrntL3!!.setOnClickListener {
                        if (outlierCount != "0") {
                            val commonBean = CommonBean()
                            var energyDashboardClickFragment =
                                EnergyDashboardClickFragment.newInstance()
                            commonBean.`object` = content
                            energyDashboardClickFragment.setData(
                                commonBean,
                                featureId,
                                featureName,
                                requestBodyfilters
                            )
                            (activity)!!.openFragments(
                                energyDashboardClickFragment,
                                commonBean,
                                true,
                                true
                            )


                            /*val commonBean = CommonBean()
                            var siteCategoryList: java.util.ArrayList<String>? = arrayListOf()
                            siteCategoryList!!.add(MyConstants.P1)
                            siteCategoryList!!.add(MyConstants.RP1)
                            siteCategoryList!!.add(MyConstants.IP_Colo)

                            var energyEnergyOpenActionClickFragment = EnergyOpenActionClickFragment.newInstance()
                            energyEnergyOpenActionClickFragment.setData(featureId!!, siteCategoryList, 0, featureName!!)
                            (activity as MainActivity).openFragments(
                                energyEnergyOpenActionClickFragment,
                                commonBean!!,
                                true,
                                true
                            )*/


                        }
                    }


                } else {
//                    holder.cnstrntL2!!.visibility = View.GONE
                }

            }













//            holder.txtValue!!.text = content["key"] as String
//
//
//            holder.txtCount!!.text = content["value"]!!.toString()
//
//            holder.dcbConstraintLayout!!.setOnClickListener {
//
//                try {
//                    if (content.containsKey("onclick") && content.containsKey("onclick") != null){
//                        if ((content["onclick"] as Int).toString().equals("0")) {
//                            val commonBean = CommonBean()
//                            commonBean.setmSubTitle(content["key"] as String+ " ("+ content["value"]!!.toString()+")")
//                            var dcbDashboardClickFragment = DcbDashboardClickFragment.newInstance()
//                            commonBean.`object` = content
//                            dcbDashboardClickFragment.setData(commonBean, content["key"] as String, appRoleCode)
//                            (activity)!!.openFragments(dcbDashboardClickFragment, commonBean, true, true)
//                        }
//                }
//
//                } catch (e: Exception) {
//                    MyExceptionHandler.handle(e)
//                }
//            }
//
        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
        }
    }

    override fun getItemCount(): Int {
        return energyList.size
    }
}