package warehouse.Controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import warehouse.LogicDTOs.ProductStorageResponseDto;
import warehouse.Service.DailyCounterService;
import warehouse.Service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping(path = "/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/product/id/{prodId}")
    public ResponseEntity<ProductStorageResponseDto> getProductById(@PathVariable("prodId") Long prodId) {
        ProductStorageResponseDto response = employeeService.getProductById(prodId);
        return ResponseEntity.ok(response);
    }

    private DailyCounterService dailyCounterService;
}
