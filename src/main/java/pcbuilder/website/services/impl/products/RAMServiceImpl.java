package pcbuilder.website.services.impl.products;

import org.springframework.stereotype.Service;
import pcbuilder.website.models.entities.products.RAM;
import pcbuilder.website.repositories.RAMDao;
import pcbuilder.website.services.RAMService;

import java.util.List;
import java.util.Optional;

@Service
public class RAMServiceImpl implements RAMService {
    private final RAMDao ramDao;

    public RAMServiceImpl(RAMDao ramDao) {this.ramDao = ramDao;}

    @Override
    public RAM save(RAM ram) {
        return ramDao.save(ram);
    }

    @Override
    public void delete(RAM ram) {
        ramDao.delete(ram);
    }

    @Override
    public RAM update(RAM ram) {
        return ramDao.update(ram);
    }

    @Override
    public RAM partialUpdate(long id, RAM ram) {
        ram.setProductID(id);
        return ramDao.findById(id).map(existingRAM -> {
            Optional.ofNullable(ram.getName()).ifPresent(existingRAM::setName);
            Optional.ofNullable(ram.getPrice()).ifPresent(existingRAM::setPrice);
            Optional.ofNullable(ram.getDescription()).ifPresent(existingRAM::setDescription);
            Optional.ofNullable(ram.getImageUrl()).ifPresent(existingRAM::setImageUrl);
            Optional.ofNullable(ram.getStock()).ifPresent(existingRAM::setStock);

            Optional.ofNullable(ram.getDdrGen()).ifPresent(existingRAM::setDdrGen);
            Optional.ofNullable(ram.getSpeed()).ifPresent(existingRAM::setSpeed);
            Optional.ofNullable(ram.getModuleCount()).ifPresent(existingRAM::setModuleCount);
            Optional.ofNullable(ram.getTotalCapacity()).ifPresent(existingRAM::setTotalCapacity);
            Optional.ofNullable(ram.getColor()).ifPresent(existingRAM::setColor);
            Optional.ofNullable(ram.getFirstWordLatency()).ifPresent(existingRAM::setFirstWordLatency);
            return ramDao.update(existingRAM);
        }).orElseThrow(() -> new RuntimeException("RAM not found"));
    }

    @Override
    public Optional<RAM> findById(long id) {
        return ramDao.findById(id);
    }

    @Override
    public List<RAM> findAll() {
        return ramDao.findAll();
    }

    @Override
    public boolean exists(long id) {
        return ramDao.exists(id);
    }

    @Override
    public List<RAM> filterRAMs(String name, Double minPrice, Double maxPrice, Integer minClockSpeed, Integer maxClockSpeed, Integer ddrGen, Integer minModuleCount, Integer maxModuleCount, Integer minCapacity, Integer maxCapacity, String color) {
        return ramDao.filterByCriteria(name, minPrice, maxPrice, minClockSpeed, maxClockSpeed, ddrGen, minModuleCount, maxModuleCount, minCapacity, maxCapacity, color);
    }
}
