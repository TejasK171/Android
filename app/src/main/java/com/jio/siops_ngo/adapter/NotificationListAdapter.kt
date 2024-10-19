package com.jio.jioinfra.ui.dashboard.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.bean.CommonBean
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.Utils
import com.jio.siops_ngo.viewholder.NotificationServiceItemViewHolder
import java.text.SimpleDateFormat


class NotificationListAdapter(private val appList: List<Map<String, Any>>, private val activity: MainActivity?) : RecyclerView.Adapter<NotificationServiceItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viwType: Int): NotificationServiceItemViewHolder {
        var view: View?


        view = LayoutInflater.from(parent.context).inflate(R.layout.notification_list_item, parent, false)

        return NotificationServiceItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationServiceItemViewHolder, position: Int) {
        try {
            val content = appList[position]


            var title = ""
            var sapCount = ""
            var time = ""

            if (content["title"] != null) {
                title = content["title"] as String
                holder.title!!.text = title
            }
            if (content["message"] != null) {
                sapCount = content["message"] as String
                holder.subTitle!!.text = sapCount
            }

            if (content["insertTime"] != null) {
                time = content["insertTime"] as String

                var ddate = content["insertTime"] as String
                var splitDate = ddate.split(" ")
                val date = SimpleDateFormat("dd-MM-yyyy").parse(splitDate[0])
                val format = SimpleDateFormat("dd-MMM")
                Log.e("date ", format.format(date))


               var timemsg= Utils.setDateParsing(time)



                holder.txt_time!!.text =format.format(date)+" "+timemsg


            }


          //  holder.moreIcon!!.visibility = View.GONE

            holder.title!!.setTextColor(activity!!.resources.getColor(R.color.black))
           holder.cardView!!.setOnClickListener {

               val ack_required:String?=content["ackRequired"] as String
             //  ack_required?.someMethodCall()


               ack_required?.let {
                   // not null do something
                   if(ack_required!!.equals("Y")) {

                       val messageId: String? = content["message"] as String
                       val featureId: String? = content["featureId"] as String

                       PreferenceUtility.addString(activity,"messageId",messageId)
                       PreferenceUtility.addString(activity,"featureId",featureId)


                       val commonBeanNew = CommonBean()
                       commonBeanNew.setmTitle(activity.resources.getString(R.string.notifications))
                       commonBeanNew.setmCommonAction(activity.resources.getString(R.string.notifications))
                       //commonBeanNew.`object` = listData
//                       var infraNotificationFragment = AcknowladgeFragment.newInstance()
//                       infraNotificationFragment.setData(commonBeanNew)
//                       activity.openFragments(infraNotificationFragment, commonBeanNew)
                   }
               }
           }
        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
        }
    }

    override fun getItemCount(): Int {
        return appList.size
    }
}