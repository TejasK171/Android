package com.jio.jioinfra.custom


import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.jio.siops_ngo.R


class ButtonViewLight(context: Context, attrs: AttributeSet?, defStyle: Int) : androidx.appcompat.widget.AppCompatButton(context, attrs, defStyle) {

    init {
        if (!isInEditMode) {
            init(context)

        }
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
        if (!isInEditMode) {
            init(context)

        }
    }

    constructor(context: Context) : this(context, null) {
        if (!isInEditMode) {
            init(context)
        }
    }

    override fun setText(text: CharSequence, type: TextView.BufferType) {
        //super.setText(text.toString().toUpperCase(), type);
        try {
            super.setText(text.toString(), type)
        } catch (exception: Exception) {
        }

    }

    fun init(mContext: Context) {
        val baseTypeface = ResourcesCompat.getFont(mContext, R.font.jio_type_light)
        this.setTypeface(baseTypeface, Typeface.NORMAL)

    }

}

