package com.jio.siops_ngo.infra.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.bean.CommonBean
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.infra.fragment.DcbInUseClickFragment
import com.jio.siops_ngo.infra.viewholder.DcbItemHolder
import com.jio.siops_ngo.utilities.MyExceptionHandler


class DcbInServiceAdapter(
    private val appList: List<Map<String, Any>>,
    private val activity: MainActivity?,
    var statusType: String?,
   var  appRoleCode: String?
) : RecyclerView.Adapter<DcbItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viwType: Int): DcbItemHolder {
        var view: View?

        view = LayoutInflater.from(parent.context).inflate(R.layout.dcb_inservice_item, parent, false)

        return DcbItemHolder(view)
    }



    override fun onBindViewHolder(holder: DcbItemHolder, position: Int) {
        try {
            val content = appList[position]
            holder.txtValue!!.text = content["key"] as String
            holder.txtCount!!.text = content["value"]!!.toString()

            holder.dcbConstraintLayout!!.setOnClickListener {

                try {
                    if (content.containsKey("onclick") && content.containsKey("onclick") != null){
                        if ((content["onclick"] as Int).toString().equals("0")) {
                            val commonBean = CommonBean()
                            var dcbDcbInUseClickFragment = DcbInUseClickFragment.newInstance()
                            dcbDcbInUseClickFragment.setData(commonBean,statusType!!,appRoleCode!!,content["id"]!!.toString(),content["key"] as String)
                            (activity)!!.openFragments(dcbDcbInUseClickFragment, commonBean, true, true)
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
