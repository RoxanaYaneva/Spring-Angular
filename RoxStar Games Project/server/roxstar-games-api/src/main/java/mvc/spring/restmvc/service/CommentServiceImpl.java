package mvc.spring.restmvc.service;

import lombok.extern.slf4j.Slf4j;
import mvc.spring.restmvc.dao.CommentRepository;
import mvc.spring.restmvc.exception.EntityNotFoundException;
import mvc.spring.restmvc.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository repo;

    @Override
    public Comment getCommentById(String id) {
        if (id == null) return null;
        return repo.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Comment with ID=%s does not exist.", id)));
    }

    @Override
    public List<Comment> getCommentsByGameId(String gameId) {
        if (gameId == null) return null;
        return repo.findByGameId(gameId);
    }

    @Override
    public Comment updateComment(Comment comment) {
        comment.setEdited(LocalDateTime.now());
        return repo.save(comment);
    }

    @Override
    public Comment createComment(Comment comment) {
        comment.setCreated(LocalDateTime.now());
        comment.setEdited(LocalDateTime.now());
        return repo.insert(comment);
    }

    @Override
    public Comment deleteComment(String id) {
        Comment old = getCommentById(id);
        repo.deleteById(id);
        return old;
    }
}
