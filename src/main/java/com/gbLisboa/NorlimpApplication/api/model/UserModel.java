package com.gbLisboa.NorlimpApplication.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gbLisboa.NorlimpApplication.domain.model.Adress;
import com.gbLisboa.NorlimpApplication.domain.model.Login;
import com.gbLisboa.NorlimpApplication.domain.model.Schedule;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class UserModel {

    private Long id;

    @NotBlank(message = "Nome é obrigatório!")
    @Size(min = 10, max = 45,
            message = "Nome deve ter pelo menos 10 caracteres e não pode ultrapassar de 45 caracteres!")
    private String name;

    @NotEmpty
    @Size(min = 11,max = 14)
    private String cpf;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate birthday;

    @NotNull(message = "Email é obrigatório!")
    @Email(message = "Email deve ser válido!")
    private String email;

    @NotBlank
    @Size(max = 14)
    private String telephone;

}
