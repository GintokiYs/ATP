package com.hundsun.atp.servers.security.repository.impl;

import com.hundsun.atp.common.domain.entity.AtpUser;
import com.hundsun.atp.servers.security.repository.AtpUserRepository;

import java.util.Map;

public class MapAtpUserRepository implements AtpUserRepository {
    private final Map<String, AtpUser> usernameToUser;

    public MapAtpUserRepository(Map<String, AtpUser> emailToCustomUser) {
        usernameToUser = emailToCustomUser;
    }

    @Override
    public AtpUser findUserByUsername(String username) {
        return usernameToUser.get(username);
    }
}