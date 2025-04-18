package warehouse.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import warehouse.Entities.CompartmentEntity;
import warehouse.Entities.RackEntity;
import warehouse.Entities.WarehouseEntity;
import warehouse.LogicDTOs.ProductStorageRequestDTO;
import warehouse.LogicDTOs.ProductStorageResponseDto;
import warehouse.LogicDTOs.WarehouseConfigRequestDTO;
import warehouse.LogicDTOs.WarehouseResponseDTO;
import warehouse.Repos.*;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final ModelMapper modelMapper;
    private final WarehouseRepo warehouseRepo;
    private final RackRepo rackRepo;
    private final CompartmentRepo compartmentRepo;
    private final ProductRepo productRepo;
    private final WarehouseMovementRepo warehouseMovementRepo;

    @Transactional
    public ResponseEntity<WarehouseResponseDTO> configureWarehouse(WarehouseConfigRequestDTO request) {
        // Validate request
        if (request.getTotalArea() == null || request.getTotalArea() <= 0) {
            return ResponseEntity.badRequest().body(null);
        }
        if (request.getRacks() == null || request.getRacks().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        // Create warehouse
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setTotalArea(request.getTotalArea());
        warehouse.setRemainingSpace(request.getTotalArea());
        warehouse = warehouseRepo.save(warehouse);

        List<WarehouseResponseDTO.RackResponse> rackResponses = new ArrayList<>();
        for (WarehouseConfigRequestDTO.RackConfig rackConfig : request.getRacks()) {
            if (rackConfig.getCapacity() == null || rackConfig.getCapacity() <= 0) {
                return ResponseEntity.badRequest().body(null);
            }
            if (rackConfig.getNumberOfCompartments() == null || rackConfig.getNumberOfCompartments() <= 0) {
                return ResponseEntity.badRequest().body(null);
            }

            // Check if warehouse has enough space for the rack
            if (warehouse.getRemainingSpace() < rackConfig.getCapacity()) {
                return ResponseEntity.badRequest().body(null);
            }

            RackEntity rack = new RackEntity();
            rack.setCapacity(rackConfig.getCapacity());
            rack.setArea(rackConfig.getCapacity()); // Available area
            rack.setWarehouse(warehouse);
            rack = rackRepo.save(rack);

            // Reduce warehouse space
            warehouse.setRemainingSpace(warehouse.getRemainingSpace() - rackConfig.getCapacity());
            warehouse = warehouseRepo.save(warehouse);

            List<Long> compartmentIds = new ArrayList<>();
            Double compartmentArea = rackConfig.getCapacity() / rackConfig.getNumberOfCompartments();
            for (int i = 0; i < rackConfig.getNumberOfCompartments(); i++) {
                // Check if rack has enough space for the compartment
                if (rack.getArea() < compartmentArea) {
                    throw new IllegalStateException("Insufficient rack space for compartment");
                }

                CompartmentEntity compartment = new CompartmentEntity();
                compartment.setArea(compartmentArea);
                compartment.setRack(rack);
                compartment = compartmentRepo.save(compartment);
                compartmentIds.add(compartment.getCompartmentId());

                // Reduce rack space
                rack.setArea(rack.getArea() - compartmentArea);
                rack = rackRepo.save(rack);
            }

            WarehouseResponseDTO.RackResponse rackResponse = new WarehouseResponseDTO.RackResponse();
            rackResponse.setRackId(rack.getRackId());
            rackResponse.setCapacity(rack.getCapacity());
            rackResponse.setCompartmentIds(compartmentIds);
            rackResponses.add(rackResponse);
        }

        WarehouseResponseDTO response = new WarehouseResponseDTO();
        response.setWarehouseId(warehouse.getWarehouseId());
        response.setTotalArea(warehouse.getTotalArea());
        response.setRemainingSpace(warehouse.getRemainingSpace());
        response.setRacks(rackResponses);

        return ResponseEntity.ok(response);
    }

}