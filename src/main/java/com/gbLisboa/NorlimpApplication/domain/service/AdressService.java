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

import java.util.Comparator;
import java.util.List;
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
        boolean adressInUse = adressRepository.existsById(adress.getId());
        if (adressInUse){
            throw new AdressException("Não foi possível cadastrar este endereço, pois ele já está presente no banco de dados.");
        }
        User user = userService.findUser(adress.getUser().getId());
        adress.setUser(user);
        return adressRepository.save(adress);
    }
    @Transactional
    public void deleteAdress (Long adressId) {
        boolean adressIsPresent = adressRepository.existsById(adressId);
        if (!adressIsPresent){
            throw new AdressException("Não foi possível excluir o endereço informado pois ele não se encontra no banco de dados.");
        }
        adressRepository.deleteById(adressId);
    }

    public List<Adress> findManyAdressByUser(User user){
        boolean userIsPresent = userRepository.findById(user.getId()).isPresent();
        if (!userIsPresent){
            throw new UserException("Usuário não encontrado no banco de dados, logo não é possível encontrar os endereços.");
        }
        return userService.findUser(user.getId()).getAdressList();
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
