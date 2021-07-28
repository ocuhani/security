package com.security.security.auth;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.security.security.security.ApplicationUserRole.*;
@Repository("fake")
public class FakApplicationUserDaoService  implements ApplicationUserDao{
    private final PasswordEncoder passwordEncoder ;
    @Autowired
    public FakApplicationUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public Optional<ApplicationUser> SelectApplicationUserByUsername(String username) {
        return getApplicationUsers()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }
    private List<ApplicationUser> getApplicationUsers(){
        List<ApplicationUser> applicationUsers = Lists.newArrayList(
          new ApplicationUser("ahmed",passwordEncoder.encode("ahmed123")
          ,STUDENT.getGrantedAutorities(),
                  true,
                  true,
                  true,
                  true),
          new ApplicationUser("linda",passwordEncoder.encode("linda123")
          ,ADMIN.getGrantedAutorities(),
                  true,
                  true,
                  true,
                  true),
                new ApplicationUser("tom",passwordEncoder.encode("tom123")
          ,ADMINTRAINE.getGrantedAutorities(),
                  true,
                  true,
                  true,
                  true)

          );
        return applicationUsers;
    }
}
