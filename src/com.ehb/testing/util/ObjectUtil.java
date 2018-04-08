package com.ehb.testing.util;

import java.util.List;
import java.util.Objects;

/**
 * Created by xiangxingqian on 2017/12/29.
 */
public class ObjectUtil {

    public static boolean notEmpty(String s) {
        return Objects.nonNull(s) && !s.equals("");
    }

    public static boolean notEmpty(List list) {
        return Objects.nonNull(list) && list.size() > 0;
    }

    public static boolean notContains(List list, Object obj) {
        return !list.contains(obj);
    }
}
