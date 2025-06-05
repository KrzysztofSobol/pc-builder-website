package pcbuilder.website.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pcbuilder.website.models.entities.products.Motherboard;
import pcbuilder.website.services.MotherboardService;

import java.util.List;
import java.util.Optional;

@RestController
public class MotherboardsController {
    private final MotherboardService motherboardService;

    public MotherboardsController(MotherboardService motherboardService){this.motherboardService = motherboardService;}

    @PostMapping(path = "/motherboards")
    public ResponseEntity<Motherboard> addMotherboard(@RequestBody Motherboard motherboard){
        Motherboard motherboardEntity = motherboardService.save(motherboard);
        return new ResponseEntity<>(motherboardEntity, HttpStatus.CREATED);
    }
    @GetMapping(path = "/motherboards/{id}")
    public ResponseEntity<Motherboard> getMotherboard(@PathVariable long id){
        Optional<Motherboard> motherboardEntity = motherboardService.findById(id);
        return motherboardEntity.map(motherboard -> new ResponseEntity<>(motherboard, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping(path = "/motherboards")
    public ResponseEntity<List<Motherboard>> getMotherboards(){
        List<Motherboard> motherboards = motherboardService.findAll();
        return new ResponseEntity<>(motherboards, HttpStatus.OK);
    }
    @PutMapping(path = "/motherboards/{id}")
    public ResponseEntity<Motherboard> updateMotherboard(@PathVariable long id, @RequestBody Motherboard motherboard){
        if(!motherboardService.exists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        motherboard.setProductID(id);
        Motherboard motherboardEntity = motherboardService.update(motherboard);
        return new ResponseEntity<>(motherboardEntity, HttpStatus.OK);
    }
    @PatchMapping(path = "/motherboards/{id}")
    public ResponseEntity<Motherboard> partialUpdateMotherboard(@PathVariable long id, @RequestBody Motherboard motherboard){
        if(!motherboardService.exists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Motherboard motherboardEntity = motherboardService.partialUpdate(id, motherboard);
        return new ResponseEntity<>(motherboardEntity, HttpStatus.OK);
    }
    @DeleteMapping(path = "/motherboards/{id}")
    public ResponseEntity<Motherboard> deleteMotherboard(@PathVariable long id){
        if(!motherboardService.exists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Motherboard motherboardEntity = motherboardService.findById(id).orElse(null);
        motherboardService.delete(motherboardEntity);
        return new ResponseEntity<>(motherboardEntity, HttpStatus.OK);
    }
    @GetMapping("/motherboards/filter")
    public ResponseEntity<List<Motherboard>> getFilteredMotherboards(
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
        return new ResponseEntity<>(motherboards, HttpStatus.OK);
    }
}
