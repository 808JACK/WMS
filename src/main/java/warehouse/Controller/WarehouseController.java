package warehouse.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import warehouse.LogicDTOs.ProductStorageRequestDTO;
import warehouse.LogicDTOs.ProductStorageResponseDto;
import warehouse.LogicDTOs.WarehouseConfigRequestDTO;
import warehouse.LogicDTOs.WarehouseResponseDTO;
import warehouse.Service.ProductService;
import warehouse.Service.WarehouseService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/warehouse")
public class WarehouseController {
    private final WarehouseService warehouseService;
//    private final ProductService productService;
    @PostMapping("/configure")
    public ResponseEntity<WarehouseResponseDTO> configureWarehouse(@RequestBody WarehouseConfigRequestDTO warehouseDTO){
        return warehouseService.configureWarehouse(warehouseDTO);
    }

    @PostMapping("/store/{rack_id}/{compartment_id}")

//    public ResponseEntity<ProductStorageResponseDto> storeProduct(@PathVariable ("rack_id") Long rackId, @PathVariable("compartment_id")Long compartmentId ,@RequestBody ProductStorageRequestDTO productStorageDTO){
//
//        ProductStorageResponseDto responseDto = productService.storeProduct(productStorageDTO,rackId,compartmentId);
//        return ResponseEntity.ok(responseDto);
//    }
}
