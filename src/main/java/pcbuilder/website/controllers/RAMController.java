package pcbuilder.website.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pcbuilder.website.mappers.Mapper;
import pcbuilder.website.models.dto.products.RAMDto;
import pcbuilder.website.models.entities.products.RAM;
import pcbuilder.website.services.RAMService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class RAMController {
    private final RAMService ramService;
    private final Mapper<RAM, RAMDto> ramMapper;

    public RAMController(RAMService ramService, Mapper<RAM, RAMDto> ramMapper) {
        this.ramService = ramService;
        this.ramMapper = ramMapper;
    }
    @PostMapping(path = "/rams")
    public ResponseEntity<RAMDto> createRAM(@RequestBody RAMDto ram){
        RAM ramEntity = ramMapper.mapFrom(ram);
        RAM savedRAM = ramService.save(ramEntity);
        return new ResponseEntity<>(ramMapper.mapTo(savedRAM), org.springframework.http.HttpStatus.CREATED);
    }
    @GetMapping(path = "/rams/{id}")
    public ResponseEntity<RAMDto> getRAM(@PathVariable long id){
        Optional<RAM> ramEntity = ramService.findById(id);
        return ramEntity.map(ram -> new ResponseEntity<>(ramMapper.mapTo(ram), org.springframework.http.HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(org.springframework.http.HttpStatus.NOT_FOUND));
    }
    @GetMapping(path = "/rams")
    public List<RAMDto> getRAMs(){
        List<RAM> gpus = ramService.findAll();
        return gpus.stream().map(ramMapper::mapTo).toList();
    }
    @PutMapping(path = "/rams/{id}")
    public ResponseEntity<RAMDto> updateRAM(@PathVariable long id, @RequestBody RAMDto ram){
        if(!ramService.exists(id)){
            return new ResponseEntity<>(org.springframework.http.HttpStatus.NOT_FOUND);
        }
        ram.setProductID(id);
        RAM ramEntity = ramMapper.mapFrom(ram);
        RAM updatedRAM = ramService.update(ramEntity);
        return new ResponseEntity<>(ramMapper.mapTo(updatedRAM), org.springframework.http.HttpStatus.OK);
    }
    @PatchMapping(path = "/rams/{id}")
    public ResponseEntity<RAMDto> partialUpdateRAM(@PathVariable long id, @RequestBody RAMDto ram){
        if(!ramService.exists(id)){
            return new ResponseEntity<>(org.springframework.http.HttpStatus.NOT_FOUND);
        }
        ram.setProductID(id);
        RAM ramEntity = ramMapper.mapFrom(ram);
        RAM updatedRAM = ramService.partialUpdate(id, ramEntity);
        return new ResponseEntity<>(ramMapper.mapTo(updatedRAM), org.springframework.http.HttpStatus.OK);
    }
    @DeleteMapping(path = "rams/{id}")
    public ResponseEntity<Void> deleteRAM(@PathVariable long id){
        return ramService.findById(id).map(ram -> {
            ramService.delete(ram);
            return new ResponseEntity<Void>(org.springframework.http.HttpStatus.NO_CONTENT);
        }).orElseGet(() -> new ResponseEntity<>(org.springframework.http.HttpStatus.NOT_FOUND));
    }
    @GetMapping(path = "rams/filter")
    public ResponseEntity<List<RAMDto>> getFilteredRAMs(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Integer minClockSpeed,
            @RequestParam(required = false) Integer maxClockSpeed,
            @RequestParam(required = false) Integer ddrGen,
            @RequestParam(required = false) Integer minModuleCount,
            @RequestParam(required = false) Integer maxModuleCount,
            @RequestParam(required = false) Integer minCapacity,
            @RequestParam(required = false) Integer maxCapacity,
            String color){
        List<RAM> filteredRAMs = ramService.filterRAMs(name, minPrice, maxPrice, minClockSpeed, maxClockSpeed, ddrGen, minModuleCount, maxModuleCount, minCapacity, maxCapacity, color);
        return new ResponseEntity<>(filteredRAMs.stream().map(ramMapper::mapTo).collect(Collectors.toList()), HttpStatus.OK);
    }
}
