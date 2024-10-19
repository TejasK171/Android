package com.jio.siops_ngo.businessBoard.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.jio.siops_ngo.MainActivity

import com.jio.siops_ngo.R
import com.jio.siops_ngo.databinding.FragmentRechargePandingBinding
import com.jio.siops_ngo.infra.adapter.PandingRechargeJourneryAdapter

/**
 * A simple [Fragment] subclass.
 */
class RechargePendingFragment : Fragment() {


    var mBinding: FragmentRechargePandingBinding? = null
    var rechargePandingList: ArrayList<Map<String, Any>>?=null

    companion object {
        fun newInstance() =
            RechargePendingFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recharge_panding, container, false)
        return mBinding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
                    var adapter2 = PandingRechargeJourneryAdapter(activity as MainActivity?,rechargePandingList!!)
                    mBinding!!.rechargejourneyList.layoutManager = LinearLayoutManager(activity)
                    mBinding!!.rechargejourneyList.itemAnimator = DefaultItemAnimator()
                    mBinding!!.rechargejourneyList!!.adapter = adapter2
                    adapter2!!.notifyDataSetChanged()
    }




    fun setData(rechargePandingList: ArrayList<Map<String, Any>>) {
        this.rechargePandingList = rechargePandingList
    }


}
