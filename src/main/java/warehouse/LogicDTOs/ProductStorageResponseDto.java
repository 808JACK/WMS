package warehouse.LogicDTOs;

import lombok.Data;
import warehouse.Entities.ACTION;
import java.time.LocalDateTime;

@Data
public class ProductStorageResponseDto {
    private Long prodId;
    private String prodName;
    private Long compartmentId;
    private Long rackId;
    private String category;
    private Double size;
    private Double weight;
    private String action;
    private Long empId; // Maps to ProductEntity.userId
    private LocalDateTime timeOfMovement;
    private Long movementId;
}