package pcbuilder.website.repositories;

import pcbuilder.website.models.entities.products.RAM;

import java.util.List;

public interface RAMDao extends GenericDao<RAM, Long> {
    List<RAM> filterByCriteria(String name, Double minPrice, Double maxPrice, Integer minClockSpeed, Integer maxClockSpeed, Integer ddrGen,
                               Integer minModuleCount, Integer maxModuleCount, Integer minCapacity, Integer maxCapacity, String color);
}
