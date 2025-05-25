package pcbuilder.website.services;

import pcbuilder.website.models.dto.orders.OrderDto;
import pcbuilder.website.models.entities.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order save(Order order);
    void delete(Order order);
    Order update(Order order);
    Order partialUpdate(long id, Order order);
    Optional<Order> findById(long id);
    List<Order> findAll();
    boolean exists(long id);
}
