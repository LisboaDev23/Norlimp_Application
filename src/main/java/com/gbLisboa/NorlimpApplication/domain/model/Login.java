package com.gbLisboa.NorlimpApplication.domain.model;

import com.gbLisboa.NorlimpApplication.domain.validation.ValidationGroups;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_login")
public class Login {

    @NotNull
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotBlank
    @Size(max = 255)
    private String email;

    @NotBlank
    @Column(name = "username", nullable = false, unique = true)
    @Size(max = 80)
    private String username;

    @NotBlank
    @Column(name = "password", nullable = false)
    private String password;

    @Valid
    @ConvertGroup(from = Default.class, to = ValidationGroups.UserId.class)
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
