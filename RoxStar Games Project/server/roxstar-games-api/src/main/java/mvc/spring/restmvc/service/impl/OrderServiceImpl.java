package mvc.spring.restmvc.service.impl;

import mvc.spring.restmvc.dao.OrderRepository;
import mvc.spring.restmvc.exception.EntityNotFoundException;
import mvc.spring.restmvc.model.Order;
import mvc.spring.restmvc.model.User;
import mvc.spring.restmvc.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository repo;

    @Override
    public List<Order> getAllOrders() {
        return repo.findAll();
    }

    @Override
    public List<Order> getOrdersByStatus(String status) {
        return repo.findByStatus(status);
    }

    @Override
    public List<Order> getOrderByUser(User user) {
        if (user == null) return null;
        return repo.findByUser(user);
    }

    @Override
    public Order getOrderById(Long id) {
        if (id == null) return null;
        return repo.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Order with id=%s does not exist", id)));
    }

    @Override
    public Order createOrder(Order order) {
        order.setCreated(LocalDateTime.now());
        order.setUpdated(LocalDateTime.now());
        return insert(order);
    }

    @Transactional
    public Order insert(Order order) {
        order.setId(null);
        return repo.save(order);
    }

    @Override
    public Order updateOrder(Order order) {
        order.setUpdated(LocalDateTime.now());
        return repo.save(order);
    }

    @Override
    public Order deleteOrder(Long id) {
        Order old = getOrderById(id);
        repo.deleteById(id);
        return old;
    }
}
