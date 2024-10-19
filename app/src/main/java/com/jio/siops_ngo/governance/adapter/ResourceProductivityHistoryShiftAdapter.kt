package com.jio.siops_ngo.governance.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.governance.viewholder.GeneralShiftLiveItemViewHolder
import com.jio.siops_ngo.governance.viewholder.ProductivityShiftRecyclerViewHolder
import com.jio.siops_ngo.utilities.MyExceptionHandler

class ResourceProductivityHistoryShiftAdapter(val mainActivity: MainActivity?, val shiftDomainIdDataMap: HashMap<String, List<Map<String, Any>>>, val shiftsList:ArrayList<String>, val shiftCitrixSessionDataMap: HashMap<String, List<Map<String, Any>>>,
                                              val shiftNgoSessionDataMap: HashMap<String, List<Map<String, Any>>>, val shiftAlertDataMap: HashMap<String, List<Map<String, Any>>>,
                                              val shiftUserTimesheetDataMap: HashMap<String, List<Map<String, Any>>>) : RecyclerView.Adapter<ProductivityShiftRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viwType: Int): ProductivityShiftRecyclerViewHolder {
        var view: View?

        view = LayoutInflater.from(parent.context).inflate(R.layout.productivity_history_shift_rv, parent, false)

        return ProductivityShiftRecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductivityShiftRecyclerViewHolder, position: Int) {
        try {
            var shiftName= shiftsList[position]
            var shiftDomainIdtList = ArrayList<Map<String, Any>>()
            var shiftCitrixSessionList = ArrayList<Map<String, Any>>()
            var shiftNgoSessionList = ArrayList<Map<String, Any>>()
            var shiftAlertList = ArrayList<Map<String, Any>>()
            var shiftUserTimeSheetList = ArrayList<Map<String, Any>>()
            if(shiftName!=null && shiftName.trim().length>0){

                holder.txtShiftHeader!!.text = shiftName+" Shift"
            }
            if(shiftDomainIdDataMap.containsKey(shiftName) && shiftDomainIdDataMap.get(shiftName)!=null){
                shiftDomainIdtList = shiftDomainIdDataMap[shiftName] as ArrayList<Map<String, Any>>
            }

            if(shiftCitrixSessionDataMap.containsKey(shiftName) && shiftCitrixSessionDataMap.get(shiftName)!=null){
                shiftCitrixSessionList = shiftCitrixSessionDataMap[shiftName] as ArrayList<Map<String, Any>>
            }

            if(shiftNgoSessionDataMap.containsKey(shiftName) && shiftNgoSessionDataMap.get(shiftName)!=null){
                shiftNgoSessionList = shiftNgoSessionDataMap[shiftName] as ArrayList<Map<String, Any>>
            }

            if(shiftAlertDataMap.containsKey(shiftName) && shiftAlertDataMap.get(shiftName)!=null){
                shiftAlertList = shiftAlertDataMap[shiftName] as ArrayList<Map<String, Any>>
            }

            if(shiftUserTimesheetDataMap.containsKey(shiftName) && shiftUserTimesheetDataMap.get(shiftName)!=null){
                shiftUserTimeSheetList = shiftUserTimesheetDataMap[shiftName] as ArrayList<Map<String, Any>>
            }


            var resourceShiftHistoryProductivityItemAdapter = ResourceProductivityHistoryShiftItemAdapter(mainActivity, shiftDomainIdtList!!, shiftCitrixSessionList!!, shiftNgoSessionList!!, shiftAlertList!!, shiftUserTimeSheetList!!)
            holder!!.live_moring_list!!.layoutManager = LinearLayoutManager(mainActivity)
            holder!!.live_moring_list!!.itemAnimator = DefaultItemAnimator()
            holder!!.live_moring_list!!.adapter = resourceShiftHistoryProductivityItemAdapter
            resourceShiftHistoryProductivityItemAdapter!!.notifyDataSetChanged()



        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
        }
    }

    override fun getItemCount(): Int {
        return shiftsList!!.size
    }
}
