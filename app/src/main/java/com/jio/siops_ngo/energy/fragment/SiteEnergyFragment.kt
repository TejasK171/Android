package com.jio.siops_ngo.infra.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.databinding.FragmentSiteEnergyBinding
import com.jio.siops_ngo.infra.adapter.SitesEnergyAdapter


/**
 * A simple [Fragment] subclass.
 */
class SiteEnergyFragment : Fragment() {

    var mBinding: FragmentSiteEnergyBinding? = null
    companion object {
        fun newInstance() = SiteEnergyFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_site_energy, container, false)
        return mBinding!!.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var adapter = SitesEnergyAdapter(activity as MainActivity?)
        mBinding!!.energySiteRecyclerView.layoutManager = LinearLayoutManager(activity)
        mBinding!!.energySiteRecyclerView.itemAnimator = DefaultItemAnimator()
        mBinding!!.energySiteRecyclerView!!.adapter = adapter
        adapter!!.notifyDataSetChanged()
        mBinding!!.imgFilterHeader.setOnClickListener {

            val toast = Toast.makeText(
               activity,
                "Coming Soon....",
                Toast.LENGTH_SHORT
            )

            toast.show()
        }
    }


}
