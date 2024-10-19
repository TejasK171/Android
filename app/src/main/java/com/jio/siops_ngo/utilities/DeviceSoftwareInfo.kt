package com.jio.siops_ngo.utilities

import android.content.Context
import android.os.Build
import android.provider.Settings

class DeviceSoftwareInfo {

    val consumptionDeviceName: String
        get() {
            var dev: String? = null
            try {
                dev = Build.DEVICE
                if (dev == null || dev.length == 0) {
                    dev = "AndroidDevice"
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return dev!!
        }

    val deviceType: String
        get() = "android"

    val deviceName: String
        get() = Build.MODEL

    val deviceVersion: String
        get() = Build.VERSION.RELEASE

    private val operatingSystem: String
        get() {
            var os: String
            try {
                val api = Build.VERSION.SDK_INT

                when (api) {
                    1 -> {
                        os = OS.BASE.platform

                        os = OS.BASE_1.platform

                        os = OS.CUPCAKE.platform

                        os = OS.DONUT.platform

                        os = OS.ECLAIR.platform

                        os = OS.ECLAIR_MR1.platform

                        os = OS.ECLAIR_MR2.platform

                        os = OS.FROYO.platform

                        os = OS.GINGERBREAD.platform

                        os = OS.GINGERBREAD_MR1.platform

                        os = OS.HONEYCOMB.platform

                        os = OS.HONEYCOMB_MR1.platform

                        os = OS.HONEYCOMB_MR2.platform

                        os = OS.ICE_CREAM_SANDWICH.platform

                        os = OS.ICE_CREAM_SANDWICH_MR1.platform

                        os = OS.JELLY_BEAN.platform

                        os = OS.JELLY_BEAN_MR1.platform

                        os = OS.JELLY_BEAN_MR2.platform

                        os = OS.KIT_KAT.platform

                        os = OS.KIT_KAT.platform

                        os = OS.LOLLIPOP.platform
                        os = ""
                    }
                    2 -> {
                        os = OS.BASE_1.platform
                        os = OS.CUPCAKE.platform
                        os = OS.DONUT.platform
                        os = OS.ECLAIR.platform
                        os = OS.ECLAIR_MR1.platform
                        os = OS.ECLAIR_MR2.platform
                        os = OS.FROYO.platform
                        os = OS.GINGERBREAD.platform
                        os = OS.GINGERBREAD_MR1.platform
                        os = OS.HONEYCOMB.platform
                        os = OS.HONEYCOMB_MR1.platform
                        os = OS.HONEYCOMB_MR2.platform
                        os = OS.ICE_CREAM_SANDWICH.platform
                        os = OS.ICE_CREAM_SANDWICH_MR1.platform
                        os = OS.JELLY_BEAN.platform
                        os = OS.JELLY_BEAN_MR1.platform
                        os = OS.JELLY_BEAN_MR2.platform
                        os = OS.KIT_KAT.platform
                        os = OS.KIT_KAT.platform
                        os = OS.LOLLIPOP.platform
                        os = ""
                    }
                    3 -> {
                        os = OS.CUPCAKE.platform
                        os = OS.DONUT.platform
                        os = OS.ECLAIR.platform
                        os = OS.ECLAIR_MR1.platform
                        os = OS.ECLAIR_MR2.platform
                        os = OS.FROYO.platform
                        os = OS.GINGERBREAD.platform
                        os = OS.GINGERBREAD_MR1.platform
                        os = OS.HONEYCOMB.platform
                        os = OS.HONEYCOMB_MR1.platform
                        os = OS.HONEYCOMB_MR2.platform
                        os = OS.ICE_CREAM_SANDWICH.platform
                        os = OS.ICE_CREAM_SANDWICH_MR1.platform
                        os = OS.JELLY_BEAN.platform
                        os = OS.JELLY_BEAN_MR1.platform
                        os = OS.JELLY_BEAN_MR2.platform
                        os = OS.KIT_KAT.platform
                        os = OS.KIT_KAT.platform
                        os = OS.LOLLIPOP.platform
                        os = ""
                    }
                    4 -> {
                        os = OS.DONUT.platform
                        os = OS.ECLAIR.platform
                        os = OS.ECLAIR_MR1.platform
                        os = OS.ECLAIR_MR2.platform
                        os = OS.FROYO.platform
                        os = OS.GINGERBREAD.platform
                        os = OS.GINGERBREAD_MR1.platform
                        os = OS.HONEYCOMB.platform
                        os = OS.HONEYCOMB_MR1.platform
                        os = OS.HONEYCOMB_MR2.platform
                        os = OS.ICE_CREAM_SANDWICH.platform
                        os = OS.ICE_CREAM_SANDWICH_MR1.platform
                        os = OS.JELLY_BEAN.platform
                        os = OS.JELLY_BEAN_MR1.platform
                        os = OS.JELLY_BEAN_MR2.platform
                        os = OS.KIT_KAT.platform
                        os = OS.KIT_KAT.platform
                        os = OS.LOLLIPOP.platform
                        os = ""
                    }
                    5 -> {
                        os = OS.ECLAIR.platform
                        os = OS.ECLAIR_MR1.platform
                        os = OS.ECLAIR_MR2.platform
                        os = OS.FROYO.platform
                        os = OS.GINGERBREAD.platform
                        os = OS.GINGERBREAD_MR1.platform
                        os = OS.HONEYCOMB.platform
                        os = OS.HONEYCOMB_MR1.platform
                        os = OS.HONEYCOMB_MR2.platform
                        os = OS.ICE_CREAM_SANDWICH.platform
                        os = OS.ICE_CREAM_SANDWICH_MR1.platform
                        os = OS.JELLY_BEAN.platform
                        os = OS.JELLY_BEAN_MR1.platform
                        os = OS.JELLY_BEAN_MR2.platform
                        os = OS.KIT_KAT.platform
                        os = OS.KIT_KAT.platform
                        os = OS.LOLLIPOP.platform
                        os = ""
                    }
                    6 -> {
                        os = OS.ECLAIR_MR1.platform
                        os = OS.ECLAIR_MR2.platform
                        os = OS.FROYO.platform
                        os = OS.GINGERBREAD.platform
                        os = OS.GINGERBREAD_MR1.platform
                        os = OS.HONEYCOMB.platform
                        os = OS.HONEYCOMB_MR1.platform
                        os = OS.HONEYCOMB_MR2.platform
                        os = OS.ICE_CREAM_SANDWICH.platform
                        os = OS.ICE_CREAM_SANDWICH_MR1.platform
                        os = OS.JELLY_BEAN.platform
                        os = OS.JELLY_BEAN_MR1.platform
                        os = OS.JELLY_BEAN_MR2.platform
                        os = OS.KIT_KAT.platform
                        os = OS.KIT_KAT.platform
                        os = OS.LOLLIPOP.platform
                        os = ""
                    }
                    7 -> {
                        os = OS.ECLAIR_MR2.platform
                        os = OS.FROYO.platform
                        os = OS.GINGERBREAD.platform
                        os = OS.GINGERBREAD_MR1.platform
                        os = OS.HONEYCOMB.platform
                        os = OS.HONEYCOMB_MR1.platform
                        os = OS.HONEYCOMB_MR2.platform
                        os = OS.ICE_CREAM_SANDWICH.platform
                        os = OS.ICE_CREAM_SANDWICH_MR1.platform
                        os = OS.JELLY_BEAN.platform
                        os = OS.JELLY_BEAN_MR1.platform
                        os = OS.JELLY_BEAN_MR2.platform
                        os = OS.KIT_KAT.platform
                        os = OS.KIT_KAT.platform
                        os = OS.LOLLIPOP.platform
                        os = ""
                    }
                    8 -> {
                        os = OS.FROYO.platform
                        os = OS.GINGERBREAD.platform
                        os = OS.GINGERBREAD_MR1.platform
                        os = OS.HONEYCOMB.platform
                        os = OS.HONEYCOMB_MR1.platform
                        os = OS.HONEYCOMB_MR2.platform
                        os = OS.ICE_CREAM_SANDWICH.platform
                        os = OS.ICE_CREAM_SANDWICH_MR1.platform
                        os = OS.JELLY_BEAN.platform
                        os = OS.JELLY_BEAN_MR1.platform
                        os = OS.JELLY_BEAN_MR2.platform
                        os = OS.KIT_KAT.platform
                        os = OS.KIT_KAT.platform
                        os = OS.LOLLIPOP.platform
                        os = ""
                    }
                    9 -> {
                        os = OS.GINGERBREAD.platform
                        os = OS.GINGERBREAD_MR1.platform
                        os = OS.HONEYCOMB.platform
                        os = OS.HONEYCOMB_MR1.platform
                        os = OS.HONEYCOMB_MR2.platform
                        os = OS.ICE_CREAM_SANDWICH.platform
                        os = OS.ICE_CREAM_SANDWICH_MR1.platform
                        os = OS.JELLY_BEAN.platform
                        os = OS.JELLY_BEAN_MR1.platform
                        os = OS.JELLY_BEAN_MR2.platform
                        os = OS.KIT_KAT.platform
                        os = OS.KIT_KAT.platform
                        os = OS.LOLLIPOP.platform
                        os = ""
                    }
                    10 -> {
                        os = OS.GINGERBREAD_MR1.platform
                        os = OS.HONEYCOMB.platform
                        os = OS.HONEYCOMB_MR1.platform
                        os = OS.HONEYCOMB_MR2.platform
                        os = OS.ICE_CREAM_SANDWICH.platform
                        os = OS.ICE_CREAM_SANDWICH_MR1.platform
                        os = OS.JELLY_BEAN.platform
                        os = OS.JELLY_BEAN_MR1.platform
                        os = OS.JELLY_BEAN_MR2.platform
                        os = OS.KIT_KAT.platform
                        os = OS.KIT_KAT.platform
                        os = OS.LOLLIPOP.platform
                        os = ""
                    }
                    11 -> {
                        os = OS.HONEYCOMB.platform
                        os = OS.HONEYCOMB_MR1.platform
                        os = OS.HONEYCOMB_MR2.platform
                        os = OS.ICE_CREAM_SANDWICH.platform
                        os = OS.ICE_CREAM_SANDWICH_MR1.platform
                        os = OS.JELLY_BEAN.platform
                        os = OS.JELLY_BEAN_MR1.platform
                        os = OS.JELLY_BEAN_MR2.platform
                        os = OS.KIT_KAT.platform
                        os = OS.KIT_KAT.platform
                        os = OS.LOLLIPOP.platform
                        os = ""
                    }
                    12 -> {
                        os = OS.HONEYCOMB_MR1.platform
                        os = OS.HONEYCOMB_MR2.platform
                        os = OS.ICE_CREAM_SANDWICH.platform
                        os = OS.ICE_CREAM_SANDWICH_MR1.platform
                        os = OS.JELLY_BEAN.platform
                        os = OS.JELLY_BEAN_MR1.platform
                        os = OS.JELLY_BEAN_MR2.platform
                        os = OS.KIT_KAT.platform
                        os = OS.KIT_KAT.platform
                        os = OS.LOLLIPOP.platform
                        os = ""
                    }
                    13 -> {
                        os = OS.HONEYCOMB_MR2.platform
                        os = OS.ICE_CREAM_SANDWICH.platform
                        os = OS.ICE_CREAM_SANDWICH_MR1.platform
                        os = OS.JELLY_BEAN.platform
                        os = OS.JELLY_BEAN_MR1.platform
                        os = OS.JELLY_BEAN_MR2.platform
                        os = OS.KIT_KAT.platform
                        os = OS.KIT_KAT.platform
                        os = OS.LOLLIPOP.platform
                        os = ""
                    }
                    14 -> {
                        os = OS.ICE_CREAM_SANDWICH.platform
                        os = OS.ICE_CREAM_SANDWICH_MR1.platform
                        os = OS.JELLY_BEAN.platform
                        os = OS.JELLY_BEAN_MR1.platform
                        os = OS.JELLY_BEAN_MR2.platform
                        os = OS.KIT_KAT.platform
                        os = OS.KIT_KAT.platform
                        os = OS.LOLLIPOP.platform
                        os = ""
                    }
                    15 -> {
                        os = OS.ICE_CREAM_SANDWICH_MR1.platform
                        os = OS.JELLY_BEAN.platform
                        os = OS.JELLY_BEAN_MR1.platform
                        os = OS.JELLY_BEAN_MR2.platform
                        os = OS.KIT_KAT.platform
                        os = OS.KIT_KAT.platform
                        os = OS.LOLLIPOP.platform
                        os = ""
                    }
                    16 -> {
                        os = OS.JELLY_BEAN.platform
                        os = OS.JELLY_BEAN_MR1.platform
                        os = OS.JELLY_BEAN_MR2.platform
                        os = OS.KIT_KAT.platform
                        os = OS.KIT_KAT.platform
                        os = OS.LOLLIPOP.platform
                        os = ""
                    }
                    17 -> {
                        os = OS.JELLY_BEAN_MR1.platform
                        os = OS.JELLY_BEAN_MR2.platform
                        os = OS.KIT_KAT.platform
                        os = OS.KIT_KAT.platform
                        os = OS.LOLLIPOP.platform
                        os = ""
                    }
                    18 -> {
                        os = OS.JELLY_BEAN_MR2.platform
                        os = OS.KIT_KAT.platform
                        os = OS.KIT_KAT.platform
                        os = OS.LOLLIPOP.platform
                        os = ""
                    }
                    19 -> {
                        os = OS.KIT_KAT.platform
                        os = OS.KIT_KAT.platform
                        os = OS.LOLLIPOP.platform
                        os = ""
                    }
                    20 -> {
                        os = OS.KIT_KAT.platform
                        os = OS.LOLLIPOP.platform
                        os = ""
                    }
                    21 -> {
                        os = OS.LOLLIPOP.platform
                        os = ""
                    }
                    else -> os = ""
                }
            } catch (e: Exception) {
                os = ""
                e.printStackTrace()
            }

            return os
        }

    fun getAndroidID(context: Context): String? {
        var id: String? = ""
        try {
            id = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        } catch (ex: Exception) {
            id = null
            ex.printStackTrace()
        }

        return id
    }

    private enum class OS private constructor(val platform: String) {
        BASE("BASE"), BASE_1("BASE 1"), CUPCAKE("CUPCAKE"), DONUT("DONUT"), ECLAIR(
                "ECLAIR"),
        ECLAIR_MR1("ECLAIR MR1"), ECLAIR_MR2("ECLAIR MR2"), FROYO(
                "FROYO"),
        GINGERBREAD("GINGERBREAD"), GINGERBREAD_MR1(
                "GINGERBREAD MR1"),
        HONEYCOMB("HONEYCOMB"), HONEYCOMB_MR1(
                "HONEYCOMB MR1"),
        HONEYCOMB_MR2("HONEYCOMB MR2"), ICE_CREAM_SANDWICH(
                "ICE CREAM SANDWICH"),
        ICE_CREAM_SANDWICH_MR1(
                "ICE CREAM SANDWICH MR1"),
        JELLY_BEAN("JELLY BEAN"), JELLY_BEAN_MR1(
                "JELLY BEAN MR1"),
        JELLY_BEAN_MR2("JELLY BEAN MR2"), KIT_KAT(
                "KITKAT"),
        LOLLIPOP(",LOLLIPOP")
    }
}
