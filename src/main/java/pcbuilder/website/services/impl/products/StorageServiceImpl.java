package pcbuilder.website.services.impl.products;

import org.springframework.stereotype.Service;
import pcbuilder.website.enums.StorageType;
import pcbuilder.website.models.entities.products.Storage;
import pcbuilder.website.repositories.StorageDao;
import pcbuilder.website.services.StorageService;

import java.util.List;
import java.util.Optional;

@Service
public class StorageServiceImpl implements StorageService {
    private final StorageDao storageDao;

    public StorageServiceImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }
    @Override
    public Storage save(Storage storage) {
        return storageDao.save(storage);
    }

    @Override
    public void delete(Storage storage) {
        storageDao.delete(storage);
    }

    @Override
    public Storage update(Storage storage) {
        return storageDao.update(storage);
    }

    @Override
    public Storage partialUpdate(long id, Storage storage) {
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
    }

    @Override
    public Optional<Storage> findById(long id) {
        return storageDao.findById(id);
    }

    @Override
    public List<Storage> findAll() {
        return storageDao.findAll();
    }

    @Override
    public boolean exists(long id) {
        return storageDao.exists(id);
    }

    @Override
    public List<Storage> filterStorages(String name, Double minPrice, Double maxPrice, Integer minCapacity, Integer maxCapacity, StorageType storageType) {
        return storageDao.filterByCriteria(name, minPrice, maxPrice, minCapacity, maxCapacity, storageType);
    }
}
