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
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
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
        return insert(order);
    }

    @Transactional
    public Order insert(Order order) {
        order.setId(null);
        order.setCreated(LocalDateTime.now());
        order.setUpdated(LocalDateTime.now());
        Set<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            Product product = orderItem.getProduct();
            product = productService.getGameById(product.getId());
            orderItem.setProduct(product);
        }

        /*
         * Order has references to OrderItem list and to Payment entities, thus this
         * operation will be cascade to them. Therefore, it is not necessary call
         * specific save() methods for the referenced entities.
         */
        order = repo.save(order);
        order.setUser(userService.getUserById(order.getUser().getId()));
        return order;
    }

    @Override
    public Order getOrderFromInsertDTO(InsertOrderDTO dto) {

        /*
         * Cross-reference between Order and Payment and between Order and OrderItem is
         * necessary to cascade the insert operation. Payment and OrderItem entities has
         * Foreign Key to Order, thus they must know Order entity. Order entity can
         * cascade all operations to Payment and OrderItem, thus if these entities are
         * referenced by Order, then it is not necessary call save() method to them. If
         * Order does not have references to Payment and OrderItem, then it is necessary
         * call explicitly save() methods for these entities.
         */

        Order order = Order.builder().user(User.builder().id(dto.getUserId()).build()).build();
//        order.getPayment().setOrder(order);


        Set<OrderItem> orderItems = dto.getOrderItems().stream()
                .map(item -> OrderItem.builder().product(Product.builder().id(item.getProductId()).build()).order(order)
                        .quantity(item.getQuantity()).build())
                .collect(Collectors.toSet());
        order.setOrderItems(orderItems);

        return order;
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
