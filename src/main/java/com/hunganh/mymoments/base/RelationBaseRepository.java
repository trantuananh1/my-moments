package com.hunganh.mymoments.base;

import com.hunganh.mymoments.model.Attachment;
import com.hunganh.mymoments.model.Post;
import com.hunganh.mymoments.model.User;
import com.hunganh.mymoments.model.relationship.AttachmentOwnership;
import com.hunganh.mymoments.model.relationship.PostOwnership;
import com.hunganh.mymoments.repository.PostRepository;
import com.hunganh.mymoments.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Tran Tuan Anh
 * @Created: Mon, 12/04/2021 4:40 PM
 **/

@Repository
@AllArgsConstructor
public class RelationBaseRepository {
    private final UserRepository userRepository;

    public boolean hasRelationship(User user1, String relationType, User user2){
        List<String> relationTypes = userRepository.getRelationType(user1.getUsername(), user2.getUsername());
        return relationTypes.contains(relationType);
    }
}
