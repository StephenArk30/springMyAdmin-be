package com.arkonrive.springmyadmin.utils;

import com.alibaba.fastjson.JSONObject;

public class Util {
    public static JSONObject add2JSON(String name, Object obj) {
        JSONObject res = new JSONObject();
        res.put(name, obj);
        return res;
    }
}
