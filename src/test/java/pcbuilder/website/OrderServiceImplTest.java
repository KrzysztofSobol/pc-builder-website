package pcbuilder.website;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import pcbuilder.website.enums.OrderStatus;
import pcbuilder.website.models.entities.Order;
import pcbuilder.website.repositories.OrderDao;
import pcbuilder.website.services.impl.OrderServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    @Mock
    private OrderDao dao;

    @InjectMocks
    private OrderServiceImpl service;

    private Order order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        order = new Order();
        order.setOrderID(1L);
        order.setStatus(OrderStatus.PROCESSING);

    }



    @Test
    void shouldDeleteOrder() {
        doNothing().when(dao).delete(order);
        assertDoesNotThrow(() -> service.delete(order));
        verify(dao).delete(order);
    }

    @Test
    void shouldUpdateOrder() {
        when(dao.update(order)).thenReturn(order);
        assertEquals(order, service.update(order));
    }

    @Test
    void shouldFindById() {
        when(dao.findById(1L)).thenReturn(Optional.of(order));
        Optional<Order> result = service.findById(1L);
        assertTrue(result.isPresent());
    }

    @Test
    void shouldFindAllOrders() {
        when(dao.findAll()).thenReturn(List.of(order));
        List<Order> result = service.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void shouldCheckIfExists() {
        when(dao.exists(1L)).thenReturn(true);
        assertTrue(service.exists(1L));
    }
}
