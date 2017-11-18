package com.itheima.bos.service.system;

import com.itheima.bos.dao.system.PermissionRepository;
import com.itheima.bos.dao.system.RoleRepository;
import com.itheima.bos.dao.system.UserRepository;
import com.itheima.bos.domain.system.Permission;
import com.itheima.bos.domain.system.Role;
import com.itheima.bos.domain.system.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRealm extends AuthorizingRealm{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private RoleRepository roleRepository;
//    授权方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if("admin".equals(user.getUsername())||"ss".equals(user.getUsername())){
            List<Role> roles = roleRepository.findAll();
            for (Role role : roles) {
                info.addRole(role.getKeyword());
            }
            List<Permission> permissions = permissionRepository.findAll();
            for (Permission permission : permissions) {
                System.out.println(permission.getKeyword());
                info.addStringPermission(permission.getKeyword());
            }
        }else {
            //根据用户id 获得 角色表中的对应角色
            List<Role> roles = roleRepository.findByUserId(user.getId());
            if(roles.size()>0){
                for (Role role : roles) {
                    info.addRole(role.getKeyword());
                }
            }
            List<Permission> permissions = permissionRepository.findByUserId(user.getId());
            if(permissions.size()>0){
                for (Permission permission : permissions) {
                    System.out.println(permission.getKeyword());
                    info.addStringPermission(permission.getKeyword());
                }
            }
            //根据用户id 获得 权限表中的对应权限

        }
        //给权限
        /*info.addStringPermission("courier_PageQuery");
        info.addStringPermission("courierAction_save");*/
        //赋予角色
        //info.addRole("j");
        //info.addStringPermission("courier:list");
        return info;
    }
//    验证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        /*用过用户名找用户*/
        String username = usernamePasswordToken.getUsername();
        User user = userRepository.findByUsername(username);
        if(user == null) {
//            用户没找到
            return null;
        }

        AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user,user.getPassword(),getName());

        return authenticationInfo;
    }
}
