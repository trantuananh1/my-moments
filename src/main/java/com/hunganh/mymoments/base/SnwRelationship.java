package com.hunganh.mymoments.base;

import com.hunganh.mymoments.model.Post;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.util.Date;

/**
 * @Author: Tran Tuan Anh
 * @Created: Mon, 12/04/2021 10:08 AM
 **/

@RelationshipProperties
@Data
public class SnwRelationship<T> {
    @Id
    @GeneratedValue
    protected Long id;
    protected final Long time;
    @TargetNode
    private final T object;

    public SnwRelationship(T object, Long time) {
        this.object = object;
        this.time = time;
    }

    public SnwRelationship(T object) {
        this.object = object;
        this.time = new Date().getTime();
    }
    public SnwRelationship(){
        this.object = null;
        this.time = 0l;
    }
}
