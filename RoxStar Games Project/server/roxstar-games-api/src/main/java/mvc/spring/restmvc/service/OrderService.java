package mvc.spring.restmvc.service;

import mvc.spring.restmvc.dto.InsertOrderDTO;
import mvc.spring.restmvc.model.Order;
import mvc.spring.restmvc.model.User;

import java.util.Set;

public interface OrderService {
    Set<Order> getAllOrders();
    Set<Order> getOrdersByStatus(String status);
    Set<Order> getOrdersByUser(User user);
    Order getOrderFromInsertDTO(InsertOrderDTO dto);
    Order getOrderById(Long id);
    Order createOrder(Order order);
    Order updateOrder(Order order);
    Order deleteOrder(Long id);
}