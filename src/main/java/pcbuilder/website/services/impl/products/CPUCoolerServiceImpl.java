package pcbuilder.website.services.impl.products;

import org.springframework.stereotype.Service;
import pcbuilder.website.models.entities.products.CPUCooler;
import pcbuilder.website.repositories.CPUCoolerDao;
import pcbuilder.website.services.CPUCoolerService;

import java.util.List;
import java.util.Optional;

@Service
public class CPUCoolerServiceImpl implements CPUCoolerService {

    private final CPUCoolerDao dao;

    public CPUCoolerServiceImpl(CPUCoolerDao dao) {
        this.dao = dao;
    }

    @Override
    public CPUCooler save(CPUCooler cooler) { return dao.save(cooler); }

    @Override
    public void delete(CPUCooler cooler) { dao.delete(cooler); }

    @Override
    public CPUCooler update(CPUCooler cooler) { return dao.update(cooler); }

    @Override
    public CPUCooler partialUpdate(long id, CPUCooler cooler) {
        cooler.setProductID(id);
        return dao.findById(id).map(existing -> {
            Optional.ofNullable(cooler.getMinRPM()).ifPresent(existing::setMinRPM);
            Optional.ofNullable(cooler.getMaxRPM()).ifPresent(existing::setMaxRPM);
            Optional.ofNullable(cooler.getMinNoiseLevel()).ifPresent(existing::setMinNoiseLevel);
            Optional.ofNullable(cooler.getMaxNoiseLevel()).ifPresent(existing::setMaxNoiseLevel);
            Optional.ofNullable(cooler.getColor()).ifPresent(existing::setColor);
            Optional.ofNullable(cooler.getHeight()).ifPresent(existing::setHeight);

            Optional.ofNullable(cooler.getName()).ifPresent(existing::setName);
            Optional.ofNullable(cooler.getPrice()).ifPresent(existing::setPrice);
            Optional.ofNullable(cooler.getDescription()).ifPresent(existing::setDescription);
            Optional.ofNullable(cooler.getImageUrl()).ifPresent(existing::setImageUrl);
            Optional.ofNullable(cooler.getStock()).ifPresent(existing::setStock);

            return dao.update(existing);
        }).orElseThrow(() -> new RuntimeException("Cooler not found"));
    }

    @Override
    public Optional<CPUCooler> findById(long id) { return dao.findById(id); }

    @Override
    public List<CPUCooler> findAll() { return dao.findAll(); }

    @Override
    public boolean exists(long id) { return dao.exists(id); }

    @Override
    public List<CPUCooler> filterCoolers(Integer minRPM, Integer maxRPM, Integer minNoise, Integer maxNoise, String color, Integer minHeight, Integer maxHeight, Double minPrice, Double maxPrice) {
        return dao.filterByCriteria(minRPM, maxRPM, minNoise, maxNoise, color, minHeight, maxHeight, minPrice, maxPrice);
    }
}
