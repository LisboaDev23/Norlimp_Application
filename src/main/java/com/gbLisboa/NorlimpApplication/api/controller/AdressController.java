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
    private UserRepository userRepository;

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
    @PostMapping("/{userId}/create")
    public AdressModel register (@PathVariable Long userId,@Valid @RequestBody AdressModel adressModel){
        return adressService.saveAdress(userId,adressModel);
    }

    @DeleteMapping("/delete/{adressId}")
    public ResponseEntity<Void> delete (@PathVariable Long adressId){
        if (!adressRepository.existsById(adressId)){
            return ResponseEntity.notFound().build();
        }
            adressService.deleteAdress(adressId);
            return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{userId}/{adressId}")
    public ResponseEntity<AdressModel> update (@PathVariable Long userId,@PathVariable Long adressId,
                                                @Valid @RequestBody AdressModel adressModel){
        if (!adressRepository.existsById(adressId) || !userRepository.existsById(userId)){
            return ResponseEntity.notFound().build();
        }
        adressRepository.findById(adressId)
                .orElseThrow(() -> new AdressException("Endereço não encontrado!"))
                .setId(adressId);
        AdressModel adressUpdate = adressService.saveAdress(userId,adressModel);
        return ResponseEntity.ok(adressUpdate);
    }

    @GetMapping("/adressSortedRoad")
    public List<AdressModel> findSortedByRoad(){
        return adressService.getAllAdressSortedByRoad();
    }
    @GetMapping("/adressSortedNeighborhood")
    public List<AdressModel> findSortedByNeighborhood(){
        return adressService.getAllAdressSortedByNeighborhood();
    }
    @GetMapping("/adressSortedCity")
    public List<AdressModel> findSortedByCity(){
        return adressService.getAllAdressSortedByCity();
    }
    @GetMapping("/adressSortedRoad")
    public List<AdressModel> findSortedByState(){
        return adressService.getAllAdressSortedByState();
    }


}
