package com.jio.jioinfra.utilities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import androidx.core.widget.ImageViewCompat
import androidx.appcompat.widget.AppCompatImageView
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager

import com.jio.siops_ngo.R

import org.w3c.dom.NodeList

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


/**
 * (View related tools)
 */
class ViewUtils {
    companion object {


        /**
         * (Automatic soft keyboard ??)
         */
        fun hideSolftInput(context: Context) {
            val manager = context
                    .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if ((context as Activity).currentFocus != null && context.currentFocus!!.windowToken != null) {
                manager.hideSoftInputFromWindow(context
                        .currentFocus!!.windowToken,
                        InputMethodManager.HIDE_NOT_ALWAYS)
            }
        }

        public fun isEmptyString(str: String?): Boolean {
            var str: String? = str ?: return true
            str = str!!.trim { it <= ' ' }
            return str.isEmpty() || str.equals("", ignoreCase = true) || str.equals("null", ignoreCase = true) || str.equals(" ", ignoreCase = true)
        }


        fun isEmptyNodeList(nodeList: NodeList?): Boolean {
            return nodeList == null || nodeList.length == 0
        }


        @SuppressLint("NewApi")
        fun setTintColor(view: View?, colorPath: String, mContext: Context) {

            try {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    if (view != null && !ViewUtils.isEmptyString(colorPath)) {
                        val drawable = view.background
                        val mutable = drawable.mutate()
                        mutable.setTint(Color.parseColor(colorPath))
                    }
                } else {
                    if (view != null && !ViewUtils.isEmptyString(colorPath)) {
                        val colors = intArrayOf(Color.parseColor(colorPath), Color.parseColor(colorPath))
                        var gd = GradientDrawable(
                                GradientDrawable.Orientation.TOP_BOTTOM,
                                colors)
                        gd.cornerRadius = mContext.resources.getDimension(R.dimen.scale_16dp)

                        val mutableDrawable = gd.mutate()

                        if (mutableDrawable is GradientDrawable) {
                            gd = mutableDrawable
                        }

                        val r = mContext.resources
                        val px = Math.round(TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP, 60f, r.displayMetrics))
                        gd.setSize(px, px)
                        view.background = gd
                    }
                }
            } catch (e: Exception) {

            }

        }


        fun setTintColorToImage(view: AppCompatImageView, colorPath: Int) {

            try {
                ImageViewCompat.setImageTintList(view, ColorStateList.valueOf(colorPath))
            } catch (e: Exception) {

            }

        }

        fun getDate(strDate: String): Long {
            //String strDate = day + "/" + month + "/" + year;
            var tempDate: Date? = null
            try {
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
                tempDate = dateFormat.parse(strDate)

            } catch (e: ParseException) {
                Log.d("ABC", "" + e.message)
            }

            return tempDate!!.time
        }


        @Throws(ParseException::class)
        fun formatTimeToAmPm(time: String): String {

            val sdf = SimpleDateFormat("hh:mm:ss")
            val parseDate = sdf.parse(time)

            val out = SimpleDateFormat("h:mm a")

            return out.format(parseDate)
        }

        fun formatDate(dateTime: String/*int day, int month, int year*/): String {
            return formatDate(getDate(dateTime))
        }

        fun formatDate(dateTime: Long): String {
            val format = "d MMM yyyy"
            val date = Date(dateTime)
            return SimpleDateFormat(format, Locale.US).format(date)
        }
    }
}
