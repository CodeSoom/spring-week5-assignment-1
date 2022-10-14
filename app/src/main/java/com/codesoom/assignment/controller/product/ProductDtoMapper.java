package com.codesoom.assignment.controller.product;

import com.codesoom.assignment.application.product.ProductCommand;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ProductDtoMapper {

    ProductDtoMapper INSTANCE = Mappers.getMapper(ProductDtoMapper.class);

    ProductCommand.Register of(ProductDto.RequestParam request);

    ProductCommand.UpdateRequest of(Long id, ProductDto.RequestParam request);
}
