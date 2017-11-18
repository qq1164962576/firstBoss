package com.itheima.bos.service.system.Impl;

import com.itheima.bos.dao.system.PermissionRepository;
import com.itheima.bos.domain.system.Permission;
import com.itheima.bos.service.system.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;
    @Override
    public Page<Permission> findPageQuery(Pageable pageable) {
        return permissionRepository.findAll(pageable);
    }

    @Override
    public void save(Permission model) {
        permissionRepository.save(model);
    }

    @Override
    public List<Permission> findAll() {
        return permissionRepository.findAll();
    }
}
