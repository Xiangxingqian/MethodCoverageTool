package com.ehb.soot;

import com.ehb.testing.config.AndroidConfig;
import com.ehb.testing.config.Global;
import com.ehb.testing.instrument.builder.TestUtil;
import soot.Scene;
import soot.SootClass;
import soot.options.Options;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangxingqian on 2017/12/28.
 */
public class SootConfig {

    public static void initSoot(String apkPath) {
        // mac
        Options.v().set_soot_classpath(apkPath + ":" + "lib/rt.jar:" + "lib/jce.jar:" + "lib/tools.jar:"
                + "lib/android.jar:" + "lib/android-support-v4.jar:" + "out/production/MethodCoverageTool"); // takes 2 hour to debug this...

//		Options.v().set_validate(true);
        Options.v().set_src_prec(Options.src_prec_apk);
        Options.v().set_output_format(Options.output_format_dex);
//		Options.v().set_output_format(Options.output_format_jimple);
        Options.v().set_output_dir(AndroidConfig.OUTPUT);
        Options.v().set_allow_phantom_refs(true);
        Options.v().set_process_multiple_dex(true);
        Options.v().set_android_api_version(23);
        Options.v().set_include_all(true);
//        Options.v().set_whole_program(true);
        for (String className : Global.v().getActivities()) {
            Scene.v().addBasicClass(className, SootClass.BODIES);
        }

        for (String appClass : appClasses) {
            Scene.v().addBasicClass(appClass, SootClass.BODIES);
        }

        Scene.v().loadNecessaryClasses();
    }

    public static List<String> appClasses = new ArrayList<>();

    static {
        appClasses.add(TestUtil.class.getName());
    }

}
