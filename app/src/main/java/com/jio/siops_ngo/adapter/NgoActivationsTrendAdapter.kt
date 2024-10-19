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
import com.jio.siops_ngo.infra.viewholder.NgoActivationsTrendItemHolder
import com.jio.siops_ngo.utilities.MyExceptionHandler
import java.text.SimpleDateFormat


class NgoActivationsTrendAdapter(
    private val appList: List<Map<String, Any>>,
    private val activity: MainActivity?
) : RecyclerView.Adapter<NgoActivationsTrendItemHolder>() {
    var boldTypeface: Typeface? = null
    var lightTypeface: Typeface? = null
    var todayCount: Int? = 0
    var ydayCount: Int? = 0
    override fun onCreateViewHolder(parent: ViewGroup, viwType: Int): NgoActivationsTrendItemHolder {
        var view: View?
        boldTypeface = ResourcesCompat.getFont(activity!!, R.font.jio_type_bold)
        lightTypeface = ResourcesCompat.getFont(activity!!, R.font.jio_type_light)
        view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ngo_trend_item, parent, false)

        return NgoActivationsTrendItemHolder(view)
    }

    override fun onBindViewHolder(holder: NgoActivationsTrendItemHolder, position: Int) {
        try {
            val content = appList[position]


            if (position == 0) {
                holder.title1!!.setTypeface(boldTypeface)
                holder.title2!!.setTypeface(boldTypeface)
                holder.title3!!.setTypeface(boldTypeface)
                holder.title4!!.setTypeface(boldTypeface)
                holder.title5!!.setTypeface(boldTypeface)
            } else {
                holder.title1!!.setTypeface(lightTypeface)
                holder.title2!!.setTypeface(lightTypeface)
                holder.title3!!.setTypeface(lightTypeface)
                holder.title4!!.setTypeface(lightTypeface)
                holder.title5!!.setTypeface(lightTypeface)
            }
            if (content.containsKey("ddate") && content["ddate"] != null) {
                if(position>0) {
                    var ddate = content["ddate"] as String
                    var splitDate = ddate.split(" ")
                    val date = SimpleDateFormat("yyyy-MM-dd").parse(splitDate[0])
                    val format = SimpleDateFormat("dd-MMM")
                    Log.e("date ", format.format(date))
//                println(date.time)
                    holder.title1!!.text = format.format(date)
                }else{
                    holder.title1!!.text = content["ddate"] as String
                }
            }

            if (content.containsKey("entered") && content["entered"] != null) {
                holder.title2!!.text = content["entered"].toString()
            }

            if (content.containsKey("activated") && content["activated"] != null) {

                holder.title3!!.text = content["activated"].toString()
            }
            if (content.containsKey("rejected") && content["rejected"] != null) {

                holder.title4!!.text = content["rejected"].toString()
            }
            if (content.containsKey("tvpending") && content["tvpending"] != null) {

                holder.title5!!.text = content["tvpending"].toString()
            }


        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
        }
    }

    override fun getItemCount(): Int {
        return appList.size
    }
}
