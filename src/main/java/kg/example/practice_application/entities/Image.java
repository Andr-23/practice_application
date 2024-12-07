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
@Table(name = "IMAGES")
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "IMAGES_SEQ", sequenceName = "IMAGES_SEQ", allocationSize = 1)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Image extends BaseEntity {
    
    @ManyToOne
    @JoinColumn(name = "POST_ID")
    Post post;

    @Column(name = "URL")
    String url;

    @Column
    String description;

}
