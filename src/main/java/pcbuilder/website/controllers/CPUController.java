package pcbuilder.website.controllers;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pcbuilder.website.mappers.Mapper;
import pcbuilder.website.models.dto.products.CPUDto;
import pcbuilder.website.models.entities.products.CPU;
import pcbuilder.website.repositories.CPUDao;
import pcbuilder.website.services.CPUService;
import pcbuilder.website.services.impl.products.GPUServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
public class CPUController {
    private final CPUService cpuService;
    private final Mapper<CPU, CPUDto> mapper;
    private final static Logger log =
            Logger.getLogger(CPUController.class.getName());

    public CPUController(CPUService cpuService, Mapper<CPU, CPUDto> mapper) {
        try {
            log.info("CPUController created");
            this.cpuService = cpuService;
            this.mapper = mapper;
        } catch (Exception e) {
            log.severe("CPUController creation failed");
            throw new RuntimeException(e);
        }
    }

    @PostMapping(path = "/cpus")
    public ResponseEntity<CPUDto> createCPU(@RequestBody CPUDto cpu) {
        CPU cpuEntity = mapper.mapFrom(cpu);
        CPU savedCPU = cpuService.save(cpuEntity);
        log.info("Saved CPU: " + savedCPU.toString());
        return new ResponseEntity<>(mapper.mapTo(savedCPU), HttpStatus.CREATED);
    }

    @GetMapping(path = "/cpus/{id}")
    public ResponseEntity<CPUDto> getCPU(@PathVariable long id) {
        Optional<CPU> cpuEntity = cpuService.findById(id);

        return cpuEntity.map(cpu -> new ResponseEntity<>(mapper.mapTo(cpu), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/cpus")
    public List<CPUDto> getCPUs() {
        List<CPU> cpus = cpuService.findAll();
        return cpus.stream().map(mapper::mapTo).collect(Collectors.toList());
    }

    @PutMapping(path = "/cpus/{id}")
    public ResponseEntity<CPUDto> updateCPU(@PathVariable long id, @RequestBody CPUDto cpu) {
        if(!cpuService.exists(id)) {
            log.warning("CPU with id: " + id + " does not exist");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        cpu.setProductID(id);
        CPU cpuEntity = mapper.mapFrom(cpu);
        CPU updatedCPU = cpuService.update(cpuEntity);
        log.info("Updated CPU: " + updatedCPU.toString());
        return new ResponseEntity<>(mapper.mapTo(updatedCPU), HttpStatus.OK);
    }

    @PatchMapping(path = "/cpus/{id}")
    public ResponseEntity<CPUDto> partialUpdateCPU(@PathVariable long id, @RequestBody CPUDto cpu) {
        if(!cpuService.exists(id)) {
            log.warning("CPU with id: " + id + " does not exist");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        cpu.setProductID(id);
        CPU cpuEntity = mapper.mapFrom(cpu);
        CPU updatedCPU = cpuService.partialUpdate(id, cpuEntity);
        log.info("Updated CPU: " + updatedCPU.toString());
        return new ResponseEntity<>(mapper.mapTo(updatedCPU), HttpStatus.OK);
    }

    @DeleteMapping(path = "/cpus/{id}")
    public ResponseEntity<Void> deleteCPU(@PathVariable long id) {
        return cpuService.findById(id).map(cpu -> {
            cpuService.delete(cpu);
            log.info("Deleted CPU: " + cpu.toString());
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/cpus/filter")
    public ResponseEntity<List<CPU>> getFilteredCPUs(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String socket,
            @RequestParam(required = false) Integer minCoreCount,
            @RequestParam(required = false) Integer maxCoreCount,
            @RequestParam(required = false) Double minCoreClock,
            @RequestParam(required = false) Double maxCoreClock,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Boolean graphics,
            @RequestParam(required = false) Boolean smt
    ) {
        List<CPU> filteredCPUs = cpuService.filterCPUs(name, socket, minCoreCount, maxCoreCount, minCoreClock, maxCoreClock, minPrice, maxPrice, graphics, smt);
        return new ResponseEntity<>(filteredCPUs, HttpStatus.OK);
    }

}
