package com.jio.jioinfra.network.business

import com.jio.jioinfra.utilities.MyConstants
import com.jio.myjio.bean.CoroutinesResponse
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.network.net.MappClient
import com.jio.siops_ngo.network.utils.Console
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class BaseCoroutines {


    suspend fun fetchData(requestBodyMap: HashMap<String, Any>, busiCode: String, activity: MainActivity): CoroutinesResponse? {
        if(Utils.verifyAvailableNetwork(activity as MainActivity)) {
        var status = MappActor.STATUS_OK
        try {

            val requestHeader = HashMap<String, Any>()
            requestHeader["serviceName"] = "userInfo"
            val transactionId = MappClient.generateTransactionId()
            val busiCode = busiCode
            val requestEntity = HashMap<String, Any>()
            val mainData = HashMap<String, Any>()
            mainData.put("requestHeader", requestHeader)
//            mainData.put("requestBody", requestBody)
            requestBodyMap.put("sessionId", PreferenceUtility.getString(activity,MyConstants.SESSION_ID, ""))
            requestBodyMap.put("mobileApplicationCode", "NGO_ANDROID")
            mainData.put("requestBody", requestBodyMap)

            requestEntity["busiParams"] = mainData
            requestEntity["busiCode"] = busiCode
            requestEntity["transactionId"] = transactionId
            requestEntity["isEncrypt"] = MyConstants.ENCRYPTION_ENABLED

            var mCoroutinesResponse: CoroutinesResponse = getLoginAsync(activity, busiCode, requestEntity, null)
            return mCoroutinesResponse


        } catch (e: Exception) {
            Console.printThrowable(e)
            status = MappActor.STATUS_INTERNAL_ERROR
        }
        }else{
//            T.show(activity, "Please check internet connection !!", 0)
            var mCoroutinesResponse = CoroutinesResponse()
            mCoroutinesResponse.errorMsg = activity.resources.getString(R.string.no_internet_connection)
            return mCoroutinesResponse
        }
        return null
    }

    suspend fun getLoginAsync(activity: MainActivity, busiCode: String, requestEntity: HashMap<String, Any>, requestEntityList: List<Map<String, Any>>?): CoroutinesResponse {
        var id = CoroutineScope(Dispatchers.IO).async {


            MappActor().executeOnCoroutines(activity, busiCode, requestEntity, null) as CoroutinesResponse
        }

        return id.await()
    }


}