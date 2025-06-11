package pcbuilder.website.repositories;

import pcbuilder.website.enums.PanelType;
import pcbuilder.website.models.entities.products.Case;

import java.util.List;

public interface CaseDao extends GenericDao<Case, Long> {
    List<Case> filterByCriteria(String type, String color, PanelType sidePanel, Double minPrice, Double maxPrice);
}
