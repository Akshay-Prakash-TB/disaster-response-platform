package backend.assignment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.assignment.entity.Assignment;

public interface AssignmentRepository
        extends JpaRepository<Assignment, Long> {

    List<Assignment> findByResourceId(Long resourceId);

    List<Assignment> findByIncidentId(Long incidentId);

    List<Assignment> findByStatus(String status);

    List<Assignment> findByStatusIn(List<String> statuses);

    Optional <Assignment>findFirstByIncidentId(Long incidentId);

    List<Assignment> findAllByOrderByAssignedAtDesc();

    List<Assignment> findByResourceIdIn(List<Long> resourceIds);
}