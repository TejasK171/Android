package com.jio.siops_ngo.fragment

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.jio.jioinfra.bean.CommonBean
import com.jio.jioinfra.network.business.BaseCoroutines
import com.jio.jioinfra.utilities.Busicode
import com.jio.jioinfra.utilities.MyConstants
import com.jio.jioinfra.utilities.ViewUtils
import com.jio.myjio.bean.CoroutinesResponse
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.MyApplication
import com.jio.siops_ngo.R
import com.jio.siops_ngo.databinding.FragmentNewLoginBinding
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.T
import com.jio.siops_ngo.utilities.Utils
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit
import android.content.Intent
import android.content.BroadcastReceiver
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.provider.Settings
import androidx.core.app.ActivityCompat
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.iid.FirebaseInstanceId
import com.google.gson.Gson
import com.jio.siops_ngo.ApplicationDefine
import com.jio.siops_ngo.network.utils.Tools
import com.jio.siops_ngo.network.utils.Util
import com.jio.siops_ngo.ngo.fragment.NgoServiceGlanceFragment
import com.jiolib.libclasses.business.FileDataCoroutines
import java.lang.RuntimeException
import java.util.regex.Pattern
import kotlin.collections.HashMap


class LoginFragment : androidx.fragment.app.Fragment() {

    var mBinding: FragmentNewLoginBinding? = null
    var rememberMeBool: Boolean? = false
    var PERMISSION_REQUEST_CODE: Int? = 1001
    var isOtpVisible: Boolean? = false
    var otpVal: String? = null
    var mobNo: String? = null
    var emailId: String? = null
    var timer: CountDownTimer? = null
    var resendOtpCount: Int? = 0

    companion object {
        fun newInstance() = LoginFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_login, container, false)

        return mBinding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        when(ApplicationDefine.SERVER){

            MyConstants.PROD -> mBinding!!.txtVersion!!.text =
                "Version " + MyApplication.Companion.getInstance().getAppVersionName()

            MyConstants.DEV -> mBinding!!.txtVersion!!.text =
                "Version " + MyApplication.Companion.getInstance().getAppVersionName() + " (Beta)"

        }
        /*mBinding!!.txtVersion!!.text =
            "Version " + MyApplication.Companion.getInstance().getAppVersionName() + " (Beta)"*/

        /*mBinding!!.txtVersion!!.text =
            "Version " + MyApplication.Companion.getInstance().getAppVersionName()*/

    if(PreferenceUtility.getString(activity, Utils.fcmToken, "").equals("")) {
        val newToken = FirebaseInstanceId.getInstance().getToken()

        PreferenceUtility.addString(activity, Utils.fcmToken, newToken)
    }
        if(PreferenceUtility.getString(activity, Utils.deviceToken, "").equals("")) {
            val android_id = Settings.Secure.getString(
                activity!!.getContentResolver(),
                Settings.Secure.ANDROID_ID
            )

            var deviceTokenValue = Tools.getDeviceId(activity)
            Log.e("deviceTokenValue", deviceTokenValue)
            PreferenceUtility.addString(activity, Utils.deviceToken, android_id)
        }




        mBinding!!.userId!!.editText!!.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int,
                count: Int
            ) {
                if (s != "") { //do your work here
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int,
                after: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {

                mBinding!!.userId!!.error = null
            }
        })


//        var msg = "Dear XYZ,\n Your Otp is 123543. Please do not share.\n Thanks, Team Ngo"


        mBinding!!.txtResendOtp.setOnClickListener {

            if (resendOtpCount!! < 2) {
                resendOTP()
                mBinding!!.edtOtpVal.setText("")
                mBinding!!.txtTimer.visibility = View.GONE
                mBinding!!.txtOtpExpiresIn.visibility = View.GONE
            } else {
                T.show(activity!!, activity!!.resources.getString(R.string.max_no_of_otps), 0)
            }
        }



        mBinding!!.password!!.editText!!.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int,
                count: Int
            ) {
                if (s != "") { //do your work here
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int,
                after: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {

                mBinding!!.password!!.error = null
            }
        })
        mBinding!!.btnLogin.setOnClickListener {
            if (isOtpVisible!!) {
                if (ViewUtils.isEmptyString(otpVal)) {
                    otpVal = mBinding!!.edtOtpVal.text.toString()
                }
                mBinding!!.txtResendOtp.isEnabled = false
                validateOTP()
            } else
                login()
        }
        rememberMeBool = PreferenceUtility.getBoolean(activity!!, MyConstants.REMEMBER_ME, false)
        if (rememberMeBool!!) {
            mBinding!!.ckRememberMe!!.isChecked = true
            mBinding!!.etUserId!!.setText(
                PreferenceUtility.getString(
                    activity!!,
                    MyConstants.SAVED_DOMAIN_ID,
                    ""
                )
            )

        }
        mBinding!!.ckRememberMe!!.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                PreferenceUtility.addBoolean(activity!!, MyConstants.REMEMBER_ME, true);
                PreferenceUtility.addString(
                    activity,
                    MyConstants.SAVED_DOMAIN_ID,
                    mBinding!!.etUserId!!.text.toString()
                )
                /*PreferenceUtility.addString(
                    activity,
                    MyConstants.SAVED_PASSWORD,
                    mBinding!!.loginPassword!!.text.toString()
                )*/
            } else {
                PreferenceUtility.addBoolean(activity!!, MyConstants.REMEMBER_ME, false);
                PreferenceUtility.addString(activity, MyConstants.SAVED_DOMAIN_ID, "")
//                PreferenceUtility.addString(activity, MyConstants.SAVED_PASSWORD, "")
            }
        }

        if(activity!=null)
            callHandshake()
    }


    private fun login() {
//        FirebaseCrashlytics.getInstance().recordException(RuntimeException("Testing Crashlytics!!"))
        var domainId = mBinding!!.userId!!.editText!!.text.trim().toString()
        var password = mBinding!!.password!!.editText!!.text.trim().toString()


        if (ViewUtils.isEmptyString(domainId)) {

            mBinding!!.userId!!.setError("Please enter valid username");

            //   T.show(activity, "Please enter valid DomainId ", 0)
            return

        } else if (ViewUtils.isEmptyString(password)) {
            // T.show(activity, "Please enter valid Password", 0)
            mBinding!!.password!!.setError("Please enter valid password");
            return
        } else {

            if (mBinding!!.ckRememberMe.isChecked == true) {
                PreferenceUtility.addString(
                    activity,
                    MyConstants.SAVED_DOMAIN_ID,
                    mBinding!!.etUserId.text.toString()
                )
                /*PreferenceUtility.addString(
                    activity,
                    MyConstants.SAVED_PASSWORD,
                    mBinding!!.loginPassword!!.text.toString()
                )*/
            }
            (activity as MainActivity).showProgressBar()

            val requestBody = HashMap<String, Any>()
            requestBody["userName"] = domainId
            requestBody["password"] = password
            requestBody["fcmId"] = PreferenceUtility.getString(activity, Utils.fcmToken, "")
            requestBody["deviceId"] = PreferenceUtility.getString(activity, Utils.deviceToken, "")
            requestBody["type"] = "userInfo"
            requestBody["channel"] = "APP"
            requestBody["channelTypeVer"] =
                MyApplication.Companion.getInstance().getAppVersionName()
            requestBody["channelType"] = "NGO_ANDROID"
            requestBody["mobileApplication"] = "ngo_Android"
//            requestBody["isNewDesign"] = "1"


            CoroutineScope(Dispatchers.IO).launch {

                var job = async {
                    BaseCoroutines().fetchData(
                        requestBody,
                        Busicode.Login,
                        activity as MainActivity
                    )
                }
                withContext(Dispatchers.Main)
                {

                    var response = job.await()
                    if(activity!=null)
                        (activity as MainActivity).hideProgressBar()

                    if (response!!.responseEntity != null && response.status == MappActor.STATUS_OK) {
                        loginAfterResponse(response)
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
    }


    fun loginAfterResponse(mCoroutinesResponse: CoroutinesResponse) {

        try {


            val msg = mCoroutinesResponse.responseEntity as HashMap<String, Any>
            if (msg.containsKey("roleToDisplay") && msg["roleToDisplay"] != null) {

                if (msg["roleToDisplay"].toString().equals("GUEST")) {
                    if (msg.containsKey("guestMessage") && msg["guestMessage"] != null) {
                        T.show(activity!!, msg["guestMessage"] as String, 0)
                    } else {
                        T.show(activity!!, "You are not authorized to use this app!!", 0)
                    }
                    return

                }

            }
            /*Gson().fromJson(getJson!!, object: TypeToken<ArrayList<Map<String, Any>>>() {}.type)*/


            val commonBean = CommonBean()
            var dashboardFragment: Fragment? = null
            var usefulLinksNgo: MutableList<Map<String, Any>>? = null
            var usefulLinksInfra = arrayListOf<Map<String, Any>>()
            var usefulLinksBusinessView = arrayListOf<Map<String, Any>>()


            if (msg.containsKey("notificationCount") && msg.get("notificationCount") != null) {
                var notificationCount: Int = msg["notificationCount"] as Int
                var notificationData: String = notificationCount.toString()

                PreferenceUtility.addString(
                    activity,
                    MyConstants.NOTIFICATION_COUNT,
                    notificationData
                )
            }


            if (msg.containsKey("otpAuthEnabled")) {
                if (msg["otpAuthEnabled"] != null) {
                    if (msg["otpAuthEnabled"]!!.equals("YES")) {

                        if (msg.containsKey("idmMobile") && msg["idmMobile"] != null) {
                            mobNo = msg["idmMobile"] as String
                            mobNo = mobNo!!.substring(mobNo!!.length - 4)
                            Log.e("mobNo", mobNo)
                            mobNo = "+91" + "xxxxxx" + mobNo
                        }

                        if (msg.containsKey("idmRILEmail") && msg["idmRILEmail"] != null) {
                            emailId = msg["idmRILEmail"] as String
                            var emailIDSplit = emailId!!.split("@")
                            emailId = emailIDSplit[0].substring(0, 2)

                            for (i in 2 until emailIDSplit[0].length) {
                                emailId = emailId + "x"

                            }
                            emailId = emailId + "@" + emailIDSplit[1]
                            Log.e("emailId", emailId + "@" + emailIDSplit[1])
                        }

                        if (mobNo != null && emailId != null) {

                            T.show(
                                activity,
                                "Otp has been sent to your registered mobile no (" + mobNo + ") and Email Id (" + emailId + ")",
                                0
                            )

                        }


//                        var strLastFourDi = phoneNumber.length() >= 4 ? phoneNumber.substring(phoneNumber.length() - 4): "";

                        if (ActivityCompat.checkSelfPermission(
                                activity!!,
                                android.Manifest.permission.READ_SMS
                            ) != PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(
                                activity!!,
                                android.Manifest.permission.RECEIVE_SMS
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            requestPermissions(
                                arrayOf(
                                    Manifest.permission.READ_SMS,
                                    Manifest.permission.RECEIVE_SMS
                                ), PERMISSION_REQUEST_CODE!!
                            )
                        } else {
                            if (timer != null) {
                                timer!!.cancel()
                            }
                            startOtpTimer()
                            LocalBroadcastManager.getInstance(activity!!)
                                .registerReceiver(receiver, IntentFilter("otpVal"))
                        }

                        return
                    } else if (msg["otpAuthEnabled"]!!.equals("NO")) {
                        isOtpVisible = false
                    }
                }
            }

            if (msg.containsKey("applications")) {
                if (msg["applications"] != null) {
                    usefulLinksNgo = msg["applications"] as MutableList<Map<String, Any>>
//                    usefulLinksNgo = msg["applications"] as MutableList<Map<String, Any>>
                    val iteratorNgo = usefulLinksNgo.iterator()
                    val iteratorInfra = usefulLinksInfra.iterator()
                    while (iteratorNgo.hasNext()) {
                        val itemNgo = iteratorNgo.next()
//                        val itemInfra = iteratorInfra.next()

                        if(itemNgo.containsKey("allowOverApp") && itemNgo["allowOverApp"]!=null){

                            val allowOverApp = itemNgo["allowOverApp"].toString()
                            if(allowOverApp.equals("N")){
                                iteratorNgo.remove()
                                continue
                            }

                        }


                        var appUCode = itemNgo.get("applicationUCode") as String


                        if (appUCode.equals(MyConstants.UCODE_NGO)) {
                            usefulLinksBusinessView.add(itemNgo)
                        }

                        if (appUCode.equals(MyConstants.UCODE_RAN) || appUCode.equals(MyConstants.UCODE_NGO) || appUCode.equals(MyConstants.APP_UCODE_FE)) {

                            iteratorNgo.remove()
//                            iteratorInfra.remove()

                            if (appUCode.equals(MyConstants.UCODE_RAN)) {
                                PreferenceUtility.addString(
                                    activity!!,
                                    MyConstants.APP_CODE_RAN,
                                    itemNgo.get("applicationCode") as String??
                                )
                            } else if (appUCode.equals(MyConstants.UCODE_NGO)) {
                                PreferenceUtility.addString(
                                    activity!!,
                                    MyConstants.APP_CODE_NGO,
                                    itemNgo.get("applicationCode") as String??
                                )
                            }
                        } else if (itemNgo.containsKey("applicability")) {
                            var applicability = itemNgo.get("applicability") as String
                            if (applicability.equals("NGO")) {
//                                iteratorInfra.remove()
                            } else if (applicability.equals("INFRA")) {
                                iteratorNgo.remove()
                                usefulLinksInfra.add(itemNgo)
                            } else if (applicability.equals("BOTH")) {
                                usefulLinksInfra.add(itemNgo)
                            }

                        }


                    }


                    if (msg.containsKey("userName") && msg["userName"] != null) {
                        PreferenceUtility.addString(
                            activity,
                            MyConstants.DOMAIN_ID,
                            msg["userName"] as String
                        )
                        mBinding!!.etUserId!!.hideKeyboard()
                        mBinding!!.loginPassword!!.hideKeyboard()


                    }

                } else {

                    val responsePayloadHeader =
                        msg["responseHeader"] as HashMap<String, String>
                    if (responsePayloadHeader != null) {

                        val errorMsg = responsePayloadHeader["messageDetails"] as String
                        if (errorMsg != null) {
                            T.show(activity, errorMsg, 0)
                        }
                    }
                }
            }
            Log.e("busUseLin ", "+" + usefulLinksBusinessView.size)


            if (msg.containsKey("userName") && msg["userName"] != null) {
                PreferenceUtility.addString(
                    activity,
                    MyConstants.DOMAIN_ID,
                    msg["userName"] as String
                )
            }
            /*PreferenceUtility.addString(
                activity,
                MyConstants.PASSWORD,
                mBinding!!.loginPassword!!.text.toString()
            )*/
            mBinding!!.etUserId!!.hideKeyboard()
            mBinding!!.loginPassword!!.hideKeyboard()

            if (msg.containsKey("roleToDisplay") && msg["roleToDisplay"]!=null) {
                PreferenceUtility.addString(
                    activity,
                    MyConstants.USER_ROLE,
                    msg["roleToDisplay"] as String
                )
            }

            /*if (msg.containsKey("allowToggle")) {
                var allowToggle: Boolean = msg["allowToggle"] as Boolean
                if (allowToggle) {
                    PreferenceUtility.addBoolean(activity, MyConstants.ALLOW_TOGGLE, allowToggle);
                    var map1: Map<String, Any> = mutableMapOf(
                        "applicationName" to "Toggle",
                        "colourCodeForApp" to "#B58D3D",
                        "applicationCode" to "0"
                    )
//                    var actionsList =  mutableListOf(map1)
                    usefulLinksNgo!!.add(map1)
                    usefulLinksInfra!!.add(map1)
                }


            }*/

            commonBean.usefulLinksNgo = usefulLinksNgo
            commonBean.usefulLinksInfra = usefulLinksInfra
            commonBean.usefulLinksBusinessView = usefulLinksBusinessView

            /*(activity as MainActivity).usefulLinksNgo = usefulLinksNgo
            (activity as MainActivity).usefulLinksNgo = usefulLinksNgo*/

            /*MyApplication.mInstance!!.usefulLinksInfra = usefulLinksInfra
            MyApplication.mInstance!!.usefulLinksNgo = usefulLinksNgo
            MyApplication.mInstance!!.usefulLinksBusinessView = usefulLinksBusinessView*/



            val gson =  Gson();
            var usefulLinksInfraJson = gson.toJson(usefulLinksInfra);
            PreferenceUtility.addString(activity!!, MyConstants.USEFUL_LINKS_INFRA, usefulLinksInfraJson)

            var usefulLinksNgoJson = gson.toJson(usefulLinksNgo);
            PreferenceUtility.addString(activity!!, MyConstants.USEFUL_LINKS_NGO, usefulLinksNgoJson)

            var usefulLinksBusinessJson = gson.toJson(usefulLinksBusinessView);
            PreferenceUtility.addString(activity!!, MyConstants.USEFUL_LINKS_BUSINESS_VIEW, usefulLinksBusinessJson)

            /*val getJson = PreferenceUtility.getString(activity!!, MyConstants.USEFUL_LINKS_INFRA, "")
//            var type : TypeToken<ArrayList<String>>(){}.type

            val turnsType = object : TypeToken<ArrayList<Map<String, Any>>>() {}.type

            val jsonList = gson.fromJson(getJson, turnsType) as ArrayList<Map<String, Any>>

            Log.e("jsonMap", ""+jsonList.size)*/

            if (msg.containsKey("allowToggle") && msg["allowToggle"] != null) {


                var allowToggleView = msg["allowToggleView"] as String
//                PreferenceUtility.addString(activity, MyConstants.ALLOW_TOGGLE, allowToggleView)


                if (allowToggleView.equals("NGO")) {
                    PreferenceUtility.addString(activity, MyConstants.ALLOW_TOGGLE, allowToggleView)
                    commonBean.setmTitle(MyConstants.NGO)
//                    dashboardFragment = DashboardNgoFragment.newInstance()
                    dashboardFragment = NgoServiceGlanceFragment.newInstance()
                    dashboardFragment.setData(commonBean)
//                    (activity as MainActivity).openFragments(dashboardFragment, commonBean, false, true)
                } else if (allowToggleView.equals("INFRA")) {
                    T.show(activity, "Hello, You are not authorized to access this application", 0)
                    return

                } else if (allowToggleView.equals(MyConstants.NGO_BUSINESS_VIEW)) {
                    PreferenceUtility.addString(activity, MyConstants.ALLOW_TOGGLE, allowToggleView)
                    commonBean.setmTitle(MyConstants.NGO_BUSINESS_VIEW)
//                    dashboardFragment = NgoBusinessViewFragment.newInstance()
                    dashboardFragment = NgoBusinessListFragment.newInstance()
                    dashboardFragment.setData(commonBean)


                }


            } else {
                commonBean.setmTitle(MyConstants.NGO)
                dashboardFragment = NgoServiceGlanceFragment.newInstance()
                dashboardFragment.setData(commonBean)
            }


            (activity as MainActivity).openFragments(dashboardFragment!!, commonBean, false, true)


            /* val timer = object: CountDownTimer(120000, 1000) {
                 override fun onTick(millisUntilFinished: Long) {

                     Log.e("Time", ""+millisUntilFinished)

                     mBinding!!.txtOtpExpiresIn.visibility = View.VISIBLE
                     mBinding!!.txtTimer.visibility = View.VISIBLE
                     mBinding!!.btnLogin.text = activity!!.resources.getString(R.string.submit)
                     mBinding!!.lnrResendOtp.visibility = View.VISIBLE

                     mBinding!!.txtTimer.text =  String.format("%d:%d",
                         TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                         TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                 TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))
                     Log.e("Time", ""+String.format("%d:%d",
                         TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                         TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                 TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))))
                 }

                 override fun onFinish() {
                     mBinding!!.txtOtpExpiresIn.visibility = View.GONE
                     mBinding!!.txtTimer.visibility = View.GONE
                     mBinding!!.btnLogin.text = activity!!.resources.getString(R.string.submit)
                     mBinding!!.lnrResendOtp.visibility = View.GONE
                 }
             }
             timer.start()*/

//            (activity as MainActivity).openFragments(dashboardFragment!!, commonBean, false, true)

            /*if(boolean){

                //IF TRUE THEN ADD TOGGLE BUTTON
                var allowToggleView:String =msg["allowToggleView"] as String

                if(allowToggleView.equals("NGO")){
                    ///NGO VIEW



                    if(msg["applications"]!=null) {
                        val listData = msg["applications"] as List<Map<String, Any>>
                        var notificationCount:Int = msg["notificationCount"] as Int
                        var notificationData:String = notificationCount.toString()

//                PreferenceUtility.addString(activity,MyConstants.NOTIFICATION_COUNT, notificationData )

                        if (listData != null && listData.size > 0) {
                            PreferenceUtility.addString(activity, MyConstants.DOMAIN_ID, msg["userName"] as String)
                            PreferenceUtility.addString(activity, MyConstants.PASSWORD, mBinding!!.loginPassword!!.text.toString())
//                    PreferenceUtility.addString(activity, MyConstants.USER_ROLE, msg["role"] as String)
                            mBinding!!.etUserId!!.hideKeyboard()
                            mBinding!!.loginPassword!!.hideKeyboard()



                            val commonBean = CommonBean()
                            commonBean.`object` = listData
                            var dashboardFragment = DashboardNgoFragment.newInstance()
                            dashboardFragment.setData(commonBean)
                            (activity as MainActivity).openFragments(dashboardFragment, commonBean, false, true)
                        } else {
//                    T.show(activity, getString(R.string.you_have_not_assigned_any_services), 0)
                        }
                    }else {

                        val responsePayloadHeader = msg["responseHeader"] as HashMap<String, String>
                        if (responsePayloadHeader != null) {

                            val errorMsg = responsePayloadHeader["messageDetails"] as String
                            if(errorMsg!=null){
                                T.show(activity,errorMsg,0)
                            }
                        }
                    }
                }else{
                    if(msg["applications"]!=null) {
                        val listData = msg["applications"] as List<Map<String, Any>>
                        var notificationCount:Int = msg["notificationCount"] as Int
                        var notificationData:String = notificationCount.toString()

//                PreferenceUtility.addString(activity,MyConstants.NOTIFICATION_COUNT, notificationData )

                        if (listData != null && listData.size > 0) {
                            PreferenceUtility.addString(activity, MyConstants.DOMAIN_ID, msg["userName"] as String)
                            PreferenceUtility.addString(activity, MyConstants.PASSWORD, mBinding!!.loginPassword!!.text.toString())
//                    PreferenceUtility.addString(activity, MyConstants.USER_ROLE, msg["role"] as String)
                            mBinding!!.etUserId!!.hideKeyboard()
                            mBinding!!.loginPassword!!.hideKeyboard()



                            val commonBean = CommonBean()
                            commonBean.`object` = listData
                            var dashboardFragment = DashboardFragment.newInstance()
                            dashboardFragment.setData(commonBean)
                            (activity as MainActivity).openFragments(dashboardFragment, commonBean, false, true)
                        } else {
//                    T.show(activity, getString(R.string.you_have_not_assigned_any_services), 0)
                        }
                    }else {

                        val responsePayloadHeader = msg["responseHeader"] as HashMap<String, String>
                        if (responsePayloadHeader != null) {

                            val errorMsg = responsePayloadHeader["messageDetails"] as String
                            if(errorMsg!=null){
                                T.show(activity,errorMsg,0)
                            }
                        }
                    }
                }


            }else{
                var allowToggleView:String =msg["allowToggleView"] as String

                if(allowToggleView.equals("NGO")){
                    ///NGO VIEW
                }else{
                    if(msg["applications"]!=null) {
                        val listData = msg["applications"] as List<Map<String, Any>>
                        var notificationCount:Int = msg["notificationCount"] as Int
                        var notificationData:String = notificationCount.toString()

//                PreferenceUtility.addString(activity,MyConstants.NOTIFICATION_COUNT, notificationData )

                        if (listData != null && listData.size > 0) {
                            PreferenceUtility.addString(activity, MyConstants.DOMAIN_ID, msg["userName"] as String)
                            PreferenceUtility.addString(activity, MyConstants.PASSWORD, mBinding!!.loginPassword!!.text.toString())
//                    PreferenceUtility.addString(activity, MyConstants.USER_ROLE, msg["role"] as String)
                            mBinding!!.etUserId!!.hideKeyboard()
                            mBinding!!.loginPassword!!.hideKeyboard()



                            val commonBean = CommonBean()
                            commonBean.`object` = listData
                            var dashboardFragment = DashboardFragment.newInstance()
                            dashboardFragment.setData(commonBean)
                            (activity as MainActivity).openFragments(dashboardFragment, commonBean, false, true)
                        } else {
//                    T.show(activity, getString(R.string.you_have_not_assigned_any_services), 0)
                        }
                    }else {

                        val responsePayloadHeader = msg["responseHeader"] as HashMap<String, String>
                        if (responsePayloadHeader != null) {

                            val errorMsg = responsePayloadHeader["messageDetails"] as String
                            if(errorMsg!=null){
                                T.show(activity,errorMsg,0)
                            }
                        }
                    }
                }
            }
*/

        } catch (e: Exception) {
            e.printStackTrace()
            MyExceptionHandler.handle(e)
            T.show(activity, getString(R.string.something_went_wrong), 0)
        }
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }


    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action!!.equals("otpVal", ignoreCase = true)) {
                val message = intent.getStringExtra("message")
//                var number = message!!.replace("[^0-9]", "");

                var p = Pattern.compile("\\d+");
                var m = p.matcher(message);
                while (m.find()) {
                    System.out.println(m.group());
                    mBinding!!.edtOtpVal.setText(m.group())
                    otpVal = m.group()
                    mBinding!!.edtOtpVal.visibility = View.VISIBLE
                    mBinding!!.lnrSendOtp.visibility = View.VISIBLE
                }

            }
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        if (!this.isVisible) {
            if (timer != null)
                timer!!.cancel()
            LocalBroadcastManager.getInstance(activity!!).unregisterReceiver(receiver)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (permissions[0].equals(Manifest.permission.READ_SMS)
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                if (timer != null) {
                    timer!!.cancel()
                }
                startOtpTimer()
                Log.e("Perm Granted", "Perms granted")
                LocalBroadcastManager.getInstance(activity!!)
                    .registerReceiver(receiver, IntentFilter("otpVal"))
            }
        }
    }

    fun startOtpTimer() {
        isOtpVisible = true
        timer = object : CountDownTimer(120000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                if (isVisible) {


                    mBinding!!.txtOtpExpiresIn.visibility = View.VISIBLE
                    mBinding!!.txtTimer.visibility = View.VISIBLE
                    mBinding!!.btnLogin.text = activity!!.resources.getString(R.string.submit)
                    mBinding!!.lnrSendOtp.visibility = View.VISIBLE

                    mBinding!!.userId.isEnabled = false
                    mBinding!!.password.isEnabled = false

                    mBinding!!.txtTimer.text = String.format(
                        "%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(
                                    TimeUnit.MILLISECONDS.toMinutes(
                                        millisUntilFinished
                                    )
                                )
                    )
                }
            }

            override fun onFinish() {
                if (isVisible) {
                    isOtpVisible = false
                    mBinding!!.txtOtpExpiresIn.visibility = View.GONE
                    mBinding!!.txtTimer.visibility = View.GONE
                    mBinding!!.btnLogin.text = activity!!.resources.getString(R.string.login)
                    mBinding!!.lnrSendOtp.visibility = View.GONE
                    mBinding!!.userId.isEnabled = true
                    mBinding!!.password.isEnabled = true
                    mBinding!!.edtOtpVal.setText("")
                }
            }
        }
        timer!!.start()


    }

    private fun validateOTP() {
        var domainId = mBinding!!.userId!!.editText!!.text.trim().toString()

        if (ViewUtils.isEmptyString(mBinding!!.edtOtpVal.text.toString())) {

            mBinding!!.edtOtpVal!!.setError("Please enter otp");

            return

        } else {
            (activity as MainActivity).showProgressBar()

            val requestBody = HashMap<String, Any>()
            requestBody["userName"] = domainId
            requestBody["otp"] = mBinding!!.edtOtpVal.text.toString()
            requestBody["type"] = "userInfo"



            CoroutineScope(Dispatchers.IO).launch {

                var job = async {
                    BaseCoroutines().fetchData(
                        requestBody,
                        Busicode.ValidateOTP,
                        activity as MainActivity
                    )
                }
                withContext(Dispatchers.Main)
                {

                    var response = job.await()
                    (activity as MainActivity).hideProgressBar()

                    if (response!!.responseEntity != null && response.status == MappActor.STATUS_OK) {
                        parseValidateOtpResponse(response)
                    } else if (response!!.errorMsg != null) {
                        mBinding!!.txtResendOtp.isEnabled = true
                        T.show(activity, response!!.errorMsg!!, 0)
                    } else {
                        mBinding!!.txtResendOtp.isEnabled = true
                        T.show(activity, "Something went wrong!", 0)
                    }
                }


            }
        }
    }


    fun parseValidateOtpResponse(mCoroutinesResponse: CoroutinesResponse) {

        try {

            val msg = mCoroutinesResponse.responseEntity as HashMap<String, Any>
            val commonBean = CommonBean()
            var dashboardFragment: Fragment? = null
            var usefulLinksNgo: MutableList<Map<String, Any>>? = null
            var usefulLinksInfra = arrayListOf<Map<String, Any>>()
            var usefulLinksBusinessView = arrayListOf<Map<String, Any>>()
            mBinding!!.txtResendOtp.isEnabled = true


            if (msg.containsKey("applications")) {
                if (msg["applications"] != null) {
                    usefulLinksNgo = msg["applications"] as MutableList<Map<String, Any>>
//                    usefulLinksNgo = msg["applications"] as MutableList<Map<String, Any>>
                    val iteratorNgo = usefulLinksNgo.iterator()
                    val iteratorInfra = usefulLinksInfra.iterator()
                    while (iteratorNgo.hasNext()) {
                        val itemNgo = iteratorNgo.next()
//                        val itemInfra = iteratorInfra.next()

                        if(itemNgo.containsKey("allowOverApp") && itemNgo["allowOverApp"]!=null){

                            val allowOverApp = itemNgo["allowOverApp"].toString()
                            if(allowOverApp.equals("N")){
                                iteratorNgo.remove()
                                continue
                            }

                        }


                        var appUCode = itemNgo.get("applicationUCode") as String


                        if (appUCode.equals(MyConstants.UCODE_NGO)) {
                            usefulLinksBusinessView.add(itemNgo)
                        }
                        if (appUCode.equals(MyConstants.UCODE_RAN) || appUCode.equals(MyConstants.UCODE_NGO) || appUCode.equals(MyConstants.APP_UCODE_FE)) {

                            iteratorNgo.remove()

                            if (appUCode.equals(MyConstants.UCODE_RAN)) {
                                PreferenceUtility.addString(
                                    activity!!,
                                    MyConstants.APP_CODE_RAN,
                                    itemNgo.get("applicationCode") as String??
                                )
                            } else if (appUCode.equals(MyConstants.UCODE_NGO)) {
                                PreferenceUtility.addString(
                                    activity!!,
                                    MyConstants.APP_CODE_NGO,
                                    itemNgo.get("applicationCode") as String??
                                )
                            }
                        } else if (itemNgo.containsKey("applicability")) {
                            var applicability = itemNgo.get("applicability") as String
                            if (applicability.equals("NGO")) {
//                                iteratorInfra.remove()
                            } else if (applicability.equals("INFRA")) {
                                iteratorNgo.remove()
                                usefulLinksInfra.add(itemNgo)
                            } else if (applicability.equals("BOTH")) {
                                usefulLinksInfra.add(itemNgo)
                            }

                        }



                    }


                    if (msg.containsKey("userName") && msg["userName"] != null) {
                        PreferenceUtility.addString(
                            activity,
                            MyConstants.DOMAIN_ID,
                            msg["userName"] as String
                        )
                        mBinding!!.etUserId!!.hideKeyboard()
                        mBinding!!.loginPassword!!.hideKeyboard()


//                        commonBean.`object` = listData

                    }

                } else {

                    val responsePayloadHeader =
                        msg["responseHeader"] as HashMap<String, String>
                    if (responsePayloadHeader != null) {

                        val errorMsg = responsePayloadHeader["messageDetails"] as String
                        if (errorMsg != null) {
                            T.show(activity, errorMsg, 0)
                        }
                    }
                }
            }
            if (msg.containsKey("notificationCount") && msg.get("notificationCount") != null) {
                var notificationCount: Int = msg["notificationCount"] as Int
                var notificationData: String = notificationCount.toString()

                PreferenceUtility.addString(
                    activity,
                    MyConstants.NOTIFICATION_COUNT,
                    notificationData
                )
            }


            if (msg.containsKey("roleToDisplay") && msg["roleToDisplay"]!=null) {
                PreferenceUtility.addString(
                    activity,
                    MyConstants.USER_ROLE,
                    msg["roleToDisplay"] as String
                )
            }

            /*if (msg.containsKey("allowToggle")) {
                var allowToggle: Boolean = msg["allowToggle"] as Boolean
                if (allowToggle) {
                    PreferenceUtility.addBoolean(activity, MyConstants.ALLOW_TOGGLE, allowToggle);
                    var map1: Map<String, Any> = mutableMapOf(
                        "applicationName" to "Toggle",
                        "colourCodeForApp" to "#B58D3D",
                        "applicationCode" to "0"
                    )
//                    var actionsList =  mutableListOf(map1)
                    usefulLinksNgo!!.add(map1)
                    usefulLinksInfra!!.add(map1)
                }


            }*/

            commonBean.usefulLinksNgo = usefulLinksNgo
            commonBean.usefulLinksInfra = usefulLinksInfra
            commonBean.usefulLinksBusinessView = usefulLinksBusinessView

            /*(activity as MainActivity).usefulLinksNgo = usefulLinksNgo
            (activity as MainActivity).usefulLinksNgo = usefulLinksNgo*/

            /*MyApplication.mInstance!!.usefulLinksInfra = usefulLinksInfra
            MyApplication.mInstance!!.usefulLinksNgo = usefulLinksNgo
            MyApplication.mInstance!!.usefulLinksBusinessView = usefulLinksBusinessView*/


            val gson =  Gson();
            var usefulLinksInfraJson = gson.toJson(usefulLinksInfra);
            PreferenceUtility.addString(activity!!, MyConstants.USEFUL_LINKS_INFRA, usefulLinksInfraJson)

            var usefulLinksNgoJson = gson.toJson(usefulLinksNgo);
            PreferenceUtility.addString(activity!!, MyConstants.USEFUL_LINKS_NGO, usefulLinksNgoJson)

            var usefulLinksBusinessJson = gson.toJson(usefulLinksBusinessView);
            PreferenceUtility.addString(activity!!, MyConstants.USEFUL_LINKS_BUSINESS_VIEW, usefulLinksBusinessJson)

            if (msg.containsKey("allowToggle") && msg["allowToggle"] != null) {


                var allowToggleView = msg["allowToggleView"] as String
//                PreferenceUtility.addString(activity, MyConstants.ALLOW_TOGGLE, allowToggleView)


                if (allowToggleView.equals("NGO")) {
                    PreferenceUtility.addString(activity, MyConstants.ALLOW_TOGGLE, allowToggleView)
                    commonBean.setmTitle(MyConstants.NGO)
                    dashboardFragment = NgoServiceGlanceFragment.newInstance()
                    dashboardFragment.setData(commonBean)
//                    (activity as MainActivity).openFragments(dashboardFragment, commonBean, false, true)
                } else if (allowToggleView.equals("INFRA")) {
                    T.show(activity, "Hello, You are not authorized to access this application", 0)
                    return

                } else if (allowToggleView.equals(MyConstants.NGO_BUSINESS_VIEW)) {
                    PreferenceUtility.addString(activity, MyConstants.ALLOW_TOGGLE, allowToggleView)
                    commonBean.setmTitle(MyConstants.NGO_BUSINESS_VIEW)
//                    dashboardFragment = NgoBusinessViewFragment.newInstance()
                    dashboardFragment = NgoBusinessListFragment.newInstance()
                    dashboardFragment.setData(commonBean)
                }


            } else {
                commonBean.setmTitle(MyConstants.NGO)
                dashboardFragment = NgoServiceGlanceFragment.newInstance()
                dashboardFragment.setData(commonBean)
            }

            (activity as MainActivity).openFragments(dashboardFragment!!, commonBean, false, true)

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

    }


    private fun resendOTP() {
        var domainId = mBinding!!.userId!!.editText!!.text.trim().toString()

        (activity as MainActivity).showProgressBar()

        val requestBody = HashMap<String, Any>()
        requestBody["userName"] = domainId
        requestBody["type"] = "userInfo"

        CoroutineScope(Dispatchers.IO).launch {

            var job = async {
                BaseCoroutines().fetchData(
                    requestBody,
                    Busicode.ResendOTP,
                    activity as MainActivity
                )
            }
            withContext(Dispatchers.Main)
            {

                var response = job.await()
                (activity as MainActivity).hideProgressBar()

                if (response!!.responseEntity != null && response.status == MappActor.STATUS_OK) {
                    resendOtpCount = resendOtpCount!! + 1
                    if (timer != null) {
                        timer!!.cancel()
                    }
                    startOtpTimer()
//                        parseValidateOtpResponse(response)
                } else if (response!!.errorMsg != null) {
                    T.show(activity, response!!.errorMsg!!, 0)
                } else {
                    T.show(activity, "Something went wrong!", 0)
                }
            }


        }
    }

    fun callHandshake() {
        try {
            if(activity!=null)
                (activity as MainActivity).showProgressBar()
            var job = CoroutineScope(Dispatchers.IO).launch {
                val fileDataCoroutines = FileDataCoroutines()
                var result = async { fileDataCoroutines.getTranseKey(activity as MainActivity ) }
                withContext(Dispatchers.Main)
                {
                    var mCoroutinesResponse = result.await()
                    if(activity!=null)
                        (activity as MainActivity).hideProgressBar()
                    try {
                        /*if (mCoroutinesResponse.status == MappActor.STATUS_OK) {
                            var respMsg = mCoroutinesResponse.responseEntity as HashMap<String, Any>
                            if (respMsg!!.get("updateFlag").toString().equals("F", true) || respMsg["updateFlag"].toString().equals("O", true)) {
                                Log.d("updateFlag", "updateFlag F")
                                try {
                                    if (respMsg!!.containsKey("msg") && !ViewUtils.isEmptyString(respMsg!!["msg"] as String)){
                                        Log.d("updateFlag", ""+respMsg!!["msg"])
                                    }else{

                                    }
                                    // updateMsg = respMsg["msg"] as String
                                } catch (ex: Exception) {
                                    MyExceptionHandler.handle(ex)

                                }


                            } else if (respMsg["updateFlag"].toString().equals("N", true)) {
                                Log.d("updateFlag", "updateFlag N")
                                // primarySyncCompleted = false
                                // loginToMapp()
                            } else {
                                Log.d("updateFlag", "updateFlag Not found")
                                // primarySyncCompleted = false
                                // loginToMapp()
                            }


                        } else if (1 == mCoroutinesResponse.status) {
                            // ViewUtils.showExceptionDialog(mActivity, msg, "", "", "", "TrackOrderStatus", "", "", "", null, msgException, false)
                        } else {
                            // ViewUtils.showExceptionDialog(mActivity, msg, "", "", "", "TrackOrderStatus", "", "", "", null, msgException)
                        }*/

                        val msg = mCoroutinesResponse.responseEntity as HashMap<String, Any>
                        if(msg.containsKey("sessionId") && msg["sessionId"]!=null){
                            var sessionId = msg["sessionId"] as String
                            PreferenceUtility.addString(activity!!, MyConstants.SESSION_ID, sessionId)
                            Util.setSessionIdVal(sessionId)
                        }
                        if(msg.containsKey("transKey") && msg["transKey"]!=null){
                            var transKey = msg["transKey"] as String
                            PreferenceUtility.addString(activity!!, MyConstants.TRANS_KEY, transKey)
                            Util.setTransKey(transKey)
                        }

                    } catch (e: Exception) {
                        MyExceptionHandler.handle(e)
                    }
                }
            }

        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
        }
    }


}