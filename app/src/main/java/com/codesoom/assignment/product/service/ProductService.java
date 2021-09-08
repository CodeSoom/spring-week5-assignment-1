package com.codesoom.assignment.product.service;

import com.codesoom.assignment.product.domain.CatToy;

import java.util.List;

public interface ProductService {
    List<CatToy> getCatToys();
    CatToy findCatToyById(Long id);
    CatToy addCatToy(CatToy catToy);
    CatToy updateCatToy(Long id, CatToy catToy);
    CatToy deleteCatToyById(Long id);
}
