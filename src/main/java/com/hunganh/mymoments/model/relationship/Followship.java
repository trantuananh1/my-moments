package com.hunganh.mymoments.model.relationship;

import com.hunganh.mymoments.model.Post;
import com.hunganh.mymoments.model.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

/**
 * @Author: Tran Tuan Anh
 * @Created: Wed, 14/04/2021 8:36 PM
 **/

@RelationshipProperties
@Getter
@Setter
public class Followship {
    @Id
    @GeneratedValue
    private Long id;
    private final Long time;
    @TargetNode
    private final User user;

    public Followship(User user, Long time) {
        this.user = user;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Followship{" +
                "id=" + id +
                ", time=" + time +
                ", user=" + user +
                '}';
    }
}
