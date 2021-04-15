package com.hunganh.mymoments.repository;

import com.hunganh.mymoments.model.Attachment;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface AttachmentRepository extends Neo4jRepository<Attachment, Long> {
}
