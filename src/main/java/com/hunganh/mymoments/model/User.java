package com.hunganh.mymoments.model;

import com.hunganh.mymoments.base.SnwObject;
import com.hunganh.mymoments.base.SnwRelationType;
import com.hunganh.mymoments.model.relationship.AttachmentOwnership;
import com.hunganh.mymoments.model.relationship.Followship;
import com.hunganh.mymoments.model.relationship.PostOwnership;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @Author: Tran Tuan Anh
 * @Created: Mon, 12/04/2021 10:12 AM
 **/

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Node
public class User extends SnwObject {
    private String username;
    private String email;
    private String saltedPassword;
    private String lastIP;
    private boolean isEnabled;
    private boolean isPrivate;
    private Profile profile;

    @Relationship(type = "HAS_POST")
    private List<PostOwnership> postOwnerships;
    @Relationship(type = "FOLLOWED_BY")
    private List<Followship> followers;
    @Relationship(type = "FOLLOWS")
    private List<Followship> followings;
    @Relationship(type = "HAS_ATTACHMENT")
    private List<AttachmentOwnership> attachmentOwnerships;

    public User(String username, String email, String password, boolean isEnabled){
        super(0L, 1, new Date().getTime(), new Date().getTime());
        this.username = username;
        this.email = email;
        this.saltedPassword = new BCryptPasswordEncoder().encode(password);
        this.isEnabled = isEnabled;
    }
    public User(String username, String email, String password, String lastIP, boolean isEnabled){
        super(0L, 1, new Date().getTime(), new Date().getTime());
        this.username = username;
        this.email = email;
        this.saltedPassword = new BCryptPasswordEncoder().encode(password);
        this.lastIP = lastIP;
        this.isEnabled = isEnabled;
    }
    public User(String username, String email, String password){
        super(0L, 1, new Date().getTime(), new Date().getTime());
        this.username = username;
        this.email = email;
        this.saltedPassword = new BCryptPasswordEncoder().encode(password);
    }
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", saltedPassword='" + saltedPassword + '\'' +
                ", lastIP='" + lastIP + '\'' +
                ", isEnabled=" + isEnabled +
                ", isPrivate=" + isPrivate +
                ", profile=" + profile +
                ", id=" + id +
                ", version=" + version +
                ", dateCreated=" + dateCreated +
                ", dateUpdated=" + dateUpdated +
                '}';
    }
}
