package pcbuilder.website.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pcbuilder.website.mappers.Mapper;
import pcbuilder.website.models.dto.products.MotherboardDto;
import pcbuilder.website.models.entities.products.Motherboard;
import pcbuilder.website.services.MotherboardService;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
public class MotherboardsController {
    private final static Logger log = Logger.getLogger(MotherboardsController.class.getName());
    private final MotherboardService motherboardService;
    private final Mapper<Motherboard, MotherboardDto> mapper;

    public MotherboardsController(MotherboardService motherboardService, Mapper<Motherboard, MotherboardDto> mapper){
        try {
            log.finer("MotherboardsController created");
            this.motherboardService = motherboardService;
            this.mapper = mapper;
        } catch (Exception e) {
            log.severe("MotherboardsController creation failed");
            throw new RuntimeException(e);
        }
    }

    @PostMapping(path = "/motherboards")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<MotherboardDto> addMotherboard(@RequestBody MotherboardDto mb){
        log.info("Motherboard Post " + mb);
        Motherboard mbEntity = mapper.mapFrom(mb);
        Motherboard savedMb = motherboardService.save(mbEntity);
        return new ResponseEntity<>(mapper.mapTo(savedMb), HttpStatus.CREATED);
    }

    @GetMapping(path = "/motherboards/{id}")
    @PreAuthorize("hasRole('Customer')")
    public ResponseEntity<MotherboardDto> getMotherboard(@PathVariable long id){
        log.info("Motherboard Get " + id);
        Optional<Motherboard> mbEntity = motherboardService.findById(id);
        return mbEntity.map(motherboard -> new ResponseEntity<>(mapper.mapTo(motherboard), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/motherboards")
    @PreAuthorize("hasRole('Customer')")
    public List<MotherboardDto> getMotherboards(){
        log.info("Motherboard Get All");
        List<Motherboard> motherboards = motherboardService.findAll();
        return motherboards.stream().map(mapper::mapTo).collect(Collectors.toList());
    }

    @PutMapping(path = "/motherboards/{id}")
    @PreAuthorize("hasRole('Mod')")
    public ResponseEntity<MotherboardDto> updateMotherboard(@PathVariable long id, @RequestBody MotherboardDto motherboard){
        log.info("Motherboard Put " + id + " " + motherboard);
        if(!motherboardService.exists(id)){
            log.warning("Motherboard with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        motherboard.setProductID(id);
        Motherboard mbEntity = mapper.mapFrom(motherboard);
        Motherboard updatedMb = motherboardService.update(mbEntity);
        return new ResponseEntity<>(mapper.mapTo(updatedMb), HttpStatus.OK);
    }

    @PatchMapping(path = "/motherboards/{id}")
    @PreAuthorize("hasRole('Mod')")
    public ResponseEntity<MotherboardDto> partialUpdateMotherboard(@PathVariable long id, @RequestBody MotherboardDto motherboard){
        log.info("Motherboard Patch " + id + " " + motherboard);
        if(!motherboardService.exists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        motherboard.setProductID(id);
        Motherboard mbEntity = mapper.mapFrom(motherboard);
        Motherboard savedMb = motherboardService.partialUpdate(id, mbEntity);
        return new ResponseEntity<>(mapper.mapTo(savedMb), HttpStatus.OK);
    }

    @DeleteMapping(path = "/motherboards/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Void> deleteMotherboard(@PathVariable long id){
        return motherboardService.findById(id).map(mb -> {
            motherboardService.delete(mb);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/motherboards/filter")
    @PreAuthorize("hasRole('Customer')")
    public List<MotherboardDto> getFilteredMotherboards(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String socket,
            @RequestParam(required = false) String formFactor,
            @RequestParam(required = false) Integer maxMemory,
            @RequestParam(required = false) Integer minMemorySlots,
            @RequestParam(required = false) Integer maxMemorySlots,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ){
        List<Motherboard> motherboards = motherboardService.filterMotherboards(name,socket, formFactor, maxMemory,minMemorySlots,maxMemorySlots,color,minPrice,maxPrice);
        return motherboards.stream().map(mapper::mapTo).collect(Collectors.toList());
    }
}
