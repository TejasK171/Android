package com.jio.siops_ngo.approvals.fragment


import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.DataBindingUtil
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
import com.jio.siops_ngo.ngo.adapter.ApprovalsChangeManagementAdapter
import com.jio.siops_ngo.ngo.adapter.ApprovalsProblemManagementAdapter
import com.jio.siops_ngo.utilities.MyExceptionHandler
import com.jio.siops_ngo.utilities.PreferenceUtility
import com.jio.siops_ngo.utilities.T
import kotlinx.coroutines.*

/**
 * A simple [Fragment] subclass.
 */
class ApprovalsChangeOrProblemManagementFragment : Fragment(), ApprovalsProblemManagementAdapter.OnProblemMgmntOptionSelectedListener,
ApprovalsChangeManagementAdapter.OnChangeMgmntOptionSelectedListener{
    var mBinding: FragmentApprovalsPendingBinding? = null
    var listData: ArrayList<Map<String, Any>>? = null
    var isChangeManagement = false
    var optionSelected: String? = ""
    var commentStr: String? = ""


    companion object {
        fun newInstance() = ApprovalsChangeOrProblemManagementFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_approvals_pending, container, false)
        return mBinding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mBinding!!.header.visibility = View.GONE
        fetchData()
    }

    fun fetchData() {
        (activity as MainActivity).showProgressBar()

        val requestBody = HashMap<String, Any>()
        requestBody["domainId"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["type"] = "hpsmRequest"
        if (isChangeManagement)
            requestBody["managementName"] = "change"
        else
            requestBody["managementName"] = "problem"
        CoroutineScope(Dispatchers.IO).launch {

            var job = async {
                BaseCoroutines().fetchData(
                    requestBody,
                    Busicode.HPSMInboxList,
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
                } else if (response!!.errorCode != null && response!!.errorCode.equals(MappActor.VERSION_SESSION_INVALID)) {
                    (activity as MainActivity).showDialogForSessionExpired(
                        (activity as MainActivity).resources.getString(
                            R.string.session_expired
                        ), (activity as MainActivity)
                    )
                } else if (response!!.errorMsg != null) {
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
                    listData = msg["dataList"] as ArrayList<Map<String, Any>>
                    mBinding!!.recyclerApprovalsList.layoutManager = LinearLayoutManager(activity)
                    mBinding!!.recyclerApprovalsList.itemAnimator = DefaultItemAnimator()
                    if(isChangeManagement) {
                        var adapter =
                            ApprovalsChangeManagementAdapter(activity as MainActivity?, listData!!, this)
                        mBinding!!.recyclerApprovalsList!!.adapter = adapter
                        adapter!!.notifyDataSetChanged()
                    }else{
                        var adapter =
                            ApprovalsProblemManagementAdapter(activity as MainActivity?, listData!!, this)
                        mBinding!!.recyclerApprovalsList!!.adapter = adapter
                        adapter!!.notifyDataSetChanged()
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

    fun setData(isChangeManagementSelected: Boolean) {
        isChangeManagement = isChangeManagementSelected
    }

    override fun onProblemManagementOptionSelected(data: Any, selectedOption: String) {
        optionSelected = selectedOption
        showDialog(data, false)
    }

    override fun onChangeMgmntOptionSelected(data: Any, selectedOption: String) {
        optionSelected = selectedOption
        showDialog(data, true)
    }


    fun showDialog(data: Any, isChangeMgmnt:Boolean){
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
        txt_id.text=optionSelected
        dialog.show()

        btn_cancle.setOnClickListener {
            dialog.dismiss()
        }
        btn_submit.setOnClickListener {


            if(edit_descreption.text!!.toString().equals("")){
                T.show(activity, getString(R.string.approvals), 0)

            }else{
                commentStr=edit_descreption.text.toString()
                approveRejectApi(data, isChangeMgmnt)
                dialog.dismiss()
            }
        }
    }

    fun approveRejectApi(data: Any, isChangeMgmnt:Boolean) {
        (activity as MainActivity).showProgressBar()
        var changeOrProblemManagementData = data as Map<String, Any>
        val requestBody = HashMap<String, Any>()
        var id = ""
        var approvalGroup = ""
        var managementName = ""
        if(isChangeMgmnt){
            id = "number"
            approvalGroup = "change_management"
            managementName = "change"
        }else{
            id = "problemId"
            approvalGroup = "problem_management"
            managementName = "problem"
        }
        if(changeOrProblemManagementData.containsKey(id) && changeOrProblemManagementData[id]!=null )
            requestBody["id"] = changeOrProblemManagementData[id].toString()


        requestBody["domainId"] = PreferenceUtility.getString(activity, MyConstants.DOMAIN_ID, "")
        requestBody["approvalGroup"] = approvalGroup
        requestBody["managementName"] = managementName
        requestBody["type"] = "hpsmMailRequest"
        requestBody["actionType"] = optionSelected!!
        requestBody["mailComment"] = commentStr!!

        Log.e("requestBody", ""+requestBody)
        CoroutineScope(Dispatchers.IO).launch {

            var job = async {
                BaseCoroutines().fetchData(
                    requestBody,
                    Busicode.HPSMInsertApprovalStatus,
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
                } else if (response!!.errorCode != null && response!!.errorCode.equals(MappActor.VERSION_SESSION_INVALID)) {
                    (activity as MainActivity).showDialogForSessionExpired(
                        (activity as MainActivity).resources.getString(
                            R.string.session_expired
                        ), (activity as MainActivity)
                    )
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
                    T.show(activity, msg["message"].toString(), 0)
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

}
