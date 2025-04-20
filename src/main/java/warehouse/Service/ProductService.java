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
    private final EmployeeService employeeService;
    private final DailyCounterService dailyCounterService;

    @Transactional
    public ProductStorageResponseDto storeProduct(ProductStorageRequestDTO request, Long rackId, Long compartmentId) {
        Long empId = (Long) SecurityContextHolder.getContext().getAuthentication().getDetails();
        log.info("Storing product, empId: " + empId);

        Optional<RackEntity> rackOpt = rackRepo.findById(rackId);
        if (rackOpt.isEmpty()) throw new IllegalArgumentException("Rack not found");
        RackEntity rack = rackOpt.get();

        Optional<CompartmentEntity> compartmentOpt = compartmentRepo.findById(compartmentId);
        if (compartmentOpt.isEmpty()) throw new IllegalArgumentException("Compartment not found");
        CompartmentEntity compartment = compartmentOpt.get();

        // Validate rack-compartment relationship
        if (!compartment.getRack().getRackId().equals(rackId)) {
            log.warn("Compartment {} belongs to Rack {}, not Rack {}", compartmentId, compartment.getRack().getRackId(), rackId);
            throw new IllegalArgumentException("Invalid compartment for Rack " + rackId);
        }
        Optional<User> userOpt = userRepo.findById(empId);
        if (userOpt.isEmpty()) throw new IllegalArgumentException("Employee not found");

        User employee = userOpt.get();
        Double productSize = request.getSize();

        // Check if compartment has enough space
        if (compartment.getArea() < productSize) {
            throw new IllegalStateException("Insufficient space in compartment");
        }

        // Replace compartment.reduceArea(productSize) with:
        compartment.increaseArea(productSize);

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
        movement.setProdId(product.getProdId());
        movement.setRack(rack);
        movement.setAction(ACTION.STORED);
        movement.setTimeOfMovement(LocalDateTime.now());
        movement.setEmployee(employee);

        movement = movementRepo.save(movement);

        dailyCounterService.incrementStoredCount();
        ProductStorageResponseDto response = new ProductStorageResponseDto();
        response.setProdId(product.getProdId());
        response.setProdName(product.getProdName());
        response.setCompartmentId(compartment.getCompartmentId());
        response.setRackId(rack.getRackId());
        response.setAction(String.valueOf(product.getAction()));
        response.setEmpId(empId);
        response.setTimeOfMovement(product.getTimeOfMovement());
        response.setMovementId(movement.getMovementId());

        return response;
    }

    @Transactional
    public ProductStorageResponseDto retrieveProduct(Long rackId, Long compartmentId, Long productId) {
        Long empId = (Long) SecurityContextHolder.getContext().getAuthentication().getDetails();
        log.info("Who's retrieving the Product: {}", empId);

        Optional<RackEntity> isRack = rackRepo.findById(rackId);
        if (isRack.isEmpty()) {
            throw new IllegalArgumentException("Rack not present with id: " + rackId);
        }

        Optional<CompartmentEntity> isCompartment = compartmentRepo.findById(compartmentId);
        if (isCompartment.isEmpty()) {
            throw new IllegalArgumentException("Compartment not present with id: " + compartmentId);
        }

        Optional<ProductEntity> isProduct = productRepo.findById(productId);
        if (isProduct.isEmpty()) {
            throw new IllegalArgumentException("Product not present with id: " + productId);
        }

        CompartmentEntity compartment = isCompartment.get();
        ProductEntity product = isProduct.get();
        RackEntity rack = isRack.get();

        if (!compartment.getRack().getRackId().equals(rackId) || !product.getCompartment().getCompartmentId().equals(compartmentId)) {
            throw new IllegalArgumentException("Invalid compartment or product-compartment mismatch");
        }

        // Reduce compartment space
        Double productSize = product.getSize();
        compartment.decreaseArea(productSize); // Use decreaseArea from CompartmentEntity

        // Log movement
        User employee = new User();
        employee.setUserId(empId);
        WarehouseMovementEntity movement = new WarehouseMovementEntity();
        movement.setProdId(product.getProdId()); // Reference product before deletion
        movement.setRack(rack);
        movement.setAction(ACTION.RETRIEVED);
        movement.setTimeOfMovement(LocalDateTime.now());
        movement.setEmployee(employee);
        movement = movementRepo.save(movement);

        // Delete product after saving movement
        productRepo.delete(product);


        // Build response
        ProductStorageResponseDto response = new ProductStorageResponseDto();
        response.setProdId(product.getProdId());
        response.setProdName(product.getProdName());
        response.setCompartmentId(compartment.getCompartmentId());
        response.setRackId(rack.getRackId());
        response.setAction(String.valueOf(ACTION.RETRIEVED));
        response.setEmpId(empId);
        response.setTimeOfMovement(movement.getTimeOfMovement());
        response.setMovementId(movement.getMovementId());

        dailyCounterService.incrementRetrievedCount();
//        TopRackResponseDTO topRack = employeeService.getTopRetrievedRack();
//        messagingTemplate.convertAndSend("top/top-rack",topRack);
        return response;
    }
}
