package com.codesoom.assignment.product.controller;

import com.codesoom.assignment.product.application.ProductCommand;

public interface ProductDtoMapper {
    ProductCommand.Register of(ProductDto.RequestParam request);

    ProductCommand.UpdateReq of(Long id, ProductDto.RequestParam requestParam);
}
