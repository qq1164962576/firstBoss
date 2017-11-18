package com.itheima.bos.dao.system;

import com.itheima.bos.domain.system.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission,Long>{

    @Query("select p from Permission p inner join p.roles r inner join r.users u where u.id=?1")
    List<Permission> findByUserId(Long id);
}
