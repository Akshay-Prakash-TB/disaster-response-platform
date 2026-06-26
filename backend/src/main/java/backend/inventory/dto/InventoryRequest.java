package backend.inventory.dto;

public class InventoryRequest {

    private Long shelterId;
    private Integer food;
    private Integer water;
    private Integer medicine;
    private Integer blankets;

    public InventoryRequest() {
    }

    public Long getShelterId() {
        return shelterId;
    }

    public void setShelterId(Long shelterId) {
        this.shelterId = shelterId;
    }

    public Integer getFood() {
        return food;
    }

    public void setFood(Integer food) {
        this.food = food;
    }

    public Integer getWater() {
        return water;
    }

    public void setWater(Integer water) {
        this.water = water;
    }

    public Integer getMedicine() {
        return medicine;
    }

    public void setMedicine(Integer medicine) {
        this.medicine = medicine;
    }

    public Integer getBlankets() {
        return blankets;
    }

    public void setBlankets(Integer blankets) {
        this.blankets = blankets;
    }
}