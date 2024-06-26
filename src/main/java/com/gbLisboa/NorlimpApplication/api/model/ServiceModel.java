package com.gbLisboa.NorlimpApplication.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceModel {

    private Long id;

    @NotBlank(message = "Nome do serviço é obrigatório!")
    private String nameService;

    @Size(max = 255)
    private String description;

    @NotNull(message = "Serviço deve ter um tipo para ser classificado!")
    private TypeModel type;

    private ScheduleModel schedule;

}
