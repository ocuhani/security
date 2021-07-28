package com.security.security.security;

public enum ApplicationUserPermission {
    STUDENT_WRITE("student:write"),
    COURSE_WRITE("course:write"),
    COURSE_READ("course:read"),
    STUDENT_READ("student:read");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
