package com.arkonrive.springmyadmin.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Util {
    public static JSONObject add2JSON(String name, Object obj) {
        JSONObject res = new JSONObject();
        res.put(name, obj);
        return res;
    }

    public static List<Attribute> JSONObject2Array(JSONObject attributeJSON) {
        List<Attribute> attributes = new ArrayList<>();
        for (String key : attributeJSON.keySet()) {
            JSONObject attributeJSONJSONObject = attributeJSON.getJSONObject(key);
            attributes.add(new Attribute(attributeJSONJSONObject));
        }

        return attributes;
    }
}
