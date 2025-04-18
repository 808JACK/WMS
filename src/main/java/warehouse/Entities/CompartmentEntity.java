package warehouse.Entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Entity
@Data
@Getter
public class CompartmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long compartmentId;

    private Double area;

    @ManyToOne
    @JoinColumn(name = "rack_id")
    private RackEntity rack;

    @OneToMany(mappedBy = "compartment", cascade = CascadeType.ALL)
    private List<ProductEntity> products;

    public void decreaseArea(Double productSize) {
        if (this.area < productSize) {
            throw new IllegalStateException("Insufficient area in compartment: " + this.compartmentId);
        }
        this.area -= productSize.longValue();
    }
    public void increaseArea(Double productSize) {
        this.area += productSize.longValue();
    }
}
