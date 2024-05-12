package org.kst.storemgmtbackend.models;

import lombok.Getter;

@Getter
public enum Authority {
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    USER_UPDATE("user:update"),
    USER_DELETE("user:delete"),

    ADMIN_READ("admin:read"),
    ADMIN_WRITE("admin:write"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete");

    private final String name;

    Authority(String name) {
        this.name = name;
    }
}
