package com.xun.testinstall.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

/**
 * @Author:Lijj
 * @Date:2014-4-17上午10:33:54
 * @Todo:TODO
 */
public class JSONToBeanHandler {

    /**
     * 数据源转化为 对象bean
     *
     * @param data
     * @param cls
     * @return
     * @throws JSONFormatExcetion
     */
    public static <T> T fromJsonString(String data, Class<T> cls) throws JSONFormatExcetion {
        try {
            return JSON.parseObject(data, cls);
        } catch (JSONException e) {
            if (null != e) {
                e.printStackTrace();
            }
            throw new JSONFormatExcetion("json format to " + cls.getName() + " exception :" + data);
        }
    }

    /**
     * 对象bean转化为json字符串
     *
     * @param value
     * @return
     * @throws JSONFormatExcetion
     */
    public static String toJsonString(Object value) throws JSONFormatExcetion {
        try {
            return JSON.toJSONString(value);
        } catch (JSONException e) {
            throw new JSONFormatExcetion(value.getClass().getName() + " to json exception");
        }
    }

}
