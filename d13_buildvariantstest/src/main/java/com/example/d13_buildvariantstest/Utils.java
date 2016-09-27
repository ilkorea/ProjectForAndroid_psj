package com.example.d13_buildvariantstest;

/**
 * Created by Log on 2016-09-27.
 */

public class Utils {
    static String getVariantInfo(){
        return String.format("[applicationId] %s\n\n[build type] %s\n\n[flavor] %s\n\n" +
                "[version code] %s\n\n[version name] %s",
                BuildConfig.APPLICATION_ID, BuildConfig.BUILD_TYPE, BuildConfig.FLAVOR,
                BuildConfig.VERSION_CODE, BuildConfig.VERSION_NAME);
    }
}
