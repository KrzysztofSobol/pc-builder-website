package pcbuilder.website.services.impl.products;

import org.springframework.stereotype.Service;
import pcbuilder.website.enums.PanelType;
import pcbuilder.website.models.entities.products.Case;
import pcbuilder.website.repositories.CaseDao;
import pcbuilder.website.services.CaseService;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class CaseServiceImpl implements CaseService {
    private final static Logger log =
            Logger.getLogger(CaseServiceImpl.class.getName());
    private final CaseDao dao;

    public CaseServiceImpl(CaseDao dao) {
        try {
            log.finer("Initializing Case Service...");
            this.dao = dao;
        } catch (Exception e) {
            log.severe("Failed to initialize Case Service error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Case save(Case c) {
        try {
            log.fine("Saving case...");
            return dao.save(c);
        } catch (Exception e) {
            log.severe("Failed to save case error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Case c) {
        try {
            log.fine("Deleting case...");
            dao.delete(c);
        } catch (Exception e) {
            log.severe("Failed to delete case error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Case update(Case c) {
        try {
            log.fine("Updating case...");
            return dao.update(c);
        } catch (Exception e) {
            log.severe("Failed to update case error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Case partialUpdate(long id, Case c) {
        try {
            log.fine("Partial update of case...");
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
        } catch (RuntimeException e) {
            log.warning("Failed to partial update error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Case> findById(long id) {
        try {
            log.fine("Finding case...");
            return dao.findById(id);
        } catch (Exception e) {
            log.severe("Failed to find case error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Case> findAll() {
        try {
            log.fine("Finding all cases...");
            return dao.findAll();
        } catch (Exception e) {
            log.severe("Failed to find all cases error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean exists(long id) {
        try {
            log.fine("Checking if case exists...");
            return dao.exists(id);
        } catch (Exception e) {
            log.severe("Failed to check if case exists error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Case> filterCases(String type, String color, PanelType sidePanel, Double minPrice, Double maxPrice) {

        try {
            log.fine("Filtering cases...");
            return dao.filterByCriteria(type, color, sidePanel, minPrice, maxPrice);
        } catch (Exception e) {
            log.severe("Failed to filter cases error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
