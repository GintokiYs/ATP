package com.hundsun.atp.servers.security;


import java.util.LinkedList;
import java.util.List;

import cn.hutool.core.util.StrUtil;
import com.hundsun.atp.common.util.security.PasswordHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AtpAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AtpUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        UserDetails userRoleVo = userDetailsService.loadUserByUsername(username);
        String encodedPassword = userRoleVo.getPassword();
        String credPassword = PasswordHelper.decodePassword(password);
        if (!StrUtil.equals(credPassword, encodedPassword)) {
            throw new AuthenticationServiceException("账号密码错误！");
        }
        List<GrantedAuthority> roles = new LinkedList<>();
        userRoleVo.getAuthorities().forEach(authority -> {
            SimpleGrantedAuthority roleId = new SimpleGrantedAuthority(authority.getAuthority());
            roles.add(roleId);
        });
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password, roles);
        return (Authentication) token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}