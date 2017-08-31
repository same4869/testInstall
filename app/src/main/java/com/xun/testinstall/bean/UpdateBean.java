package com.xun.testinstall.bean;

import java.io.Serializable;

/**
 * Created by xunwang on 2017/8/29.
 */

public class UpdateBean implements Serializable {
    /**
     * code : 0
     * data : {"versionCode":1000000,"download":"http://s.same.com/wawa-android-testupdate.apk","is_force":false}
     */

    private int code;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * versionCode : 1000000
         * download : http://s.same.com/wawa-android-testupdate.apk
         * is_force : false
         */

        private int versionCode;
        private String download;
        private boolean is_force;

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public String getDownload() {
            return download;
        }

        public void setDownload(String download) {
            this.download = download;
        }

        public boolean isIs_force() {
            return is_force;
        }

        public void setIs_force(boolean is_force) {
            this.is_force = is_force;
        }
    }
}
