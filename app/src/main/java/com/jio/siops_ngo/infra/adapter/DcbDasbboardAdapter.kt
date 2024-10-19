package com.jio.siops_ngo.infra.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.bean.CommonBean
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.infra.fragment.DcbDashboardClickFragment
import com.jio.siops_ngo.infra.fragment.DcbInserviceFragment
import com.jio.siops_ngo.infra.viewholder.DcbItemHolder
import com.jio.siops_ngo.utilities.MyExceptionHandler


class DcbDasbboardAdapter(private val appList: List<Map<String, Any>>, private val activity: MainActivity?, var commonBean: CommonBean, var outlierID: String, var appRoleCode: String) : RecyclerView.Adapter<DcbItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viwType: Int): DcbItemHolder {
        var view: View?

        view = LayoutInflater.from(parent.context).inflate(R.layout.dcb_recycler_item, parent, false)

        return DcbItemHolder(view)
    }



    override fun onBindViewHolder(holder: DcbItemHolder, position: Int) {
        try {
            val content = appList[position]



            holder.txtValue!!.text = content["key"] as String



            if( content["color"]!!.equals(1)) {
                holder.txtCount!!.setTextColor(activity!!.getResources().getColor(R.color.text_red_color))
                holder.txtCount!!.text = content["value"]!!.toString()
            }else{

                holder.txtCount!!.text = content["value"]!!.toString()
            }

            holder.dcbConstraintLayout!!.setOnClickListener {

                try {
                    if (content.containsKey("onclick") && content.containsKey("onclick") != null){
                        if ((content["key_code"] as String).toString().equals("IN_USE")) {
                            val commonBean = CommonBean()
                            commonBean.setmSubTitle(content["key"] as String+ " ("+ content["value"]!!.toString()+")")
                            var dcbInserviceFragment = DcbInserviceFragment.newInstance()
                            commonBean.`object` = content
                            dcbInserviceFragment.setData(commonBean, content["key"] as String, appRoleCode,content["key"] as String)
                            (activity)!!.openFragments(dcbInserviceFragment, commonBean, true, true)
                        }else{
                            val commonBean = CommonBean()
                            commonBean.setmSubTitle(content["key"] as String+ " ("+ content["value"]!!.toString()+")")
                            var dcbDashboardClickFragment = DcbDashboardClickFragment.newInstance()
                            commonBean.`object` = content
                            dcbDashboardClickFragment.setData(commonBean, content["key"] as String, appRoleCode,content["key"] as String)
                            (activity)!!.openFragments(dcbDashboardClickFragment, commonBean, true, true)

                        }
                }

                } catch (e: Exception) {
                    MyExceptionHandler.handle(e)
                }
            }

        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
        }
    }

    override fun getItemCount(): Int {
        return appList.size
    }
}
