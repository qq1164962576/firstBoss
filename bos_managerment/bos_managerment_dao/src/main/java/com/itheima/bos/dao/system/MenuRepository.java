package com.itheima.bos.dao.system;

import com.itheima.bos.domain.system.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu,Long>{
    List<Menu> findByParentMenuIsNull();
                //findByParentMenuIsNull
}
