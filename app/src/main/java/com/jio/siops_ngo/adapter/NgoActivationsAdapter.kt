package com.jio.siops_ngo.adapter


import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.bean.CommonBean
import com.jio.jioinfra.utilities.Busicode
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.businessBoard.fragment.OrderActivationJourneyFragment
import com.jio.siops_ngo.businessBoard.fragment.RechageJournaryFragment
import com.jio.siops_ngo.infra.viewholder.NgoActivationsItemHolder
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.Utils


class NgoActivationsAdapter(
    private val appList: List<Map<String, Any>>,
    private val activity: MainActivity?,
    private val busicode: String
) : RecyclerView.Adapter<NgoActivationsItemHolder>() {
    var boldTypeface: Typeface? = null
    var lightTypeface: Typeface? = null
    var todayCount: Int? = 0
    var ydayCount: Int? = 0
    override fun onCreateViewHolder(parent: ViewGroup, viwType: Int): NgoActivationsItemHolder {
        var view: View?
        boldTypeface = ResourcesCompat.getFont(activity!!, R.font.jio_type_bold)
        lightTypeface = ResourcesCompat.getFont(activity!!, R.font.jio_type_light)
        view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ngo_activations_item, parent, false)

        return NgoActivationsItemHolder(view)
    }

    override fun onBindViewHolder(holder: NgoActivationsItemHolder, position: Int) {
        try {
            val content = appList[position]


            holder.ll!!.setOnClickListener {


                if(busicode.equals(Busicode.NGOOrderActivation)){
                    val commonBean = CommonBean()

                    var orderActivationJourneyFragment = OrderActivationJourneyFragment.newInstance()
                    //  infraMaintenanceFragment.setData(commonBean,featureId,featureName,featureCount)
                    (activity as MainActivity).openFragments(orderActivationJourneyFragment, commonBean, true, true)
                }else{
                    val commonBean = CommonBean()
                    var rechageJournaryFragment = RechageJournaryFragment.newInstance()
                    //  infraMaintenanceFragment.setData(commonBean,featureId,featureName,featureCount)
                    (activity as MainActivity).openFragments(rechageJournaryFragment, commonBean, true, true)
                }

            }


            if (position == 0) {
                holder.txtStage!!.setTypeface(boldTypeface)
                holder.txtToday!!.setTypeface(boldTypeface)
                holder.txtYday!!.setTypeface(boldTypeface)
            } else {
                holder.txtStage!!.setTypeface(lightTypeface)
                holder.txtToday!!.setTypeface(lightTypeface)
                holder.txtYday!!.setTypeface(lightTypeface)
            }
            if (content.containsKey("stage") && content["stage"] != null) {
                holder.txtStage!!.text = content["stage"] as String
            }

            if (content.containsKey("todayCount") && content["todayCount"] != null) {
                if (position > 0) {
                    todayCount = content["todayCount"] as Int
                    holder.txtToday!!.text = content["todayCount"].toString()
                    holder.txtToday!!.text =
                        Utils.getCommaSeparatedCount(content["todayCount"] as Int)
                }else{
                    holder.txtToday!!.text = content["todayCount"].toString()
                }
            }

            if (content.containsKey("yesCount") && content["yesCount"] != null) {
                if (position > 0) {
                    ydayCount = content["yesCount"] as Int
                    holder.txtYday!!.text = content["yesCount"].toString()


                    holder.txtYday!!.text = Utils.getCommaSeparatedCount(content["yesCount"] as Int)

                    if (content.containsKey("stage") && content["stage"] != null) {
                        if (content["stage"].toString().equals("Pending")) {
                            holder.txtYday!!.text = ""
                        }
                    }
                }else{
                    holder.txtYday!!.text = content["yesCount"].toString()
                }


            }


            if (position > 0) {
                /*if (ydayCount!! > 0) {
//                    val difference = ydayCount!!.toFloat() - todayCount!!.toFloat()
                    val actualDifference = todayCount!!.toFloat() - ydayCount!!.toFloat()
//                    if(difference>0) {
                        holder.txtYdayDiff!!.text = "("+Math.abs(actualDifference.roundToInt()).toString()+")"
//                    }
                    var percentage =
                        (actualDifference / ydayCount!!.toFloat()) * 100
                    if (percentage.roundToInt() > 0) {

                        holder.imgStatus!!.setImageDrawable(activity!!.resources.getDrawable(R.drawable.ic_arrow_up_green))
                    } else {
                        holder.imgStatus!!.setImageDrawable(activity!!.resources.getDrawable(R.drawable.ic_arrow_down_red))
                    }
                    holder.txtPercentage!!.text = Math.abs(percentage.roundToInt()).toString()+ "  %"
                    Log.e("p1Percentage ", "" + percentage.roundToInt())
                }*/

                if (content.containsKey("yesTotal") && content["yesTotal"] != null) {

                    holder.txtYdayDiff!!.text = "("+content["yesTotal"].toString()+")"

                    holder.txtYdayDiff!!.text = "("+ Utils.getCommaSeparatedCount(content["yesTotal"] as Int)+")"

                        if (content.containsKey("stage") && content["stage"] != null) {
                        if(content["stage"].toString().equals("Pending")){
                            holder.txtYdayDiff!!.text =""
                        }
                    }
                }

                if (content.containsKey("perc") && content["perc"] != null) {
                    holder.txtPercentage!!.text = content["perc"].toString()+"%"

                }

                if(content.containsKey("status") && content["status"] != null){

                    if(content["status"] !="NA"){

                        var status = content["status"] as String
                        if (content.containsKey("color") && content["color"] != null) {
                            var color = content["color"] as Int
                            if (status.equals("DOWN") && color == 0){
                                holder.imgStatus!!.setImageDrawable(activity!!.resources.getDrawable(R.drawable.ic_arrow_down_green))
                            }else if (status.equals("UP") && color == 0){
                                holder.imgStatus!!.setImageDrawable(activity!!.resources.getDrawable(R.drawable.ic_arrow_up_green))
                            }else if(status.equals("UP") && color == 1){
                                holder.imgStatus!!.setImageDrawable(activity!!.resources.getDrawable(R.drawable.ic_arrow_up_red))
                            }else if(status.equals("DOWN") && color == 1){
                                holder.imgStatus!!.setImageDrawable(activity!!.resources.getDrawable(R.drawable.ic_arrow_down_red))
                            }
                        }

                    }else{
                        holder.imgStatus!!.visibility = View.GONE
                        holder.txtPercentage!!.text =""
                    }

                    if (content.containsKey("stage") && content["stage"] != null) {
                        if(content["stage"].toString().equals("Pending")){
                            holder.imgStatus!!.visibility = View.GONE
                            holder.txtPercentage!!.text =""
                        }
                    }

                }
                /*if (content.containsKey("color") && content["color"] != null) {

                    var color = content["color"] as Int
                    if(color == 0){
                        holder.imgStatus!!.setImageDrawable(activity!!.resources.getDrawable(R.drawable.ic_arrow_up_green))
                    }else{
                        holder.imgStatus!!.setImageDrawable(activity!!.resources.getDrawable(R.drawable.ic_arrow_down_red))
                    }

                }*/



            }


        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
        }
    }

    override fun getItemCount(): Int {
        return appList.size
    }
}
