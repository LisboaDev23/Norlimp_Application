package com.gbLisboa.NorlimpApplication.domain.service;

import com.gbLisboa.NorlimpApplication.domain.exception.AdressException;
import com.gbLisboa.NorlimpApplication.domain.exception.UserException;
import com.gbLisboa.NorlimpApplication.domain.model.Adress;
import com.gbLisboa.NorlimpApplication.domain.model.User;
import com.gbLisboa.NorlimpApplication.domain.repository.AdressRepository;
import com.gbLisboa.NorlimpApplication.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@AllArgsConstructor
@Service
public class AdressService {

    private AdressRepository adressRepository;
    private UserService userService;
    private UserRepository userRepository;

    public Adress findAdress(Long adressId){
        return adressRepository.findById(adressId)
                .orElseThrow(() -> new AdressException("Endereço não encontrado!"));
    }

    @Transactional
    public Adress saveAdress (Adress adress) {
        //boolean adressInUse = adressRepository.existsById(adress.getId());
        //if (adressInUse){
            //throw new AdressException("Não foi possível cadastrar este endereço, pois ele já está presente no banco de dados.");
        //}
        //User user = userService.findUser(adress.getUser().getId());
        //adress.setUser(user);
        return adressRepository.save(adress);
    }

    public List<Adress> findManyAdressByUser(User user){
        Long userFoundId = userService.findUser(user.getId()).getId();
        List<Adress> adressListUser = adressRepository.findAll();
        boolean userIsPresent = userRepository.findById(user.getId()).isPresent();
        if (!userIsPresent){
            throw new UserException("Usuário não encontrado no banco de dados, logo não é possível encontrar os seus respectivos endereços.");
        }

        for (Adress adress : adressListUser){
            if (adress.getUser().getId().equals(userFoundId)){
                adressListUser.add(adress);
            }
        }
        return adressListUser
                .stream()
                .filter(adress -> Objects.equals(adress.getUser().getId(), userFoundId))
                .collect(Collectors.toList());
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
