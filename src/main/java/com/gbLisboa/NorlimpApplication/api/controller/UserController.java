package com.gbLisboa.NorlimpApplication.api.controller;

import com.gbLisboa.NorlimpApplication.api.model.UserModel;
import com.gbLisboa.NorlimpApplication.domain.model.User;
import com.gbLisboa.NorlimpApplication.domain.repository.UserRepository;
import com.gbLisboa.NorlimpApplication.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepository;
    private UserService userService;

    @GetMapping("/list")
    public List<UserModel> findAll(){
        return userService.findAllUsers();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserModel> find(@PathVariable Long userId){
        return userService.findUser(userId);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public UserModel register(@Valid @RequestBody UserModel userModel){
        return userService.saveUser(userModel);
    }
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> delete(@PathVariable Long userId) {
        if (!userRepository.existsById(userId)){
            return ResponseEntity.notFound().build();
        }
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/update/{userId}")
    public ResponseEntity<UserModel> update(@PathVariable Long userId,
                                                @Valid @RequestBody UserModel userModel){
        if (!userRepository.existsById(userId)){
            return ResponseEntity.notFound().build();
        }
        UserModel userUpdated = userService.updateUser(userId,userModel);
        return ResponseEntity.ok(userUpdated);
    }
}
