package com.jio.siops_ngo.utilities

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.jio.jioinfra.utilities.MyConstants
import com.jio.siops_ngo.R


class T {
    private var mContext: Context? = null

    private val mHandler = Handler(Handler.Callback { msg ->
        toast = Toast.makeText(mContext, msg.obj as String, Toast.LENGTH_LONG)
        toast!!.show()
        true
    })

    /**
     * Toast
     *
     * @param context
     * @param message
     */
    fun showDoubleLong(context: Context?, message: CharSequence) {
        mContext = context

        if (context != null && context is Activity && !context.isFinishing) {
            val alertBuilder = AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT)
            alertBuilder.setMessage(message)
            alertBuilder.setCancelable(false)
            alertBuilder.setPositiveButton(context.resources.getString(R.string.button_ok)
            ) { dialog, which -> }.show()
        }
        /*if (null == toast) {
			toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
		} else {
			toast.setText(message);
		}
		Message handlermessage = mHandler.obtainMessage();
		handlermessage.obj = message;
		mHandler.sendMessageDelayed(handlermessage, 3000);
		toast.show();*/
    }

    companion object {
        // Toast
        private var toast: Toast? = null
        private var alertDialog: AlertDialog.Builder? = null

        /**
         * Toast
         *
         * @param context
         * @param message
         */
        fun showShort(context: Context?, message: CharSequence) {
            /*if (null == toast)
        {
			toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
			// toast.setGravity(Gravity.CENTER, 0, 0);
		} else {
			toast.setText(message);
		}
		toast.show();*/
            try {
                // if (MyConstants.isNetworkConnectionAvailable) {
                if (context != null && context is Activity && !context.isFinishing) {

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
                    relativeLayout_OK.setOnClickListener { dialog.dismiss() }

                    dialog.show()
                    //                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
                    //                    alertBuilder.setMessage(message);
                    //                    alertBuilder.setCancelable(false);
                    //                    alertBuilder.setPositiveButton(context.getResources().getString(R.string.ok),
                    //                            new DialogInterface.OnClickListener() {
                    //
                    //                                @Override
                    //                                public void onClick(DialogInterface dialog, int which) {
                    //
                    //                                }
                    //                    }).show();
                }
                // }

            } catch (e: Exception) {

                Log.d("ABC", "" + e.message)
            }

        }

        /**
         * Toast
         *
         * @param context
         * @param message
         */
        fun showShort(context: Context?, message: Int) {
            /*	if (null == toast) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
			// toast.setGravity(Gravity.CENTER, 0, 0);
		} else {
			toast.setText(message);
		}
		toast.show();*/
            if (MyConstants.isNetworkConnectionAvailable) {
                if (context != null && context is Activity && !context.isFinishing) {
                    if (alertDialog == null) {
                        alertDialog = AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT)
                    }
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
                    dialogContent.setText(message)
                    relativeLayout_OK.setOnClickListener { dialog.dismiss() }

                    dialog.show()
                    //                alertDialog.setMessage(message);
                    //                alertDialog.setCancelable(false);
                    //                alertDialog.setPositiveButton(context.getResources().getString(R.string.ok),
                    //                        new DialogInterface.OnClickListener() {
                    //
                    //                            @Override
                    //                            public void onClick(DialogInterface dialog, int which) {
                    //
                    //
                    //                            }
                    //                 }).show();
                }
            }
        }

        /**
         * Toast
         *
         * @param context
         * @param message
         */
        fun showLong(context: Context?, message: CharSequence) {
            /*if (null == toast) {
			toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
			// toast.setGravity(Gravity.CENTER, 0, 0);
		} else {
			toast.setText(message);
		}
		toast.show();*/

            if (context != null && context is Activity && !context.isFinishing) {
                val dialog = Dialog(context, R.style.NoTittleWithDimDialogTheme) /// changed the theme
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
                relativeLayout_OK.setOnClickListener { dialog.dismiss() }

                dialog.show()
                //            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
                //            alertBuilder.setMessage(message);
                //            alertBuilder.setCancelable(false);
                //            alertBuilder.setPositiveButton(context.getResources().getString(R.string.ok),
                //                    new DialogInterface.OnClickListener() {
                //
                //                        @Override
                //                        public void onClick(DialogInterface dialog, int which) {
                //
                //                        }
                //            }).show();
            }
        }

        /**
         * Toast
         *
         * @param context
         * @param message
         */
        fun showLong(context: Context?, message: Int) {
            /*if (null == toast) {
			toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
			// toast.setGravity(Gravity.CENTER, 0, 0);
		} else {
			toast.setText(message);
		}
		toast.show();*/


            if (context != null && context is Activity && !context.isFinishing) {
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
                dialogContent.setText(message)
                relativeLayout_OK.setOnClickListener { dialog.dismiss() }

                dialog.show()

                //            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
                //            alertBuilder.setMessage(message);
                //            alertBuilder.setCancelable(false);
                //
                //            alertBuilder.setPositiveButton(context.getResources().getString(R.string.ok),
                //                    new DialogInterface.OnClickListener() {
                //
                //                        @Override
                //                        public void onClick(DialogInterface dialog, int which) {
                //
                //                        }
                //            }).show();
            }
        }

        /**
         * Toast
         *
         * @param context
         * @param message
         * @param duration
         */
        fun show(context: Context?, message: CharSequence, duration: Int) {
            /*if (null == toast) {
			toast = Toast.makeText(context, message, duration);
			// toast.setGravity(Gravity.CENTER, 0, 0);
		} else {
			toast.setText(message);
		}
		toast.show();*/
            try {
                if (MyConstants.isNetworkConnectionAvailable) {
                    if (context != null && context is Activity && !context.isFinishing) {

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
                        relativeLayout_OK.setOnClickListener { dialog.dismiss() }
                        dialog.show()

                        //                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
                        //                alertBuilder.setMessage(message);
                        //                alertBuilder.setCancelable(false);
                        //                alertBuilder.setPositiveButton(context.getResources().getString(R.string.ok),
                        //                        new DialogInterface.OnClickListener() {
                        //
                        //                            @Override
                        //                            public void onClick(DialogInterface dialog, int which) {
                        //
                        //                            }
                        //                }).show();
                    }
                }
            } catch (e: Resources.NotFoundException) {
                e.printStackTrace()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

        }

        /**
         * Toast
         *
         * @param context
         * @param message
         * @param duration
         */
        fun show(context: Context?, message: Int, duration: Int) {
            /*if (null == toast) {
			toast = Toast.makeText(context, message, duration);
			// toast.setGravity(Gravity.CENTER, 0, 0);
		} else {
			toast.setText(message);
		}
		toast.show();*/
            try {
                if (MyConstants.isNetworkConnectionAvailable) {
                    if (context != null && context is Activity && !context.isFinishing) {
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
                        dialogContent.setText(message)
                        relativeLayout_OK.setOnClickListener { dialog.dismiss() }
                        dialog.show()
                        //                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
                        //                alertBuilder.setMessage(message);
                        //                alertBuilder.setCancelable(false);
                        //                alertBuilder.setPositiveButton(context.getResources().getString(R.string.ok), null);
                        //                alertBuilder.show();
                    }
                }
            } catch (e: Resources.NotFoundException) {
                e.printStackTrace()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

        }

        /**
         * Hide the toast, if any.
         */
        fun hideToast() {
            if (null != toast) {
                toast!!.cancel()
            }
        }

        fun showShortSystemToast(context: Context?, message: String) {
            if (context != null && !TextUtils.isEmpty(message)) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }


        fun showAlertDialog(context: Context?, message: CharSequence, duration: Int) {

            try {
                if (context != null && context is Activity && !context.isFinishing) {

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
                    relativeLayout_OK.setOnClickListener { dialog.dismiss() }
                    dialog.show()
                }
            } catch (e: Resources.NotFoundException) {
                e.printStackTrace()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

        }

        fun showErrorMsg(context: Context?, message: CharSequence, duration: Int) {
            /*if (null == toast) {
			toast = Toast.makeText(context, message, duration);
			// toast.setGravity(Gravity.CENTER, 0, 0);
		} else {
			toast.setText(message);
		}
		toast.show();*/
            try {
                if (MyConstants.isNetworkConnectionAvailable) {
                    if (context != null && context is Activity && !context.isFinishing) {

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
                        dialogContent.setTextColor(context.resources.getColor(R.color.dead_dells_orange))
                        relativeLayout_OK.setOnClickListener { dialog.dismiss() }
                        dialog.show()

                        //                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
                        //                alertBuilder.setMessage(message);
                        //                alertBuilder.setCancelable(false);
                        //                alertBuilder.setPositiveButton(context.getResources().getString(R.string.ok),
                        //                        new DialogInterface.OnClickListener() {
                        //
                        //                            @Override
                        //                            public void onClick(DialogInterface dialog, int which) {
                        //
                        //                            }
                        //                }).show();
                    }
                }
            } catch (e: Resources.NotFoundException) {
                e.printStackTrace()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

        }

    }
}
