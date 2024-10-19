package com.jio.siops_ngo

import com.jio.jioinfra.utilities.MyConstants

public class ApplicationDefine {
    companion object{
        public val CAMPAIGN_ANALYTICS1 = "MTIzQUZIS0lZ"
        public val CAMPAIGN_ANALYTICS2 = "MDkxWVFBRg=="
//        public val MAPP_SERVER_ADDRESS = "https://smart-iop-dev.jio.com"
//        public var MAPP_SERVER_ADDRESS = "https://smart-iop.jio.com:9443"
        public var MAPP_SERVER_ADDRESS = ""
        val SUPPORT_DISPATCHER = false
        val ENCRYPTION_ENABLED = true
        var CONTEXT_PATH = "jio-infra-nw-login-service"
        var SERVER = MyConstants.PROD
        val IS_UNPINNED_BUILD = false

        fun getMappServerAddress(){
            when(SERVER){

                MyConstants.PROD -> MAPP_SERVER_ADDRESS = "https://smart-iop.jio.com:9443"
                MyConstants.DEV -> MAPP_SERVER_ADDRESS = "https://smart-iop-dev.jio.com"
            }

        }

        fun IsBuildUnpnned():Boolean{
            return IS_UNPINNED_BUILD

        }
    }



}