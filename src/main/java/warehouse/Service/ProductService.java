package warehouse.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import warehouse.Entities.*;
import warehouse.LogicDTOs.ProductStorageRequestDTO;
import warehouse.LogicDTOs.ProductStorageResponseDto;
import warehouse.Repos.*;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepo productRepo;
    private final CompartmentRepo compartmentRepo;
    private final RackRepo rackRepo;
    private final UserRepo userRepo;
    private final WarehouseMovementRepo movementRepo;
    private final JwtService jwtService;

    @Transactional
    public ProductStorageResponseDto storeProduct(ProductStorageRequestDTO request, Long rackId, Long compartmentId) {
        // Extract empId from JWT
//        String empIdStr = SecurityContextHolder.getContext().getAuthentication().getName();
        Long empId = (Long) SecurityContextHolder.getContext().getAuthentication().getDetails();
        log.info("what happeneing");
//        Long empId = jwtService.getUserIdFromToken()// Assuming empId is in the JWT subject
        log.info("what happeneing");

        // Validate rack
        Optional<RackEntity> rackOpt = rackRepo.findById(rackId);
        if (rackOpt.isEmpty()) throw new IllegalArgumentException("Rack not found");

        RackEntity rack = rackOpt.get();
        Optional<CompartmentEntity> compartmentOpt = compartmentRepo.findById(compartmentId);
        if (compartmentOpt.isEmpty()) throw new IllegalArgumentException("Compartment not found");

        CompartmentEntity compartment = compartmentOpt.get();
        if (!compartment.getRack().getRackId().equals(rackId))
            throw new IllegalArgumentException("Invalid compartment");

        Optional<User> userOpt = userRepo.findById(empId);
        if (userOpt.isEmpty()) throw new IllegalArgumentException("Employee not found");

        User employee = userOpt.get();
        Double productSize = request.getSize();
        compartment.reduceArea(productSize);
        rack.reduceCapacity(productSize);
        WarehouseEntity warehouse = rack.getWarehouse();
        if (warehouse.getRemainingSpace() < productSize)
            throw new IllegalStateException("Insufficient space");

        warehouse.setRemainingSpace(warehouse.getRemainingSpace() - productSize.longValue());

        ProductEntity product = new ProductEntity();
        product.setProdName(request.getProdName());
        product.setCategory(request.getCategory());
        product.setSize(request.getSize());
        product.setWeight(request.getWeight());
        product.setAction(ACTION.STORED);
        product.setUserId(empId);
        product.setTimeOfMovement(LocalDateTime.now());
        product.setCompartment(compartment);

        product = productRepo.save(product);

        WarehouseMovementEntity movement = new WarehouseMovementEntity();
        movement.setProduct(product);
        movement.setRack(rack);
        movement.setAction(ACTION.STORED);
        movement.setTimeOfMovement(LocalDateTime.now());
        movement.setEmployee(employee);

        movement = movementRepo.save(movement);

        ProductStorageResponseDto response = new ProductStorageResponseDto();
        response.setProdId(product.getProdId());
        response.setProdName(product.getProdName());
        response.setCompartmentId(compartment.getCompartmentId());
        response.setRackId(rack.getRackId());
        response.setAction(product.getAction());
        response.setEmpId(empId);
        response.setTimeOfMovement(product.getTimeOfMovement());
        response.setMovementId(movement.getMovementId());

        return response;
    }
}
