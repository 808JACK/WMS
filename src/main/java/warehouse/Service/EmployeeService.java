package warehouse.Service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import warehouse.Entities.ProductEntity;
import warehouse.Entities.RackEntity;
import warehouse.LogicDTOs.ProductStorageResponseDto;
import warehouse.Repos.ProductRepo;
import warehouse.Repos.RackRepo;
import warehouse.Repos.WarehouseRepo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final WarehouseRepo warehouseRepo;
    private final RackRepo rackRepo;
    private final ProductRepo productRepo;
    private final ModelMapper modelMapper;

}
