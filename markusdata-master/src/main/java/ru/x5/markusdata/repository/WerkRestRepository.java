package ru.x5.markusdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.x5.markusdata.entity.Werk;

@RepositoryRestResource(path = "werk")
public interface WerkRestRepository extends JpaRepository<Werk, Long> {
}

