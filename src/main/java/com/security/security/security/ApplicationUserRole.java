package com.security.security.security;

import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static com.security.security.security.ApplicationUserPermission.*;
public enum ApplicationUserRole {
    STUDENT(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(COURSE_READ , COURSE_WRITE ,STUDENT_WRITE,STUDENT_READ)),
    ADMINTRAINE(Sets.newHashSet(COURSE_READ ,STUDENT_READ));
    private final Set<ApplicationUserPermission> permissions ;


    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }
    public Set<SimpleGrantedAuthority> getGrantedAutorities(){
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_"+ this.name()));
        return permissions ;
    }
}
