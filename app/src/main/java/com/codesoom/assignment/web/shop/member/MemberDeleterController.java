package com.codesoom.assignment.web.shop.member;

import com.codesoom.assignment.application.member.MemberDeleter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberDeleterController {

    private final MemberDeleter memberDeleter;

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        memberDeleter.delete(id);
    }
}
