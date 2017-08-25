package com.xun.testinstall;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadTask;
import com.xun.testinstall.utils.ApkController;

import commlib.xun.com.commlib.handler.CommWeakHandler;
import commlib.xun.com.commlib.thread.CommThreadPool;

public class MainActivity extends AppCompatActivity {
    private static final String DOWNLOAD_URL = "http://s.same.com/down_mqtt_1_240_v1.2.3_110.apk";
    private static final String APK_PATH = Environment.getExternalStorageDirectory() + "/same.apk";
    private static final String TARGET_PACKAGE_NAME = "ohsame.wawalive";
    private static final String TARGET_CLASS_NAME = "ohsame.wawalive.WelcomActivity";

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private Button installBtn, startAppBtn, downloadAppBtn;
    private LinearLayout progressBarLayout;
    private TextView progressBarTx;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            String msgString = (String) msg.obj;
            Log.d("kkkkkkkk", "msgString --> " + msgString);
            if (msgString != null && msgString.equals("gone")) {
                Log.d("kkkkkkkk", "progressBarLayout gone");
                progressBarLayout.setVisibility(View.GONE);
                return;
            }
            progressBarTx.setText((String) msg.obj);
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Aria.download(this).register();
        verifyStoragePermissions(this);

        installBtn = (Button) findViewById(R.id.install_btn);
        installBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSInstall(APK_PATH);
            }
        });
        startAppBtn = (Button) findViewById(R.id.startapp_btn);
        startAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startOpenApp(TARGET_PACKAGE_NAME, TARGET_CLASS_NAME);
            }
        });
        downloadAppBtn = (Button) findViewById(R.id.download_app);
        downloadAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBarLayout.setVisibility(View.VISIBLE);
                startDownload(DOWNLOAD_URL, APK_PATH);
            }
        });
        progressBarLayout = (LinearLayout) findViewById(R.id.progressBar_layout);
        progressBarTx = (TextView) findViewById(R.id.progressBarTx);
    }

    private void startOpenApp(final String packageName, final String activityName) {
        CommThreadPool.poolExecute(new Runnable() {
            @Override
            public void run() {
                if (ApkController.hasRootPerssion()) {
                    Log.d("kkkkkkkk", "has RootPerssion");
                    Log.d("kkkkkkkk", "start open app");
                    sendMsgToHandler("start open app");
                    boolean isSuc = ApkController.startApp(packageName, activityName);
                    Log.d("kkkkkkkk", "isSuc --> " + isSuc);
                    if (!isSuc) {
                        Log.d("kkkkkkkk", "start open app2");
                        sendMsgToHandler("start open app2");
                        ApkController.startApp2(getApplicationContext(), packageName, activityName);
                    }
                } else {
                    sendMsgToHandler("has not RootPerssion");
                    Log.d("kkkkkkkk", "has not RootPerssion");
                }
                sendMsgToHandler("gone");
            }
        });
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
                if (ApkController.hasRootPerssion()) {
                    Log.d("kkkkkkkk", "has RootPerssion");
                    Log.d("kkkkkkkk", "start install");
                    sendMsgToHandler("start install");
                    boolean isSuc = ApkController.install(apkPath);
                    Log.d("kkkkkkkk", "isSuc --> " + isSuc);
                    startOpenApp(TARGET_PACKAGE_NAME, TARGET_CLASS_NAME);
                } else {
                    sendMsgToHandler("has not RootPerssion");
                    Log.d("kkkkkkkk", "has not RootPerssion");
                    sendMsgToHandler("gone");
                }
            }
        });
    }

    private void sendMsgToHandler(String msg) {
        Message message = Message.obtain();
        message.obj = msg;
        handler.sendMessage(message);
    }

    private void startDownload(String downloadUrl, String downloadPath) {
        Aria.download(this)
                .load(downloadUrl)     //读取下载地址
                .setDownloadPath(downloadPath)    //设置文件保存的完整路径
                .start();   //启动下载
    }

    @Download.onPre(DOWNLOAD_URL)
    protected void onPre(DownloadTask task) {
        Log.d("kkkkkkkk", "onPre");
    }

    @Download.onTaskStart
    void taskStart(DownloadTask task) {
        Log.d("kkkkkkkk", "taskStart");
    }

    @Download.onTaskRunning(DOWNLOAD_URL)
    protected void running(DownloadTask task) {
        progressBarTx.setText("downloading taskRunning --> " + task.getPercent());
        Log.d("kkkkkkkk", "taskRunning --> " + task.getPercent());
    }

    @Download.onTaskResume(DOWNLOAD_URL)
    void taskResume(DownloadTask task) {
        Log.d("kkkkkkkk", "taskResume");
    }

    @Download.onTaskStop(DOWNLOAD_URL)
    void taskStop(DownloadTask task) {
        Log.d("kkkkkkkk", "taskStop");
    }

    @Download.onTaskCancel(DOWNLOAD_URL)
    void taskCancel(DownloadTask task) {
        Log.d("kkkkkkkk", "taskCancel");
    }

    @Download.onTaskFail(DOWNLOAD_URL)
    void taskFail(DownloadTask task) {
        Log.d("kkkkkkkk", "taskFail");
        progressBarLayout.setVisibility(View.GONE);
    }

    @Download.onTaskComplete(DOWNLOAD_URL)
    void taskComplete(DownloadTask task) {
        progressBarTx.setText("taskComplete");
        Log.d("kkkkkkkk", "taskComplete");
        startSInstall(APK_PATH);
    }

    @Download.onNoSupportBreakPoint(DOWNLOAD_URL)
    public void onNoSupportBreakPoint(DownloadTask task) {
        Log.d("kkkkkkkk", "onNoSupportBreakPoint");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacks(null);
        }
    }

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to
     * grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }
}
