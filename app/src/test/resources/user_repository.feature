Feature: user repository
  Scenario: user repository에 user를 저장한다
    Given 올바르게 생성된 user가 제공된다면
    When user repository에 user를 저장하는 경우
    Then user repository에서 id로 user를 찾을 수 있다