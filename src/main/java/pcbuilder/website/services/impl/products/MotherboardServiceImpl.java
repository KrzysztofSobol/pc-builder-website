package pcbuilder.website.services.impl.products;

import org.springframework.stereotype.Service;
import pcbuilder.website.models.entities.products.Motherboard;
import pcbuilder.website.repositories.MotherboardDao;
import pcbuilder.website.services.MotherboardService;

import java.util.List;
import java.util.Optional;

@Service
public class MotherboardServiceImpl implements MotherboardService {
    private final MotherboardDao motherboardDao;
    public MotherboardServiceImpl(MotherboardDao motherboardDao) {this.motherboardDao = motherboardDao;}

    @Override
    public Motherboard save(Motherboard motherboard) {return motherboardDao.save(motherboard);}
    @Override
    public void delete(Motherboard motherboard) {motherboardDao.delete(motherboard);}
    @Override
    public Motherboard update(Motherboard motherboard) {return motherboardDao.update(motherboard);}
    @Override
    public Motherboard partialUpdate(long id, Motherboard motherboard) {
        motherboard.setProductID(id);
        return motherboardDao.findById(id).map(existingMotherboard ->{
            Optional.ofNullable(existingMotherboard.getName()).ifPresent(existingMotherboard::setName);
            Optional.ofNullable(existingMotherboard.getPrice()).ifPresent(existingMotherboard::setPrice);
            Optional.ofNullable(existingMotherboard.getDescription()).ifPresent(existingMotherboard::setDescription);
            Optional.ofNullable(existingMotherboard.getImageUrl()).ifPresent(existingMotherboard::setImageUrl);
            Optional.ofNullable(existingMotherboard.getStock()).ifPresent(existingMotherboard::setStock);

            Optional.ofNullable(existingMotherboard.getSocket()).ifPresent(existingMotherboard::setSocket);
            Optional.ofNullable(existingMotherboard.getFormFactor()).ifPresent(existingMotherboard::setFormFactor);
            Optional.ofNullable(existingMotherboard.getMaxMemory()).ifPresent(existingMotherboard::setMaxMemory);
            Optional.ofNullable(existingMotherboard.getMemorySlots()).ifPresent(existingMotherboard::setMemorySlots);
            Optional.ofNullable(existingMotherboard.getColor()).ifPresent(existingMotherboard::setColor);

            return motherboardDao.save(existingMotherboard);
        }).orElseThrow(() -> new RuntimeException("Motherboard not found"));
    }
    @Override
    public Optional<Motherboard> findById(long id) {return motherboardDao.findById(id);}
    @Override
    public List<Motherboard> findAll() {return motherboardDao.findAll();}
    @Override
    public Boolean exists(long id){return motherboardDao.exists(id);}
    @Override
    public List<Motherboard> filterMotherboards(String name, String socket, String formFactor, Integer maxMemory,
                                                Integer minMemorySlots, Integer maxMemorySlots, String color, Double minPrice, Double maxPrice){
        return motherboardDao.filterByCriteria(name,socket, formFactor, maxMemory,minMemorySlots,maxMemorySlots,color,minPrice,maxPrice);
    }

}
