package com.jio.siops_ngo.ngo.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.bean.CommonBean
import com.jio.jioinfra.utilities.MyConstants
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.ngo.fragment.NgoDeffectHistoryFragment
import com.jio.siops_ngo.ngo.viewholder.NgoDeffectLitstemHolder
import com.jio.siops_ngo.utilities.MyExceptionHandler


class NgoDeffectAdapter(
    private val appList: List<Map<String, Any>>,
    private val activity: MainActivity?,
    var name: String,
    var header: String,
    var count: String?
) : RecyclerView.Adapter<NgoDeffectLitstemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viwType: Int): NgoDeffectLitstemHolder {
        var view: View?

        view = LayoutInflater.from(parent.context).inflate(R.layout.ngo_deffect_list, parent, false)

        return NgoDeffectLitstemHolder(view)
    }

    override fun onBindViewHolder(holder: NgoDeffectLitstemHolder, position: Int) {
        try {
            val content = appList[position]
            holder.txt_siteid_title!!.text = content["featureName"] as String
            holder.txt_siteid_value!!.text = (content["outlierCount"] as Int).toString()
            holder.ll!!.setOnClickListener {
                val commonBeanData = CommonBean()
                commonBeanData.setmTitle(MyConstants.NGO)
                var ngoNgoDeffectHistoryFragment = NgoDeffectHistoryFragment.newInstance()
                ngoNgoDeffectHistoryFragment.setData(
                    commonBeanData,
                    0,name,header,count!!,holder.txt_siteid_title!!.text.toString()
                )
                (activity as MainActivity).openFragments(
                    ngoNgoDeffectHistoryFragment,
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
