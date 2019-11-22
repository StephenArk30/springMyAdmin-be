package com.arkonrive.springmyadmin.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    public static void log(Object object) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss.SS");
        Date date = new Date();
        System.out.println(String.format("%s  LOG  ---  :  [%s]", sdf.format(date), object));
    }
}
