package pcbuilder.website;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import pcbuilder.website.models.entities.products.CPU;
import pcbuilder.website.repositories.CPUDao;
import pcbuilder.website.services.impl.products.CPUServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CPUServiceImplTest {

    @Mock
    private CPUDao dao;

    @InjectMocks
    private CPUServiceImpl service;

    private CPU cpu;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cpu = new CPU();
        cpu.setProductID(1L);
        cpu.setSocket("AM4");
        cpu.setCoreCount(8);
        cpu.setBoostClock(4.5);
        cpu.setCoreClock(3.6);
        cpu.setTdp(95);
        cpu.setGraphics(true);
        cpu.setSmt(true);
        cpu.setName("Ryzen 7 3700X");
        cpu.setPrice(329.99);
    }

    @Test
    void shouldSaveCPU() {
        when(dao.save(cpu)).thenReturn(cpu);
        assertEquals(cpu, service.save(cpu));
    }

    @Test
    void shouldDeleteCPU() {
        doNothing().when(dao).delete(cpu);
        assertDoesNotThrow(() -> service.delete(cpu));
        verify(dao).delete(cpu);
    }

    @Test
    void shouldUpdateCPU() {
        when(dao.update(cpu)).thenReturn(cpu);
        assertEquals(cpu, service.update(cpu));
    }

    @Test
    void shouldPartiallyUpdateCPU() {
        CPU patch = new CPU();
        patch.setCoreCount(12);
        patch.setProductID(1L);

        when(dao.findById(1L)).thenReturn(Optional.of(cpu));
        when(dao.update(any())).thenAnswer(inv -> inv.getArgument(0));

        CPU updated = service.partialUpdate(1L, patch);
        assertEquals(12, updated.getCoreCount());
    }

    @Test
    void shouldThrowWhenCPUNotFoundOnPartialUpdate() {
        when(dao.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.partialUpdate(1L, cpu));
    }

    @Test
    void shouldFindById() {
        when(dao.findById(1L)).thenReturn(Optional.of(cpu));
        Optional<CPU> result = service.findById(1L);
        assertTrue(result.isPresent());
    }

    @Test
    void shouldFindAllCPUs() {
        when(dao.findAll()).thenReturn(List.of(cpu));
        List<CPU> result = service.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void shouldCheckIfExists() {
        when(dao.exists(1L)).thenReturn(true);
        assertTrue(service.exists(1L));
    }

    @Test
    void shouldFilterCPUs() {
        when(dao.filterByCriteria("Ryzen", "AM4", 4, 12, 3.0, 4.5, 200.0, 400.0, true, true))
                .thenReturn(List.of(cpu));

        List<CPU> result = service.filterCPUs("Ryzen", "AM4", 4, 12, 3.0, 4.5, 200.0, 400.0, true, true);
        assertEquals(1, result.size());
        verify(dao).filterByCriteria("Ryzen", "AM4", 4, 12, 3.0, 4.5, 200.0, 400.0, true, true);
    }
}
