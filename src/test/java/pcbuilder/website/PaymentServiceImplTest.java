package pcbuilder.website;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import pcbuilder.website.enums.OrderStatus;
import pcbuilder.website.models.entities.Order;
import pcbuilder.website.repositories.OrderDao;
import pcbuilder.website.services.PaymentService;
import pcbuilder.website.services.impl.PaymentServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceImplTest {

    @Mock
    private OrderDao orderDao;

    @InjectMocks
    private PaymentServiceImpl service;

    private Order order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        order = new Order();
        order.setOrderID(1L);
        order.setStatus(OrderStatus.PROCESSING);
    }
}
