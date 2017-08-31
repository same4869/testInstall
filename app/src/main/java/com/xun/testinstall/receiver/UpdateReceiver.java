package com.xun.testinstall.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.xun.testinstall.MainActivity;
import com.xun.testinstall.bean.UpdateBean;
import com.xun.testinstall.json.JSONFormatExcetion;
import com.xun.testinstall.json.JSONToBeanHandler;
import com.xun.testinstall.service.AppInstallService;
import com.xun.testinstall.sp.CommSetting;

/**
 * 收到这个广播后，下载最新的APP到本地
 * Created by xunwang on 2017/8/29.
 */

public class UpdateReceiver extends BroadcastReceiver {
    private static final String REQUEST_BODY_KEY = "request_body_key";
    private static final String REQUEST_UPDATE_REMOTE_ACTION = "requset.update.remote.action";
    private static final String REQUEST_INSTALL_ACTION = "requset.install.remote.action";
    public static final String DOWNLOAD_INFO_KEY = "download_info_key";
    public static final String DOWNLOAD_OR_INSTALL_KEY = "download_or_install_key";//1是下载，2是安装

    @Override
    public void onReceive(Context context, Intent intent) {
        if (REQUEST_UPDATE_REMOTE_ACTION.equals(intent.getAction())) {
            String bodyString = intent.getStringExtra(REQUEST_BODY_KEY);
            Log.d("kkkkkkkk", "收到需要下载app的请求 bodyString --》 " + bodyString);
            //如果收到这个广播则开始下载新版本APP，下载完成需要有一个全局标志位标志有新APP待安装

            Intent downloadIntent = new Intent(context, AppInstallService.class);
            downloadIntent.putExtra(DOWNLOAD_INFO_KEY, bodyString);
            downloadIntent.putExtra(DOWNLOAD_OR_INSTALL_KEY, 1);
            context.startService(downloadIntent);

        } else if (REQUEST_INSTALL_ACTION.equals(intent.getAction())) {
            Log.d("kkkkkkkk", "收到需要安装app的请求 CommSetting.getIsAppShouldInstall() --> " + CommSetting.getIsAppShouldInstall());
            //安装后全局标志位要执回来，如果当前没有待安装的APP，则忽略本次消息

            if (CommSetting.getIsAppShouldInstall()) {
                Intent downloadIntent = new Intent(context, AppInstallService.class);
                downloadIntent.putExtra(DOWNLOAD_OR_INSTALL_KEY, 2);
                context.startService(downloadIntent);
            }
        }
    }
}
