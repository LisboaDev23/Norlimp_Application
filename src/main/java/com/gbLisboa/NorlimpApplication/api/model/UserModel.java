package com.gbLisboa.NorlimpApplication.api.model;

import com.gbLisboa.NorlimpApplication.domain.model.Adress;
import com.gbLisboa.NorlimpApplication.domain.model.Login;
import com.gbLisboa.NorlimpApplication.domain.model.Schedule;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class UserModel {

    private Long id;

    private String name;

    private String cpf;

    private LocalDate birthday;

    private String email;

    private String telephone;

    private Long login;


}
