package com.jio.siops_ngo.infra.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.jio.jioinfra.custom.TextViewMedium
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R

class RegionCustomDropDownAdapter(val activity: MainActivity?, var regionlist: ArrayList<Map<String, Any>>?) : BaseAdapter() {

    interface RegionSelection{

        fun regionSelect(name:String);
    }

    val mInflater: LayoutInflater = LayoutInflater.from(activity)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: ItemRowHolder
        if (convertView == null) {
            view = mInflater.inflate(R.layout.region_spiner_item, parent, false)
            vh = ItemRowHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemRowHolder
        }

        // setting adapter item height programatically.

        val params = view.layoutParams
        params.height = 200
        view.layoutParams = params
        val content = regionlist?.get(position)

        vh.label.text =  content!!["name"].toString()
        return view
    }

    override fun getItem(position: Int): Any? {

        return null

    }

    override fun getItemId(position: Int): Long {

        return 0

    }

    override fun getCount(): Int {
        return regionlist!!.size
    }

    private class ItemRowHolder(row: View?) {

        val label: TextViewMedium

        init {
            this.label = row?.findViewById(R.id.txtDropDownLabel) as TextViewMedium
        }
    }
}