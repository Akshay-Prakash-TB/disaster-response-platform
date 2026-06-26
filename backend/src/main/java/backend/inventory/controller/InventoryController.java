package backend.inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.inventory.dto.InventoryRequest;
import backend.inventory.dto.InventoryResponse;
import backend.inventory.service.InventoryService;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "http://localhost:5173")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    // ---------------- CREATE ----------------
    @PostMapping
    public ResponseEntity<InventoryResponse> createInventory(
            @RequestBody InventoryRequest request) {

        InventoryResponse response =
                inventoryService.createInventory(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ---------------- GET ALL ----------------
    @GetMapping
    public ResponseEntity<List<InventoryResponse>> getAllInventories() {

        List<InventoryResponse> list =
                inventoryService.getAllInventories();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // ---------------- GET BY ID ----------------
    @GetMapping("/{id}")
    public ResponseEntity<InventoryResponse> getInventoryById(
            @PathVariable Long id) {

        InventoryResponse response =
                inventoryService.getInventoryById(id);

        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // ---------------- GET BY SHELTER ----------------
    @GetMapping("/shelter/{shelterId}")
    public ResponseEntity<InventoryResponse> getByShelter(
            @PathVariable Long shelterId) {

        InventoryResponse response =
                inventoryService.getInventoryByShelter(shelterId);

        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // ---------------- UPDATE ----------------
    @PutMapping("/{id}")
    public ResponseEntity<InventoryResponse> updateInventory(
            @PathVariable Long id,
            @RequestBody InventoryRequest request) {

        InventoryResponse response =
                inventoryService.updateInventory(id, request);

        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // ---------------- DELETE ----------------
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInventory(
            @PathVariable Long id) {

        String result =
                inventoryService.deleteInventory(id);

        if (result.equals("Inventory not found")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/deduct/{shelterId}")
public ResponseEntity<String> deductInventory(
        @PathVariable Long shelterId,
        @RequestParam Integer food,
        @RequestParam Integer water,
        @RequestParam Integer medicine,
        @RequestParam Integer blankets) {

        String result = inventoryService.deductInventory(
                shelterId, food, water, medicine, blankets);

        if (result.equals("Inventory not found")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        if (result.equals("Insufficient inventory stock")) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}