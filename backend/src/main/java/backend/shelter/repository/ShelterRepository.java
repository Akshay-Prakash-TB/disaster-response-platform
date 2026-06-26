package backend.shelter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.shelter.entity.Shelter;

@Repository
public interface ShelterRepository extends JpaRepository<Shelter, Long> {
    List<Shelter> findByNgoId(Long ngoId);
    List<Shelter> findByStatus(String status);
}