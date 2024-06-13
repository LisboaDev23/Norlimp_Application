package com.gbLisboa.NorlimpApplication.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaymentModel {

    private Long id;

    @NotBlank(message = "Insira uma descrição para o pagamento ter algum tipo de lembrete.")
    private String description;

    @NotNull(message = "Insira o valor do pagamento, ele não deve ser nulo!")
    private Double value;
}
