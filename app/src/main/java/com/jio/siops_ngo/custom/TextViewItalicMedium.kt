package com.jio.jioinfra.custom

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.core.content.res.ResourcesCompat
import com.jio.siops_ngo.R


class TextViewItalicMedium : androidx.appcompat.widget.AppCompatTextView {

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        if (!isInEditMode) {
            init(context)
        }
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        if (!isInEditMode) {
            init(context)
        }
    }

    constructor(context: Context) : super(context) {
        if (!isInEditMode) {
            init(context)
        }
    }

    fun init(mContext: Context) {
        //        Typeface baseTypeface = MyApplication.getInstance().mJioTypeMediumItalic;
        val baseTypeface = ResourcesCompat.getFont(mContext, R.font.jio_type_medium_italic)
        this.setTypeface(baseTypeface, Typeface.BOLD)

    }
}


