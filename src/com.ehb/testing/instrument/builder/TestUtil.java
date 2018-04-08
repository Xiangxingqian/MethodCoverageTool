package com.ehb.testing.instrument.builder;

import android.util.Log;

import java.util.HashSet;
import java.util.Set;

/**
 * strategies for Testing.
 * <p>
 * Created by xiangxingqian on 2017/11/18.
 */
public class TestUtil {

    public static int METHOD;

    public static Set<Integer> methodCount = new HashSet<>();

    public static void addMethodCount(int i) {
        methodCount.add(i);
    }

    public static void printVisitedMethods() {
        Log.e("qian", "Visited method size: " + methodCount.size());
    }
}

