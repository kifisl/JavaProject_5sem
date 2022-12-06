package spring.security.jwt.controller.dto;

import lombok.Data;
import spring.security.jwt.bean.dto.Role;

@Data

public class AuthResponse {

    private String token;

    private Role role;

    public AuthResponse(String token, Role role) {
        this.token = token;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
