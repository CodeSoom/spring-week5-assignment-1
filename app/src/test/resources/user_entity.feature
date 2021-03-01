Feature: user entity
  Scenario: user entity생성하기
    Given ID, 이름, 이메일, 비밀번호가 올바르게 주어진다면
    When user를 생성하는 경우
    Then user가 올바르게 생성된다
