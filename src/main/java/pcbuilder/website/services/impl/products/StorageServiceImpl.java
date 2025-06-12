package pcbuilder.website.services.impl.products;

import org.springframework.stereotype.Service;
import pcbuilder.website.enums.StorageType;
import pcbuilder.website.models.entities.products.Storage;
import pcbuilder.website.repositories.StorageDao;
import pcbuilder.website.services.StorageService;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class StorageServiceImpl implements StorageService {
    private final static Logger log =
            Logger.getLogger(StorageServiceImpl.class.getName());
    private final StorageDao storageDao;

    public StorageServiceImpl(StorageDao storageDao) {
        try {
            log.finer("Initializing Storage Service...");
            this.storageDao = storageDao;
        } catch (Exception e) {
            log.severe("Failed to initialize Storage Service error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @Override
    public Storage save(Storage storage) {
        try {
            log.fine("Saving Storage...");
            return storageDao.save(storage);
        } catch (Exception e) {
            log.severe("Failed to save error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Storage storage) {
        try {
            log.fine("Deleting Storage...");
            storageDao.delete(storage);
        } catch (Exception e) {
            log.severe("Failed to delete error : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Storage update(Storage storage) {
        try {
            log.fine("Updating Storage...");
            return storageDao.update(storage);
        } catch (Exception e) {
            log.severe("Failed to update error :" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Storage partialUpdate(long id, Storage storage) {

        try {
            log.fine("Partial update of Storage...");
            storage.setProductID(id);
            return storageDao.findById(id).map(existingStorage ->{
                Optional.ofNullable(storage.getName()).ifPresent(existingStorage::setName);
                Optional.ofNullable(storage.getPrice()).ifPresent(existingStorage::setPrice);
                Optional.ofNullable(storage.getDescription()).ifPresent(existingStorage::setDescription);
                Optional.ofNullable(storage.getImageUrl()).ifPresent(existingStorage::setImageUrl);
                Optional.ofNullable(storage.getStock()).ifPresent(existingStorage::setStock);

                Optional.ofNullable(storage.getCapacity()).ifPresent(existingStorage::setCapacity);
                Optional.ofNullable(storage.getType()).ifPresent(existingStorage::setType);
                Optional.ofNullable(storage.getCache()).ifPresent(existingStorage::setCache);
                Optional.ofNullable(storage.getFormFactor()).ifPresent(existingStorage::setFormFactor);
                Optional.ofNullable(storage.getInterFace()).ifPresent(existingStorage::setInterFace);
                return storageDao.update(existingStorage);
            }).orElseThrow(() -> new RuntimeException("Storage not found"));
        } catch (RuntimeException e) {
            log.warning("Failed to partial update error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Storage> findById(long id) {
        try {
            log.fine("Finding Storage...");
            return storageDao.findById(id);
        } catch (Exception e) {
            log.severe("Failed to find error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Storage> findAll() {
        try {
            log.fine("Finding all Storages...");
            return storageDao.findAll();
        } catch (Exception e) {
            log.severe("Failed to find all error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean exists(long id) {
        try {
            log.fine("Checking if Storage exists...");
            return storageDao.exists(id);
        } catch (Exception e) {
            log.severe("Failed to check if Storage exists error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Storage> filterStorages(String name, Double minPrice, Double maxPrice, Integer minCapacity, Integer maxCapacity, StorageType storageType) {
        try {
            log.fine("Filtering Storages...");
            return storageDao.filterByCriteria(name, minPrice, maxPrice, minCapacity, maxCapacity, storageType);
        } catch (Exception e) {
            log.severe("Failed to filter Storages error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
