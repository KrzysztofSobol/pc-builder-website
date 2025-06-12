package pcbuilder.website;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import pcbuilder.website.models.entities.products.GPU;
import pcbuilder.website.repositories.GPUDao;
import pcbuilder.website.services.impl.products.GPUServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GPUServiceImplTest {

    @Mock
    private GPUDao dao;

    @InjectMocks
    private GPUServiceImpl service;

    private GPU gpu;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gpu = new GPU();
        gpu.setProductID(1L);
        gpu.setChipset("RTX 3080");
        gpu.setMemory(10);
        gpu.setCoreClock(1440);
        gpu.setBoostClock(1710);
        gpu.setColor("Black");
        gpu.setLength(285);
        gpu.setName("NVIDIA GeForce RTX 3080");
        gpu.setPrice(699.99);
    }

    @Test
    void shouldSaveGPU() {
        when(dao.save(gpu)).thenReturn(gpu);
        assertEquals(gpu, service.save(gpu));
    }

    @Test
    void shouldDeleteGPU() {
        doNothing().when(dao).delete(gpu);
        assertDoesNotThrow(() -> service.delete(gpu));
        verify(dao).delete(gpu);
    }

    @Test
    void shouldUpdateGPU() {
        when(dao.update(gpu)).thenReturn(gpu);
        assertEquals(gpu, service.update(gpu));
    }

    @Test
    void shouldPartiallyUpdateGPU() {
        GPU patch = new GPU();
        patch.setMemory(12);
        patch.setProductID(1L);

        when(dao.findById(1L)).thenReturn(Optional.of(gpu));
        when(dao.update(any())).thenAnswer(inv -> inv.getArgument(0));

        GPU updated = service.partialUpdate(1L, patch);
        assertEquals(12, updated.getMemory());
    }

    @Test
    void shouldThrowWhenGPUNotFoundOnPartialUpdate() {
        when(dao.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.partialUpdate(1L, gpu));
    }

    @Test
    void shouldFindById() {
        when(dao.findById(1L)).thenReturn(Optional.of(gpu));
        Optional<GPU> result = service.findById(1L);
        assertTrue(result.isPresent());
    }

    @Test
    void shouldFindAllGPUs() {
        when(dao.findAll()).thenReturn(List.of(gpu));
        List<GPU> result = service.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void shouldCheckIfExists() {
        when(dao.exists(1L)).thenReturn(true);
        assertTrue(service.exists(1L));
    }
}

