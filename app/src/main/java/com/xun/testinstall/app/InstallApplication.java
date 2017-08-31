package com.xun.testinstall.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

/**
 * Created by xunwang on 2017/8/29.
 */

public class InstallApplication extends Application{
    protected static InstallApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static InstallApplication getInstance() {
        return instance;
    }

    public SharedPreferences getCommSharedPreferences(String tbl) {
        return getSharedPreferences(tbl, Context.MODE_PRIVATE);
    }
}
