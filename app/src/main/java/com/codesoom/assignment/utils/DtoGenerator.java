package com.codesoom.assignment.utils;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductDto;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class DtoGenerator {

    public static Product toProduct(ProductDto productDto) {
        return DozerBeanMapperBuilder.buildDefault().map(productDto, Product.class);
    }

    public static ProductDto fromProduct(Product product) {
        return DozerBeanMapperBuilder.buildDefault().map(product, ProductDto.class);
    }
}
