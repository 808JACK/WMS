package warehouse.Entities;

import java.util.List;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RackEntity {

    private String rackName;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long rackId;
    private Double area;
    private Double capacity;


    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private WarehouseEntity warehouse;

    @OneToMany(mappedBy = "rack", cascade = CascadeType.ALL)
    private List<CompartmentEntity> compartments;

    public void reduceCapacity(Double productSize) {
        if (this.capacity < productSize) {
            throw new IllegalStateException("Insufficient capacity in rack: " + this.rackId);
        }
        this.capacity -= productSize.longValue();
    }
}
