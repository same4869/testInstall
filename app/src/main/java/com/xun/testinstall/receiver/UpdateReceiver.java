package com.xun.testinstall.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 收到这个广播后，下载最新的APP到本地
 * Created by xunwang on 2017/8/29.
 */

public class UpdateReceiver extends BroadcastReceiver {
    private static final String REQUEST_BODY_KEY = "request_body_key";
    public static final String REQUEST_UPDATE_REMOTE_ACTION = "requset.update.remote.action";
    private static final String REQUEST_INSTALL_ACTION = "requset.install.action";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (REQUEST_UPDATE_REMOTE_ACTION.equals(intent.getAction())) {
            String bodyString = intent.getStringExtra(REQUEST_BODY_KEY);
            Log.d("kkkkkkkk", "收到需要下载app的请求 bodyString --》 " + bodyString);
            //TODO 如果收到这个广播则开始下载新版本APP，下载完成需要有一个全局标志位标志有新APP待安装
        } else if (REQUEST_INSTALL_ACTION.equals(intent.getAction())) {
            Log.d("kkkkkkkk", "收到需要安装app的请求");
            //TODO 安装后全局标志位要执回来，如果当前没有待安装的APP，则忽略本次消息
        }
    }
}
