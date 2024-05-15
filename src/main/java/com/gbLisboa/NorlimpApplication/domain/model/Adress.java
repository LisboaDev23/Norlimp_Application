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
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "tb_adress")
public class Adress {
    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    @Column(name = "road",nullable = false, length = 80)
    private String road;

    @NotBlank
    @Column(name = "number", nullable = false)
    private String number;

    @NotBlank
    @Column(name = "neighborhood", nullable = false)
    private String neighborhood;

    @NotBlank
    @Column(name = "city", nullable = false)
    @Size(max = 40)
    private String city;

    @NotBlank
    @Column(name = "state", nullable = false)
    @Size(min = 2, max = 40)
    private String state;

    @Valid
    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_adress"), nullable = false)
    private User user;
}
