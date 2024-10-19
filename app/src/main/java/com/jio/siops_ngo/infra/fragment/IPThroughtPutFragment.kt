package com.jio.siops_ngo.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.jio.jioinfra.bean.CommonBean
import com.jio.siops_ngo.MainActivity

import com.jio.siops_ngo.R
import com.jio.siops_ngo.adapter.AlarmSubAdapter
import com.jio.siops_ngo.databinding.FragmentIpthroughtPutBinding
import com.jio.siops_ngo.databinding.FragmentOpenAlarmsBinding

/**
 * A simple [Fragment] subclass.
 */
class IPThroughtPutFragment : Fragment() {


    var mBinding: FragmentIpthroughtPutBinding? = null
    var commonBean: CommonBean? = null

    companion object {
        fun newInstance() = IPThroughtPutFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      //  return inflater.inflate(R.layout.fragment_ipthrought_put, container, false)

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_ipthrought_put, container, false)

        return mBinding!!.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var adapter = AlarmSubAdapter(activity as MainActivity?)


        mBinding!!.throughputrecyclerView.layoutManager = LinearLayoutManager(activity)
        mBinding!!.throughputrecyclerView.itemAnimator = DefaultItemAnimator()
        mBinding!!.throughputrecyclerView!!.adapter = adapter
        adapter!!.notifyDataSetChanged()
    }

}
