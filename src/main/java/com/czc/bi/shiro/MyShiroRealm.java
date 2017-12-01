package com.czc.bi.shiro;

import com.czc.bi.mapper.UserMapper;
import com.czc.bi.pojo.User;
import com.czc.bi.pojo.query.UserQuery;
import com.czc.bi.service.UserService;
import com.czc.bi.util.EncryptUtils;
import com.czc.bi.util.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// 自定义Realm ，实现安全数据 连接
public class MyShiroRealm extends AuthorizingRealm {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    // 授权...
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.debug("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = (User) principals.getPrimaryPrincipal();

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
    private static final String SQS_URL = "http://www.shangquanshow.com";
    private static final String KEY = "8ae4a027f1b34f80b1b56308f4f8e7ad";

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        logger.debug("MyShiroRealm.doGetAuthenticationInfo()");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String account = token.getUsername();
        char[] password = token.getPassword();
        // 那用户信息去商圈秀验证
        logger.debug(String.format("用户[%s]请求商圈秀系统验证",account));
        String tokenUrl = String.format("%s/co/gettoken?key=%s", SQS_URL, KEY);
        String res = HttpUtil.sendGet(tokenUrl);
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map resMap = mapper.readValue(res, Map.class);
            Integer status = (Integer) resMap.get("status");
            if (status != 1) {
                throw new AuthenticationException("系统错误");
            }

            String loginToken = (String) resMap.get("token");
            logger.debug(String.format("获取的token[%s]",loginToken));
            Map<String, Object> map = new HashMap<>(5);
            map.put("token", loginToken);
            map.put("user", EncryptUtils.encrypt3DES(account, loginToken));
            map.put("password", EncryptUtils.encrypt3DES(new String(password), loginToken));
            map.put("type", "1");
            String identifyUrl = String.format("%s/share/Identification", SQS_URL);
            res = HttpUtil.sendPost(identifyUrl, map);
            resMap = mapper.readValue(res, Map.class);
            String status1 = (String) resMap.get("status");
            // 验证不通过
            if (!"OK".equals(status1)) {
                String message = (String) resMap.get("message");
                logger.debug(String.format("验证不通过,提示信息为[%s]",message));
                throw new IncorrectCredentialsException(message);
            }
            Map userInfo = (Map) resMap.get("text");
            String name =(String) userInfo.get("name");
            if(name.equals("")){
                name = account;
            }
            logger.debug(String.format("用户账号[%s],名称[%s]验证通过",account,name));
            // 检查用户信息表
            UserQuery query = new UserQuery();
            query.setSqsAccount(account);

            int i = userMapper.selectRowsByQuery(query);
            // 如果用户不存在  插入用户信息
            if(i==0){
                User user = new User();
                user.setAccount("None");
                user.setSqsAccount(account);
                user.setName(name);
                user.setRole("商户");
                // 插入用户信息
                i = userMapper.insert(user);
                if(i==1){
                    logger.debug(String.format("用户[%s]信息同步数据库完成",user));
                } else {
                    logger.warn(String.format("用户[%s]信息同步数据库失败",user));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IncorrectCredentialsException("登陆遇到问题,请重试");
        }

        System.out.println(token.getCredentials());
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                account, //用户名
                null,    //密码
                getName()
        );
        return authenticationInfo;
    }

    @PostConstruct
    public void initCredentialsMatcher() {
        //重写shiro的密码验证
        setCredentialsMatcher(new CustomCredentialsMatcher());
    }
}