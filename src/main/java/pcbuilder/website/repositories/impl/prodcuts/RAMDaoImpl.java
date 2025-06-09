package pcbuilder.website.repositories.impl.prodcuts;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import pcbuilder.website.models.entities.products.CPU;
import pcbuilder.website.models.entities.products.RAM;
import pcbuilder.website.repositories.RAMDao;
import pcbuilder.website.repositories.impl.GenericDaoImpl;

import java.util.List;

@Repository
public class RAMDaoImpl extends GenericDaoImpl<RAM, Long> implements RAMDao {
    @Override
    public List<RAM> filterByCriteria(String name, Double minPrice, Double maxPrice, Integer minClockSpeed, Integer maxClockSpeed,
                                      Integer ddrGen, Integer minModuleCount, Integer maxModuleCount, Integer minCapacity, Integer maxCapacity, String color) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<RAM> cq = cb.createQuery(RAM.class);
        Root<RAM> ramRoot = cq.from(RAM.class);

        Predicate predicate = cb.conjunction();
        if(name != null && !name.isEmpty()){
            predicate = cb.like(cb.lower(ramRoot.get("name")), "%" + name.toLowerCase() + "%");
        }
        if(minPrice != null && minPrice > 0){
            predicate = cb.and(predicate, cb.greaterThanOrEqualTo(ramRoot.get("price"), minPrice));
        }
        if(maxPrice != null && maxPrice > 0) {
            predicate = cb.and(predicate, cb.lessThanOrEqualTo(ramRoot.get("price"), maxPrice));
        }
        if(minClockSpeed != null && minClockSpeed > 0){
            predicate = cb.and(predicate, cb.greaterThanOrEqualTo(ramRoot.get("clockSpeed"), minClockSpeed));
        }
        if(maxClockSpeed != null && maxClockSpeed > 0){
            predicate = cb.and(predicate, cb.lessThanOrEqualTo(ramRoot.get("clockSpeed"), maxClockSpeed));
        }
        if(ddrGen != null){
            predicate = cb.and(predicate, cb.equal(ramRoot.get("ddrGen"), ddrGen));
        }
        if(minModuleCount != null && minModuleCount > 0){
            predicate = cb.and(predicate, cb.greaterThanOrEqualTo(ramRoot.get("moduleCount"), minModuleCount));
        }
        if(maxModuleCount != null && maxModuleCount > 0){
            predicate = cb.and(predicate, cb.lessThanOrEqualTo(ramRoot.get("moduleCount"), maxModuleCount));
        }
        if(minCapacity != null && minCapacity > 0){
            predicate = cb.and(predicate, cb.greaterThanOrEqualTo(ramRoot.get("capacity"), minCapacity));
        }
        if(maxCapacity != null && maxCapacity > 0){
            predicate = cb.and(predicate, cb.lessThanOrEqualTo(ramRoot.get("capacity"), maxCapacity));
        }
        if(color != null && !color.isEmpty()){
            predicate = cb.and(predicate, cb.equal(ramRoot.get("color"), color));
        }
        cq.where(predicate);
        return em.createQuery(cq).getResultList();

    }
}
