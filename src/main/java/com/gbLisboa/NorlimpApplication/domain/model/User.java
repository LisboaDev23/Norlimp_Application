package com.gbLisboa.NorlimpApplication.domain.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
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
    @Size(min = 11,max = 14)
    private String cpf;

    @NotBlank
    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @NotBlank
    @Size(max = 255)
    @Email
    private String email;

    @NotBlank
    @Column(name = "telephone", nullable = false)
    @Size(max = 14)
    private String telephone;

    @OneToMany
    private List<Adress> adressList;

    @Valid
    @NotBlank
    @OneToOne
    @JoinColumn(name = "login_id")
    private Login login;

    @OneToMany
    private List<Schedule> scheduleList;
}
