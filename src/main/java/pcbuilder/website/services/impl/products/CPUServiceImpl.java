package pcbuilder.website.services.impl.products;

import org.springframework.stereotype.Service;
import pcbuilder.website.models.entities.products.CPU;
import pcbuilder.website.repositories.CPUDao;
import pcbuilder.website.services.CPUService;

import java.util.List;
import java.util.Optional;

@Service
public class CPUServiceImpl implements CPUService {
    private final CPUDao cpuDao;

    public CPUServiceImpl(CPUDao cpuDao) {this.cpuDao = cpuDao;}

    @Override
    public CPU save(CPU cpu){return cpuDao.save(cpu);}
    @Override
    public void delete(CPU cpu){cpuDao.delete(cpu);}
    @Override
    public CPU update(CPU cpu){return cpuDao.update(cpu);}
    @Override
    public CPU partialUpdate(long id, CPU cpu){
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
    }
    @Override
    public Optional<CPU> findById(long id){return cpuDao.findById(id);}
    @Override
    public List<CPU> findAll(){return cpuDao.findAll();}
    @Override
    public boolean exists(long id){return cpuDao.exists(id);}
    @Override
    public List<CPU> filterCPUs(String socket, Integer coreCount, Double minCoreClock, Double maxCoreClock, Double minPrice, Double maxPrice) {
        return cpuDao.filterByCriteria(socket, coreCount, minCoreClock, maxCoreClock, minPrice, maxPrice);
    }

}
