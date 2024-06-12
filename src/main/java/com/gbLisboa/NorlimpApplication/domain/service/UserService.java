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
    public UserModel saveUser(UserModel userModel){
        boolean cpfInUse = userRepository.findByCpf(userModel.getCpf()).isPresent();
        boolean emailInUse = userRepository.findByEmail(userModel.getEmail()).isPresent();
        if (cpfInUse || emailInUse){
            throw new UserException("Usuário com cpf ou email já cadastrado, tente novamente.");
        }
        User user = new User();
        user.setName(userModel.getName());
        user.setCpf(userModel.getCpf());
        user.setBirthday(userModel.getBirthday());
        user.setEmail(userModel.getEmail());
        user.setTelephone(userModel.getTelephone());
        User userCreated = userRepository.save(user);
        return toUserModel(userCreated);
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

    @Transactional
    public UserModel updateUser(Long userId, UserModel userModel){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("Usuário não encontrado!"));
        if (!user.getName().equals(userModel.getName())){
            if (userRepository.findByName(userModel.getName()).isPresent()){
                throw new UserException("Nome de usuário já cadastrado, atualize com outro nome.");
            }
            user.setName(userModel.getName());
        }
        if (!user.getCpf().equals(userModel.getCpf())){
            if (userRepository.findByCpf(userModel.getCpf()).isPresent()){
                throw new UserException("Cpf já cadastrado, atualize com outro cpf.");
            }
            user.setCpf(userModel.getCpf());
        }
        user.setBirthday(userModel.getBirthday());
        if (!user.getEmail().equals(userModel.getEmail())){
            if (userRepository.findByEmail(userModel.getEmail()).isPresent()){
                throw new UserException("Email já cadastrado, atualize com outro email.");
            }
            user.setEmail(userModel.getEmail());
        }
        user.setTelephone(userModel.getTelephone());
        user.setId(userId);
        User userUpdate = userRepository.save(user);
        return toUserModel(userUpdate);
    }

    private UserModel toUserModel (User user){
        return modelMapper.map(user, UserModel.class);
    }
}
