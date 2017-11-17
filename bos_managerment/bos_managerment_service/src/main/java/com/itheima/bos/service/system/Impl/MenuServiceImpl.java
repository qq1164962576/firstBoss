package com.itheima.bos.service.system.Impl;

import com.itheima.bos.dao.system.MenuRepository;
import com.itheima.bos.domain.system.Menu;
import com.itheima.bos.service.system.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;
    @Override
    public Page<Menu> findPageQuery(Pageable pageable) {
        return menuRepository.findAll(pageable);
    }

    @Override
    public List<Menu> findAllLevelOne() {
        return menuRepository.findByParentMenuIsNull();
    }

    @Override
    public void save(Menu model) {
        //获取父菜单,如果父菜单没有id 是顺时态,不能存进数据库,需要将parentmenu设置为null
        Menu parentMenu = model.getParentMenu();

        if(parentMenu.getId()==null||parentMenu.getId()==0){
            model.setParentMenu(null);
        }
        menuRepository.save(model);
    }
}
