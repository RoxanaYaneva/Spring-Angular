package mvc.spring.restmvc.web;

import lombok.extern.slf4j.Slf4j;
import mvc.spring.restmvc.exception.InvalidEntityIdException;
import mvc.spring.restmvc.model.Order;
import mvc.spring.restmvc.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping(value = {"", "/"})
    public List<Order> getOrders() {
        return service.getAllOrders();
    }

    @GetMapping("{id}")
    public Order getOrder(@PathVariable("id") Long id) {
        return service.getOrderById(id);
    }

    @GetMapping(params = "status")
    public List<Order> getOrdersWithStatus(@RequestParam(value = "status", required = true) String status) {
        return service.getOrdersByStatus(status);
    }

    @PostMapping
    public ResponseEntity<Order> addOrder(@Valid @RequestBody Order order) {
        Order created = service.createOrder(order);
        URI location = MvcUriComponentsBuilder
                .fromMethodName(OrderController.class, "addOrder", Order.class)
                .pathSegment("{id}")
                .buildAndExpand(created.getId())
                .toUri();
        log.info("Order created: {}", location);
        return ResponseEntity.created(location).body(created);
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
