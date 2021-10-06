package ru.example.letterflow.domain.entity.Enum;

public enum Permission {
    BLOCK_USER("block and unblock users"),
    CHANGE_ROLE("change the role of the user"),
    SEND_MESSAGE("send message"),
    READ_MESSAGE("read message"),
    DELETE_MESSAGE("delete message"),
    CREATE_ROOM("create room"),
    ADD_USER("add user in room"),
    REMOVE_USER("remove from a room"),
    RENAME_ROOM("rename room"),
    DELETE_ROOM("delete room");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
