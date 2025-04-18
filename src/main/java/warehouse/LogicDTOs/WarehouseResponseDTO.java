package warehouse.LogicDTOs;

import lombok.Data;

import java.util.List;

@Data
public class WarehouseResponseDTO {

    private Long warehouseId;
    private Double totalArea;
    private Double remainingSpace;
    private List<RackResponse> racks;

    @Data
    public static class RackResponse {
        private Long rackId;
        private Double capacity;
        private List<Long> compartmentIds;
    }
}
