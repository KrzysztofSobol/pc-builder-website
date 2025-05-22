package pcbuilder.website.repositories;

import pcbuilder.website.models.entities.products.CPU;

import java.util.List;

public interface CPUDao extends GenericDao<CPU, Long> {
    List<CPU> filterByCriteria(String socket, Integer coreCount, Double minCoreClock, Double maxCoreClock, Double minPrice, Double maxPrice);

}
