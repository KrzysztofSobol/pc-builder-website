package pcbuilder.website.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pcbuilder.website.mappers.Mapper;
import pcbuilder.website.models.dto.products.PSUDto;
import pcbuilder.website.models.entities.products.PSU;
import pcbuilder.website.services.PSUService;
import pcbuilder.website.enums.PSUType;
import pcbuilder.website.enums.Efficiency;
import pcbuilder.website.enums.ModularType;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
public class PSUController {

    private final PSUService psuService;
    private final Mapper<PSU, PSUDto> mapper;
    private final static Logger log = Logger.getLogger(PSUController.class.getName());

    public PSUController(PSUService psuService, Mapper<PSU, PSUDto> mapper) {
        this.psuService = psuService;
        this.mapper = mapper;
    }

    @PostMapping("/psus")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<PSUDto> createPSU(@RequestBody PSUDto psu) {
        PSU entity = mapper.mapFrom(psu);
        PSU saved = psuService.save(entity);
        log.info("Saved PSU: " + saved);
        return new ResponseEntity<>(mapper.mapTo(saved), HttpStatus.CREATED);
    }

    @GetMapping("/psus/{id}")
    @PreAuthorize("hasRole('Customer')")
    public ResponseEntity<PSUDto> getPSU(@PathVariable long id) {
        Optional<PSU> entity = psuService.findById(id);
        return entity.map(e -> new ResponseEntity<>(mapper.mapTo(e), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/psus")
    @PreAuthorize("hasRole('Customer')")
    public List<PSUDto> getAllPSUs() {
        return psuService.findAll().stream().map(mapper::mapTo).collect(Collectors.toList());
    }

    @PutMapping("/psus/{id}")
    @PreAuthorize("hasRole('Mod')")
    public ResponseEntity<PSUDto> updatePSU(@PathVariable long id, @RequestBody PSUDto psu) {
        if (!psuService.exists(id)) {
            log.warning("PSU with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        psu.setProductID(id);
        PSU updated = psuService.update(mapper.mapFrom(psu));
        log.info("Updated PSU: " + updated);
        return new ResponseEntity<>(mapper.mapTo(updated), HttpStatus.OK);
    }

    @PatchMapping("/psus/{id}")
    @PreAuthorize("hasRole('Mod')")
    public ResponseEntity<PSUDto> partialUpdatePSU(@PathVariable long id, @RequestBody PSUDto psu) {
        if (!psuService.exists(id)) {
            log.warning("PSU with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        psu.setProductID(id);
        PSU updated = psuService.partialUpdate(id, mapper.mapFrom(psu));
        log.info("Patched PSU: " + updated);
        return new ResponseEntity<>(mapper.mapTo(updated), HttpStatus.OK);
    }

    @DeleteMapping("/psus/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Void> deletePSU(@PathVariable long id) {
        return psuService.findById(id).map(p -> {
            psuService.delete(p);
            log.info("Deleted PSU: " + p);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/psus/filter")
    @PreAuthorize("hasRole('Customer')")
    public ResponseEntity<List<PSU>> filterPSUs(
            @RequestParam(required = false) PSUType type,
            @RequestParam(required = false) Efficiency efficiency,
            @RequestParam(required = false) Integer minWattage,
            @RequestParam(required = false) Integer maxWattage,
            @RequestParam(required = false) ModularType modular,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        List<PSU> filtered = psuService.filterPSUs(type, efficiency, minWattage, maxWattage, modular, color, minPrice, maxPrice);
        return new ResponseEntity<>(filtered, HttpStatus.OK);
    }
}
