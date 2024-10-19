package com.jio.siops_ngo.adapter

import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
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
import android.widget.RelativeLayout


class InfraMaintenanceItemAdapter(
    private val datalist: ArrayList<Map<String, Any>>,
    private val activity: MainActivity?,
    val name: String?, val outLierId: String?
) : RecyclerView.Adapter<InfraOpenAlarmsDetailsViewHolder>() {

    var boolean: Boolean = true
    var constantValue: SpannableStringBuilder? = null
    var sapId: String? = ""
    var ageingPopupWindow: PopupWindow? = null
    var icPopupWindow: PopupWindow? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viwType: Int
    ): InfraOpenAlarmsDetailsViewHolder {
        var view: View?
        view =
            LayoutInflater.from(parent.context).inflate(R.layout.site_down_details, parent, false)
        return InfraOpenAlarmsDetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: InfraOpenAlarmsDetailsViewHolder, position: Int) {
        try {

            val content = datalist[position]
            if (content.containsKey("category")&& content["category"]!=null) {
                holder.title3!!.text = content["category"] as String
            }

            if (content.containsKey("sapId")) {
                if (content.get("sapId") != null) {
                    sapId = content["sapId"] as String
                    holder!!.txt_siteId!!.text = content["sapId"] as String
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

            if (content.containsKey("impactedCustomers")) {
                if (content.get("impactedCustomers") != null)
                    holder!!.count2!!.text = content["impactedCustomers"].toString()
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
                    holder!!.txt_job_owner1!!.text = constantValue
                }


                //   holder!!.txt_job_owner1!!.text =constantValue+"\n"+ content["jobOwner"].toString()

            }
            holder.view_hide_id!!.setOnClickListener {
                if (holder.historyList!!.visibility == View.GONE) {
                    if (content.containsKey("sapId")) {
                        if (content.get("sapId") != null) {
                            sapId = content["sapId"] as String
                            fetchHistoryList(sapId!!, holder)
                        }
                    }
                } else {
                    holder.historyList!!.visibility = View.GONE
                    holder.txt_7_days_history!!.visibility = View.GONE
                    holder.ll!!.visibility = View.GONE
                    holder.view_hide_id!!.text =
                        activity!!.resources.getString(R.string.view_history)
                }


            }


            showAgeingPopupWindow()
            holder.imgAgeingInfo!!.setOnClickListener {
                /*var popupMenu: PopupMenu? = null;
                popupMenu = PopupMenu(activity!!,holder.imgAgeingInfo!!)
                popupMenu.menuInflater.inflate(R.menu.ageing_popup_menu,popupMenu.menu)


                try {
                    val popup = PopupMenu::class.java.getDeclaredField("mPopup")
                    popup.isAccessible = true
                    val menu = popup.get(popupMenu)
                    menu.javaClass
                        .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                        .invoke(menu, true)
                } catch (e: Exception){
                    e.printStackTrace()
                } finally {
                    popupMenu.show()
                }



                popupMenu.show()*/

                ageingPopupWindow!!.showAsDropDown(holder.imgAgeingInfo!!, -153, 0);

                /*PopupMenu popup = PopupMenu(activity, holder.imgAgeingInfo!!);
                popup.setOnMenuItemClickListener(activity);
                popup.inflate(R.menu.ageing_popup_menu);
                popup.show();*/
            }

            showIcPopupWindow()
            holder.imgIcInfo!!.setOnClickListener {
                icPopupWindow!!.showAsDropDown(holder.imgIcInfo!!, -153, 0);
            }

            /*holder.view_hide_id!!.setOnClickListener {

                //                holder.view_hide_id!!.visibility = View.GONE
                holder.view_history_id!!.visibility = View.VISIBLE
//                holder.historyList!!.visibility = View.GONE
                holder.ll!!.visibility = View.GONE

                fetchHistoryList(sapId!!, holder)


            }*/
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
                    Busicode.SiteDownHistory,
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
                            InfraSitesDownHistoryAdapter(datalist!!, activity as MainActivity?)
                        holder!!.historyList!!.layoutManager = LinearLayoutManager(activity)
                        holder!!.historyList!!.itemAnimator = DefaultItemAnimator()
                        holder!!.historyList!!.adapter = adapter
                        adapter!!.notifyDataSetChanged()
                        holder.historyList!!.visibility = View.VISIBLE
                        holder.ll!!.visibility = View.VISIBLE
                        holder.txt_7_days_history!!.visibility = View.VISIBLE
                        holder.view_hide_id!!.text =
                            activity!!.resources.getString(R.string.hide_history)
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

    /*fun showStatusPopup(context: Activity, p: Point) {

        // Inflate the popup_layout.xml
//        val viewGroup = context.findViewById<View>(R.id.llStatusChangePopup) as LinearLayout
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = layoutInflater.inflate(R.layout.ageing_popup, null)

        // Creating the PopupWindow
        var changeStatusPopUp = PopupWindow(context)
        changeStatusPopUp.setContentView(layout)
        changeStatusPopUp.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT)
        changeStatusPopUp.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT)
        changeStatusPopUp.setFocusable(true)

        // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.
        val OFFSET_X = -20
        val OFFSET_Y = 50

        //Clear the default translucent background
        changeStatusPopUp.setBackgroundDrawable(BitmapDrawable())

        // Displaying the popup at the specified location, + offsets.
        changeStatusPopUp.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y)
    }*/


    fun showAgeingPopupWindow() {
        var view = LayoutInflater.from(activity).inflate(R.layout.ageing_popup, null)
        ageingPopupWindow = PopupWindow(view, 500, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
    }

    fun showIcPopupWindow() {
        var view = LayoutInflater.from(activity).inflate(R.layout.ic_popup, null)
        icPopupWindow = PopupWindow(view, 500, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
    }
}