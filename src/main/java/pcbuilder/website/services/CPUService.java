package pcbuilder.website.services;

import pcbuilder.website.models.entities.products.CPU;

import java.util.List;
import java.util.Optional;

public interface CPUService {
    CPU save(CPU cpu);
    void delete(CPU cpu);
    CPU update(CPU cpu);
    CPU partialUpdate(long id, CPU cpu);
    Optional<CPU> findById(long id);
    List<CPU> findAll();
    boolean exists(long id);
    List<CPU> filterCPUs(String socket, Integer coreCount, Double minCoreClock, Double maxCoreClock, Double minPrice, Double maxPrice);
}
