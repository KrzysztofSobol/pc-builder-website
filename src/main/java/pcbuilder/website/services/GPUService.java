package pcbuilder.website.services;

import org.springframework.stereotype.Component;
import pcbuilder.website.models.entities.products.GPU;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public interface GPUService {
    GPU save(GPU user);
    void delete(GPU user);
    GPU update(GPU user);
    GPU partialUpdate(UUID id, GPU user);
    Optional<GPU> findById(UUID id);
    List<GPU> findAll();
    boolean exists(UUID id);
}
