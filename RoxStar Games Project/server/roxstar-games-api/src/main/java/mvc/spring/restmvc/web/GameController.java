package mvc.spring.restmvc.web;

import lombok.extern.slf4j.Slf4j;
import mvc.spring.restmvc.exception.InvalidEntityIdException;
import mvc.spring.restmvc.model.Comment;
import mvc.spring.restmvc.model.Game;
import mvc.spring.restmvc.service.CommentService;
import mvc.spring.restmvc.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/games")
@Slf4j
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private CommentService commentService;

    @GetMapping(value = {"", "/"})
    public List<Game> getGames() {
        return gameService.getAllGames();
    }

    @GetMapping("{id}")
    public Game getGame(@PathVariable("id") String id) {
        return gameService.getGameById(id);
    }

    @GetMapping("/title/{title}")
    public List<Game> getGamesByTitle(@PathVariable("title") String title) {
        return gameService.getGamesByTitle(title);
    }

    @GetMapping("/studio/{studio}")
    public List<Game> getGamesByStudio(@PathVariable("studio") String studio) {
        return gameService.getGamesByStudio(studio);
    }

    @GetMapping("/platform/{platform}")
    public List<Game> getGamesByPlatform(@PathVariable("platform") String platform) {
        return gameService.getGamesByPlatform(platform);
    }

    @GetMapping("/genre/{genre}")
    public List<Game> getGamesByGenre(@PathVariable("genre") String genre) {
        return gameService.getGamesByGenre(genre);
    }

    @GetMapping("/type/{type}")
    public List<Game> getGamesByType(@PathVariable("type") String type) {
        return gameService.getGamesByType(type);
    }

    @PostMapping
    public ResponseEntity<Game> addGame(@Valid @RequestBody Game game) {
        Game created = gameService.createGame(game);
        URI location = MvcUriComponentsBuilder
                .fromMethodName(GameController.class, "addGame", Game.class)
                .pathSegment("{id}")
                .buildAndExpand(created.getId())
                .toUri();
        log.info("Game created: {}", location);
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("{id}")
    public Game update(@PathVariable("id") String id, @Valid @RequestBody Game game) {
        if (!id.equals(game.getId())) {
            throw new InvalidEntityIdException(String.format("Entity ID='%s' is different from URL resource ID='%s'", game.getId(), id));
        } else {
            return gameService.updateGame(game);
        }
    }

    @DeleteMapping("{id}")
    public Game remove(@PathVariable("id") String id) {
        return gameService.deleteGame(id);
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
                .fromMethodName(GameController.class, "addComment", Comment.class)
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