package com.hunganh.mymoments.model;

import com.hunganh.mymoments.base.SnwObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.neo4j.core.schema.Node;

/**
 * @Author: Tran Tuan Anh
 * @Created: Mon, 12/04/2021 3:41 PM
 **/

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Node
public class Attachment extends SnwObject {
    private long userId;
    private String url;
    private String name;
    private String mime;
    private int size;
    private int width;
    private int length;

    @Override
    public String toString() {
        return "Attachment{" +
                "userId=" + userId +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", mime='" + mime + '\'' +
                ", size=" + size +
                ", width=" + width +
                ", length=" + length +
                ", id=" + id +
                ", version=" + version +
                ", dateCreated=" + dateCreated +
                ", dateUpdated=" + dateUpdated +
                '}';
    }
}
