package com.hunganh.mymoments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Tran Tuan Anh
 * @Created: Sun, 09/05/2021 6:37 PM
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SnwScoreRange {
    private long minScore;
    private long maxScore;
}
