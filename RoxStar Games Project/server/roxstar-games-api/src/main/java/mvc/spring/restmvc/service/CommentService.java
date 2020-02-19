package mvc.spring.restmvc.service;

import mvc.spring.restmvc.dto.InsertCommentDTO;
import mvc.spring.restmvc.model.Comment;
import mvc.spring.restmvc.model.Product;

import java.util.Set;

public interface CommentService {
    Set<Comment> getCommentsByProduct(Product product);
    Comment getCommentFromInsertDTO(InsertCommentDTO dto);
    Comment getCommentById(Long id);
    Comment updateComment(Comment comment);
    Comment createComment(Comment comment);
    Comment deleteComment(Long id);
}
