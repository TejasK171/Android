package com.jio.siops_ngo.ngo.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.jio.jioinfra.bean.CommonBean
import com.jio.jioinfra.custom.TextViewMedium
import com.jio.jioinfra.utilities.MyConstants
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.databinding.FragmentNgoServiceGlanceDetailsBinding
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.Utils
import java.sql.DriverManager.println
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class NgoServiceGlanceDetailsFragment : Fragment() {

    var commonBean: CommonBean? = null
   var msg:HashMap<String, Any>?=null
    var openAlertsList:ArrayList<Map<String, Any>>? = null
    var acknowledementList:ArrayList<Map<String, Any>>? = null
    var acknowledementSLAList:ArrayList<Map<String, Any>>? = null
    var applicationList:ArrayList<Map<String, Any>>? = null
    var toolList:ArrayList<Map<String, Any>>? = null
    var infrastructureList:ArrayList<Map<String, Any>>? = null
    var missedList:ArrayList<Map<String, Any>>? = null
    var metList:ArrayList<Map<String, Any>>? = null
    var acknowledgedList:ArrayList<Map<String, Any>>? = null
    var unacknowledgedList:ArrayList<Map<String, Any>>? = null
    var mBinding:FragmentNgoServiceGlanceDetailsBinding?=null
    var title:String?=null
    var openAlartsCount:String?=null
    var apiRequestFormattedDate: String? = null




    companion object{
        fun  newInstance()=
            NgoServiceGlanceDetailsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_ngo_service_glance_details, container, false)
        return mBinding!!.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mBinding!!.clanderId.setOnClickListener {

            if(mBinding!!.dateLl.visibility==View.VISIBLE) {
                mBinding!!.dateLl.visibility = View.GONE
            }else{

                mBinding!!.dateLl.visibility = View.VISIBLE
            }
        }

        var dayes= Utils.getPreviousWeek(7)
        var dayesYear= Utils.getPreviousWeekYear(7)
        var day_1=dayes!!.get(0)
        var day_2=dayes!!.get(1)
        var day_3=dayes!!.get(2)
        var day_4=dayes!!.get(3)
        var day_5=dayes!!.get(4)
        var day_6=dayes!!.get(5)
        var day_7=dayes!!.get(6)
        mBinding!!.txtDay1.text= day_1!!.replace("-","\n")
        mBinding!!.txtDay2.text=day_2!!.replace("-","\n")
        mBinding!!.txtDay3.text=day_3!!.replace("-","\n")
        mBinding!!.txtDay4.text=day_4!!.replace("-","\n")
        mBinding!!.txtDay5.text=day_5!!.replace("-","\n")
        mBinding!!.txtDay6.text=day_6!!.replace("-","\n")
        mBinding!!.txtDay7.text=day_7!!.replace("-","\n")
        val c = Calendar.getInstance().time
        println("Current time => $c")

        val df = SimpleDateFormat("dd/MM/yyyy")
        val formattedDate = df.format(c)
       // mBinding!!.txtDate.text = formattedDate


        val apiRequestDate = SimpleDateFormat("yyyy-MM-dd")
       var apiRequestFormattedDatCuu = apiRequestDate.format(c)

      //  apiRequestFormattedDate = dayesYear!!.get(6).toString()
        mBinding!!.yesterdayid.text=formattedDate

        PreferenceUtility.addString(activity,"date", formattedDate)
       // mBinding!!.yesterdayid.visibility=View.GONE

       // changeViewBg(mBinding!!.txtDay7, mBinding!!.txtDay1,mBinding!!.txtDay2,mBinding!!.txtDay3,mBinding!!.txtDay4,mBinding!!.txtDay5,mBinding!!.txtDay6)


        mBinding!!.txtDay1.setOnClickListener {


           // val apiRequestDate = SimpleDateFormat("yyyy-MM-dd")
            apiRequestFormattedDate = dayesYear!!.get(0).toString()

            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val reqFormat = SimpleDateFormat("dd/MM/yyyy")
            val date = sdf.parse(apiRequestFormattedDate)
            val formattedDate = reqFormat.format(date)
            PreferenceUtility.addString(activity,"date", formattedDate)

            changeViewBg(mBinding!!.txtDay1, mBinding!!.txtDay7,mBinding!!.txtDay2,mBinding!!.txtDay3,mBinding!!.txtDay4,mBinding!!.txtDay5,mBinding!!.txtDay6)


            val commonBeanData = CommonBean()
            commonBeanData.setmTitle(MyConstants.NGO)
            var ngoNgoHistoryFragment =
                NgoHistoryFragment.newInstance()
            ngoNgoHistoryFragment.setData(commonBeanData,title!!,apiRequestFormattedDate!!)
            (activity as MainActivity).openFragments(
                ngoNgoHistoryFragment,
                commonBeanData,
                true,
                true
            )

        }

        mBinding!!.txtDay2.setOnClickListener {


            apiRequestFormattedDate = dayesYear!!.get(1).toString()
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val reqFormat = SimpleDateFormat("dd/MM/yyyy")
            val date = sdf.parse(apiRequestFormattedDate)
            val formattedDate = reqFormat.format(date)
            PreferenceUtility.addString(activity,"date", formattedDate)
            changeViewBg(mBinding!!.txtDay2, mBinding!!.txtDay7,mBinding!!.txtDay1,mBinding!!.txtDay3,mBinding!!.txtDay4,mBinding!!.txtDay5,mBinding!!.txtDay6)
            val commonBeanData = CommonBean()
            commonBeanData.setmTitle(MyConstants.NGO)
            var ngoNgoHistoryFragment =
                NgoHistoryFragment.newInstance()
            ngoNgoHistoryFragment.setData(commonBeanData,title!!,apiRequestFormattedDate!!)
            (activity as MainActivity).openFragments(
                ngoNgoHistoryFragment,
                commonBeanData,
                true,
                true
            )
        }



        mBinding!!.txtDay3.setOnClickListener {


            apiRequestFormattedDate = dayesYear!!.get(2).toString()
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val reqFormat = SimpleDateFormat("dd/MM/yyyy")
            val date = sdf.parse(apiRequestFormattedDate)
            val formattedDate = reqFormat.format(date)
            PreferenceUtility.addString(activity,"date", formattedDate)
            changeViewBg(mBinding!!.txtDay3, mBinding!!.txtDay7,mBinding!!.txtDay1,mBinding!!.txtDay2,mBinding!!.txtDay4,mBinding!!.txtDay5,mBinding!!.txtDay6)

            val commonBeanData = CommonBean()
            commonBeanData.setmTitle(MyConstants.NGO)
            var ngoNgoHistoryFragment =
                NgoHistoryFragment.newInstance()
            ngoNgoHistoryFragment.setData(commonBeanData,title!!,apiRequestFormattedDate!!)
            (activity as MainActivity).openFragments(
                ngoNgoHistoryFragment,
                commonBeanData,
                true,
                true
            )

        }


        mBinding!!.txtDay4.setOnClickListener {


            apiRequestFormattedDate = dayesYear!!.get(3).toString()

            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val reqFormat = SimpleDateFormat("dd/MM/yyyy")
            val date = sdf.parse(apiRequestFormattedDate)
            val formattedDate = reqFormat.format(date)
            PreferenceUtility.addString(activity,"date", formattedDate)
            changeViewBg(mBinding!!.txtDay4, mBinding!!.txtDay7,mBinding!!.txtDay1,mBinding!!.txtDay2,mBinding!!.txtDay3,mBinding!!.txtDay5,mBinding!!.txtDay6)

            val commonBeanData = CommonBean()
            commonBeanData.setmTitle(MyConstants.NGO)
            var ngoNgoHistoryFragment =
                NgoHistoryFragment.newInstance()
            ngoNgoHistoryFragment.setData(commonBeanData,title!!,apiRequestFormattedDate!!)
            (activity as MainActivity).openFragments(
                ngoNgoHistoryFragment,
                commonBeanData,
                true,
                true
            )

        }


        mBinding!!.txtDay5.setOnClickListener {

            apiRequestFormattedDate = dayesYear!!.get(4).toString()

            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val reqFormat = SimpleDateFormat("dd/MM/yyyy")
            val date = sdf.parse(apiRequestFormattedDate)
            val formattedDate = reqFormat.format(date)
            PreferenceUtility.addString(activity,"date", formattedDate)
            changeViewBg(mBinding!!.txtDay5, mBinding!!.txtDay7,mBinding!!.txtDay1,mBinding!!.txtDay2,mBinding!!.txtDay4,mBinding!!.txtDay3,mBinding!!.txtDay6)

            val commonBeanData = CommonBean()
            commonBeanData.setmTitle(MyConstants.NGO)
            var ngoNgoHistoryFragment =
                NgoHistoryFragment.newInstance()
            ngoNgoHistoryFragment.setData(commonBeanData,title!!,apiRequestFormattedDate!!)
            (activity as MainActivity).openFragments(
                ngoNgoHistoryFragment,
                commonBeanData,
                true,
                true
            )

        }


        mBinding!!.txtDay6.setOnClickListener {
            apiRequestFormattedDate = dayesYear!!.get(5).toString()
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val reqFormat = SimpleDateFormat("dd/MM/yyyy")
            val date = sdf.parse(apiRequestFormattedDate)
            val formattedDate = reqFormat.format(date)
            PreferenceUtility.addString(activity,"date", formattedDate)
            changeViewBg(mBinding!!.txtDay6, mBinding!!.txtDay7,mBinding!!.txtDay1,mBinding!!.txtDay2,mBinding!!.txtDay4,mBinding!!.txtDay5,mBinding!!.txtDay3)
            val commonBeanData = CommonBean()
            commonBeanData.setmTitle(MyConstants.NGO)
            var ngoNgoHistoryFragment =
                NgoHistoryFragment.newInstance()
            ngoNgoHistoryFragment.setData(commonBeanData,title!!,apiRequestFormattedDate!!)
            (activity as MainActivity).openFragments(
                ngoNgoHistoryFragment,
                commonBeanData,
                true,
                true
            )
        }
        mBinding!!.txtDay7.setOnClickListener {
            apiRequestFormattedDate = dayesYear!!.get(6).toString()
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val reqFormat = SimpleDateFormat("dd/MM/yyyy")
            val date = sdf.parse(apiRequestFormattedDate)
            val formattedDate = reqFormat.format(date)
            PreferenceUtility.addString(activity,"date", formattedDate)
            changeViewBg(mBinding!!.txtDay7, mBinding!!.txtDay3,mBinding!!.txtDay1,mBinding!!.txtDay2,mBinding!!.txtDay4,mBinding!!.txtDay5,mBinding!!.txtDay6)
            val commonBeanData = CommonBean()
            commonBeanData.setmTitle(MyConstants.NGO)
            var ngoNgoHistoryFragment =
                NgoHistoryFragment.newInstance()
            ngoNgoHistoryFragment.setData(commonBeanData,title!!,apiRequestFormattedDate!!)
            (activity as MainActivity).openFragments(
                ngoNgoHistoryFragment,
                commonBeanData,
                true,
                true
            )
        }


        mBinding!!.header.text="Service Glance - "+title
        mBinding!!.txtOpenAlartCount.text="("+openAlartsCount+")"
        if(msg!!.containsKey("openAlerts")&&msg!!["openAlerts"]!=null){
            openAlertsList= msg!!["openAlerts"] as ArrayList<Map<String, Any>>


            if(Utils.hasIndex(0, openAlertsList!!)){
                if(openAlertsList!!.get(0).containsKey("featureName")&& openAlertsList!!.get(0)["featureName"]!=null){
                    mBinding!!.infraTitle1.text= openAlertsList!!.get(0)["featureName"].toString()
                }
                if(openAlertsList!!.get(0).containsKey("outlierCount")&& openAlertsList!!.get(0)["outlierCount"]!=null){
                    mBinding!!.infraCount1.text= openAlertsList!!.get(0)["outlierCount"].toString()
                }
            }

            if(Utils.hasIndex(1, openAlertsList!!)){
                if(openAlertsList!!.get(1).containsKey("featureName")&& openAlertsList!!.get(1)["featureName"]!=null){
                    mBinding!!.applicationTitle2.text= openAlertsList!!.get(1)["featureName"].toString()
                }
                if(openAlertsList!!.get(1).containsKey("outlierCount")&& openAlertsList!!.get(1)["outlierCount"]!=null){
                    mBinding!!.applicationCount2.text= openAlertsList!!.get(1)["outlierCount"].toString()
                }
            }

            if(Utils.hasIndex(2, openAlertsList!!)){
                if(openAlertsList!!.get(2).containsKey("featureName")&& openAlertsList!!.get(2)["featureName"]!=null){
                    mBinding!!.toolTitle3.text= openAlertsList!!.get(2)["featureName"].toString()
                }
                if(openAlertsList!!.get(0).containsKey("outlierCount")&& openAlertsList!!.get(2)["outlierCount"]!=null){
                    mBinding!!.toolCount3.text= openAlertsList!!.get(2)["outlierCount"].toString()
                }
            }

        }

        if(msg!!.containsKey("acknowledged")&&msg!!["acknowledged"]!=null){
            acknowledementList= msg!!["acknowledement"] as ArrayList<Map<String, Any>>


            if(Utils.hasIndex(0, acknowledementList!!)){
                if(acknowledementList!!.get(0).containsKey("featureName")&& acknowledementList!!.get(0)["featureName"]!=null){
                    mBinding!!.acknowladgedTitle1.text= acknowledementList!!.get(0)["featureName"].toString()
                }
                if(acknowledementList!!.get(0).containsKey("outlierCount")&& acknowledementList!!.get(0)["outlierCount"]!=null){
                    mBinding!!.acknowladgedCount1.text= acknowledementList!!.get(0)["outlierCount"].toString()
                }
            }

            if(Utils.hasIndex(1, acknowledementList!!)){
                if(acknowledementList!!.get(1).containsKey("featureName")&& acknowledementList!!.get(1)["featureName"]!=null){
                    mBinding!!.acknowladgedTitle3.text= acknowledementList!!.get(1)["featureName"].toString()
                }
                if(acknowledementList!!.get(1).containsKey("outlierCount")&& acknowledementList!!.get(1)["outlierCount"]!=null){
                    mBinding!!.acknowladgedCount3.text= acknowledementList!!.get(1)["outlierCount"].toString()
                }
            }
        }
        if(msg!!.containsKey("acknowledementSLA")&&msg!!["acknowledementSLA"]!=null){
            acknowledementSLAList= msg!!["acknowledementSLA"] as ArrayList<Map<String, Any>>

            if(Utils.hasIndex(0, acknowledementSLAList!!)){
                if(acknowledementSLAList!!.get(0).containsKey("featureName")&& acknowledementSLAList!!.get(0)["featureName"]!=null){
                    mBinding!!.openchargeTitle1.text= acknowledementSLAList!!.get(0)["featureName"].toString()
                }
                if(acknowledementSLAList!!.get(0).containsKey("outlierCount")&& acknowledementSLAList!!.get(0)["outlierCount"]!=null){
                    mBinding!!.metCount1.text= acknowledementSLAList!!.get(0)["outlierCount"].toString()
                }
            }

            if(Utils.hasIndex(1, acknowledementSLAList!!)){
                if(acknowledementSLAList!!.get(1).containsKey("featureName")&& acknowledementSLAList!!.get(1)["featureName"]!=null){
                    mBinding!!.opnechargeTitle3.text= acknowledementSLAList!!.get(1)["featureName"].toString()
                }
                if(acknowledementSLAList!!.get(1).containsKey("outlierCount")&& acknowledementSLAList!!.get(1)["outlierCount"]!=null){
                    mBinding!!.missedCount3.text= acknowledementSLAList!!.get(1)["outlierCount"].toString()
                }
            }

        }




        if(msg!!.containsKey("application")&&msg!!["application"]!=null){
            applicationList = msg!!["application"] as java.util.ArrayList<Map<String, Any>>
        }

        if(msg!!.containsKey("infrastructure")&&msg!!["infrastructure"]!=null){
            infrastructureList = msg!!["infrastructure"] as java.util.ArrayList<Map<String, Any>>
        }

        if(msg!!.containsKey("tools")&&msg!!["tools"]!=null){
            toolList = msg!!["tools"] as java.util.ArrayList<Map<String, Any>>
        }

        if(msg!!.containsKey("acknowledged")&&msg!!["acknowledged"]!=null){
            acknowledgedList = msg!!["acknowledged"] as java.util.ArrayList<Map<String, Any>>
        }

        if(msg!!.containsKey("unAcknowledged")&&msg!!["unAcknowledged"]!=null){
            unacknowledgedList = msg!!["unAcknowledged"] as java.util.ArrayList<Map<String, Any>>
        }

        if(msg!!.containsKey("met")&&msg!!["met"]!=null){
            metList = msg!!["met"] as java.util.ArrayList<Map<String, Any>>
        }

        if(msg!!.containsKey("unAcknowledged")&&msg!!["unAcknowledged"]!=null){
            missedList = msg!!["missed"] as java.util.ArrayList<Map<String, Any>>
        }

        mBinding!!.card1.setOnClickListener {

            if(infrastructureList!=null) {

                val commonBeanData = CommonBean()
                PreferenceUtility.addString(activity,"subtitle", mBinding!!.infraTitle1.text.toString())
                commonBeanData.setmTitle(MyConstants.NGO)
                var ngoServiceGlaneceNgoOpenAlertsDetailsFragment =
                    ServiceGlaneceNgoOpenAlertsDetailsFragment.newInstance()
                ngoServiceGlaneceNgoOpenAlertsDetailsFragment.setData(
                    commonBeanData,
                    infrastructureList,
                    mBinding!!.infraTitle1.text.toString(),
                    mBinding!!.infraCount1.text.toString()
                )
                (activity as MainActivity).openFragments(
                    ngoServiceGlaneceNgoOpenAlertsDetailsFragment,
                    commonBeanData,
                    true,
                    true
                )
            }
        }
        mBinding!!.card2.setOnClickListener {

            if(applicationList!=null){

                PreferenceUtility.addString(activity,"subtitle", mBinding!!.applicationTitle2.text.toString())
            val commonBeanData = CommonBean()
            commonBeanData.setmTitle(MyConstants.NGO)
            var ngoServiceGlaneceNgoOpenAlertsDetailsFragment =
                ServiceGlaneceNgoOpenAlertsDetailsFragment.newInstance()
            ngoServiceGlaneceNgoOpenAlertsDetailsFragment.setData(commonBeanData, applicationList,mBinding!!.applicationTitle2.text.toString(), mBinding!!.applicationCount2.text.toString())
            (activity as MainActivity).openFragments(
                ngoServiceGlaneceNgoOpenAlertsDetailsFragment,
                commonBeanData,
                true,
                true
            )
            }
        }
        mBinding!!.card3.setOnClickListener {

            if(toolList!=null) {

                val commonBeanData = CommonBean()
                PreferenceUtility.addString(activity,"subtitle", mBinding!!.toolTitle3.text.toString())
                commonBeanData.setmTitle(MyConstants.NGO)
                /*commonBeanData.usefulLinksNgo = commonBean!!.usefulLinksNgo
            commonBeanData.usefulLinksNgo = commonBean!!.usefulLinksNgo
            commonBeanData.usefulLinksBusinessView = commonBean!!.usefulLinksBusinessView*/
                //var dashboardNgoFragment = DashboardNgoFragment.newInstance()
                var ngoServiceGlaneceNgoOpenAlertsDetailsFragment =
                    ServiceGlaneceNgoOpenAlertsDetailsFragment.newInstance()
                ngoServiceGlaneceNgoOpenAlertsDetailsFragment.setData(
                    commonBeanData,
                    toolList,
                    mBinding!!.toolTitle3.text.toString(),
                    mBinding!!.toolCount3.text.toString()
                )
                (activity as MainActivity).openFragments(
                    ngoServiceGlaneceNgoOpenAlertsDetailsFragment,
                    commonBeanData,
                    true,
                    true
                )
            }
        }
//        mBinding!!.card3.setOnClickListener {
//
//            if(toolList!!.size!=null) {
//
//                val commonBeanData = CommonBean()
//                commonBeanData.setmTitle(MyConstants.NGO)
//                /*commonBeanData.usefulLinksNgo = commonBean!!.usefulLinksNgo
//            commonBeanData.usefulLinksNgo = commonBean!!.usefulLinksNgo
//            commonBeanData.usefulLinksBusinessView = commonBean!!.usefulLinksBusinessView*/
//                //var dashboardNgoFragment = DashboardNgoFragment.newInstance()
//                var ngoServiceGlaneceNgoOpenAlertsDetailsFragment =
//                    ServiceGlaneceNgoOpenAlertsDetailsFragment.newInstance()
//                ngoServiceGlaneceNgoOpenAlertsDetailsFragment.setData(
//                    commonBeanData,
//                    toolList,
//                    mBinding!!.toolTitle3.text.toString(),
//                    mBinding!!.toolCount3.text.toString()
//                )
//                (activity as MainActivity).openFragments(
//                    ngoServiceGlaneceNgoOpenAlertsDetailsFragment,
//                    commonBeanData,
//                    true,
//                    true
//                )
//            }
//        }

        mBinding!!.card11.setOnClickListener {


            if(acknowledementList!=null) {
                PreferenceUtility.addString(activity,"subtitle", mBinding!!.acknowladgedTitle1.text.toString())
                val commonBeanData = CommonBean()
                commonBeanData.setmTitle(MyConstants.NGO)
                var ngoServiceGlaneceNgoOpenAlertsDetailsFragment =
                    ServiceGlaneceNgoOpenAlertsDetailsFragment.newInstance()
                ngoServiceGlaneceNgoOpenAlertsDetailsFragment.setData(
                    commonBeanData,
                    acknowledgedList,
                    mBinding!!.acknowladgedTitle1.text.toString(),
                    mBinding!!.acknowladgedCount1.text.toString()
                )
                (activity as MainActivity).openFragments(
                    ngoServiceGlaneceNgoOpenAlertsDetailsFragment,
                    commonBeanData,
                    true,
                    true
                )
            }
        }


        mBinding!!.card33.setOnClickListener {


            if (unacknowledgedList!= null){
                PreferenceUtility.addString(activity,"subtitle", mBinding!!.acknowladgedTitle3.text.toString())
            val commonBeanData = CommonBean()
            commonBeanData.setmTitle(MyConstants.NGO)
            var ngoServiceGlaneceNgoOpenAlertsDetailsFragment =
                ServiceGlaneceNgoOpenAlertsDetailsFragment.newInstance()
            ngoServiceGlaneceNgoOpenAlertsDetailsFragment.setData(
                commonBeanData,
                unacknowledgedList,
                mBinding!!.acknowladgedTitle3.text.toString(),
                mBinding!!.acknowladgedCount3.text.toString()
            )
            (activity as MainActivity).openFragments(
                ngoServiceGlaneceNgoOpenAlertsDetailsFragment,
                commonBeanData,
                true,
                true
            )
        }
        }


        mBinding!!.cardopencharge.setOnClickListener {
            if(metList!=null) {
                val commonBeanData = CommonBean()
                PreferenceUtility.addString(activity,"subtitle", mBinding!!.openchargeTitle1.text.toString())
                commonBeanData.setmTitle(MyConstants.NGO)
                var ngoServiceGlaneceNgoOpenAlertsDetailsFragment =
                    ServiceGlaneceNgoOpenAlertsDetailsFragment.newInstance()
                ngoServiceGlaneceNgoOpenAlertsDetailsFragment.setData(
                    commonBeanData,
                    metList,
                    mBinding!!.openchargeTitle1.text.toString(),
                    mBinding!!.metCount1.text.toString()
                )
                (activity as MainActivity).openFragments(
                    ngoServiceGlaneceNgoOpenAlertsDetailsFragment,
                    commonBeanData,
                    true,
                    true
                )
            }
        }

        mBinding!!.cardopencharge3.setOnClickListener {

            if(missedList!=null) {
                val commonBeanData = CommonBean()
                commonBeanData.setmTitle(MyConstants.NGO)
                PreferenceUtility.addString(activity,"subtitle", mBinding!!.opnechargeTitle3.text.toString())
                var ngoServiceGlaneceNgoOpenAlertsDetailsFragment =
                    ServiceGlaneceNgoOpenAlertsDetailsFragment.newInstance()
                ngoServiceGlaneceNgoOpenAlertsDetailsFragment.setData(
                    commonBeanData,
                    missedList,
                    mBinding!!.opnechargeTitle3.text.toString(),
                    mBinding!!.missedCount3.text.toString()
                )
                (activity as MainActivity).openFragments(
                    ngoServiceGlaneceNgoOpenAlertsDetailsFragment,
                    commonBeanData,
                    true,
                    true
                )
            }
        }

    }

    fun changeViewBg(v1: TextViewMedium, v2: TextViewMedium, v3: TextViewMedium, v4: TextViewMedium, v5: TextViewMedium, v6: TextViewMedium, v7: TextViewMedium) {

        v1.setTextColor(activity!!.resources.getColor(R.color.blue_text))

        v2.setTextColor(activity!!.resources.getColor(R.color.jioinfra_gray))

        v3.setTextColor(activity!!.resources.getColor(R.color.jioinfra_gray))
        v4.setTextColor(activity!!.resources.getColor(R.color.jioinfra_gray))
        v5.setTextColor(activity!!.resources.getColor(R.color.jioinfra_gray))
        v6.setTextColor(activity!!.resources.getColor(R.color.jioinfra_gray))
        v7.setTextColor(activity!!.resources.getColor(R.color.jioinfra_gray))


    }

    fun setData(commonBean: CommonBean,msg:HashMap<String, Any>?,title:String,openAlartsCount:String) {
        this.commonBean = commonBean
        this.msg = msg
        this.title = title
        this.openAlartsCount = openAlartsCount
    }
}
