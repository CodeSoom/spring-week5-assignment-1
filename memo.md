java bean validation

유효성 검사
- 프론트에서 입력값을 체크하여 받아온다고 생각했는데, 백엔드에서도 2차 검사(?)로 유효성 체크

롬복 - 직관적인 네이밍
- @allargscontructor
- @noargscontructor 

- @builder -> @allargsconstructor 와 함께 사용해야한다.
- 메소드 체이닝? -> 위임이 안되는 경우가 문제 된다. 


### dto : Data Transfer Object
- web 을 통해 외부와 소통하는 용도로 사용된다. setter, getter 필요
- 순수하게 데이터만 다룬다.
- product class 는 더이상 웹의 데이터를 다루지 않게 된다.
- 컨트롤러에서도 모두 ProductData 로 바꿔준다 -> 컨트롤러가 웹과의 접점이기 때문.
- DTO 는 디비와 맞닿은 부분인줄 알았는데 잘못 알고 있었다 (DAO 였던 것).

- logic 을 갖지않는 순수한 데이터 객체로, getter/setter 메서드만을 갖는다.
- 하지만 DB 에서 꺼낸 값을 임의로 변경할 필요가 없기에 setter 는 없다 (대신 생성자에서 값을 할당:setter 역할).


### DAO: data access object
- 실제로 DB 에 접근하는 객체, DB에 CRUD 하는 역할


### Q. ProductService class의 createProduct() 에서 굳이 product 로 builder 과정을 거쳐 save() 를 할 필요가 있나..?
- 곧장 ProductData 를 save() 하면 안되는가..?

- dozer mapper

- DozerBeanMapperBuilder.buildDefault();

- changeWith() 을 도입한 것은 service 코드를 단순화 하기 위함.

```java
    public Product createProduct(ProductData productData) {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        Product product = mapper.map(productData, Product.class);

        // mappper 를 사용하면 아래의 과정을 간소화 할 수 있다.
//        Product product = Product.builder()
//                .name(productData.getName())
//                .maker(productData.getMaker())
//                .price(productData.getPrice())
//                .imageUrl(productData.getImageUrl())
//                .build();
        return productRepository.save(product);
    }
```

```java
public Product updateProduct(Long id, ProductData productData) {
        Product product = findProduct(id);

//        product.change(
//                productData.getName(),
//                productData.getMaker(),
//                productData.getPrice(),
//                productData.getImageUrl()
//        );

        product.changeWith(mapper.map(productData, Product.class));

        return product;
    }
```



```java
    public void change(String name,
                       String maker,
                       Integer price,
                       String imageUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public void changeWith(Product source) {
        this.name = source.name;
        this.maker = source.maker;
        this.price = source.price;

    }
```


## 생성자, Constructor
- 특수한 목적을 가지는 메소드로, 객체가 생성될 때 자동으로 호출되어 <b>필드를 초기화하는 역할<b/>을 가진다.

### 생성자가 필요한 이유
- 클래스 내부 필드 선언시 초기값까지 정의해버리면, 동일 클래스에서 생성되는 객체는 모두 같은 값을 갖게 된다.
- 객체 생성 시점부터 원하는 초기값 설정 위해 생성자를 사용

- 객체 생성시 생성자가 호출되지 않으면 예외가 발생


### 0601 trouble shooting
문제 상황: UserServiceTest의 createUser() 테스트 실패
상세 상황: user.getName() 의 값이 null로 출력
해결 방법: domain class의 필드에 @Mapping() 어노테이션 지정
- @Mapping()을 지정하지 않아 발생한 문제였다. 

#### @Email, password regex 조건은 왜 User 가 아닌, UserData 에 걸어야 동작하는가 ?


