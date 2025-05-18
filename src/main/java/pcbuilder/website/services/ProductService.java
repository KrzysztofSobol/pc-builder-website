package pcbuilder.website.services;

import pcbuilder.website.models.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product save(Product product);
    void delete(Product product);
    Product update(Product product);
    Product partialUpdate(long id, Product product);
    Optional<Product> findById(long id);
    List<Product> findAll();
    boolean exists(long id);
}
