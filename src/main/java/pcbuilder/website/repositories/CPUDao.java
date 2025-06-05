package pcbuilder.website.repositories;

import pcbuilder.website.models.entities.products.CPU;

import java.util.List;

public interface CPUDao extends GenericDao<CPU, Long> {
    List<CPU> filterByCriteria(String name, String socket, Integer minCoreCount, Integer maxCoreCount, Double minCoreClock,
                               Double maxCoreClock, Double minPrice, Double maxPrice,Boolean graphics, Boolean smt);

}
