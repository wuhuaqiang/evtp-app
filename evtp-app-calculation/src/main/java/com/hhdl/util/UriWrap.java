package com.hhdl.util;

import java.util.Map;
import java.util.Set;

public class UriWrap {
    public static String CreateUri(String uri, Map param) {

        Set<String> keys = param.keySet();
        if (keys.size() > 0) {
            uri += "?";
        }
        for (String key : keys) {
            uri += key + "={" + key + "}&";
        }
        return uri.substring(0, uri.length() - 1);
    }
}
