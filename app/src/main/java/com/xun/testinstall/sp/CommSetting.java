package com.xun.testinstall.sp;

/**
 * Created by xunwang on 2017/8/29.
 */

public class CommSetting {
    public static final String SETTING = "setting";
    private static final String IS_APP_SHOULD_INSTALL = "is_app_should_install";

    public static boolean getIsAppShouldInstall() {
        return PrefsMgr.getBoolean(SETTING, IS_APP_SHOULD_INSTALL, false);
    }

    public static void setIsAppShouldInstall(boolean isAppShouldInstall) {
        PrefsMgr.putBoolean(SETTING, IS_APP_SHOULD_INSTALL, isAppShouldInstall);
    }
}
