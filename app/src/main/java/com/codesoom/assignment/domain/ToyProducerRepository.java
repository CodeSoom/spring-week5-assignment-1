package com.codesoom.assignment.domain;

import com.codesoom.assignment.domain.entities.ToyProducer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ToyProducerRepository extends CrudRepository<ToyProducer, Long> {
    List<ToyProducer> findAll();

    Optional<ToyProducer> findById(Long id);

    ToyProducer save(ToyProducer producer);

    void delete(ToyProducer producer);
}
