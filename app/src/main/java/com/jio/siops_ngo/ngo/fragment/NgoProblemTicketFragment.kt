package com.jio.siops_ngo.ngo.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jio.siops_ngo.MainActivity

import com.jio.siops_ngo.R
import com.jio.siops_ngo.databinding.FragmentNgoOpenChangeBinding
import com.jio.siops_ngo.ngo.adapter.NgoProblemTicketItemAdapter
import com.jio.siops_ngo.utilities.PreferenceUtility

/**
 * A simple [Fragment] subclass.
 */
class NgoProblemTicketFragment : Fragment() {
    var mBinding:FragmentNgoOpenChangeBinding?=null
    var msg: HashMap<String, Any>? = null
    var selection: Int? = 0
    var header:String?=null

    var count:String?=null
    var listList:ArrayList<Map<String, Any>>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_ngo_open_change, container, false)
        return mBinding!!.root
      //  return inflater.inflate(R.layout.fragment_ngo_open_change, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mBinding!!.header.text="Problem Ticket-"+ PreferenceUtility.getString(activity,"title","")

        mBinding!!.txtOpenChargeHeader.text=header
        mBinding!!.txtOpenChargeCount.text=count
        mBinding!!.txtHeader.text="Service Glance - Problem Ticket"

        mBinding!!.recyclerViewList.apply {
            layoutManager =
                LinearLayoutManager(activity as MainActivity, RecyclerView.VERTICAL, false)
            adapter = NgoProblemTicketItemAdapter(activity as MainActivity?,listList!!, selection!!)
        }
    }


    companion object {
        fun newInstance() =
            NgoProblemTicketFragment()
    }

    fun setData(listList:ArrayList<Map<String, Any>>?, selection: Int,header:String,count:String) {
        this.listList = listList
        this.header = header
        this.count = count
        this.selection = selection
    }

}
