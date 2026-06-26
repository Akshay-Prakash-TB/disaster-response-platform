package backend.relief.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.relief.entity.ReliefRequest;

public interface ReliefRequestRepository extends JpaRepository<ReliefRequest, Long> {
    List<ReliefRequest> findByCitizenId(Long citizenId);
}