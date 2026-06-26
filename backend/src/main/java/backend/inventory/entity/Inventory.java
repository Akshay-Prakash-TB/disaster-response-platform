package backend.inventory.entity;

import backend.shelter.entity.Shelter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shelter_id", nullable = false)
    private Shelter shelter;

    @Column(nullable = false)
    private Integer food;

    @Column(nullable = false)
    private Integer water;

    @Column(nullable = false)
    private Integer medicine;

    @Column(nullable = false)
    private Integer blankets;

    // ---------- Constructors ----------

    public Inventory() {
    }

    public Inventory(Long id, Shelter shelter, Integer food, Integer water,
                     Integer medicine, Integer blankets) {
        this.id = id;
        this.shelter = shelter;
        this.food = food;
        this.water = water;
        this.medicine = medicine;
        this.blankets = blankets;
    }

    // ---------- Getters ----------

    public Long getId() {
        return id;
    }

    public Shelter getShelter() {
        return shelter;
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

    // ---------- Setters ----------

    public void setId(Long id) {
        this.id = id;
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
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
}