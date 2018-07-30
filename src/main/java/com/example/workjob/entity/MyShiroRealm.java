package com.example.workjob.entity;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.example.workjob.service.UserInfoService;

/**
 * Created by Administrator on
 * 自定义权限匹配和账号密码匹配
 */
public class MyShiroRealm extends AuthorizingRealm {

	@Resource
	private UserInfoService userInfoService;
 
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    	   System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
    	    SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
    	    UserInfo userInfo  = (UserInfo)principals.getPrimaryPrincipal();
    	    for(SysRole role:userInfo.getRoleList()){
    	        authorizationInfo.addRole(role.getRole());
    	        for(SysPermission p:role.getPermissions()){
    	            authorizationInfo.addStringPermission(p.getPermission());
    	        }
    	    }
    	    return authorizationInfo;
    }
 
    /*主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
    	   System.out.println("MyShiroRealm.doGetAuthenticationInfo()");
    	    //获取用户的输入的账号.
    	    String username = (String)token.getPrincipal();
    	    System.out.println("what's this------------------------------------" + token.getCredentials());
    	    //通过username从数据库中查找 User对象，如果找到，没找到.
    	    //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
    	    UserInfo userInfo = userInfoService.findByUsername(username);
    	    
    	    
    	    if(userInfo == null){
    	        return null;
    	    }
    	    System.out.println("what's this------------------------------------" + userInfo.getPassword());
    	    System.out.println("----->>userInfo="+userInfo);
    	    Object crdentials = userInfo.getPassword();//密码原值
    	    SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
    	    		userInfo, //用户
    	    		crdentials, //密码
    	            ByteSource.Util.bytes(userInfo.getUid() + "--" + userInfo.getUsername()),//salt=username+salt
    	            getName()  //realm name
    	    );
    	    
    	    return authenticationInfo;
    }
 
    @PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher("MD5");
		setCredentialsMatcher(matcher);
		matcher.setHashIterations(1);
	}

}
