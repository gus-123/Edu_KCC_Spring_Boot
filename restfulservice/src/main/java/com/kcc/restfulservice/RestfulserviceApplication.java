package com.kcc.restfulservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

// 실행하는 것 만으로 component Scan 됨.
@SpringBootApplication
public class RestfulserviceApplication {

    public static void main(String[] args) {
        //System.out.println("KCC Spring Boot!!!");
        SpringApplication.run(RestfulserviceApplication.class, args);
    }

    // 다국어 처리
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.KOREA);

        return localeResolver;
    }

}
