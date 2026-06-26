package backend.relief.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "relief_request")
public class ReliefRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long citizenId;

    @Column(nullable = false)
    private Long shelterId;

    @Column(nullable = false)
    private Integer food;

    @Column(nullable = false)
    private Integer water;

    @Column(nullable = false)
    private Integer medicine;

    @Column(nullable = false)
    private Integer blankets;

    @Column(nullable = false)
    private String status;

    public ReliefRequest() {
    }

    public ReliefRequest(
            Long id,
            Long citizenId,
            Long shelterId,
            Integer food,
            Integer water,
            Integer medicine,
            Integer blankets,
            String status) {

        this.id = id;
        this.citizenId = citizenId;
        this.shelterId = shelterId;
        this.food = food;
        this.water = water;
        this.medicine = medicine;
        this.blankets = blankets;
        this.status = status;
    }

    // ---------- GETTERS ----------

    public Long getId() {
        return id;
    }

    public Long getCitizenId() {
        return citizenId;
    }

    public Long getShelterId() {
        return shelterId;
    }

    public Integer getFood() {
        return food;
    }

    public Integer getWater() {
        return water;
    }

    public Integer getMedicine() {
        return medicine;
    }

    public Integer getBlankets() {
        return blankets;
    }

    public String getStatus() {
        return status;
    }

    // ---------- SETTERS ----------

    public void setId(Long id) {
        this.id = id;
    }

    public void setCitizenId(Long citizenId) {
        this.citizenId = citizenId;
    }

    public void setShelterId(Long shelterId) {
        this.shelterId = shelterId;
    }

    public void setFood(Integer food) {
        this.food = food;
    }

    public void setWater(Integer water) {
        this.water = water;
    }

    public void setMedicine(Integer medicine) {
        this.medicine = medicine;
    }

    public void setBlankets(Integer blankets) {
        this.blankets = blankets;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}