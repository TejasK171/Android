package com.jio.siops_ngo.infra.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.bean.CommonBean
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.infra.viewholder.DcbOnClickItemHolder
import com.jio.siops_ngo.utilities.MyExceptionHandler


class DcbOnClickAdapter(private val appList: List<Map<String, Any>>, private val activity: MainActivity?, var commonBean: CommonBean, var outlierID: String) : RecyclerView.Adapter<DcbOnClickItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viwType: Int): DcbOnClickItemHolder {
        var view: View?

        view = LayoutInflater.from(parent.context).inflate(R.layout.dcb_site_id_item, parent, false)

        return DcbOnClickItemHolder(view)
    }

    override fun onBindViewHolder(holder: DcbOnClickItemHolder, position: Int) {
        try {
            val content = appList[position]



            holder.txtDcbItemTitle!!.text = content["sap_ID"] as String



        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
        }
    }

    override fun getItemCount(): Int {
        return appList.size
    }
}
