package com.arkonrive.springmyadmin.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class CookieUtil {
    /**
     * 根据名字获取cookie
     * @param request
     * @param name cookie名字
     * @return
     */
    public static Cookie getCookieByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = ReadCookieMap(request);
        return cookieMap.getOrDefault(name, null);
    }

    /**
     * 将cookie封装到Map里面
     * @param request
     * @return
     */
    private static Map<String,Cookie> ReadCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    public static void addCookie(HttpServletResponse response, String name, String value, int expire) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(expire);
        cookie.setDomain("arkonrive.site");
        response.addCookie(cookie); // 传递对象时是传引用
    }

    public static void editCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, int expire){
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals(name)) {
                    cookie.setValue(value);
                    cookie.setMaxAge(expire);// 设置为30min
                    response.addCookie(cookie);
                    break;
                }
            }
        } else {
            addCookie(response, name, value, expire);
        }
    }

    public static String[] getUserInfo(HttpServletRequest request) {
        Cookie usernameCookie, passwordCookie;
        usernameCookie = getCookieByName(request, "username");
        passwordCookie = getCookieByName(request, "password");
        if (usernameCookie == null || passwordCookie == null) { return null; }
        String username = usernameCookie.getValue();
        String password = passwordCookie.getValue();
        return new String[]{username, password};
    }
}
