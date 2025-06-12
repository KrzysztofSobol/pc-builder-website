package pcbuilder.website.services.impl.products;

import org.springframework.stereotype.Service;
import pcbuilder.website.models.entities.products.Motherboard;
import pcbuilder.website.repositories.MotherboardDao;
import pcbuilder.website.services.MotherboardService;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class MotherboardServiceImpl implements MotherboardService {
    private final static Logger log =
            Logger.getLogger(MotherboardServiceImpl.class.getName());
    private final MotherboardDao motherboardDao;

    public MotherboardServiceImpl(MotherboardDao motherboardDao) {
        try {
            log.finer("Initializing Motherboard Service...");
            this.motherboardDao = motherboardDao;
        } catch (Exception e) {
            log.severe("Failed to initialize Motherboard Service error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Motherboard save(Motherboard motherboard) {
        try {
            log.fine("Saving Motherboard...");
            return motherboardDao.save(motherboard);
        } catch (Exception e) {
            log.severe("Failed to save error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @Override
    public void delete(Motherboard motherboard) {
        try {
            log.fine("Deleting Motherboard...");
            motherboardDao.delete(motherboard);
        } catch (Exception e) {
            log.severe("Failed to delete error : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @Override
    public Motherboard update(Motherboard motherboard) {
        try {
            log.fine("Updating Motherboard...");
            return motherboardDao.update(motherboard);
        } catch (Exception e) {
            log.severe("Failed to update error :" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @Override
    public Motherboard partialUpdate(long id, Motherboard mb) {
        try {
            log.fine("Partial update of Motherboard...");
            mb.setProductID(id);
            return motherboardDao.findById(id).map(existingMotherboard ->{
                Optional.ofNullable(mb.getName()).ifPresent(existingMotherboard::setName);
                Optional.ofNullable(mb.getPrice()).ifPresent(existingMotherboard::setPrice);
                Optional.ofNullable(mb.getDescription()).ifPresent(existingMotherboard::setDescription);
                Optional.ofNullable(mb.getImageUrl()).ifPresent(existingMotherboard::setImageUrl);
                Optional.ofNullable(mb.getStock()).ifPresent(existingMotherboard::setStock);

                Optional.ofNullable(mb.getSocket()).ifPresent(existingMotherboard::setSocket);
                Optional.ofNullable(mb.getFormFactor()).ifPresent(existingMotherboard::setFormFactor);
                Optional.ofNullable(mb.getMaxMemory()).ifPresent(existingMotherboard::setMaxMemory);
                Optional.ofNullable(mb.getMemorySlots()).ifPresent(existingMotherboard::setMemorySlots);
                Optional.ofNullable(mb.getColor()).ifPresent(existingMotherboard::setColor);

                return motherboardDao.save(existingMotherboard);
            }).orElseThrow(() -> new RuntimeException("Motherboard not found"));
        } catch (RuntimeException e) {
            log.warning("Failed to partial update error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @Override
    public Optional<Motherboard> findById(long id) {
        try {
            log.fine("Finding Motherboard...");
            return motherboardDao.findById(id);
        } catch (Exception e) {
            log.severe("Failed to find error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<Motherboard> findAll() {
        try {
            log.fine("Finding all Motherboards...");
            return motherboardDao.findAll();
        } catch (Exception e) {
            log.severe("Failed to find all error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @Override
    public Boolean exists(long id){
        try {
            log.fine("Checking if Motherboard exists...");
            return motherboardDao.exists(id);
        } catch (Exception e) {
            log.severe("Failed to check if Motherboard exists error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<Motherboard> filterMotherboards(String name, String socket, String formFactor, Integer maxMemory,
                                                Integer minMemorySlots, Integer maxMemorySlots, String color, Double minPrice, Double maxPrice){
        try {
            log.fine("Filtering Motherboards...");
            return motherboardDao.filterByCriteria(name,socket, formFactor, maxMemory,minMemorySlots,maxMemorySlots,color,minPrice,maxPrice);
        } catch (Exception e) {
            log.severe("Failed to filter Motherboards error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
