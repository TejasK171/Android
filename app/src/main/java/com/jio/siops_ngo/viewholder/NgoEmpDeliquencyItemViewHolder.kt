package com.jio.siops_ngo.viewholder

import android.view.View
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.jio.jioinfra.custom.TextViewBold
import com.jio.jioinfra.custom.TextViewMedium
import com.jio.siops_ngo.R

class NgoEmpDeliquencyItemViewHolder (view: View?) : RecyclerView.ViewHolder(view!!) {

    var txtDomainId: TextViewBold? = null
    var txtDate1: TextViewMedium? = null
    var txtDate2: TextViewMedium? = null
    var txtDate3: TextViewMedium? = null
    var txtDate4: TextViewMedium? = null
    var txtDate5: TextViewMedium? = null
    var txtDate6: TextViewMedium? = null
    var txtDate7: TextViewMedium? = null
    var txtStatus1: TextViewMedium? = null
    var txtStatus2: TextViewMedium? = null
    var txtStatus3: TextViewMedium? = null
    var txtStatus4: TextViewMedium? = null
    var txtStatus5: TextViewMedium? = null
    var txtStatus6: TextViewMedium? = null
    var txtStatus7: TextViewMedium? = null
    var txtHrs1: TextViewMedium? = null
    var txtHrs2: TextViewMedium? = null
    var txtHrs3: TextViewMedium? = null
    var txtHrs4: TextViewMedium? = null
    var txtHrs5: TextViewMedium? = null
    var txtHrs6: TextViewMedium? = null
    var txtHrs7: TextViewMedium? = null
    var sendReminderRelL: RelativeLayout? = null

    init {
        txtDomainId = view!!.findViewById(R.id.txt_domain_id);
        txtDate1 = view!!.findViewById(R.id.txt_date1);
        txtDate2 = view!!.findViewById(R.id.txt_date2);
        txtDate3 = view!!.findViewById(R.id.txt_date3);
        txtDate4 = view!!.findViewById(R.id.txt_date4);
        txtDate5 = view!!.findViewById(R.id.txt_date5);
        txtDate6 = view!!.findViewById(R.id.txt_date6);
        txtDate7 = view!!.findViewById(R.id.txt_date7);
        txtStatus1 = view!!.findViewById(R.id.txt_status1);
        txtStatus2 = view!!.findViewById(R.id.txt_status2);
        txtStatus3 = view!!.findViewById(R.id.txt_status3);
        txtStatus4 = view!!.findViewById(R.id.txt_status4);
        txtStatus5 = view!!.findViewById(R.id.txt_status5);
        txtStatus6 = view!!.findViewById(R.id.txt_status6);
        txtStatus7 = view!!.findViewById(R.id.txt_status7);
        txtHrs1 = view!!.findViewById(R.id.txt_hrs1);
        txtHrs2 = view!!.findViewById(R.id.txt_hrs2);
        txtHrs3 = view!!.findViewById(R.id.txt_hrs3);
        txtHrs4 = view!!.findViewById(R.id.txt_hrs4);
        txtHrs5 = view!!.findViewById(R.id.txt_hrs5);
        txtHrs6 = view!!.findViewById(R.id.txt_hrs6);
        txtHrs7 = view!!.findViewById(R.id.txt_hrs7);
        sendReminderRelL = view!!.findViewById(R.id.rel_send_reminder);

    }
}