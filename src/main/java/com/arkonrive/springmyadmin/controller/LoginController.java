package com.arkonrive.springmyadmin.controller;

import com.alibaba.fastjson.JSONObject;
import com.arkonrive.springmyadmin.utils.CookieUtil;
import com.arkonrive.springmyadmin.utils.SQLBase;
import com.arkonrive.springmyadmin.utils.Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/login")
public class LoginController {

    @RequestMapping(value="", method= RequestMethod.POST)
    @ResponseBody
    public JSONObject login(HttpServletRequest request, HttpServletResponse response, @RequestBody String data){
        String username, password;
        username = JSONObject.parseObject(data).getString("username");
        password = JSONObject.parseObject(data).getString("password");

        boolean auth = SQLBase.authUser(username, password);
        if (auth) {
            if (CookieUtil.getCookieByName(request, "username") == null) {
                CookieUtil.addCookie(response, "username", username, 30 * 60); // 没有就添加
            } else {
                CookieUtil.editCookie(request, response, "username", username, 30 * 60); // 有就修改
            }
            if (CookieUtil.getCookieByName(request, "password") == null) {
                CookieUtil.addCookie(response, "password", password, 30 * 60);
            } else {
                CookieUtil.editCookie(request, response, "password", password, 30 * 60);
            }
            return Util.add2JSON("res", true);
        } else return Util.add2JSON("err", false);
    }
}
