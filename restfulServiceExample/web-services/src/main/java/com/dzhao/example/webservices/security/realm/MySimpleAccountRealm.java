package com.dzhao.example.webservices.security.realm;

import com.dzhao.example.utility.PasswordHelper;
import com.dzhao.example.webservices.security.config.MySecurityConfig;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Arrays;

public class MySimpleAccountRealm extends AuthorizingRealm {

    private static String SYNC_ROLE_DEFAULT = "*";
    private static String SYNC_PERMISSION_DEFAULT = "*";

    @Inject
    Provider<MySecurityConfig> config;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(Arrays.asList(SYNC_ROLE_DEFAULT));
        info.addStringPermissions(Arrays.asList(SYNC_PERMISSION_DEFAULT));
        return new SimpleAuthorizationInfo();
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;

        String username = usernamePasswordToken.getUsername();
        String password = String.valueOf(usernamePasswordToken.getPassword());

        String salt = config.get().getSalt();
        String algorithm = config.get().getAlgorithm();

        String validUserName = config.get().getUsername();
        String validPassword = PasswordHelper.getSecurePassword(algorithm, config.get().getPassword(), salt);

        if(validUserName==null || validUserName.isEmpty()){
            throw new RuntimeException("username required!");
        }

        if(validPassword==null || validPassword.isEmpty()){
            throw new RuntimeException("password required!");
        }

        if(validUserName.equals(username) &&
                validPassword.equals(password)){
            return new SimpleAuthenticationInfo(username, password, getName());
        }

        throw new RuntimeException("Permission denied: Incorrect username or password.");
    }

}
