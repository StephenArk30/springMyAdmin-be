package com.arkonrive.springmyadmin.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class Attribute {
    private String COLUMN_NAME;
    private String COLUMN_TYPE;
    private String COLUMN_DEFAULT;
    private String COLUMN_KEY;
    private String IS_NULLABLE;
    private String COLUMN_COMMENT;

    Attribute(String column_name, String column_type, String column_default, String column_key, String is_nullable, String column_comment) {
        COLUMN_NAME = column_name;
        COLUMN_TYPE = column_type;
        COLUMN_DEFAULT = column_default;
        COLUMN_KEY = column_key;
        IS_NULLABLE = is_nullable;
        COLUMN_COMMENT = column_comment;
    }

    Attribute(JSONObject jsonObject) {
        for (String key : jsonObject.keySet()) {
            String value = jsonObject.getString(key);
            if (value.toUpperCase().compareTo("COLUMN_NAME") == 0) {
                COLUMN_NAME = value;
            } else if (value.toUpperCase().compareTo("COLUMN_TYPE") == 0) {
                COLUMN_TYPE = value;
            } else if (value.toUpperCase().compareTo("COLUMN_DEFAULT") == 0) {
                COLUMN_DEFAULT = value;
            } else if (value.toUpperCase().compareTo("COLUMN_KEY") == 0) {
                COLUMN_KEY = value;
            } else if (value.toUpperCase().compareTo("IS_NULLABLE") == 0) {
                IS_NULLABLE = value;
            } else if (value.toUpperCase().compareTo("COLUMN_COMMENT") == 0) {
                COLUMN_COMMENT = value;
            }
        }
    }

    public String getTypeName() {
        if (COLUMN_TYPE.indexOf("(") > 0)
            return COLUMN_TYPE.substring(0, COLUMN_TYPE.indexOf("("));
        else return COLUMN_TYPE;
    }

    @Override
    public String toString() {
        return COLUMN_NAME + " "
        + getTypeName() + " "
        + COLUMN_DEFAULT + " "
        + COLUMN_KEY + " "
        + IS_NULLABLE + " "
        + COLUMN_COMMENT;
    }

    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("COLUMN_NAME", COLUMN_NAME);
        jsonObject.put("COLUMN_TYPE", COLUMN_TYPE);
        jsonObject.put("COLUMN_DEFAULT", COLUMN_DEFAULT);
        jsonObject.put("COLUMN_KEY", COLUMN_KEY);
        jsonObject.put("IS_NULLABLE", IS_NULLABLE);
        jsonObject.put("COLUMN_COMMENT", COLUMN_COMMENT);
        return jsonObject;
    }

    public String getCOLUMN_NAME() {
        return COLUMN_NAME;
    }

    public String getCOLUMN_TYPE() {
        return COLUMN_TYPE;
    }

    public String getCOLUMN_COMMENT() {
        return COLUMN_COMMENT;
    }

    public String getCOLUMN_DEFAULT() {
        return COLUMN_DEFAULT;
    }

    public String getCOLUMN_KEY() {
        return COLUMN_KEY;
    }

    public String getIS_NULLABLE() {
        return IS_NULLABLE;
    }
}
