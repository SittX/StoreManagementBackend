package org.kst.storemgmtbackend.models;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static org.kst.storemgmtbackend.models.Authority.*;

@Getter
public enum Role {
    USER(Set.of(
            USER_READ, USER_WRITE, USER_UPDATE, USER_DELETE
    )),
    ADMIN(Set.of(
            ADMIN_READ, ADMIN_WRITE, ADMIN_UPDATE, ADMIN_DELETE, USER_READ, USER_WRITE, USER_UPDATE, USER_DELETE
    )),
    DEFAULT_USER(Set.of(
            USER_READ
    ));

    private final Set<SimpleGrantedAuthority> authorities;

    Role(Set<Authority> authorities) {
        this.authorities = authorities
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toSet());
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        this.authorities.add(new SimpleGrantedAuthority(this.name()));
        return this.authorities;
    }

}
