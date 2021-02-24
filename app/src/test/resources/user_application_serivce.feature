Feature: user management
  Scenario: 회원 생성하기
    Given 이름, 이메일, 비밀번호가 올바르게 주어진다면
    When 회원을 생성하는 경우
    Then 정상적으로 회원이 생성된다

  Scenario: 회원 정보 변경하기
    Given 이미 회원이 생성되었다면
    When 회원의 이름을 변경하는 경우
    Then 회원의 이름이 변경된다

  Scenario: 존재하지 않는 회원 정보 변경하기
    Given 회원이 생성되지 않았을 때
    When 존재하지 않는 회원의 이름을 변경하는 경우 에러가 발생한다
