package com.gbLisboa.NorlimpApplication.api.model;

import com.gbLisboa.NorlimpApplication.domain.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginModel {

    private Long id;

    private String email;

    private String username;

    private String password;

    private Long user;


}
