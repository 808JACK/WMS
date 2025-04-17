package warehouse.LogicDTOs;

import lombok.Data;
import warehouse.Entities.CATEGORY;

@Data
public class ProductStorageRequestDTO {

    private String prodName;
    private CATEGORY category;
    private Double size;
    private Double weight;
    private Long empId;

    // Getters and Setters
}
