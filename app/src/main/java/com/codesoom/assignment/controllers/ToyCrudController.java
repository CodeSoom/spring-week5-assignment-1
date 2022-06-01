package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ToyCrudService;
import com.codesoom.assignment.controllers.dtos.ToyRequestData;
import com.codesoom.assignment.controllers.dtos.ToyResponseData;
import com.codesoom.assignment.controllers.interfaces.*;
import com.codesoom.assignment.domain.Toy;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ToyCrudController implements ToyCreateController, ToyDetailController,
        ToyListController, ToyUpdateController, ToyDeleteController {
    private final ToyCrudService service;

    public ToyCrudController(ToyCrudService service) {
        this.service = service;
    }

    @GetMapping
    @Override
    public List<ToyResponseData> list() {
        return service.showAll().stream()
                .map(toy -> new ToyResponseData().dtoFrom(toy))
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    @Override
    public ToyResponseData detail(@PathVariable Long id) {
        Toy toy = service.showById(id);
        return new ToyResponseData().dtoFrom(toy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public ToyResponseData create(@RequestBody @Valid ToyRequestData requestDto) {
        Toy toy = service.create(requestDto.toEntity());
        return new ToyResponseData().dtoFrom(toy);
    }

    @PatchMapping("{id}")
    @Override
    public ToyResponseData update(@PathVariable Long id, @RequestBody @Valid ToyRequestData requestDto) {
        Toy toy = service.update(id, requestDto.toEntity());
        return new ToyResponseData().dtoFrom(toy);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void delete(@PathVariable Long id) {
        service.deleteBy(id);
    }
}
