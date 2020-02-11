package mvc.spring.restmvc.service;

import mvc.spring.restmvc.model.Order;
import mvc.spring.restmvc.model.User;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();
    List<Order> getOrdersByStatus(String status);
    List<Order> getOrderByUser(User user);
    Order getOrderById(Long id);
    Order createOrder(Order order);
    Order updateOrder(Order order);
    Order deleteOrder(Long id);
}
