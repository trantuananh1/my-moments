package com.hunganh.mymoments.model.relationship;

import java.util.Comparator;

/**
 * @Author: Tran Tuan Anh
 * @Created: Tue, 11/05/2021 12:39 AM
 **/

public class PostOwnershipComparator implements Comparator<PostOwnership> {
    @Override
    public int compare(PostOwnership o1, PostOwnership o2) {
        return o2.getScore()>(o1.getScore())?1:-1;
    }
}
