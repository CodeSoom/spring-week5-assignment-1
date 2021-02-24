package com.codesoom.assignment.infra;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserRepositoryTest {
    private User user;
    private UserRepository repository = new InMemoryUserRepository();

    private Long nextId;

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

    @When("다음 아이디를 가져온 경우")
    public void getNextId() {
        nextId = repository.nextId();
    }

    @Then("가져온 아이디로 기존에 저장된 user를 찾을 수 없다")
    public void cantFindAnyUserByNextId() {
        Optional<User> foundUser = repository.findById(nextId);

        assertThat(foundUser).isEmpty();
    }

    @Given("이미 user가 save되었다면")
    public void alreadySavedUser() {
        repository.save(user);
    }

    @When("user를 remove하는 경우")
    public void removeUser() {
        repository.delete(user);
    }

    @Then("user repository에서 id로 user를 찾을 수 없다")
    public void cantFindUser() {
        Optional<User> foundUser = repository.findById(user.getId());

        assertThat(foundUser).isEmpty();
    }
}
