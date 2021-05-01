package com.hunganh.mymoments.model;

import com.hunganh.mymoments.base.SnwObject;
import com.hunganh.mymoments.model.relationship.AttachmentOwnership;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Date;
import java.util.List;

/**
 * @Author: Tran Tuan Anh
 * @Created: Sat, 01/05/2021 4:44 PM
 **/

@Data
@SuperBuilder
@NoArgsConstructor
@Node
public class Comment extends SnwObject {
    private String content;

    @Relationship("HAS_ATTACHMENT")
    private List<AttachmentOwnership> attachmentOwnerships;

    public Comment(String content){
        super(0L, 1, new Date().getTime(), new Date().getTime());
        this.content  = content;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "content='" + content + '\'' +
                ", id=" + id +
                ", version=" + version +
                ", dateCreated=" + dateCreated +
                ", dateUpdated=" + dateUpdated +
                '}';
    }
}
