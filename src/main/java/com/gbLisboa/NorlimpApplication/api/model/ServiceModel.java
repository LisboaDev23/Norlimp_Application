package com.gbLisboa.NorlimpApplication.api.model;

import com.gbLisboa.NorlimpApplication.domain.model.Schedule;
import com.gbLisboa.NorlimpApplication.domain.model.Type;
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

    @NotNull(message = "Serviço deve ter um id do tipo para referenciar a qual tipo o serviço pertence!")
    private TypeModel type;

    @NotNull(message = "Serviço deve ter um id do agendamento para referenciar a qual agendamento o serviço pertence!")
    private ScheduleModel schedule;

}
