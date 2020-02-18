package mvc.spring.restmvc.web;

import lombok.extern.slf4j.Slf4j;
import mvc.spring.restmvc.dto.InsertCommentDTO;
import mvc.spring.restmvc.exception.InvalidEntityIdException;
import mvc.spring.restmvc.model.Comment;
import mvc.spring.restmvc.model.Product;
import mvc.spring.restmvc.service.CommentService;
import mvc.spring.restmvc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@Slf4j
public class ProductController {

    private static final String UPLOADS_DIR = "tmp";


    private ProductService productService;
    private CommentService commentService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    @CrossOrigin
    @GetMapping(value = {"", "/"})
    public List<Product> getGames() {
        return productService.getAllGames();
    }

    @CrossOrigin
    @GetMapping("{id}")
    public Product getGame(@PathVariable("id") Long id) {
        return productService.getGameById(id);
    }

    @CrossOrigin
    @GetMapping(params = "title")
    public Product getGamesByTitle(@RequestParam(value = "title", required = true) String title) {
        return productService.getGamesByTitle(title);
    }

    @CrossOrigin
    @GetMapping(params = "studio")
    public List<Product> getGamesByStudio(@RequestParam(value = "studio", required = true) String studio) {
        return productService.getGamesByStudio(studio);
    }

    @CrossOrigin
    @GetMapping(params = "platform")
    public List<Product> getGamesByPlatform(@RequestParam(value = "platform", required = true) String platform) {
        return productService.getGamesByPlatform(platform);
    }

    @CrossOrigin
    @GetMapping(params = "genre")
    public List<Product> getGamesByGenre(@RequestParam(value = "genre", required = true) String genre) {
        return productService.getGamesByGenre(genre);
    }

    @CrossOrigin
    @GetMapping(params = "type")
    public List<Product> getGamesByType(@RequestParam(value = "type", required = true) String type) {
        return productService.getGamesByType(type);
    }

    @CrossOrigin
    @GetMapping(value = "onSale")
    public List<Product> getGamesByOnSale() {
        return productService.getGamesByOnSale(true);
    }

    @CrossOrigin
    @GetMapping(value = "new")
    public List<Product> getGamesNew() {
        return productService.getNewGames();
    }

    @PostMapping
    public ResponseEntity<Product> addGame(@RequestBody Product game/*, @RequestParam("file") MultipartFile file*/) {
//        if (!file.isEmpty() && file.getOriginalFilename().length() > 0) {
//            if (Pattern.matches("\\w+\\.(jpg|png)", file.getOriginalFilename())) {
//                handleMultipartFile(file);
//                game.setImageUrl(file.getOriginalFilename());
//            } else {
//                game.setImageUrl(null);
//            }
//        }

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
    public Product update(@PathVariable("id") Long id, @Valid @RequestBody Product game) {
        if (!id.equals(game.getId())) {
            throw new InvalidEntityIdException(String.format("Entity ID='%s' is different from URL resource ID='%s'", game.getId(), id));
        } else {
            return productService.updateGame(game);
        }
    }

    @DeleteMapping("{id}")
    public Product remove(@PathVariable("id") Long id) {
        return productService.deleteGame(id);
    }

    @CrossOrigin
    @GetMapping("/{id}/comments")
    public List<Comment> getCommentsForGame(@PathVariable("id") Long id) {
        Product product = productService.getGameById(id);
        return commentService.getCommentsByProduct(product);
    }

    @GetMapping("/{id}/comments/{commentId}")
    public Comment getCommentForGame(@PathVariable("id") Long id, @PathVariable("commentId") Long commentId) {
        List<Comment> comments = commentService.getCommentsByProduct(productService.getGameById(id))
                .stream()
                .filter(comment -> comment.getId().equals(commentId))
                .collect(Collectors.toList());
        return comments.isEmpty() ? null : comments.get(0);
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<Comment> addComment(@RequestBody InsertCommentDTO dto) {
        Comment comment = commentService.getCommentFromInsertDTO(dto);
        comment = commentService.createComment(comment);
        URI location = MvcUriComponentsBuilder
                .fromMethodName(ProductController.class, "addComment", Comment.class)
                .pathSegment("{commentId}")
                .buildAndExpand(comment.getProduct().getId(), comment.getId())
                .toUri();
        log.info("Comment created: {}", location);
        return ResponseEntity.created(location).body(comment);
    }

    @PutMapping("{id}/comments/{commentId}")
    public Comment editComment(@PathVariable("commentId") Long commentId, @Valid @RequestBody Comment comment) {
        if (!commentId.equals(comment.getId())) {
            throw new InvalidEntityIdException(String.format("Entity ID='%s' is different from URL resource ID='%s'", comment.getId(), commentId));
        } else {
            return commentService.updateComment(comment);
        }
    }

    @DeleteMapping("{id}/comments/{commentId}")
    public Comment deleteComment(@PathVariable("commentId") Long commentId) {
        return commentService.deleteComment(commentId);
    }

    private void handleMultipartFile(MultipartFile file) {
        String name = file.getOriginalFilename();
        long size = file.getSize();
        log.info("File: " + name + ", Size: " + size);
        try {
            File currentDir = new File(UPLOADS_DIR);
            if (!currentDir.exists()) {
                currentDir.mkdirs();
            }
            String path = currentDir.getAbsolutePath() + "/" + file.getOriginalFilename();
            path = new File(path).getAbsolutePath();
            log.info(path);
            File f = new File(path);
            FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(f));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}