package mvc.spring.restmvc.web;

import lombok.extern.slf4j.Slf4j;
import mvc.spring.restmvc.exception.InvalidEntityIdException;
import mvc.spring.restmvc.model.Game;
import mvc.spring.restmvc.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/games")
@Slf4j
public class GameController {

    @Autowired
    private GameService service;

    @GetMapping(value = {"", "/"})
    public List<Game> getGames() {
        return service.getAllGames();
    }

    @GetMapping("{id}")
    public Game getGame(@PathVariable("id") String id) {
        return service.getGameById(id);
    }

    @PostMapping
    public Game addGame(@Valid @RequestBody Game game) {
        return null;
    }

    @PutMapping("{id}")
    public Game update(@PathVariable("id") String id, @Valid @RequestBody Game game) {
        if (!id.equals(game.getId())) {
            throw new InvalidEntityIdException(String.format("Entity ID='%s' is different from URL resource ID='%s'", game.getId(), id));
        } else {
            return service.updateGame(game);
        }
    }

    @DeleteMapping("{id}")
    public Game remove(@PathVariable("id") String id) {
        return service.deleteGame(id);
    }
}
