package com.itheima.bos.service.system;

import com.itheima.bos.dao.system.UserRepository;
import com.itheima.bos.domain.system.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRealm extends AuthorizingRealm{
    @Autowired
    private UserRepository userRepository;
//    授权方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //给权限
        info.addStringPermission("courier_PageQuery");
        info.addStringPermission("courierAction_save");
        //赋予角色
        info.addRole("j");
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
