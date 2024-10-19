package com.jio.siops_ngo.ngo.adapter


import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.bean.CommonBean
import com.jio.jioinfra.utilities.MyConstants
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.approvals.fragment.TimeTrackingExclusionFragment
import com.jio.siops_ngo.approvals.viewholder.ApprovalsDetailsItemHolder
import com.jio.siops_ngo.utilities.MyExceptionHandler


class ApprovalsDetailsListAdapter(
    private val activity: MainActivity?,
    private val listData: ArrayList<Map<String, Any>>,
    private val mClickApprovalsConFormation: ClickApprovalsConFormation,
    private val mClickLeaveApprovalsConFormation: ClickLeaveApprovalsConFormation,
    val key:String
) : RecyclerView.Adapter<ApprovalsDetailsItemHolder>() {
    var map: HashMap<Int, Int>? = hashMapOf()
    var grpMap: HashMap<Int, Int>? = hashMapOf()
    var mediumTypeface: Typeface? = null
    var lightTypeface: Typeface? = null

    interface ClickApprovalsConFormation {
        fun onClick(action: String, requestID: String, domanName: String, position: Int)
    }

    interface ClickLeaveApprovalsConFormation {
        fun onLeaveApproveRejectClick(action: String, domanName: String, startDate: String, endDate:String, position:Int)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viwType: Int
    ): ApprovalsDetailsItemHolder {
        var view: View?

        view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ngo_approval_click_item, parent, false)
        mediumTypeface = ResourcesCompat.getFont(activity!!, R.font.jio_type_medium)
        lightTypeface = ResourcesCompat.getFont(activity!!, R.font.jio_type_light)

        return ApprovalsDetailsItemHolder(view)
    }

    override fun onBindViewHolder(holder: ApprovalsDetailsItemHolder, position: Int) {
        try {
            val content = listData[position]

            var justificationText: String? = ""

            if (content.containsKey("UserName") && content["UserName"] != null) {
                holder.txtName!!.text = content["UserName"] as String
            }
            if (content.containsKey("RequestID") && content["RequestID"] != null) {
                holder.txtRequestIdTitle!!.text =
                    "Request ID " + "\n" + content["RequestID"].toString()
            }

            if (content.containsKey("StartDate") && content["StartDate"] != null) {
                holder.txtStartDate!!.text = content["StartDate"].toString()
            } else {
                holder.txtStartDate!!.text = "-"
            }
            if (content.containsKey("ToDate") && content["ToDate"] != null) {
                holder.txtEndDate!!.text = content["ToDate"].toString()
            } else {
                holder.txtEndDate!!.text = "-"
            }
            if (content.containsKey("Justification") && content["Justification"] != null) {
                holder.txtJustification!!.text = content["Justification"].toString()
                justificationText = content["Justification"].toString()
                holder.txtJustificationTitle!!.text = activity!!.resources.getString(R.string.reason)
            }
            if (content.containsKey("Reason") && content["Reason"] != null) {
                holder.txtJustification!!.text = content["Reason"].toString()
                justificationText = content["Reason"].toString()
            }

            if (map!!.containsKey(position)) {
                holder.txtJustificationDetails!!.visibility = View.VISIBLE
                holder.txtJustificationDetails!!.text = justificationText!!
                holder.txtJustificationViewDetails!!.text =
                    activity!!.resources.getString(R.string.hidedetails)
            } else {
                holder.txtJustificationDetails!!.visibility = View.GONE
                holder.txtJustificationViewDetails!!.text =
                    activity!!.resources.getString(R.string.viewdetails)
            }
            if(key.equals(MyConstants.KEY_TRANSFERTOMANAGER)){
                holder.cnstrntLGroup!!.visibility = View.GONE
                holder.txtStartDateTitle!!.text = activity!!.resources.getString(R.string.transfer_date_from)
                holder.txtEndDateTitle!!.text = activity!!.resources.getString(R.string.transfer_from)
                holder.txtJustificationTitle!!.text = activity!!.resources.getString(R.string.reason)
                if (content.containsKey("TransferDate") && content["TransferDate"] != null) {
                    holder.txtStartDate!!.text = content["TransferDate"].toString()
                }
                if (content.containsKey("FromManager") && content["FromManager"] != null) {
                    holder.txtEndDate!!.text = content["FromManager"].toString()
                }
                if (content.containsKey("Reason") && content["Reason"] != null) {
                    holder.txtJustification!!.text = content["Reason"].toString()
                    justificationText = content["Reason"].toString()
                }
            }
            if(key.equals(MyConstants.KEY_TIMETRACK) || key.equals(MyConstants.KEY_NOMINATEMANAGER) ){
                holder.cnstrntLGroup!!.visibility = View.GONE

                holder.txtStartDateTitle!!.text = activity!!.resources.getString(R.string.initiated_by)
                holder.txtEndDateTitle!!.text = activity!!.resources.getString(R.string.application)
                holder.txtJustificationTitle!!.text = activity!!.resources.getString(R.string.job_role)
                if (content.containsKey("InitiatedBy") && content["InitiatedBy"] != null) {
                    holder.txtStartDate!!.text = content["InitiatedBy"].toString()
                }
                if (content.containsKey("Application") && content["Application"] != null) {
                    holder.txtEndDate!!.text = content["Application"].toString()
                }
                if (content.containsKey("JobRole") && content["JobRole"] != null) {
                    holder.txtJustification!!.text = content["JobRole"].toString()
                    justificationText = content["JobRole"].toString()
                }
                holder.txtJustificationViewDetails!!.visibility = View.GONE
            }
            if (content.containsKey("Group") && content["Group"] != null) {
                var groupList = content["Group"] as ArrayList<Map<String, Any>>
                holder.txtGroupViewDetails!!.setTypeface(mediumTypeface)

                var adapter = ApprovalsGroupDetailsAdapter(groupList)
                val gridLayoutManagerUsefulLinks = GridLayoutManager(activity, 3)
                holder.rvGroup!!.layoutManager =
                    gridLayoutManagerUsefulLinks!!
                holder.rvGroup!!.itemAnimator =
                    DefaultItemAnimator()

                holder.rvGroup!!!!.adapter = adapter

            }


            if(grpMap!!.containsKey(position)){
                holder.rvGroup!!.visibility = View.VISIBLE
                holder.txtGroupViewDetails!!.text =
                    activity!!.resources.getString(R.string.hidedetails)

            }else{

                holder.rvGroup!!.visibility = View.GONE
                holder.txtGroupViewDetails!!.text =
                    activity!!.resources.getString(R.string.viewdetails)

            }

            if (content.containsKey("LeaveType") && content["LeaveType"] != null) {
                holder.txtGroupTitle!!.text = activity!!.resources.getString(R.string.leave_type)
                holder.txtGroupViewDetails!!.text = content["LeaveType"].toString()
                holder.txtGroupViewDetails!!.setTextColor(activity!!.resources.getColor(R.color.dcb_grey_txt_color))
                holder.txtGroupViewDetails!!.setTypeface(lightTypeface)
            }


            if (key.equals(MyConstants.KEY_TIMETRACK)) {
                var timeTrackingExclusionFragment = TimeTrackingExclusionFragment.newInstance()
                var platform: String = ""
                var jobRole: String = ""

                if (content.containsKey("Platform") && content["Platform"] != null) {
                    platform = content["Platform"].toString()
                }
                if (content.containsKey("JobRole") && content["JobRole"] != null) {
                    jobRole = content["JobRole"].toString()

                }
                holder.txtViewSimilar!!.visibility = View.VISIBLE

                holder.txtViewSimilar!!.setOnClickListener {

                    val commonBean = CommonBean()
                    timeTrackingExclusionFragment.setData(jobRole, platform)
                    (activity)!!.openFragments(
                        timeTrackingExclusionFragment,
                        commonBean,
                        true,
                        true
                    )


                }



            }else{
                holder.txtViewSimilar!!.visibility = View.VISIBLE
            }


            holder.txtGroupViewDetails!!.setOnClickListener {
                if (holder.rvGroup!!.visibility == View.VISIBLE) {
                    if (grpMap!!.containsKey(position)) {
                        grpMap!!.remove(position)
                    }
                    holder.rvGroup!!.visibility = View.GONE
                    holder.txtGroupViewDetails!!.text =
                        activity!!.resources.getString(R.string.viewdetails)
                } else {
                    grpMap!!.put(position, position)
                    holder.rvGroup!!.visibility = View.VISIBLE
                    holder.txtGroupViewDetails!!.text =
                        activity!!.resources.getString(R.string.hidedetails)
                }

            }

            holder.txtJustificationViewDetails!!.setOnClickListener {
                if (holder.txtJustificationDetails!!.visibility == View.VISIBLE) {
                    if (map!!.containsKey(position)) {
                        map!!.remove(position)
                    }
                    holder.txtJustificationDetails!!.visibility = View.GONE
                    holder.txtJustificationViewDetails!!.text =
                        activity!!.resources.getString(R.string.viewdetails)
                } else {
                    map!!.put(position, position)
                    holder.txtJustificationDetails!!.visibility = View.VISIBLE
                    holder.txtJustificationDetails!!.text = justificationText!!
                    holder.txtJustificationViewDetails!!.text =
                        activity!!.resources.getString(R.string.hidedetails)
                }

            }
            holder.cardApprove!!.setOnClickListener {
                if(key.equals(MyConstants.KEY_LEAVE_APPROVAL)){
                    mClickLeaveApprovalsConFormation.onLeaveApproveRejectClick("A",content["UserName"].toString(),
                        content["StartDate"].toString(), content["ToDate"].toString(), position)
                }else {
                    mClickApprovalsConFormation.onClick(
                        "Approve",
                        content["ID"].toString(),
                        content["UserName"] as String,
                        position
                    )
                }
            }


            holder.cardReject!!.setOnClickListener {
                if(key.equals(MyConstants.KEY_LEAVE_APPROVAL)){
                    mClickLeaveApprovalsConFormation.onLeaveApproveRejectClick("R",content["UserName"].toString(),
                        content["StartDate"].toString(), content["ToDate"].toString(), position)
                }else {
                    mClickApprovalsConFormation.onClick(
                        "Reject",
                        content["ID"].toString(),
                        content["UserName"] as String,
                        position
                    )
                }

            }


        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}
