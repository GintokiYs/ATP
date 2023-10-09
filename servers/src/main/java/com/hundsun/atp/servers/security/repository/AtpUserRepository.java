package com.hundsun.atp.servers.security.repository;

import com.hundsun.atp.common.domain.entity.AtpUser;

public interface AtpUserRepository {
    AtpUser findUserByUsername(String username);
}
