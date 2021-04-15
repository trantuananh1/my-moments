package com.hunganh.mymoments.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;

/**
 * @Author: Tran Tuan Anh
 * @Created: Tue, 13/04/2021 10:51 AM
 **/

@Data
@Builder
@AllArgsConstructor
public class Profile {
    @Id
    private long userId;
    private String fullName;
    private String city;
    private String country;
    private String biography;
    private String gender;
    private String dateOfBirth;
    private String avatarUrl;
    private String coverUrl;
}
