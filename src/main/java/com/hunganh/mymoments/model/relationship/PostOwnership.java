package com.hunganh.mymoments.model.relationship;

import com.hunganh.mymoments.base.SnwRelationship;
import com.hunganh.mymoments.model.Post;
import javafx.geometry.Pos;
import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.util.Date;

/**
 * @Author: Tran Tuan Anh
 * @Created: Mon, 12/04/2021 3:56 PM
 **/

@RelationshipProperties
@Getter
@Setter
public class PostOwnership {
    @Id
    @GeneratedValue
    private Long id;
    private final Long time;
    @TargetNode
    private final Post post;

    public PostOwnership(Post post, Long time) {
        this.post = post;
        this.time = time;
    }

    @Override
    public String toString() {
        return "PostOwnership{" +
                "id=" + id +
                ", time=" + time +
                ", post=" + post +
                '}';
    }
}
