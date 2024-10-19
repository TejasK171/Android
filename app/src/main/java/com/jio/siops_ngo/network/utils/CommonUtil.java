package com.jio.siops_ngo.network.utils;

import java.io.Serializable;

public class CommonUtil implements Serializable {

    public static boolean isStringEmptyOrNull(String input) {
        return input == null || "null".equals(input) || "".equals(input.trim());
    }
}
