package mvc.spring.restmvc.dao;

import mvc.spring.restmvc.model.Game;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends MongoRepository<Game, String> {
    List<Game> findByStudio(String studio);
    List<Game> findByPlatform(String platform);
    List<Game> findByTitle(String title);
    List<Game> findByGenre(String genre);
    List<Game> findByType(String type);
}
