package com.hundsun.atp.servers.security.repository;

import com.hundsun.atp.common.domain.entity.DiUser;

public interface AtpUserRepository {
    DiUser findUserByUsername(String username);
}
