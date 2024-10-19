package com.jio.siops_ngo.infra.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.bean.CommonBean
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.infra.viewholder.DcbItemHolder
import com.jio.siops_ngo.utilities.MyExceptionHandler


class DcbDasbboardTowerListAdapter(private val appList: List<Map<String, Any>>, private val activity: MainActivity?, var commonBean: CommonBean, var totalCount: String? = "") : RecyclerView.Adapter<DcbItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viwType: Int): DcbItemHolder {
        var view: View?

        view = LayoutInflater.from(parent.context).inflate(R.layout.dcb_recycler_item, parent, false)

        return DcbItemHolder(view)
    }



    override fun onBindViewHolder(holder: DcbItemHolder, position: Int) {
        try {
            val content = appList[position]



            holder.txtValue!!.text = content["key"] as String


            holder.txtCount!!.text = content["value"]!!.toString()

            holder.dcbConstraintLayout!!.setOnClickListener {

            }

        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
        }
    }

    override fun getItemCount(): Int {
        return appList.size
    }
}
