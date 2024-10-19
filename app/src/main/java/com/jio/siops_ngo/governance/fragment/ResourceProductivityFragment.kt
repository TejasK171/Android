package com.jio.siops_ngo.governance.fragment


import android.os.Bundle
import android.util.Log
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
import com.jio.siops.ngo.adapter.ResourceProductivityItemAdapter
import com.jio.siops.ngo.adapter.TimeTrackingViewSimilarAdapter
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R

import com.jio.siops_ngo.databinding.FragmentResourceProductivityBinding
import com.jio.siops_ngo.databinding.FragmentTimeTrackingViewSimilarBinding
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.T
import kotlinx.coroutines.*
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast


/**
 * A simple [Fragment] subclass.
 */
class ResourceProductivityFragment : Fragment() {
    var mBinding: FragmentResourceProductivityBinding? = null
    var listData: ArrayList<Map<String, Any>>? = null
    var applicationsCategoryList = ArrayList<Map<String, Any>>()
    var applicationsList = ArrayList<Map<String, Any>>()
    var applicationsListNames = ArrayList<String>()
    var autoCompleteapplicationsListNames = ArrayList<String>()
    var applicationsListMap = HashMap<String, List<Map<String, Any>>>()
    var appListSelected = ArrayList<Map<String, Any>>()
    var appListSearched = ArrayList<Map<String, Any>>()

    var onBoardedToNgoText = "OnBoarded To NGO"
    var notOnBoardedToNgoText = "Not onBoarded To NGO"
    var exemptedFromNgoText = "Exempted From NGO"
    var buList: ArrayList<String>? = ArrayList()

    companion object {
        fun newInstance() = ResourceProductivityFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_resource_productivity,
            container,
            false
        )
        return mBinding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fetchData()






        mBinding!!.autoTextView.onItemClickListener = AdapterView.OnItemClickListener{
                parent,view,position,id->

            var searchedText = parent.getItemAtPosition(position).toString()
            appListSearched.clear()
            if (searchedText.trim().length == 0) {
                var adapter =
                    ResourceProductivityItemAdapter(activity as MainActivity?, appListSelected)
                mBinding!!.rvResourceProductivity.layoutManager = LinearLayoutManager(activity)
                mBinding!!.rvResourceProductivity.itemAnimator = DefaultItemAnimator()
                mBinding!!.rvResourceProductivity!!.adapter = adapter
                adapter!!.notifyDataSetChanged()
            } else {

                for ((index, value) in applicationsListNames!!.withIndex()) {
                    val text = value
                    //if the existing elements contains the search input
                    if (!text.equals(onBoardedToNgoText) && !text.equals(notOnBoardedToNgoText) && !text.equals(
                            exemptedFromNgoText
                        )
                    ) {
                        if (text.toLowerCase().contains(searchedText.toLowerCase())) {
//
                            Log.e("text", "" + index)
                            appListSearched.add(appListSelected.get(index))
                            var adapter = ResourceProductivityItemAdapter(
                                activity as MainActivity?,
                                appListSearched
                            )
                            mBinding!!.rvResourceProductivity.layoutManager =
                                LinearLayoutManager(activity)
                            mBinding!!.rvResourceProductivity.itemAnimator =
                                DefaultItemAnimator()
                            mBinding!!.rvResourceProductivity!!.adapter = adapter
                            adapter!!.notifyDataSetChanged()
                        }
                    }
                }
            }




        }

        mBinding!!.autoTextView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                var searchedText = mBinding!!.autoTextView.text.toString()
                if (searchedText.trim().length == 0) {
                    var adapter =
                        ResourceProductivityItemAdapter(activity as MainActivity?, appListSelected)
                    mBinding!!.rvResourceProductivity.layoutManager = LinearLayoutManager(activity)
                    mBinding!!.rvResourceProductivity.itemAnimator = DefaultItemAnimator()
                    mBinding!!.rvResourceProductivity!!.adapter = adapter
                    adapter!!.notifyDataSetChanged()
                }

            }
        })






        /*mBinding!!.edtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE|| actionId == EditorInfo.IME_ACTION_GO) {

                Log.e("EDT", mBinding!!.edtSearch.text.toString())
                var searchedText = mBinding!!.edtSearch.text.toString()
                appListSearched.clear()
                if (searchedText.trim().length == 0) {
                    var adapter =
                        ResourceProductivityItemAdapter(activity as MainActivity?, appListSelected)
                    mBinding!!.rvResourceProductivity.layoutManager = LinearLayoutManager(activity)
                    mBinding!!.rvResourceProductivity.itemAnimator = DefaultItemAnimator()
                    mBinding!!.rvResourceProductivity!!.adapter = adapter
                    adapter!!.notifyDataSetChanged()
                } else {

                    for ((index, value) in applicationsListNames!!.withIndex()) {
                        val text = value
                        //if the existing elements contains the search input
                        if (!text.equals(onBoardedToNgoText) && !text.equals(notOnBoardedToNgoText) && !text.equals(
                                exemptedFromNgoText
                            )
                        ) {
                            if (text.toLowerCase().contains(searchedText.toLowerCase())) {
//
                                Log.e("text", "" + index)
                                appListSearched.add(appListSelected.get(index))
                                var adapter = ResourceProductivityItemAdapter(
                                    activity as MainActivity?,
                                    appListSearched
                                )
                                mBinding!!.rvResourceProductivity.layoutManager =
                                    LinearLayoutManager(activity)
                                mBinding!!.rvResourceProductivity.itemAnimator =
                                    DefaultItemAnimator()
                                mBinding!!.rvResourceProductivity!!.adapter = adapter
                                adapter!!.notifyDataSetChanged()
                            }
                        }
                    }
                }



                true
            }
            false
        }*/
        /*mBinding!!.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                var searchedText = mBinding!!.edtSearch.text.toString()
                if (searchedText.trim().length == 0) {
                    var adapter =
                        ResourceProductivityItemAdapter(activity as MainActivity?, appListSelected)
                    mBinding!!.rvResourceProductivity.layoutManager = LinearLayoutManager(activity)
                    mBinding!!.rvResourceProductivity.itemAnimator = DefaultItemAnimator()
                    mBinding!!.rvResourceProductivity!!.adapter = adapter
                    adapter!!.notifyDataSetChanged()
                }

            }
        })*/


        mBinding!!.spnrBu.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                arg1: View,
                position: Int,
                arg3: Long
            ) { // TODO Auto-generated method stub
                Log.e("itemText", parent!!.getItemAtPosition(position).toString())

                var selectedBU = parent!!.getItemAtPosition(position).toString()
//                mBinding!!.edtSearch.setText("")
                mBinding!!.autoTextView.setText("")

                if (applicationsListMap.containsKey(selectedBU)) {
                    applicationsListNames = ArrayList()
                    autoCompleteapplicationsListNames = ArrayList()
                    appListSelected =
                        applicationsListMap[selectedBU] as ArrayList<Map<String, Any>>
                    for ((index, value) in appListSelected!!.withIndex()) {
                        val map = value

                        if (map.containsKey("appName") && map["appName"] != null) {
                            applicationsListNames.add(map["appName"].toString())
                            if (!map["appName"].toString().equals(onBoardedToNgoText) && !map["appName"].toString().equals(notOnBoardedToNgoText) && !map["appName"].toString().equals(
                                    exemptedFromNgoText
                                )
                            ) {
                                autoCompleteapplicationsListNames.add(map["appName"].toString())
                            }
                        }
                    }


                    var adapter = ResourceProductivityItemAdapter(
                        activity as MainActivity?,
                        appListSelected!!
                    )
                    mBinding!!.rvResourceProductivity.layoutManager = LinearLayoutManager(activity)
                    mBinding!!.rvResourceProductivity.itemAnimator = DefaultItemAnimator()
                    mBinding!!.rvResourceProductivity!!.adapter = adapter
                    adapter!!.notifyDataSetChanged()


                    val autoCompleteAdapter
                            = ArrayAdapter(activity as MainActivity,
                        R.layout.spinner_text, autoCompleteapplicationsListNames)
                    mBinding!!.autoTextView.setAdapter(autoCompleteAdapter)
                }
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) { // TODO Auto-generated method stub
            }
        })

    }

    fun fetchData() {
        (activity as MainActivity).showProgressBar()

        val requestBody = HashMap<String, Any>()
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "productivityRequest"
        requestBody["jwtToken"] = "POSTMAN-TESTING"
        requestBody["view"] = "live"
        //   requestBody["appRoleCode"] = "741"
        CoroutineScope(Dispatchers.IO).launch {

            var job = async {
                BaseCoroutines().fetchData(
                    requestBody,
                    Busicode.ProductivityDashboardBuAndApplications,
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
                    (activity as MainActivity).showDialogForSessionExpired(
                        (activity as MainActivity).resources.getString(
                            R.string.session_expired
                        ), (activity as MainActivity)
                    )
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
                    listData = msg["productivtyDashboardList"] as ArrayList<Map<String, Any>>
                    buList!!.clear()

                    for ((index, value) in listData!!.withIndex()) {
                        val map = value
                        applicationsList = ArrayList()
                        applicationsList= ArrayList()
                        if (map.containsKey("businessUnit") && map["businessUnit"] != null) {
                            Log.e("map ", map["businessUnit"].toString())
                            var businessUnit = map["businessUnit"].toString()
                            if (map.containsKey("onboardedToNgoList") && map["onboardedToNgoList"] != null) {
                                applicationsCategoryList =
                                    map["onboardedToNgoList"] as ArrayList<Map<String, Any>>
                                if (applicationsCategoryList.size > 0) {

                                    var headerMap: Map<String, Any> = mutableMapOf(
                                        "appName" to onBoardedToNgoText,
                                        "count" to applicationsCategoryList.size.toString()
                                    )
                                    applicationsCategoryList!!.add(0, headerMap!!)

                                }
                                applicationsList.addAll(applicationsCategoryList)
                            }
                            if (map.containsKey("notOnboardedToNgoList") && map["notOnboardedToNgoList"] != null) {
                                applicationsCategoryList =
                                    map["notOnboardedToNgoList"] as ArrayList<Map<String, Any>>
                                if (applicationsCategoryList.size > 0) {

                                    var headerMap: Map<String, Any> = mutableMapOf(
                                        "appName" to notOnBoardedToNgoText,
                                        "count" to applicationsCategoryList.size.toString()
                                    )
                                    applicationsCategoryList!!.add(0, headerMap!!)

                                }
                                applicationsList.addAll(applicationsCategoryList)
                            }
                            if (map.containsKey("exemptedFromNgoList") && map["exemptedFromNgoList"] != null) {
                                applicationsCategoryList =
                                    map["exemptedFromNgoList"] as ArrayList<Map<String, Any>>
                                if (applicationsCategoryList.size > 0) {


                                    var headerMap: Map<String, Any> = mutableMapOf(
                                        "appName" to exemptedFromNgoText,
                                        "count" to applicationsCategoryList.size.toString()
                                    )
                                    applicationsCategoryList!!.add(0, headerMap!!)

                                }
                                applicationsList.addAll(applicationsCategoryList)
                            }
                            buList!!.add(businessUnit)
                            applicationsListMap.put(businessUnit, applicationsList)
                        }


                    }


                    val buSpinnerAdapter =
                        ArrayAdapter<String>(
                            activity!!,
                            R.layout.spinner_text,
                            buList!!
                        )
                    mBinding!!.spnrBu.setAdapter(buSpinnerAdapter)




                    /*appListSelected =
                        applicationsListMap["JIO TELECOM"] as ArrayList<Map<String, Any>>
                    for ((index, value) in appListSelected!!.withIndex()) {
                        val map = value

                        if (map.containsKey("appName") && map["appName"] != null) {
                            applicationsListNames.add(map["appName"].toString())
                        }
                    }


                    var adapter = ResourceProductivityItemAdapter(
                        activity as MainActivity?,
                        appListSelected!!
                    )
                    mBinding!!.rvResourceProductivity.layoutManager = LinearLayoutManager(activity)
                    mBinding!!.rvResourceProductivity.itemAnimator = DefaultItemAnimator()
                    mBinding!!.rvResourceProductivity!!.adapter = adapter
                    adapter!!.notifyDataSetChanged()*/


                } catch (e: Exception) {
                    MyExceptionHandler.handle(e)
                }
            }
        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
            T.show(activity, getString(R.string.something_went_wrong), 0)
        }
    }
}
