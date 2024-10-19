package com.jio.siops_ngo.utilities

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout
import android.widget.TextView
import com.jio.siops_ngo.R

object DialogUtils {

    interface AutoDismissOnClickListener {
        fun onYesClick()

        fun onNoClick()
    }


    fun showKeyboard(context: Context) {
        try {
            (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        } catch (ext: Exception) {

        }

    }

    fun hideKeyboard(ctx: Context) {
        val inputManager = ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        // check if no view has focus:
        val v = (ctx as Activity).currentFocus ?: return

        inputManager.hideSoftInputFromWindow(v.windowToken, 0)
    }

   public fun isEmptyString(str: String?): Boolean {
        var str: String? = str ?: return true
        str = str!!.trim { it <= ' ' }
        return str.isEmpty() || str.equals("", ignoreCase = true) || str.equals("null", ignoreCase = true) || str.equals(" ", ignoreCase = true)
    }



    fun showYesNoDialogAutoDismiss(context: Context?,
                                   message: String, okLabel: String, cancelLabel: String,
                                   mAutoDismissOnClickListener: AutoDismissOnClickListener?): Dialog? {
        try {
            if (context != null && context is Activity && !context.isFinishing) {
                val dialog = Dialog(context, R.style.NoTittleWithDimDialogTheme)
                dialog.setCancelable(false)
                dialog.setContentView(R.layout.dialog_yes_and_no)
                val dialogContent = dialog
                        .findViewById<TextView>(R.id.tv_dialog_content)
                val cancelTextView = dialog
                        .findViewById<TextView>(R.id.tv_cancle)
                val confirmTextView = dialog
                        .findViewById<TextView>(R.id.tv_confirm)
                val cancel = dialog
                        .findViewById<RelativeLayout>(R.id.rl_cancle)
                val confirm = dialog
                        .findViewById<RelativeLayout>(R.id.rl_confirm)
                cancelTextView.text = cancelLabel
                confirmTextView.text = okLabel
                dialogContent.text = message
                confirm.setOnClickListener {
                    dialog.dismiss()
                    mAutoDismissOnClickListener?.onYesClick()
                }
                cancel.setOnClickListener {
                    dialog.dismiss()
                    mAutoDismissOnClickListener?.onNoClick()
                }
                dialog.show()
                return dialog
            }
        } catch (e: Exception) {

        }

        return null
    }

    fun showYesDialogAutoDismiss(context: Context?,
                                   message: String, okLabel: String, cancelLabel: String,
                                   mAutoDismissOnClickListener: AutoDismissOnClickListener?): Dialog? {
        try {
            if (context != null && context is Activity && !context.isFinishing) {
               /* val dialog = Dialog(context, R.style.NoTittleWithDimDialogTheme)
                dialog.setCancelable(false)
                dialog.setContentView(R.layout.dialog_yes_and_no)
                val dialogContent = dialog
                    .findViewById<TextView>(R.id.tv_dialog_content)
                val cancelTextView = dialog
                    .findViewById<TextView>(R.id.tv_cancle)
                val confirmTextView = dialog
                    .findViewById<TextView>(R.id.tv_confirm)
                val cancel = dialog
                    .findViewById<RelativeLayout>(R.id.rl_cancle)
                val confirm = dialog
                    .findViewById<RelativeLayout>(R.id.rl_confirm)
                cancelTextView.text = cancelLabel
                confirmTextView.text = okLabel
                dialogContent.text = message
                confirm.setOnClickListener {
                    dialog.dismiss()
                    mAutoDismissOnClickListener?.onYesClick()
                }
                cancel.visibility = View.GONE
                cancel.setOnClickListener {
                    dialog.dismiss()
                    mAutoDismissOnClickListener?.onNoClick()
                }*/




                val dialog = Dialog(context, R.style.NoTittleWithDimDialogTheme)
                dialog.setCancelable(false)
                dialog.setContentView(R.layout.dialog_ok)
                val dialogContent = dialog
                    .findViewById<TextView>(R.id.tv_dialog_content)
                val oKTextView = dialog
                    .findViewById<TextView>(R.id.tv_ok)
                val relativeLayout_OK = dialog
                    .findViewById<RelativeLayout>(R.id.rl_cancle)
                oKTextView.text = context.resources.getString(R.string.button_ok)
                dialogContent.text = message
                relativeLayout_OK.setOnClickListener { dialog.dismiss()
                    mAutoDismissOnClickListener?.onYesClick()}
                dialog.show()



                dialog.show()
                return dialog
            }
        } catch (e: Exception) {

        }

        return null
    }

    fun openAppSettings(mContext: Context) {
        //Console.debug(TAG, "openAppSettings()");
        try {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val uri = Uri.fromParts("package", mContext.packageName, null)
            intent.data = uri
            mContext.startActivity(intent)
        } catch (e: Exception) {

        }

    }

    fun setOTPErrorBackgroundColor(view: View, color: Int) {
        val drawable = view.background // get current EditText drawable
        drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP) // change the drawable color
        if (Build.VERSION.SDK_INT > 16) {
            view.background = drawable // set the new drawable to EditText
        } else {
            view.setBackgroundDrawable(drawable) // use setBackgroundDrawable because setBackground required API 16
        }
    }

    fun getAndroidId(mContext: Context): String? {
        var androidId: String? = ""
        try {
            val sm = DeviceSoftwareInfo()
            androidId = sm.getAndroidID(mContext)
        } catch (e: Exception) {

        }

        return androidId
    }


}
