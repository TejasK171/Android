package com.jio.siops_ngo.infra.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.bean.CommonBean
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.energy.fragment.EnergyOpenActionClickFragment
import com.jio.siops_ngo.energy.viewholder.OpenActionItemHolder
import com.jio.siops_ngo.utilities.MyExceptionHandler
import java.util.ArrayList


class EnergyOpenActionItemAdapter(
    private val activity: MainActivity?,
    private val openActionItemList: ArrayList<Map<String, Any>>,val siteCategoryList: ArrayList<String>, val selectedPosition: Int
) : RecyclerView.Adapter<OpenActionItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viwType: Int): OpenActionItemHolder {
        var view: View?

        view = LayoutInflater.from(parent.context).inflate(R.layout.item_view_open_action, parent, false)

        return OpenActionItemHolder(view)
    }


    override fun onBindViewHolder(holder: OpenActionItemHolder, position: Int) {
        try {
              val content = openActionItemList[position]
              var outlierId:String? = ""
              var featureName:String? = ""

            if(content["featureName"]!=null && content.containsKey("featureName")) {
                holder.textViewActionName!!.text = content["featureName"] as String
                featureName = content["featureName"] as String
            }
            if(content["outlierCount"]!=null && content.containsKey("outlierCount")) {
                holder.textViewActionCount!!.text = content["outlierCount"].toString()
            }
            if(content["featureId"]!=null && content.containsKey("featureId")) {
                outlierId = content["featureId"].toString()
            }

            holder.constLL!!.setOnClickListener {

                val commonBean = CommonBean()

                var energyEnergyOpenActionClickFragment = EnergyOpenActionClickFragment.newInstance()
                energyEnergyOpenActionClickFragment.setData(outlierId!!, siteCategoryList, selectedPosition, featureName!!)
                (activity as MainActivity).openFragments(
                    energyEnergyOpenActionClickFragment,
                    commonBean!!,
                    true,
                    true
                )

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
        return openActionItemList.size
    }
}