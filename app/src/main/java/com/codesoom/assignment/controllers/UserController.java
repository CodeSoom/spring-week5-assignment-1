package com.codesoom.assignment.controllers;

import com.codesoom.assignment.ResourceNotFoundException;
import com.codesoom.assignment.application.UserCommandService;
import com.codesoom.assignment.application.UserQueryService;
import com.codesoom.assignment.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 사용자를 모두 조회,생성,수정,삭제 한다.
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserQueryService query;
    private final UserCommandService command;

    public UserController(UserQueryService query, UserCommandService command) {
        this.query = query;
        this.command = command;
    }

    /**
     * 모든 사용자를 조회한다.
     */
    @GetMapping
    public List<User> findAll(){
        return query.findAll();
    }

    /**
     * 사용자의 정보를 저장한다.
     *
     * @param user 등록할 사용자의 정보
     * @throws MethodArgumentNotValidException 필수 정보가 비어있는 경우
     * @return 저장된 사용자의 정보
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody @Valid User user){
        return command.save(user);
    }

    /**
     * 사용자의 정보를 수정한다.
     *
     * @param id 수정할 사용자의 식별자
     * @param user 수정할 정보
     * @throws ResourceNotFoundException 식별자에 해당하는 사용자가 없는 경우
     * @throws MethodArgumentNotValidException 필수 정보가 비어있는 경우
     * @return 수정된 사용자의 정보
     */
    @PatchMapping("{id}")
    public User update(@PathVariable Long id , @RequestBody User user){
        return command.update(query.findUser(id) , user);
    }

    /**
     * 사용자의 정보를 삭제한다.
     *
     * @param id 삭제할 사용자의 식별자
     * @throws ResourceNotFoundException 식별자에 해당하는 사용자가 없는 경우
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        command.delete(query.findUser(id));
    }
}
