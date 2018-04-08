package com.ehb.testing.util;

import com.ehb.testing.config.Global;
import soot.*;

import java.util.List;

/**
 * Created by xiangxingqian on 2017/12/29.
 */
public class CheckUtil {

    public static SootClass activity = Scene.v().getSootClass("android.app.Activity");

    /**
     * Check sc is an real activity.
     *
     * @param sc
     */
    public static boolean checkActivity(SootClass sc) {
        if (!Scene.v().getActiveHierarchy().getSuperclassesOf(sc).contains(activity)) {
            System.err.println(sc.getName() + "declared in manifest.xml activity is not an activity");
            return false;
        }
        return true;
    }

    public static boolean checkApplicationClass(SootClass sc) {
        return Scene.v().getApplicationClasses().contains(sc);
    }

    public static boolean checkValidMethod(Body b) {
        SootMethod method = b.getMethod();
        String methodName = method.getName();

        if (b.getMethod().getDeclaringClass().getName().startsWith(Global.v().getPkg())) {

            if (!method.isConcrete()) {
                return false;
            }

            if (avoidMethod(b))
                return false;

            if (methodName.equals("<clinit>") || method.equals("<init>") || method.getSignature().contains("$") || methodName.startsWith("access$")) {
                return false;
            }
            return true;
        } else return false;
    }

    public static boolean avoidMethod(Body b) {
        if (b.getMethod().getDeclaringClass().isInterface())
            return false;
        List<SootClass> superclassesOf = Scene.v().getActiveHierarchy().getSuperclassesOf(b.getMethod().getDeclaringClass());
        for (SootClass sootClass : superclassesOf) {
            if (sootClass.getName().equals("com.rabbit.doctor.ui.data.entity.DRBaseModel"))
                return true;
        }
        return false;
    }
}
