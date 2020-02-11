package mvc.spring.restmvc.service.impl;

import lombok.extern.slf4j.Slf4j;
import mvc.spring.restmvc.dao.ProductRepository;
import mvc.spring.restmvc.exception.EntityAlreadyExistsException;
import mvc.spring.restmvc.exception.EntityNotFoundException;
import mvc.spring.restmvc.model.Product;
import mvc.spring.restmvc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

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
    public Product getGameById(Long id) {
        if (id == null) return null;
        return repo.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Game with ID=%s does not exist.", id)));
    }

    @Override
    public Product createGame(Product game) {
        Product result = repo.findByTitle(game.getTitle());
        if (result != null) {
            throw new EntityAlreadyExistsException(String.format("Game with that title exists.", game));
        } else {
            log.info("Creating default game: {}", game);
            return insert(game);
        }
    }

    @Transactional
    public Product insert(Product game) {
        game.setId(null);
        return repo.save(game);
    }

    @Override
    public Product updateGame(Product game) {
        return repo.save(game);
    }

    @Override
    public Product deleteGame(Long id) {
        Product old = getGameById(id);
        repo.deleteById(id);
        return old;
    }

    @Override
    public Product getGamesByTitle(String title) {
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

    @Override
    public List<Product> getGamesByOnSale(boolean onSale) {
        return repo.findByOnSale(onSale);
    }

    @Override
    public List<Product> getNewGames() {
        List<Product> products = repo.findAll();
        products.sort((p2, p1) -> p1.getReleased().compareTo(p2.getReleased()));
        return products;

    }
}
