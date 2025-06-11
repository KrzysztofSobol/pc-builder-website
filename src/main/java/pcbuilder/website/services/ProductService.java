package pcbuilder.website.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    Page<Product> findPaged(Pageable pageable);
    boolean exists(long id);
}
