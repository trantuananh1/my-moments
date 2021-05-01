package com.hunganh.mymoments.repository;

import com.hunganh.mymoments.model.Comment;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface CommentRepository extends Neo4jRepository<Comment, Long> {
}

