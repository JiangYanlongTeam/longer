package com.longer.util;

import java.io.File;

public class Util {

    public static boolean judeDirExists(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                return true;
            } else {
                return false;
            }
        } else {
            file.mkdir();
        }
        return true;
    }
}
