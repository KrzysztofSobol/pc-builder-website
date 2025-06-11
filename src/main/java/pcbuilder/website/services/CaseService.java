package pcbuilder.website.services;

import pcbuilder.website.enums.PanelType;
import pcbuilder.website.models.entities.products.Case;

import java.util.List;
import java.util.Optional;

public interface CaseService {
    Case save(Case c);
    void delete(Case c);
    Case update(Case c);
    Case partialUpdate(long id, Case c);
    Optional<Case> findById(long id);
    List<Case> findAll();
    boolean exists(long id);
    List<Case> filterCases(String type, String color, PanelType sidePanel, Double minPrice, Double maxPrice);
}
