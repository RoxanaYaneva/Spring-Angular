package mvc.spring.restmvc.service;

import mvc.spring.restmvc.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();
    List<Order> getOrdersByStatus(String status);
    List<Order> getOrderByUserId(String userId);
    Order getOrderById(String id);
    Order createOrder(Order order);
    Order updateOrder(Order order);
    Order deleteOrder(String id);
}
