package com.jio.siops_ngo.adapter


import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.utilities.Busicode
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.Utils
import com.jio.siops_ngo.viewholder.NgoMnpTrendItemHolder
import java.text.SimpleDateFormat


class NgoMnpTrendAdapter(
    private val appList: List<Map<String, Any>>,
    private val activity: MainActivity?, val busicode:String
) : RecyclerView.Adapter<NgoMnpTrendItemHolder>() {
    var boldTypeface: Typeface? = null
    var lightTypeface: Typeface? = null
    var todayCount: Int? = 0
    var ydayCount: Int? = 0
    var dateFormant:String? = "yyyy-MM-dd"
    override fun onCreateViewHolder(parent: ViewGroup, viwType: Int): NgoMnpTrendItemHolder {
        var view: View?
        boldTypeface = ResourcesCompat.getFont(activity!!, R.font.jio_type_bold)
        lightTypeface = ResourcesCompat.getFont(activity!!, R.font.jio_type_light)
        view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ngo_mnp_trend_item, parent, false)

        return NgoMnpTrendItemHolder(view)
    }

    override fun onBindViewHolder(holder: NgoMnpTrendItemHolder, position: Int) {
        try {
            val content = appList[position]
            var title1: String? = ""
            var title2: String? = ""
            var title3: String? = ""
            var title4: String? = ""
            var title5: String? = ""

            if(busicode.equals(Busicode.NGOPortInPortOutCountTrends)){
                dateFormant = "dd-MM-yyyy"
                title1 = "ddate"
                title2 = "pintotal"
                title3 = "pinact"
                title4 = "pouttotal"
                title5 = "poutact"
                holder.title5!!.visibility = View.VISIBLE
            }else if(busicode.equals(Busicode.NGOOrderActivation)){
                title1 = "ddate"
                title2 = "entered"
                title3 = "activated"
                title4 = "rejected"
                title5 = "wip"
                holder.title5!!.visibility = View.VISIBLE
            }else if(busicode.equals(Busicode.NGORechargeActivation)){
                title1 = "rdate"
                title2 = "initiated"
                title3 = "paymentfailed"
                title4 = "paymentsuccess"
                title5 = "succes"
                holder.title5!!.visibility = View.VISIBLE
            }




            if (position == 0) {
                holder.lnrHeader!!.visibility = View.VISIBLE
                holder.lnrTrendValues!!.visibility = View.GONE
            } else {
                holder.lnrHeader!!.visibility = View.GONE
                holder.lnrTrendValues!!.visibility = View.VISIBLE
            }
            if (content.containsKey(title1) && content[title1] != null) {
                if(position==0) {
                    holder.header1!!.text = content[title1] as String

                }else{
                    var ddate = content[title1] as String
                    var splitDate = ddate.split(" ")
                    val date = SimpleDateFormat(dateFormant).parse(splitDate[0])
                    val format = SimpleDateFormat("dd-MMM")
                    Log.e("date ", format.format(date))
                    holder.title1!!.text = format.format(date)
                }
            }

            if (content.containsKey(title2) && content[title2] != null) {
                if(position==0) {
                    holder.header2!!.text = content[title2].toString()
                }else{
                    holder.title2!!.text = content[title2].toString()
                    /*val format = NumberFormat.getCurrencyInstance(Locale("hi","IN"))
                    val currency = format.format(content[title2] as Int)

//                holder.txtYday!!.text = content["yesCount"].toString()

                    if(currency.contains(".")){
                        val currencySplit = currency.split(".")
                        holder.title2!!.text = currencySplit[0].replace("\u20B9","")
                    }else{
                        holder.title2!!.text = currency.replace("\u20B9","")
                    }*/
                    holder.title2!!.text = Utils.getCommaSeparatedCount(content[title2] as Int)

                }
            }

            if (content.containsKey(title3) && content[title3] != null) {
                if(position==0) {
                    holder.header3!!.text = content[title3].toString()
                }else{
                    holder.title3!!.text = content[title3].toString()
                    /*val format = NumberFormat.getCurrencyInstance(Locale("hi","IN"))
                    val currency = format.format(content[title3] as Int)

//                holder.txtYday!!.text = content["yesCount"].toString()

                    if(currency.contains(".")){
                        val currencySplit = currency.split(".")
                        holder.title3!!.text = currencySplit[0].replace("\u20B9","")
                    }else{
                        holder.title3!!.text = currency.replace("\u20B9","")
                    }*/
                    holder.title3!!.text = Utils.getCommaSeparatedCount(content[title3] as Int)
                }
            }
            if (content.containsKey(title4) && content[title4] != null) {
                if(position==0)
                    holder.header4!!.text = content[title4].toString()
                else {
                    holder.title4!!.text = content[title4].toString()
                    /*val format = NumberFormat.getCurrencyInstance(Locale("hi","IN"))
                    val currency = format.format(content[title4] as Int)

//                holder.txtYday!!.text = content["yesCount"].toString()

                    if(currency.contains(".")){
                        val currencySplit = currency.split(".")
                        holder.title4!!.text = currencySplit[0].replace("\u20B9","")
                    }else{
                        holder.title4!!.text = currency.replace("\u20B9","")
                    }*/

                    holder.title4!!.text = Utils.getCommaSeparatedCount(content[title4] as Int)
                }
            }
            if (content.containsKey(title5) && content[title5] != null) {
                if(position==0)
                    holder.header5!!.text = content[title5].toString()
                else {
                    holder.title5!!.text = content[title5].toString()
                    /*val format = NumberFormat.getCurrencyInstance(Locale("hi","IN"))
                    val currency = format.format(content[title5] as Int)

//                holder.txtYday!!.text = content["yesCount"].toString()

                    if(currency.contains(".")){
                        val currencySplit = currency.split(".")
                        holder.title5!!.text = currencySplit[0].replace("\u20B9","")
                    }else{
                        holder.title5!!.text = currency.replace("\u20B9","")
                    }*/
                    holder.title5!!.text = Utils.getCommaSeparatedCount(content[title5] as Int)

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
