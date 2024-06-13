package com.gbLisboa.NorlimpApplication.domain.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gbLisboa.NorlimpApplication.domain.validation.ValidationGroups;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
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

    @NotBlank
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "value")
    private Double value;

    @OneToMany(mappedBy = "payment")
    @JsonManagedReference
    private List<Schedule> schedulesList;

}
