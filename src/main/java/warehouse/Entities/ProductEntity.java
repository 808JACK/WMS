package warehouse.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prodId;
    private String prodName;
    @Enumerated(EnumType.STRING)
    private CATEGORY category;
    private Double size;
    private Double weight;

    @Enumerated(EnumType.STRING)
    private ACTION action; // Action could be something like "Added", "Removed", etc.
    private Long userId; // Assuming employee ID as a Long
    private LocalDateTime timeOfMovement; // Time of the movement

    @ManyToOne
    @JoinColumn(name = "compartment_id")
    private CompartmentEntity compartment; // Added to establish the relationship
}