package pcbuilder.website.repositories.impl.prodcuts;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import pcbuilder.website.models.entities.products.Motherboard;
import pcbuilder.website.repositories.MotherboardDao;
import pcbuilder.website.repositories.impl.GenericDaoImpl;

import java.util.List;
@Repository
public class MotherboardDaoImpl extends GenericDaoImpl<Motherboard, Long> implements MotherboardDao {
    @Override
    public List<Motherboard> filterByCriteria(String name, String socket, String formFactor, Integer maxMemory,
                                       Integer minMemorySlots, Integer maxMemorySlots, String color,Double minPrice, Double maxPrice){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Motherboard> cq = cb.createQuery(Motherboard.class);
        Root<Motherboard> root = cq.from(Motherboard.class);

        Predicate predicate = cb.conjunction();

        if(name != null && !name.isEmpty()){
            predicate = cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        }
        if(socket != null && !socket.isEmpty()){
            predicate = cb.and(predicate, cb.equal(root.get("socket"), socket));
        }
        if(formFactor != null && !formFactor.isEmpty()){
            predicate = cb.and(predicate, cb.equal(root.get("formFactor"), formFactor));
        }
        if(maxMemory != null && maxMemory > 0){
            predicate = cb.and(predicate, cb.greaterThan(root.get("maxMemory"), maxMemory));
        }
        if(minMemorySlots != null && minMemorySlots > 0){
            predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("minMemorySlots"), minMemorySlots));
        }
        if(maxMemorySlots != null && maxMemorySlots > 0){
            predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("maxMemorySlots"), maxMemorySlots));
        }
        if(color != null && !color.isEmpty()){
            predicate = cb.and(predicate, cb.equal(root.get("color"), color));
        }
        if(minPrice != null && minPrice > 0){
            predicate = cb.and(predicate, cb.greaterThan(root.get("minPrice"), minPrice));
        }
        if(maxPrice != null && maxPrice > 0){
            predicate = cb.and(predicate, cb.lessThan(root.get("maxPrice"), maxPrice));
        }
        cq.where(predicate);
        return em.createQuery(cq).getResultList();
    }
}
