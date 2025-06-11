package pcbuilder.website.services.impl.products;

import org.springframework.stereotype.Service;
import pcbuilder.website.enums.Efficiency;
import pcbuilder.website.enums.ModularType;
import pcbuilder.website.enums.PSUType;
import pcbuilder.website.models.entities.products.PSU;
import pcbuilder.website.repositories.PSUDao;
import pcbuilder.website.services.PSUService;

import java.util.List;
import java.util.Optional;

@Service
public class PSUServiceImpl implements PSUService {

    private final PSUDao psuDao;

    public PSUServiceImpl(PSUDao psuDao) {
        this.psuDao = psuDao;
    }

    @Override
    public PSU save(PSU psu) { return psuDao.save(psu); }

    @Override
    public void delete(PSU psu) { psuDao.delete(psu); }

    @Override
    public PSU update(PSU psu) { return psuDao.update(psu); }

    @Override
    public PSU partialUpdate(long id, PSU psu) {
        psu.setProductID(id);

        return psuDao.findById(id).map(existing -> {
            Optional.ofNullable(psu.getType()).ifPresent(existing::setType);
            Optional.ofNullable(psu.getEfficiency()).ifPresent(existing::setEfficiency);
            Optional.ofNullable(psu.getWattage()).ifPresent(existing::setWattage);
            Optional.ofNullable(psu.getModular()).ifPresent(existing::setModular);
            Optional.ofNullable(psu.getColor()).ifPresent(existing::setColor);

            Optional.ofNullable(psu.getName()).ifPresent(existing::setName);
            Optional.ofNullable(psu.getPrice()).ifPresent(existing::setPrice);
            Optional.ofNullable(psu.getDescription()).ifPresent(existing::setDescription);
            Optional.ofNullable(psu.getImageUrl()).ifPresent(existing::setImageUrl);
            Optional.ofNullable(psu.getStock()).ifPresent(existing::setStock);

            return psuDao.update(existing);
        }).orElseThrow(() -> new RuntimeException("PSU not found"));
    }

    @Override
    public Optional<PSU> findById(long id) { return psuDao.findById(id); }

    @Override
    public List<PSU> findAll() { return psuDao.findAll(); }

    @Override
    public boolean exists(long id) { return psuDao.exists(id); }

    @Override
    public List<PSU> filterPSUs(PSUType type, Efficiency efficiency, Integer minWattage, Integer maxWattage, ModularType modular, String color, Double minPrice, Double maxPrice) {
        return psuDao.filterByCriteria(type, efficiency, minWattage, maxWattage, modular, color, minPrice, maxPrice);
    }
}
