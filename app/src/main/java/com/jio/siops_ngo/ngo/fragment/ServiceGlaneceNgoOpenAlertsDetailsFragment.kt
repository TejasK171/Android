package com.jio.siops_ngo.ngo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.bean.CommonBean
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.databinding.FragmentNgoOpenItemDetailsBinding
import com.jio.siops_ngo.ngo.adapter.ServiceGlaneceNgoAlarmAdapter
import com.jio.siops_ngo.utilities.PreferenceUtility

class ServiceGlaneceNgoOpenAlertsDetailsFragment : androidx.fragment.app.Fragment() {

    var mBinding: FragmentNgoOpenItemDetailsBinding? = null
    var commonBean: CommonBean? = null

    var msg:ArrayList<Map<String, Any>>?=null
    var feactureName:String?=null
    var count:String?=null

    var dataMap: ArrayList<Map<String, Any>>?  = null

    companion object {
        fun newInstance() =
            ServiceGlaneceNgoOpenAlertsDetailsFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_ngo_open_item_details,
            container,
            false
        )

        return mBinding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
            mBinding!!.currentDate.text= PreferenceUtility.getString(activity,"date", "")
            mBinding!!.header.text= PreferenceUtility.getString(activity,"title", "")+"-"+PreferenceUtility.getString(activity,"subtitle", "")
            mBinding!!.txtDashboardSubtitle1.text = feactureName

            mBinding!!.countData.text = count



        mBinding!!.rvParent.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            adapter = ServiceGlaneceNgoAlarmAdapter(msg!!, activity as MainActivity?)
        }

      //  fetchDashboardDbData()

    }




    fun setData(
        commonBean: CommonBean,
        msg: ArrayList<Map<String, Any>>?,
        feactureName:String,
        count:String) {
        this.commonBean = commonBean
        this.msg = msg
        this.feactureName = feactureName
        this.count = count

    }


}