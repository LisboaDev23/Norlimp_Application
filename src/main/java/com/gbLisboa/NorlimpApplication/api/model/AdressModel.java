package com.gbLisboa.NorlimpApplication.api.model;

import com.gbLisboa.NorlimpApplication.domain.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdressModel {

    @NotBlank(message = "Rua é obrigatória!")
    private String road;

    @NotBlank(message = "Número é obrigatório!")
    private String number;

    @NotBlank(message = "Bairro é obrigatório!")
    private String neighborhood;

    @NotBlank(message = "Cidade é obrigatória!")
    private String city;

    @NotBlank(message = "Estado é obrigatório!")
    private String state;

    @NotNull
    private Long user;


}
