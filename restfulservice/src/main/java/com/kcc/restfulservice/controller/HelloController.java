package com.kcc.restfulservice.controller;

import com.kcc.restfulservice.bean.HelloWorldBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloController {
    
    // 다국어 처리를 위한 용도
    @Autowired
    private MessageSource messageSource;

    // http://localhost:8081/hello-world
    @GetMapping("/hello-world")
    public String helloWorld() {
        return "Hello World!!!";
    }

    // http://localhost:8081/hello-world-bean
    @GetMapping("/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("Hello World Bean!!!");
    }

    // http://localhost:8081/hello-world-bean/kim
    @GetMapping("/hello-world-bean/{name}")
    public HelloWorldBean helloWorldBean2(@PathVariable String name) {
        return new HelloWorldBean(String.format("Hello World Bean: %s", name));
    }

    // http://localhost:8081/hello-world-international
    @GetMapping("/hello-world-international")
    public String helloWorldInternational(
        @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        return messageSource.getMessage("greeting.message", null, locale);
    }
}
