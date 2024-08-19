package com.kcc.restfulservice.service;

import com.kcc.restfulservice.bean.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class UserDaoService {
    private static List<User> users = new ArrayList<>();
    private static int usersCount = 3;

    static {
        users.add(new User(1, "kim", new Date(), "pass1", "701010-1111111"));
        users.add(new User(2, "lee", new Date(), "pass2", "801010-1111111"));
        users.add(new User(3, "park", new Date(), "pass3", "901010-1111111"));
    }
    
    //user 전체 검색
    public List<User> findAll() {
        return users;
    }
    
    //user 추가
    public User save(User user) {
        if (user.getId() == null) {
            user.setId(++usersCount);
        }

        if(user.getJoinDate() == null) {
            user.setJoinDate(new Date());
        }

        users.add(user);

        return user;
    }

    //user 한명을 검색
    public User findOne(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }

        return null;
    }

//    public void delete(int id) {
//        for (User user : users) {
//            if (user.getId() == id) {
//                users.remove(user);
//            }
//        }
//    }

    //user를 삭제
    public User deleteById(int id) {
        Iterator<User> iterator = users.iterator();
        while(iterator.hasNext()) {
            User user = iterator.next();

            if(user.getId() == id) {
                iterator.remove();
                return user;
            }
        }
        return null;

    }
}
