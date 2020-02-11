package mvc.spring.restmvc.service.impl;

import lombok.extern.slf4j.Slf4j;
import mvc.spring.restmvc.dao.CommentRepository;
import mvc.spring.restmvc.exception.EntityNotFoundException;
import mvc.spring.restmvc.model.Comment;
import mvc.spring.restmvc.model.Product;
import mvc.spring.restmvc.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository repo;

    @Override
    public Comment getCommentById(Long id) {
        if (id == null) return null;
        return repo.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Comment with ID=%s does not exist.", id)));
    }

    @Override
    public List<Comment> getCommentsByProduct(Product product) {
        if (product == null) return null;
        return repo.findByProduct(product);
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
        return insert(comment);
    }

    @Transactional
    public Comment insert(Comment comment) {
        comment.setId(null);
        return repo.save(comment);
    }

    @Override
    public Comment deleteComment(Long id) {
        Comment old = getCommentById(id);
        repo.deleteById(id);
        return old;
    }
}
