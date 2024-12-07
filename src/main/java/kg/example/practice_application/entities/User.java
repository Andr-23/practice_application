package kg.example.practice_application.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "USERS")
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "USERS_SEQ", sequenceName = "USERS_SEQ", allocationSize = 1)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity implements UserDetails {

    @Column(unique = true)
    String username;

    @Column(unique = true)
    String email;

    String password;

    @LastModifiedDate
    @Column(name = "MODIFIED_AT")
    LocalDateTime modifiedAt;

    @Column(name = "PROFILE_IMAGE_URL")
    String profileImageUrl;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Post> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<FavouritePost> favouritePosts;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
