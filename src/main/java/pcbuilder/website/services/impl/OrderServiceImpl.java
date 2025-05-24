package pcbuilder.website.services.impl;

import org.springframework.stereotype.Service;
import pcbuilder.website.enums.OrderStatus;
import pcbuilder.website.models.dto.orders.OrderDto;
import pcbuilder.website.models.dto.orders.OrderItemDto;
import pcbuilder.website.models.entities.Order;
import pcbuilder.website.models.entities.OrderItem;
import pcbuilder.website.models.entities.Product;
import pcbuilder.website.models.entities.User;
import pcbuilder.website.repositories.OrderDao;
import pcbuilder.website.repositories.ProductDao;
import pcbuilder.website.repositories.UserDao;
import pcbuilder.website.services.OrderService;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;
    private final UserDao userDao;
    private final ProductDao productDao;

    public OrderServiceImpl(OrderDao orderDao, UserDao userDao, ProductDao productDao){
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.productDao = productDao;
    }

    @Override
    public Order save(Order order) {
        User user = userDao.findById(order.getUser().getUserID()).orElseThrow(() -> new RuntimeException("User not found"));

        order.setUser(user);

        for(OrderItem item : order.getOrderItems()){
            Product product = productDao.findById(item.getProduct().getProductID()).orElseThrow(() -> new RuntimeException("Product not found"));

            if(product.getStock() < item.getQuantity()){
                throw new RuntimeException("Not enough stock");
            }

            item.setProduct(product);

            product.setStock(product.getStock() - item.getQuantity());
            productDao.update(product);
        }

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
