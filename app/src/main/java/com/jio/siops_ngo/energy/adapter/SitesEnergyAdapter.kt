package com.jio.siops_ngo.infra.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.infra.viewholder.DcbItemHolder
import com.jio.siops_ngo.utilities.MyExceptionHandler


class SitesEnergyAdapter(private val activity: MainActivity?) : RecyclerView.Adapter<DcbItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viwType: Int): DcbItemHolder {
        var view: View?

        view = LayoutInflater.from(parent.context).inflate(R.layout.site_energy_item, parent, false)

        return DcbItemHolder(view)
    }



    override fun onBindViewHolder(holder: DcbItemHolder, position: Int) {
        try {
          //  val content = appList[position]



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
        return 10
    }
}
