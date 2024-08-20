package com.kcc.restfulservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 문서 명세서
@OpenAPIDefinition(
        info = @Info(title = "My Restful Service API 명세서",
                    description = "Spring Boot를 개발하는 Rest API 명세서 입니다.",
                    version = "v1.0.0")
)
@Configuration // 설정을 만들때는 꼭 필요
public class NewSwaggerConfig {

    @Bean
    public GroupedOpenApi customTestOpenAPI() {
        String[] paths = {"/users/**", "/admin/**"};

        return GroupedOpenApi.builder()
                .group("일반사용자, 관리자를 위한 API")
                .pathsToMatch(paths)
                .build();
    }
}
