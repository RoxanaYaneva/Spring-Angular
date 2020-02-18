package mvc.spring.restmvc.service;

import mvc.spring.restmvc.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllGames();
    Product getGameById(Long id);
    Product createGame(Product game);
    Product updateGame(Product game);
    Product deleteGame(Long id);
    Product getGamesByTitle(String title);
    List<Product> getGamesByGenre(String genre);
    List<Product> getGamesByStudio(String studio);
    List<Product> getGamesByPlatform(String platform);
    List<Product> getGamesByType(String type);
    List<Product> getGamesByOnSale(boolean onSale);
    List<Product> getNewGames();

}
