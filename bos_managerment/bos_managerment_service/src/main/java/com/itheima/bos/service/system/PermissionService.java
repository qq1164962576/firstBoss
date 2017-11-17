package com.itheima.bos.service.system;

import com.itheima.bos.domain.system.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PermissionService {

    Page<Permission> findPageQuery(Pageable pageable);

    void save(Permission model);
}
