package com.gbLisboa.NorlimpApplication.domain.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
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
@Table(name = "tb_user")
public class User {
    @NotNull//(groups = ValidationGroups.UserId.class)
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //autoincrement id
    private Long id;

    @NotBlank
    @Column(name = "name", nullable = false)
    @Size(max = 80,min = 12)
    private String name;

    @NotBlank
    @Column(name = "cpf", nullable = false, unique = true, updatable = false)
    @Size(max = 11)
    private String cpf;

    @NotBlank
    @Column(name = "email", nullable = false, unique = true)
    @Size(max = 256)
    @Email
    private String email;

    @NotBlank
    @Column(name = "telephone", nullable = false)
    @Size(max = 14)
    private String telephone;

    @Valid
    @NotNull
    @NotBlank
    @OneToOne(mappedBy = "adress_id")
    private AdressClient adress;

    @Valid
    @NotNull
    @OneToOne
    @JoinColumn(name = "login_id")
    private Login login;

    @Valid
    @NotNull
    @NotBlank
    @OneToMany(mappedBy = "tb_service")
    private List<Service> serviceRequest;

    @Valid
    @NotNull
    @NotBlank
    @OneToMany(mappedBy = "tb_schedulling")
    private List<Schedulling> schedulling;

}
