package com.jio.siops_ngo.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.jio.jioinfra.bean.CommonBean
import com.jio.jioinfra.network.business.BaseCoroutines
import com.jio.jioinfra.utilities.Busicode
import com.jio.jioinfra.utilities.MyConstants
import com.jio.myjio.bean.CoroutinesResponse
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.adapter.DelinquentAdapter
import com.jio.siops_ngo.databinding.FragmentDelinquentBinding
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.T
import kotlinx.android.synthetic.main.fragment_delinquent.*
import kotlinx.coroutines.*

/**
 * A simple [Fragment] subclass.
 */
class DelinquentFragment : Fragment() {

    var mBinding: FragmentDelinquentBinding? = null
    var commonBean: CommonBean? = null
    var dataListNgoItem: ArrayList<Map<String, Any>>? = null

    companion object {
        fun newInstance() = DelinquentFragment()
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_delinquent, container, false)

        return mBinding!!.root
      //  return inflater.inflate(R.layout.fragment_delinquent, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        fetchDashboardDbData()
//        var adapter = DelinquentAdapter( activity as MainActivity?)
//        delingentList!!.layoutManager = LinearLayoutManager(activity)
//        delingentList!!.itemAnimator = DefaultItemAnimator()
//        delingentList!!.adapter = adapter
//        adapter!!.notifyDataSetChanged()
    }
    fun setData(commonBean: CommonBean) {
        this.commonBean = commonBean
    }



    fun fetchDashboardDbData() {
        (activity as MainActivity).showProgressBar()
        val requestBody = HashMap<String, Any>()

       // requestBody["outlierType"] = type
        requestBody["appRoleCode"] = PreferenceUtility.getString(activity, MyConstants.APP_CODE_NGO, "")
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "userInfo"
        CoroutineScope(Dispatchers.IO).launch {

            var job = async { BaseCoroutines().fetchData(requestBody, Busicode.NGOTSDomain, activity as MainActivity) }
            withContext(Dispatchers.Main)
            {

                var response = job.await()
                if (activity != null) {
                    (activity!! as MainActivity).hideProgressBar()
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


            if (msg != null) {
                try {


                    dataListNgoItem = msg["domainList"] as ArrayList<Map<String, Any>>



                    var adapter = DelinquentAdapter(dataListNgoItem!!, activity as MainActivity?)
                    //  var adapter = AlarmSubAdapter( activity as MainActivity?)
                    delingentList!!.layoutManager = LinearLayoutManager(activity)
                    delingentList!!.itemAnimator = DefaultItemAnimator()
                    delingentList!!.adapter = adapter
                    adapter!!.notifyDataSetChanged()



                } catch (e: Exception) {
                    MyExceptionHandler.handle(e)
                }
            }
            // val responsePayload = msg["responsePayload"] as HashMap<String, Any>
            //val listData = responsePayload["applications"] as List<Map<String, Any>>
            /*listData as MutableList
             listData.addAll(responsePayload["applications"] as List<Map<String, Any>>)
*/

        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
            T.show(activity, activity!!.getString(R.string.something_went_wrong), 0)
        }
    }


}
