package pcbuilder.website.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pcbuilder.website.mappers.Mapper;
import pcbuilder.website.models.dto.ProductDto;
import pcbuilder.website.models.entities.Product;
import pcbuilder.website.services.ProductService;
import pcbuilder.website.services.impl.ProdcutServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ProductController {
    private final ProductService productService;
    private final Mapper<Product, ProductDto> mapper;

    public ProductController(ProductService productService, Mapper<Product, ProductDto> mapper){
        this.productService = productService;
        this.mapper = mapper;
    }

    @PostMapping(path = "/products")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        Product productEntity = mapper.mapFrom(productDto);
        Product savedProduct = productService.save(productEntity);
        return new ResponseEntity<>(mapper.mapTo(savedProduct), HttpStatus.CREATED);
    }

    @GetMapping(path = "/products/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable long id) {
        Optional<Product> productEntity = productService.findById(id);

        return productEntity.map(product -> new ResponseEntity<>(mapper.mapTo(product), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/products")
    public List<ProductDto> getProducts() {
        return productService.findAll().stream().map(mapper::mapTo).collect(Collectors.toList());
    }

    @GetMapping(path = "/products/paged")
    public ResponseEntity<Page<Product>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productService.findPaged(pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PutMapping(path = "/products/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable long id, @RequestBody ProductDto productDto){
        if(!productService.exists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        productDto.setProductID(id);
        Product productEntity = mapper.mapFrom(productDto);
        Product savedProduct = productService.save(productEntity);
        return new ResponseEntity<>(mapper.mapTo(savedProduct), HttpStatus.OK);
    }

    @PatchMapping(path = "/products/{id}")
    public ResponseEntity<ProductDto> patchProduct(@PathVariable long id, @RequestBody ProductDto productDto){
        if(!productService.exists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        productDto.setProductID(id);
        Product productEntity = mapper.mapFrom(productDto);
        Product updatedProduct = productService.partialUpdate(id, productEntity);
        return new ResponseEntity<>(mapper.mapTo(updatedProduct), HttpStatus.OK);
    }

    @DeleteMapping(path = "products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id){
        return productService.findById(id).map(product -> {
            productService.delete(product);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
