package pcbuilder.website.services.impl;

import org.springframework.stereotype.Service;
import pcbuilder.website.models.entities.Order;
import pcbuilder.website.models.entities.OrderItem;
import pcbuilder.website.models.entities.Product;
import pcbuilder.website.models.entities.User;
import pcbuilder.website.repositories.OrderDao;
import pcbuilder.website.repositories.ProductDao;
import pcbuilder.website.repositories.UserDao;
import pcbuilder.website.services.OrderService;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class OrderServiceImpl implements OrderService {
    private final static Logger log =
            Logger.getLogger(OrderServiceImpl.class.getName());
    private final OrderDao orderDao;
    private final UserDao userDao;
    private final ProductDao productDao;

    public OrderServiceImpl(OrderDao orderDao, UserDao userDao, ProductDao productDao){
        try {
            log.finer("Initializing Order Service...");
            this.orderDao = orderDao;
            this.userDao = userDao;
            this.productDao = productDao;
        } catch (Exception e) {
            log.severe("Failed to initialize Order Service error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Order save(Order order) {
        try {
            log.fine("Saving order...");
            User user = userDao.findById(order.getUser().getUserID()).orElseThrow(() -> new RuntimeException("User not found"));

            order.setUser(user);

            Double totalAmount = 0d;
            for(OrderItem item : order.getOrderItems()){
                Product product = productDao.findById(item.getProduct().getProductID()).orElseThrow(() -> new RuntimeException("Product not found"));

                if(product.getStock() < item.getQuantity()){
                    throw new RuntimeException("Not enough stock");
                }

                product.setStock(product.getStock() - item.getQuantity());
                item.setProduct(product);
                totalAmount += Math.round(product.getPrice() * item.getQuantity() * 100.0) / 100.0;
            }
            order.setTotalAmount(totalAmount);
        } catch (RuntimeException e) {
            log.warning("Failed to save order error: " + e.getMessage());
            throw new RuntimeException(e);
        }

        try {
            log.fine("Saving order...");
            return orderDao.save(order);
        } catch (Exception e) {
            log.severe("Failed to save order error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Order order) {
        try {
            log.fine("Deleting order...");
            orderDao.delete(order);
        } catch (Exception e) {
            log.severe("Failed to delete order error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Order update(Order order) {
        try {
            log.fine("Updating order...");
            return orderDao.update(order);
        } catch (Exception e) {
            log.severe("Failed to update order error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Order partialUpdate(long id, Order order) {
        try {
            log.fine("Partial update of order...");
            order.setOrderID(id);

            return orderDao.findById(id).map(existingOrder -> {
                Optional.ofNullable(order.getUser()).ifPresent(existingOrder::setUser);
                Optional.ofNullable(order.getOrderDate()).ifPresent(existingOrder::setOrderDate);
                return orderDao.update(existingOrder);
            }).orElseThrow(() -> new RuntimeException("Order not found"));
        } catch (RuntimeException e) {
            log.warning("Failed to partial update order error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Order> findById(long id) {
        try {
            log.fine("Finding order...");
            return orderDao.findById(id);
        } catch (Exception e) {
            log.severe("Failed to find order error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Order> findAll() {
        try {
            log.fine("Finding all orders...");
            return orderDao.findAll();
        } catch (Exception e) {
            log.severe("Failed to find all orders error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean exists(long id) {
        try {
            log.fine("Checking if order exists...");
            return orderDao.exists(id);
        } catch (Exception e) {
            log.severe("Failed to check if order exists error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
