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
import java.util.logging.Logger;

@Service
public class PSUServiceImpl implements PSUService {
    private final static Logger log =
            Logger.getLogger(PSUServiceImpl.class.getName());

    private final PSUDao psuDao;

    public PSUServiceImpl(PSUDao psuDao) {
        try {
            log.finer("Initializing PSU Service...");
            this.psuDao = psuDao;
        } catch (Exception e) {
            log.severe("Failed to initialize PSU Service error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public PSU save(PSU psu) {
        try {
            log.fine("Saving PSU...");
            return psuDao.save(psu);
        } catch (Exception e) {
            log.severe("Failed to save PSU error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(PSU psu) {
        try {
            log.fine("Deleting PSU...");
            psuDao.delete(psu);
        } catch (Exception e) {
            log.severe("Failed to delete PSU error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public PSU update(PSU psu) {
        try {
            log.fine("Updating PSU...");
            return psuDao.update(psu);
        } catch (Exception e) {
            log.severe("Failed to update PSU error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public PSU partialUpdate(long id, PSU psu) {
        try {
            log.fine("Partial update of PSU...");
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
        } catch (RuntimeException e) {
            log.warning("Failed to partial update error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<PSU> findById(long id) {
        try {
            log.fine("Finding PSU...");
            return psuDao.findById(id);
        } catch (Exception e) {
            log.severe("Failed to find PSU error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PSU> findAll() {
        try {
            log.fine("Finding all PSU...");
            return psuDao.findAll();
        } catch (Exception e) {
            log.severe("Failed to find all PSU error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean exists(long id) {
        try {
            log.fine("Checking if PSU exists...");
            return psuDao.exists(id);
        } catch (Exception e) {
            log.severe("Failed to check if PSU exists error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PSU> filterPSUs(PSUType type, Efficiency efficiency, Integer minWattage, Integer maxWattage, ModularType modular, String color, Double minPrice, Double maxPrice) {
        try {
            log.fine("Filtering PSU...");
            return psuDao.filterByCriteria(type, efficiency, minWattage, maxWattage, modular, color, minPrice, maxPrice);
        } catch (Exception e) {
            log.severe("Failed to filter PSU error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
