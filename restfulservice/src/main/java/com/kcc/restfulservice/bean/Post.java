package com.kcc.restfulservice.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {  // 기본적으로 자바의 클래스 이름을 따라감.
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)  // id 값 자동 증가
    private Integer id;  // Primary key 역할을 함.
    private String description;
    private Integer user_id;  // mybatis일때 추가 함.
    
    //@ManyToOne(fetch = FetchType.LAZY)  // 현 클래스 기준 TO 연결할 클래스 (지연 로딩을 설정하여, User 엔티티가 실제로 필요할 때까지 데이터를 로딩하지 않습니다. 이는 불필요한 조회를 방지하고 성능을 향상시키는 데 도움)
    //@JsonIgnore  // 개별적으로 민감한 데이터를 보이지 않게 처리
    //private User user;
}
