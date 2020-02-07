package mvc.spring.restmvc.web;

import lombok.extern.slf4j.Slf4j;
import mvc.spring.restmvc.exception.InvalidEntityIdException;
import mvc.spring.restmvc.model.Comment;
import mvc.spring.restmvc.model.Product;
import mvc.spring.restmvc.service.CommentService;
import mvc.spring.restmvc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CommentService commentService;

    @GetMapping(value = {"", "/"})
    public List<Product> getGames() {
        return productService.getAllGames();
    }

    @GetMapping("{id}")
    public Product getGame(@PathVariable("id") String id) {
        return productService.getGameById(id);
    }

    @GetMapping
    public List<Product> getGamesByTitle(@RequestParam(value = "title", required = true) String title) {
        return productService.getGamesByTitle(title);
    }

    @GetMapping
    public List<Product> getGamesByStudio(@RequestParam(value = "studio", required = true) String studio) {
        return productService.getGamesByStudio(studio);
    }

    @GetMapping
    public List<Product> getGamesByPlatform(@RequestParam(value = "platform", required = true) String platform) {
        return productService.getGamesByPlatform(platform);
    }

    @GetMapping
    public List<Product> getGamesByGenre(@RequestParam(value = "genre", required = true) String genre) {
        return productService.getGamesByGenre(genre);
    }

    @GetMapping
    public List<Product> getGamesByType(@RequestParam(value = "type", required = true) String type) {
        return productService.getGamesByType(type);
    }

    @PostMapping
    public ResponseEntity<Product> addGame(@Valid @RequestBody Product game) {
        Product created = productService.createGame(game);
        URI location = MvcUriComponentsBuilder
                .fromMethodName(ProductController.class, "addGame", Product.class)
                .pathSegment("{id}")
                .buildAndExpand(created.getId())
                .toUri();
        log.info("Game created: {}", location);
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("{id}")
    public Product update(@PathVariable("id") String id, @Valid @RequestBody Product game) {
        if (!id.equals(game.getId())) {
            throw new InvalidEntityIdException(String.format("Entity ID='%s' is different from URL resource ID='%s'", game.getId(), id));
        } else {
            return productService.updateGame(game);
        }
    }

    @DeleteMapping("{id}")
    public Product remove(@PathVariable("id") String id) {
        return productService.deleteGame(id);
    }

    @GetMapping("/{id}/comments")
    public List<Comment> getCommentsForGame(@PathVariable("id") String id) {
        return commentService.getCommentsByGameId(id);
    }

    @GetMapping("/{id}/comments/{commentId}")
    public Comment getCommentForGame(@PathVariable("id") String id, @PathVariable("commentId") String commentId) {
        List<Comment> comments = commentService.getCommentsByGameId(id)
                .stream()
                .filter(comment -> comment.getId().equals(commentId))
                .collect(Collectors.toList());
        return comments.isEmpty() ? null : comments.get(0);
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<Comment> addComment(@Valid @RequestBody Comment comment) {
        Comment created = commentService.createComment(comment);
        URI location = MvcUriComponentsBuilder
                .fromMethodName(ProductController.class, "addComment", Comment.class)
                .pathSegment("{commentId}")
                .buildAndExpand(comment.getGameId(), created.getId())
                .toUri();
        log.info("Comment created: {}", location);
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("{id}/comments/{commentId}")
    public Comment editComment(@PathVariable("commentId") String commentId, @Valid @RequestBody Comment comment) {
        if (!commentId.equals(comment.getId())) {
            throw new InvalidEntityIdException(String.format("Entity ID='%s' is different from URL resource ID='%s'", comment.getId(), commentId));
        } else {
            return commentService.updateComment(comment);
        }
    }

    @DeleteMapping("{id}/comments/{commentId}")
    public Comment deleteComment(@PathVariable("commentId") String commentId) {
        return commentService.deleteComment(commentId);
    }
}