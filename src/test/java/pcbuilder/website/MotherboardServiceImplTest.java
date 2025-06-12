package pcbuilder.website;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import pcbuilder.website.models.entities.products.Motherboard;
import pcbuilder.website.repositories.MotherboardDao;
import pcbuilder.website.services.impl.products.MotherboardServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MotherboardServiceImplTest {

    @Mock
    private MotherboardDao dao;

    @InjectMocks
    private MotherboardServiceImpl service;

    private Motherboard motherboard;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        motherboard = new Motherboard();
        motherboard.setProductID(1L);
        motherboard.setSocket("LGA1200");
        motherboard.setFormFactor("ATX");
        motherboard.setMaxMemory(128);
        motherboard.setMemorySlots(4);
        motherboard.setColor("Black");
        motherboard.setName("ASUS Prime Z590-A");
        motherboard.setPrice(249.99);
    }

    @Test
    void shouldSaveMotherboard() {
        when(dao.save(motherboard)).thenReturn(motherboard);
        assertEquals(motherboard, service.save(motherboard));
    }

    @Test
    void shouldDeleteMotherboard() {
        doNothing().when(dao).delete(motherboard);
        assertDoesNotThrow(() -> service.delete(motherboard));
        verify(dao).delete(motherboard);
    }

    @Test
    void shouldUpdateMotherboard() {
        when(dao.update(motherboard)).thenReturn(motherboard);
        assertEquals(motherboard, service.update(motherboard));
    }

    @Test
    void shouldPartiallyUpdateMotherboard() {
        Motherboard patch = new Motherboard();
        patch.setMaxMemory(64);
        patch.setProductID(1L);

        when(dao.findById(1L)).thenReturn(Optional.of(motherboard));
        when(dao.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Motherboard updated = service.partialUpdate(1L, patch);
        assertEquals(64, updated.getMaxMemory());
    }

    @Test
    void shouldThrowWhenMotherboardNotFoundOnPartialUpdate() {
        when(dao.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.partialUpdate(1L, motherboard));
    }

    @Test
    void shouldFindById() {
        when(dao.findById(1L)).thenReturn(Optional.of(motherboard));
        Optional<Motherboard> result = service.findById(1L);
        assertTrue(result.isPresent());
    }

    @Test
    void shouldFindAllMotherboards() {
        when(dao.findAll()).thenReturn(List.of(motherboard));
        List<Motherboard> result = service.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void shouldCheckIfExists() {
        when(dao.exists(1L)).thenReturn(true);
        assertTrue(service.exists(1L));
    }
}
