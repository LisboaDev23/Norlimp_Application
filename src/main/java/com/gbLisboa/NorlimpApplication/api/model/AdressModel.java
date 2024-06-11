package com.gbLisboa.NorlimpApplication.api.model;

import com.gbLisboa.NorlimpApplication.domain.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdressModel {

    private String road;

    private String number;

    private String neighborhood;

    private String city;

    private String state;

    private Long user;


}
