package com.jio.jioinfra.bean


import android.os.Bundle


class CommonBean {
    var bundle: Bundle? = null
    private var mTitle = ""
    private var id=""
    private var mSubTitle = ""
    private var mCommonAction = ""
    private var mName = ""
    var errorMessage = ""
    public var `object`: Any? = null
    public var viewType: Boolean? = null
    public var isAgeing: Boolean? = null
    public var `usefulLinksNgo`: Any? = null
    public var `usefulLinksInfra`: Any? = null
    public var `usefulLinksBusinessView`: Any? = null

    var errorCode = ""


    fun getId(): String {
        return id
    }

    fun setmId(id: String) {
        this.id = mTitle
    }


    fun getmTitle(): String {
        return mTitle
    }

    fun setmTitle(mTitle: String) {
        this.mTitle = mTitle
    }

    fun getmSubTitle(): String {
        return mSubTitle
    }

    fun setmSubTitle(mSubTitle: String) {
        this.mSubTitle = mSubTitle
    }

    fun getmName(): String {
        return mName
    }

    fun setmName(mName: String) {
        this.mName = mName
    }

    fun getIsAgeing(): Boolean {
        return isAgeing!!
    }

    fun setIsAgeing(isAgeing: Boolean) {
        this.isAgeing = isAgeing
    }

    fun getmCommonAction(): String {
        return mCommonAction
    }

    fun setmCommonAction(mCommonAction: String) {
        this.mCommonAction = mCommonAction
    }
}
