//package com.kcc.restfulservice.controller;
//
//import com.kcc.restfulservice.bean.Post;
//import com.kcc.restfulservice.bean.User;
//import com.kcc.restfulservice.exception.UserNotFoundException;
//import com.kcc.restfulservice.repository.PostRepository;
//import com.kcc.restfulservice.repository.UserRepository;
//import com.kcc.restfulservice.service.UserDaoService;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.hateoas.EntityModel;
//import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//
//import java.net.URI;
//import java.util.List;
//import java.util.Optional;
//
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
//
//@RestController
////@AllArgsConstructor - 1번 방식
//@RequestMapping("/jpa")
//public class UserJpaController {
//
//    //@Autowired - 2번 방식
//    private UserRepository userRepository;
//    private PostRepository postRepository;
//
//    // 3번 방식
//    public UserJpaController(UserRepository userRepository, PostRepository postRepository) {
//        this.userRepository = userRepository;
//        this.postRepository = postRepository;
//    }
//
//    // http://localhost:8081/jpa/users
//    @GetMapping("/users")
//    public List<User> retrieveAllUsers() {
//        return userRepository.findAll();
//    }
//
//    // http://localhost:8081/jpa/users/9001
//    @GetMapping("/users/{id}")
//    public EntityModel<User> retrieveUser(@PathVariable int id) {
//        //User user = userRepository.findOne(id); 데이터 하나를 뽑을때 jpa는 user객체를 못 만듬. - findOne은 직접 만든거
//        Optional<User> user = userRepository.findById(id);
//
//        if(user == null) {  // 여기서 에러가 날 경우 UserNotFoundException 발생시킴.
//            throw new UserNotFoundException(String.format("ID[%s] not found",id));
//        }
//
//        EntityModel entityModel =  EntityModel.of(user);
//
//        // WebMvcLinkBuilder를 static으로 넣으면 linkTo를 import할 필요가 없다.
//        // retrieveAllUsers을 첨부해서 all-users라는 형태로 보내 줌.
//        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).retrieveAllUsers());
//        entityModel.add(linkTo.withRel("all-users"));
//
//        return entityModel;
//    }
//
//    // http://localhost:8081/jpa/users
//    @PostMapping("/users")
//    /*
//    public ResponseEntity<User> createUser(@RequestBody User user):
//    이 메서드는 공개 메서드이며, createUser라는 이름을 가지고 있습니다.
//    @RequestBody 어노테이션은 요청 본문(body)에 담긴 JSON 데이터를 User 객체 형태로 자동 변환하도록 지정합니다.
//    User 클래스는 사용자 정보를 담는 클래스 (이름, 이메일, 등) 라고 예상됩니다.
//     */
//    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
//        // 클라이언트가 보낸 user 객체를 service라는 객체에 전달하여 저장하는 작업을 수행
//        // service 객체는 실제 데이터베이스에 사용자 정보를 저장하는 로직을 가지고 있다.
//        User saveUser = userRepository.save(user);
//        // 생성된 사용자의 고유 식별자(id)를 이용하여 해당 사용자 정보를 조회할 수 있는 URI(Uniform Resource Identifier)를 만듦
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(saveUser.getId())
//                .toUri();
//
//        // 클라이언트에게 HTTP 응답 코드 201 (Created)를 반환 - 201 코드는 새로운 리소스가 성공적으로 생성되었음을 의미
//        return ResponseEntity.created(location).build();
//    }
//
//    // http://localhost:8081/jpa/users/9001
//    @DeleteMapping("/users/{id}")
//    public void deleteUser(@PathVariable int id) {
//        userRepository.deleteById(id);
//    }
//
//    // http://localhost:8081/jpa/users/9001/posts
//    @GetMapping("/users/{id}/posts")
//    public List<Post> retrieveAllPosts(@PathVariable int id) {
//        Optional<User> user = userRepository.findById(id);
//
//        if(!user.isPresent()) {  // 데이터가 없어도 user에 Optional객체가 넘어오기때문에 null이 아닐때로 표시
//            throw new UserNotFoundException(String.format("ID[%s] not found",id));
//        }
//
//        return user.get().getPosts();
//    }
//
//    // http://localhost:8081/jpa/users/9001/posts
//    @PostMapping("/users/{id}/posts")  //insert를 위해서는 Repository 필요
//    public ResponseEntity<Post> createPost(@PathVariable int id, @RequestBody Post post) {
//        // 한개의 user 객체를 얻기위한 코드
//        Optional<User> user = userRepository.findById(id);
//        if(user.isEmpty()) {
//            throw new UserNotFoundException(String.format("ID[%s] not found",id));
//        }
//
//        post.setUser(user.get());
//
//        Post savedPost = postRepository.save(post);
//
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(savedPost.getId())
//                .toUri();
//
//        return ResponseEntity.created(location).build();
//    }
//
//    // http://localhost:8081/jpa/users/9001/posts/1
//    @GetMapping("/users/{id}/posts/{post_id}")
//    public EntityModel<Post> retrieveAllPostsByUser(@PathVariable int id, @PathVariable int post_id) {
//        Optional<User> user = userRepository.findById(id);
//        Optional<Post> post = postRepository.findById(post_id);
//
//        if(!user.isPresent()) {  // 데이터가 없어도 user에 Optional객체가 넘어오기때문에 null이 아닐때로 표시
//            throw new UserNotFoundException(String.format("ID[%s] not found",id));
//        }
//
//        if(!post.isPresent()) {  // 데이터가 없어도 user에 Optional객체가 넘어오기때문에 null이 아닐때로 표시
//            throw new UserNotFoundException(String.format("ID[%s] not found",id));
//        }
//
//        EntityModel entityModel =  EntityModel.of(post);
//
//        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).retrieveAllPosts(id));
//        entityModel.add(linkTo.withRel("all-posts"));
//
//        return entityModel;
//    }
//}
