package com.kcc.restfulservice.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(value = {"password", "ssn"})  // 전체적으로 민감한 데이터를 보이지 않게 처리 - 2번 방법
public class User {
    private Integer id;
    @Size(min = 2, message = "Name은 2글자 이상 입력해 주세요")
    private String name;
    @Past(message = "등록일은 미래 날짜를 입력하실 수 없습니다.")
    private Date joinDate;

    //@JsonIgnore
    // 개별적으로 민감한 데이터를 보이지 않게 처리 - 1번 방법
    private String password;
    //@JsonIgnore
    // 개별적으로 민감한 데이터를 보이지 않게 처리 - 1번 방법
    private String ssn;  // 주민번호
}
