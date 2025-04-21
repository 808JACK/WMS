package warehouse.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class WarehouseEntity {

    private String wareHouseName;

    public String getWareHouseName() {
        return wareHouseName;
    }

    public void setWareHouseName(String wareHouseName) {
        this.wareHouseName = wareHouseName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long warehouseId;

    private Double totalArea;
    private Double remainingSpace;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RackEntity> racks = new ArrayList<>();

    public void reduceRemainingSpace(Double productSize) {
        if (this.remainingSpace < productSize) {
            throw new IllegalStateException("Insufficient space in warehouse: " + this.warehouseId);
        }
        this.remainingSpace -= productSize;
    }
}
