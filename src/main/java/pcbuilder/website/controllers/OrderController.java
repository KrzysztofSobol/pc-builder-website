package pcbuilder.website.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pcbuilder.website.mappers.Mapper;
import pcbuilder.website.models.dto.orders.OrderDto;
import pcbuilder.website.models.dto.orders.OrderResponseDto;
import pcbuilder.website.models.entities.Order;
import pcbuilder.website.services.MailService;
import pcbuilder.website.services.OrderService;

@RestController
public class OrderController {
    private final OrderService orderService;
    private final Mapper<Order, OrderResponseDto> orderResponseMapper;
    private final Mapper<Order, OrderDto> orderMapper;
    private final MailService mailService;

    public OrderController(OrderService orderService, Mapper<Order, OrderResponseDto> orderResponseMapper, Mapper<Order, OrderDto> orderMapper, MailService mailService) {
        this.orderService = orderService;
        this.orderResponseMapper = orderResponseMapper;
        this.orderMapper = orderMapper;
        this.mailService = mailService;
    }

    @PostMapping(path = "/orders")
    @PreAuthorize("hasRole('Customer')")
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderDto order) {
        Order orderEntity = orderMapper.mapFrom(order);
        Order savedOrder = orderService.save(orderEntity);
        mailService.sendEmail(order.getRecipient().getEmail(), "Order confirmation", "Your order has been placed successfully! Waiting for payment");
        return new ResponseEntity<>(orderResponseMapper.mapTo(savedOrder), HttpStatus.OK);
    }
}
