package pcbuilder.website.repositories;

import pcbuilder.website.models.entities.products.Motherboard;
import java.util.List;

public interface MotherboardDao extends GenericDao<Motherboard, Long> {
    List<Motherboard> filterByCriteria(String name, String socket, String formFactor, Integer maxMemory,
                                       Integer minMemorySlots, Integer maxMemorySlots, String color, Double minPrice, Double maxPrice);
}
