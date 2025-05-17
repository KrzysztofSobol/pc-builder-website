package pcbuilder.website.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pcbuilder.website.models.entities.Order;
import pcbuilder.website.services.OrderService;

@RestController
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(path = "/orders")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order orderEntity = orderService.save(order);
        return new ResponseEntity<>(orderEntity, HttpStatus.OK);
    }
}
