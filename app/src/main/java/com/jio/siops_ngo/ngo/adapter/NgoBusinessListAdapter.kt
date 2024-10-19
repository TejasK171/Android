package com.jio.siops_ngo.ngo.adapter


import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.bean.CommonBean
import com.jio.jioinfra.utilities.MyConstants
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.ngo.fragment.NgoBusinessViewFragmentNew
import com.jio.siops_ngo.ngo.viewholder.NgoBusinessListItemViewHolder
import com.jio.siops_ngo.utilities.MyExceptionHandler


class NgoBusinessListAdapter(
    private val appList: List<Map<String, Any>>,
    private val activity: MainActivity?
) : RecyclerView.Adapter<NgoBusinessListItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viwType: Int
    ): NgoBusinessListItemViewHolder {
        var view: View?

        view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ngo_business_list_item, parent, false)

        return NgoBusinessListItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: NgoBusinessListItemViewHolder, position: Int) {
        try {
            val content = appList[position]


            if (content.containsKey("title") && content["title"] != null) {
                holder.txtTitle!!.text = content["title"] as String
            }
            if (content.containsKey("color") && content["color"] != null) {
                val color = Color.parseColor(content["color"].toString())
                (holder.imgCircle!!.getBackground() as GradientDrawable).setColor(color)
            }

            var busicode = ""
            if (content.containsKey("key") && content["key"] != null) {
                busicode = content["key"] as String
            }


            holder.cnstrntLItem!!.setOnClickListener {
                val commonBeanData = CommonBean()
                commonBeanData.setmTitle(MyConstants.NGO_BUSINESS_VIEW)

                var dashboardNgoBusinessViewFragmentNew = NgoBusinessViewFragmentNew.newInstance()
                dashboardNgoBusinessViewFragmentNew.setData(commonBeanData, busicode)
                (activity as MainActivity).openFragments(
                    dashboardNgoBusinessViewFragmentNew,
                    commonBeanData,
                    true,
                    true
                )
            }


        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
        }
    }

    override fun getItemCount(): Int {
        return appList.size
    }
}
