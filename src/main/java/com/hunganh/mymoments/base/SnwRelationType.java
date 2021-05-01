package com.hunganh.mymoments.base;

/**
 * @Author: Tran Tuan Anh
 * @Created: Tue, 09/03/2021 4:05 PM
 **/

public enum SnwRelationType {
    HAS_POST(1, "HasPost"),
    BELONG_USER(2, "BelongUser"),
    FOLLOWS(3, "Follows"),
    FOLLOWED_BY(4, "FollowedBy"),
    HAS_PROFILE(5, "HasProfile"),
    HAS_COMMENT(6, "HasComment"),
    BELONG_POST(7, "BelongPost"),
    HAS_REACTION(8, "HasReaction"),
    HAS_TAG(9, "HasTag"),
    HAS_HASHTAG(10, "HasHashtag"),
    HAS_VERIFICATION_TOKEN(11, "HasVerificationToken"),
    HAS_ATTACHMENT(12, "HasAttachment");

    private final int value;
    private final String name;

    SnwRelationType(int value, String name) {
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
