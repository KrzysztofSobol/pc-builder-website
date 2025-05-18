package pcbuilder.website.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pcbuilder.website.mappers.Mapper;
import pcbuilder.website.models.dto.orders.OrderDto;
import pcbuilder.website.models.dto.orders.OrderResponseDto;
import pcbuilder.website.models.entities.Order;
import pcbuilder.website.services.OrderService;

@RestController
public class OrderController {
    private final OrderService orderService;
    private final Mapper<Order, OrderResponseDto> mapper;

    public OrderController(OrderService orderService, Mapper<Order, OrderResponseDto> mapper) {
        this.orderService = orderService;
        this.mapper = mapper;
    }

    @PostMapping(path = "/orders")
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderDto order) {
        Order orderEntity = orderService.save(order); // CHANGE IT do it takes Order and not OrderDto for better project structure!!!!
        OrderResponseDto orderResponse = mapper.mapTo(orderEntity);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }
}
