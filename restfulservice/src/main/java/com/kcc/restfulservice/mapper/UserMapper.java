package com.kcc.restfulservice.mapper;

import com.kcc.restfulservice.bean.Post;
import com.kcc.restfulservice.bean.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    public List<User> findAllUsers();
    public User findUser(int id);
    public void createUser(User user);
    public void createPost(Post post);
}
