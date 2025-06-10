package com.MuharremAslan.Model;

import org.springframework.security.core.GrantedAuthority;

public enum ROLE  implements GrantedAuthority {
    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN"),
    ROLE_MODERATOR("MOD");

    private String role;

    ROLE(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String getAuthority() {
        return name();
    }
}
