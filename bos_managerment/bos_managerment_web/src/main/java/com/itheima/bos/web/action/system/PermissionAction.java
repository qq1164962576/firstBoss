package com.itheima.bos.web.action.system;

import com.itheima.bos.domain.system.Permission;
import com.itheima.bos.service.system.PermissionService;
import com.itheima.bos.web.action.base.BaseAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

@Controller
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")

public class PermissionAction extends BaseAction<Permission> {
    @Autowired
    private PermissionService permissionService;
    @Action("permissionAction_findPageQuery")
    public String findPageQuery() throws IOException {
        Pageable pageable = new PageRequest(page-1,rows);
        Page<Permission> page = permissionService.findPageQuery(pageable);
        Map2Json(page,new String[]{"roles"});
        return NONE;
    }
    @Action(value = "permissionAction_save",results = {@Result(name = "success",type = "redirect",location = "/pages/system/permission.html")})
    public String save(){
        permissionService.save(getModel());
        return SUCCESS;
    }
    @Action("permissionAction_findAll")
    public String findAll() throws IOException {
        List<Permission> list =  permissionService.findAll();
        List2Json(list,new String[]{"roles"});
        return NONE;
    }
}
