package com.hunganh.mymoments.base;

import com.hunganh.mymoments.model.Post;
import com.hunganh.mymoments.model.User;
import com.hunganh.mymoments.model.relationship.PostOwnership;
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
    public void addRelation(Object o1, Object o2){
        if (o1 instanceof User){
            User user = (User) o1;
            if (user.getPostOwnerships()==null){
                user.setPostOwnerships(new ArrayList<>());
            }
            user.getPostOwnerships().add(new PostOwnership((Post) o2, new Date().getTime()));
            userRepository.save(user);
        }
    }

    public boolean hasRelationship(User user1, String relationType, User user2){
        List<String> relationTypes = userRepository.getRelationType(user1.getUsername(), user2.getUsername());
        return relationTypes.contains(relationType);
    }
}
