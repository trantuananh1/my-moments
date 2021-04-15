package com.hunganh.mymoments.repository;

import com.hunganh.mymoments.model.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @Author: Tran Tuan Anh
 * @Created: Mon, 12/04/2021 10:14 AM
 **/

public interface UserRepository extends Neo4jRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    @Query(("MATCH (user1:User{username:$uname1}) \n" +
            "MATCH (user2:User{username:$uname2}) \n" +
            "WITH user1, user2 \n" +
            "MATCH q=(user1)-[:FOLLOWS]->(user2)\n" +
            "RETURN nodes(q), relationships(q) IS NULL"))
    Boolean findFollowings(@Param("uname1") String uname1, @Param("uname2") String uname2);

    @Query("MATCH (:User{username : $username1})-[r]->(:User{username : $username2})\n" +
            "RETURN type(r) as type")
    List<String> getRelationType(String username1, String username2);
}
