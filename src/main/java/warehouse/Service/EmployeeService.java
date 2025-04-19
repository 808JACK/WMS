package warehouse.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import warehouse.Entities.ACTION;
import warehouse.Entities.ProductEntity;
import warehouse.Entities.RackEntity;
import warehouse.Entities.WarehouseMovementEntity;
import warehouse.LogicDTOs.ProductStorageResponseDto;
import warehouse.Repos.ProductRepo;
import warehouse.Repos.RackRepo;
import warehouse.Repos.WarehouseMovementRepo;
import warehouse.Repos.WarehouseRepo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    private final WarehouseRepo warehouseRepo;
    private final RackRepo rackRepo;
    private final ProductRepo productRepo;
    private final ModelMapper modelMapper;
    private final WarehouseMovementRepo movementRepo;

    @Transactional
    public ProductStorageResponseDto getProductById(Long productId) {
        log.info("Fetching product with ID: {}", productId);

        Optional<ProductEntity> productOpt = productRepo.findById(productId);
        if (productOpt.isEmpty()) {
            throw new IllegalArgumentException("Product not found with ID: " + productId);
        }

        ProductEntity product = productOpt.get();
        ProductStorageResponseDto dto = modelMapper.map(product, ProductStorageResponseDto.class);
        dto.setCompartmentId(product.getCompartment().getCompartmentId());
        dto.setEmpId(productRepo.findById(productId).get().getUserId());
        dto.setRackId(product.getCompartment().getRack().getRackId());
        Optional<WarehouseMovementEntity> movementOpt = movementRepo.findByProdIdAndAction(productId, ACTION.STORED);
        if (movementOpt.isPresent()) {
            dto.setMovementId(movementOpt.get().getMovementId());
        } else {
            log.warn("No STORED movement found for product ID: {}", productId);
            dto.setMovementId(null); // Explicitly set null if no movement
        }
        return dto;
    }

//    @Transactional
//    public TopRackResponseDTO getTopRetrievedRack() {
//        log.info("Fetching rack with most retrievals");
//        Optional<Object[]> result = movementRepo.findTopRackByRetrievalCount();
//        if (result.isEmpty()) {
//            throw new IllegalStateException("No retrievals found in the warehouse");
//        }
//        Object[] data = result.get();
//        TopRackResponseDTO dto = new TopRackResponseDTO();
//        dto.setRackId((Long) data[0]);
//        dto.setRetrievalCount((Long) data[1]);
//        return dto;
//    }
}

