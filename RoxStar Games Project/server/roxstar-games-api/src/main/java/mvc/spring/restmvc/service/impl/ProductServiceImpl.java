package mvc.spring.restmvc.service.impl;

import lombok.extern.slf4j.Slf4j;
import mvc.spring.restmvc.dao.ProductRepository;
import mvc.spring.restmvc.exception.EntityAlreadyExistsException;
import mvc.spring.restmvc.exception.EntityNotFoundException;
import mvc.spring.restmvc.model.Product;
import mvc.spring.restmvc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repo;

    @Override
    public List<Product> getAllGames() {
        return repo.findAll();
    }

    @Override
    public Product getGameById(String id) {
        if (id == null) return null;
        return repo.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Game with ID=%s does not exist.", id)));
    }

    @Override
    public Product createGame(Product game) {
        List<Product> result = repo.findByTitle(game.getTitle());
        if (!result.isEmpty()) {
            throw new EntityAlreadyExistsException(String.format("Entity already exists.", game));
        } else {
            log.info("Creating default user: {}", game);
            return repo.insert(game);
        }
    }

    @Override
    public Product updateGame(Product game) {
        return repo.save(game);
    }

    @Override
    public Product deleteGame(String id) {
        Product old = getGameById(id);
        repo.deleteById(id);
        return old;
    }

    @Override
    public List<Product> getGamesByTitle(String title) {
        return repo.findByTitle(title);
    }

    @Override
    public List<Product> getGamesByGenre(String genre) {
        return repo.findByGenre(genre);
    }

    @Override
    public List<Product> getGamesByStudio(String studio) {
        return repo.findByStudio(studio);
    }

    @Override
    public List<Product> getGamesByPlatform(String platform) {
        return repo.findByPlatform(platform);
    }

    @Override
    public List<Product> getGamesByType(String type) {
        return repo.findByType(type);
    }
}
