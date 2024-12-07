package kg.example.practice_application.repository;

import kg.example.practice_application.entities.FavouritePost;
import kg.example.practice_application.entities.Post;
import kg.example.practice_application.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavouritePostRepository extends JpaRepository<FavouritePost, Long> {
    boolean existsByUserAndPost(User user, Post post);
    Optional<FavouritePost> findByUserAndPost(User user, Post post);
}