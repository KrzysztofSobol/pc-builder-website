package pcbuilder.website.repositories;

import pcbuilder.website.models.entities.products.CPUCooler;

import java.util.List;

public interface CPUCoolerDao extends GenericDao<CPUCooler, Long> {
    List<CPUCooler> filterByCriteria(Integer minRPM, Integer maxRPM, Integer minNoise, Integer maxNoise,
                                     String color, Integer minHeight, Integer maxHeight,
                                     Double minPrice, Double maxPrice);
}
