package com.hunganh.mymoments.output;

import com.hunganh.mymoments.dto.SnwScoreRange;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.checkerframework.checker.units.qual.min;

import java.util.List;

/**
 * @Author: Tran Tuan Anh
 * @Created: Sun, 09/05/2021 6:32 PM
 **/

@Data
public class AssocBaseOutput {
    private long objectId;
    private long minScore;
    private long maxScore;
    private int total;
    private List<Long> itemIds;

    public AssocBaseOutput(long objectId, long minScore, long maxScore, int total, List<Long> itemIds) {
        this.objectId = objectId;
        this.minScore = minScore;
        this.maxScore = maxScore;
        this.total = total;
        this.itemIds = itemIds;
    }

    public AssocBaseOutput(long objectId, SnwScoreRange snwScoreRange, int total, List<Long> itemIds){
        this.objectId = objectId;
        this.minScore = snwScoreRange.getMinScore();
        this.maxScore = snwScoreRange.getMaxScore();
        this.total = total;
        this.itemIds = itemIds;
    }
}
