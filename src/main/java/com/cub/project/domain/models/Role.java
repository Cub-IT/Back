package com.cub.project.domain.models;

import lombok.Getter;

@Getter
public enum Role {
    OWNER(0),
    ADMIN(1),
    MEMBER(2);

    private final Integer roleId;

    Role(int roleId) {
        this.roleId = roleId;
    }

    public static Role getById(Integer id) {
        for (Role role : values()) {
            if (role.roleId.equals(id)) {
                return role;
            }
        }
        throw new IllegalArgumentException("unknown role id given");
    }
}
