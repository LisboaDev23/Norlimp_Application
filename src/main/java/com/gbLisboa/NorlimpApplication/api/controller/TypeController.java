package com.gbLisboa.NorlimpApplication.api.controller;

import com.gbLisboa.NorlimpApplication.api.model.TypeModel;
import com.gbLisboa.NorlimpApplication.domain.model.Type;
import com.gbLisboa.NorlimpApplication.domain.repository.TypeRepository;
import com.gbLisboa.NorlimpApplication.domain.service.TypeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/type")
public class TypeController {

    private TypeService typeService;
    private TypeRepository typeRepository;
    private ModelMapper modelMapper;

    @GetMapping
    public List<Type> findAllTypes(){
        return typeRepository.findAll();
    }
    @GetMapping("/{typeId}")
    public ResponseEntity<TypeModel> getType(@PathVariable Long typeId){
        return typeRepository.findById(typeId)
                .map(type -> modelMapper.map(type, TypeModel.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Type registerType(@RequestBody Type type){
        return typeService.saveType(type);
    }
    @DeleteMapping("/{typeId}")
    public ResponseEntity<Void> deleteType(@PathVariable Long typeId){
        if (!typeRepository.existsById(typeId)){
            return ResponseEntity.notFound().build();
        }
        typeService.deleteTypeById(typeId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{typeId}")
    public ResponseEntity<TypeModel> updateService(@PathVariable Long typeId,
                                                      @Valid @RequestBody Type type){
        type.setId(typeId);
        typeService.saveType(type);
        return typeRepository.findById(type.getId())
                .map(t -> modelMapper.map(t, TypeModel.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
