package mvc.spring.blogsapi.domain;

import mvc.spring.blogsapi.model.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts();
    Post getPostById(String postId);
    Post createPost(Post post);
    Post updatePost(Post post);
    Post deletePost(String postId);
}
