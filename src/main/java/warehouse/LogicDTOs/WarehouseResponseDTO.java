package warehouse.LogicDTOs;

import lombok.Data;

import java.util.List;

@Data
public class WarehouseResponseDTO {

    private Long warehouseId;
    private Long totalArea;
    private Long remainingSpace;
    private List<RackResponse> racks;

    @Data
    public static class RackResponse {
        private Long rackId;
        private Long capacity;
        private List<Long> compartmentIds;
    }
}
