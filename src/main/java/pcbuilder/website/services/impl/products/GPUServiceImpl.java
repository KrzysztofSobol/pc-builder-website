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
    private final static Logger log =
            Logger.getLogger(GPUServiceImpl.class.getName());
    private final GPUDao gpuDao;

    public GPUServiceImpl(GPUDao gpuDao) {
        try {
            log.finer("Initializing GPU Service...");
            this.gpuDao = gpuDao;
        } catch (Exception e) {
            log.severe("Failed to initialize GPU Service error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public GPU save(GPU gpu) {
        try {
            log.fine("Saving GPU...");
            return gpuDao.save(gpu);
        } catch (Exception e) {
            log.severe("Failed to save error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(GPU gpu) {
        try {
            log.fine("Deleting GPU...");
            gpuDao.delete(gpu);
        } catch (Exception e) {
            log.severe("Failed to delete error : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public GPU update(GPU gpu) {
        try {
            log.fine("Updating GPU...");
            return gpuDao.update(gpu);
        } catch (Exception e) {
            log.severe("Failed to update error :" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public GPU partialUpdate(long id, GPU gpu) {

        try {
            log.fine("Partial update of GPU...");
            gpu.setProductID(id);
            return gpuDao.findById(id).map(existingGPU ->{
                Optional.ofNullable(gpu.getChipset()).ifPresent(existingGPU::setChipset);
                Optional.ofNullable(gpu.getMemory()).ifPresent(existingGPU::setMemory);
                Optional.ofNullable(gpu.getCoreClock()).ifPresent(existingGPU::setCoreClock);
                Optional.ofNullable(gpu.getBoostClock()).ifPresent(existingGPU::setBoostClock);
                Optional.ofNullable(gpu.getColor()).ifPresent(existingGPU::setColor);
                Optional.ofNullable(gpu.getLength()).ifPresent(existingGPU::setLength);

                Optional.ofNullable(gpu.getName()).ifPresent(existingGPU::setName);
                Optional.ofNullable(gpu.getPrice()).ifPresent(existingGPU::setPrice);
                Optional.ofNullable(gpu.getDescription()).ifPresent(existingGPU::setDescription);
                Optional.ofNullable(gpu.getImageUrl()).ifPresent(existingGPU::setImageUrl);
                Optional.ofNullable(gpu.getStock()).ifPresent(existingGPU::setStock);
                return gpuDao.update(existingGPU);
            }).orElseThrow(() -> new RuntimeException("GPU not found"));
        } catch (RuntimeException e) {
            log.warning("Failed to partial update error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<GPU> findById(long id) {
        try {
            log.fine("Finding GPU...");
            return gpuDao.findById(id);
        } catch (Exception e) {
            log.severe("Failed to find error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<GPU> findAll() {
        try {
            log.fine("Finding all GPUs...");
            return gpuDao.findAll();
        } catch (Exception e) {
            log.severe("Failed to find all error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean exists(long id) {
        try {
            log.fine("Checking if GPU exists...");
            return gpuDao.exists(id);
        } catch (Exception e) {
            log.severe("Failed to check if GPU exists error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
