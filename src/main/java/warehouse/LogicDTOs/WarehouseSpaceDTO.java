package warehouse.LogicDTOs;

import lombok.Data;

import java.util.List;

@Data
public class WarehouseSpaceDTO {
    private Long warehouseId;
    private List<RackDTO> racks; // List of racks with their areas and capacity
    private Long totalSpace;
    private Long usedSpace;
    private Long remainingSpace;

    // Getters and Setters
}
