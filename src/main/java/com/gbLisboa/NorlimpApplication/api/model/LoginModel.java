package com.gbLisboa.NorlimpApplication.api.model;

import com.gbLisboa.NorlimpApplication.domain.model.User;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginModel {
    @NotBlank(message = "Email é obrigatório!")
    @Email(message = "Email deve ser válido!")
    private String email;

    @NotBlank(message = "Nome de usuário obrigatório!")
    @Size(min = 6, max = 25,
            message ="Nome do usuário deve ter pelo menos 6 caracteres e não deve ter mais de 25 caracteres." )
    private String username;

    @NotBlank(message = "Senha obrigatória!")
    @Size(min = 8, max = 20,
            message ="Senha deve ter pelo menos 8 caracteres e não deve ter mais de 20 caracteres." )
    private String password;

    @NotNull(message = "O id do usuário deve ser informado para saber a qual usuário esse login se refere.")
    private Long user;
}
