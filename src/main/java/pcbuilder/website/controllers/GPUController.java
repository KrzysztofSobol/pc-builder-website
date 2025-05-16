package pcbuilder.website.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pcbuilder.website.models.entities.products.GPU;
import pcbuilder.website.services.GPUService;

import java.util.List;
import java.util.Optional;

@RestController
public class GPUController {
    private final GPUService gpuService;

    public GPUController(GPUService gpuService) {
        this.gpuService = gpuService;
    }

    @PostMapping(path = "/gpus")
    public ResponseEntity<GPU> createGPU(@RequestBody GPU gpu) {
        GPU gpuEntity = gpuService.save(gpu);
        return new ResponseEntity<>(gpuEntity, HttpStatus.CREATED);
    }

    @GetMapping(path = "/gpus/{id}")
    public ResponseEntity<GPU> getGPU(@PathVariable long id) {
        Optional<GPU> gpuEntity = gpuService.findById(id);

        return gpuEntity.map(gpu -> new ResponseEntity<>(gpu, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/gpus")
    public ResponseEntity<List<GPU>> getGPUs() {
        List<GPU> gpus = gpuService.findAll();
        return new ResponseEntity<>(gpus, HttpStatus.OK);
    }

    @PutMapping(path = "/gpus/{id}")
    public ResponseEntity<GPU> updateGPU(@PathVariable long id, @RequestBody GPU gpu) {
        if(!gpuService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        gpu.setProductID(id);
        GPU gpuEntity = gpuService.update(gpu);
        return new ResponseEntity<>(gpuEntity, HttpStatus.OK);
    }

    @PatchMapping(path = "/gpus/{id}")
    public ResponseEntity<GPU> partialUpdateGPU(@PathVariable long id, @RequestBody GPU gpu) {
        if(!gpuService.exists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        gpu.setProductID(id);
        GPU gpuEntity = gpuService.partialUpdate(id, gpu);
        return new ResponseEntity<>(gpuEntity, HttpStatus.OK);
    }

    @DeleteMapping(path = "gpus/{id}")
    public ResponseEntity<Void> deleteGPU(@PathVariable long id) {
        return gpuService.findById(id).map(gpu -> {
            gpuService.delete(gpu);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
