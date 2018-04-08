package com.ehb.testing.util;

import com.ehb.testing.instrument.builder.TestUtil;
import soot.Scene;
import soot.SootMethod;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by xiangxingqian on 2018/1/7.
 */
public class SignatureUtil {

    public static List<String> appClasses = new ArrayList<>();

    static {
        appClasses.add(TestUtil.class.getName());
    }

    /**
     * 在多个字符之间插入分隔符
     *
     * @param splitChar
     * @param ss
     * @param
     * @return
     */
    public static String handleStringsStrict(String defaultStr, char splitChar, String... ss) {
        StringBuilder builder = new StringBuilder();
        for (String s1 : ss) {
            if (s1 != null && !s1.trim().equals("") && !s1.startsWith("0")) {
                builder.append(s1);
                builder.append(splitChar);
            }
        }
        String s = builder.toString();
        return notEmpty(s) ? s.substring(0, s.lastIndexOf(splitChar)) : defaultStr;
    }

    public static boolean notEmpty(String s) {
        return s != null && !s.equals("");
    }

    public static void main(String[] args) throws ClassNotFoundException {
        String s = handleStringsStrict("--", '·', "0", "1", "123");

        System.out.println("main Line78 " + s);
    }

    /**
     * Get sootMethod by className and methodName
     * <p>
     * Puzzled by method overwritten? Nope, there only be one method name in class.
     *
     * @param cName
     * @param mName
     * @return
     */
    public static SootMethod getSootMethodSignature(String cName, String mName) {
        return Scene.v().getMethod(getMethodSignature(cName, mName));
    }

    /**
     * Get signature of an existing method
     *
     * @param cName
     * @param mName
     * @return
     */
    public static String getMethodSignature(String cName, String mName) {
        Class<?> aClass = null;
        try {
            aClass = Class.forName(cName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        for (Method method : aClass.getMethods()) {
            if (method.getName().equals(mName)) {
                String params = getParamType(method);
                String returnName = method.getReturnType().getCanonicalName();
                return String.format("<%s: %s %s(%s)>", cName, returnName, mName, params);
            }
        }
        throw new RuntimeException("No such method");
    }

    /**
     * Method SignatureUtil#getMethodSignature(String cName, String mName)
     * return
     * java.lang.String,java.lang.String
     *
     * @param method
     * @return
     */
    private static String getParamType(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        List<String> list = new ArrayList<>();
        for (Class<?> parameterType : parameterTypes) {
            list.add(parameterType.getCanonicalName());
        }
        String listToStr = list.toString().replaceAll("\\s+", "");
        return list.size() > 0 ? listToStr.substring(1, listToStr.length() - 1) : "";
    }
}
