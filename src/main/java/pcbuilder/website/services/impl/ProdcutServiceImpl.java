package pcbuilder.website.services.impl;

import org.springframework.stereotype.Service;
import pcbuilder.website.models.entities.Product;
import pcbuilder.website.repositories.ProductDao;
import pcbuilder.website.services.ProductService;

import java.util.List;
import java.util.Optional;

@Service
public class ProdcutServiceImpl implements ProductService {
    private final ProductDao productDao;

    public ProdcutServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public Product save(Product product) {
        return productDao.save(product);
    }

    @Override
    public void delete(Product product) {
        productDao.delete(product);
    }

    @Override
    public Product update(Product product) {
        return productDao.update(product);
    }

    @Override
    public Product partialUpdate(long id, Product product) {
        product.setProductID(id);

        return productDao.findById(id).map(existingProduct -> {
            Optional.ofNullable(product.getName()).ifPresent(existingProduct::setName);
            Optional.ofNullable(product.getPrice()).ifPresent(existingProduct::setPrice);
            Optional.ofNullable(product.getDescription()).ifPresent(existingProduct::setDescription);
            Optional.ofNullable(product.getImageUrl()).ifPresent(existingProduct::setImageUrl);
            Optional.ofNullable(product.getStock()).ifPresent(existingProduct::setStock);
            return productDao.update(existingProduct);
        }).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public Optional<Product> findById(long id) {
        return Optional.empty();
    }

    @Override
    public List<Product> findAll() {
        return List.of();
    }

    @Override
    public boolean exists(long id) {
        return false;
    }
}
