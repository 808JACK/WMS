package warehouse.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import warehouse.LogicDTOs.*;
import warehouse.Service.DailyCounterService;
import warehouse.Service.ProductService;
import warehouse.Service.WarehouseService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/warehouse")
public class WarehouseController {
    private final WarehouseService warehouseService;
    private final ProductService productService;
    private final DailyCounterService dailyCounterService;
    @PostMapping("/configure")
    public ResponseEntity<WarehouseResponseDTO> configureWarehouse(@RequestBody WarehouseConfigRequestDTO warehouseDTO){
        return warehouseService.configureWarehouse(warehouseDTO);
    }

    @PostMapping("/store/{rack_id}/{compartment_id}")

    public ResponseEntity<ProductStorageResponseDto> storeProduct(@PathVariable ("rack_id") Long rackId, @PathVariable("compartment_id")Long compartmentId ,@RequestBody ProductStorageRequestDTO productStorageDTO){

        ProductStorageResponseDto responseDto = productService.storeProduct(productStorageDTO,rackId,compartmentId);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/retrieve/{rack_id}/{compartment_id}/{product_id}")
    public ResponseEntity<ProductStorageResponseDto> retrieveProduct(@PathVariable ("rack_id") Long rackId,
                                                                     @PathVariable ("compartment_id")Long compartment_id,
                                                                     @PathVariable ("product_id")Long product_id){
        ProductStorageResponseDto responseDto = productService.retrieveProduct(rackId,compartment_id,product_id);
        return ResponseEntity.ok(responseDto);
    }
    @GetMapping("/daily-counts")
    public ResponseEntity<DailyCountsDTO> getDailyCounts() {
        DailyCountsDTO response = dailyCounterService.getDailyCounts();
        return ResponseEntity.ok(response);
    }

    private Long extractEmpIdFromToken(String token) {
        // Implement JWT parsing
        return 1L; // Placeholder
    }
}
