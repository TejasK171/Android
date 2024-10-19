package com.jio.siops_ngo.bean

class FilterGetterAndSetter {

   private var regian:String=""
    private var startimpactedCustomer:String=""
    private var endimpactedCustomer:String=""
    private var startWorkOrder:String=""
    private var endWorkOrder:String=""
    private var startAging:String=""
    private var endAging:String=""
    private  var circle:String=""
    private  var mp:String=""
    private  var jc:String=""

    fun getRegian(): String {
        return regian
    }

    fun setRegian(regian: String) {
        this.regian = regian
    }



    fun getStartWorkOrder(): String {
        return startWorkOrder
    }

    fun setStartWorkOrder(startWorkOrder: String) {
        this.startWorkOrder = startWorkOrder
    }



    fun getStartimpactedCustomer(): String {
        return startimpactedCustomer
    }



    fun getEndimpactedCustomer(): String {
        return endimpactedCustomer
    }

    fun setEndimpactedCustomer(endimpactedCustomer: String) {
        this.endimpactedCustomer = endimpactedCustomer
    }



    fun setStartimpactedCustomer(startimpactedCustomer: String) {
        this.startimpactedCustomer = startimpactedCustomer
    }

    fun getStartAging(): String {
        return startAging
    }

    fun setStartAging(startAging: String) {
        this.startAging = startAging
    }


    fun getEndAging(): String {
        return endAging
    }

    fun setEndAging(endAging: String) {
        this.endAging = endAging
    }


    fun getCircle(): String {
        return circle
    }

    fun setCircle(circle: String) {
        this.circle = circle
    }



    fun getMp(): String {
        return mp
    }

    fun setMp(mp: String) {
        this.mp = mp
    }



    fun getJc(): String {
        return jc
    }

    fun setJc(jc: String) {
        this.jc = jc
    }

}