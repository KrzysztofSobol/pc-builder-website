package pcbuilder.website.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pcbuilder.website.mappers.Mapper;
import pcbuilder.website.models.dto.ProductDto;
import pcbuilder.website.models.entities.Product;

@Component
public class ProductMapperImpl implements Mapper<Product, ProductDto> {
    private final ModelMapper mapper = new ModelMapper();

    @Override
    public ProductDto mapTo(Product product) {
        return mapper.map(product, ProductDto.class);
    }

    @Override
    public Product mapFrom(ProductDto productDto) {
        return mapper.map(productDto, Product.class);
    }
}
