package pcbuilder.website.repositories.impl.prodcuts;

import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import pcbuilder.website.enums.Efficiency;
import pcbuilder.website.enums.ModularType;
import pcbuilder.website.enums.PSUType;
import pcbuilder.website.models.entities.products.PSU;
import pcbuilder.website.repositories.PSUDao;
import pcbuilder.website.repositories.impl.GenericDaoImpl;

import java.util.List;

@Repository
public class PSUDaoImpl extends GenericDaoImpl<PSU, Long> implements PSUDao {

    @Override
    public List<PSU> filterByCriteria(PSUType type, Efficiency efficiency, Integer minWattage, Integer maxWattage,
                                      ModularType modular, String color, Double minPrice, Double maxPrice) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PSU> cq = cb.createQuery(PSU.class);
        Root<PSU> root = cq.from(PSU.class);
        Predicate predicate = cb.conjunction();

        if (type != null) predicate = cb.and(predicate, cb.equal(root.get("type"), type));
        if (efficiency != null) predicate = cb.and(predicate, cb.equal(root.get("efficiency"), efficiency));
        if (minWattage != null) predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("wattage"), minWattage));
        if (maxWattage != null) predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("wattage"), maxWattage));
        if (modular != null) predicate = cb.and(predicate, cb.equal(root.get("modular"), modular));
        if (color != null && !color.isEmpty()) predicate = cb.and(predicate, cb.like(cb.lower(root.get("color")), "%" + color.toLowerCase() + "%"));
        if (minPrice != null) predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("price"), minPrice));
        if (maxPrice != null) predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("price"), maxPrice));

        cq.where(predicate);
        return em.createQuery(cq).getResultList();
    }
}