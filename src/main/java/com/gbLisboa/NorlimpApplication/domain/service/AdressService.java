package com.gbLisboa.NorlimpApplication.domain.service;

import com.gbLisboa.NorlimpApplication.api.model.AdressModel;
import com.gbLisboa.NorlimpApplication.domain.exception.AdressException;
import com.gbLisboa.NorlimpApplication.domain.model.Adress;
import com.gbLisboa.NorlimpApplication.domain.repository.AdressRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
@AllArgsConstructor
@Service
public class AdressService {

    private AdressRepository adressRepository;
    private final ModelMapper modelMapper;

    public AdressModel findAdress (Long adressId){
        return adressRepository.findById(adressId)
                .map(this::toAdressModel)
                .orElseThrow(() -> new AdressException("Endereço não encontrado!"));
    }

    public List<AdressModel> findAllAdress (){
        return adressRepository.findAll()
                .stream()
                .map(this::toAdressModel)
                .collect(Collectors.toList());
    }

    @Transactional
    public AdressModel saveAdress (Adress adress) {
        try {
            adressRepository.save(adress);
            return modelMapper.map(adress, AdressModel.class);
        } catch (RuntimeException e){
            throw new AdressException("Não foi possível cadastrar o endereço!");
        }
    }

    @Transactional
    public void deleteAdress (Long adressId){
        try {
            adressRepository.deleteById(adressId);
        } catch (RuntimeException e){
            if (!adressRepository.existsById(adressId)) {
                throw new AdressException("Endereço não encontrado!");
            }
            throw new AdressException("Não foi possível excluir o endereço!");
        }
    }

    public List<AdressModel> getAllAdressSortedByRoad (){
        return adressRepository.findAll().stream().
                sorted(Comparator.comparing(Adress::getRoad))
                .map(adress -> modelMapper.map(adress, AdressModel.class))
                .collect(Collectors.toList());
    }

    public List<AdressModel> getAllAdressSortedByNeighborhood (){
        return adressRepository.findAll().stream().
                sorted(Comparator.comparing(Adress::getNeighborhood))
                .map(adress -> modelMapper.map(adress, AdressModel.class))
                .collect(Collectors.toList());
    }
    public List<AdressModel> getAllAdressSortedByCity (){
        return adressRepository.findAll().stream().
                sorted(Comparator.comparing(Adress::getCity))
                .map(adress -> modelMapper.map(adress, AdressModel.class))
                .collect(Collectors.toList());
    }
    public List<AdressModel> getAllAdressSortedByState (){
        return adressRepository.findAll().stream().
                sorted(Comparator.comparing(Adress::getState))
                .map(adress -> modelMapper.map(adress, AdressModel.class))
                .collect(Collectors.toList());
    }
    private AdressModel toAdressModel(Adress adress){
        return modelMapper.map(adress, AdressModel.class);
    }

}
