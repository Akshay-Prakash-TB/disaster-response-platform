package backend.auth.dto;

public class RescueUserDTO {

    private Long id;
    private String name;

    public RescueUserDTO(
            Long id,
            String name) {

        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}