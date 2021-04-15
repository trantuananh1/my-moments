package com.hunganh.mymoments.model;

import com.hunganh.mymoments.base.SnwObject;
import com.hunganh.mymoments.model.relationship.PostOwnership;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.List;

/**
 * @Author: Tran Tuan Anh
 * @Created: Mon, 12/04/2021 3:37 PM
 **/

@Data
@SuperBuilder
@NoArgsConstructor
@Node
public class Post extends SnwObject {
    private String caption;
    private Location location;

    public Post(String caption){
        super(0L, 1, new Date().getTime(), new Date().getTime());
        this.caption  = caption;
    }

    public Post(String caption, Location location){
        super(0L, 1, new Date().getTime(), new Date().getTime());
        this.caption  = caption;
        this.location = location;
    }
}
