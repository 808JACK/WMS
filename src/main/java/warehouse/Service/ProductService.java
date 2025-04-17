//package warehouse.Service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import warehouse.Entities.RackEntity;
//import warehouse.LogicDTOs.ProductStorageRequestDTO;
//import warehouse.LogicDTOs.ProductStorageResponseDto;
//import warehouse.Repos.CompartmentRepo;
//import warehouse.Repos.ProductRepo;
//import warehouse.Repos.RackRepo;
//import warehouse.Repos.WarehouseMovementRepo;
//
//import javax.swing.text.html.Option;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class ProductService {
//
//    private final ProductRepo productRepo;
//    private final CompartmentRepo compartmentRepo;
//    private final RackRepo rackRepo;
//    private final WarehouseMovementRepo movementRepo;
//    public ProductStorageResponseDto storeProduct(ProductStorageRequestDTO productStorageDTO, Long rackId, Long compartmentId) {
//
//        Optional<RackEntity> rackOpt = rackRepo.findById(rackId);
//        if (rackOpt.isEmpty()) {
//            throw new IllegalArgumentException("Rack with ID " + rackId + " not found");
//        }
//        RackEntity rack = rackOpt.get();
//
//
//    }
//}
