package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ToyCrudService;
import com.codesoom.assignment.controllers.dtos.ToyRequestData;
import com.codesoom.assignment.controllers.dtos.ToyResponseData;
import com.codesoom.assignment.controllers.interfaces.*;
import com.codesoom.assignment.domain.Toy;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
                .map(toy -> new ToyResponseData().toDto(toy))
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    @Override
    public ToyResponseData detail(@PathVariable Long id) {
        Toy toy = service.showById(id);
        return new ToyResponseData().toDto(toy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public ToyResponseData create(@RequestBody ToyRequestData requestDto) {
        Toy toy = requestDto.toEntity();
        return new ToyResponseData().toDto(toy);
    }

    @PatchMapping("{id}")
    @Override
    public ToyResponseData update(@PathVariable Long id, @RequestBody ToyRequestData requestDto) {
        Toy toy = service.update(id, requestDto.toEntity());
        return new ToyResponseData().toDto(toy);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void delete(@PathVariable Long id) {
        service.deleteBy(id);
    }
}
