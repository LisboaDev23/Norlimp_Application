package com.gbLisboa.NorlimpApplication.api.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ScheduleModel {

    private Long id;

    private List<Date> dates;

    private Long user;

    private Long payment;

    private List<ServiceModel> serviceRequest;


}
