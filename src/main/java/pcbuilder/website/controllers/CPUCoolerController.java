package pcbuilder.website.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pcbuilder.website.mappers.Mapper;
import pcbuilder.website.models.dto.products.CPUCoolerDto;
import pcbuilder.website.models.entities.products.CPUCooler;
import pcbuilder.website.services.CPUCoolerService;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
public class CPUCoolerController {

    private final CPUCoolerService coolerService;
    private final Mapper<CPUCooler, CPUCoolerDto> mapper;
    private final static Logger log = Logger.getLogger(CPUCoolerController.class.getName());

    public CPUCoolerController(CPUCoolerService coolerService, Mapper<CPUCooler, CPUCoolerDto> mapper) {
        try {
            log.finer("CPUCoolerController created");
            this.coolerService = coolerService;
            this.mapper = mapper;
        } catch (Exception e) {
            log.severe("CPUCoolerController creation failed");
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/coolers")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<CPUCoolerDto> createCooler(@RequestBody CPUCoolerDto cooler) {
        log.info("Cooler Post " + cooler);
        CPUCooler entity = mapper.mapFrom(cooler);
        CPUCooler saved = coolerService.save(entity);
        log.info("Saved CPUCooler: " + saved);
        return new ResponseEntity<>(mapper.mapTo(saved), HttpStatus.CREATED);
    }

    @GetMapping("/coolers/{id}")
    @PreAuthorize("hasRole('Customer')")
    public ResponseEntity<CPUCoolerDto> getCooler(@PathVariable long id) {
        log.info("Cooler Get " + id);
        Optional<CPUCooler> entity = coolerService.findById(id);
        return entity.map(e -> new ResponseEntity<>(mapper.mapTo(e), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/coolers")
    @PreAuthorize("hasRole('Customer')")
    public List<CPUCoolerDto> getAllCoolers() {
        log.info("Cooler Get All");
        return coolerService.findAll().stream().map(mapper::mapTo).collect(Collectors.toList());
    }

    @PutMapping("/coolers/{id}")
    @PreAuthorize("hasRole('Mod')")
    public ResponseEntity<CPUCoolerDto> updateCooler(@PathVariable long id, @RequestBody CPUCoolerDto cooler) {
        log.info("Cooler Put " + id + " " + cooler);
        if (!coolerService.exists(id)) {
            log.warning("Cooler with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        cooler.setProductID(id);
        CPUCooler updated = coolerService.update(mapper.mapFrom(cooler));
        return new ResponseEntity<>(mapper.mapTo(updated), HttpStatus.OK);
    }

    @PatchMapping("/coolers/{id}")
    @PreAuthorize("hasRole('Mod')")
    public ResponseEntity<CPUCoolerDto> partialUpdateCooler(@PathVariable long id, @RequestBody CPUCoolerDto cooler) {
        log.info("Cooler Patch " + id + " " + cooler);
        if (!coolerService.exists(id)) {
            log.warning("Cooler with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        cooler.setProductID(id);
        CPUCooler updated = coolerService.partialUpdate(id, mapper.mapFrom(cooler));
        return new ResponseEntity<>(mapper.mapTo(updated), HttpStatus.OK);
    }

    @DeleteMapping("/coolers/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Void> deleteCooler(@PathVariable long id) {
        log.info("Cooler Delete " + id);
        return coolerService.findById(id).map(c -> {
            coolerService.delete(c);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }).orElseGet(() -> {
            log.warning("Cooler with id " + id + " not found");
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return null;
        });
    }

    @GetMapping("/coolers/filter")
    @PreAuthorize("hasRole('Customer')")
    public ResponseEntity<List<CPUCooler>> filterCoolers(
            @RequestParam(required = false) Integer minRPM,
            @RequestParam(required = false) Integer maxRPM,
            @RequestParam(required = false) Integer minNoise,
            @RequestParam(required = false) Integer maxNoise,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) Integer minHeight,
            @RequestParam(required = false) Integer maxHeight,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        log.info("Cooler filter " + "minRPM: " + minRPM + "maxRPM: " + maxRPM + "minNoise: " + minNoise + "maxNoise: " + maxNoise + "color: " + color + "minHeight: " + minHeight + "maxHeight: " + maxHeight + "minPrice: " + minPrice + "maxPrice: " + maxPrice);
        List<CPUCooler> filtered = coolerService.filterCoolers(minRPM, maxRPM, minNoise, maxNoise, color, minHeight, maxHeight, minPrice, maxPrice);
        return new ResponseEntity<>(filtered, HttpStatus.OK);
    }
}
