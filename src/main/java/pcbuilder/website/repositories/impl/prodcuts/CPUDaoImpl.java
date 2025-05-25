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
    public List<CPU> filterByCriteria(String socket, Integer coreCount, Double minCoreClock, Double maxCoreClock, Double minPrice, Double maxPrice) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CPU> cq = cb.createQuery(CPU.class);
        Root<CPU> cpuRoot = cq.from(CPU.class);

        Predicate predicate = cb.conjunction();

        // Dodawanie dynamicznych warunków do zapytania:
        if (socket != null && !socket.isEmpty()) {
            predicate = cb.and(predicate, cb.equal(cpuRoot.get("socket"), socket));
        }
        if (coreCount != null) {
            predicate = cb.and(predicate, cb.equal(cpuRoot.get("coreCount"), coreCount));
        }
        if (minCoreClock != null) {
            predicate = cb.and(predicate, cb.greaterThanOrEqualTo(cpuRoot.get("coreClock"), minCoreClock));
        }
        if (maxCoreClock != null) {
            predicate = cb.and(predicate, cb.lessThanOrEqualTo(cpuRoot.get("coreClock"), maxCoreClock));
        }
        if (minPrice != null) {
            predicate = cb.and(predicate, cb.greaterThanOrEqualTo(cpuRoot.get("price"), minPrice));
        }
        if (maxPrice != null) {
            predicate = cb.and(predicate, cb.lessThanOrEqualTo(cpuRoot.get("price"), maxPrice));
        }
        cq.where(predicate);
        return em.createQuery(cq).getResultList();
    }

}
