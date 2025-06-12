package pcbuilder.website.services.impl.products;

import org.springframework.stereotype.Service;
import pcbuilder.website.models.entities.products.CPU;
import pcbuilder.website.repositories.CPUDao;
import pcbuilder.website.services.CPUService;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class CPUServiceImpl implements CPUService {
    private final static Logger log =
            Logger.getLogger(CPUServiceImpl.class.getName());
    private final CPUDao cpuDao;

    public CPUServiceImpl(CPUDao cpuDao) {
        try {
            log.finer("Initializing CPU Service...");
            this.cpuDao = cpuDao;
        } catch (Exception e) {
            log.severe("Failed to initialize CPU Service error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public CPU save(CPU cpu){
        try {
            log.fine("Saving CPU...");
            return cpuDao.save(cpu);
        } catch (Exception e) {
            log.severe("Failed to save error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @Override
    public void delete(CPU cpu){
        try {
            log.fine("Deleting CPU...");
            cpuDao.delete(cpu);
        } catch (Exception e) {
            log.severe("Failed to delete error : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @Override
    public CPU update(CPU cpu){
        try {
            log.fine("Updating CPU...");
            return cpuDao.update(cpu);
        } catch (Exception e) {
            log.severe("Failed to update error :" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @Override
    public CPU partialUpdate(long id, CPU cpu){
        try {
            log.fine("Partial update of CPU...");
            cpu.setProductID(id);
            return cpuDao.findById(id).map(existingCPU ->{
                Optional.ofNullable(existingCPU.getSocket()).ifPresent(existingCPU::setSocket);
                Optional.ofNullable(cpu.getCoreCount()).ifPresent(existingCPU::setCoreCount);
                Optional.ofNullable(cpu.getBoostClock()).ifPresent(existingCPU::setBoostClock);
                Optional.ofNullable(cpu.getCoreClock()).ifPresent(existingCPU::setCoreClock);
                Optional.ofNullable(cpu.getTdp()).ifPresent(existingCPU::setTdp);
                Optional.ofNullable(cpu.getGraphics()).ifPresent(existingCPU::setGraphics);
                Optional.ofNullable(cpu.getSmt()).ifPresent(existingCPU::setSmt);

                Optional.ofNullable(cpu.getName()).ifPresent(existingCPU::setName);
                Optional.ofNullable(cpu.getPrice()).ifPresent(existingCPU::setPrice);
                Optional.ofNullable(cpu.getDescription()).ifPresent(existingCPU::setDescription);
                Optional.ofNullable(cpu.getImageUrl()).ifPresent(existingCPU::setImageUrl);
                Optional.ofNullable(cpu.getStock()).ifPresent(existingCPU::setStock);

                return cpuDao.update(existingCPU);
            }).orElseThrow(() -> new RuntimeException("CPU not found"));
        } catch (RuntimeException e) {
            log.warning("Failed to partial update error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @Override
    public Optional<CPU> findById(long id){
        try {
            log.fine("Finding CPU...");
            return cpuDao.findById(id);
        } catch (Exception e) {
            log.severe("Failed to find error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<CPU> findAll(){
        try {
            log.fine("Finding all CPUs...");
            return cpuDao.findAll();
        } catch (Exception e) {
            log.severe("Failed to find all error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean exists(long id){
        try {
            log.fine("Checking if CPU exists...");
            return cpuDao.exists(id);
        } catch (Exception e) {
            log.severe("Failed to check if CPU exists error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<CPU> filterCPUs(String name, String socket, Integer minCoreCount, Integer maxCoreCount, Double minCoreClock,
                                Double maxCoreClock, Double minPrice, Double maxPrice,Boolean graphics, Boolean smt) {
        try {
            log.fine("Filtering CPUs...");
            return cpuDao.filterByCriteria(name, socket, minCoreCount, maxCoreCount, minCoreClock, maxCoreClock, minPrice, maxPrice, graphics, smt);
        } catch (Exception e) {
            log.severe("Failed to filter CPUs error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
