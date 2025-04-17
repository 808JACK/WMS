package warehouse.LogicDTOs;

import lombok.Data;

import java.util.List;

@Data
public class CompartmentDTO {
    private Long compartmentId;
    private Long area;

    private List<ProductStorageRequestDTO> products; // List of products in this compartment

    // Getters and Setters
}

