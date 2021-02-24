Feature: user repository
  Scenario: user repository에 user를 저장한다
    Given 올바르게 생성된 user가 제공된다면
    When user repository에 user를 저장하는 경우
    Then user repository에서 id로 user를 찾을 수 있다

  Scenario: 다음 id를 가져온다
    When 다음 아이디를 가져온 경우
    Then 가져온 아이디로 기존에 저장된 user를 찾을 수 없다

  Scenario: user를 삭제한다
    Given 올바르게 생성된 user가 제공된다면
    And 이미 user가 save되었다면
    When user를 remove하는 경우
    Then user repository에서 id로 user를 찾을 수 없다
