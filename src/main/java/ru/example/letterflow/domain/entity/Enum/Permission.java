package ru.example.letterflow.domain.entity.Enum;

public enum Permission {
    BLOCK_USER("block"),
    CHANGE_ROLE("change"),
    SEND_MESSAGE("send"),
    READ_MESSAGE("read"),
    DELETE_MESSAGE("delete");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
