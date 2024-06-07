package com.gbLisboa.NorlimpApplication.api.controller;

import com.gbLisboa.NorlimpApplication.api.model.AdressModel;
import com.gbLisboa.NorlimpApplication.domain.model.Adress;
import com.gbLisboa.NorlimpApplication.domain.repository.AdressRepository;
import com.gbLisboa.NorlimpApplication.domain.service.AdressService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/adress")
public class AdressController {

    private AdressService adressService;
    private AdressRepository adressRepository;
    private final ModelMapper modelMapper;


    @GetMapping
    public List<Adress> findAllAdress (){
        return adressRepository.findAll();
    }

    @GetMapping("/{adressId}")
    public ResponseEntity<AdressModel> findAdress (@PathVariable Long adressId){
        return adressRepository.findById(adressId)
                .map(adress -> modelMapper.map(adress, AdressModel.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Adress registerAdress (@Valid @RequestBody Adress adress){
        return adressService.saveAdress(adress);
    }

    @DeleteMapping("/{adressId}")
    public ResponseEntity<Void> deleteAdress (@PathVariable Long adressId){
        if (!adressRepository.existsById(adressId)){
            return ResponseEntity.notFound().build();
        }
        adressRepository.deleteById(adressId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{adressId}")
    public ResponseEntity<Adress> updateAdress (@PathVariable Long adressId,
                                                @Valid @RequestBody Adress adress){
        if (!adressRepository.existsById(adressId)){
            return ResponseEntity.notFound().build();
        }
        adress.setId(adressId);
        adress = adressService.saveAdress(adress);
        return ResponseEntity.ok(adress);
    }

}
