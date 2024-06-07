package com.gbLisboa.NorlimpApplication.domain.model;

import com.gbLisboa.NorlimpApplication.domain.validation.ValidationGroups;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.ConvertGroup;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_service")
public class Service {
    @Id
    @NotNull(groups = ValidationGroups.ServiceId.class)
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY) //autoincrement id
    private Long id;

    @NotBlank
    @Column(name = "name_service", nullable = false, unique = true)
    @Size(min = 4,max = 80)
    private String nameService;

    @NotBlank
    @Column(name = "description", nullable = false)
    private String description;

    @Valid
    @ConvertGroup(from = Service.class, to = ValidationGroups.TypeId.class)
    @NotNull
    @ManyToOne
    private Type type;

    @Valid
    @ConvertGroup(from = Service.class,to = ValidationGroups.ScheduleId.class)
    @NotNull
    @ManyToOne
    private Schedule schedule;
}
