package warehouse.LogicDTOs;

import lombok.Data;

@Data
public class WarehouseMovementDTO {
    private Long movementId;
    private ProductStorageRequestDTO product;
    private RackDTO rack;
    private String action; // "stored", "retrieved", etc.
    private String timeOfMovement;
    private Long empId;

    // Getters and Setters
}
