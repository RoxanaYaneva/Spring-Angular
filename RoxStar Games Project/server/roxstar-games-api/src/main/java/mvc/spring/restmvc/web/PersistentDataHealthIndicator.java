package mvc.spring.restmvc.web;

import mvc.spring.restmvc.dao.ProductRepository;
import mvc.spring.restmvc.dao.OrderRepository;
import mvc.spring.restmvc.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class PersistentDataHealthIndicator implements HealthIndicator {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Health health() {
        return Health
                .up()
                .withDetail("products.count", productRepository.count())
                .withDetail("users.count", userRepository.count())
                .withDetail("orders.count", orderRepository.count())
                .build();
    }
}
