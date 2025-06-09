package pcbuilder.website.repositories.impl.prodcuts;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import pcbuilder.website.enums.StorageType;
import pcbuilder.website.models.entities.products.CPU;
import pcbuilder.website.models.entities.products.Storage;
import pcbuilder.website.repositories.StorageDao;
import pcbuilder.website.repositories.impl.GenericDaoImpl;

import java.util.List;

@Repository
public class StorageDaoImpl extends GenericDaoImpl<Storage, Long> implements StorageDao {
    @Override
    public List<Storage> filterByCriteria(String name, Double minPrice, Double maxPrice, Integer minCapacity, Integer maxCapacity, StorageType storageType){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Storage> cq = cb.createQuery(Storage.class);
        Root<Storage> storageRoot = cq.from(Storage.class);

        Predicate predicate = cb.conjunction();
        if(name != null && !name.isEmpty()){
            predicate = cb.like(cb.lower(storageRoot.get("name")), "%" + name.toLowerCase() + "%");
        }
        if(minPrice != null && minPrice > 0){
            predicate = cb.and(predicate, cb.greaterThanOrEqualTo(storageRoot.get("price"), minPrice));
        }
        if(maxPrice != null && maxPrice > 0){
            predicate = cb.and(predicate, cb.lessThanOrEqualTo(storageRoot.get("price"), maxPrice));
        }
        if(minCapacity != null && minCapacity > 0){
            predicate = cb.and(predicate, cb.greaterThanOrEqualTo(storageRoot.get("capacity"), minCapacity));
        }
        if(maxCapacity != null && maxCapacity > 0){
            predicate = cb.and(predicate, cb.lessThanOrEqualTo(storageRoot.get("capacity"), maxCapacity));
        }
        if(storageType != null){
            predicate = cb.and(predicate, cb.equal(storageRoot.get("storageType"), storageType));
        }
        cq.where(predicate);
        return em.createQuery(cq).getResultList();
    }
}
