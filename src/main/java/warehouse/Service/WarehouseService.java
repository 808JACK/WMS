package warehouse.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import warehouse.Entities.*;
import warehouse.LogicDTOs.*;
import warehouse.Repos.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        warehouse.setWareHouseName(request.getWareHouseName());
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
            rack.setRackName(rackConfig.getRackName());
            rack.setArea(rackConfig.getCapacity()); // Available area
            rack.setWarehouse(warehouse);
            rack.setRackName(rack.getRackName());
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
            rackResponse.setRackName(rack.getRackName());
            rackResponse.setCapacity(rack.getCapacity());
            rackResponse.setCompartmentIds(compartmentIds);
            rackResponses.add(rackResponse);
        }
        WarehouseResponseDTO response = new WarehouseResponseDTO();
        response.setWarehouseId(warehouse.getWarehouseId());
        response.setWareHouseName(warehouse.getWareHouseName());
        response.setTotalArea(warehouse.getTotalArea());
        response.setRemainingSpace(warehouse.getRemainingSpace());
        response.setRacks(rackResponses);


        return ResponseEntity.ok(response);
    }
//
//    public List<ProductStorageResponseDto> getProductByRack(Long rackId) {
//        // Validate rack exists
//        RackEntity rack = rackRepo.findById(rackId)
//                .orElseThrow(() -> new IllegalArgumentException("Rack does not exist with id: " + rackId));
//
//        // Find movements for the rack with action = STORED
//        List<WarehouseMovementEntity> movements = warehouseRepo.findByRackIdAndAction(rackId, "STORED");
//
//        // Map to DTO, fetching product details
//        return movements.stream()
//                .map(movement -> {
//                    ProductEntity product = productRepo.findById(movement.getProdId())
//                            .orElseThrow(() -> new IllegalArgumentException("Product not found for id: " + movement.getProdId()));
//                    return new ProductStorageResponseDto(
//                            product.getProdId(), // prodId
//                            product.getProdName(), // prodName
//                            product.getCompartment().getCompartmentId(), // compartmentId
//                            rackId, // rackId
//                            product.getCategory().toString(), // category
//                            Double.valueOf(product.getSize()), // size (assuming String in ProductEntity, convert to Double)
//                            product.getWeight(), // weight
//                            product.getAction().toString(), // action
//                            product.getUserId(), // empId
//                            product.getTimeOfMovement(), // timeOfMovement
//                            movement.getMovementId() // movementId
//                    );
//                })
//                .collect(Collectors.toList());
//    }
}