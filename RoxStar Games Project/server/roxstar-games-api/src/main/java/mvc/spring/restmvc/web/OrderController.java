package mvc.spring.restmvc.web;

import lombok.extern.slf4j.Slf4j;
import mvc.spring.restmvc.dto.InsertOrderDTO;
import mvc.spring.restmvc.exception.InvalidEntityIdException;
import mvc.spring.restmvc.model.Order;
import mvc.spring.restmvc.service.OrderService;
import mvc.spring.restmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Set;

@RestController
@RequestMapping("/api/orders")
@Slf4j
public class OrderController {

    private OrderService service;
    private UserService userService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.service = orderService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = {"", "/"})
    public Set<Order> getOrders() {
        return service.getAllOrders();
    }

    @GetMapping("{id}")
    public Order getOrder(@PathVariable("id") Long id) {
        return service.getOrderById(id);
    }

    @GetMapping(params = "status")
    public Set<Order> getOrdersWithStatus(@RequestParam(value = "status", required = true) String status) {
        return service.getOrdersByStatus(status);
    }

    @GetMapping(params = "userId")
    public Set<Order> getOrdersByUser(@RequestParam(value = "userId", required = true) Long userId) {
        return service.getOrdersByUser(userService.getUserById(userId));
    }

    @PostMapping
    public ResponseEntity<Order> addOrder(@RequestBody InsertOrderDTO dto) {
        Order order = service.getOrderFromInsertDTO(dto);
        order = service.createOrder(order);
        URI location = MvcUriComponentsBuilder
                .fromMethodName(OrderController.class, "addOrder", Order.class)
                .pathSegment("{id}")
                .buildAndExpand(order.getId())
                .toUri();
        log.info("Order created: {}", location);
        return ResponseEntity.created(location).body(order);
    }

    @PutMapping("{id}")
    public ResponseEntity<Order> update(@PathVariable("id") String id, @Valid @RequestBody Order order) {
        if (!id.equals(order.getId())) {
            throw new InvalidEntityIdException(String.format("Entity ID='%s' is different from URL resource ID='%s'", order.getId(), id));
        } else {
            Order updated = service.updateOrder(order);
            log.info("Order updated: {}", updated);
            return ResponseEntity.ok(updated);
        }
    }

    @DeleteMapping("{id}")
    public Order remove(@PathVariable("id") Long id) {
        return service.deleteOrder(id);
    }
}
