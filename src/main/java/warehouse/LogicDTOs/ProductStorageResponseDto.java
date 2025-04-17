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
    private ACTION action;
    private Long empId;
    private LocalDateTime timeOfMovement;
    private Long movementId;
}