package pcbuilder.website.repositories;

import pcbuilder.website.enums.StorageType;
import pcbuilder.website.models.entities.products.Storage;

import java.util.List;

public interface StorageDao extends GenericDao<Storage, Long> {
    List<Storage> filterByCriteria(String name, Double minPrice, Double maxPrice, Integer minCapacity, Integer maxCapacity, StorageType storageType);
}
