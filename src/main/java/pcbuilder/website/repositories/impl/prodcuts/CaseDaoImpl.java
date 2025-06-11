package pcbuilder.website.repositories.impl.prodcuts;

import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import pcbuilder.website.enums.PanelType;
import pcbuilder.website.models.entities.products.Case;
import pcbuilder.website.repositories.CaseDao;
import pcbuilder.website.repositories.impl.GenericDaoImpl;

import java.util.List;

@Repository
public class CaseDaoImpl extends GenericDaoImpl<Case, Long> implements CaseDao {

    @Override
    public List<Case> filterByCriteria(String type, String color, PanelType sidePanel, Double minPrice, Double maxPrice) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Case> cq = cb.createQuery(Case.class);
        Root<Case> root = cq.from(Case.class);
        Predicate predicate = cb.conjunction();

        if (type != null && !type.isEmpty()) predicate = cb.and(predicate, cb.like(cb.lower(root.get("type")), "%" + type.toLowerCase() + "%"));
        if (color != null && !color.isEmpty()) predicate = cb.and(predicate, cb.like(cb.lower(root.get("color")), "%" + color.toLowerCase() + "%"));
        if (sidePanel != null) predicate = cb.and(predicate, cb.equal(root.get("sidePanel"), sidePanel));
        if (minPrice != null) predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("price"), minPrice));
        if (maxPrice != null) predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("price"), maxPrice));

        cq.where(predicate);
        return em.createQuery(cq).getResultList();
    }
}