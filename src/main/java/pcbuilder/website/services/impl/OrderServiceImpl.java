package pcbuilder.website.services.impl;

import org.springframework.stereotype.Service;
import pcbuilder.website.models.entities.Order;
import pcbuilder.website.repositories.OrderDao;
import pcbuilder.website.services.OrderService;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;

    public OrderServiceImpl(OrderDao orderDao){
        this.orderDao = orderDao;
    }

    @Override
    public Order save(Order order) {
        return orderDao.save(order);
    }

    @Override
    public void delete(Order order) {
        orderDao.delete(order);
    }

    @Override
    public Order update(Order order) {
        return orderDao.update(order);
    }

    @Override
    public Order partialUpdate(long id, Order order) {
        order.setOrderID(id);

        return orderDao.findById(id).map(existingOrder -> {
            Optional.ofNullable(order.getUser()).ifPresent(existingOrder::setUser);
            Optional.ofNullable(order.getOrderDate()).ifPresent(existingOrder::setOrderDate);
            return orderDao.update(existingOrder);
        }).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public Optional<Order> findById(long id) {
        return orderDao.findById(id);
    }

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }

    @Override
    public boolean exists(long id) {
        return orderDao.exists(id);
    }
}
