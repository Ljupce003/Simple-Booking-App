package finki.emt.lab_emt.model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    ROLE_USER,
    ROLE_HOST;

    @Override
    public String getAuthority() {
        return name();
    }
}
