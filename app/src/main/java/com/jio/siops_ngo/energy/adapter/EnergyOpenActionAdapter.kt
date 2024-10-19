package com.jio.siops_ngo.infra.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.utilities.MyConstants
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.energy.viewholder.EnergyRegionItemHolder
import com.jio.siops_ngo.utilities.MyExceptionHandler
import java.util.ArrayList


class EnergyOpenActionAdapter(
    private val activity: MainActivity?,
    private val dataList: ArrayList<Map<String, Any>>,
    val regionStateMpJcListener: regionStateMpJcCLickListener,
    val apiCode:String
) : RecyclerView.Adapter<EnergyRegionItemHolder>() {


    interface regionStateMpJcCLickListener {

        fun onRegStateMpJcClicked(key:String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viwType: Int): EnergyRegionItemHolder {
        var view: View?

        view =
            LayoutInflater.from(parent.context).inflate(R.layout.all_dynamic_region, parent, false)

        return EnergyRegionItemHolder(view)
    }


    override fun onBindViewHolder(holder: EnergyRegionItemHolder, position: Int) {
        try {
            val content = dataList[position]
            var featureName: String = ""
            if (content["featureName"] != null && content.containsKey("featureName")) {
                holder.txtName!!.text = content["featureName"] as String
                featureName = content["featureName"] as String
            }
            if (content["outlierCount"] != null && content.containsKey("outlierCount")) {
                holder.txtCount!!.text = content["outlierCount"].toString()
            }

            if(apiCode.equals(MyConstants.ENERGY_SAP_ID)){

                if (content["featureId"] != null && content.containsKey("featureId")) {
                    holder.txtName!!.text = content["featureId"] as String
                    featureName = content["featureId"] as String
                }
                holder.txtCount!!.text = ""
            }else{
                if (content["featureName"] != null && content.containsKey("featureName")) {
                    holder.txtName!!.text = content["featureName"] as String
                    featureName = content["featureName"] as String
                }
            }


            holder.lnrItem!!.setOnClickListener {

                try {
                    regionStateMpJcListener.onRegStateMpJcClicked(featureName)

                } catch (e: Exception) {
                    MyExceptionHandler.handle(e)
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
        return dataList.size
    }
}