package com.xun.testinstall.service;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadTask;
import com.arialyy.aria.util.WeakHandler;
import com.xun.testinstall.utils.ApkInstallUtil;
import com.xun.testinstall.utils.LogUtil;

import commlib.xun.com.commlib.thread.CommThreadPool;

/**
 * 静默下载安装并打开目标APP
 * Created by xunwang on 2017/8/28.
 */

public class AppInstallService extends Service {
    private static final String DOWNLOAD_URL = "http://s.same.com/down_mqtt_1_240_v1.2.3_110.apk";
    private static final String APK_PATH = Environment.getExternalStorageDirectory() + "/same.apk";
    private static final String TARGET_PACKAGE_NAME = "ohsame.wawalive";
    private static final String TARGET_CLASS_NAME = "ohsame.wawalive.WelcomActivity";

    private boolean isUpdating = false;

    Handler.Callback callback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            String msgString = (String) message.obj;
            LogUtil.d("kkkkkkkk", "msgString --> " + msgString);
            if (msgString != null && msgString.equals("gone")) {
                LogUtil.d("kkkkkkkk", "progressBarLayout gone");
                isUpdating = false;
                return false;
            }
            return false;
        }
    };

    private WeakHandler weakHandler = new WeakHandler(callback);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d("kkkkkkkk", "AppInstallService onCreate");
        Aria.download(this).register();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.d("kkkkkkkk", "AppInstallService onStartCommand");
        if (!isUpdating) {
            startDownload(DOWNLOAD_URL, APK_PATH);
        }
        return START_STICKY;
    }

    /**
     * 开始静默安装
     *
     * @param apkPath
     */
    private void startSInstall(final String apkPath) {
        CommThreadPool.poolExecute(new Runnable() {
            @Override
            public void run() {
                if (ApkInstallUtil.hasRootPerssion()) {
                    LogUtil.d("kkkkkkkk", "has RootPerssion");
                    LogUtil.d("kkkkkkkk", "start install");
                    sendMsgToHandler("start install");
                    boolean isSuc = ApkInstallUtil.install(apkPath);
                    LogUtil.d("kkkkkkkk", "isSuc --> " + isSuc);
                    startOpenApp(TARGET_PACKAGE_NAME, TARGET_CLASS_NAME);
                } else {
                    sendMsgToHandler("has not RootPerssion");
                    LogUtil.d("kkkkkkkk", "has not RootPerssion");
                    sendMsgToHandler("gone");
                    isUpdating = false;
                }
            }
        });
    }

    private void sendMsgToHandler(String msg) {
        Message message = Message.obtain();
        message.obj = msg;
        weakHandler.sendMessage(message);
    }

    private void startDownload(String downloadUrl, String downloadPath) {
        isUpdating = true;
        Aria.download(this)
                .load(downloadUrl)     //读取下载地址
                .setDownloadPath(downloadPath)    //设置文件保存的完整路径
                .start();   //启动下载
    }

    @Download.onPre(DOWNLOAD_URL)
    protected void onPre(DownloadTask task) {
        LogUtil.d("kkkkkkkk", "onPre");
    }

    @Download.onTaskStart
    void taskStart(DownloadTask task) {
        LogUtil.d("kkkkkkkk", "taskStart");
    }

    @Download.onTaskRunning(DOWNLOAD_URL)
    protected void running(DownloadTask task) {
        LogUtil.d("kkkkkkkk", "taskRunning --> " + task.getPercent());
    }

    @Download.onTaskResume(DOWNLOAD_URL)
    void taskResume(DownloadTask task) {
        LogUtil.d("kkkkkkkk", "taskResume");
    }

    @Download.onTaskStop(DOWNLOAD_URL)
    void taskStop(DownloadTask task) {
        LogUtil.d("kkkkkkkk", "taskStop");
    }

    @Download.onTaskCancel(DOWNLOAD_URL)
    void taskCancel(DownloadTask task) {
        LogUtil.d("kkkkkkkk", "taskCancel");
        isUpdating = false;
    }

    @Download.onTaskFail(DOWNLOAD_URL)
    void taskFail(DownloadTask task) {
        LogUtil.d("kkkkkkkk", "taskFail");
        isUpdating = false;
    }

    @Download.onTaskComplete(DOWNLOAD_URL)
    void taskComplete(DownloadTask task) {
        LogUtil.d("kkkkkkkk", "taskComplete");
        startSInstall(APK_PATH);
    }

    @Download.onNoSupportBreakPoint(DOWNLOAD_URL)
    public void onNoSupportBreakPoint(DownloadTask task) {
        LogUtil.d("kkkkkkkk", "onNoSupportBreakPoint");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isUpdating = false;
        if (weakHandler != null) {
            weakHandler.removeCallbacks(null);
        }
    }

    /**
     * 打开目标app
     *
     * @param packageName
     * @param activityName
     */
    private void startOpenApp(final String packageName, final String activityName) {
        CommThreadPool.poolExecute(new Runnable() {
            @Override
            public void run() {
                if (ApkInstallUtil.hasRootPerssion()) {
                    LogUtil.d("kkkkkkkk", "has RootPerssion");
                    LogUtil.d("kkkkkkkk", "start open app");
                    sendMsgToHandler("start open app");
                    boolean isSuc = ApkInstallUtil.startApp(packageName, activityName);
                    LogUtil.d("kkkkkkkk", "isSuc --> " + isSuc);
                    if (!isSuc) {
                        LogUtil.d("kkkkkkkk", "start open app2");
                        sendMsgToHandler("start open app2");
                        ApkInstallUtil.startApp2(getApplicationContext(), packageName, activityName);
                    }
                } else {
                    sendMsgToHandler("has not RootPerssion");
                    LogUtil.d("kkkkkkkk", "has not RootPerssion");
                }
                sendMsgToHandler("gone");
            }
        });
    }
}
