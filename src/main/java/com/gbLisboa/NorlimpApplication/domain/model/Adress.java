package com.gbLisboa.NorlimpApplication.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "tb_adress")
public class Adress {
    @Id
    @NotNull(groups = ValidationGroups.AdressId.class)
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "road", length = 80)
    private String road;

    @NotBlank
    @Column(name = "number")
    private String number;

    @NotBlank
    @Column(name = "neighborhood")
    private String neighborhood;

    @NotBlank
    @Column(name = "city")
    @Size(max = 40)
    private String city;

    @NotBlank
    @Column(name = "state")
    @Size(min = 2, max = 40)
    private String state;

    @Valid
    @ConvertGroup(to = ValidationGroups.UserId.class)
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference
    private User user;
}
