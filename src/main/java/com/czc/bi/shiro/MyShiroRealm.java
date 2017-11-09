package com.czc.bi.shiro;

import com.czc.bi.pojo.User;
import com.czc.bi.service.UserService;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

// 自定义Realm ，实现安全数据 连接
public class MyShiroRealm extends AuthorizingRealm {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    private UserService userService;

    // 授权...
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.debug("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user  = (User)principals.getPrimaryPrincipal();

        // 调用业务层，查询角色
//        List<Role> roles = roleService.findByUser(user);
//        for (Role role : roles) {
//            authorizationInfo.addRole(role.getKeyword());
//        }
        // 调用业务层，查询权限
//        List<Permission> permissions = permissionService.findByUser(user);
//        for (Permission permission : permissions) {
//            authorizationInfo.addStringPermission(permission.getKeyword());
//        }
        return authorizationInfo;
    }

    /*主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        logger.debug("MyShiroRealm.doGetAuthenticationInfo()");
        //获取用户的输入的账号.
//        String username = (String)authenticationToken.getPrincipal();
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        System.out.println(token.getCredentials());
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        User userInfo = userService.findUserByAccount(token.getUsername());
        logger.debug("----->>userInfo="+userInfo);
        if(userInfo == null){
            throw new UnknownAccountException();
//            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo.getAccount(), //用户名
                token.getPassword(), //密码
                //ByteSource.Util.bytes(userInfo.getCredentialsSalt()),//salt=username+salt
                getName()  //realm name
        );
        return authenticationInfo;
    }

}