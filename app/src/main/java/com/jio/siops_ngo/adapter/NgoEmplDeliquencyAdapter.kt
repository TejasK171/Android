package com.jio.siops_ngo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.jio.siops_ngo.viewholder.NgoEmpDeliquencyItemViewHolder
import kotlinx.coroutines.*
import java.lang.Exception

class NgoEmplDeliquencyAdapter(
    private val dataList: List<Map<String, Any>>,
    private val activity: MainActivity?
) : RecyclerView.Adapter<NgoEmpDeliquencyItemViewHolder>() {

    var boolean: Boolean = true

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viwType: Int
    ): NgoEmpDeliquencyItemViewHolder {
        var view: View?


        view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ngo_deliquency_emp_details, parent, false)

        return NgoEmpDeliquencyItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: NgoEmpDeliquencyItemViewHolder, position: Int) {
        try {

            val content = dataList[position]

            val datesArr = content["dates"] as List<Map<String, Any>>

            if (datesArr[0].containsKey("dates")) {
                holder!!.txtDate1!!.text = datesArr[0]["dates"] as String
            }
            if (datesArr[1].containsKey("dates")) {
                holder!!.txtDate2!!.text = datesArr[1]["dates"] as String
            }
            if (datesArr[2].containsKey("dates")) {
                holder!!.txtDate3!!.text = datesArr[2]["dates"] as String
            }
            if (datesArr[3].containsKey("dates")) {
                holder!!.txtDate4!!.text = datesArr[3]["dates"] as String
            }
            if (datesArr[4].containsKey("dates")) {
                holder!!.txtDate5!!.text = datesArr[4]["dates"] as String
            }
            if (datesArr[5].containsKey("dates")) {
                holder!!.txtDate6!!.text = datesArr[5]["dates"] as String
            }
            if (datesArr[6].containsKey("dates")) {
                holder!!.txtDate7!!.text = datesArr[6]["dates"] as String
            }


            if (datesArr[0].containsKey("hrs")) {
                holder!!.txtHrs1!!.text = datesArr[0]["hrs"] as String + " hrs"
            }
            if (datesArr[1].containsKey("hrs")) {
                holder!!.txtHrs2!!.text = datesArr[1]["hrs"] as String + " hrs"
            }
            if (datesArr[2].containsKey("hrs")) {
                holder!!.txtHrs3!!.text = datesArr[2]["hrs"] as String + " hrs"
            }
            if (datesArr[3].containsKey("hrs")) {
                holder!!.txtHrs4!!.text = datesArr[3]["hrs"] as String + " hrs"
            }
            if (datesArr[4].containsKey("hrs")) {
                holder!!.txtHrs5!!.text = datesArr[4]["hrs"] as String + " hrs"
            }
            if (datesArr[5].containsKey("hrs")) {
                holder!!.txtHrs6!!.text = datesArr[5]["hrs"] as String + " hrs"
            }
            if (datesArr[6].containsKey("hrs")) {
                holder!!.txtHrs6!!.text = datesArr[6]["hrs"] as String + " hrs"
            }

            if (content.containsKey("domainId")) {

                holder!!.txtDomainId!!.text = content["domainId"] as String
            }


            var domainId = content["domainId"] as String
            var count = content["delinquentCount"] as Int
            holder.sendReminderRelL!!.setOnClickListener { fetchEmplDeliquentData(domainId, count) }


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun fetchEmplDeliquentData(domainId: String, count: Int) {
        (activity as MainActivity).showProgressBar()

        val requestBody = HashMap<String, Any>()
        requestBody["domainId"] = domainId
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["sendReminderCode"] = "TIME_DELINQUENCY"
        requestBody["type"] = "message"
        requestBody["count"] = count
        CoroutineScope(Dispatchers.IO).launch {

            var job = async {
                BaseCoroutines().fetchData(
                    requestBody,
                    Busicode.SendReminder,
                    activity as MainActivity
                )
            }
            withContext(Dispatchers.Main)
            {

                var response = job.await()
                if (activity != null) {
                    (activity as MainActivity).hideProgressBar()
                }

                if (response!!.responseEntity != null && response.status == MappActor.STATUS_OK) {
                    filterData(response)
                } else if (response!!.errorCode != null && response!!.errorCode.equals(MappActor.VERSION_SESSION_INVALID)){
                    (activity as MainActivity).showDialogForSessionExpired((activity as MainActivity).resources.getString(R.string.session_expired), (activity as MainActivity))
                }else if (response!!.errorMsg != null) {
                    T.show(activity, response!!.errorMsg!!, 0)
                } else {
                    T.show(activity, "Something went wrong!", 0)
                }
            }

        }
    }


    fun filterData(mCoroutinesResponse: CoroutinesResponse) {

        try {
            val msg = mCoroutinesResponse.responseEntity as HashMap<String, Any>


            if (msg != null) {
                try {

                    if (msg.containsKey("message")) {
                        if (msg.containsKey("apkInstallationStatus")) {
                            if (msg["apkInstallationStatus"].toString().equals("0")) {
                                T.showErrorMsg(activity, msg["message"] as String, 0)
                            } else {
                                T.show(activity, msg["message"] as String, 0)
                            }
                        } else
                            T.show(activity, msg["message"] as String, 0)
                    }

                } catch (e: Exception) {
                    MyExceptionHandler.handle(e)
                }
            }


        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
            T.show(activity, activity!!.getString(R.string.something_went_wrong), 0)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


}