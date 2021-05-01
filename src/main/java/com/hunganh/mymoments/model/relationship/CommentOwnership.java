package com.hunganh.mymoments.model.relationship;

import com.hunganh.mymoments.model.Attachment;
import com.hunganh.mymoments.model.Comment;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

/**
 * @Author: Tran Tuan Anh
 * @Created: Sat, 01/05/2021 5:12 PM
 **/

@RelationshipProperties
@Getter
@Setter
public class CommentOwnership {
    @Id
    @GeneratedValue
    private Long id;
    private final Long time;
    @TargetNode
    private final Comment comment;

    public CommentOwnership(Comment comment, Long time) {
        this.comment = comment;
        this.time = time;
    }
}
