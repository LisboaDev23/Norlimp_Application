package com.gbLisboa.NorlimpApplication.api.model;

import com.gbLisboa.NorlimpApplication.domain.model.Schedule;
import com.gbLisboa.NorlimpApplication.domain.model.Type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceModel {

    private Long id;

    private String nameService;

    private String description;

    private Long type;

    private Long schedule;


}
