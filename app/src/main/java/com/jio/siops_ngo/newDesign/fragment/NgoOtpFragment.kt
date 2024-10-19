package com.jio.siops_ngo.newDesign.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.jio.siops_ngo.R
import com.jio.siops_ngo.databinding.FragmentNewOtpBinding


/**
 * A simple [Fragment] subclass.
 */
class NgoOtpFragment : Fragment() {
    var mBinding:FragmentNewOtpBinding?=null

    companion object {
        fun newInstance() = NgoOtpFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_otp, container, false)

        return mBinding!!.root

    }

}
