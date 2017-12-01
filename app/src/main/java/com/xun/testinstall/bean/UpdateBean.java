package com.xun.testinstall.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xunwang on 2017/8/29.
 */

public class UpdateBean implements Serializable {

    /**
     * code : 0
     * msg : ok
     * data : {"versionCode":1001002,"download":[{"mqtt":"115.159.201.100","url":"http://ogd7odgy8.bkt.clouddn.com/wawa-1001002-171201-1.apk"}],"is_force":false,"special":[11,1],"selfCode":1,"selfDownload":""}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * versionCode : 1001002
         * download : [{"mqtt":"115.159.201.100","url":"http://ogd7odgy8.bkt.clouddn.com/wawa-1001002-171201-1.apk"}]
         * is_force : false
         * special : [11,1]
         * selfCode : 1
         * selfDownload :
         */

        private int versionCode;
        private boolean is_force;
        private int selfCode;
        private String selfDownload;
        private List<DownloadBean> download;
        private List<Integer> special;

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public boolean isIs_force() {
            return is_force;
        }

        public void setIs_force(boolean is_force) {
            this.is_force = is_force;
        }

        public int getSelfCode() {
            return selfCode;
        }

        public void setSelfCode(int selfCode) {
            this.selfCode = selfCode;
        }

        public String getSelfDownload() {
            return selfDownload;
        }

        public void setSelfDownload(String selfDownload) {
            this.selfDownload = selfDownload;
        }

        public List<DownloadBean> getDownload() {
            return download;
        }

        public void setDownload(List<DownloadBean> download) {
            this.download = download;
        }

        public List<Integer> getSpecial() {
            return special;
        }

        public void setSpecial(List<Integer> special) {
            this.special = special;
        }

        public static class DownloadBean {
            /**
             * mqtt : 115.159.201.100
             * url : http://ogd7odgy8.bkt.clouddn.com/wawa-1001002-171201-1.apk
             */

            private String mqtt;
            private String url;

            public String getMqtt() {
                return mqtt;
            }

            public void setMqtt(String mqtt) {
                this.mqtt = mqtt;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
