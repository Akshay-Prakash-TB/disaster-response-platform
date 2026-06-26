package backend.shelter.entity;

import java.util.ArrayList;
import java.util.List;

import backend.inventory.entity.Inventory;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "shelter")
public class Shelter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long ngoId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private Integer currentOccupancy;

    @Column(nullable = false)
    private String status;

    // RELATIONSHIP ADDED
    @OneToMany(mappedBy = "shelter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inventory> inventoryList = new ArrayList<>();

    // ---------- Constructors ----------

    public Shelter() {
    }

    public Shelter(Long id,
                   Long ngoId,
                   String name,
                   Double latitude,
                   Double longitude,
                   Integer capacity,
                   Integer currentOccupancy,
                   String status) {

        this.id = id;
        this.ngoId = ngoId;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.capacity = capacity;
        this.currentOccupancy = currentOccupancy;
        this.status = status;
    }

    // ---------- Getters ----------

    public Long getId() {
        return id;
    }

    public Long getNgoId() {
        return ngoId;
    }

    public String getName() {
        return name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Integer getCurrentOccupancy() {
        return currentOccupancy;
    }

    public String getStatus() {
        return status;
    }

    public List<Inventory> getInventoryList() {
        return inventoryList;
    }

    // ---------- Setters ----------

    public void setId(Long id) {
        this.id = id;
    }

    public void setNgoId(Long ngoId) {
        this.ngoId = ngoId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public void setCurrentOccupancy(Integer currentOccupancy) {
        this.currentOccupancy = currentOccupancy;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setInventoryList(List<Inventory> inventoryList) {
        this.inventoryList = inventoryList;
    }
}