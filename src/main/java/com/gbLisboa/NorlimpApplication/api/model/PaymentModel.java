package com.gbLisboa.NorlimpApplication.api.model;

import com.gbLisboa.NorlimpApplication.domain.model.Schedule;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaymentModel {

    private String description;

    private Double value;

    private List<ScheduleModel> schedulesList;


}
