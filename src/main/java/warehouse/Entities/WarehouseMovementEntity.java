package warehouse.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class WarehouseMovementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movementId;

    @ManyToOne
    @JoinColumn(name = "prod_id")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "rack_id")
    private RackEntity rack;

    private ACTION action;
    private LocalDateTime timeOfMovement;

    @ManyToOne
    @JoinColumn(name = "emp_id")
    private User employee;
}


