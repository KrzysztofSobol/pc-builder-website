package pcbuilder.website.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pcbuilder.website.mappers.Mapper;
import pcbuilder.website.models.dto.products.CaseDto;
import pcbuilder.website.models.entities.products.Case;
import pcbuilder.website.services.CaseService;
import pcbuilder.website.enums.PanelType;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
public class CaseController {

    private final CaseService caseService;
    private final Mapper<Case, CaseDto> mapper;
    private final static Logger log = Logger.getLogger(CaseController.class.getName());

    public CaseController(CaseService caseService, Mapper<Case, CaseDto> mapper) {
        try {
            log.finer("CaseController created");
            this.caseService = caseService;
            this.mapper = mapper;
        } catch (Exception e) {
            log.severe("CaseController creation failed");
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/cases")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<CaseDto> createCase(@RequestBody CaseDto dto) {
        log.info("Case Post " + dto);
        Case entity = mapper.mapFrom(dto);
        Case saved = caseService.save(entity);
        return new ResponseEntity<>(mapper.mapTo(saved), HttpStatus.CREATED);
    }

    @GetMapping("/cases/{id}")
    @PreAuthorize("hasRole('Customer')")
    public ResponseEntity<CaseDto> getCase(@PathVariable long id) {
        log.info("Case Get " + id);
        Optional<Case> entity = caseService.findById(id);
        return entity.map(e -> new ResponseEntity<>(mapper.mapTo(e), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/cases")
    @PreAuthorize("hasRole('Customer')")
    public List<CaseDto> getAllCases() {
        log.info("Case Get All");
        return caseService.findAll().stream().map(mapper::mapTo).collect(Collectors.toList());
    }

    @PutMapping("/cases/{id}")
    @PreAuthorize("hasRole('Mod')")
    public ResponseEntity<CaseDto> updateCase(@PathVariable long id, @RequestBody CaseDto dto) {
        log.info("Case Put " + id + " " + dto);
        if (!caseService.exists(id)) {
            log.warning("Case with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        dto.setProductID(id);
        Case updated = caseService.update(mapper.mapFrom(dto));
        return new ResponseEntity<>(mapper.mapTo(updated), HttpStatus.OK);
    }

    @PatchMapping("/cases/{id}")
    @PreAuthorize("hasRole('Mod')")
    public ResponseEntity<CaseDto> partialUpdateCase(@PathVariable long id, @RequestBody CaseDto dto) {
        log.info("Case Patch " + id + " " + dto);
        if (!caseService.exists(id)) {
            log.warning("Case with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        dto.setProductID(id);
        Case updated = caseService.partialUpdate(id, mapper.mapFrom(dto));
        return new ResponseEntity<>(mapper.mapTo(updated), HttpStatus.OK);
    }

    @DeleteMapping("/cases/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Void> deleteCase(@PathVariable long id) {
        log.info("Case Delete " + id);
        return caseService.findById(id).map(c -> {
            caseService.delete(c);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }).orElseGet(() -> {
            log.warning("Case with id " + id + " not found");
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return null;
        });
    }

    @GetMapping("/cases/filter")
    @PreAuthorize("hasRole('Customer')")
    public ResponseEntity<List<Case>> filterCases(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) PanelType sidePanel,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        log.info("Case Filter " + "type: " + type + "color: " + color + "sidePanel: " + sidePanel + "minPrice: " + minPrice + "maxPrice: " + maxPrice);
        List<Case> filtered = caseService.filterCases(type, color, sidePanel, minPrice, maxPrice);
        return new ResponseEntity<>(filtered, HttpStatus.OK);
    }
}
