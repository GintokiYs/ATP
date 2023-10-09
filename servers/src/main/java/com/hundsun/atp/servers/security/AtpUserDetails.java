package com.hundsun.atp.servers.security;


import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.hundsun.atp.common.domain.entity.AtpUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

final class AtpUserDetails extends AtpUser implements UserDetails {
    private static final List<GrantedAuthority> ROLE_USER = Collections.unmodifiableList(AuthorityUtils.createAuthorityList(new String[]{"ROLE_USER"}));

    AtpUserDetails(AtpUser user) {
        super(user.getId(), user.getUsername(), user.getPassword());
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return ROLE_USER;
    }
    @Override
    public String getUsername() {
        return super.getUsername();
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}