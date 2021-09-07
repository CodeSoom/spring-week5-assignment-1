package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


@Nested
@DisplayName("CatToyService 클래스")
class ProductServiceTest {

    private ProductService productService;
    private ProductRepository productRepository;

    private CatToy givenCatToy;
    private List<CatToy> givenCatToyList;

    private static final Long EXIST_ID = 1L;

    @BeforeEach
    public void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductServiceImpl(productRepository);

        givenCatToy = new CatToy(
                EXIST_ID,
                "Test Name",
                "Test Maker",
                10000,
                "test Image Url"
        );

        givenCatToyList = new ArrayList<>();
        givenCatToyList.add(givenCatToy);
    }

    @Nested
    @DisplayName("getCatToys 메서드는")
    class getAllCatToys {
        @BeforeEach
        void prepareTest() {
            given(productRepository.findAll()).willReturn(givenCatToyList);
        }

        @Test
        @DisplayName("장난감 목록 전체를 반환을 요청한다.")
        void getCatToys() {
            List<CatToy> catToys = productService.getCatToys();

            Assertions.assertThat(catToys).hasSize(1);
        }
    }

    @Nested
    @DisplayName("findCatToyById 메서드는")
    class findCatToyById {
        @BeforeEach
        void prepareTest() {
            given(productRepository.findById(EXIST_ID)).willReturn(Optional.of(givenCatToy));
        }

        @Test
        @DisplayName("식별자와 일치하는 장난감을 반환을 요청한다.")
        void findCatToyById() {
            CatToy catToy = productService.findCatToyById(EXIST_ID);

            Assertions.assertThat(catToy.getName()).isEqualTo("Test Name");
        }
    }

    @Nested
    @DisplayName("addCatToy 메서드는")
    class addCatToy {
        @BeforeEach
        void prepareTest() {
            given(productRepository.save(any(CatToy.class))).willReturn(givenCatToy);
        }

        @Test
        @DisplayName("요청된 장난감을 저장하고 반환을 요청한다.")
        void addCatToy() {
            CatToy catToy = productService.addCatToy(givenCatToy);

            Assertions.assertThat(catToy.getName()).isEqualTo("Test Name");
        }
    }

    @Nested
    @DisplayName("updateCatToy 메서드는")
    class updateCatToy {
        @BeforeEach
        void prepareTest() {
            given(productRepository.findById(EXIST_ID)).willReturn(Optional.of(givenCatToy));
        }

        @Test
        @DisplayName("요청된 장난감을 수정을 요청한다.")
        void updateCatToy() {
            CatToy catToy = productService.updateCatToy(EXIST_ID, givenCatToy);

            Assertions.assertThat(catToy.getName()).isEqualTo(givenCatToy.getName());
        }
    }

    @Nested
    @DisplayName("updateCatToy 메서드는")
    class deleteCatToyById {
        @BeforeEach
        void prepareTest() {
            given(productRepository.findById(EXIST_ID)).willReturn(Optional.of(givenCatToy));
        }

        @Test
        @DisplayName("요청된 장난감을 삭제를 요청한다.")
        void deleteCatToyById() {
            productService.deleteCatToyById(1L);
            verify(productRepository).deleteById(1L);
        }
    }
}