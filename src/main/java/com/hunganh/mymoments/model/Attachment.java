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
    private ExtendInfo extendInfo;
}
