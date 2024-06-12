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
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/type")
public class TypeController {

    private TypeService typeService;
    private TypeRepository typeRepository;
    private ModelMapper modelMapper;

    @GetMapping("/list")
    public List<TypeModel> findAll(){
        return typeService.findAllTypes();
    }

    @GetMapping("{typeId}")
    public ResponseEntity<TypeModel> find (@PathVariable Long typeId){
        if (!typeRepository.existsById(typeId)){
            return ResponseEntity.notFound().build();
        }
        TypeModel typeFound = typeService.findType(typeId);
        return ResponseEntity.ok(typeFound);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public TypeModel register(@RequestBody TypeModel typeModel){
        return typeService.saveType(typeModel);
    }

    @DeleteMapping("/delete/{typeId}")
    public ResponseEntity<Void> delete(@PathVariable Long typeId){
        if (!typeRepository.existsById(typeId)){
            return ResponseEntity.notFound().build();
        }
        typeService.deleteType(typeId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/update/{typeId}")
    public ResponseEntity<TypeModel> update(@PathVariable Long typeId,
                                                      @Valid @RequestBody TypeModel typeModel){
        if (!typeRepository.existsById(typeId)){
            return ResponseEntity.notFound().build();
        }
        TypeModel typeUpdate = typeService.updateModel(typeId, typeModel);
        return ResponseEntity.ok(typeUpdate);
    }


}
