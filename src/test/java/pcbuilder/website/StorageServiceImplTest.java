package pcbuilder.website;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import pcbuilder.website.enums.StorageType;
import pcbuilder.website.models.entities.products.Storage;
import pcbuilder.website.repositories.StorageDao;
import pcbuilder.website.services.impl.products.StorageServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StorageServiceImplTest {

    @Mock
    private StorageDao dao;

    @InjectMocks
    private StorageServiceImpl service;

    private Storage storage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        storage = new Storage();
        storage.setProductID(1L);
        storage.setCapacity(1000);
        storage.setType(StorageType.SSD);
    }

    @Test
    void shouldSaveStorage() {
        when(dao.save(storage)).thenReturn(storage);
        assertEquals(storage, service.save(storage));
    }

    @Test
    void shouldDeleteStorage() {
        doNothing().when(dao).delete(storage);
        assertDoesNotThrow(() -> service.delete(storage));
    }

    @Test
    void shouldUpdateStorage() {
        when(dao.update(storage)).thenReturn(storage);
        assertEquals(storage, service.update(storage));
    }

    @Test
    void shouldPartiallyUpdateStorage() {
        Storage patch = new Storage();
        patch.setCapacity(2000);
        patch.setProductID(1L);

        when(dao.findById(1L)).thenReturn(Optional.of(storage));
        when(dao.update(any())).thenAnswer(inv -> inv.getArgument(0));

        Storage updated = service.partialUpdate(1L, patch);
        assertEquals(2000, updated.getCapacity());
    }

    @Test
    void shouldThrowWhenStorageNotFound() {
        when(dao.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.partialUpdate(1L, storage));
    }

    @Test
    void shouldFindById() {
        when(dao.findById(1L)).thenReturn(Optional.of(storage));
        assertTrue(service.findById(1L).isPresent());
    }

    @Test
    void shouldFindAll() {
        when(dao.findAll()).thenReturn(List.of(storage));
        assertEquals(1, service.findAll().size());
    }

    @Test
    void shouldCheckExists() {
        when(dao.exists(1L)).thenReturn(true);
        assertTrue(service.exists(1L));
    }
}
