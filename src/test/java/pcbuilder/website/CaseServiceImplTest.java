package pcbuilder.website;


import pcbuilder.website.services.impl.products.CaseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import pcbuilder.website.enums.PanelType;
import pcbuilder.website.models.entities.products.Case;
import pcbuilder.website.repositories.CaseDao;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CaseServiceImplTest {

    @Mock
    private CaseDao dao;

    @InjectMocks
    private CaseServiceImpl service;

    private Case sampleCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleCase = new Case();
        sampleCase.setProductID(1L);
        sampleCase.setName("Cooler Master");
        sampleCase.setColor("Black");
        sampleCase.setPrice(250.0);
        sampleCase.setSidePanel(PanelType.Acrylic);
    }

    @Test
    void testSave() {
        when(dao.save(sampleCase)).thenReturn(sampleCase);
        Case result = service.save(sampleCase);
        assertEquals(sampleCase, result);
        verify(dao).save(sampleCase);
    }

    @Test
    void testDelete() {
        doNothing().when(dao).delete(sampleCase);
        assertDoesNotThrow(() -> service.delete(sampleCase));
        verify(dao).delete(sampleCase);
    }

    @Test
    void testUpdate() {
        when(dao.update(sampleCase)).thenReturn(sampleCase);
        Case result = service.update(sampleCase);
        assertEquals(sampleCase, result);
        verify(dao).update(sampleCase);
    }

    @Test
    void testPartialUpdate_existingCase() {
        Case patch = new Case();
        patch.setColor("White");
        patch.setProductID(1L);

        when(dao.findById(1L)).thenReturn(Optional.of(sampleCase));
        when(dao.update(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Case result = service.partialUpdate(1L, patch);
        assertEquals("White", result.getColor());
        verify(dao).update(sampleCase);
    }

    @Test
    void testPartialUpdate_caseNotFound() {
        when(dao.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.partialUpdate(1L, sampleCase));
    }

    @Test
    void testFindById() {
        when(dao.findById(1L)).thenReturn(Optional.of(sampleCase));
        Optional<Case> result = service.findById(1L);
        assertTrue(result.isPresent());
        assertEquals(sampleCase, result.get());
        verify(dao).findById(1L);
    }

    @Test
    void testFindAll() {
        List<Case> cases = List.of(sampleCase);
        when(dao.findAll()).thenReturn(cases);
        List<Case> result = service.findAll();
        assertEquals(1, result.size());
        verify(dao).findAll();
    }

    @Test
    void testExists() {
        when(dao.exists(1L)).thenReturn(true);
        assertTrue(service.exists(1L));
        verify(dao).exists(1L);
    }

    @Test
    void testFilterCases() {
        List<Case> cases = List.of(sampleCase);
        when(dao.filterByCriteria("ATX", "Black", PanelType.Acrylic, 100.0, 300.0))
                .thenReturn(cases);

        List<Case> result = service.filterCases("ATX", "Black", PanelType.Acrylic, 100.0, 300.0);
        assertEquals(1, result.size());
        verify(dao).filterByCriteria("ATX", "Black", PanelType.Acrylic, 100.0, 300.0);
    }
}
