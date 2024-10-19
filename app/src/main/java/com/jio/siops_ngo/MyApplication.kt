package com.jio.siops_ngo

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.jio.jioinfra.bean.CommonBean
import com.jio.jioinfra.utilities.MyConstants
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.network.net.MappClient
import com.jio.siops_ngo.network.utils.Console
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.PreferenceUtility

class MyApplication : MultiDexApplication() {
	public val TAG = "MyApplication"
	public var appId: String? = null
	public var versionName = "1.1"
	public var lang = "en_US"
	public var statusBarHeight = 0

	public var mMyJioKey: String? = null

	private var appVersion = 0

	public var dashboardCommonBan: CommonBean? = null
	public var `usefulLinksNgo`: Any? = null
	public var `usefulLinksInfra`: Any? = null
	public var `usefulLinksBusinessView`: Any? = null

        // Called when the application isMap starting, before any other application objects have been created.
        // Overriding this method is totally optional!
	override fun onCreate() {
	    super.onCreate()
			mInstance = this
			mMyJioKey = ApplicationDefine.CAMPAIGN_ANALYTICS1+ApplicationDefine.CAMPAIGN_ANALYTICS2
			initApplication()
            // Required initialization logic here!
	}

        // Called by the system when the device configuration changes while your component is running.
        // Overriding this method is totally optional!
	override fun onConfigurationChanged ( newConfig : Configuration) {
	    super.onConfigurationChanged(newConfig)
	}

        // This is called when the overall system is running low on memory, 
        // and would like actively running processes to tighten their belts.
        // Overriding this method is totally optional!
	override fun onLowMemory() {
	    super.onLowMemory()
	}

	companion object {
		public var mInstance: MyApplication? = null
		@Synchronized
		public fun getInstance(): MyApplication {

			if (mInstance == null)
				mInstance = MyApplication()

			return mInstance as MyApplication
		}
	}
	public fun getmMyJioKey(): String {
		return mMyJioKey!!
	}

	public fun getAppVersion(): Int {
		try {
			if (appVersion == 0) {
				val pInfo = applicationContext.packageManager.getPackageInfo(applicationContext.packageName, PackageManager.GET_META_DATA)
				appVersion = pInfo.versionCode
				versionName = pInfo.versionName
			} else {
				return appVersion
			}
		} catch (e: PackageManager.NameNotFoundException) {
			return appVersion
		} catch (e: Exception) {
			return appVersion
		}

		return appVersion
	}

	public fun getAppVersionName(): String {
		try {
			if (appVersion == 0) {
				val pInfo = applicationContext.packageManager.getPackageInfo(applicationContext.packageName, PackageManager.GET_META_DATA)
				versionName = pInfo.versionName
			} else {
				return versionName
			}
		} catch (e: PackageManager.NameNotFoundException) {
			return versionName
		} catch (e: Exception) {
			return versionName
		}

		return versionName
	}
	public fun initApplication() {
		try {
			ApplicationDefine.getMappServerAddress()
			val context = applicationContext

			appId = context.packageName

			 MappClient.getMappClient().prepare(ApplicationDefine.MAPP_SERVER_ADDRESS, ApplicationDefine.SUPPORT_DISPATCHER);

			val packageInfo = context.packageManager
					.getPackageInfo(appId, 0)
			versionName = packageInfo.versionName
			appVersion = packageInfo.versionCode

			MappActor.ENCRYPTION_ENABLED = ApplicationDefine.ENCRYPTION_ENABLED
			MappActor.REPLICA_ENABLED = true


		} catch (e: Exception) {
			Console.printThrowable(e)
			MyExceptionHandler.handle(e)
		}


	}
	fun getDbCommonBean(): CommonBean {
		return dashboardCommonBan!!
	}

	fun setDbCommonBean(dashboardCommonBan: CommonBean) {
		this.dashboardCommonBan = dashboardCommonBan
	}

	override fun attachBaseContext(base: Context?) {
		super.attachBaseContext(base)
		MultiDex.install(this)
	}

	fun getSessionId():String{
		var sessionId:String? = ""

		sessionId = PreferenceUtility.getString(applicationContext, MyConstants.SESSION_ID, "")
		return  sessionId!!

	}

	fun getTransKey():String{
		var transKey:String? = ""

		transKey = PreferenceUtility.getString(applicationContext, MyConstants.TRANS_KEY, "")
		return  transKey!!

	}


}