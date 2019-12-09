package mvc.spring.restmvc.service;

import mvc.spring.restmvc.dao.GameRepository;
import mvc.spring.restmvc.exception.EntityNotFoundException;
import mvc.spring.restmvc.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository repo;

    @Override
    public List<Game> getAllGames() {
        return repo.findAll();
    }

    @Override
    public Game getGameById(String id) {
        if (id == null) return null;
        return repo.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Game with ID=%s does not exist.", id)));
    }

    @Override
    public Game createGame(Game game) {
        return repo.insert(game);
    }

    @Override
    public Game updateGame(Game game) {
        return repo.save(game);
    }

    @Override
    public Game deleteGame(String id) {
        Game old = getGameById(id);
        repo.deleteById(id);
        return old;
    }

    @Override
    public List<Game> getGamesByTitle(String title) {
        return repo.findByTitle(title);
    }

    @Override
    public List<Game> getGamesByGenre(String genre) {
        return repo.findByGenre(genre);
    }

    @Override
    public List<Game> getGamesByStudio(String studio) {
        return repo.findByStudio(studio);
    }

    @Override
    public List<Game> getGamesByPlatform(String platform) {
        return repo.findByPlatform(platform);
    }

    @Override
    public List<Game> getGamesByType(String type) {
        return repo.findByType(type);
    }
}
