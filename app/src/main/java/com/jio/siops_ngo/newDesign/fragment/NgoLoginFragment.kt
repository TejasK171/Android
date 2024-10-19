package com.jio.siops_ngo.newDesign

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment

import com.jio.siops_ngo.R
import com.jio.siops_ngo.databinding.FragmentNewOtpBinding
import com.jio.siops_ngo.databinding.FragmentNgoLoginBinding


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [NgoLoginFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [NgoLoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NgoLoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    var mBinding: FragmentNgoLoginBinding?=null

    companion object {
        fun newInstance() = NgoLoginFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_ngo_login, container, false)

        return mBinding!!.root
       // return inflater.inflate(R.layout.fragment_ngo_login, container, false)
    }


}
