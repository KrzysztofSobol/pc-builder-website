package pcbuilder.website.services;

import pcbuilder.website.models.entities.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order save(Order user);
    void delete(Order user);
    Order update(Order user);
    Order partialUpdate(long id, Order user);
    Optional<Order> findById(long id);
    List<Order> findAll();
    boolean exists(long id);
}
