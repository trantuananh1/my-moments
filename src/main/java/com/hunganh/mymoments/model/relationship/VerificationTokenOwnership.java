package com.hunganh.mymoments.model.relationship;

import com.hunganh.mymoments.model.Post;
import com.hunganh.mymoments.model.VerificationToken;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

/**
 * @Author: Tran Tuan Anh
 * @Created: Tue, 11/05/2021 12:21 PM
 **/

@RelationshipProperties
@Getter
@Setter
public class VerificationTokenOwnership {
    @Id
    @GeneratedValue
    private Long id;
    private final Long score;
    @TargetNode
    private final VerificationToken verificationToken;

    public VerificationTokenOwnership(VerificationToken verificationToken, Long score) {
        this.verificationToken = verificationToken;
        this.score = score;
    }

    @Override
    public String toString() {
        return "PostOwnership{" +
                "id=" + id +
                ", score=" + score +
                ", token=" + verificationToken +
                '}';
    }
}
