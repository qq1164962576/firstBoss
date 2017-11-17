package com.itheima.bos.dao.system;

import com.itheima.bos.domain.system.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    /**
     * @param username
     * @return
     */
    User findByUsername(String username);
}
