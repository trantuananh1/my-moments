package com.hunganh.mymoments.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.util.Date;

/**
 * @Author: Tran Tuan Anh
 * @Created: Mon, 12/04/2021 10:07 AM
 **/

@Data
@SuperBuilder
@NoArgsConstructor
public class SnwObject {
    @Id
    @GeneratedValue
    protected Long id;
    protected int version;
    @CreatedDate
    protected long dateCreated;
    @LastModifiedDate
    protected long dateUpdated;

    public SnwObject(long id, int version, long dateCreated, long dateUpdated) {
        this.id = null;
        this.version = 1;
        this.dateCreated = new Date().getTime();
        this.dateUpdated = new Date().getTime();
    }
}
