package ru.solomatin.vinylstore.productservice.repository;

import org.springframework.data.repository.CrudRepository;
import ru.solomatin.vinylstore.productservice.model.Vinyl;

public interface VinylRepository extends CrudRepository<Vinyl, String> {
}
