package com.jio.siops_ngo.adapter


import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.infra.viewholder.NgoMacdTrendItemHolder
import com.jio.siops_ngo.utilities.MyExceptionHandler
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


class NgoMacdTrendAdapter(
    private val appList: List<Map<String, Any>>,
    private val activity: MainActivity?
) : RecyclerView.Adapter<NgoMacdTrendItemHolder>() {
    var boldTypeface: Typeface? = null
    var lightTypeface: Typeface? = null
    var todayCount: Int? = 0
    var ydayCount: Int? = 0
    override fun onCreateViewHolder(parent: ViewGroup, viwType: Int): NgoMacdTrendItemHolder {
        var view: View?
        boldTypeface = ResourcesCompat.getFont(activity!!, R.font.jio_type_bold)
        lightTypeface = ResourcesCompat.getFont(activity!!, R.font.jio_type_light)
        view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ngo_trend_item, parent, false)

        return NgoMacdTrendItemHolder(view)
    }

    override fun onBindViewHolder(holder: NgoMacdTrendItemHolder, position: Int) {
        try {
            val content = appList[position]


            if (position == 0) {
                holder.lnrHeader!!.visibility = View.VISIBLE
                holder.lnrTrendValues!!.visibility = View.GONE
            } else {
                holder.lnrHeader!!.visibility = View.GONE
                holder.lnrTrendValues!!.visibility = View.VISIBLE
            }
            if (content.containsKey("ddate") && content["ddate"] != null) {
                if(position!=0) {
                    var ddate = content["ddate"] as String
                    var splitDate = ddate.split(" ")
                    val date = SimpleDateFormat("dd-MM-yyyy").parse(splitDate[0])
                    val format = SimpleDateFormat("dd-MMM")
                    Log.e("date ", format.format(date))
                    holder.title1!!.text = format.format(date)
                }else{
                    holder.header1!!.text = content["ddate"] as String
                }
            }

            if (content.containsKey("simChangeCount") && content["simChangeCount"] != null) {
                if(position!=0) {
                    holder.title2!!.text = content["simChangeCount"].toString()
                    val format = NumberFormat.getCurrencyInstance(Locale("hi","IN"))
                    val currency = format.format(content["simChangeCount"] as Int)
                    if(currency.contains(".")){
                        val currencySplit = currency.split(".")
                        holder.title2!!.text = currencySplit[0].replace("\u20B9","")
                    }else{
                        holder.title2!!.text = currency.replace("\u20B9","")
                    }
                }else{
                    holder.header2!!.text = content["simChangeCount"] as String
                }
            }

            if (content.containsKey("irWatchCount") && content["irWatchCount"] != null) {
                if(position!=0) {
                    holder.title3!!.text = content["irWatchCount"].toString()
                    val format = NumberFormat.getCurrencyInstance(Locale("hi","IN"))
                    val currency = format.format(content["irWatchCount"] as Int)
                    if(currency.contains(".")){
                        val currencySplit = currency.split(".")
                        holder.title3!!.text = currencySplit[0].replace("\u20B9","")
                    }else{
                        holder.title3!!.text = currency.replace("\u20B9","")
                    }
                }else{
                    holder.header3!!.text = content["irWatchCount"].toString()
                }
            }
            if (content.containsKey("appleIWatchCount") && content["appleIWatchCount"] != null) {
                if(position!=0) {
                    holder.title4!!.text = content["appleIWatchCount"].toString()
                    val format = NumberFormat.getCurrencyInstance(Locale("hi","IN"))
                    val currency = format.format(content["appleIWatchCount"] as Int)
                    if(currency.contains(".")){
                        val currencySplit = currency.split(".")
                        holder.title4!!.text = currencySplit[0].replace("\u20B9","")
                    }else{
                        holder.title4!!.text = currency.replace("\u20B9","")
                    }
                }else{
                    holder.header4!!.text = content["appleIWatchCount"].toString()
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
