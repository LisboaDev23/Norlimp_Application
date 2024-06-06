package com.gbLisboa.NorlimpApplication.api.controller;

import com.gbLisboa.NorlimpApplication.api.model.LoginModel;
import com.gbLisboa.NorlimpApplication.domain.model.Login;
import com.gbLisboa.NorlimpApplication.domain.repository.LoginRepository;
import com.gbLisboa.NorlimpApplication.domain.service.LoginService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/login")
public class LoginController {

    private LoginRepository loginRepository;
    private LoginService loginService;
    private ModelMapper modelMapper;

    @GetMapping
    public List<Login> findAllLogins (){
        return loginRepository.findAll();
    }
    @GetMapping("/{loginId}")
    public ResponseEntity<LoginModel> findLogin(@PathVariable Long loginId){
        return loginRepository.findById(loginId)
                .map(login -> modelMapper.map(login, LoginModel.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Login registerLogin (@Valid @RequestBody Login login){
        return loginService.saveLogin(login);
    }
    @DeleteMapping("/{loginId}")
    public ResponseEntity<Void> deleteLogin (@PathVariable Long loginId){
        if (!loginRepository.existsById(loginId)){
            return ResponseEntity.notFound().build();
        }
        loginService.deleteLoginById(loginId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{loginId}")
    public ResponseEntity<Login> updateLogin (@PathVariable Long loginId,
                                              @Valid @RequestBody Login login){
        if (!loginRepository.existsById(loginId)){
            return ResponseEntity.notFound().build();
        }
        login.setId(loginId);
        login = loginService.saveLogin(login);
        return ResponseEntity.ok(login);
    }
}
