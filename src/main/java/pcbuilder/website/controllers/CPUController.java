package pcbuilder.website.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pcbuilder.website.models.entities.products.CPU;
import pcbuilder.website.services.CPUService;

import java.util.List;
import java.util.Optional;

@RestController
public class CPUController {
    private final CPUService cpuService;

    public CPUController(CPUService cpuService) {this.cpuService = cpuService;}

    @PostMapping(path = "/cpus")
    public ResponseEntity<CPU> createCPU(@RequestBody CPU cpu) {
        CPU cpuEntity = cpuService.save(cpu);
        return new ResponseEntity<>(cpuEntity, HttpStatus.CREATED);
    }

    @GetMapping(path = "/cpus/{id}")
    public ResponseEntity<CPU> getCPU(@PathVariable long id) {
        Optional<CPU> cpuEntity = cpuService.findById(id);
        return cpuEntity.map(cpu -> new ResponseEntity<>(cpu, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping(path = "/cpus")
    public ResponseEntity<List<CPU>> getCPUs() {
        List<CPU> cpus = cpuService.findAll();
        return new ResponseEntity<>(cpus, HttpStatus.OK);
    }
    @PutMapping(path = "/cpus/{id}")
    public ResponseEntity<CPU> updateCPU(@PathVariable long id, @RequestBody CPU cpu) {
        if(!cpuService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        cpu.setProductID(id);
        CPU cpuEntity = cpuService.update(cpu);
        return new ResponseEntity<>(cpuEntity, HttpStatus.OK);
    }
    @PatchMapping(path = "/cpus/{id}")
    public ResponseEntity<CPU> partialUpdateCPU(@PathVariable long id, @RequestBody CPU cpu) {
        if(!cpuService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CPU cpuEntity = cpuService.partialUpdate(id, cpu);
        return new ResponseEntity<>(cpuEntity, HttpStatus.OK);
    }
    @DeleteMapping(path = "/cpus/{id}")
    public ResponseEntity<CPU> deleteCPU(@PathVariable long id) {
        if(!cpuService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CPU cpuEntity = cpuService.findById(id).orElse(null);
        cpuService.delete(cpuEntity);
        return new ResponseEntity<>(cpuEntity, HttpStatus.OK);
    }
    @GetMapping(path = "/cpus/filter")
    public ResponseEntity<List<CPU>> getFilteredCPUs(
            @RequestParam(required = false) String socket,
            @RequestParam(required = false) Integer coreCount,
            @RequestParam(required = false) Double minCoreClock,
            @RequestParam(required = false) Double maxCoreClock,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        List<CPU> filteredCPUs = cpuService.filterCPUs(socket, coreCount, minCoreClock, maxCoreClock, minPrice, maxPrice);
        return new ResponseEntity<>(filteredCPUs, HttpStatus.OK);
    }

}
