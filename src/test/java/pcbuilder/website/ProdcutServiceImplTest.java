package pcbuilder.website;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import pcbuilder.website.models.entities.Product;
import pcbuilder.website.repositories.ProductDao;
import pcbuilder.website.services.impl.ProdcutServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProdcutServiceImplTest {

    @Mock
    private ProductDao productDao;

    @InjectMocks
    private ProdcutServiceImpl service;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product();
        product.setProductID(1L);
        product.setName("Test Product");
    }

    @Test
    void shouldSaveProduct() {
        when(productDao.save(product)).thenReturn(product);
        assertEquals(product, service.save(product));
    }

    @Test
    void shouldDeleteProduct() {
        doNothing().when(productDao).delete(product);
        assertDoesNotThrow(() -> service.delete(product));
        verify(productDao).delete(product);
    }

    @Test
    void shouldUpdateProduct() {
        when(productDao.update(product)).thenReturn(product);
        assertEquals(product, service.update(product));
    }

    @Test
    void shouldFindById() {
        when(productDao.findById(1L)).thenReturn(Optional.of(product));
        assertTrue(service.findById(1L).isPresent());
    }

    @Test
    void shouldFindAll() {
        when(productDao.findAll()).thenReturn(List.of(product));
        assertEquals(1, service.findAll().size());
    }

    @Test
    void shouldCheckIfExists() {
        when(productDao.exists(1L)).thenReturn(true);
        assertTrue(service.exists(1L));
    }


}
