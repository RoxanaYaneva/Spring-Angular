package mvc.spring.restmvc.service;

import mvc.spring.restmvc.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentsByGameId(String gameId);
    Comment getCommentById(String id);
    Comment updateComment(Comment comment);
    Comment createComment(Comment comment);
    Comment deleteComment(String id);
}
