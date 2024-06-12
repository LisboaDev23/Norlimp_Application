package com.gbLisboa.NorlimpApplication.api.model;

import com.gbLisboa.NorlimpApplication.domain.model.Service;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TypeModel {

    @NotBlank(message = "Nome do tipo é obrigatório!")
    private String nameType;

    @Size(max = 255)
    private String description;

    private List<Service> serviceList;


}
