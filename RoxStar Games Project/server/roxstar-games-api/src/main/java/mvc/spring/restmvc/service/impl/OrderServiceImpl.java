package mvc.spring.restmvc.service.impl;

import mvc.spring.restmvc.dao.OrderRepository;
import mvc.spring.restmvc.dto.InsertOrderDTO;
import mvc.spring.restmvc.exception.EntityNotFoundException;
import mvc.spring.restmvc.model.Order;
import mvc.spring.restmvc.model.OrderItem;
import mvc.spring.restmvc.model.Product;
import mvc.spring.restmvc.model.User;
import mvc.spring.restmvc.service.OrderService;
import mvc.spring.restmvc.service.ProductService;
import mvc.spring.restmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository repo;
    private ProductService productService;
    private UserService userService;

    @Autowired
    public void setOrderRepository(OrderRepository repo) {
        this.repo = repo;
    }

    @Autowired
    public void setProductService(ProductService service) {
        this.productService = service;
    }

    @Autowired
    public void setUserService(UserService service) {
        this.userService = service;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Set<Order> getAllOrders() {
        return new HashSet<>(repo.findAll());
    }

    @Override
    @PreAuthorize("hasAuthority('ALL_ORDER_READ')")
    public Set<Order> getOrdersByStatus(String status) {
        return repo.findByStatus(status);
    }

    @Override
    @PreAuthorize("#user.email == authentication.name or hasRole('ADMIN')")
    public Set<Order> getOrdersByUser(User user) {
        if (user == null) return null;
        return repo.findByUser(user);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Order getOrderById(Long id) {
        if (id == null) return null;
        return repo.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Order with id=%s does not exist", id)));
    }

    @Override
    public Order createOrder(Order order) {
        return insert(order);
    }

    @Transactional
    private Order insert(Order order) {
        order.setId(null);
        order.setCreated(LocalDateTime.now());
        order.setUpdated(LocalDateTime.now());
        Set<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            Product product = orderItem.getProduct();
            product = productService.getProductById(product.getId());
            orderItem.setProduct(product);
        }
        order = repo.save(order);
        order.setUser(userService.getUserById(order.getUser().getId()));
        return order;
    }

    @Override
    public Order getOrderFromInsertDTO(InsertOrderDTO dto) {
        Order order = Order.builder().user(User.builder().id(dto.getUserId()).build()).build();
        Set<OrderItem> orderItems = dto.getOrderItems().stream()
                .map(item -> OrderItem.builder().product(Product.builder().id(item.getProductId()).build()).order(order)
                        .quantity(item.getQuantity()).build())
                .collect(Collectors.toSet());
        order.setOrderItems(orderItems);
        return order;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Order updateOrder(Order order) {
        order.setUpdated(LocalDateTime.now());
        return repo.save(order);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Order deleteOrder(Long id) {
        Order old = getOrderById(id);
        repo.deleteById(id);
        return old;
    }
}
