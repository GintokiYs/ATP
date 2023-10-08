package com.hundsun.atp.servers.security;


import java.util.HashMap;
import java.util.Map;

import com.hundsun.atp.common.domain.entity.DiUser;
import com.hundsun.atp.common.util.security.EncryptUtil;
import com.hundsun.atp.servers.security.repository.impl.MapAtpUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AtpSecurityConfig {
    @Value("${spring.security.username:admin}")
    private String username;

    @Value("${spring.security.password:admin}")
    private String password;

    @Bean
    MapAtpUserRepository userRepository() {
        String encodedPassword = EncryptUtil.rsaDecode(password);
        DiUser customUser = new DiUser(1L, this.username, encodedPassword);
        Map<String, DiUser> usernameToCustomUser = new HashMap<>(1);
        usernameToCustomUser.put(customUser.getUsername(), customUser);
        return new MapAtpUserRepository(usernameToCustomUser);
    }
}