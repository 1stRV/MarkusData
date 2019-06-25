package ru.x5.markusdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.x5.markusdata.entity.Product;

@RepositoryRestResource(path = "product")
public interface ProductRestRepository extends JpaRepository<Product, Long> {
}

