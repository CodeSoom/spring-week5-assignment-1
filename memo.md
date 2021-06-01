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



