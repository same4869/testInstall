package com.xun.testinstall.utils;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.xun.testinstall.config.Constants.IS_DEBUG;
import static com.xun.testinstall.config.Constants.IS_SAVE_LOG_TO_LOCAL;

/**
 * Created by xunwang on 2017/8/28.
 */

public class LogUtil {
    private static String MYLOG_PATH_SDCARD_DIR = Environment.getExternalStorageDirectory().getAbsolutePath();
    private static String MYLOGFILEName = "AppUpdateLog.txt";// 本类输出的日志文件名称
    private static SimpleDateFormat myLogSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 日志的输出格式
    private static SimpleDateFormat logfile = new SimpleDateFormat("yyyy-MM-dd");// 日志文件格式

    public static void d(String tag, String msg) {
        if (IS_DEBUG) {
            Log.d(tag, msg);
            if (IS_SAVE_LOG_TO_LOCAL) {
                writeLogtoFile(tag, msg);
            }
        }
    }

    /**
     * 打开日志文件并写入日志
     *
     * @return
     **/
    private static void writeLogtoFile(String tag, String text) {// 新建或打开日志文件
        Date nowtime = new Date();
        String needWriteFiel = logfile.format(nowtime);
        String needWriteMessage = myLogSdf.format(nowtime)
                + "    " + tag + "    " + text;
        File file = new File(MYLOG_PATH_SDCARD_DIR, needWriteFiel
                + MYLOGFILEName);
        try {
            FileWriter filerWriter = new FileWriter(file, true);//后面这个参数代表是不是要接上文件中原来的数据，不进行覆盖
            BufferedWriter bufWriter = new BufferedWriter(filerWriter);
            bufWriter.write(needWriteMessage);
            bufWriter.newLine();
            bufWriter.close();
            filerWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
