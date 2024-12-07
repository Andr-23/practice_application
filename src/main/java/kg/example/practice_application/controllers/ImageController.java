package kg.example.practice_application.controllers;

import kg.example.practice_application.payload.post_images.PostImageDto;
import kg.example.practice_application.service.ImageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post-images")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImageController {

    ImageService imageService;

    @GetMapping
    public ResponseEntity<Page<PostImageDto>> getAllPostImages(Pageable pageable) {
        Page<PostImageDto> postImages = imageService.getAllPostImages(pageable);
        return ResponseEntity.ok(postImages);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<Page<PostImageDto>> getAllPostImagesByPostId(@PathVariable Long postId, Pageable pageable) {
        Page<PostImageDto> postImages = imageService.getAllPostImagesByPostId(postId, pageable);
        return ResponseEntity.ok(postImages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostImageDto> getPostImageById(@PathVariable Long id) {
        PostImageDto postImageDto = imageService.getById(id);
        return ResponseEntity.ok(postImageDto);
    }

}
