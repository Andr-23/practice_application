package kg.example.practice_application.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "POSTS")
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "POSTS_SEQ", sequenceName = "POSTS_SEQ", allocationSize = 1)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Post extends BaseEntity {
    
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    User user;

    @Column
    String title;

    @Column(columnDefinition = "TEXT")
    String content;

    @LastModifiedDate
    @Column(name = "MODIFIED_AT")
    LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    List<Image> images;

    public void addPostImage(Image image) {
        this.images.add(image);
    }

}

