package mvc.spring.restmvc.service.impl;

import lombok.extern.slf4j.Slf4j;
import mvc.spring.restmvc.dao.CommentRepository;
import mvc.spring.restmvc.dto.InsertCommentDTO;
import mvc.spring.restmvc.exception.EntityNotFoundException;
import mvc.spring.restmvc.model.Comment;
import mvc.spring.restmvc.model.Product;
import mvc.spring.restmvc.model.User;
import mvc.spring.restmvc.service.CommentService;
import mvc.spring.restmvc.service.ProductService;
import mvc.spring.restmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    private CommentRepository repo;

    private ProductService productService;

    private UserService userService;

    @Autowired
    public void setCommentRepository(CommentRepository repo) {
        this.repo = repo;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Comment getCommentById(Long id) {
        if (id == null) return null;
        return repo.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Comment with ID=%s does not exist.", id)));
    }

    @Override
    public Set<Comment> getCommentsByProduct(Product product) {
        if (product == null) return null;
        return repo.findByProduct(product);
    }

    @Override
    public Comment getCommentFromInsertDTO(InsertCommentDTO dto) {
        Comment comment = Comment.builder().user(User.builder().id(dto.getUserId()).build())
                .text(dto.getText())
                .product(Product.builder().id(dto.getProductId()).build()).build();
        return comment;
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
        comment.setProduct(productService.getProductById(comment.getProduct().getId()));
        comment.setUser(userService.getUserById(comment.getUser().getId())); // TODO : change to currently logged in user
        return repo.save(comment);
    }

    @Override
    public Comment deleteComment(Long id) {
        Comment old = getCommentById(id);
        repo.deleteById(id);
        return old;
    }
}
