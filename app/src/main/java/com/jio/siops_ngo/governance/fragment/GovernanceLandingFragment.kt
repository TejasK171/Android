package com.jio.siops_ngo.governance.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.jio.jioinfra.bean.CommonBean
import com.jio.jioinfra.network.business.BaseCoroutines
import com.jio.jioinfra.utilities.Busicode
import com.jio.jioinfra.utilities.MyConstants
import com.jio.myjio.bean.CoroutinesResponse
import com.jio.siops.ngo.adapter.TimeTrackingViewSimilarAdapter
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.databinding.FragmentGovernanceBinding
import com.jio.siops_ngo.databinding.FragmentTimeTrackingViewSimilarBinding
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.T
import kotlinx.coroutines.*

/**
 * A simple [Fragment] subclass.
 */
class GovernanceLandingFragment : Fragment() {
    var mBinding: FragmentGovernanceBinding? = null
    var listData: ArrayList<Map<String, Any>>? = null
    var jobRole: String? = null
    var platform: String? = null

    companion object {
        fun newInstance() = GovernanceLandingFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_governance,
            container,
            false
        )
        return mBinding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        mBinding!!.txtResProductivityTitle!!.setOnClickListener {
            var commonBean = CommonBean()
            var resourceProductivityFragment = ResourceProductivityFragment.newInstance()
            (activity as MainActivity)!!.openFragments(
                resourceProductivityFragment,
                commonBean,
                true,
                true
            )
        }

    }

}
