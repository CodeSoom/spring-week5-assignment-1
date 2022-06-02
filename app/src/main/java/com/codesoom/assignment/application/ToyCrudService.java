package com.codesoom.assignment.application;

import com.codesoom.assignment.application.exceptions.ProductNotFoundException;
import com.codesoom.assignment.application.interfaces.*;
import com.codesoom.assignment.domain.*;
import com.codesoom.assignment.domain.entities.Toy;
import com.codesoom.assignment.domain.entities.ToyProducer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToyCrudService implements ToyCreateService, ToyShowService, ToyUpdateService, ToyDeleteService {
    private final ToyRepository repository;
    private final ToyProducerRepository producerRepository;

    public ToyCrudService(ToyRepository repository, ToyProducerRepository producerRepository) {
        this.repository = repository;
        this.producerRepository = producerRepository;
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
        ToyProducer producer = producerRepository.save(toy.getProducer());
        Toy toySaving = Toy.builder()
                .name(toy.getName())
                .price(toy.getPrice())
                .producer(producer)
                .demo(toy.getDemo())
                .build();
        return repository.save(toySaving);
    }

    @Override
    public Toy update(Long id, Toy toy) {
        if (!repository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }

        Toy toyUpdating = Toy.builder()
                .id(id)
                .name(toy.getName())
                .price(toy.getPrice())
                .producer(toy.getProducer())
                .demo(toy.getDemo())
                .build();
        return repository.save(toyUpdating);
    }

    @Override
    public void deleteBy(Long id) {
        if (!repository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }

        repository.deleteById(id);
    }
}


