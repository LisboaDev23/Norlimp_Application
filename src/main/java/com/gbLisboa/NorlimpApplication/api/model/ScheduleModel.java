package com.gbLisboa.NorlimpApplication.api.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ScheduleModel {

    private List<Date> dates;

    @NotNull(message = "O agendamento deve ter um id de usuário referenciando a qual usuário pertence esse agendamento!")
    private Long user;

    @NotNull(message = "O agendamento deve ter um id de pagamento referenciando a qual pagamento esse agendamento pertence!")
    private Long payment;


    private List<ServiceModel> serviceRequest;


}
