package pcbuilder.website.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pcbuilder.website.models.entities.Product;
import pcbuilder.website.repositories.ProductDao;
import pcbuilder.website.services.ProductService;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ProdcutServiceImpl implements ProductService {
    private final static Logger log =
            Logger.getLogger(ProdcutServiceImpl.class.getName());
    private final ProductDao productDao;

    public ProdcutServiceImpl(ProductDao productDao) {
        try {
            log.finer("Initializing Product Service...");
            this.productDao = productDao;
        } catch (Exception e) {
            log.severe("Failed to initialize Product Service error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product save(Product product) {
        try {
            log.fine("Saving product...");
            return productDao.save(product);
        } catch (Exception e) {
            log.severe("Failed to save product error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Product product) {
        try {
            log.fine("Deleting product...");
            productDao.delete(product);
        } catch (Exception e) {
            log.severe("Failed to delete product error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product update(Product product) {
        try {
            log.fine("Updating product...");
            return productDao.update(product);
        } catch (Exception e) {
            log.severe("Failed to update product error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product partialUpdate(long id, Product product) {
        try {
            log.fine("Partial update of product...");
            product.setProductID(id);

            return productDao.findById(id).map(existingProduct -> {
                Optional.ofNullable(product.getName()).ifPresent(existingProduct::setName);
                Optional.ofNullable(product.getPrice()).ifPresent(existingProduct::setPrice);
                Optional.ofNullable(product.getDescription()).ifPresent(existingProduct::setDescription);
                Optional.ofNullable(product.getImageUrl()).ifPresent(existingProduct::setImageUrl);
                Optional.ofNullable(product.getStock()).ifPresent(existingProduct::setStock);
                return productDao.update(existingProduct);
            }).orElseThrow(() -> new RuntimeException("Product not found"));
        } catch (RuntimeException e) {
            log.warning("Failed to partial update product error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Product> findById(long id) {
        try {
            log.fine("Finding product...");
            return productDao.findById(id);
        } catch (Exception e) {
            log.severe("Failed to find product error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> findAll() {
        try {
            log.fine("Finding all products...");
            return productDao.findAll();
        } catch (Exception e) {
            log.severe("Failed to find all products error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Page<Product> findPaged(Pageable pageable){
        try {
            log.fine("Finding all products...");
            return productDao.findPaged(pageable);
        } catch (Exception e) {
            log.severe("Failed to find all products error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean exists(long id) {
        try {
            log.fine("Checking if product exists...");
            return productDao.exists(id);
        } catch (Exception e) {
            log.severe("Failed to check if product exists error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
