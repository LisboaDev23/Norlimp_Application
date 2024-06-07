package com.gbLisboa.NorlimpApplication.domain.model;

import com.gbLisboa.NorlimpApplication.domain.validation.ValidationGroups;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_payment")
public class Payment {
    @Id
    @NotNull(groups = ValidationGroups.PaymentId.class)
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @NotBlank
    @Column(name = "value", nullable = false)
    private Double value;

    @OneToMany(mappedBy = "payment")
    private List<Schedule> schedulesList;

}
