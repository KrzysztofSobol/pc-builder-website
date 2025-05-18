package pcbuilder.website.repositories.impl;

import org.springframework.stereotype.Repository;
import pcbuilder.website.models.entities.Product;
import pcbuilder.website.repositories.ProductDao;

@Repository
public class ProductDaoImpl extends GenericDaoImpl<Product, Long> implements ProductDao {
}
