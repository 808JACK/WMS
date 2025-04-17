package warehouse.LogicDTOs;

import lombok.Data;

import java.util.List;

@Data
public class ProductMovementSummaryDTO {
    private Long productId;
    private String productName;
    private List<WarehouseMovementDTO> movements; // List of movements for this product

    // Getters and Setters
}

