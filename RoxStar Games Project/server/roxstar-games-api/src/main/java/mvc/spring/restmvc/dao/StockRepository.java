package mvc.spring.restmvc.dao;

import mvc.spring.restmvc.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
