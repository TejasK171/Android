package com.jio.siops_ngo.utilities

import android.content.Context
import android.net.ConnectivityManager
import android.net.ParseException
import android.provider.Settings.Secure
import android.text.Html
import android.text.Spanned
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jio.siops_ngo.MainActivity
import java.sql.DriverManager.println
import java.text.DateFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class Utils {


    companion object {


        fun dateConvert(D: String?): String? {
            val format1 = SimpleDateFormat("yyyy-MM-dd")
            val format2 = SimpleDateFormat("dd-MMM-yyyy")
            var date: Date? = null
            try {
                date = format1.parse(D)
            } catch (e: ParseException) { // TODO Auto-generated catch block
                e.printStackTrace()
            }
            var dateString = format2.format(date)
            dateString = dateString.replace("-", " ")
            println(dateString)
            return dateString
        }


        fun convertDate(dateStr: String?, currentDateFormat: SimpleDateFormat, requiredDateFormat: SimpleDateFormat): String? {
            /*val format1 = SimpleDateFormat("yyyy-MM-dd")
            val format2 = SimpleDateFormat("dd-MMM-yyyy")*/
            var date: Date? = null
            try {
                date = currentDateFormat.parse(dateStr)
            } catch (e: ParseException) { // TODO Auto-generated catch block
                e.printStackTrace()
            }
            var dateString = requiredDateFormat.format(date)
//            dateString = dateString.replace("-", " ")
            println(dateString)
            return dateString
        }



        val calendar = Calendar.getInstance()

        fun getNextWeek(days:Int): Array<String?>? {
            val format: DateFormat = SimpleDateFormat("dd-MMM")
            val days = arrayOfNulls<String>(days)
            for (i in 0..6) {
                days[i] = format.format(calendar.time)
                calendar.add(Calendar.DATE, 1)
            }
            return days
        }


        fun getNextWeekYear(days:Int): Array<String?>? {
            val format: DateFormat = SimpleDateFormat("yyyy-MM-dd")
            val days = arrayOfNulls<String>(days)
            for (i in 0..6) {
                days[i] = format.format(calendar.time)
                calendar.add(Calendar.DATE, 1)
            }
            return days
        }

        fun getPreviousWeek(days:Int): Array<String?>? {
            calendar.add(Calendar.DATE, -days)
            return getNextWeek(days)
        }

        fun getPreviousWeekYear(days:Int): Array<String?>? {
            calendar.add(Calendar.DATE, -days)
            return getNextWeekYear(days)
        }

        fun maxOfNumList(numList: ArrayList<Int>): Int {

            return Collections.max(numList)
        }

        fun getBoldString(
            textNotBoldFirst: String,
            textToBold: String,
            textNotBoldLast: String
        ): Spanned? {
            var resultant: String? = null
            resultant = "$textNotBoldFirst <b>$textToBold</b> $textNotBoldLast"
            return Html.fromHtml(resultant)
        }


        fun verifyAvailableNetwork(activity: MainActivity):Boolean{
            val connectivityManager=activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo=connectivityManager.activeNetworkInfo
            return  networkInfo!=null && networkInfo.isConnected
        }

        var fcmToken: String = "FCM_TOKEN"
        var deviceToken: String = "Device_Token"


        fun getDeviceId(context: Context): String {
            // returns 64-bit unique string
            return Secure.getString(context.getContentResolver(), Secure.ANDROID_ID)

        }


        fun getShowDilog(context: Context): String {
            // returns 64-bit unique string
            return Secure.getString(context.getContentResolver(), Secure.ANDROID_ID)

        }

        fun getDissmissDilog(context: Context): String {
            // returns 64-bit unique string
            return Secure.getString(context.getContentResolver(), Secure.ANDROID_ID)

        }


        fun getAgeing(selection: Int): String {
            var ageingVal: String? = null
            when (selection) {
                0 -> ageingVal = " > 12 Hrs"
                1 -> ageingVal = "8 - 12 Hrs"
                2 -> ageingVal = "4 - 8 Hrs"
                3 -> ageingVal = "0 - 4 Hrs"
            }
            return ageingVal!!
        }

        fun hasIndex(index: Int, dataList: ArrayList<Map<String, Any>>): Boolean {
            if (index < dataList!!.size)
                return true
            else
                return false

        }



        fun hasIndexInService(index: Int, dataList:List<HashMap<String, Any>>?): Boolean {
            if (index < dataList!!.size)
                return true
            else
                return false

        }


        @Throws(ParseException::class)
        fun setDateParsing(date: String?): String? { // This is the format date we want
            val mSDF: DateFormat = SimpleDateFormat("hh:mm a")
            // This format date is actually present
            val formatter = SimpleDateFormat("yyyy-mm-dd hh:mm")
            return mSDF.format(formatter.parse(date))
        }

        fun getListFromJson(mContext: Context, prefsKey: String):List<Map<String, Any>> {

            var usefulLinks: List<Map<String, Any>>? = ArrayList()
            val gson =  Gson();
            val usefulLinksBusinessJson = PreferenceUtility.getString(mContext, prefsKey, "")

            val typeToken = object : TypeToken<ArrayList<Map<String, Any>>>() {}.type

            if(usefulLinksBusinessJson!=null && usefulLinksBusinessJson.length>0)
                usefulLinks =  gson.fromJson(usefulLinksBusinessJson, typeToken) as ArrayList<Map<String, Any>>
            return usefulLinks!!


        }

        fun getCommaSeparatedCount(count:Int):String{
            var commaSeparatedVal: String? =""
            val format = NumberFormat.getCurrencyInstance(Locale("en","US"))
            val currency = format.format(count)
            if(currency.contains(".")){
                val currencySplit = currency.split(".")
//                commaSeparatedVal= currencySplit[0].replace("\u20B9","")
                commaSeparatedVal= currencySplit[0].replace("$","")
            }else{
//                commaSeparatedVal= currency.replace("\u20B9","")
                commaSeparatedVal= currency.replace("$","")
            }
            return  commaSeparatedVal

        }

    }



}