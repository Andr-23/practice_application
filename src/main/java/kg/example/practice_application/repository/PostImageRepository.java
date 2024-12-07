package kg.example.practice_application.repository;

import kg.example.practice_application.entities.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostImageRepository extends JpaRepository<Image, Long> {
    Page<Image> findAllByPostId(Long postId, Pageable pageable);
}