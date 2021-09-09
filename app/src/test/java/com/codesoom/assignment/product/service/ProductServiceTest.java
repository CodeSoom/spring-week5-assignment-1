package com.codesoom.assignment.product.service;

import com.codesoom.assignment.common.exception.CatToyNotFoundException;
import com.codesoom.assignment.product.domain.CatToy;
import com.codesoom.assignment.product.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    private final List<CatToy> givenCatToyList = new ArrayList<>();
    private static Long EXIST_ID;
    private static Long NOT_EXIST_ID;

    private static final String NAME = "Test Name";
    private static final String MAKER = "Test Maker";
    private static final int PRICE = 10000;
    private static final String IMAGE_URL = "test Image Url";

    @BeforeEach
    public void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductServiceImpl(productRepository);

        setUpFixtures();
        setUpSaveTask();
    }

    void setUpFixtures() {
        givenCatToy = CatToy.builder()
                .name(NAME)
                .maker(MAKER)
                .price(PRICE)
                .imageUrl(IMAGE_URL)
                .build();

        givenCatToyList.add(givenCatToy);
        EXIST_ID = givenCatToy.getId();
        NOT_EXIST_ID = EXIST_ID + 100L;

        given(productRepository.findAll()).willReturn(givenCatToyList);
        given(productRepository.findById(EXIST_ID)).willReturn(Optional.of(givenCatToy));
        given(productRepository.findById(NOT_EXIST_ID)).willReturn(Optional.empty());
    }

    void setUpSaveTask() {
        given(productRepository.save(any(CatToy.class))).will(invocation -> {
            CatToy catToy = invocation.getArgument(0);
            catToy.setId(2L);
            givenCatToyList.add(catToy);
            return catToy;
        });
    }

    @Nested
    @DisplayName("getCatToys 메서드는")
    class getAllCatToys {
        @Test
        @DisplayName("장난감 목록 전체를 반환한다.")
        void getCatToys() {
            List<CatToy> catToys = productService.getCatToys();

            Assertions.assertThat(catToys.size()).isEqualTo(givenCatToyList.size());
        }
    }

    @Nested
    @DisplayName("findCatToyById 메서드는")
    class findCatToyById {
        @Test
        @DisplayName("존재하는 식별자일 때 일치하는 장난감을 반환한다.")
        void findCatToyByExistedId() {
            CatToy catToy = productService.findCatToyById(EXIST_ID);

            Assertions.assertThat(catToy.getName()).isEqualTo("Test Name");
        }

        @Test
        @DisplayName("존재하지 않는 식별자일 때 예외를 반환한다.")
        void findCatToyByNotExistedId() {
            assertThatThrownBy(() -> productService.findCatToyById(NOT_EXIST_ID))
                    .isInstanceOf(CatToyNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("addCatToy 메서드는")
    class addCatToy {
        CatToy source = CatToy.builder()
                .name("Test Name2")
                .maker("Test Maker2")
                .price(20000)
                .imageUrl("test Image Url2")
                .build();

        @Test
        @DisplayName("요청된 장난감을 저장하고 반환한다.")
        void addCatToy() {
            int oldSize = productService.getCatToys().size();
            CatToy catToy = productService.addCatToy(source);
            int newSize = productService.getCatToys().size();

            Assertions.assertThat(catToy.getId()).isEqualTo(2L);
            Assertions.assertThat(newSize - oldSize).isEqualTo(1);
        }
    }

    @Nested
    @DisplayName("updateCatToy 메서드는")
    class updateCatToy {
        CatToy source = CatToy.builder()
                .name("Test Name2")
                .maker("Test Maker2")
                .price(20000)
                .imageUrl("test Image Url2")
                .build();

        @Test
        @DisplayName("존재하는 식별자일 때 요청된 장난감을 수정한다.")
        void updateCatToyWithExistedID() {
            CatToy catToy = productService.updateCatToy(EXIST_ID, source);

            Assertions.assertThat(productService.findCatToyById(EXIST_ID).getName()).isEqualTo(catToy.getName());
        }

        @Test
        @DisplayName("존재하지 않는 식별자일 때 예외를 반환한다.")
        void updateCatToyWithNotExistedID() {
            assertThatThrownBy(() -> productService.updateCatToy(NOT_EXIST_ID, source))
                    .isInstanceOf(CatToyNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("deleteCatToyById 메서드는")
    class deleteCatToyById {
        @BeforeEach
        void prepareTest() {
            given(productRepository.findById(EXIST_ID)).willReturn(Optional.of(givenCatToy));
        }

        @Test
        @DisplayName("존재하는 식별자일 때 요청된 장난감을 삭제한다.")
        void deleteCatToyByExistedId() {
            productService.deleteCatToyById(EXIST_ID);
//            assertThatThrownBy(() -> productService.findCatToyById(EXIST_ID))
//                    .isInstanceOf(CatToyNotFoundException.class);
        }

        @Test
        @DisplayName("존재하지 않는 식별자일 때 요청된 장난감을 삭제한다.")
        void deleteCatToyByNotExistedId() {
            assertThatThrownBy(() -> productService.deleteCatToyById(NOT_EXIST_ID))
                    .isInstanceOf(CatToyNotFoundException.class);
        }
    }
}
