package mvc.spring.blogsapi.controller;

import lombok.extern.slf4j.Slf4j;
import mvc.spring.blogsapi.domain.PostService;
import mvc.spring.blogsapi.exception.InvalidEntityException;
import mvc.spring.blogsapi.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Slf4j
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping
    List<Post> getPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("{postId}")
    public Post getPost(@PathVariable String postId) {
        return postService.getPostById(postId);
    }

    @PostMapping
    ResponseEntity<Post> addPost(@RequestBody @Valid Post post) {
        Post created = postService.createPost(post);
        URI location = MvcUriComponentsBuilder.fromMethodName(PostController.class, "addPost", post)
                .pathSegment("{postId}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable String postId, @RequestBody @Valid Post post) {
        if(!postId.equals(post.getId())) {
            throw new InvalidEntityException(String.format("Entity IDs %s and %s don't match", postId, post.getId()));
        }
        Post updated = postService.updatePost(post);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("{postId}")
    public Post deletePost(@PathVariable String postId) {
        return postService.deletePost(postId);
    }
}
