package pcbuilder.website.services;

import org.springframework.stereotype.Component;
import pcbuilder.website.models.entities.products.GPU;

import java.util.List;
import java.util.Optional;

public interface GPUService {
    GPU save(GPU user);
    void delete(GPU user);
    GPU update(GPU user);
    GPU partialUpdate(long id, GPU user);
    Optional<GPU> findById(long id);
    List<GPU> findAll();
    boolean exists(long id);
}
