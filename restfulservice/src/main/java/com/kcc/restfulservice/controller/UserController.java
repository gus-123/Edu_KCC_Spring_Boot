package com.kcc.restfulservice.controller;

import com.kcc.restfulservice.bean.User;
import com.kcc.restfulservice.exception.UserNotFoundException;
import com.kcc.restfulservice.service.UserDaoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Iterator;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
//@AllArgsConstructor - 1번 방식

public class UserController {

    //@Autowired - 2번 방식
    private UserDaoService service;

    // 3번 방식
    public UserController(UserDaoService service) {
        this.service = service;
    }

    // http://localhost:8081/users
    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    // http://localhost:8081/users/1
    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id) {
        User user = service.findOne(id);

        if(user == null) {  // 여기서 에러가 날 경우 UserNotFoundException 발생시킴.
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }

        EntityModel entityModel =  EntityModel.of(user);

        // WebMvcLinkBuilder를 static으로 넣으면 linkTo를 import할 필요가 없다.
        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(linkTo.withRel("all-users"));

        return entityModel;
    }

//    @PostMapping("/users")
//    public User createUser(@RequestBody User user) {
//        return service.save(user);
//    }

    @PostMapping("/users")
    /*
    public ResponseEntity<User> createUser(@RequestBody User user):
    이 메서드는 공개 메서드이며, createUser라는 이름을 가지고 있습니다.
    @RequestBody 어노테이션은 요청 본문(body)에 담긴 JSON 데이터를 User 객체 형태로 자동 변환하도록 지정합니다.
    User 클래스는 사용자 정보를 담는 클래스 (이름, 이메일, 등) 라고 예상됩니다.
     */
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        // 클라이언트가 보낸 user 객체를 service라는 객체에 전달하여 저장하는 작업을 수행
        // service 객체는 실제 데이터베이스에 사용자 정보를 저장하는 로직을 가지고 있다.
        User saveUser = service.save(user);
        // 생성된 사용자의 고유 식별자(id)를 이용하여 해당 사용자 정보를 조회할 수 있는 URI(Uniform Resource Identifier)를 만듦
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saveUser.getId())
                .toUri();
        
        // 클라이언트에게 HTTP 응답 코드 201 (Created)를 반환 - 201 코드는 새로운 리소스가 성공적으로 생성되었음을 의미
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        User user = service.deleteById(id);

        if(user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }
    }
}
