package pcbuilder.website.services;

import pcbuilder.website.models.entities.products.RAM;

import java.util.List;
import java.util.Optional;

public interface RAMService {
    RAM save(RAM ram);
    void delete(RAM ram);
    RAM update(RAM ram);
    RAM partialUpdate(long id, RAM ram);
    Optional<RAM> findById(long id);
    List<RAM> findAll();
    boolean exists(long id);
    List<RAM> filterRAMs(String name, Double minPrice, Double maxPrice, Integer minClockSpeed, Integer maxClockSpeed,
                         Integer ddrGen, Integer minModuleCount, Integer maxModuleCount, Integer minCapacity, Integer maxCapacity, String color);
}
