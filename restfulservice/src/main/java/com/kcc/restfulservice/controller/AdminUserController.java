package com.kcc.restfulservice.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.kcc.restfulservice.bean.AdminUser;
import com.kcc.restfulservice.bean.User;
import com.kcc.restfulservice.exception.UserNotFoundException;
import com.kcc.restfulservice.service.UserDaoService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
//@AllArgsConstructor - 1번 방식
@RequestMapping("/admin")
public class AdminUserController {

    //@Autowired - 2번 방식
    private UserDaoService service;

    // 3번 방식
    public AdminUserController(UserDaoService service) {
        this.service = service;
    }

    // http://localhost:8081/admin/users
    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers() {
        List<User> users = service.findAll();
        List<AdminUser> adminUsers = new ArrayList<>();
        AdminUser adminUser = null;
        for (User user : users) {
            adminUser = new AdminUser();
            BeanUtils.copyProperties(user, adminUser);
            adminUsers.add(adminUser);
        }

        SimpleBeanPropertyFilter filter
                = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "password", "ssn");
        FilterProvider filterProvider
                = new SimpleFilterProvider().addFilter("UserInfo", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(adminUsers);
        mapping.setFilters(filterProvider);

        return mapping;
    }

    // http://localhost:8081/admin/users/1
    @GetMapping("/users/{id}")
    public MappingJacksonValue retrieveUser(@PathVariable int id) {
        User user = service.findOne(id);
        AdminUser adminUser = new AdminUser();  // AdminUser 객체 생성

        if(user == null) {  // 해당 user가 없을 경우
            // 여기서 에러가 날 경우 UserNotFoundException 발생시킴.
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        } else { // Admin일 경우 - 누구에게 요청하는지에 따라 url별로 개별적으로 필터를 사용해 데이터를 제한 할수 있음.
            BeanUtils.copyProperties(user, adminUser);  // copyProperties사용해서 adminUser 전달해줌.
        }
        SimpleBeanPropertyFilter filter
                = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");
        FilterProvider filterProvider
                = new SimpleFilterProvider().addFilter("UserInfo", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filterProvider);

        return mapping;
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
