package backend.inventory.dto;

public class InventoryResponse {

    private Long id;
    private Long shelterId;
    private Integer food;
    private Integer water;
    private Integer medicine;
    private Integer blankets;

    public InventoryResponse() {
    }

    public InventoryResponse(Long id, Long shelterId,
                                Integer food, Integer water,
                                Integer medicine, Integer blankets) {
        this.id = id;
        this.shelterId = shelterId;
        this.food = food;
        this.water = water;
        this.medicine = medicine;
        this.blankets = blankets;
    }

    public Long getId() {
        return id;
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

    public void setId(Long id) {
        this.id = id;
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
}