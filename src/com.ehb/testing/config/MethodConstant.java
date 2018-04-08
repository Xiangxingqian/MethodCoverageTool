package com.ehb.testing.config;

import soot.Scene;
import soot.SootMethod;

/**
 * Created by xiangxingqian on 2017/12/29.
 */
public class MethodConstant {

    public final static SootMethod onCreateOptionsMenu = Scene.v().getMethod("<android.app.Activity: boolean onCreateOptionsMenu(android.view.Menu)>");
    public static final SootMethod onDestroy = Scene.v().getMethod(TypeConstant.ON_DESTROY_SIGNATURE);
}
