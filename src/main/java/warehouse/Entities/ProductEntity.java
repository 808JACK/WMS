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
    private ACTION action;
    private Long userId;
    private LocalDateTime timeOfMovement;

    @ManyToOne
    @JoinColumn(name = "compartment_id")
    private CompartmentEntity compartment;
}