package com.ehb.testing.util;

import com.ehb.testing.config.AndroidConfig;

import java.io.File;

/**
 * Created by xiangxingqian on 2018/1/10.
 */
public class CleanTask {

    public static void cleanOutput() {
        deleteDirectory(AndroidConfig.OUTPUT);
    }

    public static void cleanFolder(String fileName) {
        try {
            File file = new File(fileName);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {

        }
    }

    public static void cleanFolder(File file) {
        try {
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {

        }
    }

    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.isFile() && file.exists()) {
            Boolean succeedDelete = file.delete();
            if (succeedDelete) {
                return true;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public static boolean deleteDirectory(String dir) {
        if (!dir.endsWith(File.separator)) {
            dir = dir + File.separator;
        }
        File dirFile = new File(dir);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            } else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }
        return true;
    }
}
