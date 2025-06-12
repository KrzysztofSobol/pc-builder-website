package pcbuilder.website.services.impl.products;

import org.springframework.stereotype.Service;
import pcbuilder.website.models.entities.products.RAM;
import pcbuilder.website.repositories.RAMDao;
import pcbuilder.website.services.RAMService;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class RAMServiceImpl implements RAMService {
    private final static Logger log =
            Logger.getLogger(RAMServiceImpl.class.getName());
    private final RAMDao ramDao;

    public RAMServiceImpl(RAMDao ramDao) {
        try {
            log.finer("Initializing RAM Service...");
            this.ramDao = ramDao;
        } catch (Exception e) {
            log.severe("Failed to initialize RAM Service error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public RAM save(RAM ram) {
        try {
            log.fine("Saving RAM...");
            return ramDao.save(ram);
        } catch (Exception e) {
            log.severe("Failed to save error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(RAM ram) {
        try {
            log.fine("Deleting RAM...");
            ramDao.delete(ram);
        } catch (Exception e) {
            log.severe("Failed to delete error : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public RAM update(RAM ram) {
        try {
            log.fine("Updating RAM...");
            return ramDao.update(ram);
        } catch (Exception e) {
            log.severe("Failed to update error :" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public RAM partialUpdate(long id, RAM ram) {
        try {
            log.fine("Partial update of RAM...");
            ram.setProductID(id);
            return ramDao.findById(id).map(existingRAM -> {
                Optional.ofNullable(ram.getName()).ifPresent(existingRAM::setName);
                Optional.ofNullable(ram.getPrice()).ifPresent(existingRAM::setPrice);
                Optional.ofNullable(ram.getDescription()).ifPresent(existingRAM::setDescription);
                Optional.ofNullable(ram.getImageUrl()).ifPresent(existingRAM::setImageUrl);
                Optional.ofNullable(ram.getStock()).ifPresent(existingRAM::setStock);

                Optional.ofNullable(ram.getDdrGen()).ifPresent(existingRAM::setDdrGen);
                Optional.ofNullable(ram.getSpeed()).ifPresent(existingRAM::setSpeed);
                Optional.ofNullable(ram.getModuleCount()).ifPresent(existingRAM::setModuleCount);
                Optional.ofNullable(ram.getTotalCapacity()).ifPresent(existingRAM::setTotalCapacity);
                Optional.ofNullable(ram.getColor()).ifPresent(existingRAM::setColor);
                Optional.ofNullable(ram.getFirstWordLatency()).ifPresent(existingRAM::setFirstWordLatency);
                return ramDao.update(existingRAM);
            }).orElseThrow(() -> new RuntimeException("RAM not found"));
        } catch (RuntimeException e) {
            log.warning("Failed to partial update error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<RAM> findById(long id) {
        try {
            log.fine("Finding RAM...");
            return ramDao.findById(id);
        } catch (Exception e) {
            log.severe("Failed to find error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<RAM> findAll() {
        try {
            log.fine("Finding all RAMs...");
            return ramDao.findAll();
        } catch (Exception e) {
            log.severe("Failed to find all error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean exists(long id) {
        try {
            log.fine("Checking if RAM exists...");
            return ramDao.exists(id);
        } catch (Exception e) {
            log.severe("Failed to check if RAM exists error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<RAM> filterRAMs(String name, Double minPrice, Double maxPrice, Integer minClockSpeed, Integer maxClockSpeed, Integer ddrGen, Integer minModuleCount, Integer maxModuleCount, Integer minCapacity, Integer maxCapacity, String color) {
        try {
            log.fine("Filtering RAMs...");
            return ramDao.filterByCriteria(name, minPrice, maxPrice, minClockSpeed, maxClockSpeed, ddrGen, minModuleCount, maxModuleCount, minCapacity, maxCapacity, color);
        } catch (Exception e) {
            log.severe("Failed to filter RAMs error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
