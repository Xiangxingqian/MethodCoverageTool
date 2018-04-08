package com.ehb.testing.config;

/**
 * Created by xiangxingqian on 2017/12/28.
 */
public class AndroidConfig {

    public final static String APK_NAME = "debug";
    public final static String APK_PATH = String.format("benchmark/%s.apk", APK_NAME);
    public static String ANDROID_PLATFORM = System.getenv().get("ANDROID_HOME") + "/platform";
    public static final String OUTPUT = String.format("output/%s", APK_NAME);

    public static final String OUTPUT_ACTIVITY = String.format("output/%s/%s_activity.txt", APK_NAME, APK_NAME);
    public static final String OUTPUT_METHODS = String.format("output/%s/%s_method.txt", APK_NAME, APK_NAME);
//    public static final String PACKAGE_NAME = "com.docrab.pro";

//    public static final List<String> BASE_ACTIVITY_LIST = new ArrayList<>();
//    static {
//        BASE_ACTIVITY_LIST.add("com.docrab.pro.ui.base.BaseActivity");
//        BASE_ACTIVITY_LIST.add("com.rabbit.doctor.ui.BaseActivity");
//    }
}
