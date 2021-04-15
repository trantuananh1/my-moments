package com.hunganh.mymoments.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;

/**
 * @Author: Tran Tuan Anh
 * @Created: Mon, 12/04/2021 3:44 PM
 **/

@Data
@Builder
@AllArgsConstructor
public class ExtendInfo {
    @Id
    private long attachmentId;
    private int width;
    private int length;
}
