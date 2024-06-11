package com.gbLisboa.NorlimpApplication.domain.service;

import com.gbLisboa.NorlimpApplication.api.model.UserModel;
import com.gbLisboa.NorlimpApplication.domain.exception.UserException;
import com.gbLisboa.NorlimpApplication.domain.model.User;
import com.gbLisboa.NorlimpApplication.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;

    public ResponseEntity<UserModel> findUser(Long userId){
        return userRepository.findById(userId)
                .map(user -> modelMapper.map(user, UserModel.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public List<UserModel> findAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(this::toUserModel)
                .collect(Collectors.toList());
    }

    @Transactional
    public User saveUser(User user){
        boolean cpfInUse = userRepository.findByCpf(user.getCpf())
                .filter(u -> !u.equals(user))
                .isPresent();
        boolean emailInUse = userRepository.findByEmail(user.getEmail())
                .filter(u -> u.equals(user))
                .isPresent();
        if (cpfInUse || emailInUse){
            throw new UserException("Usuário com cpf ou email já cadastrado, tente novamente.");
        }
        return userRepository.save(user);
    }

    @Transactional
    public UserModel updateUser(User user){
        UserModel userModel = userRepository.findById(user.getId())
                .map(u -> modelMapper.map(u, UserModel.class))
                .orElseThrow(() -> new UserException("Usuário não encontrado!"));
        userRepository.save(user);
        return userModel;
    }

    @Transactional
    public void deleteUser(Long userId){
        try {
            userRepository.deleteById(userId);
        } catch (RuntimeException e){
            if (!userRepository.existsById(userId)){
                throw new UserException("Usuário não encontrado!");
            }
            throw new UserException("Usuário não excluído!");
        }
    }

    private UserModel toUserModel (User user){
        return modelMapper.map(user, UserModel.class);
    }
}
