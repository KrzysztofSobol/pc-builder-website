package pcbuilder.website.services.impl;

import pcbuilder.website.models.entities.products.GPU;
import pcbuilder.website.repositories.GPUDao;
import pcbuilder.website.services.GPUService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public GPU partialUpdate(UUID id, GPU gpu) {
        return null;
    }

    @Override
    public Optional<GPU> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<GPU> findAll() {
        return List.of();
    }

    @Override
    public boolean exists(UUID id) {
        return false;
    }
}
