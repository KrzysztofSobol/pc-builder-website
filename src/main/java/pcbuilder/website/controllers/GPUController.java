package pcbuilder.website.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pcbuilder.website.mappers.Mapper;
import pcbuilder.website.models.dto.products.GPUDto;
import pcbuilder.website.models.entities.products.GPU;
import pcbuilder.website.services.GPUService;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
public class GPUController {
    private final static Logger log = Logger.getLogger(GPUController.class.getName());
    private final GPUService gpuService;
    private final Mapper<GPU, GPUDto> gpuMapper;

    public GPUController(GPUService gpuService, Mapper<GPU, GPUDto> gpuMapper) {
        try {
            log.finer("GPUController created");
            this.gpuService = gpuService;
            this.gpuMapper = gpuMapper;
        } catch (Exception e) {
            log.severe("GPUController creation failed");
            throw new RuntimeException(e);
        }
    }

    @PostMapping(path = "/gpus")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<GPUDto> createGPU(@RequestBody GPUDto gpu) {
        log.info("GPU Post " + gpu);
        GPU gpuEntity = gpuMapper.mapFrom(gpu);
        GPU savedGPU = gpuService.save(gpuEntity);
        return new ResponseEntity<>(gpuMapper.mapTo(savedGPU), HttpStatus.CREATED);
    }

    @GetMapping(path = "/gpus/{id}")
    @PreAuthorize("hasRole('Customer')")
    public ResponseEntity<GPUDto> getGPU(@PathVariable long id) {
        log.info("GPU Get " + id);
        Optional<GPU> gpuEntity = gpuService.findById(id);

        return gpuEntity.map(gpu -> new ResponseEntity<>(gpuMapper.mapTo(gpu), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/gpus")
    @PreAuthorize("hasRole('Customer')")
    public List<GPUDto> getGPUs() {
        log.info("GPU Get All");
        List<GPU> gpus = gpuService.findAll();
        return gpus.stream().map(gpuMapper::mapTo).collect(Collectors.toList());
    }

    @PutMapping(path = "/gpus/{id}")
    @PreAuthorize("hasRole('Mod')")
    public ResponseEntity<GPUDto> updateGPU(@PathVariable long id, @RequestBody GPUDto gpu) {
        log.info("GPU Put " + id + " " + gpu);
        if(!gpuService.exists(id)) {
            log.warning("GPU with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        gpu.setProductID(id);
        GPU gpuEntity = gpuMapper.mapFrom(gpu);
        GPU updatedGPU = gpuService.update(gpuEntity);
        return new ResponseEntity<>(gpuMapper.mapTo(updatedGPU), HttpStatus.OK);
    }

    @PatchMapping(path = "/gpus/{id}")
    @PreAuthorize("hasRole('Mod')")
    public ResponseEntity<GPUDto> partialUpdateGPU(@PathVariable long id, @RequestBody GPUDto gpu) {
        log.info("GPU Patch " + id + " " + gpu);
        if(!gpuService.exists(id)){
            log.warning("GPU with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        gpu.setProductID(id);
        GPU gpuEntity = gpuMapper.mapFrom(gpu);
        GPU updatedGPU = gpuService.partialUpdate(id, gpuEntity);
        return new ResponseEntity<>(gpuMapper.mapTo(updatedGPU), HttpStatus.OK);
    }

    @DeleteMapping(path = "gpus/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Void> deleteGPU(@PathVariable long id) {
        log.info("GPU Delete " + id);
        return gpuService.findById(id).map(gpu -> {
            gpuService.delete(gpu);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }).orElseGet(() -> {
            log.warning("GPU with id " + id + " not found");
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return null;
        });
    }
}
