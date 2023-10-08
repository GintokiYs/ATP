package com.hundsun.atp.servers.security.repository.impl;

import com.hundsun.atp.common.domain.entity.DiUser;
import com.hundsun.atp.servers.security.repository.AtpUserRepository;

import java.util.Map;

public class MapAtpUserRepository implements AtpUserRepository {
    private final Map<String, DiUser> usernameToUser;

    public MapAtpUserRepository(Map<String, DiUser> emailToCustomUser) {
        usernameToUser = emailToCustomUser;
    }

    @Override
    public DiUser findUserByUsername(String username) {
        return usernameToUser.get(username);
    }
}