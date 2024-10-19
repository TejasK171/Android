package com.jio.siops_ngo.approvals.fragment


import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.jio.jioinfra.custom.TextViewLight
import com.jio.jioinfra.network.business.BaseCoroutines
import com.jio.jioinfra.utilities.Busicode
import com.jio.jioinfra.utilities.MyConstants
import com.jio.myjio.bean.CoroutinesResponse
import com.jio.siops_ngo.MainActivity
import com.jio.siops_ngo.R
import com.jio.siops_ngo.databinding.FragmentApprovalsPendingBinding
import com.jio.siops_ngo.network.MappActor
import com.jio.siops_ngo.ngo.adapter.ApprovalsDetailsListAdapter
import com.jio.siops_ngo.utilities.DialogUtils
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.T
import kotlinx.coroutines.*
import java.text.SimpleDateFormat


/**
 * A simple [Fragment] subclass.
 */
class ApprovalsDetailsFragment : Fragment(),
    ApprovalsDetailsListAdapter.ClickApprovalsConFormation, ApprovalsDetailsListAdapter.ClickLeaveApprovalsConFormation {


    var mBinding:FragmentApprovalsPendingBinding?=null
    var listData: ArrayList<Map<String, Any>>? = null
    var relatedTo: String? = ""
    var count: String? = ""
    var name: String? = ""
    var actionData: String? = ""
    var requestIDdata: String? = ""
    var actionDataName: String? = ""
    var commentStr: String? = ""
    var mClickApprovalsConFormation: ApprovalsDetailsListAdapter.ClickApprovalsConFormation?=null
    var position: Int = 0

    companion object {
        fun newInstance() = ApprovalsDetailsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding =DataBindingUtil.inflate(inflater, R.layout.fragment_approvals_pending, container, false)
        return mBinding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mBinding!!.header.visibility = View.GONE
        mBinding!!.cnstrntLHeader.visibility = View.VISIBLE
        mBinding!!.txtItemName.text = name!!

        mClickApprovalsConFormation=this
        fetchData()


        /*var adapter = ApprovalsDetailsListAdapter(activity as MainActivity?)
        mBinding!!.recyclerApprovalsList.layoutManager = LinearLayoutManager(activity)
        mBinding!!.recyclerApprovalsList.itemAnimator = DefaultItemAnimator()
        mBinding!!.recyclerApprovalsList!!.adapter = adapter
        adapter!!.notifyDataSetChanged()*/
    }

    fun fetchData() {
        (activity as MainActivity).showProgressBar()

        val requestBody = HashMap<String, Any>()
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "userInfo"
        requestBody["relatedTo"] = relatedTo!!
        CoroutineScope(Dispatchers.IO).launch {

            var job = async {
                BaseCoroutines().fetchData(
                    requestBody,
                    Busicode.ApprovalDetail,
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
                } else if (response!!.errorMsg != null) {
                    T.show(activity, response!!.errorMsg!!, 0)

                    if(mBinding!!.txtItemCount.text == "1"){
                        mBinding!!.txtItemCount.text = ""
                    }

                    mBinding!!.recyclerApprovalsList.visibility=View.GONE
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
                    mBinding!!.recyclerApprovalsList.visibility=View.VISIBLE
                    listData = msg["list"] as ArrayList<Map<String, Any>>
                    mBinding!!.txtItemCount.text = listData!!.size.toString()
//                    var adapter = ApprovalsDetailsListAdapter(activity as MainActivity?,listData!!,mClickApprovalsConFormation!!)
                    var adapter = ApprovalsDetailsListAdapter(activity as MainActivity?,listData!!,mClickApprovalsConFormation!!, this, relatedTo!!)
                    mBinding!!.recyclerApprovalsList.layoutManager = LinearLayoutManager(activity)
                    mBinding!!.recyclerApprovalsList.itemAnimator = DefaultItemAnimator()
                    mBinding!!.recyclerApprovalsList!!.adapter = adapter
                    adapter!!.notifyDataSetChanged()
                    if(position!=0){
                        mBinding!!.recyclerApprovalsList.scrollToPosition(position - 1)
                    }
                } catch (e: Exception) {
                    MyExceptionHandler.handle(e)
                }
            }
        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
            T.show(activity, getString(R.string.something_went_wrong), 0)
        }
    }

    fun setData(relatedTo:String, count:String, name:String) {
        this.relatedTo = relatedTo
        this.count = count
        this.name = name
    }

    override fun onClick(action: String,requestID:String,domanName:String, position: Int) {
        this.position = position

        if(action.equals("Approve")){
            actionData="A"
            actionDataName="Approve"
        }else{
            actionData="R"
            actionDataName="Reject"
        }

        if(relatedTo!!.equals(MyConstants.KEY_NOMINATEMANAGER)|| relatedTo!!.equals(MyConstants.KEY_TIMETRACK)|| relatedTo!!.equals(MyConstants.KEY_LEAVE_APPROVAL)){
            requestIDdata=domanName

            ApprovalsData()


        }else if(relatedTo!!.equals(MyConstants.KEY_TRANSFERTOMANAGER)){
            requestIDdata=requestID

            ApprovalsData()
        }else {

            requestIDdata = requestID



            val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(
                context
            )
            val inflater = activity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view: View = inflater.inflate(R.layout.accept_reject_dialog_main, null)
            alertDialogBuilder.setView(view)
            alertDialogBuilder.setCancelable(false)
            val dialog: AlertDialog = alertDialogBuilder.create()

            val txt_id: TextViewLight = view.findViewById(R.id.txt_id) as TextViewLight
           val edit_descreption: AppCompatEditText = view.findViewById(R.id.edit_descreption) as AppCompatEditText
           val btn_cancle: Button = view.findViewById(R.id.btn_cancle) as Button
           val btn_submit: Button = view.findViewById(R.id.btn_submit) as Button
            txt_id.text=actionDataName
            dialog.show()

            btn_cancle.setOnClickListener {


                dialog.dismiss()
            }
            btn_submit.setOnClickListener {


                if(edit_descreption.text!!.toString().equals("")){
                    T.show(activity, getString(R.string.approvals), 0)

                }else{
                    commentStr=edit_descreption.text.toString()
                    ApprovalsData()
                    dialog.dismiss()
                }
            }
        }
    }
    fun ApprovalsData() {
        (activity as MainActivity).showProgressBar()

        val requestBody = HashMap<String, Any>()
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "userInfo"
        requestBody["relatedTo"] = relatedTo!!
        requestBody["action"] = actionData!!
        if( relatedTo!!.equals(MyConstants.KEY_TRANSFERTOMANAGER)){
            requestBody["id"] = requestIDdata!!
        }
        if(relatedTo!!.equals(MyConstants.KEY_NOMINATEMANAGER)|| relatedTo!!.equals(MyConstants.KEY_TIMETRACK) || relatedTo!!.equals(MyConstants.KEY_LEAVE_APPROVAL)){
            requestBody["domainId"] = requestIDdata!!
        }else{
            requestBody["id"] = requestIDdata!!
            requestBody["comment"] = commentStr!!
        }

        CoroutineScope(Dispatchers.IO).launch {

            var job = async {
                BaseCoroutines().fetchData(
                    requestBody,
                    Busicode.ApprovalAction,
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
                    filterDataForApprovals(response)
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

    fun filterDataForApprovals(mCoroutinesResponse: CoroutinesResponse) {

        try {
            val msg = mCoroutinesResponse.responseEntity as HashMap<String, Any>
            if (msg != null) {
                try {
                    T.show(activity, msg["messageDetails"].toString(), 0)
                    fetchData()
                } catch (e: Exception) {
                    MyExceptionHandler.handle(e)
                }
            }
        } catch (e: Exception) {
            MyExceptionHandler.handle(e)
            T.show(activity, getString(R.string.something_went_wrong), 0)
        }
    }


    override fun onLeaveApproveRejectClick(
        action: String,
        domanName: String,
        startDate: String,
        endDate: String,
        position: Int
    ) {
        this.position = position
        var dialogText = ""
        if(action.equals("A")){
            dialogText = "Are you sure you want to approve this request?"
        }else{
            dialogText = "Are you sure you want to reject this request?"
        }

        DialogUtils.showYesNoDialogAutoDismiss(activity, dialogText, this.resources.getString(R.string.button_ok),
            this.resources.getString(R.string.cancel), object : DialogUtils.AutoDismissOnClickListener {
                override fun onYesClick() {
                    approveRejectAPi(action, domanName, startDate, endDate)
                }

                override fun onNoClick() {
                }
            })



        }


    fun approveRejectAPi(action:String, domanName:String, startDate: String, endDate: String){

        (activity as MainActivity).showProgressBar()

        val requestBody = HashMap<String, Any>()
        requestBody["userName"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
//        requestBody["userName"] = domanName
        requestBody["type"] = "userInfo"
        requestBody["relatedTo"] = relatedTo!!
        requestBody["action"] = action
        requestBody["domainId"] = domanName

        val apiDateformat = SimpleDateFormat("yyyy-MM-dd")
        val startDateFormat = SimpleDateFormat("dd MMM yyyy").parse(startDate)
        requestBody["startDate"] = apiDateformat.format(startDateFormat)
        Log.e("startDate ", apiDateformat.format(startDateFormat))


        val endDateFormat = SimpleDateFormat("dd MMM yyyy").parse(endDate)
        requestBody["endDate"] = apiDateformat.format(endDateFormat)
        Log.e("endDate ", apiDateformat.format(endDateFormat))



        CoroutineScope(Dispatchers.IO).launch {

            var job = async {
                BaseCoroutines().fetchData(
                    requestBody,
                    Busicode.ApprovalAction,
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
                    filterDataForApprovals(response)
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



}
