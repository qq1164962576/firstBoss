package com.itheima.bos.dao.system;

import com.itheima.bos.domain.system.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu,Long>{
    List<Menu> findByParentMenuIsNull();
    @Query("select m from Menu m inner join m.roles r inner join  r.users u where u.id = ?1")
    List<Menu> findByUid(Long id);
    //findByParentMenuIsNull
}
