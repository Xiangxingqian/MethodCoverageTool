package com.ehb.testing;

import com.ehb.manifest.ProcessManifest;
import com.ehb.soot.SootConfig;
import com.ehb.testing.config.AndroidConfig;
import com.ehb.testing.config.Global;
import com.ehb.testing.instrument.builder.TestUtil;
import com.ehb.testing.instrument.transformer.ApkBodyTransformer;
import com.ehb.testing.util.CleanTask;
import com.ehb.testing.util.FileUtil;
import soot.PackManager;
import soot.Transform;

import java.util.Map;
import java.util.TreeMap;

import static com.ehb.testing.config.Global.indexToMethod;

public class Main {

    public static void main(String[] args) {
        CleanTask.cleanOutput();
        parseXML(AndroidConfig.APK_PATH);
        SootConfig.initSoot(AndroidConfig.APK_PATH);
        instrumentApk();
    }

    private static void parseXML(String apk) {
        ProcessManifest processMan = new ProcessManifest();
        processMan.loadManifestFile(apk);
        processMan.addToGlobal();
    }

    private static void instrumentApk() {
        String params[] = {"-android-jars", AndroidConfig.ANDROID_PLATFORM, "-process-dir", AndroidConfig.APK_PATH};
        ApkBodyTransformer t = new ApkBodyTransformer();
        PackManager.v().getPack("jtp").add(new Transform("jtp.myInstrumenter", t));
        soot.Main.main(params);
        FileUtil.writeFile(AndroidConfig.OUTPUT_ACTIVITY, Global.v().toString());
        String methodCount = "Method Count: " + TestUtil.METHOD + "\n\n";
        Map treeMap = new TreeMap();
        treeMap.putAll(indexToMethod);
        FileUtil.writeFile(AndroidConfig.OUTPUT_METHODS, methodCount + treeMap);
    }
}
