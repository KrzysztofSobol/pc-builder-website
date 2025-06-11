package pcbuilder.website.services.impl.products;

import org.springframework.stereotype.Service;
import pcbuilder.website.models.entities.products.GPU;
import pcbuilder.website.repositories.GPUDao;
import pcbuilder.website.services.GPUService;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class GPUServiceImpl implements GPUService {
    private final GPUDao gpuDao;

    public GPUServiceImpl(GPUDao gpuDao) {
        this.gpuDao = gpuDao;
    }

    @Override
    public GPU save(GPU gpu) {
        return gpuDao.save(gpu);
    }

    @Override
    public void delete(GPU gpu) {
        gpuDao.delete(gpu);
    }

    @Override
    public GPU update(GPU gpu) {
       return gpuDao.update(gpu);
    }

    @Override
    public GPU partialUpdate(long id, GPU gpu) {
        gpu.setProductID(id);

        return gpuDao.findById(id).map(existingGPU ->{
            Optional.ofNullable(gpu.getChipset()).ifPresent(existingGPU::setChipset);
            Optional.ofNullable(gpu.getMemory()).ifPresent(existingGPU::setMemory);
            Optional.ofNullable(gpu.getCoreClock()).ifPresent(existingGPU::setCoreClock);
            Optional.ofNullable(gpu.getBoostClock()).ifPresent(existingGPU::setBoostClock);
            Optional.ofNullable(gpu.getColor()).ifPresent(existingGPU::setColor);
            Optional.ofNullable(gpu.getLength()).ifPresent(existingGPU::setLength);
            return gpuDao.update(existingGPU);
        }).orElseThrow(() -> new RuntimeException("GPU not found"));
    }

    @Override
    public Optional<GPU> findById(long id) {
        return gpuDao.findById(id);
    }

    @Override
    public List<GPU> findAll() {
        return gpuDao.findAll();
    }

    @Override
    public boolean exists(long id) {
        return gpuDao.exists(id);
    }
}
