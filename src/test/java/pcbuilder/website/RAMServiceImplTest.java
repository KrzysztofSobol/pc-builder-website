package pcbuilder.website;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import pcbuilder.website.models.entities.products.RAM;
import pcbuilder.website.repositories.RAMDao;
import pcbuilder.website.services.impl.products.RAMServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RAMServiceImplTest {

    @Mock
    private RAMDao dao;

    @InjectMocks
    private RAMServiceImpl service;

    private RAM ram;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ram = new RAM();
        ram.setProductID(1L);
        ram.setColor("Black");
        ram.setTotalCapacity(16);
    }

    @Test
    void shouldSaveRAM() {
        when(dao.save(ram)).thenReturn(ram);
        assertEquals(ram, service.save(ram));
    }

    @Test
    void shouldDeleteRAM() {
        doNothing().when(dao).delete(ram);
        assertDoesNotThrow(() -> service.delete(ram));
        verify(dao).delete(ram);
    }

    @Test
    void shouldUpdateRAM() {
        when(dao.update(ram)).thenReturn(ram);
        assertEquals(ram, service.update(ram));
    }

    @Test
    void shouldPartiallyUpdateRAM() {
        RAM patch = new RAM();
        patch.setTotalCapacity(32);
        patch.setProductID(1L);

        when(dao.findById(1L)).thenReturn(Optional.of(ram));
        when(dao.update(any())).thenAnswer(inv -> inv.getArgument(0));

        RAM updated = service.partialUpdate(1L, patch);
        assertEquals(32, updated.getTotalCapacity());
    }

    @Test
    void shouldThrowWhenRAMNotFound() {
        when(dao.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.partialUpdate(1L, ram));
    }

    @Test
    void shouldFindById() {
        when(dao.findById(1L)).thenReturn(Optional.of(ram));
        assertTrue(service.findById(1L).isPresent());
    }

    @Test
    void shouldFindAll() {
        when(dao.findAll()).thenReturn(List.of(ram));
        assertEquals(1, service.findAll().size());
    }

    @Test
    void shouldCheckExists() {
        when(dao.exists(1L)).thenReturn(true);
        assertTrue(service.exists(1L));
    }
}
