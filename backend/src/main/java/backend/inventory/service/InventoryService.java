package backend.inventory.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.inventory.dto.InventoryRequest;
import backend.inventory.dto.InventoryResponse;
import backend.inventory.entity.Inventory;
import backend.inventory.repository.InventoryRepository;
import backend.shelter.entity.Shelter;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    // ---------------- CREATE ----------------
    public InventoryResponse createInventory(InventoryRequest request) {

        // check if inventory already exists for shelter
        Inventory existing =
                inventoryRepository.findByShelterId(request.getShelterId())
                        .orElse(null);

        if (existing != null) {
            return convertToResponse(existing);
        }

        Inventory inventory = new Inventory();

        // IMPORTANT: using relation (not raw ID)
        Shelter shelter = new Shelter();
        shelter.setId(request.getShelterId());

        inventory.setShelter(shelter);

        inventory.setFood(request.getFood());
        inventory.setWater(request.getWater());
        inventory.setMedicine(request.getMedicine());
        inventory.setBlankets(request.getBlankets());

        Inventory saved = inventoryRepository.save(inventory);

        return convertToResponse(saved);
    }

    // ---------------- GET ALL ----------------
    public List<InventoryResponse> getAllInventories() {

        List<Inventory> list = inventoryRepository.findAll();

        List<InventoryResponse> responseList = new ArrayList<>();

        for (Inventory i : list) {
            responseList.add(convertToResponse(i));
        }

        return responseList;
    }

    // ---------------- GET BY ID ----------------
    public InventoryResponse getInventoryById(Long id) {

        Inventory inventory =
                inventoryRepository.findById(id)
                        .orElse(null);

        if (inventory == null) return null;

        return convertToResponse(inventory);
    }

    // ---------------- GET BY SHELTER ----------------
    public InventoryResponse getInventoryByShelter(Long shelterId) {

        Inventory inventory =
                inventoryRepository.findByShelterId(shelterId)
                        .orElse(null);

        if (inventory == null) return null;

        return convertToResponse(inventory);
    }

    // ---------------- UPDATE ----------------
    public InventoryResponse updateInventory(Long id, InventoryRequest request) {

        Inventory inventory =
                inventoryRepository.findById(id)
                        .orElse(null);

        if (inventory == null) return null;

        // safe update (no null overwrite issues)
        if (request.getFood() != null)
            inventory.setFood(request.getFood());

        if (request.getWater() != null)
            inventory.setWater(request.getWater());

        if (request.getMedicine() != null)
            inventory.setMedicine(request.getMedicine());

        if (request.getBlankets() != null)
            inventory.setBlankets(request.getBlankets());

        Inventory updated = inventoryRepository.save(inventory);

        return convertToResponse(updated);
    }

    // ---------------- DELETE ----------------
    public String deleteInventory(Long id) {

        Inventory inventory =
                inventoryRepository.findById(id)
                        .orElse(null);

        if (inventory == null) {
            return "Inventory not found";
        }

        inventoryRepository.deleteById(id);

        return "Inventory deleted successfully";
    }

    // ---------------- MAPPER ----------------
    private InventoryResponse convertToResponse(Inventory i) {

        InventoryResponse res = new InventoryResponse();

        res.setId(i.getId());

        // IMPORTANT FIX: now comes from relation
        res.setShelterId(i.getShelter().getId());

        res.setFood(i.getFood());
        res.setWater(i.getWater());
        res.setMedicine(i.getMedicine());
        res.setBlankets(i.getBlankets());

        return res;
    }

    public String deductInventory(Long shelterId,
                              Integer food,
                              Integer water,
                              Integer medicine,
                              Integer blankets) {

        Inventory inventory =
                inventoryRepository.findByShelterId(shelterId)
                        .orElse(null);

        if (inventory == null) {
            return "Inventory not found";
        }

        // ---------------- SAFETY CHECK ----------------
        if (inventory.getFood() < food ||
            inventory.getWater() < water ||
            inventory.getMedicine() < medicine ||
            inventory.getBlankets() < blankets) {

            return "Insufficient inventory stock";
        }

        // ---------------- DEDUCTION ----------------
        inventory.setFood(inventory.getFood() - food);
        inventory.setWater(inventory.getWater() - water);
        inventory.setMedicine(inventory.getMedicine() - medicine);
        inventory.setBlankets(inventory.getBlankets() - blankets);

        inventoryRepository.save(inventory);

        return "Inventory deducted successfully";
    }
}