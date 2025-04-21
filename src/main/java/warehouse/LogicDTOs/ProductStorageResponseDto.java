package warehouse.LogicDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import warehouse.Entities.ACTION;
import warehouse.Entities.CATEGORY;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductStorageResponseDto {

    private String rackName;
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