package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ToyCrudService;
import com.codesoom.assignment.controllers.dtos.ToyRequestData;
import com.codesoom.assignment.controllers.dtos.ToyResponseData;
import com.codesoom.assignment.controllers.interfaces.*;
import com.codesoom.assignment.domain.Toy;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ToyCrudController implements ToyDetailController, ToyListController {
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
}
