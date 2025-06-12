package pcbuilder.website;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import pcbuilder.website.models.entities.products.CPUCooler;
import pcbuilder.website.repositories.CPUCoolerDao;
import pcbuilder.website.services.impl.products.CPUCoolerServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CPUCoolerServiceImplTest {

    @Mock
    private CPUCoolerDao dao;

    @InjectMocks
    private CPUCoolerServiceImpl service;

    private CPUCooler cooler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cooler = new CPUCooler();
        cooler.setProductID(1L);
        cooler.setColor("Black");
        cooler.setMinRPM(800);
        cooler.setMaxRPM(2000);
        cooler.setMinNoiseLevel(20);
        cooler.setMaxNoiseLevel(35);
        cooler.setHeight(160);
        cooler.setPrice(99.99);
    }

    @Test
    void shouldSaveCooler() {
        when(dao.save(cooler)).thenReturn(cooler);
        assertEquals(cooler, service.save(cooler));
    }

    @Test
    void shouldDeleteCooler() {
        doNothing().when(dao).delete(cooler);
        assertDoesNotThrow(() -> service.delete(cooler));
        verify(dao).delete(cooler);
    }

    @Test
    void shouldUpdateCooler() {
        when(dao.update(cooler)).thenReturn(cooler);
        assertEquals(cooler, service.update(cooler));
    }

    @Test
    void shouldPartiallyUpdateCooler() {
        CPUCooler patch = new CPUCooler();
        patch.setColor("White");
        patch.setProductID(1L);

        when(dao.findById(1L)).thenReturn(Optional.of(cooler));
        when(dao.update(any())).thenAnswer(inv -> inv.getArgument(0));

        CPUCooler updated = service.partialUpdate(1L, patch);
        assertEquals("White", updated.getColor());
    }

    @Test
    void shouldThrowWhenCoolerNotFoundOnPartialUpdate() {
        when(dao.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.partialUpdate(1L, cooler));
    }

    @Test
    void shouldFindById() {
        when(dao.findById(1L)).thenReturn(Optional.of(cooler));
        Optional<CPUCooler> result = service.findById(1L);
        assertTrue(result.isPresent());
    }

    @Test
    void shouldFindAllCoolers() {
        when(dao.findAll()).thenReturn(List.of(cooler));
        List<CPUCooler> result = service.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void shouldCheckIfExists() {
        when(dao.exists(1L)).thenReturn(true);
        assertTrue(service.exists(1L));
    }

    @Test
    void shouldFilterCoolers() {
        when(dao.filterByCriteria(800, 2000, 20, 35, "Black", 150, 170, 50.0, 150.0))
                .thenReturn(List.of(cooler));

        List<CPUCooler> result = service.filterCoolers(800, 2000, 20, 35, "Black", 150, 170, 50.0, 150.0);
        assertEquals(1, result.size());
        verify(dao).filterByCriteria(800, 2000, 20, 35, "Black", 150, 170, 50.0, 150.0);
    }
}
