package com.itheima.bos.service.system.Impl;

import com.itheima.bos.dao.system.RoleRepository;
import com.itheima.bos.domain.system.Menu;
import com.itheima.bos.domain.system.Permission;
import com.itheima.bos.domain.system.Role;
import com.itheima.bos.service.system.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public List<Role> findAll() {
       return roleRepository.findAll();
    }

    @Override
    public void save(Role role, String menuIds, List<Long> permissionIds) {

        roleRepository.save(role);

        if (StringUtils.isNoneEmpty(menuIds)) {
            String[] split = menuIds.split(",");
            for (String string : split) {
                Long menuId = Long.parseLong(string);
                // 持久态的Menu
                Menu menu =new Menu();//瞬时态
                menu.setId(menuId);//脱管/游离态
                // role持久态
                role.getMenus().add(menu);
            }

        }
        if (permissionIds != null && permissionIds.size() > 0) {
            for (Long permissionId : permissionIds) {
                Permission permission =new Permission();
                permission.setId(permissionId);
                role.getPermissions().add(permission);
            }
        }
    }

}

