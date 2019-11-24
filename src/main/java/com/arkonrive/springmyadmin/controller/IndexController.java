package com.arkonrive.springmyadmin.controller;

import com.arkonrive.springmyadmin.utils.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String apiMessage(Model m) {
        m.addAttribute("message", "SpringMyAdmin API");
        Logger.log(m);
        return "index";
    }
}
