package pcbuilder.website.services;

import pcbuilder.website.models.entities.products.CPUCooler;

import java.util.List;
import java.util.Optional;

public interface CPUCoolerService {
    CPUCooler save(CPUCooler cooler);
    void delete(CPUCooler cooler);
    CPUCooler update(CPUCooler cooler);
    CPUCooler partialUpdate(long id, CPUCooler cooler);
    Optional<CPUCooler> findById(long id);
    List<CPUCooler> findAll();
    boolean exists(long id);
    List<CPUCooler> filterCoolers(Integer minRPM, Integer maxRPM, Integer minNoise, Integer maxNoise, String color, Integer minHeight, Integer maxHeight, Double minPrice, Double maxPrice);
}
