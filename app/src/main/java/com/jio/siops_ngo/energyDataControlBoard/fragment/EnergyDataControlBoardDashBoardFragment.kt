package com.jio.siops_ngo.energyDataControlBoard.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.jio.siops_ngo.MainActivity

import com.jio.siops_ngo.R
import com.jio.siops_ngo.databinding.FragmentEnergyDataControlBoardDashBoardBinding
import com.jio.siops_ngo.energyDataControlBoard.adapter.EnergyDataCompletsGapesListAdapter
import com.jio.siops_ngo.energyDataControlBoard.adapter.EnergyDataCompletsMissingListAdapter

/**
 * A simple [Fragment] subclass.
 */
class EnergyDataControlBoardDashBoardFragment : Fragment() {

    var mBinding: FragmentEnergyDataControlBoardDashBoardBinding? = null
    var eNodeList: ArrayList<String>? = ArrayList()
    var fslist: ArrayList<String>? = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_energy_data_control_board_dash_board, container, false)

        return mBinding!!.root
    }



    companion object {
        fun newInstance() =
            EnergyDataControlBoardDashBoardFragment()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()
    }

    private fun initView() {

        eNodeList!!.clear()
        eNodeList!!.add("eNodeB");
        fslist!!.clear()
        fslist!!.add("Small Facililty(SFL)");


        val langAdapter =
            ArrayAdapter<String>(
                activity!!,
                R.layout.spinner_text,
                fslist!!
            )
        mBinding!!.spnrSmall.setAdapter(langAdapter)



        val langAdapter1 =
            ArrayAdapter<String>(
                activity!!,
                R.layout.spinner_text,
                eNodeList!!
            )
        mBinding!!.spnrEnode.setAdapter(langAdapter1)

        var adapter = EnergyDataCompletsGapesListAdapter(
            activity as MainActivity?)
        mBinding!!.rvBadsitesList.layoutManager = LinearLayoutManager(activity)
        mBinding!!.rvBadsitesList.itemAnimator = DefaultItemAnimator()
        mBinding!!.rvBadsitesList!!.adapter = adapter
        adapter!!.notifyDataSetChanged()

        var adapter1 = EnergyDataCompletsGapesListAdapter(
            activity as MainActivity?)
        mBinding!!.rvGapessitesList.layoutManager = LinearLayoutManager(activity)
        mBinding!!.rvGapessitesList.itemAnimator = DefaultItemAnimator()
        mBinding!!.rvGapessitesList!!.adapter = adapter1
        adapter!!.notifyDataSetChanged()



        var adapter2 = EnergyDataCompletsMissingListAdapter(

            activity as MainActivity

        )

        val gridLayoutManager = GridLayoutManager(activity, 3)
        mBinding!!.rvMissingList!!.layoutManager =
            gridLayoutManager
        mBinding!!.rvMissingList!!.itemAnimator =
            DefaultItemAnimator()

        mBinding!!.rvMissingList!!.adapter = adapter2
    }


}
