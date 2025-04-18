package warehouse.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Data
public class WarehouseMovementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movementId;

    @Enumerated(EnumType.STRING)
    private ACTION action;

    // Getters and setters
    @Setter
    private Long prodId; // Replace @ManyToOne ProductEntity product

    @ManyToOne
    private RackEntity rack;

    private LocalDateTime timeOfMovement;

    @ManyToOne
    private User employee;

}