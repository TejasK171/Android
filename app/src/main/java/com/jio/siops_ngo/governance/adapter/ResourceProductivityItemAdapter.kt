package com.jio.siops.ngo.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.bean.CommonBean
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.governance.fragment.ResourceProductivityLiveFragment
import com.jio.siops_ngo.governance.viewholder.ResourceProductivityItemViewHolder
import com.jio.siops_ngo.utilities.MyExceptionHandler


class ResourceProductivityItemAdapter(
    private val activity: MainActivity?,
    private val listData: ArrayList<Map<String, Any>>

) : RecyclerView.Adapter<ResourceProductivityItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viwType: Int
    ): ResourceProductivityItemViewHolder {
        var view: View?

        view = LayoutInflater.from(parent.context)
            .inflate(R.layout.resource_productivity_rv_item, parent, false)

        return ResourceProductivityItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResourceProductivityItemViewHolder, position: Int) {
        try {
            val content = listData[position]

            if (content.containsKey("count") && content["count"] != null) {
                if (content.containsKey("appName") && content["appName"] != null) {
                    holder.cnstrntlHeader!!.visibility = View.VISIBLE
                    holder.cnstrntlItem!!.visibility = View.GONE
                    holder.txtHeader!!.text = content["appName"] as String +" ("+ content["count"] as String +")"
                }
            } else if (content.containsKey("appName") && content["appName"] != null) {
                holder.cnstrntlHeader!!.visibility = View.GONE
                holder.cnstrntlItem!!.visibility = View.VISIBLE
                holder.txtItem!!.text = content["appName"] as String
            }

            holder.cnstrntlItem!!.setOnClickListener {

                var commonBean = CommonBean()
                commonBean.`object` = content
                var resourceProductivityHistoryAndLiveFragment = ResourceProductivityLiveFragment.newInstance()
                resourceProductivityHistoryAndLiveFragment.setData(content as HashMap<String, Any>)
                (activity as MainActivity)!!.openFragments(
                    resourceProductivityHistoryAndLiveFragment,
                    commonBean,
                    true,
                    true
                )

            }




        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}
