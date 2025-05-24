package pcbuilder.website.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pcbuilder.website.mappers.Mapper;
import pcbuilder.website.models.dto.products.GPUDto;
import pcbuilder.website.models.entities.products.GPU;
import pcbuilder.website.services.GPUService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class GPUController {
    private final GPUService gpuService;
    private final Mapper<GPU, GPUDto> gpuMapper;

    public GPUController(GPUService gpuService, Mapper<GPU, GPUDto> gpuMapper) {
        this.gpuService = gpuService;
        this.gpuMapper = gpuMapper;
    }

    @PostMapping(path = "/gpus")
    public ResponseEntity<GPUDto> createGPU(@RequestBody GPUDto gpu) {
        GPU gpuEntity = gpuMapper.mapFrom(gpu);
        GPU savedGPU = gpuService.save(gpuEntity);
        return new ResponseEntity<>(gpuMapper.mapTo(savedGPU), HttpStatus.CREATED);
    }

    @GetMapping(path = "/gpus/{id}")
    public ResponseEntity<GPUDto> getGPU(@PathVariable long id) {
        Optional<GPU> gpuEntity = gpuService.findById(id);

        return gpuEntity.map(gpu -> new ResponseEntity<>(gpuMapper.mapTo(gpu), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/gpus")
    public List<GPUDto> getGPUs() {
        List<GPU> gpus = gpuService.findAll();
        return gpus.stream().map(gpuMapper::mapTo).collect(Collectors.toList());
    }

    @PutMapping(path = "/gpus/{id}")
    public ResponseEntity<GPUDto> updateGPU(@PathVariable long id, @RequestBody GPUDto gpu) {
        if(!gpuService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        gpu.setProductID(id);
        GPU gpuEntity = gpuMapper.mapFrom(gpu);
        GPU updatedGPU = gpuService.update(gpuEntity);
        return new ResponseEntity<>(gpuMapper.mapTo(updatedGPU), HttpStatus.OK);
    }

    @PatchMapping(path = "/gpus/{id}")
    public ResponseEntity<GPUDto> partialUpdateGPU(@PathVariable long id, @RequestBody GPUDto gpu) {
        if(!gpuService.exists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        gpu.setProductID(id);
        GPU gpuEntity = gpuMapper.mapFrom(gpu);
        GPU updatedGPU = gpuService.partialUpdate(id, gpuEntity);
        return new ResponseEntity<>(gpuMapper.mapTo(updatedGPU), HttpStatus.OK);
    }

    @DeleteMapping(path = "gpus/{id}")
    public ResponseEntity<Void> deleteGPU(@PathVariable long id) {
        return gpuService.findById(id).map(gpu -> {
            gpuService.delete(gpu);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
