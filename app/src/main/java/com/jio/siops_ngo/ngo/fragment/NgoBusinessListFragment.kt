package com.jio.siops_ngo.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.jio.jioinfra.bean.CommonBean
import com.jio.jioinfra.network.business.BaseCoroutines
import com.jio.jioinfra.utilities.Busicode
import com.jio.jioinfra.utilities.MyConstants
import com.jio.myjio.bean.CoroutinesResponse
import com.jio.siops_ngo.MainActivity

import com.jio.siops_ngo.R
import com.jio.siops_ngo.adapter.UsefulLinksAdapter
import com.jio.siops_ngo.databinding.FragmentNgoBusinessListBinding
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.ngo.adapter.NgoBusinessListAdapter
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.T
import com.jio.siops_ngo.utilities.Utils
import kotlinx.coroutines.*

/**
 * A simple [Fragment] subclass.
 */
class NgoBusinessListFragment : Fragment() {


    var mBinding: FragmentNgoBusinessListBinding? = null
    var commonBean: CommonBean? = null
    var usefulLinksBusiness: List<Map<String, Any>>? = null

    companion object {
        fun newInstance() = NgoBusinessListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //  return inflater.inflate(R.layout.fragment_ipthrought_put, container, false)

        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_ngo_business_list, container, false)

        return mBinding!!.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        PreferenceUtility.addString(activity,MyConstants.USER_TYPE,"NGO")
        usefulLinksBusiness =
            Utils.getListFromJson(activity!!, MyConstants.USEFUL_LINKS_BUSINESS_VIEW)
        if (usefulLinksBusiness != null) {
            var adapter = UsefulLinksAdapter(
                usefulLinksBusiness!!,
                activity as MainActivity,
                commonBean!!
            )

            val gridLayoutManagerUsefulLinks = GridLayoutManager(activity, 3)
            mBinding!!.usefulLinksRv!!.layoutManager =
                gridLayoutManagerUsefulLinks
            mBinding!!.usefulLinksRv!!.itemAnimator =
                DefaultItemAnimator()

            mBinding!!.usefulLinksRv!!.adapter = adapter
        }

        fetchData()

        /*var adapter = AlarmSubAdapter(activity as MainActivity?)


        mBinding!!.throughputrecyclerView.layoutManager = LinearLayoutManager(activity)
        mBinding!!.throughputrecyclerView.itemAnimator = DefaultItemAnimator()
        mBinding!!.throughputrecyclerView!!.adapter = adapter
        adapter!!.notifyDataSetChanged()*/
    }

    fun fetchData() {
        (activity as MainActivity).showProgressBar()
        val requestBody = HashMap<String, Any>()
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["appRoleCode"] = MyConstants.APP_ROLE_CODE_NGO
        requestBody["type"] = "userInfo"
        CoroutineScope(Dispatchers.IO).launch {

            var job = async {
                BaseCoroutines().fetchData(
                    requestBody,
                    Busicode.NGOBusinessSummary,
                    activity as MainActivity
                )
            }
            withContext(Dispatchers.Main)
            {

                var response = job.await()
                if (activity != null) {
                    (activity as MainActivity).hideProgressBar()
                }

                if (response!!.responseEntity != null && response.status == MappActor.STATUS_OK) {
                    filterData(response)
                } else if (response!!.errorCode != null && response!!.errorCode.equals(MappActor.VERSION_SESSION_INVALID)) {
                    if(activity!=null)
                    (activity as MainActivity).showDialogForSessionExpired(
                        (activity as MainActivity).resources.getString(
                            R.string.session_expired
                        ), (activity as MainActivity)
                    )
                } else if (response!!.errorMsg != null) {
                    T.show(activity, response!!.errorMsg!!, 0)
                    mBinding!!.cnstrntLUsefulLinks.visibility = View.VISIBLE
                } else {
                    T.show(activity, "Something went wrong!", 0)
                    mBinding!!.cnstrntLUsefulLinks.visibility = View.VISIBLE
                }
            }

        }
    }


    fun filterData(mCoroutinesResponse: CoroutinesResponse) {

        try {
            val msg = mCoroutinesResponse.responseEntity as HashMap<String, Any>
            if (msg != null) {
                try {


                    if (msg.containsKey("list") && msg["list"] != null) {

                        var ngoBusinessList = msg["list"] as ArrayList<HashMap<String, Any>>
                        var adapter =
                            NgoBusinessListAdapter(ngoBusinessList!!, activity as MainActivity?)
                        mBinding!!.ngoBusinessRv.layoutManager = LinearLayoutManager(activity)
                        mBinding!!.ngoBusinessRv.itemAnimator = DefaultItemAnimator()
                        mBinding!!.ngoBusinessRv!!.adapter = adapter
                        adapter!!.notifyDataSetChanged()
                    }


                } catch (e: Exception) {
                    MyExceptionHandler.handle(e)
                }
            }

        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
            T.show(activity, getString(R.string.something_went_wrong), 0)
        }

        mBinding!!.cnstrntLUsefulLinks.visibility = View.VISIBLE
    }


    fun setData(commonBean: CommonBean) {
        this.commonBean = commonBean
    }
}
