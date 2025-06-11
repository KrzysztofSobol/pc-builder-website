package pcbuilder.website.services;

import pcbuilder.website.enums.Efficiency;
import pcbuilder.website.enums.ModularType;
import pcbuilder.website.enums.PSUType;
import pcbuilder.website.models.entities.products.PSU;

import java.util.List;
import java.util.Optional;

public interface PSUService {
    PSU save(PSU psu);
    void delete(PSU psu);
    PSU update(PSU psu);
    PSU partialUpdate(long id, PSU psu);
    Optional<PSU> findById(long id);
    List<PSU> findAll();
    boolean exists(long id);
    List<PSU> filterPSUs(PSUType type, Efficiency efficiency, Integer minWattage, Integer maxWattage, ModularType modular, String color, Double minPrice, Double maxPrice);
}
