package com.jio.siops_ngo.infra.broadcastreceiver

import androidx.localbroadcastmanager.content.LocalBroadcastManager
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.telephony.SmsManager
import android.telephony.SmsMessage
import android.util.Log


class SmsBroadCastReceiver : BroadcastReceiver() {

    // Get the object of SmsManager
    internal val sms = SmsManager.getDefault()

    override fun onReceive(context: Context, intent: Intent) {

        // Retrieves a map of extended data from the intent.
        val bundle = intent.extras
        var address: String? = ""
        try {

            if (bundle != null) {

                val pdusObj = bundle.get("pdus") as Array<Any>?

                for (i in pdusObj!!.indices) {

                    val currentMessage = SmsMessage.createFromPdu(pdusObj[i] as ByteArray)
                    val phoneNumber = currentMessage.getDisplayOriginatingAddress()

                    var message = currentMessage.messageBody

                    address = currentMessage.originatingAddress

//                    message = message.substring(0, message.length - 1)
                    Log.i("SmsReceiver", "senderNum: $phoneNumber; message: $message")

//                    if (address!!.contains("NGOOTP", ignoreCase = true) || address!!.equals("+918618998199")) {
                    if (address!!.contains("NGOOTP", ignoreCase = true)) {
                            val myIntent = Intent("otpVal")
                            myIntent.putExtra("message", message)
                            LocalBroadcastManager.getInstance(context).sendBroadcast(myIntent)
                        }
                    // Show Alert

                } // end for loop
            } // bundle is null

        } catch (e: Exception) {
            Log.e("SmsReceiver", "Exception smsReceiver$e")

        }

    }
}