package com.gbLisboa.NorlimpApplication.domain.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_service")
public class Service {
    @NotNull
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //autoincrement id
    private Long id;

    @NotBlank
    @Column(name = "name_service", nullable = false, unique = true)
    @Size(min = 4,max = 80)
    private String nameService;

    @NotBlank
    @Column(name = "description", nullable = false)
    private String description;

    @NotBlank
    @ManyToOne
    @JoinColumn(name = "type_id", foreignKey = @ForeignKey(name = "fk_service_type"), nullable = false)
    @Valid
    private Type type;

    @ManyToOne
    @JoinColumn(name = "schedule_id", foreignKey = @ForeignKey(name = "fk_schedule_service"), nullable = false)
    @Valid
    private Schedule schedule;
}
