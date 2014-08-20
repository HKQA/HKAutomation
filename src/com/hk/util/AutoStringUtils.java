package com.hk.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 8/20/14
 * Time: 6:26 PM
 */
public class AutoStringUtils {

    private static final char DEFAULT_SEP = ',';

    public static String getListAsString(List list) {
        return getListAsString(list, DEFAULT_SEP);
    }

    public static String getListAsString(List list, char separator) {
        String str = "";

        for (Object o : list) {
            str += o.toString() + separator;
        }

        return str.substring(0, str.length() - 1);
    }
}
