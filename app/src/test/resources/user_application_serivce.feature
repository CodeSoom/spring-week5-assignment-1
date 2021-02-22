Feature: user management
  Scenario: 회원 생성하기
    Given 이름, 이메일, 비밀번호가 올바르게 주어진다면
    When 회원을 생성하는 경우
    Then 정상적으로 회원이 생성된다