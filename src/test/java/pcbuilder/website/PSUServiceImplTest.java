package pcbuilder.website;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import pcbuilder.website.enums.Efficiency;
import pcbuilder.website.enums.ModularType;
import pcbuilder.website.enums.PSUType;
import pcbuilder.website.models.entities.products.PSU;
import pcbuilder.website.repositories.PSUDao;
import pcbuilder.website.services.impl.products.PSUServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PSUServiceImplTest {

    @Mock
    private PSUDao dao;

    @InjectMocks
    private PSUServiceImpl service;

    private PSU psu;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        psu = new PSU();
        psu.setProductID(1L);
        psu.setType(PSUType.ATX);
        psu.setEfficiency(Efficiency.Gold);
        psu.setWattage(750);
        psu.setModular(ModularType.Full);
        psu.setColor("Black");
        psu.setName("Corsair RM750x");
        psu.setPrice(129.99);
    }

    @Test
    void shouldSavePSU() {
        when(dao.save(psu)).thenReturn(psu);
        assertEquals(psu, service.save(psu));
    }

    @Test
    void shouldDeletePSU() {
        doNothing().when(dao).delete(psu);
        assertDoesNotThrow(() -> service.delete(psu));
        verify(dao).delete(psu);
    }

    @Test
    void shouldUpdatePSU() {
        when(dao.update(psu)).thenReturn(psu);
        assertEquals(psu, service.update(psu));
    }

    @Test
    void shouldPartiallyUpdatePSU() {
        PSU patch = new PSU();
        patch.setWattage(850);
        patch.setProductID(1L);

        when(dao.findById(1L)).thenReturn(Optional.of(psu));
        when(dao.update(any())).thenAnswer(inv -> inv.getArgument(0));

        PSU updated = service.partialUpdate(1L, patch);
        assertEquals(850, updated.getWattage());
    }

    @Test
    void shouldThrowWhenPSUNotFoundOnPartialUpdate() {
        when(dao.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.partialUpdate(1L, psu));
    }

    @Test
    void shouldFindById() {
        when(dao.findById(1L)).thenReturn(Optional.of(psu));
        Optional<PSU> result = service.findById(1L);
        assertTrue(result.isPresent());
    }

    @Test
    void shouldFindAllPSUs() {
        when(dao.findAll()).thenReturn(List.of(psu));
        List<PSU> result = service.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void shouldCheckIfExists() {
        when(dao.exists(1L)).thenReturn(true);
        assertTrue(service.exists(1L));
    }
}
