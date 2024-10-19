package com.jio.siops_ngo.ngo.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.bean.CommonBean
import com.jio.jioinfra.network.business.BaseCoroutines
import com.jio.jioinfra.utilities.Busicode
import com.jio.jioinfra.utilities.MyConstants
import com.jio.myjio.bean.CoroutinesResponse
import com.jio.siops_ngo.MainActivity

import com.jio.siops_ngo.R
import com.jio.siops_ngo.databinding.FragmentNgoOpenChangeBinding
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.ngo.adapter.NgoDeffectChangeListItemAdapter
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.T
import kotlinx.coroutines.*

/**
 * A simple [Fragment] subclass.
 */
class NgoDeffectHistoryFragment : Fragment() {
    var mBinding:FragmentNgoOpenChangeBinding?=null
    var commonBean: CommonBean? = null
    var msg: HashMap<String, Any>? = null
    var selection: Int? = 0
    var header:String?=null
    var name:String?=null

    var count:String?=null
    var namedeffect:String?=null
    var   duration: String?=null
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


        mBinding!!.header.visibility=View.GONE
        mBinding!!.txtOpenChargeHeader.text=namedeffect
        mBinding!!.txtOpenChargeCount.text=header

        fetchData()
    }


    companion object {
        fun newInstance() =
            NgoDeffectHistoryFragment()
    }


    fun fetchData() {
        (activity as MainActivity).showProgressBar()
        val requestBody = HashMap<String, Any>()
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["appRoleCode"] = MyConstants.APP_ROLE_CODE_NGO
        requestBody["type"] = "userInfo"
        requestBody["defectType"] = name!!
        requestBody["category"] = namedeffect!!
        requestBody["duration"] = duration!!
        CoroutineScope(Dispatchers.IO).launch {

            var job = async { BaseCoroutines().fetchData(requestBody, Busicode.NGOOpenDefectDetail, activity as MainActivity) }
            withContext(Dispatchers.Main)
            {

                var response = job.await()
                if (activity != null) {
                    (activity as MainActivity).hideProgressBar()
                }

                if (response!!.responseEntity != null && response.status == MappActor.STATUS_OK) {
                    filterData(response)
                } else if (response!!.errorCode != null && response!!.errorCode.equals(MappActor.VERSION_SESSION_INVALID)){
                    (activity as MainActivity).showDialogForSessionExpired((activity as MainActivity).resources.getString(R.string.session_expired), (activity as MainActivity))
                } else if (response!!.errorMsg != null) {
                    T.show(activity, response!!.errorMsg!!, 0)
                } else {
                    T.show(activity, "Something went wrong!", 0)
                }
            }

        }



    }

    fun filterData(mCoroutinesResponse: CoroutinesResponse) {

        try {
            val msg = mCoroutinesResponse.responseEntity as HashMap<String, Any>
           // mBinding!!.cnstrntLdeffectlist.visibility=View.VISIBLE

            if (msg["list"] != null) {

                listList = msg["list"] as ArrayList<Map<String, Any>>
                mBinding!!.recyclerViewList.apply {
                    layoutManager =
                        LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                    adapter = NgoDeffectChangeListItemAdapter(activity as MainActivity?,listList!!)
                }



            }


        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
            T.show(activity, getString(R.string.something_went_wrong), 0)
        }
    }

    fun setData(
        commonBean: CommonBean,
        selection: Int,
        name: String,
        header: String,
        namedeffect: String,
        duration: String
    ) {
        this.commonBean = commonBean
        this.selection = selection
        this.name = name
        this.header = header
        this.namedeffect = namedeffect
        this.duration = duration
    }

}
