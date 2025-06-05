package pcbuilder.website.repositories.impl.prodcuts;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import pcbuilder.website.models.entities.products.CPU;
import pcbuilder.website.repositories.CPUDao;
import pcbuilder.website.repositories.impl.GenericDaoImpl;

import java.util.List;

@Repository
public class CPUDaoImpl extends GenericDaoImpl<CPU, Long> implements CPUDao {
    @Override
    public List<CPU> filterByCriteria(String name, String socket, Integer minCoreCount, Integer maxCoreCount, Double minCoreClock,
                                      Double maxCoreClock, Double minPrice, Double maxPrice,Boolean graphics, Boolean smt){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CPU> cq = cb.createQuery(CPU.class);
        Root<CPU> cpuRoot = cq.from(CPU.class);

        Predicate predicate = cb.conjunction();

        // Dodawanie dynamicznych warunków do zapytania:
        if(name != null && !name.isEmpty()){
            predicate = cb.like(cb.lower(cpuRoot.get("name")), "%" + name.toLowerCase() + "%");
        }
        if (socket != null && !socket.isEmpty()) {
            predicate = cb.and(predicate, cb.equal(cpuRoot.get("socket"), socket));
        }
        if (minCoreCount != null && minCoreCount > 0) {
            predicate = cb.and(predicate, cb.greaterThanOrEqualTo(cpuRoot.get("coreCount"), minCoreCount));
        }
        if(maxCoreCount != null && maxCoreCount > 0) {
            predicate = cb.and(predicate, cb.lessThanOrEqualTo(cpuRoot.get("coreCount"), maxCoreCount));
        }
        if (minCoreClock != null && minCoreClock > 0) {
            predicate = cb.and(predicate, cb.greaterThanOrEqualTo(cpuRoot.get("coreClock"), minCoreClock));
        }
        if (maxCoreClock != null && maxCoreClock > 0) {
            predicate = cb.and(predicate, cb.lessThanOrEqualTo(cpuRoot.get("coreClock"), maxCoreClock));
        }
        if (minPrice != null && minPrice > 0) {
            predicate = cb.and(predicate, cb.greaterThanOrEqualTo(cpuRoot.get("price"), minPrice));
        }
        if (maxPrice != null && maxPrice > 0) {
            predicate = cb.and(predicate, cb.lessThanOrEqualTo(cpuRoot.get("price"), maxPrice));
        }
        if (graphics != null) {
            predicate = cb.and(predicate, cb.equal(cpuRoot.get("graphics"), graphics));
        }
        if (smt != null) {
            predicate = cb.and(predicate, cb.equal(cpuRoot.get("smt"), smt));
        }
        cq.where(predicate);
        return em.createQuery(cq).getResultList();
    }

}
