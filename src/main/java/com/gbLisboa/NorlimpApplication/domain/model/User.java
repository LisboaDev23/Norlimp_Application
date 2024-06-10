package com.gbLisboa.NorlimpApplication.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gbLisboa.NorlimpApplication.domain.validation.ValidationGroups;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_user")
public class User {
    @Id
    @NotNull(groups = ValidationGroups.UserId.class)
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY) //autoincrement id
    private Long id;

    @NotBlank
    @Column(name = "name")
    @Size(max = 80,min = 12)
    private String name;

    @NotBlank
    @Column(name = "cpf", unique = true, updatable = false)
    @Size(min = 11,max = 14)
    private String cpf;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @NotBlank
    @Size(max = 255)
    @Email
    private String email;

    @NotBlank
    @Column(name = "telephone")
    @Size(max = 14)
    private String telephone;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Adress> adressList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Schedule> scheduleList = new ArrayList<>();
}
