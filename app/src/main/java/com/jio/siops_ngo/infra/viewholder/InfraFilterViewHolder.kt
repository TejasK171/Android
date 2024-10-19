package com.jio.siops_ngo.infra.viewholder

import android.view.View
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.jio.siops_ngo.R

class InfraFilterViewHolder(view: View?)   : RecyclerView.ViewHolder(view!!) {
    var radioButton: RadioButton? = null
//    var textViewId: TextViewBold? = null
   // var dcbConstraintLayout: RelativeLayout? = null


    init {
        radioButton = view!!.findViewById(R.id.radioButton);
//        textViewId = view!!.findViewById(R.id.textViewId);
      //  dcbConstraintLayout = view!!.findViewById(R.id.cnsrntL_dcb);
    }
}