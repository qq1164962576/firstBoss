package com.itheima.bos.dao.system;

import com.itheima.bos.domain.system.Role;
import com.itheima.bos.domain.system.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Long>{
    @Query("select r from Role r inner join r.users u where u.id = ?1")
    List<Role> findByUserId(Long id);
}
