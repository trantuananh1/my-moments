package com.hunganh.mymoments.model.relationship;

import com.hunganh.mymoments.base.SnwRelationship;
import com.hunganh.mymoments.model.Attachment;
import com.hunganh.mymoments.model.Post;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

/**
 * @Author: Tran Tuan Anh
 * @Created: Sun, 18/04/2021 5:05 PM
 **/

@RelationshipProperties
@Getter
@Setter
public class AttachmentOwnership{

    @Id
    @GeneratedValue
    private Long id;
    private final Long time;
    @TargetNode
    private final Attachment attachment;

    public AttachmentOwnership(Attachment attachment, Long time) {
        this.attachment = attachment;
        this.time = time;
    }

    @Override
    public String toString() {
        return "AttachmentOwnership{" +
                "id=" + id +
                ", time=" + time +
                ", attachment=" + attachment +
                '}';
    }
}
