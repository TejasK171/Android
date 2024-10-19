package com.jio.siops_ngo.fcm


import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.AudioManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.utilities.PreferenceUtility
import java.util.*


/**
 * Ranjan Gupta
 */

class MMyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(s: String) {
        Log.e("NEW_TOKEN", s)
    }

    @SuppressLint("ResourceAsColor")
    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        super.onMessageReceived(remoteMessage);

//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            sendNotification1(remoteMessage);
//        }else{
//            sendNotification(remoteMessage);
//        }

        val data = remoteMessage.data
        val random = Random()
        val m = random.nextInt(9999 - 1000) + 1000


        // val params = remoteMessage.notification!!.body
        // val `object` = JSONObject(params as Map<String, String>)
        //   Log.e("JSON_OBJECT", `params`.toString())

        val NOTIFICATION_CHANNEL_ID = getString(R.string.default_notification_channel_id)


        //   val notificationJsonObj: JSONObject = JSONObject(remoteMessage.notification!!.body)

        //  var titlemsg = notificationJsonObj.getString("message")
        var title =data.get("title")
        var titlemsg =data.get("message")
        var ack_required: String? = "N"
        var messageId: String? = ""
        var featureId: String? = ""
        var source: String? = ""
        ack_required = data.get("ack_required")
        if (data.get("ack_required").equals("Y")) {
            ack_required = data.get("ack_required")
            messageId =data.get("messageId")
            featureId = data.get("featureId")
            source = data.get("source")

        }else{
            ack_required = data.get("ack_required")
            source = data.get("source")
        }


        //   Log.e("ack_required",ack_required)
        //  Log.e("JSON_OBJECT", notificationJsonObj.toString())


        val pattern = longArrayOf(0, 1000, 500, 1000)
        val sound: Uri =
            Uri.parse("android.resource://" +
                    getApplicationContext().getPackageName() +
                    "/" +
                    R.raw.eventually)

        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_ALARM)
            .build()

        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (ack_required!!.equals("Y")) {
            PreferenceUtility.addString(this,"Push","1")
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("messageId", messageId)
            intent.putExtra("featureId", featureId)
            intent.putExtra("ack_required", ack_required)
            intent.putExtra("source", source)
            intent.putExtra("click", "click")
            //intent.putExtra("messageId")
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            // intent.addFlags(Intent.FLAG_FROM_BACKGROUND);
            PreferenceUtility.addString(this,"Push","1")
            val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {



                val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, "SIOIS",
                    NotificationManager.IMPORTANCE_HIGH)

                notificationChannel.description = ""
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.RED
                notificationChannel.vibrationPattern = pattern
                notificationChannel.enableVibration(true)


                mNotificationManager.createNotificationChannel(notificationChannel)
            }


            // to diaplay notification in DND Mode
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = mNotificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID)
                channel.canBypassDnd()
            }

            val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)


            notificationBuilder.setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(titlemsg)
                .setDefaults(Notification.DEFAULT_ALL).setGroupSummary(true).addAction(android.R.drawable.ic_menu_directions, "Acknowledge", pendingIntent)
                .setWhen(System.currentTimeMillis()).setSound(sound, AudioManager.STREAM_NOTIFICATION)
                .setSmallIcon(R.drawable.app_icon).setNumber(10)
                .setAutoCancel(true).setContentIntent(pendingIntent)


            mNotificationManager.notify(m, notificationBuilder.build())



        } else {
            PreferenceUtility.addString(this,"Push","1")
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("ack_required", ack_required)
            intent.putExtra("click", "click")
            intent.putExtra("source", source)

            intent.addFlags(Intent.FLAG_FROM_BACKGROUND);
            PreferenceUtility.addString(this,"Push","1")
            val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, title,
                    NotificationManager.IMPORTANCE_HIGH)

                notificationChannel.description = ""
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.RED
                notificationChannel.vibrationPattern = pattern
                notificationChannel.enableVibration(true)


                mNotificationManager.createNotificationChannel(notificationChannel)
            }

            // to diaplay notification in DND Mode
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = mNotificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID)
                channel.canBypassDnd()
            }

            val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)


            notificationBuilder.setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(titlemsg)
                .setDefaults(Notification.DEFAULT_ALL).setGroupSummary(true)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.app_icon).setNumber(10).setSound(sound,AudioManager.STREAM_NOTIFICATION)
                .setAutoCancel(true).setContentIntent(pendingIntent)
                 //mNotificationManager.sound = Uri.parse("android.resource://" + applicationContext.getPackageName() + "/" + R.raw.eventually);



            mNotificationManager.notify(m, notificationBuilder.build())
        }
    }

}