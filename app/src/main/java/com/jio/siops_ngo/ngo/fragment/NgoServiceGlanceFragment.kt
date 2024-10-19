package com.jio.siops_ngo.ngo.fragment


import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
import com.jio.siops_ngo.databinding.FragmentNgoServiceGlanceBinding
import com.jio.siops_ngo.fragment.NgoOpenAlertsHistoryFragment
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.T
import com.jio.siops_ngo.utilities.Utils
import kotlinx.android.synthetic.main.activity_main.*

import kotlinx.coroutines.*

/**
 * A simple [Fragment] subclass.
 */
class NgoServiceGlanceFragment : Fragment() {
    var mBinding: FragmentNgoServiceGlanceBinding? = null
    var msg: HashMap<String, Any>? = null
    var apiResponseOpenIncodent: HashMap<String, Any>? = null
    var apiResponseOpenAlerts: HashMap<String, Any>? = null
    var apiResponseOpenChange: HashMap<String, Any>? = null
    var apiResponseOpenActionItem: HashMap<String, Any>? = null
    var apiResponseProblemTicket: HashMap<String, Any>? = null
    var apiResponseOpenAction: HashMap<String, Any>? = null

    var hotFixList: ArrayList<Map<String, Any>>? = null
    var defectList: ArrayList<Map<String, Any>>? = null
    var emergencyList: ArrayList<Map<String, Any>>? = null
    var normalList: ArrayList<Map<String, Any>>? = null
    var macDList: ArrayList<Map<String, Any>>? = null
    var pendingClosureList: ArrayList<Map<String, Any>>? = null
    var pendingImplementaionList: ArrayList<Map<String, Any>>? = null
    var pendingActionList: ArrayList<Map<String, Any>>? = null
    var overdueActionList: ArrayList<Map<String, Any>>? = null
    var pendingRCAList: ArrayList<Map<String, Any>>? = null
    var rcaactionItemList: ArrayList<Map<String, Any>>? = null
    var usefulLinksNgo: List<Map<String, Any>>? = null
    var commonBean: CommonBean? = null
    var isSessionInValid: Boolean? = false

    companion object {
        fun newInstance() = NgoServiceGlanceFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_ngo_service_glance,
            container,
            false
        )
        return mBinding!!.root

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        PreferenceUtility.addString(activity as MainActivity,"noti","0");

        PreferenceUtility.addString(
            activity as MainActivity,
            MyConstants.USER_TYPE,
            "NGO"
        )
        val txtBadge = (activity as MainActivity).txtBadge

        var notificationCount: String =
            PreferenceUtility.getString(activity, MyConstants.NOTIFICATION_COUNT, "")

        if (!notificationCount.equals("0")) {
            txtBadge.visibility = View.VISIBLE
            txtBadge.setText(notificationCount)
        } else {
            txtBadge.visibility = View.GONE
        }
        fetchData(Busicode.NGOOpenAlerts)
        fetchData(Busicode.NGOOpenIncident)
        fetchData(Busicode.NGOOpenChange)
        fetchData(Busicode.NGOOpenDefect)
        fetchData(Busicode.NGOProblemTicket)
        fetchData(Busicode.NGOOpenAction)



        if (activity != null) {
            mBinding!!.itemsswipetorefresh.setProgressBackgroundColorSchemeColor(
                ContextCompat.getColor(
                    (activity as MainActivity),
                    R.color.toolbar_bg
                )
            )
        }

        mBinding!!.itemsswipetorefresh.setColorSchemeColors(Color.WHITE)
        mBinding!!.itemsswipetorefresh.setOnRefreshListener {
            fetchData(Busicode.NGOOpenAlerts)
            fetchData(Busicode.NGOOpenIncident)
            fetchData(Busicode.NGOOpenChange)
            fetchData(Busicode.NGOOpenDefect)
            fetchData(Busicode.NGOProblemTicket)
            fetchData(Busicode.NGOOpenAction)
        }

        if (activity != null) {
            usefulLinksNgo = Utils.getListFromJson(activity!!, MyConstants.USEFUL_LINKS_NGO)

            /* mBinding!!.rvParent.apply {
                 layoutManager =
                     LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)

                 adapter = UsefulLinksAdapter( usefulLinksNgo!!, activity as MainActivity,commonBean!!
                 )
             }*/
            var adapter = UsefulLinksAdapter(
                usefulLinksNgo!!,
                activity as MainActivity,
                commonBean!!
            )

//            val gridLayoutManagerUsefulLinks = GridLayoutManager(activity, 3)



            var horizonalLayoutManager = LinearLayoutManager(
                activity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            mBinding!!.rvParent!!.layoutManager =
                horizonalLayoutManager


            mBinding!!.rvParent!!.layoutManager =
                horizonalLayoutManager
            mBinding!!.rvParent!!.itemAnimator =
                DefaultItemAnimator()

            mBinding!!.rvParent!!.adapter = adapter

        }


        mBinding!!.txtAlarms!!.setOnClickListener {


            val commonBean = CommonBean()
            var ngoOpenAlertsHistoryFragment = NgoOpenAlertsHistoryFragment.newInstance()
            (activity as MainActivity).openFragments(
                ngoOpenAlertsHistoryFragment,
                commonBean,
                true,
                true
            )
        }

        mBinding!!.card3.setOnClickListener {


            if (mBinding!!.criticalCount3.text != "0") {
                val commonBeanData = CommonBean()
                commonBeanData.setmTitle(MyConstants.NGO)

                PreferenceUtility.addString(activity,"title", mBinding!!.criticalTitle3.text.toString())
                /*commonBeanData.usefulLinksNgo = commonBean!!.usefulLinksNgo
            commonBeanData.usefulLinksNgo = commonBean!!.usefulLinksNgo
            commonBeanData.usefulLinksBusinessView = commonBean!!.usefulLinksBusinessView*/
                //var dashboardNgoFragment = DashboardNgoFragment.newInstance()
                var ngoServiceGlanceFragment = NgoServiceGlanceDetailsFragment.newInstance()
                ngoServiceGlanceFragment.setData(
                    commonBeanData,
                    apiResponseOpenAlerts,
                    mBinding!!.criticalTitle3.text.toString(),
                    mBinding!!.txtOpenAlartCount.text.toString()
                )
                (activity as MainActivity).openFragments(
                    ngoServiceGlanceFragment,
                    commonBeanData,
                    true,
                    true
                )

            }

        }


        mBinding!!.card1.setOnClickListener {


            if (mBinding!!.fatalCount1.text.toString() != "0") {

                PreferenceUtility.addString(activity,"title", mBinding!!.fatelTitle1.text.toString())
                val commonBeanData = CommonBean()
                commonBeanData.setmTitle(MyConstants.NGO)
                var ngoServiceGlanceFragment = NgoServiceGlanceDetailsFragment.newInstance()
                ngoServiceGlanceFragment.setData(
                    commonBeanData,
                    apiResponseOpenAlerts,
                    mBinding!!.fatelTitle1.text.toString(),
                    mBinding!!.txtOpenAlartCount.text.toString()
                )
                (activity as MainActivity).openFragments(
                    ngoServiceGlanceFragment,
                    commonBeanData,
                    true,
                    true
                )
            }
        }
        mBinding!!.txtMoreDetailsOi.setOnClickListener {


            if (mBinding!!.cnstrntLOi!!.visibility == View.VISIBLE) {
                mBinding!!.txtMoreDetailsOi.text = activity!!.getString(R.string.viewdetails)
                mBinding!!.cnstrntLOi!!.visibility = View.GONE
                mBinding!!.txtMoreDetailsOi!!.setBackgroundDrawable(
                    activity!!.resources.getDrawable(
                        R.drawable.grey_rounded_corner_bg
                    )
                )
                mBinding!!.txtMoreDetailsOi!!.setTextColor(activity!!.resources.getColor(R.color.text_color_grey))
            } else {
                mBinding!!.txtMoreDetailsOi!!.text = activity!!.getString(R.string.hide_details)
                mBinding!!.cnstrntLOi!!.visibility = View.VISIBLE
                mBinding!!.txtMoreDetailsOi!!.setBackgroundDrawable(
                    activity!!.resources.getDrawable(
                        R.drawable.blue_rounded_corner_bg
                    )
                )
                mBinding!!.txtMoreDetailsOi!!.setTextColor(activity!!.resources.getColor(R.color.white))

            }

        }



        mBinding!!.txtOpenChargeMoreDetails.setOnClickListener {
            if (mBinding!!.constantOpenchange!!.visibility == View.VISIBLE) {
                mBinding!!.txtOpenChargeMoreDetails.text = activity!!.getString(R.string.viewdetails)
                mBinding!!.constantOpenchange!!.visibility = View.GONE
                mBinding!!.txtOpenChargeMoreDetails!!.setBackgroundDrawable(
                    activity!!.resources.getDrawable(
                        R.drawable.grey_rounded_corner_bg
                    )
                )
                mBinding!!.txtOpenChargeMoreDetails!!.setTextColor(activity!!.resources.getColor(R.color.text_color_grey))
            } else {
                mBinding!!.txtOpenChargeMoreDetails!!.text = activity!!.getString(R.string.hide_details)
                mBinding!!.constantOpenchange!!.visibility = View.VISIBLE
                mBinding!!.txtOpenChargeMoreDetails!!.setBackgroundDrawable(
                    activity!!.resources.getDrawable(
                        R.drawable.blue_rounded_corner_bg
                    )
                )
                mBinding!!.txtOpenChargeMoreDetails!!.setTextColor(activity!!.resources.getColor(R.color.white))

            }

        }


    }

    fun fetchData(busicode: String) {
        //  (activity as MainActivity).showProgressBar()

        val requestBody = HashMap<String, Any>()
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "userInfo"
        requestBody["appRoleCode"] = "726"
        CoroutineScope(Dispatchers.IO).launch {

            var job = async {
                BaseCoroutines().fetchData(
                    requestBody,
                    busicode,
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
                    filterData(response, busicode)
                } else if (response!!.errorCode != null && response!!.errorCode.equals(MappActor.VERSION_SESSION_INVALID)) {
                    if(!isSessionInValid!!) {
                        isSessionInValid = true
                        (activity as MainActivity).showDialogForSessionExpired(
                            (activity as MainActivity).resources.getString(
                                R.string.session_expired
                            ), (activity as MainActivity)
                        )
                    }
                } else if (response!!.errorMsg != null) {

                    if(busicode.equals(Busicode.NGOOpenAlerts)){
                        mBinding!!.progress1.visibility=View.GONE
                           mBinding!!.txtOpenAlartMsg1.text=response!!.errorMsg!!
                        }

                    if(busicode.equals(Busicode.NGOOpenIncident)){
                        mBinding!!.progress2.visibility=View.GONE
                        mBinding!!.txtOpenAlartMsg2.text=response!!.errorMsg!!
                    }

                    if(busicode.equals(Busicode.NGOOpenChange)){
                        mBinding!!.progress3.visibility=View.GONE
                        mBinding!!.txtOpenAlartMsg3.text=response!!.errorMsg!!
                    }

                    if(busicode.equals(Busicode.NGOOpenDefect)){
                        mBinding!!.progress4.visibility=View.GONE
                        mBinding!!.txtOpenAlartMsg4.text=response!!.errorMsg!!
                    }

                    if(busicode.equals(Busicode.NGOProblemTicket)){
                        mBinding!!.progress5.visibility=View.GONE
                        mBinding!!.txtOpenAlartMsg5.text=response!!.errorMsg!!
                    }

                    if(busicode.equals(Busicode.NGOOpenAction)){
                        mBinding!!.progress6.visibility=View.GONE
                        mBinding!!.txtOpenAlartMsg6.text=response!!.errorMsg!!
                    }
                   // T.show(activity, response!!.errorMsg!!, 0)
                } else {
                  //  T.show(activity, "Something went wrong!", 0)

                    if(busicode.equals(Busicode.NGOOpenAlerts)){
                        mBinding!!.progress1.visibility=View.GONE
                        mBinding!!.txtOpenAlartMsg1.text="Something went wrong!"
                    }

                    if(busicode.equals(Busicode.NGOOpenIncident)){
                        mBinding!!.progress2.visibility=View.GONE
                        mBinding!!.txtOpenAlartMsg2.text="Something went wrong!"
                    }

                    if(busicode.equals(Busicode.NGOOpenChange)){
                        mBinding!!.progress3.visibility=View.GONE
                        mBinding!!.txtOpenAlartMsg3.text="Something went wrong!"
                    }

                    if(busicode.equals(Busicode.NGOOpenDefect)){
                        mBinding!!.progress4.visibility=View.GONE
                        mBinding!!.txtOpenAlartMsg4.text="Something went wrong!"
                    }

                    if(busicode.equals(Busicode.NGOProblemTicket)){
                        mBinding!!.progress5.visibility=View.GONE
                        mBinding!!.txtOpenAlartMsg5.text="Something went wrong!"
                    }

                    if(busicode.equals(Busicode.NGOOpenAction)){
                        mBinding!!.progress6.visibility=View.GONE
                        mBinding!!.txtOpenAlartMsg6.text="Something went wrong!"
                    }
                }
            }

        }
    }


    fun filterData(mCoroutinesResponse: CoroutinesResponse, busicode: String) {

        try {
            msg = mCoroutinesResponse.responseEntity as HashMap<String, Any>

            mBinding!!.itemsswipetorefresh.isRefreshing = false

            mBinding!!.scrollViewId.visibility = View.VISIBLE
            if (msg != null) {
                try {
                    if (busicode.equals(Busicode.NGOOpenAlerts)) {

                        apiResponseOpenAlerts = msg

                        mBinding!!.cardView1.visibility = View.VISIBLE
                        mBinding!!.progress1.visibility = View.GONE
                        if (msg!!.containsKey("fatal") && msg!!["fatal"] != null) {
                            mBinding!!.fatalCount1.text = msg!!["fatal"].toString()
                        }
                        if (msg!!.containsKey("critical") && msg!!["critical"] != null) {
                            mBinding!!.criticalCount3.text = msg!!["critical"].toString()
                        }

                        if (msg!!.containsKey("openAlertCount") && msg!!["openAlertCount"] != null) {
                            mBinding!!.txtOpenAlartCount.text = msg!!["openAlertCount"].toString()
                        }
                    } else if (busicode.equals(Busicode.NGOOpenDefect)) {

                        mBinding!!.cardView4.visibility = View.VISIBLE
                        mBinding!!.progress4.visibility = View.GONE
                        if (msg!!.containsKey("hotFix") && msg!!["hotFix"] != null) {
                            mBinding!!.hotFixCount1.text = msg!!["hotFix"].toString()
                        }
                        if (msg!!.containsKey("defect") && msg!!["defect"] != null) {
                            mBinding!!.bugFixCount2.text = msg!!["defect"].toString()
                        }


                        if (msg!!.containsKey("hotFixList") && msg!!["hotFixList"] != null) {
                            hotFixList = msg!!["hotFixList"] as ArrayList<Map<String, Any>>
                        }

                        if (msg!!.containsKey("defectList") && msg!!["defectList"] != null) {
                            defectList = msg!!["defectList"] as ArrayList<Map<String, Any>>
                        }

                        mBinding!!.cardOpenDeffected1.setOnClickListener {

                            if (hotFixList != null) {


                                PreferenceUtility.addString(activity,"title", mBinding!!.hotFixTitle1.text.toString())
                                val commonBeanData = CommonBean()
                                // commonBeanData.setmTitle(MyConstants.NGO)
                                var ngoNgoOpenDeffectsFragment =
                                    NgoOpenDeffectsFragment.newInstance()
                                ngoNgoOpenDeffectsFragment.setData(
                                    commonBeanData,
                                    hotFixList,
                                    mBinding!!.hotFixTitle1.text.toString(),
                                    mBinding!!.hotFixCount1.text.toString()
                                )
                                (activity as MainActivity).openFragments(
                                    ngoNgoOpenDeffectsFragment,
                                    commonBeanData,
                                    true,
                                    true
                                )
                            }
                        }


                        mBinding!!.cardOpenDeffecte2.setOnClickListener {


                            if (defectList != null) {


                                val commonBeanData = CommonBean()

                                PreferenceUtility.addString(activity,"title", mBinding!!.bugFixTitle2.text.toString())

                                // commonBeanData.setmTitle(MyConstants.NGO)
                                var ngoNgoOpenDeffectsFragment =
                                    NgoOpenDeffectsFragment.newInstance()
                                ngoNgoOpenDeffectsFragment.setData(
                                    commonBeanData,
                                    defectList,
                                    mBinding!!.bugFixTitle2.text.toString(),
                                    mBinding!!.bugFixCount2.text.toString()
                                )
                                (activity as MainActivity).openFragments(
                                    ngoNgoOpenDeffectsFragment,
                                    commonBeanData,
                                    true,
                                    true
                                )
                            }
                        }
                    } else if (busicode.equals(Busicode.NGOOpenChange)) {
                        mBinding!!.cardView3.visibility = View.VISIBLE
                        mBinding!!.progress3.visibility = View.GONE

                        apiResponseOpenChange = msg
                        if (msg!!.containsKey("emergency") && msg!!["emergency"] != null) {
                            mBinding!!.opnechargeCount1.text = msg!!["emergency"].toString()
                        }
                        if (msg!!.containsKey("normal") && msg!!["normal"] != null) {
                            mBinding!!.openchargeCount2.text = msg!!["normal"].toString()
                        }

                        if (msg!!.containsKey("macD") && msg!!["macD"] != null) {
                            mBinding!!.openchareCount3.text = msg!!["macD"].toString()
                        }
                        if (msg!!.containsKey("pendingClosure") && msg!!["pendingClosure"] != null) {
                            mBinding!!.pendingCount1.text = msg!!["pendingClosure"].toString()
                        }
                        if (msg!!.containsKey("pendingImplementaion") && msg!!["pendingImplementaion"] != null) {
                            mBinding!!.pendingCount4.text = msg!!["pendingImplementaion"].toString()
                        }


                        if (msg!!.containsKey("emergencyList") && msg!!["emergencyList"] != null) {
                            emergencyList = msg!!["emergencyList"] as ArrayList<Map<String, Any>>
                        }

                        if (msg!!.containsKey("normalList") && msg!!["normalList"] != null) {
                            normalList = msg!!["normalList"] as ArrayList<Map<String, Any>>
                        }

                        if (msg!!.containsKey("macDList") && msg!!["macDList"] != null) {
                            macDList = msg!!["macDList"] as ArrayList<Map<String, Any>>
                        }

                        if (msg!!.containsKey("pendingClosureList") && msg!!["pendingClosureList"] != null) {
                            pendingClosureList =
                                msg!!["pendingClosureList"] as ArrayList<Map<String, Any>>
                        }

                        if (msg!!.containsKey("pendingImplementaionList") && msg!!["pendingImplementaionList"] != null) {
                            pendingImplementaionList =
                                msg!!["pendingImplementaionList"] as ArrayList<Map<String, Any>>
                        }

                        mBinding!!.cardopencharge.setOnClickListener {
                            val commonBeanData = CommonBean()

                            PreferenceUtility.addString(activity,"title", mBinding!!.openchargeTitle1.text.toString())
                            commonBeanData.setmTitle(MyConstants.NGO)
                            var ngoNgoOpenChangeFragment = NgoOpenChangeFragment.newInstance()
                            ngoNgoOpenChangeFragment.setData(
                                commonBeanData,
                                emergencyList!!,
                                0,
                                mBinding!!.openchargeTitle1.text.toString(),
                                mBinding!!.opnechargeCount1.text.toString()
                            )
                            (activity as MainActivity).openFragments(
                                ngoNgoOpenChangeFragment,
                                commonBeanData,
                                true,
                                true
                            )

                        }
                        mBinding!!.cardopencharge2.setOnClickListener {
                            val commonBeanData = CommonBean()
                            commonBeanData.setmTitle(MyConstants.NGO)
                            PreferenceUtility.addString(activity,"title", mBinding!!.opnechareTitle2.text.toString())

                            var ngoNgoOpenChangeFragment = NgoOpenChangeFragment.newInstance()
                            ngoNgoOpenChangeFragment.setData(
                                commonBeanData,
                                normalList,
                                1,
                                mBinding!!.opnechareTitle2.text.toString(),
                                mBinding!!.openchargeCount2.text.toString()
                            )
                            (activity as MainActivity).openFragments(
                                ngoNgoOpenChangeFragment,
                                commonBeanData,
                                true,
                                true
                            )

                        }

                        mBinding!!.cardopencharge3.setOnClickListener {
                            val commonBeanData = CommonBean()
                            commonBeanData.setmTitle(MyConstants.NGO)

                            PreferenceUtility.addString(activity,"title", mBinding!!.opnechargeTitle3.text.toString())
                            var ngoNgoOpenChangeFragment = NgoOpenChangeFragment.newInstance()
                            ngoNgoOpenChangeFragment.setData(
                                commonBeanData,
                                macDList,
                                1,
                                mBinding!!.opnechargeTitle3.text.toString(),
                                mBinding!!.openchareCount3.text.toString()
                            )
                            (activity as MainActivity).openFragments(
                                ngoNgoOpenChangeFragment,
                                commonBeanData,
                                true,
                                true
                            )

                        }

                        mBinding!!.card1Charge.setOnClickListener {
                            val commonBeanData = CommonBean()
                            commonBeanData.setmTitle(MyConstants.NGO)
                            PreferenceUtility.addString(activity,"title", mBinding!!.pendingTitel1.text.toString())
                            var ngoNgoOpenChangeFragment = NgoOpenChangeFragment.newInstance()
                            ngoNgoOpenChangeFragment.setData(
                                commonBeanData,
                                pendingClosureList,
                                1,
                                mBinding!!.pendingTitel1.text.toString(),
                                mBinding!!.pendingCount1.text.toString()
                            )
                            (activity as MainActivity).openFragments(
                                ngoNgoOpenChangeFragment,
                                commonBeanData,
                                true,
                                true
                            )
                        }
                        mBinding!!.card4Charge.setOnClickListener {
                            val commonBeanData = CommonBean()
                            commonBeanData.setmTitle(MyConstants.NGO)
                            PreferenceUtility.addString(activity,"title", mBinding!!.pendingTitel2.text.toString())
                            var ngoNgoOpenChangeFragment = NgoOpenChangeFragment.newInstance()
                            ngoNgoOpenChangeFragment.setData(
                                commonBeanData,
                                pendingImplementaionList,
                                1,
                                mBinding!!.pendingTitel2.text.toString(),
                                mBinding!!.pendingCount4.text.toString()
                            )
                            (activity as MainActivity).openFragments(
                                ngoNgoOpenChangeFragment,
                                commonBeanData,
                                true,
                                true
                            )
                        }
                    } else if (busicode.equals(Busicode.NGOOpenIncident)) {
                        apiResponseOpenIncodent = msg

                        mBinding!!.cardView2.visibility = View.VISIBLE
                        mBinding!!.progress2.visibility = View.GONE
                        if (msg!!.containsKey("businessImpactingCount") && msg!!["businessImpactingCount"] != null) {
                            mBinding!!.txtIncidentCount1.text =
                                msg!!["businessImpactingCount"].toString()
                        }
                        if (msg!!.containsKey("nonBusinessImpactingCount") && msg!!["nonBusinessImpactingCount"] != null) {
                            mBinding!!.txtIncidentCount2.text =
                                msg!!["nonBusinessImpactingCount"].toString()
                        }
                        if (msg!!.containsKey("s1Count") && msg!!["s1Count"] != null) {
                            mBinding!!.txtSeverityCount1.text = msg!!["s1Count"].toString()
                        }
                        if (msg!!.containsKey("s2Count") && msg!!["s2Count"] != null) {
                            mBinding!!.txtSeverityCount2.text = msg!!["s2Count"].toString()
                        }
                        if (msg!!.containsKey("s3Count") && msg!!["s3Count"] != null) {
                            mBinding!!.txtSeverityCount3.text = msg!!["s3Count"].toString()
                        }
                        if (msg!!.containsKey("s4Count") && msg!!["s4Count"] != null) {
                            mBinding!!.txtSeverityCount4.text = msg!!["s4Count"].toString()
                        }


                        mBinding!!.cardOiBusiness.setOnClickListener {
                            val commonBeanData = CommonBean()
                            commonBeanData.setmTitle(MyConstants.NGO)
                            var ngoOpenIncidentFragment = NgoOpenIncidentFragment.newInstance()
                            ngoOpenIncidentFragment.setData(
                                commonBeanData,
                                apiResponseOpenIncodent,
                                0
                            )
                            (activity as MainActivity).openFragments(
                                ngoOpenIncidentFragment,
                                commonBeanData,
                                true,
                                true
                            )

                        }
                        mBinding!!.cardOiNonBusiness.setOnClickListener {
                            val commonBeanData = CommonBean()
                            commonBeanData.setmTitle(MyConstants.NGO)
                            var ngoOpenIncidentFragment = NgoOpenIncidentFragment.newInstance()
                            ngoOpenIncidentFragment.setData(
                                commonBeanData,
                                apiResponseOpenIncodent,
                                1
                            )
                            (activity as MainActivity).openFragments(
                                ngoOpenIncidentFragment,
                                commonBeanData,
                                true,
                                true
                            )

                        }


                    } else if (busicode.equals(Busicode.NGOProblemTicket)) {
                        apiResponseProblemTicket = msg

                        mBinding!!.cardView5.visibility = View.VISIBLE
                        mBinding!!.progress5.visibility = View.GONE

                        if (msg!!.containsKey("pendingRCA") && msg!!["pendingRCA"] != null) {
                            mBinding!!.pendingRcaCount1.text = msg!!["pendingRCA"].toString()

                        }

                        if (msg!!.containsKey("rcaactionItem") && msg!!["rcaactionItem"] != null) {
                            mBinding!!.pendingCount3.text = msg!!["rcaactionItem"].toString()

                        }

                        if (msg!!.containsKey("rcaactionItemList") && msg!!["rcaactionItemList"] != null) {
                            rcaactionItemList =
                                msg!!["rcaactionItemList"] as ArrayList<Map<String, Any>>
                        }

                        if (msg!!.containsKey("pendingRCAList") && msg!!["pendingRCAList"] != null) {
                            pendingRCAList =
                                msg!!["pendingRCAList"] as ArrayList<Map<String, Any>>
                        }

                        mBinding!!.card1Problem.setOnClickListener {
                            val commonBeanData = CommonBean()
                            commonBeanData.setmTitle(MyConstants.NGO)
                            PreferenceUtility.addString(activity,"title", mBinding!!.pendingTitle1.text.toString())

                            var ngoProblemTicketFragment = NgoProblemTicketFragment.newInstance()
                            ngoProblemTicketFragment.setData(
                                pendingRCAList!!,
                                0, mBinding!!.pendingTitle1!!.text.toString(),
                                mBinding!!.pendingRcaCount1!!.text.toString()
                            )
                            (activity as MainActivity).openFragments(
                                ngoProblemTicketFragment,
                                commonBeanData,
                                true,
                                true
                            )
                        }

                        mBinding!!.card3Problem.setOnClickListener {
                            val commonBeanData = CommonBean()
                            commonBeanData.setmTitle(MyConstants.NGO)
                            PreferenceUtility.addString(activity,"title", mBinding!!.pendingTitle3.text.toString())

                            var ngoProblemTicketFragment = NgoProblemTicketFragment.newInstance()
                            ngoProblemTicketFragment.setData(
                                rcaactionItemList!!,
                                1, mBinding!!.pendingTitle3!!.text.toString(),
                                mBinding!!.pendingCount3!!.text.toString()
                            )
                            (activity as MainActivity).openFragments(
                                ngoProblemTicketFragment,
                                commonBeanData,
                                true,
                                true
                            )
                        }

                    } else if (busicode.equals(Busicode.NGOOpenAction)) {
                        apiResponseOpenAction = msg
                        mBinding!!.cardView6.visibility = View.VISIBLE
                        mBinding!!.progress6.visibility = View.GONE
                        if (msg!!.containsKey("pendingAction") && msg!!["pendingAction"] != null) {
                            mBinding!!.pendingActionCount1.text = msg!!["pendingAction"].toString()

                        }
                        if (msg!!.containsKey("overdueAction") && msg!!["overdueAction"] != null) {
                            mBinding!!.pendingActionCount3.text = msg!!["overdueAction"].toString()

                        }
                        if (msg!!.containsKey("pendingActionList") && msg!!["pendingActionList"] != null) {
                            pendingActionList =
                                msg!!["pendingActionList"] as ArrayList<Map<String, Any>>
                        }

                        if (msg!!.containsKey("overdueActionList") && msg!!["overdueActionList"] != null) {
                            overdueActionList =
                                msg!!["overdueActionList"] as ArrayList<Map<String, Any>>
                        }

                        mBinding!!.card1Action.setOnClickListener {
                            val commonBeanData = CommonBean()
                            commonBeanData.setmTitle(MyConstants.NGO)
                            PreferenceUtility.addString(activity,"title", mBinding!!.pendingActionTitle1.text.toString())

                            var ngoOpenActionFragment = NgoOpenActionFragment.newInstance()
                            ngoOpenActionFragment.setData(
                                pendingActionList!!,
                                0, mBinding!!.pendingActionTitle1!!.text.toString(),
                                mBinding!!.pendingActionCount1!!.text.toString()
                            )
                            (activity as MainActivity).openFragments(
                                ngoOpenActionFragment,
                                commonBeanData,
                                true,
                                true
                            )
                        }

                        mBinding!!.card3Action.setOnClickListener {
                            val commonBeanData = CommonBean()
                            commonBeanData.setmTitle(MyConstants.NGO)
                            PreferenceUtility.addString(activity,"title", mBinding!!.pendingActionTitle3.text.toString())

                            var ngoOpenActionFragment = NgoOpenActionFragment.newInstance()
                            ngoOpenActionFragment.setData(
                                overdueActionList!!,
                                1, mBinding!!.pendingActionTitle3!!.text.toString(),
                                mBinding!!.pendingActionCount3!!.text.toString()
                            )
                            (activity as MainActivity).openFragments(
                                ngoOpenActionFragment,
                                commonBeanData,
                                true,
                                true
                            )
                        }

                    }


                    //   ngoData = msg["sites"] as ArrayList<Map<String, Any>>


                } catch (e: Exception) {
                    MyExceptionHandler.handle(e)
                }
            }
        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
            T.show(activity, getString(R.string.something_went_wrong), 0)
        }
    }

    fun setData(commonBean: CommonBean) {
        this.commonBean = commonBean
    }


}
