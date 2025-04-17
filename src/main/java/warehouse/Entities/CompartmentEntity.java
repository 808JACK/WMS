package warehouse.Entities;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class CompartmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long compartmentId;

    private Long area;

    @ManyToOne
    @JoinColumn(name = "rack_id")
    private RackEntity rack;

    @OneToMany(mappedBy = "compartment", cascade = CascadeType.ALL)
    private List<ProductEntity> products;

    public void reduceArea(Double productSize) {
        if (this.area < productSize) {
            throw new IllegalStateException("Insufficient area in compartment: " + this.compartmentId);
        }
        this.area -= productSize.longValue();
    }
}
