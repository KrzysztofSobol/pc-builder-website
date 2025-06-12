package pcbuilder.website.controllers;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
            log.finer("CPUController created");
            this.cpuService = cpuService;
            this.mapper = mapper;
        } catch (Exception e) {
            log.severe("CPUController creation failed");
            throw new RuntimeException(e);
        }
    }

    @PostMapping(path = "/cpus")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<CPUDto> createCPU(@RequestBody CPUDto cpu) {
        log.info("CPU Post " + cpu);
        CPU cpuEntity = mapper.mapFrom(cpu);
        CPU savedCPU = cpuService.save(cpuEntity);
        return new ResponseEntity<>(mapper.mapTo(savedCPU), HttpStatus.CREATED);
    }

    @GetMapping(path = "/cpus/{id}")
    @PreAuthorize("hasRole('Customer')")
    public ResponseEntity<CPUDto> getCPU(@PathVariable long id) {
        log.info("CPU Get " + id);
        Optional<CPU> cpuEntity = cpuService.findById(id);

        return cpuEntity.map(cpu -> new ResponseEntity<>(mapper.mapTo(cpu), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/cpus")
    @PreAuthorize("hasRole('Customer')")
    public List<CPUDto> getCPUs() {
        log.info("CPU Get All");
        List<CPU> cpus = cpuService.findAll();
        return cpus.stream().map(mapper::mapTo).collect(Collectors.toList());
    }

    @PutMapping(path = "/cpus/{id}")
    @PreAuthorize("hasRole('Mod')")
    public ResponseEntity<CPUDto> updateCPU(@PathVariable long id, @RequestBody CPUDto cpu) {
        log.info("CPU Put " + id + " " + cpu);
        if(!cpuService.exists(id)) {
            log.warning("CPU with id: " + id + " does not exist");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        cpu.setProductID(id);
        CPU cpuEntity = mapper.mapFrom(cpu);
        CPU updatedCPU = cpuService.update(cpuEntity);
        return new ResponseEntity<>(mapper.mapTo(updatedCPU), HttpStatus.OK);
    }

    @PatchMapping(path = "/cpus/{id}")
    @PreAuthorize("hasRole('Mod')")
    public ResponseEntity<CPUDto> partialUpdateCPU(@PathVariable long id, @RequestBody CPUDto cpu) {
        log.info("CPU Patch " + id + " " + cpu);
        if(!cpuService.exists(id)) {
            log.warning("CPU with id: " + id + " does not exist");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        cpu.setProductID(id);
        CPU cpuEntity = mapper.mapFrom(cpu);
        CPU updatedCPU = cpuService.partialUpdate(id, cpuEntity);
        return new ResponseEntity<>(mapper.mapTo(updatedCPU), HttpStatus.OK);
    }

    @DeleteMapping(path = "/cpus/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Void> deleteCPU(@PathVariable long id) {
        log.info("CPU Delete " + id);
        return cpuService.findById(id).map(cpu -> {
            cpuService.delete(cpu);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }).orElseGet(() -> {
            log.warning("CPU with id: " + id + " does not exist");
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return null;
        });
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
        log.info("Cpu filter " + "name: "+ name + "socket: " + socket + "coreCount: " + minCoreCount + "coreClock: " + minCoreClock + "price: " + minPrice + "graphics: " + graphics + "smt: " + smt);
        List<CPU> filteredCPUs = cpuService.filterCPUs(name, socket, minCoreCount, maxCoreCount, minCoreClock, maxCoreClock, minPrice, maxPrice, graphics, smt);
        return new ResponseEntity<>(filteredCPUs, HttpStatus.OK);
    }

}
