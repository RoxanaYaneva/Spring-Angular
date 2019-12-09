package mvc.spring.restmvc.service;

import mvc.spring.restmvc.model.Game;

import java.util.List;

public interface GameService {
    List<Game> getAllGames();
    Game getGameById(String id);
    Game createGame(Game game);
    Game updateGame(Game game);
    Game deleteGame(String id);
    List<Game> getGamesByTitle(String title);
    List<Game> getGamesByGenre(String genre);
    List<Game> getGamesByStudio(String studio);
    List<Game> getGamesByPlatform(String platform);
    List<Game> getGamesByType(String type);
}
