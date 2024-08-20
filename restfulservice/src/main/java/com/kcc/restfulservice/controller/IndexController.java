package com.kcc.restfulservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/test")
    public String test(Model model) {
        model.addAttribute("message", "Hello JSP!!!");
        return "test";
    }
}
