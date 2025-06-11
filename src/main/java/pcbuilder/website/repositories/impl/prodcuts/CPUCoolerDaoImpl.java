package pcbuilder.website.repositories.impl.prodcuts;

import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import pcbuilder.website.models.entities.products.CPUCooler;
import pcbuilder.website.repositories.CPUCoolerDao;
import pcbuilder.website.repositories.impl.GenericDaoImpl;

import java.util.List;

@Repository
public class CPUCoolerDaoImpl extends GenericDaoImpl<CPUCooler, Long> implements CPUCoolerDao {

    @Override
    public List<CPUCooler> filterByCriteria(Integer minRPM, Integer maxRPM, Integer minNoise, Integer maxNoise,
                                            String color, Integer minHeight, Integer maxHeight,
                                            Double minPrice, Double maxPrice) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CPUCooler> cq = cb.createQuery(CPUCooler.class);
        Root<CPUCooler> root = cq.from(CPUCooler.class);
        Predicate predicate = cb.conjunction();

        if (minRPM != null) predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("minRPM"), minRPM));
        if (maxRPM != null) predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("maxRPM"), maxRPM));
        if (minNoise != null) predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("minNoiseLevel"), minNoise));
        if (maxNoise != null) predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("maxNoiseLevel"), maxNoise));
        if (color != null && !color.isEmpty()) predicate = cb.and(predicate, cb.like(cb.lower(root.get("color")), "%" + color.toLowerCase() + "%"));
        if (minHeight != null) predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("height"), minHeight));
        if (maxHeight != null) predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("height"), maxHeight));
        if (minPrice != null) predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("price"), minPrice));
        if (maxPrice != null) predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("price"), maxPrice));

        cq.where(predicate);
        return em.createQuery(cq).getResultList();
    }
}