package backend.resource.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.resource.entity.Resource;

public interface ResourceRepository
        extends JpaRepository<Resource, Long> {

    List<Resource> findByStatus(String status);

    List<Resource> findByAssignedUserId(Long assignedUserId);
}