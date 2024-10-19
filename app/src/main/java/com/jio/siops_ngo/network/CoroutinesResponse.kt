package com.jio.myjio.bean

import android.os.Bundle
import java.io.Serializable

public class CoroutinesResponse :Serializable{
    var responseEntity:Map<String, Any>?=null
    var responseEntityList:List<Map<String, Any>>?=null
    var status:Int=0;
    var bundle:Bundle?=null
    var errorMsg:String?=null
    var errorCode:String?=null
}