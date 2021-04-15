package com.hunganh.mymoments.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.neo4j.core.schema.Id;

/**
 * @Author: Tran Tuan Anh
 * @Created: Mon, 12/04/2021 3:39 PM
 **/

@Data
@Builder
@AllArgsConstructor
public class Location {
    @Id
    private long postId;
    private float latitude;
    private float longitude;
}
