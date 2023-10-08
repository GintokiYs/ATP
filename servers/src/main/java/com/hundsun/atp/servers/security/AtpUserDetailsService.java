package com.hundsun.atp.servers.security;


import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.hundsun.atp.common.domain.entity.DiUser;
import com.hundsun.atp.servers.security.repository.AtpUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AtpUserDetailsService implements UserDetailsService {
    @Autowired
    private AtpUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DiUser diUser = userRepository.findUserByUsername(username);
        if (diUser == null) {
            throw new UsernameNotFoundException("username " + username + " is not found");
        }
        return new DiUserDetails(diUser);
    }

    static final class DiUserDetails extends DiUser implements UserDetails {
        private static final List<GrantedAuthority> ROLE_USER = Collections.unmodifiableList(AuthorityUtils.createAuthorityList(new String[]{"ROLE_USER"}));

        DiUserDetails(DiUser user) {
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
}