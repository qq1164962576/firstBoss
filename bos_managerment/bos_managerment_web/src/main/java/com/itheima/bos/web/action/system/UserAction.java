package com.itheima.bos.web.action.system;

import com.itheima.bos.domain.system.User;
import com.itheima.bos.service.system.UserService;
import com.itheima.bos.web.action.base.BaseAction;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
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

public class UserAction extends BaseAction<User> {
    private String validatecode;
    @Autowired
    private UserService userService;
    public void setValidatecode(String validatecode) {
        this.validatecode = validatecode;
    }
    private List<Long> roleIds;

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    @Action(value = "userAction_save", results = {@Result(name = "success",
            location = "/pages/system/userlist.html", type = "redirect")}

    )
    public String save() {
        userService.save(getModel(),roleIds);
        return SUCCESS;
    }
    @Action(value="userAction_login",results={
            @Result(name="success",type="redirect",location="/index.html"),
            @Result(name="error",type="redirect",location="/unauthorized.html"),
            @Result(name="login",type="redirect",location="/login.html"),
            @Result(name="passworderror",type="redirect",location="/unauthorized1.html")})
    public String login() {
        String  servercode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
        System.out.println(servercode);
        if(StringUtils.isNotEmpty(servercode)&&servercode.equals(validatecode)){
            Subject subject = SecurityUtils.getSubject();
            AuthenticationToken token = new UsernamePasswordToken(
                    getModel().getUsername(), getModel().getPassword());
            // 框架提供的登录方法
            try {
                subject.login(token);
                User user = (User) subject.getPrincipal();
                // 把用户存入Session
                ServletActionContext.getRequest().getSession().setAttribute("user", user);
                return SUCCESS;
            } catch (UnknownAccountException e) {
                return ERROR;
                //System.out.println("用户名不匹配");
                //e.printStackTrace();

            } catch (IncorrectCredentialsException e){
                System.out.println("密码错误");
                e.printStackTrace();
                return "passworderror";
            }

        }

        return LOGIN;
    }
    @Action(value = "userAction_logout", results = {@Result(name = "success",
            location = "/login.html", type = "redirect")}
    )
    public String logout() {
        SecurityUtils.getSubject().logout();
        ServletActionContext.getRequest().getSession().invalidate();
        return SUCCESS;
    }

    @Action(value = "userAction_findPageQuery")
    public String findPageQuery() throws IOException {
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<User> page = userService.findPageQuery(pageable);
        Map2Json(page,new String[]{"roles"});
        return NONE;
    }
}
