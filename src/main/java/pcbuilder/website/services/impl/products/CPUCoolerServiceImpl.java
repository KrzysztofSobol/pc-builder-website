package pcbuilder.website.services.impl.products;

import org.springframework.stereotype.Service;
import pcbuilder.website.models.entities.products.CPUCooler;
import pcbuilder.website.repositories.CPUCoolerDao;
import pcbuilder.website.services.CPUCoolerService;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class CPUCoolerServiceImpl implements CPUCoolerService {
    private final static Logger log =
            Logger.getLogger(CPUCoolerServiceImpl.class.getName());

    private final CPUCoolerDao dao;

    public CPUCoolerServiceImpl(CPUCoolerDao dao) {
        try {
            log.finer("Initializing CPU Cooler Service...");
            this.dao = dao;
        } catch (Exception e) {
            log.severe("Failed to initialize CPU Cooler Service error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public CPUCooler save(CPUCooler cooler) {
        try {
            log.fine("Saving cooler...");
            return dao.save(cooler);
        } catch (Exception e) {
            log.severe("Failed to save cooler error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(CPUCooler cooler) {
        try {
            log.fine("Deleting cooler...");
            dao.delete(cooler);
        } catch (Exception e) {
            log.severe("Failed to delete cooler error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public CPUCooler update(CPUCooler cooler) {
        try {
            log.fine("Updating cooler...");
            return dao.update(cooler);
        } catch (Exception e) {
            log.severe("Failed to update cooler error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public CPUCooler partialUpdate(long id, CPUCooler cooler) {
        try {
            log.fine("Partial update of cooler...");
            cooler.setProductID(id);
            return dao.findById(id).map(existing -> {
                Optional.ofNullable(cooler.getMinRPM()).ifPresent(existing::setMinRPM);
                Optional.ofNullable(cooler.getMaxRPM()).ifPresent(existing::setMaxRPM);
                Optional.ofNullable(cooler.getMinNoiseLevel()).ifPresent(existing::setMinNoiseLevel);
                Optional.ofNullable(cooler.getMaxNoiseLevel()).ifPresent(existing::setMaxNoiseLevel);
                Optional.ofNullable(cooler.getColor()).ifPresent(existing::setColor);
                Optional.ofNullable(cooler.getHeight()).ifPresent(existing::setHeight);

                Optional.ofNullable(cooler.getName()).ifPresent(existing::setName);
                Optional.ofNullable(cooler.getPrice()).ifPresent(existing::setPrice);
                Optional.ofNullable(cooler.getDescription()).ifPresent(existing::setDescription);
                Optional.ofNullable(cooler.getImageUrl()).ifPresent(existing::setImageUrl);
                Optional.ofNullable(cooler.getStock()).ifPresent(existing::setStock);

                return dao.update(existing);
            }).orElseThrow(() -> new RuntimeException("Cooler not found"));
        } catch (RuntimeException e) {
            log.warning("Failed to partial update error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<CPUCooler> findById(long id) {
        try {
            log.fine("Finding cooler...");
            return dao.findById(id);
        } catch (Exception e) {
            log.severe("Failed to find cooler error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CPUCooler> findAll() {
        try {
            log.fine("Finding all coolers...");
            return dao.findAll();
        } catch (Exception e) {
            log.severe("Failed to find all coolers error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean exists(long id) {
        try {
            log.fine("Checking if cooler exists...");
            return dao.exists(id);
        } catch (Exception e) {
            log.severe("Failed to check if cooler exists error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CPUCooler> filterCoolers(Integer minRPM, Integer maxRPM, Integer minNoise, Integer maxNoise, String color, Integer minHeight, Integer maxHeight, Double minPrice, Double maxPrice) {
        try {
            log.fine("Filtering coolers...");
            return dao.filterByCriteria(minRPM, maxRPM, minNoise, maxNoise, color, minHeight, maxHeight, minPrice, maxPrice);
        } catch (Exception e) {
            log.severe("Failed to filter coolers error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
