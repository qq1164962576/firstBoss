package com.itheima.bos.service.system;

import com.itheima.bos.domain.system.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface UserService {
    Page<User> findPageQuery(Pageable pageable);

    void save(User model, List<Long> roleIds);
}
