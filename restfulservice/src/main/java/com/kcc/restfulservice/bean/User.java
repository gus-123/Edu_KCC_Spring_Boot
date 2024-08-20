package com.kcc.restfulservice.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

//@Entity
//@Table(name = "users")  // user라는 예약어가 DB에 존재하기 때문에 users로 테이블 이름을 변경 해 줌.
@Data
//@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"password", "ssn"})  // 전체적으로 민감한 데이터를 보이지 않게 처리 - 2번 방법
public class User {
    //@Id
    //@GeneratedValue
    private Integer id;
    
    @Schema(title = "사용자 이름", description = "사용자 이름을 입력하세요.")
    @Size(min = 2, message = "Name은 2글자 이상 입력해 주세요")
    private String name;

    @Schema(title = "사용자 등록일", description = "사용자 등록일을 입력하세요.")
    @Past(message = "등록일은 미래 날짜를 입력하실 수 없습니다.")
    private Date joinDate;

    //@JsonIgnore
    // 개별적으로 민감한 데이터를 보이지 않게 처리 - 1번 방법
    //@Pattern(regexp = "[0-9a-zA-Z]*", message = "특수문자 금지")
    private String password;
    //@JsonIgnore
    // 개별적으로 민감한 데이터를 보이지 않게 처리 - 1번 방법
    private String ssn;  // 주민번호

    // Post 엔티티에 User를 참조하는 user 필드가 존재하며, 양방향 관계에서 외래 키가 위치할 필드를 지정 (즉, Post 테이블에 user_id라는 외래 키가 존재하고, 이 외래 키를 통해 User 테이블과 연결)
    //@OneToMany(mappedBy = "user")  // mapping 한 테이블의 이름을 따름(user)
    private List<Post> posts;

    // @AllArgsConstructor하면 생성자를 만들어주나 'List<Post> posts'는 필요가 없으므로 어노테이션을 쓰지 않음.
    public User(Integer id, String name, Date joinDate, String password, String ssn) {
        this.id = id;
        this.name = name;
        this.joinDate = joinDate;
        this.password = password;
        this.ssn = ssn;
    }
}
