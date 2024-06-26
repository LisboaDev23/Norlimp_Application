package com.gbLisboa.NorlimpApplication.domain.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gbLisboa.NorlimpApplication.domain.validation.ValidationGroups;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_type")
public class Type {
    @Id
    @NotNull(groups = ValidationGroups.TypeId.class)
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "name_type", unique = true)
    @Size(max = 120)
    private String nameType;

    @NotBlank
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "type")
    @JsonManagedReference
    private List<Service> serviceList;
}
