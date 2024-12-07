package kg.example.practice_application.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Entity
@Table(name = "FAVOURITE_POST")
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "FAVOURITE_POST_SEQ", sequenceName = "FAVOURITE_POST_SEQ", allocationSize = 1)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FavouritePost extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    User user;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    Post post;

}

