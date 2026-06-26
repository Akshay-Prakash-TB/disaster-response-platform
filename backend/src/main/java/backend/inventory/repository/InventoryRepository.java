package backend.inventory.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.inventory.entity.Inventory; 

@Repository
public interface InventoryRepository
        extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByShelterId(Long shelterId);
    List<Inventory> findAllByShelterId(Long shelterId);
}