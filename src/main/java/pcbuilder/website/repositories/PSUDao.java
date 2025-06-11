package pcbuilder.website.repositories;

import pcbuilder.website.enums.Efficiency;
import pcbuilder.website.enums.ModularType;
import pcbuilder.website.enums.PSUType;
import pcbuilder.website.models.entities.products.PSU;

import java.util.List;

public interface PSUDao extends GenericDao<PSU, Long> {
    List<PSU> filterByCriteria(PSUType type, Efficiency efficiency, Integer minWattage, Integer maxWattage,
                               ModularType modular, String color, Double minPrice, Double maxPrice);
}
