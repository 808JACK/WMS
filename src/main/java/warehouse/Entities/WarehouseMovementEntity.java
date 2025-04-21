package warehouse.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WarehouseMovementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movementId;

    @Enumerated(EnumType.ORDINAL)
    private ACTION action;

    // Getters and setters
    @Setter
    private Long prodId; // Replace @ManyToOne ProductEntity product

    @ManyToOne
//    @JoinColumn(name = "rack_id")
    private RackEntity rack;

    private LocalDateTime timeOfMovement;

    @ManyToOne
    private User employee;

}