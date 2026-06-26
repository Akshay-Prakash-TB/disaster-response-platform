package backend.relief.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import backend.inventory.entity.Inventory;
import backend.inventory.repository.InventoryRepository;
import backend.notification.service.NotificationService;
import backend.relief.entity.ReliefRequest;
import backend.relief.repository.ReliefRequestRepository;

@Service
public class ReliefRequestService {

    @Autowired
    private ReliefRequestRepository repository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private NotificationService notificationService;

    // ---------------- CREATE REQUEST ----------------
    public ReliefRequest createRequest(ReliefRequest request) {

        request.setStatus("PENDING");

        return repository.save(request);
    }

    // ---------------- GET ALL ----------------
    public List<ReliefRequest> getAllRequests() {
        return repository.findAll();
    }

    // ---------------- GET BY ID ----------------
    public ReliefRequest getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    // =========================================================
    // 🚨 STEP 2 CORE FEATURE: APPROVE + DEDUCT INVENTORY
    // =========================================================

    @Transactional
    public String approveRequest(Long requestId) {

        ReliefRequest request =
                repository.findById(requestId).orElse(null);

        if (request == null) {
            return "Request not found";
        }

        if (!request.getStatus().equals("PENDING")) {
            return "Request already processed";
        }

        Inventory inventory =
                inventoryRepository.findByShelterId(request.getShelterId())
                        .orElse(null);

        if (inventory == null) {
            return "Inventory not found";
        }

        // ---------------- SAFETY CHECK ----------------
        if (inventory.getFood() < request.getFood() ||
            inventory.getWater() < request.getWater() ||
            inventory.getMedicine() < request.getMedicine() ||
            inventory.getBlankets() < request.getBlankets()) {

            return "Insufficient inventory stock";
        }

        // ---------------- DEDUCTION ----------------
        inventory.setFood(inventory.getFood() - request.getFood());
        inventory.setWater(inventory.getWater() - request.getWater());
        inventory.setMedicine(inventory.getMedicine() - request.getMedicine());
        inventory.setBlankets(inventory.getBlankets() - request.getBlankets());

        inventoryRepository.save(inventory);

        // ---------------- UPDATE REQUEST STATUS ----------------
        request.setStatus("APPROVED");
        repository.save(request);

        notificationService.createNotification(
                request.getCitizenId(),
                "Relief Request Approved",
                "Your relief request has been approved. Please collect your supplies from the selected shelter."
        );

        return "Request approved and inventory updated";
    }

    // ---------------- REJECT REQUEST ----------------
    public String rejectRequest(Long requestId) {

        ReliefRequest request =
                repository.findById(requestId).orElse(null);

        if (request == null) {
            return "Request not found";
        }

        request.setStatus("REJECTED");

        repository.save(request);

        notificationService.createNotification(
                request.getCitizenId(),
                "Relief Request Rejected",
                "Your relief request has been rejected."
        );

        return "Request rejected";
    }

    public List<ReliefRequest> getRequestsByCitizen(Long citizenId) {
        return repository.findByCitizenId(citizenId);
    }
}