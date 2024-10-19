package com.jiolib.libclasses.business

import android.util.Log
import com.jio.myjio.bean.CoroutinesResponse
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.network.net.MappClient
import com.jio.siops_ngo.network.utils.RsaUtil
import com.jio.siops_ngo.network.utils.Tools
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import java.security.Key
import java.security.interfaces.RSAPublicKey
import java.util.*
import java.util.concurrent.TimeUnit

class FileDataCoroutines {
    private var keys: Map<String, Key>? = null

    suspend fun getFileFromAkamaized(fileName: String): CoroutinesResponse {

        var fileDetailJob = GlobalScope.async {
            getFileFromAkamaizedAsync(fileName) as CoroutinesResponse
        }
        return fileDetailJob.await();

    }

    /*suspend fun getFileDetail(fileName: String): CoroutinesResponse {


        var fileDetailJob = GlobalScope.async {
            getFileDetailAsync(fileName) as CoroutinesResponse
        }
        return fileDetailJob.await();

    }*/

    fun getFileFromAkamaizedAsync(url: String): CoroutinesResponse {
        var lastTime = System.currentTimeMillis()
        val mCoroutinesResponse = CoroutinesResponse()
        val responseEntity = HashMap<String, Any>()
        var responseString = "error"
        try {

            val client = OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build()


            val requestBuilder = okhttp3.Request.Builder()
                    .url(url)
                    .get()

            requestBuilder.addHeader("Accept", "application/json")

            val request = requestBuilder.build()
            Log.d("Request", request.toString())
            val response = client.newCall(request).execute()
            Log.d("Request", "Time in Zla : " + (System.currentTimeMillis() - lastTime))
            val responseCode = response.code()
            if (responseCode == 200) {
                responseString = response.body()!!.string()
                Log.d("responseString", responseString)
                responseEntity.put("Response", responseString)
                mCoroutinesResponse.responseEntity = responseEntity
                mCoroutinesResponse.status = 0

            } else {
                responseString = response.body()!!.string()
                Log.d("responseString", responseString)
                responseEntity.put("Response", responseString)
                mCoroutinesResponse.responseEntity = responseEntity
                mCoroutinesResponse.status = 1
            }


        } catch (e: Exception) {

            Log.d("responseString", responseString)
            responseEntity.put("Response", responseString)
            mCoroutinesResponse.responseEntity = responseEntity
            mCoroutinesResponse.status = 1
            e.printStackTrace()
        }

        return mCoroutinesResponse
    }

    /*suspend fun getFileDetailAsync(fileName: String): CoroutinesResponse {
        var status = MappActor.STATUS_OK

        val busiParams = HashMap<String, Any>()
        busiParams["fileName"] = fileName

        val transactionId = MappClient.generateTransactionId()

        val busiCode = "GetFileDetail"
        val requestEntity = HashMap<String, Any>()
        requestEntity["busiParams"] = busiParams
        requestEntity["busiCode"] = busiCode
        requestEntity["transactionId"] = transactionId
        requestEntity["isEncrypt"] = MappActor.ENCRYPTION_ENABLED

       // return  MappActor().executeOnCoroutines(busiCode, requestEntity, null) as CoroutinesResponse


        var mCoroutinesResponse: CoroutinesResponse = getFileDetailAsync(busiCode, requestEntity, null)
        try {
            if (0 == mCoroutinesResponse?.status) {

                val code = mCoroutinesResponse?.responseEntity?.get("code") as String
                var respMsg: Map<String, Any>? = null
                respMsg = mCoroutinesResponse?.responseEntity?.get("respMsg") as Map<String, Any>
                Console.debug(String.format("JioPreviewOffer::GetFileDetail=%s respMsg=%s", code, respMsg))
                if ("0" == code) {
                    mCoroutinesResponse?.responseEntity = respMsg
                } else {
                    status = 1
                    mCoroutinesResponse?.responseEntity
                }
            }

        } catch (e: Exception) {
            Console.printThrowable(e)
            mCoroutinesResponse.status = MappActor.STATUS_INTERNAL_ERROR
        } finally {
            mCoroutinesResponse
        }
        return mCoroutinesResponse
    }*/

/*suspend fun getFileDetail(fileName: String,bundle: Bundle): CoroutinesResponse {


    var fileDetailJob = GlobalScope.async {
        getFileDetailAsync(fileName,bundle) as CoroutinesResponse
    }
    return fileDetailJob.await();

}*/

/*suspend fun getFileDetailAsync(fileName: String,bundle: Bundle): CoroutinesResponse {
    var status = MappActor.STATUS_OK

    val busiParams = HashMap<String, Any>()
    busiParams["fileName"] = fileName

    val transactionId = MappClient.generateTransactionId()

    val busiCode = "GetFileDetail"
    val requestEntity = HashMap<String, Any>()
    requestEntity["busiParams"] = busiParams
    requestEntity["busiCode"] = busiCode
    requestEntity["transactionId"] = transactionId
    requestEntity["isEncrypt"] = MappActor.ENCRYPTION_ENABLED

    // return  MappActor().executeOnCoroutines(busiCode, requestEntity, null) as CoroutinesResponse


    var mCoroutinesResponse: CoroutinesResponse = getFileDetailAsync(busiCode, requestEntity, null)
    try {
         mCoroutinesResponse.bundle=bundle
        if (0 == mCoroutinesResponse?.status) {

            val code = mCoroutinesResponse?.responseEntity?.get("code") as String
            var respMsg: Map<String, Any>? = null
            respMsg = mCoroutinesResponse?.responseEntity?.get("respMsg") as Map<String, Any>
            Console.debug(String.format("JioPreviewOffer::GetFileDetail=%s respMsg=%s", code, respMsg))
            if ("0" == code) {
                mCoroutinesResponse?.responseEntity = respMsg
            } else {
                status = 1
                mCoroutinesResponse?.responseEntity
            }
        }

    } catch (e: Exception) {
        Console.printThrowable(e)
        mCoroutinesResponse.status = MappActor.STATUS_INTERNAL_ERROR
    } finally {
        mCoroutinesResponse
    }
    return mCoroutinesResponse
}*/

suspend fun getFileDetailAsync(activity: MainActivity, busiCode: String, requestEntity: HashMap<String, Any>, requestEntityList: List<Map<String, Any>>?): CoroutinesResponse {
    var id = GlobalScope.async {
        MappActor().executeOnCoroutines(activity, busiCode, requestEntity, null) as CoroutinesResponse
    }
    return id.await()
}

suspend fun getTranseKey(activity: MainActivity): CoroutinesResponse {
    var fileDetailJob = GlobalScope.async {
        getTranseKeyAsync(activity) as CoroutinesResponse
    }
    return fileDetailJob.await();
}

suspend fun getTranseKeyAsync(activity: MainActivity): CoroutinesResponse {

    var status = MappActor.STATUS_OK
    val responseEntity = HashMap<String, Any>()
    this.keys = RsaUtil.generateRSAKeyPair()
    val publicKey = keys!!.get("PUBLIC_KEY") as RSAPublicKey
    val busiParams = HashMap<String, Any>()
    busiParams["type"] = 0
    busiParams["key"] = RsaUtil.wrapPublicKey(publicKey)
    if (MappActor.DEVICE_INFO_ENABLE_HANDSHAKE == false) {
        val deviceInfo = Tools.getDeviceInfoHanshake()
        if (deviceInfo != null) {
            busiParams["deviceInfo"] = deviceInfo
        }
    }

    val transactionId = MappClient.generateTransactionId()
    val busiCode = "GetTransKey"
    val requestEntity = HashMap<String, Any>()
    requestEntity["busiParams"] = busiParams
    requestEntity["busiCode"] = busiCode
    requestEntity["transactionId"] = transactionId
//        requestEntity["isEncrypt"] = MappActor.ENCRYPTION_ENABLED

    requestEntity["isEncrypt"] = false


    var mCoroutinesResponse: CoroutinesResponse = getLoginAsync(activity, busiCode, requestEntity, null)
    return mCoroutinesResponse
}
    suspend fun getLoginAsync(activity: MainActivity, busiCode: String, requestEntity: HashMap<String, Any>, requestEntityList: List<Map<String, Any>>?): CoroutinesResponse {
        var id = CoroutineScope(Dispatchers.IO).async {


            MappActor().executeOnCoroutines(activity, busiCode, requestEntity, null) as CoroutinesResponse
        }

        return id.await()
    }
}