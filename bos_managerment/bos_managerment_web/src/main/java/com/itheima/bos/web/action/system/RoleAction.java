package com.itheima.bos.web.action.system;

import com.itheima.bos.domain.system.Role;
import com.itheima.bos.service.system.RoleService;
import com.itheima.bos.web.action.base.BaseAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

@Controller
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")

public class RoleAction extends BaseAction<Role> {

    @Autowired
    private RoleService roleService;
    @Action("roleAction_findAll")
    public String findAll() throws IOException {
        List<Role> list = roleService.findAll();
        List2Json(list,new String[]{"users", "permissions", "menus"});
        return NONE;
    }
    private String menuIds;
    private List<Long> permissionIds;

    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }

    public void setPermissionIds(List<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }

    @Action(value = "roleAction_save", results = {@Result(name = "success",
            location = "/pages/system/role.html", type = "redirect")})
    public String save() {

        roleService.save(getModel(), menuIds, permissionIds);
        return SUCCESS;
    }

}
