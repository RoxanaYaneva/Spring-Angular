package mvc.spring.restmvc.service;

import mvc.spring.restmvc.model.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts();
    List<Post> getTop15ByPublishedDesc();
    List<Post> getAllPostsByStatus(String status);
    List<Post> getByAuthorId(String authorId);
    Post getPostById(String postId);
    Post createPost(Post post);
    Post updatePost(Post post);
    Post deletePost(String postId);
}
