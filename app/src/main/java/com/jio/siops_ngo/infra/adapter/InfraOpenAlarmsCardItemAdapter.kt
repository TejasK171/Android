package com.jio.siops_ngo.adapter

import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.RelativeLayout
import androidx.core.text.bold
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.network.business.BaseCoroutines
import com.jio.jioinfra.utilities.Busicode
import com.jio.jioinfra.utilities.MyConstants
import com.jio.myjio.bean.CoroutinesResponse
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.T
import com.jio.siops_ngo.viewholder.InfraOpenAlarmsDetailsViewHolder
import kotlinx.coroutines.*
import java.lang.Exception

class InfraOpenAlarmsCardItemAdapter(private  val datalist:  ArrayList<Map<String, Any>>, private val activity: MainActivity?, val outLierId: String) : RecyclerView.Adapter<InfraOpenAlarmsDetailsViewHolder>() {

    var constantValue: SpannableStringBuilder? =null
    var sapId: String? = ""
    var ageingPopupWindow: PopupWindow? = null

    override fun onCreateViewHolder(parent: ViewGroup, viwType: Int): InfraOpenAlarmsDetailsViewHolder {
        var view: View?
        view = LayoutInflater.from(parent.context).inflate(R.layout.reasons_details_item, parent, false)
        return InfraOpenAlarmsDetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: InfraOpenAlarmsDetailsViewHolder, position: Int) {
        try {

            val content = datalist[position]
            if (content.containsKey("sapId")) {
                if (content.get("sapId") != null){
                    holder!!.txt_siteId!!.text = content["sapId"] as String
            }
            }
            if (content.containsKey("ageing")) {
                if (content.get("ageing") != null)
                    holder!!.txtageing!!.text = content["ageing"].toString()
            }

            if (content.containsKey("workOrder")) {
                if (content.get("workOrder") != null)
                    holder!!.txt_work_order!!.text = content["workOrder"].toString()
            }


            if (content.containsKey("jobOwner")) {
                if (content.get("jobOwner") != null) {

                    constantValue = SpannableStringBuilder()
                        .bold { append("Job Owner\n") }
                        .append(content["jobOwner"].toString())
                    holder!!.txt_job_owner!!.text = constantValue
//                    holder!!.txt_job_owner!!.text = content["jobOwner"].toString()
                }
            }


            if (content.containsKey("ageing")) {
                if (content.get("ageing") != null) {

                    var ageingVal: Double? = 0.0
                    ageingVal = content["ageing"] as Double
                    if (ageingVal > 0 && ageingVal < 2) {
                        holder!!.txtageing!!.setTextColor(activity!!.resources.getColor(R.color.hosp_dells_yellow))
                    } else if (ageingVal > 2 && ageingVal < 5) {
                        holder!!.txtageing!!.setTextColor(activity!!.resources.getColor(R.color.icu_dells_yellow))
                    } else if (ageingVal > 5) {
                        holder!!.txtageing!!.setTextColor(activity!!.resources.getColor(R.color.dead_dells_orange))
                    }
//                    content["ageing"].toString()
                    /*if (content["ageing"].toString().equals("0")) {
                    holder!!.txtageing!!.text = "Coming Soon"
                } else*/
                    holder!!.txtageing!!.text = ageingVal.toString()
                }

            }

            holder!!.title3!!.text = "ENERGY"
            holder.view_history_id!!.setOnClickListener {


                /*var adapter = InfraOpenAlarmsSubHistoryAdapter(activity as MainActivity?)
                holder!!.historyList!!.layoutManager = LinearLayoutManager(activity)
                holder!!.historyList!!.itemAnimator = DefaultItemAnimator()
                holder!!.historyList!!.adapter = adapter
                adapter!!.notifyDataSetChanged()

                holder.view_hide_id!!.visibility = View.VISIBLE
                holder.view_history_id!!.visibility = View.GONE
                holder.historyList!!.visibility = View.VISIBLE
                holder.ll!!.visibility = View.VISIBLE*/

            }


            holder.view_hide_id!!.setOnClickListener {
                if (holder.historyList!!.visibility == View.GONE) {
                    if (content.containsKey("sapId")) {
                        if (content.get("sapId") != null) {
                            sapId = content["sapId"] as String
                            fetchHistoryList(sapId!!, holder)
                        }
                    }
                }else{
                    holder.historyList!!.visibility = View.GONE
                    holder.txt_7_days_history!!.visibility = View.GONE
                    holder.ll!!.visibility = View.GONE
                    holder.view_hide_id!!.text = activity!!.resources.getString(R.string.view_history)
                }


            }


            showAgeingPopupWindow()
            holder.imgAgeingInfo!!.setOnClickListener {
                ageingPopupWindow!!.showAsDropDown(holder.imgAgeingInfo!!, -153, 0);

            }


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    fun fetchHistoryList(sapId: String, holder: InfraOpenAlarmsDetailsViewHolder) {
        (activity as MainActivity).showProgressBar()

        val requestBody = HashMap<String, Any>()

        requestBody["outlierId"] = outLierId!!
        requestBody["sapId"] = sapId!!

        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "userInfo"




        CoroutineScope(Dispatchers.IO).launch {

            var job = async {
                BaseCoroutines().fetchData(
                    requestBody,
                    Busicode.UtilityAlarmHistory,
                    activity
                )
            }
            withContext(Dispatchers.Main)
            {

                var response = job.await()
                if (activity != null) {
                    (activity!! as MainActivity).hideProgressBar()
                }


                if (response!!.responseEntity != null && response.status == MappActor.STATUS_OK) {
                    filterData(response, holder)
                    PreferenceUtility.addString(activity, MyConstants.NOTIFICATION_COUNT, "0")
                } else if (response!!.errorCode != null && response!!.errorCode.equals(MappActor.VERSION_SESSION_INVALID)){
                    (activity as MainActivity).showDialogForSessionExpired((activity as MainActivity).resources.getString(R.string.session_expired), (activity as MainActivity))
                } else if (response!!.errorMsg != null) {
                    T.show(activity, response!!.errorMsg!!, 0)
                } else {
                    T.show(activity, "Something went wrong!", 0)
                }
            }

        }
    }


    fun filterData(
        mCoroutinesResponse: CoroutinesResponse,
        holder: InfraOpenAlarmsDetailsViewHolder
    ) {

        try {
            val msg = mCoroutinesResponse.responseEntity as HashMap<String, Any>


            if (msg != null) {
                try {
                    if (msg["list"] != null) {
                        var datalist = msg["list"] as ArrayList<Map<String, Any>>

                        var adapter =
                            InfraOpenAlarmsHistoryAdapter(datalist!!, activity as MainActivity?)
                        holder!!.historyList!!.layoutManager = LinearLayoutManager(activity)
                        holder!!.historyList!!.itemAnimator = DefaultItemAnimator()
                        holder!!.historyList!!.adapter = adapter
                        adapter!!.notifyDataSetChanged()
                        holder.historyList!!.visibility = View.VISIBLE
                        holder.ll!!.visibility = View.VISIBLE
                        holder.txt_7_days_history!!.visibility = View.VISIBLE
                        holder.view_hide_id!!.text = activity!!.resources.getString(R.string.hide_history)
                    }


                } catch (e: Exception) {
                    MyExceptionHandler.handle(e)
                }
            }

        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
            T.show(activity, activity!!.resources.getString(R.string.something_went_wrong), 0)
        }
    }

    fun showAgeingPopupWindow() {
        var view = LayoutInflater.from(activity).inflate(R.layout.ageing_popup, null)
        ageingPopupWindow = PopupWindow(view, 500, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
    }

}