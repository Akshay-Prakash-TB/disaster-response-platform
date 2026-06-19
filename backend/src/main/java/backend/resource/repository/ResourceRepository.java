package backend.resource.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.resource.entity.Resource;

public interface ResourceRepository
        extends JpaRepository<Resource, Long> {
}