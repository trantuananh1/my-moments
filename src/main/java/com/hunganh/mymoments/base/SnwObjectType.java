package com.hunganh.mymoments.base;

/**
 * @Author: Tran Tuan Anh
 * @Created: Tue, 09/03/2021 4:15 PM
 **/

public enum SnwObjectType {
    USER(1, "User"),
    POST(2, "Post"),
    COMMENT(3, "Comment"),
    TAG(4, "Tag"),
    HASHTAG(5, "Hashtag"),
    REACTION(6, "Reaction"),
    PROFILE(7, "Profile");

    private final int value;
    private final String name;

    private SnwObjectType(int value, String name) {
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
