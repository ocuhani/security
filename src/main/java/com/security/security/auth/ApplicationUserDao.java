package com.security.security.auth;

import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface ApplicationUserDao {
    Optional<ApplicationUser> SelectApplicationUserByUsername(String username);
}
