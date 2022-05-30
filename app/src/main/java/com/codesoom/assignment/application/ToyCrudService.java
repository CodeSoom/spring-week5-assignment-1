package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.application.interfaces.*;
import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.domain.ToyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToyCrudService implements ToyCreateService, ToyShowService, ToyUpdateService, ToyDeleteService {
    private final ToyRepository repository;

    public ToyCrudService(ToyRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Toy> showAll() {
        return repository.findAll();
    }

    @Override
    public Toy showById(Long id) {
        return repository.findById(id).stream()
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public Toy create(Toy toy) {
        return repository.save(toy);
    }

    @Override
    public Toy update(Long id, Toy toy) {
        Toy found = showById(id);

        Toy toyUpdating = Toy.builder()
                .name(toy.getName())
                .price(toy.getPrice())
                .producer(toy.getProducer())
                .demo(toy.getDemo())
                .build();
        return repository.save(toyUpdating);
    }

    @Override
    public void deleteBy(Long id) {
        repository.deleteById(id);
    }
}


