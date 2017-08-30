package com.xun.testinstall.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xunwang on 2017/8/29.
 */

public class UpdateBean implements Serializable {

    /**
     * code : 0
     * data : {"version":"0.1","download":"http://same.com","special":[1,2]}
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

    public static class DataBean implements Serializable {
        /**
         * version : 0.1
         * download : http://same.com
         * special : [1,2]
         */

        private String version;
        private String download;
        private List<Integer> special;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getDownload() {
            return download;
        }

        public void setDownload(String download) {
            this.download = download;
        }

        public List<Integer> getSpecial() {
            return special;
        }

        public void setSpecial(List<Integer> special) {
            this.special = special;
        }
    }
}
