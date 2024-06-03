package com.gbLisboa.NorlimpApplication.domain.service;

import com.gbLisboa.NorlimpApplication.domain.model.Adress;
import com.gbLisboa.NorlimpApplication.domain.repository.AdressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdressService {
    @Autowired
    private AdressRepository adressRepository;

    public Adress saveAdress (Adress adress) {
        return adressRepository.save(adress);
    }
    public void deleteAdressById (Adress adress, Long id) {
        if (adressRepository.findById(id).isPresent()){
            adressRepository.deleteById(id);
        } else {
            throw new RuntimeException("Não existe nenhum endereço com esse id!");
        }
    }
    public List<Adress> getAllAdressSortedByRoad (){
        return adressRepository.findAll().stream().
                sorted(Comparator.comparing(Adress::getRoad))
                .collect(Collectors.toList());
    }
    public List<Adress> getAllAdressSortedByNeighborhood (){
        return adressRepository.findAll().stream().
                sorted(Comparator.comparing(Adress::getNeighborhood))
                .collect(Collectors.toList());
    }
    public List<Adress> getAllAdressSortedByCity (){
        return adressRepository.findAll().stream().
                sorted(Comparator.comparing(Adress::getCity))
                .collect(Collectors.toList());
    }
    public List<Adress> getAllAdressSortedByState (){
        return adressRepository.findAll().stream().
                sorted(Comparator.comparing(Adress::getState))
                .collect(Collectors.toList());
    }

}
