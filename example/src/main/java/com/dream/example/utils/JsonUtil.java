package com.dream.example.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

/**
 * Description: Gson封装. <br>
 * Date: 2016/4/7 18:05 <br>
 * Author: ysj
 */
public class JsonUtil {
    private static Gson gson;

    static {
        if (gson == null) {
            gson = new Gson();
        }
    }

    private JsonUtil() {
    }

    /**
     * 转成json
     *
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        String jsonString = null;
        if (gson != null) {
            jsonString = gson.toJson(object);
        }
        return jsonString;
    }

    /**
     * 转成bean
     *
     * @param JsonString
     * @param cls
     * @return
     */
    public static <T> T jsonToBean(String JsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(JsonString, cls);
        }
        return t;
    }

    /**
     * 转成list
     *
     * @param JsonString
     * @param cls
     * @return
     */
    public static <T> List<T> jsonToList(String JsonString, Class<T> cls) {
        List<T> list = null;
        if (gson != null) {
            list = gson.fromJson(JsonString, new TypeToken<List<T>>() {
            }.getType());
        }
        return list;
    }

    /**
     * 转成list中有map的
     *
     * @param JsonString
     * @return
     */
    public static <T> List<Map<String, T>> jsonToListMap(String JsonString) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(JsonString,
                    new TypeToken<List<Map<String, T>>>() {
                    }.getType());
        }
        return list;
    }

    /**
     * 转成map的
     *
     * @param JsonString
     * @return
     */
    public static <T> Map<String, T> jsonToMap(String JsonString) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(JsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }
}
