package com.gbLisboa.NorlimpApplication.api.controller;

import com.gbLisboa.NorlimpApplication.api.model.AdressModel;
import com.gbLisboa.NorlimpApplication.domain.exception.AdressException;
import com.gbLisboa.NorlimpApplication.domain.model.Adress;
import com.gbLisboa.NorlimpApplication.domain.repository.AdressRepository;
import com.gbLisboa.NorlimpApplication.domain.repository.UserRepository;
import com.gbLisboa.NorlimpApplication.domain.service.AdressService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/adress")
public class AdressController {

    private AdressService adressService;
    private AdressRepository adressRepository;

    @GetMapping("/list")
    public List<AdressModel> findAll (){
        return adressService.findAllAdress();
    }

    @GetMapping("/{adressId}")
    public ResponseEntity<AdressModel> find (@PathVariable Long adressId){
        if (!adressRepository.existsById(adressId)){
            return ResponseEntity.notFound().build();
        }
        AdressModel adressFound = adressService.findAdress(adressId);
        return ResponseEntity.ok(adressFound);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public AdressModel register (@Valid @RequestBody AdressModel adressModel){
        return adressService.saveAdress(adressModel);
    }

    @DeleteMapping("/delete/{adressId}")
    public ResponseEntity<Void> delete (@PathVariable Long adressId){
        if (!adressRepository.existsById(adressId)){
            return ResponseEntity.notFound().build();
        }
            adressService.deleteAdress(adressId);
            return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{adressId}")
    public ResponseEntity<AdressModel> update (@PathVariable Long adressId,
                                                @Valid @RequestBody AdressModel adressModel){
        if (!adressRepository.existsById(adressId)){
            return ResponseEntity.notFound().build();
        }
        AdressModel adressUpdate = adressService.updateAdress(adressId,adressModel);
        return ResponseEntity.ok(adressUpdate);
    }

}
