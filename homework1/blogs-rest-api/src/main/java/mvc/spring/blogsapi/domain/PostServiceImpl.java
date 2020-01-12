package mvc.spring.blogsapi.domain;

import mvc.spring.blogsapi.exception.EntityNotFoundException;
import mvc.spring.blogsapi.model.Post;
import mvc.spring.blogsapi.repositories.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostsRepository repo;

    @Override
    public List<Post> getAllPosts() {
        return repo.findAll();
    }

    @Override
    public Post getPostById(String postId) {
        if (postId == null) return null;
        return repo.findById(postId).orElseThrow(() -> new EntityNotFoundException("User with id " + postId + " was not found."));
    }

    @Override
    @PreAuthorize("hasAuthority('ALL_POST_CREATE')")
    public Post createPost(Post post) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        post.setAuthorId(auth.getName());
        return repo.insert(post);
    }

    @Override
    @PreAuthorize("#authorId == authentication.name or hasAuthority('ALL_POST_UPDATE')")
    public Post updatePost(Post post) {
        return repo.save(post);
    }

    @Override
    @Secured("ROLE_ADMIN")
    @PreAuthorize("hasAuthority('ALL_POST_DELETE')")
    public Post deletePost(String postId) {
        Optional<Post> post = repo.findById(postId);
        repo.deleteById(postId);
        return post.get();
    }
}
