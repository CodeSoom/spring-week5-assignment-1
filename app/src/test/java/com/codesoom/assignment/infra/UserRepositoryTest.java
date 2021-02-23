package com.codesoom.assignment.infra;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserRepositoryTest {
    private User user;
    private UserRepository repository = new InMemoryUserRepository();

    @Given("올바르게 생성된 user가 제공된다면")
    public void givenRightUser() {
        String name = "라스";
        String mail = "las@magica.dev";
        String password = "pK1RZRUAgExuFYfr0qHY";

        user = new User(1L, name, mail, password);
    }

    @When("user repository에 user를 저장하는 경우")
    public void saveUser() {
        repository.save(user);
    }

    @Then("user repository에서 id로 user를 찾을 수 있다")
    public void findUserById() {
        User foundUser = repository.findById(user.getId()).get();

        assertThat(foundUser).isEqualTo(user);
    }
}
