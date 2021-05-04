package com.hunganh.mymoments.base;

/**
 * @Author: Tran Tuan Anh
 * @Created: Sun, 02/05/2021 6:12 PM
 **/

public enum SnwActionType {
    ADD(1, "Add"),
    UPDATE(2, "Update"),
    DELETE(3, "Delete");

    private final int value;
    private final String name;

    private SnwActionType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }
}
