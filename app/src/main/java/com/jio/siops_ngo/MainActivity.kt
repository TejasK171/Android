package com.jio.siops_ngo

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.provider.Settings.Secure
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.iid.FirebaseInstanceId
import com.jio.jioinfra.bean.CommonBean
import com.jio.jioinfra.network.business.BaseCoroutines
import com.jio.jioinfra.utilities.Busicode
import com.jio.jioinfra.utilities.MyConstants
import com.jio.myjio.bean.CoroutinesResponse
import com.jio.siops_ngo.databinding.ActivityMainBinding
import com.jio.siops_ngo.fragment.*
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.network.utils.Tools.getDeviceId
import com.jio.siops_ngo.ngo.fragment.NgoServiceGlanceFragment
import com.jio.siops_ngo.utilities.*
import com.jio.siops_ngo.utilities.RootUtil.Companion.isDeviceRooted
import com.jio.siops_ngo.utilities.Utils.Companion.deviceToken
import com.jio.siops_ngo.utilities.Utils.Companion.fcmToken
import kotlinx.coroutines.*
import java.util.HashMap
import com.jio.siops_ngo.utilities.T
import com.microsoft.appcenter.crashes.Crashes
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics


class MainActivity : AppCompatActivity() {

    var mBinding: ActivityMainBinding? = null
    var mCurrentFragment: Fragment? = null

    var savedCommonBeanData: CommonBean? = null
    var usefulLinksNgo: List<Map<String, Any>>? = null
    var usefulLinksBusiness: List<Map<String, Any>>? = null
    var usefulLinksInfra: List<Map<String, Any>>? = null
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    /*public var `usefulLinksNgo`: Any? = null
    public var `usefulLinksNgo`: Any? = null*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(mBinding!!.toolbar)
        PreferenceUtility.createSharedPrefs(this)
        AppCenter.start(
            application, "f7f9efe3-64d0-448b-8f50-30d4a081bc29",
            Analytics::class.java, Crashes::class.java
        )

        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        mBinding!!.icBckButton.setOnClickListener {
            onBackPressed()
        }
        if (isDeviceRooted()) {
            dialog()
        } else {

            initListner();

            PreferenceUtility.addString(this, "noti", "0");

            FirebaseApp.initializeApp(this)
            FirebaseInstanceId.getInstance()
                .instanceId.addOnSuccessListener(this) { instanceIdResult ->
                val newToken = instanceIdResult.token
                Log.e("newToken", newToken)

                PreferenceUtility.addString(this, fcmToken, newToken)
            }.addOnFailureListener(this) {
                if (ApplicationDefine.IS_UNPINNED_BUILD) {
                    PreferenceUtility.addString(this, fcmToken, "testing")
                }
            }

            var notificationManager: NotificationManager? = null
            notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancelAll();


            val android_id = Secure.getString(
                this.getContentResolver(),
                Secure.ANDROID_ID
            )
            var deviceTokenValue = getDeviceId(applicationContext)
            Log.e("deviceTokenValue", deviceTokenValue)
            PreferenceUtility.addString(this, deviceToken, android_id)

            handleIntent(intent)

        }


        mBinding!!.btnLogout.setOnClickListener {

            showDialogForLogout(getString(R.string.logout_msg), this)

        }

    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }


    fun handleIntent(intent: Intent?) {

        val extras = getIntent().extras
        if (extras != null) {

            Log.i("dd", "Extra:" + extras.getString("ack_required"))
            Handler().postDelayed({

                if (extras.getString("ack_required") != null) {
                    val ack_required: String? = extras.getString("ack_required")
                    val source: String? = extras.getString("source")
                    //  val source: String? = intent!!.getStringExtra("source")
                    PreferenceUtility.addString(this, MyConstants.USER_TYPE, source)

                    if (ack_required!!.equals("Y")) {

                        //  val messageId: String? = intent!!.getStringExtra("messageId")
                        val messageId: String? = extras.getString("messageId")
                        val featureId: String? = extras.getString("featureId")
                        //   val featureId: String? = intent!!.getStringExtra("featureId")


                        PreferenceUtility.addString(this, "messageId", messageId)
                        PreferenceUtility.addString(this, "featureId", featureId)

                        val commonBeanNew = CommonBean()
                        commonBeanNew.setmTitle(resources.getString(R.string.notifications))
                        commonBeanNew.setmCommonAction(resources.getString(R.string.notifications))
//                        //commonBeanNew.`object` = listData
//                        var infraAcknowladgeFragment = AcknowladgeFragment.newInstance()
//                        infraAcknowladgeFragment.setData(commonBeanNew)
//                        openFragments(infraAcknowladgeFragment, commonBeanNew)
                    } else {
                        val commonBeanNew = CommonBean()
                        commonBeanNew.setmTitle(resources.getString(R.string.notifications))
                        commonBeanNew.setmCommonAction(resources.getString(R.string.notifications))
                        //commonBeanNew.`object` = listData
                        var infraNotificationFragment = NotificationListFragment.newInstance()
                        infraNotificationFragment.setData(commonBeanNew)
                        (openFragments(infraNotificationFragment, commonBeanNew, true, true))
                    }

                } else {


                    var sessionId = PreferenceUtility.getString(this, MyConstants.SESSION_ID, "")
                    var userName = PreferenceUtility.getString(this, MyConstants.DOMAIN_ID, "")
                    if (userName != null && userName.length > 0) {
                        openDefaultDashboardFragment()

                        /*var defaultView = PreferenceUtility.getString(this, MyConstants.ALLOW_TOGGLE, "")
                        var dashboardFragment: Fragment? = null
                        var commonBean = CommonBean()
                        *//*commonBean.usefulLinksNgo = MyApplication.mInstance!!.usefulLinksNgo
                        commonBean.usefulLinksInfra = MyApplication.mInstance!!.usefulLinksInfra
                        commonBean.usefulLinksBusinessView =
                            MyApplication.mInstance!!.usefulLinksBusinessView*//*

                        usefulLinksNgo = Utils.getListFromJson(this, MyConstants.USEFUL_LINKS_NGO)
                        usefulLinksBusiness = Utils.getListFromJson(this, MyConstants.USEFUL_LINKS_BUSINESS_VIEW)
                        usefulLinksInfra = Utils.getListFromJson(this, MyConstants.USEFUL_LINKS_INFRA)
//                        if (commonBean.usefulLinksNgo != null && commonBean.usefulLinksInfra != null) {
                            if (defaultView.equals("NGO")) {
                                commonBean.setmTitle(MyConstants.NGO)
                                dashboardFragment = DashboardNgoFragment.newInstance()
                                dashboardFragment.setData(commonBean, false)
                                openFragments(dashboardFragment!!, commonBean, false, true)
                            } else if (defaultView.equals(MyConstants.NGO_BUSINESS_VIEW)) {
                                commonBean.setmTitle(MyConstants.NGO_BUSINESS_VIEW)
                                dashboardFragment = NgoBusinessViewFragment.newInstance()
                                dashboardFragment.setData(commonBean)
                                openFragments(dashboardFragment!!, commonBean, false, true)
                            } else if (defaultView.equals("INFRA")) {
                                commonBean.setmTitle(MyConstants.INFRA)
                                dashboardFragment = DashboardFragment.newInstance()
                                dashboardFragment.setData(commonBean, false)
                                openFragments(dashboardFragment!!, commonBean, false, true)
                            }*/
//                        }

                    } else {
                        val commonBean = CommonBean()
                        commonBean.setmCommonAction(MyConstants.LOGIN)
                        openFragments(LoginFragment.newInstance(), commonBean, false, true)
                    }

                }


            }, 200)


        } else {

            var userName = PreferenceUtility.getString(this, MyConstants.DOMAIN_ID, "")
            if (userName != null && userName.length > 0) {
                openDefaultDashboardFragment()
            } else {
                Handler().postDelayed({
                    PreferenceUtility.addString(this, "Push", "0")
                    Handler().postDelayed({
                        val commonBean = CommonBean()
                        commonBean.setmCommonAction(MyConstants.LOGIN)
                        openFragments(LoginFragment.newInstance(), commonBean, false, true)
                    }, 200)
                }, 200)
            }
        }
    }

    fun initListner() {


        mBinding!!.txtHome.setOnClickListener {

            if (mCurrentFragment !is NgoServiceGlanceFragment && mCurrentFragment !is DashboardFragment
                && mCurrentFragment !is NgoBusinessListFragment
            ) {

                var defaultView = PreferenceUtility.getString(this, MyConstants.ALLOW_TOGGLE, "")
                var dashboardFragment: Fragment? = null
                var commonBean = CommonBean()
                /*commonBean.usefulLinksNgo = MyApplication.mInstance!!.usefulLinksNgo
                commonBean.usefulLinksInfra = MyApplication.mInstance!!.usefulLinksInfra
                commonBean.usefulLinksBusinessView =
                    MyApplication.mInstance!!.usefulLinksBusinessView*/
                usefulLinksNgo = Utils.getListFromJson(this, MyConstants.USEFUL_LINKS_NGO)
                usefulLinksBusiness =
                    Utils.getListFromJson(this, MyConstants.USEFUL_LINKS_BUSINESS_VIEW)
                usefulLinksInfra = Utils.getListFromJson(this, MyConstants.USEFUL_LINKS_INFRA)
//                if (commonBean.usefulLinksNgo != null && commonBean.usefulLinksInfra != null) {
                if (defaultView.equals("NGO")) {
                    commonBean.setmTitle(MyConstants.NGO)
//                        dashboardFragment = DashboardNgoFragment.newInstance()
                    dashboardFragment = NgoServiceGlanceFragment.newInstance()
                    dashboardFragment.setData(commonBean)
                    supportFragmentManager.popBackStackImmediate(
                        null,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    );
                    openFragments(dashboardFragment!!, commonBean, false, true)
                } else if (defaultView.equals(MyConstants.NGO_BUSINESS_VIEW)) {
                    commonBean.setmTitle(MyConstants.NGO_BUSINESS_VIEW)
//                        dashboardFragment = NgoBusinessViewFragment.newInstance()
                    dashboardFragment = NgoBusinessListFragment.newInstance()
                    dashboardFragment.setData(commonBean)
                    dashboardFragment.setData(commonBean)
                    supportFragmentManager.popBackStackImmediate(
                        null,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    );
                    openFragments(dashboardFragment!!, commonBean, false, true)
                } else if (defaultView.equals("INFRA")) {
                    commonBean.setmTitle(MyConstants.INFRA)
                    dashboardFragment = DashboardFragment.newInstance()
                    dashboardFragment.setData(commonBean, false)
                    supportFragmentManager.popBackStackImmediate(
                        null,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    );
                    openFragments(dashboardFragment!!, commonBean, false, true)
                }

                /*} else {
                    val commonBean = CommonBean()
                    commonBean.setmCommonAction(MyConstants.LOGIN)

                    openFragments(LoginFragment.newInstance(), commonBean, false, true)
                }*/
            }
        }
        mBinding!!.btnBadge!!.setOnClickListener {

            PreferenceUtility.addString(this, "noti", "1");
            val commonBeanNew = CommonBean()
            commonBeanNew.setmTitle(resources.getString(R.string.notifications))
            commonBeanNew.setmCommonAction(resources.getString(R.string.notifications))
            //commonBeanNew.`object` = listData
            var infraNotificationFragment = NotificationListFragment.newInstance()
            infraNotificationFragment.setData(commonBeanNew)
            (openFragments(infraNotificationFragment!!, commonBeanNew, true, true))


        }
//        bnb?.setSelectedItemId(R.id.id_home)
    }

    fun showProgressBar() {
        mBinding!!.progressBar.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        mBinding!!.progressBar!!.visibility = View.GONE

    }

    fun openFragments(
        fragment: androidx.fragment.app.Fragment,
        commonBean: CommonBean,
        addToBackStack: Boolean,
        replace: Boolean
    ) {
        try {
            mCurrentFragment = fragment
            setToolbar(addToBackStack)
            var fragmentTransaction: FragmentTransaction? = null
            fragmentTransaction = supportFragmentManager.beginTransaction()
            //  fragmentTransaction.add(R.id.container, fragment)
            if (replace)
                fragmentTransaction.replace(R.id.container, fragment, fragment.javaClass.name)
            else
                fragmentTransaction.add(R.id.container, fragment, fragment.javaClass.name)

            if (addToBackStack) {
                fragmentTransaction.addToBackStack(null)
            }

            fragmentTransaction.commit()


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setToolbar(showBackBtn: Boolean) {

        if (mCurrentFragment is NotificationListFragment) {
            mBinding!!.relNotification.visibility = View.GONE
        } else {
            mBinding!!.relNotification.visibility = View.VISIBLE

//            if (mCurrentFragment is NgoBusinessListFragment) {
//                mBinding!!.relNotification.visibility = View.GONE
//            } else {
//                mBinding!!.relNotification.visibility = View.VISIBLE
//            }
        }


        if (showBackBtn) {
            mBinding!!.icBckButton.visibility = View.VISIBLE
        } else {
            mBinding!!.icBckButton.visibility = View.GONE
        }
        if (mCurrentFragment is LoginFragment) {
            mBinding!!.relToolbar.visibility = View.GONE
        } else {
            mBinding!!.txtUsername.text =
                PreferenceUtility.getString(this, MyConstants.DOMAIN_ID, "")
            mBinding!!.relToolbar.visibility = View.VISIBLE
            mBinding!!.txtUserRole.text =
                PreferenceUtility.getString(this, MyConstants.USER_ROLE, "")

        }

    }

    override fun onBackPressed() {

        val currentFragment = supportFragmentManager.fragments.last()
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed();
        }

        if (supportFragmentManager.backStackEntryCount > 1) {
            mBinding!!.icBckButton.visibility = View.VISIBLE
        } else {

            if (PreferenceUtility.getString(this, "noti", "").equals("0")) {
                if (currentFragment.tag.equals("com.jio.siops_ngo.fragment.NotificationListFragment")) {
                    mBinding!!.icBckButton.visibility = View.GONE
                    super.onBackPressed();
                }
            }
            mBinding!!.icBckButton.visibility = View.GONE
            mBinding!!.relNotification.visibility = View.VISIBLE
        }


    }

    private fun dialog() {

        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage(R.string.rooted_device_msg).setCancelable(false)
        dialogBuilder.setPositiveButton("OK", { dialog, whichButton ->
            dialog.dismiss()
            finish()
        })

        val b = dialogBuilder.create()
        b.show()
    }


    fun showDialogForLogout(dialogText: String, mContext: MainActivity) {
        DialogUtils.showYesNoDialogAutoDismiss(this,
            dialogText,
            this.resources.getString(R.string.button_ok),
            this.resources.getString(R.string.cancel),
            object : DialogUtils.AutoDismissOnClickListener {
                override fun onYesClick() {
                    logout(mContext!!)
                    /*var savedDomainId = PreferenceUtility.getString(
                        mContext,
                        MyConstants.SAVED_DOMAIN_ID,
                        ""
                    )
                    var rememberMeBool = PreferenceUtility.getBoolean(mContext, MyConstants.REMEMBER_ME, false)
                    PreferenceUtility.deletePre(mContext)

                    PreferenceUtility.addBoolean(mContext, MyConstants.REMEMBER_ME, rememberMeBool);
                    PreferenceUtility.addString(
                        mContext,
                        MyConstants.SAVED_DOMAIN_ID,
                        savedDomainId
                    )
                    val commonBean = CommonBean()
                    commonBean.setmCommonAction(MyConstants.LOGIN)
                    supportFragmentManager.popBackStackImmediate(
                        null,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    );
                    openFragments(LoginFragment.newInstance(), commonBean, false, true)*/
                }

                override fun onNoClick() {
                }
            })
    }

    fun showDialogForSessionExpired(dialogText: String, mContext: Context) {
        DialogUtils.showYesDialogAutoDismiss(this,
            dialogText,
            this.resources.getString(R.string.button_ok),
            this.resources.getString(R.string.cancel),
            object : DialogUtils.AutoDismissOnClickListener {
                override fun onYesClick() {
                    var savedDomainId = PreferenceUtility.getString(
                        mContext,
                        MyConstants.SAVED_DOMAIN_ID,
                        ""
                    )

                    var rememberMeBool =
                        PreferenceUtility.getBoolean(mContext, MyConstants.REMEMBER_ME, false)
                    PreferenceUtility.deletePre(mContext)

                    PreferenceUtility.addBoolean(mContext, MyConstants.REMEMBER_ME, rememberMeBool);
                    PreferenceUtility.addString(
                        mContext,
                        MyConstants.SAVED_DOMAIN_ID,
                        savedDomainId
                    )

                    /*val commonBean = CommonBean()
                    commonBean.setmCommonAction(MyConstants.LOGIN)
                    supportFragmentManager.popBackStackImmediate(
                        null,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    );
                    openFragments(LoginFragment.newInstance(), commonBean, false, true)*/

                    val intent = Intent(mContext, MainActivity::class.java)// New activity
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                }

                override fun onNoClick() {
                }
            })
    }

    fun openDefaultDashboardFragment() {
        var defaultView = PreferenceUtility.getString(this, MyConstants.ALLOW_TOGGLE, "")
        var dashboardFragment: Fragment? = null
        var commonBean = CommonBean()
        /*commonBean.usefulLinksNgo = MyApplication.mInstance!!.usefulLinksNgo
        commonBean.usefulLinksInfra = MyApplication.mInstance!!.usefulLinksInfra
        commonBean.usefulLinksBusinessView =
            MyApplication.mInstance!!.usefulLinksBusinessView*/

        usefulLinksNgo = Utils.getListFromJson(this, MyConstants.USEFUL_LINKS_NGO)
        usefulLinksBusiness = Utils.getListFromJson(this, MyConstants.USEFUL_LINKS_BUSINESS_VIEW)
        usefulLinksInfra = Utils.getListFromJson(this, MyConstants.USEFUL_LINKS_INFRA)
//                        if (commonBean.usefulLinksNgo != null && commonBean.usefulLinksInfra != null) {
        if (defaultView.equals("NGO")) {
            commonBean.setmTitle(MyConstants.NGO)
            dashboardFragment = NgoServiceGlanceFragment.newInstance()
            dashboardFragment.setData(commonBean)
            openFragments(dashboardFragment!!, commonBean, false, true)
        } else if (defaultView.equals(MyConstants.NGO_BUSINESS_VIEW)) {
            commonBean.setmTitle(MyConstants.NGO_BUSINESS_VIEW)
//            dashboardFragment = NgoBusinessViewFragment.newInstance()
            dashboardFragment = NgoBusinessListFragment.newInstance()
            dashboardFragment.setData(commonBean)
            openFragments(dashboardFragment!!, commonBean, false, true)
        } else if (defaultView.equals("INFRA")) {
            commonBean.setmTitle(MyConstants.INFRA)
            dashboardFragment = DashboardFragment.newInstance()
            dashboardFragment.setData(commonBean, false)
            openFragments(dashboardFragment!!, commonBean, false, true)
        }


    }


    fun logout(activity: MainActivity) {
        showProgressBar()
        val requestBody = HashMap<String, Any>()
        requestBody["userName"] = PreferenceUtility.getString(this, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "userInfo"
        CoroutineScope(Dispatchers.IO).launch {

            var job = async {
                BaseCoroutines().fetchData(
                    requestBody,
                    Busicode.Logout,
                    activity
                )
            }
            withContext(Dispatchers.Main)
            {

                var response = job.await()
                hideProgressBar()

                if (response!!.responseEntity != null && response.status == MappActor.STATUS_OK) {
                    filterData(response)
                } else if (response!!.errorCode != null && response!!.errorCode.equals(MappActor.VERSION_SESSION_INVALID)) {
                    var savedDomainId = PreferenceUtility.getString(
                        activity,
                        MyConstants.SAVED_DOMAIN_ID,
                        ""
                    )

                    var rememberMeBool =
                        PreferenceUtility.getBoolean(activity, MyConstants.REMEMBER_ME, false)
                    PreferenceUtility.deletePre(activity)

                    PreferenceUtility.addBoolean(activity, MyConstants.REMEMBER_ME, rememberMeBool);
                    PreferenceUtility.addString(
                        activity,
                        MyConstants.SAVED_DOMAIN_ID,
                        savedDomainId
                    )
                    val intent = Intent(activity, MainActivity::class.java)// New activity
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
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
            val respMsg = mCoroutinesResponse.responseEntity as HashMap<String, Any>

            var savedDomainId = PreferenceUtility.getString(
                this,
                MyConstants.SAVED_DOMAIN_ID,
                ""
            )
            var rememberMeBool = PreferenceUtility.getBoolean(this, MyConstants.REMEMBER_ME, false)
            PreferenceUtility.deletePre(this)

            PreferenceUtility.addBoolean(this, MyConstants.REMEMBER_ME, rememberMeBool);
            PreferenceUtility.addString(
                this,
                MyConstants.SAVED_DOMAIN_ID,
                savedDomainId
            )

            val intent = Intent(this, MainActivity::class.java)// New activity
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}
