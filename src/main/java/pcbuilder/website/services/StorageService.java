package pcbuilder.website.services;


import pcbuilder.website.enums.StorageType;
import pcbuilder.website.models.entities.products.Storage;

import java.util.List;
import java.util.Optional;

public interface StorageService {
    Storage save(Storage storage);
    void delete(Storage storage);
    Storage update(Storage storage);
    Storage partialUpdate(long id, Storage storage);
    Optional<Storage> findById(long id);
    List<Storage> findAll();
    boolean exists(long id);
    List<Storage> filterStorages(String name, Double minPrice, Double maxPrice, Integer minCapacity, Integer maxCapacity, StorageType storageType);
}
