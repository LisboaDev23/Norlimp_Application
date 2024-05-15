package com.gbLisboa.NorlimpApplication.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    @Column(name = "name_type", nullable = false, unique = true)
    @Size(max = 120)
    private String nameType;

    @NotBlank
    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany
    @JoinColumn(name = "type_id") //na tabela de serviço, join na coluna type_id
    private List<Service> serviceList;
}
