package com.itheima.bos.service.system.Impl;

import com.itheima.bos.dao.system.UserRepository;
import com.itheima.bos.domain.system.Role;
import com.itheima.bos.domain.system.User;
import com.itheima.bos.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public Page<User> findPageQuery(Pageable pageable) {
        return userRepository.findAll(pageable);
    }



    @Override
    public void save(User model, List<Long> roleIds) {

        userRepository.save(model);
        if(roleIds!=null&&roleIds.size()>0){
            for (Long roleId : roleIds) {
                Role role = new Role();
                role.setId(roleId);
                model.getRoles().add(role);
            }
        }
    }
}
