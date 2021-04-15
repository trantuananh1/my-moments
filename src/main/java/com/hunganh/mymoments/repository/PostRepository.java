package com.hunganh.mymoments.repository;

import com.hunganh.mymoments.model.Attachment;
import com.hunganh.mymoments.model.Post;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface PostRepository extends Neo4jRepository<Post, Long> {
}
