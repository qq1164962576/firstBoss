package com.jun.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.jun.domain.User;
import com.jun.service.UserService;

/**  
 * ClassName:UserServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Nov 5, 2017 11:02:35 AM <br/>       
 */
public class UserServiceImpl implements UserService {

    @Override
    public void save(User user) {
        System.out.println("保存user:"+user);

    }

    @Override
    public void delete(String name) {
        System.out.println("删除user:"+name);
        // TODO Auto-generated method stub  

    }

    @Override
    public void update(User user) {
        System.out.println("更新user:"+user);
        // TODO Auto-generated method stub  

    }

    @Override
    public List<User> findAll() {
        User user1  =  new User();
        user1.setId(1);
        user1.setName("1hao");
        user1.setPassword("11111");
        User user2  =  new User();
        user2.setId(2);
        user2.setName("2hao");
        user2.setPassword("22222");
        User user3  =  new User();
        user3.setId(3);
        user3.setName("3hao");
        user3.setPassword("33333");
        
        System.out.println("传回list");
        List<User> list = new ArrayList<>();
        list.add(user1);
        list.add(user2);
        list.add(user3);
        // TODO Auto-generated method stub  
        return list;
    }

    @Override
    public User findById(int id) {
        User user1  =  new User();
        user1.setId(1);
        user1.setName("1hao");
        user1.setPassword("11111");
        User user2  =  new User();
        user2.setId(2);
        user2.setName("2hao");
        user2.setPassword("22222");
        User user3  =  new User();
        user3.setId(3);
        user3.setName("3hao");
        user3.setPassword("33333");
        List<User> list = new ArrayList<>();
        list.add(user1);
        list.add(user2);
        list.add(user3);
        System.out.println("传回user");
        return list.get(id-1);
    }

}
  
