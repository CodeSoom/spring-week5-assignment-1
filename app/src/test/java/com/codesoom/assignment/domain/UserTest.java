package com.codesoom.assignment.domain;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserTest {
    Long id;
    String name;
    String mail;
    String password;
    User createdUser;

    @Given("ID, 이름, 이메일, 비밀번호가 올바르게 주어진다면")
    public void provideAllInformation() {
        id = 1L;
        name = "라스";
        mail = "las@magica.dev";
        password = "pK1RZRUAgExuFYfr0qHY";
    }

    @When("user를 생성하는 경우")
    public void createUser() {
        createdUser = new User(id, name, mail, password);
    }

    @Then("user가 올바르게 생성된다")
    public void userCreatedCorrectly() {
        assertThat(createdUser).isNotNull();
    }
}
