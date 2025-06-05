package pcbuilder.website.services;

import pcbuilder.website.models.entities.products.Motherboard;

import java.util.List;
import java.util.Optional;

public interface MotherboardService {
    Motherboard save(Motherboard motherboard);
    void delete(Motherboard motherboard);
    Motherboard update(Motherboard motherboard);
    Motherboard partialUpdate(long id, Motherboard motherboard);
    Optional<Motherboard> findById(long id);
    List<Motherboard> findAll();
    Boolean exists(long id);
    List<Motherboard> filterMotherboards(String name, String socket, String formFactor, Integer maxMemory,
                                         Integer minMemorySlots, Integer maxMemorySlots, String color, Double minPrice, Double maxPrice);
}
