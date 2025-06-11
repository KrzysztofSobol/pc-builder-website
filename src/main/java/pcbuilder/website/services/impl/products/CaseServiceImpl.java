package pcbuilder.website.services.impl.products;

import org.springframework.stereotype.Service;
import pcbuilder.website.enums.PanelType;
import pcbuilder.website.models.entities.products.Case;
import pcbuilder.website.repositories.CaseDao;
import pcbuilder.website.services.CaseService;

import java.util.List;
import java.util.Optional;

@Service
public class CaseServiceImpl implements CaseService {

    private final CaseDao dao;

    public CaseServiceImpl(CaseDao dao) {
        this.dao = dao;
    }

    @Override
    public Case save(Case c) { return dao.save(c); }

    @Override
    public void delete(Case c) { dao.delete(c); }

    @Override
    public Case update(Case c) { return dao.update(c); }

    @Override
    public Case partialUpdate(long id, Case c) {
        c.setProductID(id);
        return dao.findById(id).map(existing -> {
            Optional.ofNullable(c.getType()).ifPresent(existing::setType);
            Optional.ofNullable(c.getColor()).ifPresent(existing::setColor);
            Optional.ofNullable(c.getSidePanel()).ifPresent(existing::setSidePanel);

            Optional.ofNullable(c.getName()).ifPresent(existing::setName);
            Optional.ofNullable(c.getPrice()).ifPresent(existing::setPrice);
            Optional.ofNullable(c.getDescription()).ifPresent(existing::setDescription);
            Optional.ofNullable(c.getImageUrl()).ifPresent(existing::setImageUrl);
            Optional.ofNullable(c.getStock()).ifPresent(existing::setStock);

            return dao.update(existing);
        }).orElseThrow(() -> new RuntimeException("Case not found"));
    }

    @Override
    public Optional<Case> findById(long id) { return dao.findById(id); }

    @Override
    public List<Case> findAll() { return dao.findAll(); }

    @Override
    public boolean exists(long id) { return dao.exists(id); }

    @Override
    public List<Case> filterCases(String type, String color, PanelType sidePanel, Double minPrice, Double maxPrice) {
        return dao.filterByCriteria(type, color, sidePanel, minPrice, maxPrice);
    }
}
