package com.itheima.bos.web.action.system;

import com.itheima.bos.domain.system.Menu;
import com.itheima.bos.service.system.MenuService;
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

@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
@Controller

public class MenuAction extends BaseAction<Menu> {
    @Autowired
    private MenuService menuService;

    @Action("menuAction_findPageQuery")
    public String pageQuery() throws IOException {
        Pageable pageable = new PageRequest(Integer.parseInt(getModel().getPage())-1,rows);
        Page<Menu> page = menuService.findPageQuery(pageable);
        Map2Json(page,new String[] {"roles", "childrenMenus", "parentMenu"});
        return NONE;
    }

    @Action(value = "menuAction_save",results={
            @Result(name="success",type="redirect",location="/pages/system/menu.html")})
    public String save() throws IOException {
        menuService.save(getModel());
        return SUCCESS;
    }
    // 查询所有的一级菜单项
    @Action("menuAction_findAllLevelOne")
    public String findAllLevelOne() throws IOException {

        // 只查询所有的一级菜单,子菜单应该是通过父菜单的childrenMenus属性加载出来
        List<Menu> list = menuService.findAllLevelOne();
        List2Json(list, new String[] {"roles", "childrenMenus", "parentMenu"});
        return NONE;
    }
}