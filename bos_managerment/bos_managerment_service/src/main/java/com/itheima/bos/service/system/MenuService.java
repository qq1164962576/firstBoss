package com.itheima.bos.service.system;

import com.itheima.bos.domain.system.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface MenuService {

    Page<Menu> findPageQuery(Pageable pageable);

    List<Menu> findAllLevelOne();

    void save(Menu model);

    List<Menu> findByUser();
}
