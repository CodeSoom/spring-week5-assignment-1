package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.infra.InMemoryUserRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

public class UserApplicationServiceTest {
    UserRepository repository = new InMemoryUserRepository();
    UserApplicationService userApplicationService = new UserApplicationService(repository);

    String name;
    String mail;
    String password;
    User createdUser;

    @Given("이름, 이메일, 비밀번호가 올바르게 주어진다면")
    public void provideRightInformation() {
        name = "라스";
        mail = "las@magica.dev";
        password = "pK1RZRUAgExuFYfr0qHY";
    }

    @When("회원을 생성하는 경우")
    public void createUser() {
        createdUser = userApplicationService.createUser(name, mail, password);
    }

    @Then("정상적으로 회원이 생성된다")
    public void userCreatedNormally() {
        assertThat(createdUser).isNotNull();
    }
}
