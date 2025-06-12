package pcbuilder.website.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pcbuilder.website.enums.StorageType;
import pcbuilder.website.mappers.Mapper;
import pcbuilder.website.models.dto.products.StorageDto;
import pcbuilder.website.models.entities.products.CPU;
import pcbuilder.website.models.entities.products.Storage;
import pcbuilder.website.services.StorageService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class StorageController {
    private final StorageService storageService;
    private final Mapper<Storage, StorageDto> storageMapper;

    public StorageController(StorageService storageService, Mapper<Storage, StorageDto> storageMapper) {
        this.storageService = storageService;
        this.storageMapper = storageMapper;
    }
    @PostMapping(path = "/storages")
    @PreAuthorize( "hasRole('Admin')")
    public ResponseEntity<StorageDto> createStorage(@RequestBody StorageDto storage) {
        Storage storageEntity = storageMapper.mapFrom(storage);
        Storage savedStorage = storageService.save(storageEntity);
        return new ResponseEntity<>(storageMapper.mapTo(savedStorage), HttpStatus.CREATED);
    }
    @GetMapping(path = "/storages/{id}")
    @PreAuthorize("hasRole('Customer')")
    public ResponseEntity<StorageDto> getStorage(@PathVariable long id){
        Optional<Storage> storageEntity = storageService.findById(id);
        return storageEntity.map(storage -> new ResponseEntity<>(storageMapper.mapTo(storage),HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping(path = "/storages")
    @PreAuthorize("hasRole('Customer')")
    public List<StorageDto> getStorages(){
        List<Storage> storages = storageService.findAll();
        return storages.stream().map(storageMapper::mapTo).collect(Collectors.toList());
    }
    @PutMapping(path = "/storages/{id}")
    @PreAuthorize("hasRole('Mod')")
    public ResponseEntity<StorageDto> updateStorage(@PathVariable long id, @RequestBody StorageDto storage) {
        if(!storageService.exists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        storage.setProductID(id);
        Storage storageEntity = storageMapper.mapFrom(storage);
        Storage updatedStorage = storageService.update(storageEntity);
        return new ResponseEntity<>(storageMapper.mapTo(updatedStorage),HttpStatus.OK);
    }
    @PatchMapping(path = "/storages/{id}")
    @PreAuthorize("hasRole('Mod')")
    public ResponseEntity<StorageDto> partialUpdateStorage(@PathVariable long id, @RequestBody StorageDto storage){
        if(!storageService.exists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        storage.setProductID(id);
        Storage storageEntity = storageMapper.mapFrom(storage);
        Storage updatedStorage = storageService.partialUpdate(id, storageEntity);
        return new ResponseEntity<>(storageMapper.mapTo(updatedStorage),HttpStatus.OK);
    }
    @DeleteMapping(path = "/storages/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Void> deleteStorage(@PathVariable long id){
        return storageService.findById(id).map(storage -> {
            storageService.delete(storage);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping(path = "/storages/filter")
    @PreAuthorize("hasRole('Customer')")
    public ResponseEntity<List<StorageDto>> getFilteredStorages(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Integer minCapacity,
            @RequestParam(required = false) Integer maxCapacity,
            @RequestParam(required = false) StorageType storageType
    ){
        List<Storage> filteredStorages = storageService.filterStorages(name, minPrice, maxPrice, minCapacity, maxCapacity, storageType);
        return new ResponseEntity<>(filteredStorages.stream().map(storageMapper::mapTo).collect(Collectors.toList()), HttpStatus.OK);
    }
}
